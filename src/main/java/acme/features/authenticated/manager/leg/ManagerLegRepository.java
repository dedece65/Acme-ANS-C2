
package acme.features.authenticated.manager.leg;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.leg.Leg;

@Repository
public interface ManagerLegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.id= :id")
	Leg findLegById(int id);

	@Query("select l from Leg l")
	Collection<Leg> findAllLegs();
}
