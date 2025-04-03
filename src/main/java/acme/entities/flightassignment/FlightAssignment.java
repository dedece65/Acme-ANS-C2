
package acme.entities.flightassignment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.entities.leg.Leg;
import acme.realms.FlightCrewMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FlightAssignment extends AbstractEntity {

	// Serialisation --------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -----------------------------------------------------------

	@Mandatory
	@Valid
	@Automapped
	private Duty				duty;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				lastUpdate;

	@Mandatory
	@Valid
	@Automapped
	private AssignmentStatus	status;

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String				remarks;

	@Mandatory
	@Valid
	@Automapped
	private Boolean				draftMode;

	// Derived attributes ---------------------------------------------------

	// Relationships --------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private FlightCrewMember	crewMember;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Leg					leg;
}
