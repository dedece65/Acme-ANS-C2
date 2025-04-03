
package acme.features.administrator.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.controllers.GuiController;
import acme.client.services.AbstractGuiService;
import acme.entities.task.Task;

@GuiController
public class AdministratorTaskListService extends AbstractGuiService<Administrator, Task> {

	//Internal state ---------------------------------------------

	@Autowired
	private AdministratorTaskRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Collection<Task> tasks;
		Integer maintenanceRecordId;

		maintenanceRecordId = super.getRequest().hasData("maintenanceRecordId") ? super.getRequest().getData("maintenanceRecordId", int.class) : null;

		tasks = this.repository.findPublishedTasksByMasterId(maintenanceRecordId);

		super.getBuffer().addData(tasks);

	}

	@Override
	public void unbind(final Task task) {
		Dataset dataset;

		dataset = super.unbindObject(task, "type", "description", "priority");
		super.addPayload(dataset, task, "estimatedDuration");

		super.getResponse().addData(dataset);
	}

}
