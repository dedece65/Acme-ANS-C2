
package acme.features.administrator.maintenanceRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.controllers.GuiController;
import acme.client.services.AbstractGuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecord.MaintenanceStatus;

@GuiController
public class AdministratorMaintenanceRecordShowService extends AbstractGuiService<Administrator, MaintenanceRecord> {

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
		MaintenanceRecord maintenanceRecord;
		int id;

		id = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findPublishedMaintenanceRecordById(id);

		super.getBuffer().addData(maintenanceRecord);

	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {

		SelectChoices choices;
		SelectChoices aircraft;
		Collection<Aircraft> aircrafts;

		Dataset dataset;
		aircrafts = this.repository.findAllAircrafts();
		choices = SelectChoices.from(MaintenanceStatus.class, maintenanceRecord.getStatus());
		aircraft = SelectChoices.from(aircrafts, "id", maintenanceRecord.getAircraft());

		dataset = super.unbindObject(maintenanceRecord, "maintenanceMoment", "status", "nextInspectionDue", "estimatedCost", "notes", "aircraft");
		dataset.put("status", choices);
		dataset.put("aircraft", aircraft);

		super.getResponse().addData(dataset);
	}

}
