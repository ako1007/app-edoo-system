package it.city.appedooserver.projection;

import it.city.appedooserver.entity.PayType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = PayType.class)
public interface CustomPayType {
    Integer getId();
    String getName();
    Boolean isActive();
}
