
package acme.features.administrator.task;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.controllers.GuiController;
import acme.client.services.AbstractGuiService;
import acme.entities.task.Task;
import acme.entities.task.TaskType;

@GuiController
public class AdministratorTaskShowService extends AbstractGuiService<Administrator, Task> {

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
		Task task;
		int id;

		id = super.getRequest().getData("id", int.class);
		task = this.repository.findTaskById(id);

		super.getBuffer().addData(task);
	}

	@Override
	public void unbind(final Task task) {

		SelectChoices types;

		Dataset dataset;
		types = SelectChoices.from(TaskType.class, task.getType());

		dataset = super.unbindObject(task, "type", "description", "priority", "estimatedDuration");
		dataset.put("type", types);

		super.getResponse().addData(dataset);
	}

}
