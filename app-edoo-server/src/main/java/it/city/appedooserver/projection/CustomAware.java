package it.city.appedooserver.projection;

import it.city.appedooserver.entity.Aware;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Aware.class)
public interface CustomAware {
    Integer getId();
    String getName();
}
