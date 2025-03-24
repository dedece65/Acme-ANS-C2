
package acme.features.authenticated.manager.leg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
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

		leg = new Leg();

		leg.setManager((Manager) super.getRequest().getPrincipal().getActiveRealm());
		leg.setDraftMode(true);

		super.getBuffer().addData(leg);
	}

	@Override
	public void bind(final Leg leg) {
		assert leg != null;

		super.bindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "departureAirport", "arrivalAirport", "aircraft", "flight");
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
		SelectChoices choices;

		choices = SelectChoices.from(LegStatus.class, leg.getStatus());

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport", "aircraft", "flight");
		dataset.put("legStatus", choices.getSelected().getKey());
		dataset.put("legStatuses", choices);

		super.getResponse().addData(dataset);
	}
}
