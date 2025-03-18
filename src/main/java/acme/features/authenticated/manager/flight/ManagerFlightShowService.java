
package acme.features.authenticated.manager.flight;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.realms.Manager;

@GuiService
public class ManagerFlightShowService extends AbstractGuiService<Manager, Flight> {

	// Internal State -------------------------------------------------------

	@Autowired
	private ManagerFlightRepository repository;

	// AbstractGuiService interface -----------------------------------------


	@Override
	public void authorise() {
		boolean exist;
		boolean ownsFlight;
		Flight flight;
		int id;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		exist = flight != null;
		ownsFlight = flight.getManager().getId() == super.getRequest().getPrincipal().getAccountId();
		if (!(exist && ownsFlight))
			exist = false;
		super.getResponse().setAuthorised(exist);
	}

	@Override
	public void load() {
		Flight flight;
		int id;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		super.getBuffer().addData(flight);
	}

	@Override
	public void unbind(final Flight flight) {
		assert flight != null;

		Dataset dataset;
		dataset = super.unbindObject(flight, "tag", "requiresSelfTransfer", "cost", "description");
		dataset.put("scheduledDeparture", flight.getScheduledDeparture());
		dataset.put("scheduledArrival", flight.getScheduledArrival());
		dataset.put("originCity", flight.getOriginCity());
		dataset.put("destinationCity", flight.getDestinationCity());
		dataset.put("layovers", flight.getLayovers());

		super.getResponse().addData(dataset);
	}
}
