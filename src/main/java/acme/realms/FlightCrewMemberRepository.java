
package acme.realms;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface FlightCrewMemberRepository extends AbstractRepository {

	@Query("SELECT c FROM FlightCrewMember c WHERE c.code = :code AND c.id != :flightCrewMemberId")
	Optional<FlightCrewMember> findByEmployeeCode(String code, int flightCrewMemberId);

}
