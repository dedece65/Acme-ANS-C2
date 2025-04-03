
package acme.entities.claim;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidClaim;
import acme.entities.leg.Leg;
import acme.realms.AssistanceAgent;
import lombok.Getter;
import lombok.Setter;

@ValidClaim
@Entity
@Getter
@Setter
public class Claim extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString(max = 255)
	@Automapped
	private String				description;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@Mandatory
	@ValidEmail
	@Automapped
	private String				passengerEmail;

	@Mandatory
	@Valid
	@Automapped
	private ClaimType			claimType;

	@Mandatory
	@Automapped
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Automapped
	private ClaimIndicator			indicator; //Se ha decidido que esta operación se hara en el nivel de servicio, pero depende del inidicador de TrackingLogs

	// Relationships ----------------------------------------------------------

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private AssistanceAgent		assistanceAgents;

	@Mandatory
	@ManyToOne(optional = false)
	@Valid
	private Leg					legs;

}
