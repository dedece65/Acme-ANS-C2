
package acme.features.assistanceAgent.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.ClaimIndicator;
import acme.entities.claim.ClaimType;
import acme.entities.leg.Leg;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimUpdateService extends AbstractGuiService<AssistanceAgent, Claim> {

	@Autowired
	private AssistanceAgentClaimRepository repository;


	@Override
	public void authorise() {
		boolean exist;
		Claim claim;
		AssistanceAgent assistanceAgent;
		int id;

		id = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(id);

		exist = claim != null;
		if (exist) {
			assistanceAgent = (AssistanceAgent) super.getRequest().getPrincipal().getActiveRealm();
			if (assistanceAgent.equals(claim.getAssistanceAgents()))
				super.getResponse().setAuthorised(true);
		}
	}

	@Override
	public void load() {
		Claim claim;
		int id;

		id = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(id);

		super.getBuffer().addData(claim);
	}

	@Override
	public void bind(final Claim claim) {
		super.bindObject(claim, "passengerEmail", "description", "claimType", "indicator", "legs");
	}

	@Override
	public void validate(final Claim claim) {
		if (!super.getBuffer().getErrors().hasErrors("passengerEmail"))
			super.state(claim.getPassengerEmail() != null, "passengerEmail", "assistance-agent.claim.form.error.emptyEmail", claim);

		if (!super.getBuffer().getErrors().hasErrors("claimType"))
			super.state(claim.getClaimType() != null, "claimType", "assistance-agent.claim.form.error.emptyClaimType", claim);

		if (!super.getBuffer().getErrors().hasErrors("indicator"))
			super.state(claim.getIndicator() != null, "indicator", "assistance-agent.claim.form.error.emptyIndicator", claim);

		if (!super.getBuffer().getErrors().hasErrors("leg"))
			super.state(claim.getLegs() != null, "leg", "assistance-agent.claim.form.error.emptyLeg", claim);
	}

	@Override
	public void perform(final Claim claim) {
		assert claim != null;
		this.repository.save(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		SelectChoices claimTypes, indicators, legsChoices;
		Collection<Leg> legs;
		Dataset dataset;

		legs = this.repository.findAllLegs();
		claimTypes = SelectChoices.from(ClaimType.class, claim.getClaimType());
		indicators = SelectChoices.from(ClaimIndicator.class, claim.getIndicator());
		legsChoices = SelectChoices.from(legs, "flightNumber", claim.getLegs());

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "claimType", "indicator", "legs");

		dataset.put("claimTypes", claimTypes);
		dataset.put("indicators", indicators);
		dataset.put("legs", legsChoices);

		super.getResponse().addData(dataset);
	}
}
