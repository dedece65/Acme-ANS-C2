
package acme.entities.customer;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidIdentifier;
import acme.constraints.ValidLongText;
import acme.constraints.ValidShortText;
import acme.datatypes.Phone;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidIdentifier
	@Column(unique = true)
	private String				identifier;

	@Mandatory
	@Automapped
	private Phone				phoneNumber;

	@Mandatory
	@ValidLongText
	@Automapped
	private String				address;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				city;

	@Mandatory
	@ValidShortText
	@Automapped
	private String				country;

	@Optional
	@ValidNumber(min = 0, max = 500000)
	@Automapped
	private int					earnedPoints;

}
