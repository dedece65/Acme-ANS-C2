
package acme.datatypes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Leg extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@NotBlank
	@NotNull
    @Pattern(regexp = "[A-Z]{2}\d{4}", message = "Flight number must follow the IATA format (XX0000)")
    private String			flightNumber;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date			scheduledDeparture;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date			scheduledArrival;

	@NotNull
	@Positive
	private Double			durationHours;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LegStatus		status;

	/*
	@NotNull
	@ManyToOne
	private Airport			departureAirport;

	@NotNull
	@ManyToOne
	private Airport			arrivalAirport;

	@NotNull
	@ManyToOne
	private Aircraft		aircraft;
	*/
	
	@ManyToOne
	private Flight			flight;
}
