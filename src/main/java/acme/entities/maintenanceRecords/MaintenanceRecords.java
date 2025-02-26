
package acme.entities.maintenanceRecords;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MaintenanceRecords extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotNull
	@Mandatory
	@Temporal(TemporalType.TIMESTAMP)
	private Date				maintenanceMoment;

	@NotNull
	@Mandatory
	@Enumerated(EnumType.STRING)
	private MaintenanceStatus	status;

	@NotNull
	@Mandatory
	@Future
	@Temporal(TemporalType.DATE)
	private Date				nextInspectionDue;

	@NotNull
	@Mandatory
	@Positive
	@ValidScore
	private double				estimatedCost;

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String				notes;

}
