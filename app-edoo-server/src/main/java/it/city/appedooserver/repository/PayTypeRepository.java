package it.city.appedooserver.repository;

import it.city.appedooserver.entity.PayType;
import it.city.appedooserver.projection.CustomPayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payType", collectionResourceRel = "list", excerptProjection = CustomPayType.class)
public interface PayTypeRepository extends JpaRepository<PayType,Integer> {
}
