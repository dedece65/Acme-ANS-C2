
package acme.features.authenticated.technician;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Provider;

@Repository
public interface AuthenticatedTechnicianRepository extends AbstractRepository {

	@Query("select t from Technician t where t.userAccount.id = :id")
	Provider findTechnicianByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

}
