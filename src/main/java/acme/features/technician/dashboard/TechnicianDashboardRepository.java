
package acme.features.technician.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.realms.Technician;

@Repository
public interface TechnicianDashboardRepository extends AbstractRepository {

	@Query("SELECT t FROM Technician t WHERE t.userAccount.id = :id")
	Technician findOneTechnicianByUserAccoundId(int id);

	@Query("SELECT COUNT(m) FROM MaintenanceRecord m WHERE m.technician.id = :technicianId")
	Integer countMaintenanceRecordsByTechnicianId(int technicianId);

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.technician.id = :technicianId ORDER BY m.nextInspectionDue ASC")
	MaintenanceRecord findNearestInspectionRecordByTechnicianId(int technicianId);

	@Query("SELECT STDDEV(m.estimatedCost) FROM MaintenanceRecord m WHERE m.technician.id = :technicianId")
	Double findDeviationEstimatedCost(int technicianId);

	@Query("SELECT MIN(m.estimatedCost) FROM MaintenanceRecord m WHERE m.technician.id = :technicianId")
	Double findMinEstimatedCost(int technicianId);

	@Query("SELECT MAX(m.estimatedCost) FROM MaintenanceRecord m WHERE m.technician.id = :technicianId")
	Double findMaxEstimatedCost(int technicianId);

	@Query("SELECT AVG(t.estimatedDuration) FROM Task t WHERE t.technician.id = :technicianId")
	Double findAverageEstimatedDuration(int technicianId);

	@Query("SELECT STDDEV(t.estimatedDuration) FROM Task t WHERE t.technician.id = :technicianId")
	Double findDeviationEstimatedDuration(int technicianId);

	@Query("SELECT MIN(t.estimatedDuration) FROM Task t WHERE t.technician.id = :technicianId")
	Double findMinEstimatedDuration(int technicianId);

	@Query("SELECT MAX(t.estimatedDuration) FROM Task t WHERE t.technician.id = :technicianId")
	Double findMaxEstimatedDuration(int technicianId);
}
