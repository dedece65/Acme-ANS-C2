
package acme.features.assistanceAgent.claim;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.claim.ClaimIndicator;
import acme.entities.claim.ClaimType;
import acme.entities.leg.Leg;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimCreateService extends AbstractGuiService<AssistanceAgent, Claim> {

	@Autowired
	private AssistanceAgentClaimRepository repository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Claim claim = new Claim();
		int assistanceAgentId = super.getRequest().getPrincipal().getActiveRealm().getId();
		AssistanceAgent assistanceAgent = this.repository.findAssistanceAgentById(assistanceAgentId);
		Date registrationMoment = MomentHelper.getCurrentMoment();

		claim.setAssistanceAgents(assistanceAgent);
		claim.setDraftMode(true);
		claim.setRegistrationMoment(registrationMoment);
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

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "claimType", "indicator", "legs", "draftMode");

		dataset.put("claimTypes", claimTypes);
		dataset.put("indicators", indicators);
		dataset.put("legs", legsChoices);

		super.getResponse().addData(dataset);
	}
}
