
package acme.realms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Optional;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manager extends AbstractRole {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@NotNull
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	private String				idNumber;

	@NotNull
	@Positive
	private Integer				xpYears;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				dateOfBirth;

	@Optional
	private String				linkToPicture;
}
