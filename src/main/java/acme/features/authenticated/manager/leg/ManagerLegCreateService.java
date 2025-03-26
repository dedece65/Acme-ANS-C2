
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
public class ManagerLegCreateService extends AbstractGuiService<Manager, Leg> {

	@Autowired
	private ManagerLegRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().getActiveRealm().getClass().equals(Manager.class));
	}

	@Override
	public void load() {
		Leg leg;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);

		leg = new Leg();

		leg.setFlight(this.repository.findFlightById(masterId));
		leg.setManager((Manager) super.getRequest().getPrincipal().getActiveRealm());
		leg.setDraftMode(true);

		super.getBuffer().addData(leg);
	}

	@Override
	public void bind(final Leg leg) {
		assert leg != null;

		super.bindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "departureAirport", "arrivalAirport", "aircraft");
	}

	@Override
	public void validate(final Leg leg) {
		assert leg != null;
		if (!this.getBuffer().getErrors().hasErrors("arrivalAirport") && leg.getArrivalAirport() != null)
			super.state(leg.getArrivalAirport() != leg.getDepartureAirport(), "arrivalAirport", "manager.flight.form.error.same-Airports", leg);

	}

	@Override
	public void perform(final Leg leg) {
		assert leg != null;
		int masterId = super.getRequest().getData("masterId", int.class);

		// Si hay ya un aircraft asignado a otro leg del mismo vuelo se queda ese aircraft
		List<Leg> legsFromFlight = this.repository.findLegsByFlight(masterId);
		if (legsFromFlight.size() > 0)
			leg.setAircraft(legsFromFlight.get(0).getAircraft());

		this.repository.save(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		assert leg != null;

		Dataset dataset;
		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport", "flight", "draftMode");
		dataset.put("masterId", leg.getFlight().getId());

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
