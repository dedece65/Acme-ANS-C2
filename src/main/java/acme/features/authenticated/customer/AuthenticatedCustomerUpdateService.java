
package acme.features.authenticated.customer;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Customer;

@GuiService
public class AuthenticatedCustomerUpdateService extends AbstractGuiService<Authenticated, Customer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedCustomerRepository authenticatedCustomerRepository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Customer object;
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		object = this.authenticatedCustomerRepository.findCustomerByUserAccountId(userAccountId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Customer object) {
		assert object != null;

		super.bindObject(object, "identifier", "phoneNumber", "address", "city", "country", "earnedPoints");
	}

	@Override
	public void validate(final Customer object) {
		assert object != null;
		Customer existing = this.authenticatedCustomerRepository.findCustomerByCustomerIdentifier(object.getIdentifier());
		boolean valid = existing == null || existing.getId() == object.getId();
		super.state(valid, "identifier", "authenticated.customer.form.error.duplicateIdentifier");
	}

	@Override
	public void perform(final Customer modifiedCustomer) {
		assert modifiedCustomer != null;

		Customer newCustomer = (Customer) this.authenticatedCustomerRepository.findById(modifiedCustomer.getId()).get();
		newCustomer.setIdentifier(modifiedCustomer.getIdentifier());
		newCustomer.setPhoneNumber(modifiedCustomer.getPhoneNumber());
		newCustomer.setAddress(modifiedCustomer.getAddress());
		newCustomer.setCity(modifiedCustomer.getCity());
		newCustomer.setCountry(modifiedCustomer.getCountry());

		this.authenticatedCustomerRepository.save(newCustomer);
	}

	@Override
	public void unbind(final Customer object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbindObject(object, "identifier", "phoneNumber", "address", "city", "country", "earnedPoints");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
