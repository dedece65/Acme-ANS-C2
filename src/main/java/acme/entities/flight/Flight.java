
package acme.entities.flight;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;
import acme.realms.Manager;
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
	@Automapped
	private String				tag;

	@Mandatory
	@Valid
	@Automapped
	private Boolean				requiresSelfTransfer;

	@Mandatory
	@ValidMoney(min = 0)
	@Automapped
	private Money				cost;

	@Mandatory
	@ValidString(max = 255)
	@Automapped
	private String				description;

	@Mandatory
	@Valid
	@ManyToOne(optional = true)
	private Manager				manager;


	@Transient
	private Date getScheduledDeparture() {
		return Date.from(Instant.now());
	}

	@Transient
	private Date getScheduledArrival() {
		return Date.from(Instant.now());
	}

	@Transient
	private String getOriginCity() {
		return "";
	}

	@Transient
	private String getDestinationCity() {
		return "";
	}

	@Transient
	private Integer getLayovers() {
		return 1;
	}
}
