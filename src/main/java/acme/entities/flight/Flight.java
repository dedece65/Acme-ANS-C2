
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
	@Automapped
	private Boolean				draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = true)
	private Manager				manager;


	@Transient
	public Date getScheduledDeparture() {
		Date result;
		result = Date.from(Instant.now());

		//ManagerLegRepository legRepository;
		//legRepository = SpringHelper.getBean(ManagerLegRepository.class);

		return result;
	}

	@Transient
	public Date getScheduledArrival() {
		return Date.from(Instant.now());
	}

	@Transient
	public String getOriginCity() {
		return "";
	}

	@Transient
	public String getDestinationCity() {
		return "";
	}

	@Transient
	public Integer getLayovers() {
		return 1;
	}
}
