package it.city.appedooserver.projection;

import it.city.appedooserver.entity.Role;
import it.city.appedooserver.entity.enums.RoleName;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Role.class)
public interface CustomRole {
    Integer getId();

    RoleName getRoleName();

}
