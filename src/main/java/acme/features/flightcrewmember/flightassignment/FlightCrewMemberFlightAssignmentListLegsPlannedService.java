
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.entities.leg.LegStatus;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberFlightAssignmentListLegsPlannedService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository repository;


	@Override
	public void authorise() {

		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean authorised = this.repository.existsFlightCrewMember(flightCrewMemberId);
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Collection<FlightAssignment> flightAssignments;

		LegStatus onTime = LegStatus.ON_TIME;
		LegStatus delayed = LegStatus.DELAYED;
		LegStatus cancelled = LegStatus.CANCELLED;
		int flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		flightAssignments = this.repository.findAllFlightAssignmentByLegStatus(onTime, flightCrewMemberId);
		flightAssignments.addAll(this.repository.findAllFlightAssignmentByLegStatus(delayed, flightCrewMemberId));
		flightAssignments.addAll(this.repository.findAllFlightAssignmentByLegStatus(cancelled, flightCrewMemberId));

		super.getBuffer().addData(flightAssignments);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset = super.unbindObject(flightAssignment, "duty", "lastUpdate", "status", "remarks", "draftMode");

		super.getResponse().addData(dataset);
	}
}
