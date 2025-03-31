
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

	private List<String>		lastFiveDestinations;
	private Money				spentMoneyLastYear;
	private Integer				economyBookings;
	private Integer				businessBookings;
	private Money				bookingCountCost;
	private Money				bookingAverageCost;
	private Money				bookingMinimumCost;
	private Money				bookingMaximumCost;
	private Money				bookingDeviationCost;
	private Integer				bookingCountPassengers;
	private Double				bookingAveragePassengers;
	private Integer				bookingMinimumPassengers;
	private Integer				bookingMaximumPassengers;
	private Double				bookingDeviationPassengers;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
