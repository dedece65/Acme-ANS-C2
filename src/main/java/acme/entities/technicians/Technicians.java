
package acme.entities.technicians;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Technicians extends AbstractEntity {

	//Serialisation version ------------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	//Attributes -----------------------------------------------------------------
	@Mandatory
	@Pattern(regexp = "^[A-Z]{2,3}\\d{6}$")
	@Column(unique = true)
	private String				licenseNumber;

	@Mandatory
	@Pattern(regexp = "^[A-Z]{2,3}\\d{6}$")
	private String				phoneNumber;

	@Mandatory
	@Size(max = 50)
	@Automapped
	private String				specialisation;

	@Mandatory
	private boolean				passedHealthTest;

	@Mandatory
	@ValidNumber
	@Automapped
	private int					yearsOfExperience;

	@Optional
	@Size(max = 255)
	@Automapped
	private String				certifications;

	// Derived attributes --------------------------------------------------------

	// Relationships -------------------------------------------------------------
}
