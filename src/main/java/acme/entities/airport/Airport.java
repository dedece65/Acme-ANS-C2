
package acme.entities.airport;

import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Airport extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidString(max = 50)
	private String				name;

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}$")
	private String				iataCode;

	@Mandatory
	@Valid
	private OperationalScope	operationalScope;

	@Mandatory
	@ValidString(max = 50)
	private String				city;

	@Mandatory
	@ValidString(max = 50)
	private String				country;

	@Optional
	@ValidUrl
	private String				website;

	@Optional
	@ValidEmail
	private String				email;

	@Optional
	@ValidString()
	private String				address;

	@Optional
	@ValidString(pattern = "^\\+?\\d{6,15}$")
	private String				phoneNumber;
	/*
	 * It is assumed that airports have only two runways,
	 * one for landing and one for take-offs;
	 * no further information about them is needed in the system.
	 * 
	 * @Mandatory
	 * 
	 * @Valid
	 * 
	 * @OneToMany
	 * private Aircraft aircraft;
	 * 
	 * @Mandatory
	 * 
	 * @Valid
	 * 
	 * @OneToMany
	 * private Airline Airline;
	 */
}
