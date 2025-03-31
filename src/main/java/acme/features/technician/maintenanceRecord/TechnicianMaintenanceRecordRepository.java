
package acme.features.technician.maintenanceRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecordTask.MaintenanceRecordTask;
import acme.entities.task.Task;

@Repository
public interface TechnicianMaintenanceRecordRepository extends AbstractRepository {

	@Query("SELECT m FROM MaintenanceRecord m")
	Collection<MaintenanceRecord> findAllMaintenanceRecords();

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.technician.id = :technicianId ")
	Collection<MaintenanceRecord> findAllMaintenanceRecordByTechnicianId(final int technicianId);

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.id = :id")
	MaintenanceRecord findMaintenanceRecordById(int id);

	@Query("SELECT a FROM Aircraft a")
	Collection<Aircraft> findAllAircrafts();

	@Query("select i.task from MaintenanceRecordTask i where i.maintenanceRecord.id = :maintenanceRecordId")
	Collection<Task> findTasksByMaintenanceRecordId(int maintenanceRecordId);

	@Query("select i from MaintenanceRecordTask i where i.maintenanceRecord.id = :maintenanceRecordId")
	Collection<MaintenanceRecordTask> findInvolvesByMaintenanceRecordId(int maintenanceRecordId);
}
