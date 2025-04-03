
package acme.features.administrator.trackingLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;
import acme.entities.trackingLog.TrackingLog;

@Repository
public interface AdministratorTrackingLogRepository extends AbstractRepository {

	@Query("select t from TrackingLog t where t.id = :id")
	TrackingLog findTrackingLogById(int id);

	@Query("select t from TrackingLog t where t.claims.id = :claimId")
	Collection<TrackingLog> findTrackingLogsByClaimId(int claimId);

	@Query("select t from TrackingLog t where t.claims.assistanceAgents.id = :assistanceAgentId")
	Collection<TrackingLog> findTrackingLogsByAssistanceAgentId(int assistanceAgentId);

	@Query("select c.draftMode from Claim c where c.id = :claimId")
	boolean isClaimPublished(int claimId);

	@Query("select c from Claim c where c.id = :claimId")
	Claim findClaimById(int claimId);

	@Query("select t from TrackingLog t where t.claims.id = :claimId order by t.updateMoment desc")
	Collection<TrackingLog> findLatestTrackingLogsByClaimId(int claimId);

	@Modifying
	@Query("delete from TrackingLog t where t.id = :id")
	void deleteTrackingLogById(int id);
}
