
package acme.features.assistanceAgent.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Principal;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.ClaimIndicator;
import acme.entities.claim.ClaimType;
import acme.entities.leg.Leg;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimShowService extends AbstractGuiService<AssistanceAgent, Claim> {

	// Internal State --------------------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	// AbstractGuiService ----------------------------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int currentAssistanceAgentId;
		int claimId;
		Claim selectedClaim;
		Principal principal;

		principal = super.getRequest().getPrincipal();

		currentAssistanceAgentId = principal.getActiveRealm().getId();
		claimId = super.getRequest().getData("id", int.class);
		selectedClaim = this.repository.findClaimById(claimId);

		status = principal.hasRealmOfType(AssistanceAgent.class) && selectedClaim.getAssistanceAgents().getId() == currentAssistanceAgentId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim claim;
		int claimId;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(claimId);

		super.getBuffer().addData(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;
		SelectChoices claimTypes;
		SelectChoices indicators;
		SelectChoices legsChoices;

		// Obtener todas las Legs disponibles sin importar si están publicadas o no
		Collection<Leg> allLegs = this.repository.findAllLegs();

		// Obtener la Leg actual del Claim
		Leg selectedLeg = claim.getLegs();

		// Crear SelectChoices con todas las Legs, marcando la actual como seleccionada
		legsChoices = SelectChoices.from(allLegs, "flightNumber", selectedLeg);

		// Crear SelectChoices para claimTypes e indicators
		claimTypes = SelectChoices.from(ClaimType.class, claim.getClaimType());
		indicators = SelectChoices.from(ClaimIndicator.class, claim.getIndicator());

		// Crear dataset con la información relevante del Claim
		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "claimType", "indicator", "legs", "draftMode");

		// Agregar opciones al dataset
		dataset.put("claimTypes", claimTypes);
		dataset.put("indicators", indicators);
		dataset.put("legs", legsChoices);
		dataset.put("leg", legsChoices.getSelected().getKey());

		// Añadir dataset a la respuesta
		super.getResponse().addData(dataset);
	}
}
