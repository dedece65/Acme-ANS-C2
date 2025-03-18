
package acme.features.authenticated.manager.flight;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.realms.Manager;

@GuiService
public class ManagerFlightDeleteService extends AbstractGuiService<Manager, Flight> {

	@Autowired
	private ManagerFlightRepository repository;


	@Override
	public void authorise() {
		boolean exist;
		boolean ownsFlight;
		Flight flight;
		int id;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		exist = flight != null;
		ownsFlight = flight.getManager().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		if (!(exist && ownsFlight))
			exist = false;
		if (!flight.getDraftMode() || flight.getDraftMode() == null)
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
	public void bind(final Flight object) {
		assert object != null;
		super.bindObject(object, "id");
	}

	@Override
	public void validate(final Flight object) {
		assert object != null;
	}

	@Override
	public void perform(final Flight object) {
		assert object != null;
		this.repository.delete(object);
	}
}
