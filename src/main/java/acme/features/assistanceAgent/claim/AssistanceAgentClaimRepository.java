
package acme.features.assistanceAgent.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;
import acme.entities.leg.Leg;
import acme.entities.trackingLog.TrackingLog;
import acme.realms.AssistanceAgent;

@Repository
public interface AssistanceAgentClaimRepository extends AbstractRepository {

	@Query("select c from Claim c where c.indicator in (0, 2) and c.assistanceAgents.id = :assistanceAgentId")
	Collection<Claim> findCompletedClaimsByAssistanceAgentId(int assistanceAgentId);

	@Query("select c from Claim c where c.indicator = 1 and c.assistanceAgents.id = :assistanceAgentId")
	Collection<Claim> findUndergoingClaimsByAssistanceAgentId(int assistanceAgentId);

	@Query("select t from TrackingLog t where t.claims.id = :claimId")
	Collection<TrackingLog> findAllTrackingLogsByClaimId(int claimId);

	@Query("select c from Claim c where c.id = :id")
	Claim findClaimById(int id);

	@Query("select a from AssistanceAgent a where a.id = :assistanceAgentId")
	AssistanceAgent findAssistanceAgentById(int assistanceAgentId);

	@Query("select l from Leg l where l.draftMode = true")
	Collection<Leg> findAllLegsNotPublished();

	@Query("select l from Leg l where l.id = :legId")
	Leg findLegById(int legId);

	@Query("select l from Leg l")
	Collection<Leg> findAllLegs();
}
