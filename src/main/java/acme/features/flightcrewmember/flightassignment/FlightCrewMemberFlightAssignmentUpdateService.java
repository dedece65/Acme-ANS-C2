
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
import acme.entities.leg.LegStatus;
import acme.realms.AvailabilityStatus;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberFlightAssignmentUpdateService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository repository;


	@Override
	public void authorise() {
		String method = super.getRequest().getMethod();
		boolean authorised;
		FlightAssignment flightAssignment = null;
		boolean isHis = false;
		if (method.equals("GET"))
			authorised = false;
		else {
			int flightAssignmentId = super.getRequest().getData("id", int.class);
			flightAssignment = this.repository.findFlightAssignmentById(flightAssignmentId);
			int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
			int legId = super.getRequest().getData("leg", int.class);
			boolean authorised3 = true;
			if (legId != 0)
				authorised3 = this.repository.existsLeg(legId);
			boolean authorised1 = this.repository.existsFlightCrewMember(flightCrewMemberId);
			authorised = authorised3 && authorised1 && this.repository.thatFlightAssignmentIsOf(flightAssignmentId, flightCrewMemberId);
			isHis = flightAssignment.getCrewMember().getId() == flightCrewMemberId;
		}
		super.getResponse().setAuthorised(authorised && flightAssignment != null && flightAssignment.getDraftMode() && isHis);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		FlightAssignment flightAssignment = this.repository.findFlightAssignmentById(id);
		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void bind(final FlightAssignment flightAssignment) {
		Integer legId = super.getRequest().getData("leg", int.class);
		Leg leg = this.repository.findLegById(legId);
		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		FlightCrewMember flightCrewMember = this.repository.findMemberById(flightCrewMemberId);

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
		FlightAssignment original = this.repository.findFlightAssignmentById(flightAssignment.getId());
		Leg leg = flightAssignment.getLeg();
		AvailabilityStatus status = flightAssignment.getCrewMember().getStatus();
		boolean cambioDuty = !original.getDuty().equals(flightAssignment.getDuty());
		boolean cambioLeg = !original.getLeg().equals(flightAssignment.getLeg());
		boolean cambioMoment = !original.getLastUpdate().equals(flightAssignment.getLastUpdate());
		boolean cambioStatus = !original.getStatus().equals(flightAssignment.getStatus());
		boolean draftedLeg = flightAssignment.getLeg() == null ? false : flightAssignment.getLeg().getDraftMode();

		if (!(cambioDuty || cambioLeg || cambioMoment || cambioStatus))
			return;

		if (leg != null && cambioLeg && !this.isLegCompatible(flightAssignment))
			super.state(false, "crewMember", "acme.validation.FlightAssignment.FlightCrewMemberIncompatibleLegs.message");

		if (leg != null && (cambioDuty || cambioLeg))
			this.checkPilotAndCopilotAssignment(flightAssignment);

		if (!AvailabilityStatus.AVAILABLE.equals(status))
			super.state(false, "crewMember", "acme.validation.FlightAssignment.OnlyAvailableCanBeAssigned.message");

		if (draftedLeg)
			super.state(false, "leg", "acme.validation.FlightAssignment.CannotUseDraftedLeg.message");
	}

	@Override
	public void perform(final FlightAssignment flightAssignment) {
		flightAssignment.setLastUpdate(MomentHelper.getCurrentMoment());
		this.repository.save(flightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Collection<Leg> legs = this.repository.findAllLegs();
		boolean isCompleted;
		int flightAssignmentId;

		flightAssignmentId = super.getRequest().getData("id", int.class);

		LegStatus landed = LegStatus.LANDED;
		isCompleted = this.repository.areLegsCompletedByFlightAssignment(flightAssignmentId, landed);
		SelectChoices legChoices = SelectChoices.from(legs, "flightNumber", flightAssignment.getLeg());
		SelectChoices currentStatus = SelectChoices.from(AssignmentStatus.class, flightAssignment.getStatus());
		SelectChoices duty = SelectChoices.from(Duty.class, flightAssignment.getDuty());
		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		FlightCrewMember flightCrewMember = this.repository.findMemberById(flightCrewMemberId);

		Dataset dataset = super.unbindObject(flightAssignment, "duty", "lastUpdate", "status", "remarks", "draftMode");
		dataset.put("readonly", false);
		dataset.put("lastUpdate", MomentHelper.getCurrentMoment());
		dataset.put("status", currentStatus);
		dataset.put("duty", duty);
		dataset.put("leg", legChoices.getSelected().getKey());
		dataset.put("legs", legChoices);
		dataset.put("crewMember", flightCrewMember.getCode());
		dataset.put("isCompleted", isCompleted);
		dataset.put("draftMode", flightAssignment.getDraftMode());

		super.getResponse().addData(dataset);
	}
}
