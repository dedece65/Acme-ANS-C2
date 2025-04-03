
package acme.features.administrator.maintenanceRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.controllers.GuiController;
import acme.client.services.AbstractGuiService;
import acme.entities.maintenanceRecord.MaintenanceRecord;

@GuiController
public class AdministratorMaintenanceRecordListService extends AbstractGuiService<Administrator, MaintenanceRecord> {

	//Internal state ---------------------------------------------

	@Autowired
	private AdministratorMaintenanceRecordRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<MaintenanceRecord> maintenanceRecord;

		maintenanceRecord = this.repository.findPublishedMaintenanceRecords();

		super.getBuffer().addData(maintenanceRecord);
	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {
		Dataset dataset;

		dataset = super.unbindObject(maintenanceRecord, "maintenanceMoment", "status", "nextInspectionDue");

		super.addPayload(dataset, maintenanceRecord, "maintenanceMoment");

		super.getResponse().addData(dataset);
	}

}
