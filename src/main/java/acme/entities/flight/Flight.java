
package acme.entities.flight;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidString;
import acme.realms.AirlineManager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString(max = 50)
	private String				tag;

	@Mandatory
	private Boolean				requiresSelfTransfer;

	@Mandatory
	@ValidScore
	private Double				cost;

	@ValidString(max = 255)
	private String				description;

	@Mandatory
	@ValidMoment(past = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				scheduledDeparture;

	@Mandatory
	@ValidMoment(past = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				scheduledArrival;

	@ValidString()
	@Mandatory
	private String				originCity;

	@ValidString()
	@Mandatory
	private String				destinationCity;

	@Mandatory
	@ValidScore
	private Integer				layovers;

	@Mandatory
	@Valid
	@ManyToOne
	private AirlineManager		airlineManager;
}
