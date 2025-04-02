
package acme.features.customer.passenger;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.services.GuiService;
import acme.entities.booking.Passenger;
import acme.realms.Customer;

@GuiService
public class CustomerPassengerDeleteService extends AbstractService<Customer, Passenger> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerPassengerRepository customerPassengerRepository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

		Integer passengerId = super.getRequest().getData("id", int.class);
		Passenger passenger = this.customerPassengerRepository.getPassengerById(passengerId);

		Integer customerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = status && passenger.getCustomer().getId() == customerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		//		int passengerId;
		//		passengerId = super.getRequest().getData("id", int.class);
		//
		//		Passenger passenger = this.customerPassengerRepository.findPassengerById(passengerId);
		//
		//		super.getBuffer().addData(passenger);
	}

	@Override
	public void bind(final Passenger passenger) {
		super.bindObject(passenger, "id");

	}

	@Override
	public void validate(final Passenger passenger) {

	}

	@Override
	public void perform(final Passenger passenger) {
		passenger.setPublished(false);
		this.customerPassengerRepository.delete(passenger);
	}

	@Override
	public void unbind(final Passenger passenger) {
		assert passenger != null;

		Dataset dataset = super.unbindObject(passenger, "fullName", "email", "passportNumber", "dateOfBirth", "specialNeeds", "published");

		super.getResponse().addData(dataset);
	}

}
