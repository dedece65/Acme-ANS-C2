
package acme.realms;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerRepository extends AbstractRepository {

	@Query("SELECT a FROM Manager a WHERE a.idNumber = :identifier AND a.id != :managerId")
	Optional<Manager> findByIdentifier(String identifier, int managerId);

}
