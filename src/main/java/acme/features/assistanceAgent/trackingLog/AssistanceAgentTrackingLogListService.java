
package acme.features.assistanceAgent.trackingLog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Principal;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claim.Claim;
import acme.entities.trackingLog.TrackingLog;
import acme.realms.AssistanceAgent;

@GuiService
public class AssistanceAgentTrackingLogListService extends AbstractGuiService<AssistanceAgent, TrackingLog> {

	@Autowired
	private AssistanceAgentTrackingLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int claimId;
		int currentAssistanceAgentId;
		Claim claim;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		currentAssistanceAgentId = principal.getActiveRealm().getId();
		claimId = super.getRequest().getData("claimId", int.class);
		claim = this.repository.findClaimById(claimId);
		status = principal.hasRealmOfType(AssistanceAgent.class) && claim.getAssistanceAgents().getId() == currentAssistanceAgentId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrackingLog> trackingLogs;
		int claimId = super.getRequest().getData("claimId", int.class);
		trackingLogs = this.repository.findTrackingLogsByClaimId(claimId);
		super.getBuffer().addData(trackingLogs);
	}

	@Override
	public void unbind(final TrackingLog trackingLog) {
		Dataset dataset = super.unbindObject(trackingLog, "updateMoment", "step", "resolutionPercentage", "indicator", "resolutionReason");

		if (trackingLog.isDraftMode()) {
			final Locale locale = super.getRequest().getLocale();
			dataset.put("draftMode", locale.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}
}
