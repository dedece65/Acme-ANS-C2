
package acme.datatypes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 50)
	private String				tag;

	@NotNull
	private Boolean				requiresSelfTransfer;

	@NotNull
	@Positive
	private Double				cost;

	@Size(max = 255)
	private String				description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Future
	private Date				scheduledDeparture;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Future
	private Date				scheduledArrival;

	@NotBlank
	@NotNull
	private String				originCity;

	@NotBlank
	@NotNull
	private String				destinationCity;

	@NotNull
	@Min(0)
	private Integer				layovers;
}
