
package acme.features.administrator.trackingLog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.trackingLog.TrackingLog;

@GuiService
public class AdministratorTrackingLogListService extends AbstractGuiService<Administrator, TrackingLog> {

	@Autowired
	private AdministratorTrackingLogRepository repository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Administrator.class);
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
