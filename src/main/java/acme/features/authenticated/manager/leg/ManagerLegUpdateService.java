
package acme.features.authenticated.manager.leg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.Manager;

@GuiService
public class ManagerLegUpdateService extends AbstractGuiService<Manager, Leg> {

	@Autowired
	private ManagerLegRepository repository;


	@Override
	public void authorise() {
		int id;
		int accountId;
		accountId = super.getRequest().getPrincipal().getAccountId();
		id = super.getRequest().getData("id", int.class);
		Leg leg = this.repository.findLegById(id);

		super.getResponse().setAuthorised(leg.getManager().getUserAccount().getId() == accountId);
	}

	@Override
	public void load() {
		Leg leg;

		leg = new Leg();

		leg.setStatus(super.getRequest().getData("legStatus", LegStatus.class));
		leg.setFlight(this.repository.findFlightById(super.getRequest().getData("id", int.class)));
		leg.setManager((Manager) super.getRequest().getPrincipal().getActiveRealm());

		super.getBuffer().addData(leg);
	}

	@Override
	public void bind(final Leg leg) {
		assert leg != null;

		super.bindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport", "aircraft", "flight");
	}

	@Override
	public void validate(final Leg leg) {
		assert leg != null;
	}

	@Override
	public void perform(final Leg leg) {
		assert leg != null;
		int id = super.getRequest().getData("id", int.class);

		// Si hay ya un aircraft asignado a otro leg del mismo vuelo se queda ese aircraft
		List<Leg> legsFromFlight = this.repository.findLegsByFlight(id);
		if (legsFromFlight.size() > 0)
			leg.setAircraft(legsFromFlight.get(0).getAircraft());

		this.repository.save(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		assert leg != null;

		Dataset dataset;
		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport", "aircraft", "flight");

		SelectChoices choices;
		choices = SelectChoices.from(LegStatus.class, leg.getStatus());
		dataset.put("choices", choices);
		dataset.put("legStatus", leg.getStatus());

		List<Aircraft> aircrafts = this.repository.findAllAircraft();
		SelectChoices aircraftChoices = SelectChoices.from(aircrafts, "id", leg.getAircraft());
		dataset.put("aircraftChoices", aircraftChoices);

		List<Airport> airports = this.repository.findAllAirports();
		SelectChoices airportChoices = SelectChoices.from(airports, "id", leg.getDepartureAirport());
		dataset.put("airportChoices", airportChoices);

		super.getResponse().addData(dataset);
	}
}
