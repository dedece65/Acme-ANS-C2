
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.AssignmentStatus;
import acme.entities.flightassignment.Duty;
import acme.entities.flightassignment.FlightAssignment;
import acme.entities.leg.Leg;
import acme.realms.AvailabilityStatus;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberFlightAssignmentCreateService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository repository;


	@Override
	public void authorise() {

		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean authorised1 = this.repository.existsFlightCrewMember(flightCrewMemberId);
		boolean authorised2 = true;
		if (super.getRequest().hasData("leg", int.class)) {
			int legId = super.getRequest().getData("leg", int.class);
			if (legId != 0)
				authorised2 = this.repository.existsLeg(legId);
		}

		boolean authorised = authorised1 && authorised2;

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		FlightAssignment flightAssignment;

		flightAssignment = new FlightAssignment();

		flightAssignment.setDraftMode(true);
		flightAssignment.setStatus(AssignmentStatus.PENDING);
		flightAssignment.setDuty(Duty.CABIN_ATTENDANT);

		flightAssignment.setCrewMember(this.repository.findMemberById(super.getRequest().getPrincipal().getActiveRealm().getId()));
		flightAssignment.setLastUpdate(MomentHelper.getCurrentMoment());
		flightAssignment.setRemarks("");
		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void bind(final FlightAssignment flightAssignment) {
		Integer legId;
		Leg leg;

		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		FlightCrewMember flightCrewMember = this.repository.findMemberById(flightCrewMemberId);

		legId = super.getRequest().getData("leg", int.class);
		leg = this.repository.findLegById(legId);

		super.bindObject(flightAssignment, "duty", "status", "remarks");
		flightAssignment.setLeg(leg);
		flightAssignment.setCrewMember(flightCrewMember);
	}

	private boolean compatibleLegs(final Leg newLeg, final Leg oldLeg) {
		return !(MomentHelper.isInRange(newLeg.getScheduledDeparture(), oldLeg.getScheduledDeparture(), oldLeg.getScheduledArrival()) || MomentHelper.isInRange(newLeg.getScheduledArrival(), oldLeg.getScheduledDeparture(), oldLeg.getScheduledArrival()));
	}

	private boolean isLegCompatible(final FlightAssignment flightAssignment) {

		Collection<Leg> legsByFlightCrewMember = this.repository.findLegsByFlightCrewMemberId(flightAssignment.getCrewMember().getId());
		Leg newLeg = flightAssignment.getLeg();

		return legsByFlightCrewMember.stream().anyMatch(existingLeg -> !this.compatibleLegs(newLeg, existingLeg));
	}

	private void checkPilotAndCopilotAssignment(final FlightAssignment flightAssignment) {
		boolean havePilot = this.repository.existsFlightCrewMemberWithDutyInLeg(flightAssignment.getLeg().getId(), Duty.PILOT);
		boolean haveCopilot = this.repository.existsFlightCrewMemberWithDutyInLeg(flightAssignment.getLeg().getId(), Duty.COPILOT);

		if (Duty.PILOT.equals(flightAssignment.getDuty()))
			super.state(!havePilot, "duty", "acme.validation.FlightAssignment.havePilot.message");
		if (Duty.COPILOT.equals(flightAssignment.getDuty()))
			super.state(!haveCopilot, "duty", "acme.validation.FlightAssignment.haveCopilot.message");
	}

	@Override
	public void validate(final FlightAssignment flightAssignment) {

		FlightCrewMember flightCrewMember = flightAssignment.getCrewMember();
		Leg leg = flightAssignment.getLeg();
		AvailabilityStatus status = flightAssignment.getCrewMember().getStatus();
		if (flightCrewMember != null && leg != null && this.isLegCompatible(flightAssignment)) {
			super.state(false, "crewMember", "acme.validation.FlightAssignment.FlightCrewMemberIncompatibleLegs.message");
			return;
		}
		if (leg != null)
			this.checkPilotAndCopilotAssignment(flightAssignment);
		if (!AvailabilityStatus.AVAILABLE.equals(status))
			super.state(false, "crewMember", "acme.validation.FlightAssignment.OnlyAvailableCanBeAssigned.message");
	}

	@Override
	public void perform(final FlightAssignment flightAssignment) {
		flightAssignment.setLastUpdate(MomentHelper.getCurrentMoment());
		this.repository.save(flightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {

		SelectChoices currentStatus;
		SelectChoices duty;

		Collection<Leg> legs;
		SelectChoices legChoices;

		Dataset dataset;

		legs = this.repository.findAllLegs();

		legChoices = SelectChoices.from(legs, "flightNumber", flightAssignment.getLeg());
		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		FlightCrewMember flightCrewMember = this.repository.findMemberById(flightCrewMemberId);

		currentStatus = SelectChoices.from(AssignmentStatus.class, flightAssignment.getStatus());
		duty = SelectChoices.from(Duty.class, flightAssignment.getDuty());

		dataset = super.unbindObject(flightAssignment, "duty", "lastUpdate", "status", "remarks", "draftMode");
		dataset.put("confirmation", false);
		dataset.put("readonly", false);
		dataset.put("lastUpdate", MomentHelper.getBaseMoment());
		dataset.put("status", currentStatus);
		dataset.put("duty", duty);
		dataset.put("leg", legChoices.getSelected().getKey());
		dataset.put("legs", legChoices);
		dataset.put("crewMember", flightCrewMember.getCode());

		super.getResponse().addData(dataset);
	}
}
