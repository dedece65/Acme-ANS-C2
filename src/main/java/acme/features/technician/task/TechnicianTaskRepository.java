
package acme.features.technician.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecordTask.MaintenanceRecordTask;
import acme.entities.task.Task;

@Repository
public interface TechnicianTaskRepository extends AbstractRepository {

	@Query("select t from Task t where t.id = :id")
	Task findTaskById(int id);

	@Query("select mr from MaintenanceRecord mr where mr.id = :id")
	MaintenanceRecord findMaintenanceRecordById(int id);

	@Query("select mrt.task from MaintenanceRecordTask mrt where mrt.maintenanceRecord.id = :masterId")
	Collection<Task> findTasksByMasterId(int masterId);

	@Query("select mrt.maintenanceRecord from MaintenanceRecordTask mrt where mrt.task.id = :taskId")
	Collection<MaintenanceRecord> findMaintenanceRecordsByTaskId(int taskId);

	@Query("select t from Task t where t.technician.id = :technicianId")
	Collection<Task> findTasksByTechnicianId(int technicianId);

	@Query("select t from Task t where t.draftMode = false")
	Collection<Task> findPublishedTasks();

	@Query("select mrt from MaintenanceRecordTask mrt where mrt.task.id = :taskId")
	Collection<MaintenanceRecordTask> findInvolvesByTaskId(int taskId);

}
