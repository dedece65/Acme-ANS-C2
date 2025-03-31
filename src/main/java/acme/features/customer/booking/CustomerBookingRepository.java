
package acme.features.customer.booking;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.booking.Booking;
import acme.entities.booking.Passenger;
import acme.entities.flight.Flight;
import acme.realms.Customer;

@Repository
public interface CustomerBookingRepository extends AbstractRepository {

	@Query("select b from Booking b where b.id = :id")
	Booking findBookingById(int id);

	@Query("select b from Booking b where b.customer.id = :customerId")
	Collection<Booking> findBookingsByCustomerId(int customerId);

	@Query("select b.passenger from BookingRecord b where b.booking.id = :bookingId")
	Collection<Passenger> findPassengersByBooking(int bookingId);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("select c from Customer c where c.userAccount.id = :id")
	Customer findConsumerByUserAccountId(int id);

	@Query("select fl from Flight fl where fl.draftMode = false")
	Collection<Flight> findAllPublishedFlights();

	@Query("SELECT c FROM Customer c WHERE c.id = :customerId")
	Customer findCustomerById(Integer customerId);

	@Query("SELECT b FROM Booking b")
	List<Booking> findAllBooking();

	@Query("SELECT c FROM Customer c WHERE c.userAccount.id = :userAccountId")
	Customer findCustomerByUserAccountId(Integer userAccountId);

	@Query("SELECT b FROM Booking b WHERE b.locatorCode = :locatorCode")
	Booking findBookingByLocatorCode(String locatorCode);

	@Query("SELECT b FROM Booking b WHERE b.customer.id = :customerId")
	Collection<Booking> findBookingsByCustomer(Integer customerId);

	@Query("SELECT f FROM Flight f WHERE f.id = :flightId")
	Flight findFlightById(Integer flightId);

}
