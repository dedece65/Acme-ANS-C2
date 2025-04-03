
package acme.features.authenticated.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.service.Service;

@Repository
public interface AuthenticatedServiceRepository extends AbstractRepository {

	@Query("SELECT s FROM Service s ORDER BY FUNCTION('RAND')")
	List<Service> findRandomService();

}
