package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Role;
import it.city.appedooserver.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);
}
