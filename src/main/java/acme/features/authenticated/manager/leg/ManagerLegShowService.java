
package acme.features.authenticated.manager.leg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.Manager;

@GuiService
public class ManagerLegShowService extends AbstractGuiService<Manager, Leg> {

	// Internal State -------------------------------------------------------

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface -----------------------------------------


	@Override
	public void authorise() {
		boolean exist;
		boolean ownsFlight;
		Leg leg;
		int id;

		id = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(id);

		exist = leg != null;
		ownsFlight = leg.getManager().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		if (!(exist && ownsFlight))
			exist = false;
		super.getResponse().setAuthorised(exist);
	}

	@Override
	public void load() {
		Leg leg;
		int id;

		id = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(id);

		super.getBuffer().addData(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		assert leg != null;

		Dataset dataset;
		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport", "aircraft", "flight", "draftMode");

		dataset.put("legStatus", leg.getStatus());

		SelectChoices choices;
		choices = SelectChoices.from(LegStatus.class, leg.getStatus());
		dataset.put("choices", choices);

		if (leg.getAircraft() == null) {
			List<Aircraft> aircrafts = this.repository.findAllAircraft();
			SelectChoices aircraftChoices = SelectChoices.from(aircrafts, "id", leg.getAircraft());
			dataset.put("aircraftChoices", aircraftChoices);
		}

		if (leg.getFlight() == null) {
			List<Flight> flights = this.repository.findAllFlights();
			SelectChoices flightChoices = SelectChoices.from(flights, "id", leg.getFlight());
			dataset.put("flightChoices", flightChoices);
		}

		if (leg.getDepartureAirport() == null) {
			List<Airport> airports = this.repository.findAllAirports();
			SelectChoices airportChoices = SelectChoices.from(airports, "id", leg.getDepartureAirport());
			dataset.put("airportChoices", airportChoices);
		}
		super.getResponse().addData(dataset);
	}
}
