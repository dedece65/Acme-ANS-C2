
package acme.features.administrator.airport;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.airport.Airport;
import acme.entities.airport.OperationalScope;

@GuiService
public class AdministratorAirportUpdateService extends AbstractGuiService<Administrator, Airport> {

	// Internal State -------------------------------------------------------

	@Autowired
	private AdministratorAirportRepository repository;

	// AbstractGuiService interface -----------------------------------------


	@Override
	public void authorise() {
		boolean exist;
		Optional<Airport> airport;
		int id;

		id = super.getRequest().getData("id", int.class);
		airport = this.repository.findAirportById(id);
		exist = airport.isPresent();
		super.getResponse().setAuthorised(exist);
	}

	@Override
	public void load() {
		Airport airport;
		int id;

		id = super.getRequest().getData("id", int.class);

		airport = this.repository.findAirportById(id).get();

		super.getBuffer().addData(airport);
	}

	@Override
	public void bind(final Airport airport) {
		assert airport != null;

		super.bindObject(airport, "name", "iataCode", "operationalScope", "city", "country", "website", "email", "address", "phoneNumber");
	}

	@Override
	public void validate(final Airport airport) {
		assert airport != null;

		if (!this.getBuffer().getErrors().hasErrors("name") && airport.getName() != null)
			super.state(airport.getName().length() <= 50, "name", "administrator.airport.form.error.name", airport);

		if (!this.getBuffer().getErrors().hasErrors("iataCode") && airport.getIataCode() != null) {
			Airport airportdup;

			airportdup = this.repository.findAirportByIataCode(airport.getIataCode()).get();
			super.state(airportdup == null, "iataCode", "administrator.airport.form.error.duplicated");
		}

		if (!this.getBuffer().getErrors().hasErrors("operationalScope"))
			super.state(airport.getOperationalScope() != null, "operationalScope", "administrator.airport.form.error.noOperationalScope", airport);

		if (!this.getBuffer().getErrors().hasErrors("city") && airport.getName() != null)
			super.state(airport.getCity().length() <= 50, "city", "administrator.airport.form.error.city", airport);

		if (!this.getBuffer().getErrors().hasErrors("country") && airport.getName() != null)
			super.state(airport.getCountry().length() <= 50, "country", "administrator.airport.form.error.country", airport);

		if (!this.getBuffer().getErrors().hasErrors("website") && airport.getName() != null)
			super.state(airport.getWebsite().length() <= 255, "website", "administrator.airport.form.error.website", airport);

		if (!this.getBuffer().getErrors().hasErrors("address") && airport.getName() != null)
			super.state(airport.getAddress().length() <= 255, "address", "administrator.airport.form.error.address", airport);

		if (!this.getBuffer().getErrors().hasErrors("email") && airport.getName() != null)
			super.state(airport.getEmail().length() <= 255, "email", "administrator.airport.form.error.email", airport);

		if (!this.getBuffer().getErrors().hasErrors("phoneNumber") && airport.getName() != null)
			super.state(airport.getPhoneNumber().length() <= 15, "phoneNumber", "administrator.airport.form.error.phoneNumber", airport);

	}

	@Override
	public void perform(final Airport airport) {
		assert airport != null;

		this.repository.save(airport);
	}

	@Override
	public void unbind(final Airport airport) {
		assert airport != null;

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(OperationalScope.class, airport.getOperationalScope());
		dataset = super.unbindObject(airport, "name", "iataCode", "city", "country", "website", "email", "address", "phoneNumber");
		dataset.put("operationalScope", choices.getSelected().getKey());
		dataset.put("operationalScopes", choices);

		super.getResponse().addData(dataset);
	}
}
