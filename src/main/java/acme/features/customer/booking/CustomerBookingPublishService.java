
package acme.features.customer.booking;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.Passenger;
import acme.entities.booking.TravelClass;
import acme.entities.flight.Flight;
import acme.features.customer.passenger.CustomerPassengerRepository;
import acme.realms.Customer;

@GuiService
public class CustomerBookingPublishService extends AbstractGuiService<Customer, Booking> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerBookingRepository	customerBookingRepository;

	@Autowired
	private CustomerPassengerRepository	customerPassengerRepository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

		Integer bookingId = super.getRequest().getData("id", int.class);
		Booking booking = this.customerBookingRepository.findBookingById(bookingId);

		Integer customerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = status && booking.getCustomer().getId() == customerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Integer id = super.getRequest().getData("id", int.class);
		Booking booking = this.customerBookingRepository.findBookingById(id);
		super.getBuffer().addData(booking);
	}

	@Override
	public void bind(final Booking booking) {
		super.bindObject(booking, "flight", "locatorCode", "travelClass", "lastNibble");
	}

	@Override
	public void validate(final Booking booking) {
		boolean status1 = !booking.getLastNibble().isBlank();
		super.state(status1, "lastNibble", "customer.booking.form.error.lastNibble");
		List<Passenger> passengers = this.customerPassengerRepository.findPassengerByBookingId(booking.getId());
		boolean status2 = !passengers.isEmpty();
		super.state(status2, "*", "customer.booking.form.error.passengers");
		List<Passenger> passenger2 = this.customerPassengerRepository.findPassengerByBookingId(booking.getId());
		Boolean passengersNotPublished = passenger2.stream().anyMatch(p -> !p.getPublished());
		boolean status3 = !passengersNotPublished;
		super.state(status3, "*", "customer.booking.form.error.passengers2");
		Collection<Booking> bookings = this.customerBookingRepository.findBookingsByLocatorCode(booking.getLocatorCode());
		boolean isUnique;
		isUnique = bookings.isEmpty() || bookings.stream().allMatch(b -> b.getId() == booking.getId());
		super.state(isUnique, "locatorCode", "customer.booking.form.error.locatorCode");
	}

	@Override
	public void perform(final Booking booking) {
		booking.setPublished(true);
		this.customerBookingRepository.save(booking);
	}

	@Override
	public void unbind(final Booking booking) {
		SelectChoices travelClasses = SelectChoices.from(TravelClass.class, booking.getTravelClass());
		Collection<Flight> flights = this.customerBookingRepository.findAllPublishedFlights();
		SelectChoices flightChoices = SelectChoices.from(flights, "flightSummary", booking.getFlight());

		Boolean hasPassengers;
		hasPassengers = !this.customerPassengerRepository.findPassengerByBookingId(booking.getId()).isEmpty();
		super.getResponse().addGlobal("hasPassengers", hasPassengers);

		Dataset dataset = super.unbindObject(booking, "flight", "customer", "locatorCode", "purchaseMoment", "travelClass", "price", "lastNibble", "published", "id");
		dataset.put("travelClass", travelClasses);
		dataset.put("flights", flightChoices);

		super.getResponse().addData(dataset);
	}

}
