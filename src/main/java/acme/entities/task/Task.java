
package acme.entities.task;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.realms.Technician;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "draftMode"),              // 
	@Index(columnList = "draftMode, technician_id")
})
public class Task extends AbstractEntity {

	//Serialisation version ------------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	//Attributes -----------------------------------------------------------------

	@Mandatory
	@Enumerated(EnumType.STRING)
	@Automapped
	private TaskType			type;

	@Mandatory
	@ValidString(max = 255)
	@Automapped
	private String				description;

	@Mandatory
	@ValidNumber(min = 0, max = 10)
	@Automapped
	private Integer				priority;

	@Mandatory
	@ValidNumber
	@Positive
	private Double				estimatedDuration;

	@Mandatory
	@Automapped
	private boolean				draftMode;
	// Derived attributes --------------------------------------------------------

	// Relationships -------------------------------------------------------------

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Technician			technician;

}
