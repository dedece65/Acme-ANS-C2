
package acme.features.administrator.airport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.airport.Airport;

@GuiService
public class AdministratorAirportListService extends AbstractGuiService<Administrator, Airport> {

	// Internal State -------------------------------------------------------

	@Autowired
	private AdministratorAirportRepository repository;

	// AbstractGuiService interface -----------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Airport> airport;

		airport = this.repository.findAllAirports();

		super.getBuffer().addData(airport);
	}

	@Override
	public void unbind(final Airport airport) {
		assert airport != null;

		Dataset dataset;

		dataset = super.unbindObject(airport, "name", "phoneNumber", "iataCode");
		super.getResponse().addData(dataset);
	}
}
