
package acme.features.administrator.maintenanceRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecord.MaintenanceRecord;

@Repository
public interface AdministratorMaintenanceRecordRepository extends AbstractRepository {

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.draftMode = false")
	Collection<MaintenanceRecord> findPublishedMaintenanceRecords();

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.draftMode = false AND m.id = :id")
	MaintenanceRecord findPublishedMaintenanceRecordById(int id);

	@Query("SELECT a FROM Aircraft a")
	Collection<Aircraft> findAllAircrafts();
}
