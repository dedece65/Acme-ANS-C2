
package acme.features.technician.dashboard;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Principal;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.forms.TechnicianDashboard;
import acme.realms.Technician;

@Service
@GuiService
public class TechnicianDashboardShowService extends AbstractGuiService<Technician, TechnicianDashboard> {

	//Internal state ----------------------------------------------------------

	@Autowired
	private TechnicianDashboardRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final TechnicianDashboard dashboard = new TechnicianDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Technician technician = this.repository.findOneTechnicianByUserAccoundId(userAccountId);

		// Number of maintenance records grouped by their status
		//dashboard.setNumberOfRecordsGroupedByStatus(this.repository.countMaintenanceRecordsByTechnicianId(technician.getId()));

		// Maintenance record with the nearest inspection due date
		MaintenanceRecord nearestRecord = this.repository.findNearestInspectionRecordsByTechnicianId(technician.getId()).stream().findFirst().orElse(null);
		Date nearestRecordDate = nearestRecord.getNextInspectionDue();
		dashboard.setNearestInspectionMaintenanceRecord(nearestRecordDate);

		// Top five aircrafts with the highest number of tasks in their maintenance records
		//List<Aircraft> topFiveAircrafts = this.repository.findTopFiveAircraftsByTechnicianId(technician.getId());
		//dashboard.setTopFiveAircrafts(topFiveAircrafts);

		// Statistics on the estimated cost of maintenance records in the last year
		dashboard.setAverageEstimatedCost(this.repository.findAverageEstimatedCost(technician.getId()));
		dashboard.setDeviationEstimatedCost(this.repository.findDeviationEstimatedCost(technician.getId()));
		dashboard.setMinEstimatedCost(this.repository.findMinEstimatedCost(technician.getId()));
		dashboard.setMaxEstimatedCost(this.repository.findMaxEstimatedCost(technician.getId()));

		// Statistics on the estimated duration of tasks in which the technician is involved
		dashboard.setAverageEstimatedDuration(this.repository.findAverageEstimatedDuration(technician.getId()));
		dashboard.setDeviationEstimatedDuration(this.repository.findDeviationEstimatedDuration(technician.getId()));
		dashboard.setMinEstimatedDuration(this.repository.findMinEstimatedDuration(technician.getId()));
		dashboard.setMaxEstimatedDuration(this.repository.findMaxEstimatedDuration(technician.getId()));

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final TechnicianDashboard object) {

		// Convertimos el Map de maintenanceStatus a un SelectChoices
		SelectChoices maintenanceStatusChoices = new SelectChoices();

		if (object.getNumberOfRecordsGroupedByStatus() != null)
			object.getNumberOfRecordsGroupedByStatus().forEach((status, count) -> {
				// Agregamos cada estado con su conteo como una opción al SelectChoices
				boolean isSelected = false; // Establece esta variable según la lógica que necesites
				maintenanceStatusChoices.add(status.toString(), count.toString(), isSelected);
			});

		// Crear el dataset para pasar los datos al JSP
		Dataset dataset = super.unbindObject(object, "nearestInspectionMaintenanceRecord", "topFiveAircrafts", "averageEstimatedCost", "deviationEstimatedCost", "minEstimatedCost", "maxEstimatedCost", "averageEstimatedDuration",
			"deviationEstimatedDuration", "minEstimatedDuration", "maxEstimatedDuration");

		// Insertar las opciones convertidas en el dataset
		dataset.put("maintenanceStatus", maintenanceStatusChoices);

		// Añadir el dataset al response
		super.getResponse().addData(dataset);
	}
}
