
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.SystemConfiguration;
import acme.entities.offer.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o where o.id = :id")
	Offer findOfferById(int id);

	@Query("select o from Offer o")
	Collection<Offer> findAllOffer();

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
