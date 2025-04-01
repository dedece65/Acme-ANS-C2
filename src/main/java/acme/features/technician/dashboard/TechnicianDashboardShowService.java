
package acme.features.technician.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Principal;
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
		dashboard.setMaintenanceStatus(this.repository.countMaintenanceRecordsByTechnicianId(technician.getId()));

		// Maintenance record with the nearest inspection due date
		MaintenanceRecord nearestRecord = this.repository.findNearestInspectionRecordByTechnicianId(technician.getId());
		dashboard.setNearestInspectionMaintenanceRecord(nearestRecord);

		// Top five aircrafts with the highest number of tasks in their maintenance records
		//List<Aircraft> topFiveAircrafts = this.repository.findTopFiveAircraftsByTechnicianId(technician.getId());
		//dashboard.setTopFiveAircrafts(topFiveAircrafts);

		// Statistics on the estimated cost of maintenance records in the last year
		//dashboard.setAverageEstimatedCost(this.repository.findAverageEstimatedCost(technician.getId()));
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
		super.getResponse().addData(super.unbindObject(object, "maintenanceStatus", "nearestInspectionMaintenanceRecord", "topFiveAircrafts", "averageEstimatedCost", "deviationEstimatedCost", "minEstimatedCost", "maxEstimatedCost",
			"averageEstimatedDuration", "deviationEstimatedDuration", "minEstimatedDuration", "maxEstimatedDuration"));
	}
}
