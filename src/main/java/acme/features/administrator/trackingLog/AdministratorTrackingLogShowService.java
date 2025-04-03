
package acme.features.administrator.trackingLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.trackingLog.IndicatorType;
import acme.entities.trackingLog.TrackingLog;

@GuiService
public class AdministratorTrackingLogShowService extends AbstractGuiService<Administrator, TrackingLog> {

	// Internal State --------------------------------------------------------------------

	@Autowired
	private AdministratorTrackingLogRepository repository;

	// AbstractGuiService ----------------------------------------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrackingLog trackingLog;
		int trackingLogId;

		// Obtener el ID del tracking log y cargarlo
		trackingLogId = super.getRequest().getData("id", int.class);
		trackingLog = this.repository.findTrackingLogById(trackingLogId);

		// Agregar el tracking log al buffer para la vista
		super.getBuffer().addData(trackingLog);
	}

	@Override
	public void unbind(final TrackingLog trackingLog) {
		Dataset dataset;
		SelectChoices indicators;

		// Obtener los tipos de indicadores disponibles
		indicators = SelectChoices.from(IndicatorType.class, trackingLog.getIndicator());

		// Crear el dataset con la información del tracking log
		dataset = super.unbindObject(trackingLog, "updateMoment", "step", "resolutionPercentage", "indicator", "resolutionReason", "draftMode");

		// Agregar los SelectChoices de indicadores al dataset
		dataset.put("indicators", indicators);

		// Añadir el dataset a la respuesta
		super.getResponse().addData(dataset);
	}
}
