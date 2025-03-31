
package acme.entities.maintenanceRecordTask;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.task.Task;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MaintenanceRecordTask extends AbstractEntity {

	//Serialisation version ------------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	//Attributes -----------------------------------------------------------------

	// Derived attributes --------------------------------------------------------

	// Relationships -------------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne
	private Task				task;

	@Mandatory
	@Valid
	@ManyToOne
	private MaintenanceRecord	maintenanceRecord;
}
