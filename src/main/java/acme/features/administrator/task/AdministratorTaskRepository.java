
package acme.features.administrator.task;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.task.Task;

@Repository
public interface AdministratorTaskRepository extends AbstractRepository {

	@Query("select mr from MaintenanceRecord mr where mr.id = :id")
	MaintenanceRecord findMaintenanceRecordById(int id);

	@Query("SELECT mrt.task FROM MaintenanceRecordTask mrt JOIN mrt.task t WHERE mrt.maintenanceRecord.id = :masterId AND t.draftMode = false")
	Collection<Task> findPublishedTasksByMasterId(Integer masterId);

}
