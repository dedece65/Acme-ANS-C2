
package acme.entities.task;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.technician.Technician;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends AbstractEntity {

	//Serialisation version ------------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	//Attributes -----------------------------------------------------------------

	@Mandatory
	@Enumerated(EnumType.STRING)
	private TaskType			type;

	@Mandatory
	@ValidString(max = 255)
	private String				description;

	@Mandatory
	@ValidNumber(min = 0, max = 10)
	private int					priority;

	@Mandatory
	@ValidNumber
	@Positive
	private double				estimatedDuration;

	// Derived attributes --------------------------------------------------------

	// Relationships -------------------------------------------------------------

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Technician			technician;

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private MaintenanceRecord	maintenanceRecord;
}
