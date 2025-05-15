
package acme.features.technician.maintenanceRecordTask;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecordTask.MaintenanceRecordTask;
import acme.entities.task.Task;
import acme.realms.Technician;

@GuiService
public class TechnicianMaintenanceRecordTaskCreateService extends AbstractGuiService<Technician, MaintenanceRecordTask> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianMaintenanceRecordTaskRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		int maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);
		MaintenanceRecord maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

		if (maintenanceRecord != null) {
			int technicianId = super.getRequest().getPrincipal().getActiveRealm().getId();
			boolean isAuthorised = technicianId == maintenanceRecord.getTechnician().getId();

			super.getResponse().setAuthorised(isAuthorised);
		} else
			super.getResponse().setAuthorised(false);
	}

	@Override
	public void load() {
		int maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);
		MaintenanceRecord maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

		MaintenanceRecordTask involves = new MaintenanceRecordTask();
		involves.setMaintenanceRecord(maintenanceRecord);

		super.getBuffer().addData(involves);
	}

	@Override
	public void bind(final MaintenanceRecordTask maintenanceRecordTask) {
		super.bindObject(maintenanceRecordTask, "task");
	}

	@Override
	public void validate(final MaintenanceRecordTask maintenanceRecordTask) {

	}

	@Override
	public void perform(final MaintenanceRecordTask maintenanceRecordTask) {
		this.repository.save(maintenanceRecordTask);
	}

	@Override
	public void unbind(final MaintenanceRecordTask maintenanceRecordTask) {
		SelectChoices task;
		Dataset dataset;

		int technicianId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);

		Collection<Task> taskOnMaintenanceRecord = this.repository.findTaskOfMaintenanceRecord(maintenanceRecordId);

		Collection<Task> technicianTasks = this.repository.findAllTaskByTechnicianId(technicianId).stream().filter(t -> !taskOnMaintenanceRecord.contains(t)).toList();

		Collection<Task> publishedTasks = this.repository.findAllPublishedTasks();

		Set<Task> combinedTasks = new HashSet<>();
		combinedTasks.addAll(technicianTasks);
		combinedTasks.addAll(publishedTasks);

		Collection<Task> tasks = combinedTasks.stream().filter(t -> !taskOnMaintenanceRecord.contains(t)).toList();

		task = SelectChoices.from(tasks, "id", maintenanceRecordTask.getTask());

		dataset = super.unbindObject(maintenanceRecordTask, "maintenanceRecord", "task");

		dataset.put("task", task);
		dataset.put("maintenanceRecordId", maintenanceRecordId);

		super.getResponse().addData(dataset);
	}

}
