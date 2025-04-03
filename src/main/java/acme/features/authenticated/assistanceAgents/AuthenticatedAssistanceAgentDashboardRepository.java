
package acme.features.authenticated.assistanceAgents;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAssistanceAgentDashboardRepository extends AbstractRepository {

	// Consulta para obtener los tres meses con más reclamaciones para un agente de asistencia
	@Query("SELECT DATE_FORMAT(c.registrationMoment, '%Y-%m') " + "FROM Claim c " + "WHERE c.assistanceAgents.id = :assistanceAgentId " + "GROUP BY DATE_FORMAT(c.registrationMoment, '%Y-%m') " + "ORDER BY COUNT(c) DESC")
	List<String> findTopThreeMonthsWithMostClaims(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para contar el número total de reclamaciones
	@Query("SELECT COUNT(c) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId")
	long countClaimsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para contar las reclamaciones aceptadas
	@Query("SELECT COUNT(c) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId AND c.indicator = 'ACCEPTED'")
	long countResolvedClaimsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para contar las reclamaciones rechazadas
	@Query("SELECT COUNT(c) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId AND c.indicator = 'REJECTED'")
	long countRejectedClaimsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener el promedio de resolución de los TrackingLogs
	@Query("SELECT COALESCE(AVG(t.resolutionPercentage), 0) FROM TrackingLog t WHERE t.claims.assistanceAgents.id = :assistanceAgentId")
	double averageClaimLogsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener la desviación estándar de resolución de los TrackingLogs
	@Query("SELECT COALESCE(STDDEV(t.resolutionPercentage), 0) FROM TrackingLog t WHERE t.claims.assistanceAgents.id = :assistanceAgentId")
	double deviationClaimLogsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener la resolución mínima de los TrackingLogs
	@Query("SELECT COALESCE(MIN(t.resolutionPercentage), 0) FROM TrackingLog t WHERE t.claims.assistanceAgents.id = :assistanceAgentId")
	int minClaimLogsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener la resolución máxima de los TrackingLogs
	@Query("SELECT COALESCE(MAX(t.resolutionPercentage), 0) FROM TrackingLog t WHERE t.claims.assistanceAgents.id = :assistanceAgentId")
	int maxClaimLogsByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener el promedio de reclamaciones atendidas
	@Query("SELECT COALESCE(AVG(c.assistanceAgents.id), 0) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId")
	double averageClaimsAssistedByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener la desviación estándar de las reclamaciones atendidas
	@Query("SELECT COALESCE(STDDEV(c.assistanceAgents.id), 0) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId")
	double deviationClaimsAssistedByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener el mínimo de reclamaciones atendidas
	@Query("SELECT COALESCE(MIN(c.assistanceAgents.id), 0) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId")
	int minClaimsAssistedByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);

	// Consulta para obtener el máximo de reclamaciones atendidas
	@Query("SELECT COALESCE(MAX(c.assistanceAgents.id), 0) FROM Claim c WHERE c.assistanceAgents.id = :assistanceAgentId")
	int maxClaimsAssistedByAssistanceAgentId(@Param("assistanceAgentId") int assistanceAgentId);
}
