package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Aware;
import it.city.appedooserver.projection.CustomAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(path = "aware", collectionResourceRel = "list", excerptProjection = CustomAware.class)
public interface AwareRepository extends JpaRepository<Aware, Integer> {
}
