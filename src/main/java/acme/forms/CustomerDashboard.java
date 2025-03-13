
package acme.forms;

import java.util.List;

import acme.client.components.basis.AbstractForm;
import acme.client.components.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private List<String>		last5Destinations;
	private Money				moneySpentLastYear;
	private Integer				economyBookings;
	private Integer				businessBookings;
	private Money				countBookingCost;
	private Money				averageBookingCost;
	private Money				minimumBookingCost;
	private Money				maximumBookingCost;
	private Money				standardDeviationBookingCost;
	private Integer				countNumberPassengers;
	private Double				averageNumberPassengers;
	private Integer				minimumNumberPassengers;
	private Integer				maximumNumberPassengers;
	private Double				standardDeviationNumberPassengers;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
