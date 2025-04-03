
package acme.features.assistanceAgent.claim;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimListUndergoingService extends AbstractGuiService<AssistanceAgent, Claim> {

	@Autowired
	private AssistanceAgentClaimRepository repository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Claim> claims;
		int assistanceAgentId = super.getRequest().getPrincipal().getActiveRealm().getId();

		// Recuperar las reclamaciones en curso (con indicadores distintos de aceptado/rechazado)
		claims = this.repository.findUndergoingClaimsByAssistanceAgentId(assistanceAgentId);
		super.getBuffer().addData(claims);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "claimType", "indicator", "legs");

		if (claim.isDraftMode()) {
			final Locale locale = super.getRequest().getLocale();
			dataset.put("draftMode", locale.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}
}
