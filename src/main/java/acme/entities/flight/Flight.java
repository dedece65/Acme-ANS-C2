
package acme.entities.flight;

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
import acme.client.helpers.SpringHelper;
import acme.features.authenticated.manager.leg.ManagerLegRepository;
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
		ManagerLegRepository repository = SpringHelper.getBean(ManagerLegRepository.class);
		result = repository.findDepartureByFlightId(this.getId());

		return result;
	}

	@Transient
	public Date getScheduledArrival() {
		Date result;
		ManagerLegRepository repository = SpringHelper.getBean(ManagerLegRepository.class);
		result = repository.findArrivalByFlightId(this.getId());

		return result;
	}

	@Transient
	public String getOriginCity() {
		String result;
		ManagerLegRepository repository = SpringHelper.getBean(ManagerLegRepository.class);
		if (repository.findDestinationCityByFlightId(this.getId()).size() == 0)
			return "NA";

		result = repository.findOriginCityByFlightId(this.getId()).getFirst();

		return result;
	}

	@Transient
	public String getDestinationCity() {
		String result;
		ManagerLegRepository repository = SpringHelper.getBean(ManagerLegRepository.class);
		if (repository.findDestinationCityByFlightId(this.getId()).size() == 0)
			return "NA";

		result = repository.findDestinationCityByFlightId(this.getId()).getFirst();

		return result;
	}

	@Transient
	public Integer getLayovers() {
		Integer result;
		ManagerLegRepository repository = SpringHelper.getBean(ManagerLegRepository.class);
		result = repository.numberOfLayovers(this.getId());

		return result;
	}
}
