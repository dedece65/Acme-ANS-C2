
package acme.features.authenticated.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.leg.Leg;
import acme.realms.Manager;

@GuiService
public class ManagerLegListAllService extends AbstractGuiService<Manager, Leg> {

	// Internal State -------------------------------------------------------

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface -----------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Leg> legs;

		int id = super.getRequest().getPrincipal().getAccountId();

		legs = this.repository.findLegsByManager(id);

		super.getBuffer().addData(legs);
	}

	@Override
	public void unbind(final Leg leg) {
		assert leg != null;

		Dataset dataset;

		dataset = super.unbindObject(leg, "flightNumber", "status", "scheduledDeparture", "draftMode");
		super.getResponse().addData(dataset);
	}
}
