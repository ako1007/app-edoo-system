package it.city.appedooserver.service;


import it.city.appedooserver.entity.Role;
import it.city.appedooserver.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

import static it.city.appedooserver.entity.enums.RoleName.*;


@Service
public class CheckRole {
    final
    RoleRepository roleRepository;

    public CheckRole(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean isROLE_SUPER_ADMIN(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRoleName() == ROLE_DIRECTOR) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRoleName() == ROLE_TEACHER||role.getRoleName()==ROLE_MANAGER) {
                return true;
            }
        }
        return false;
    }


//    public boolean isUser(Set<Role> roles) {
//        for (Role role : roles) {
//            if (role.getRoleName() == ROLE_USER) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isNone(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRoleName() == NONE) {
                return true;
            }
        }
        return false;
    }
}
