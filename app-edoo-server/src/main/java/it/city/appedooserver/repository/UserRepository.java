package it.city.appedooserver.repository;

import it.city.appedooserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameEqualsIgnoreCase(String username);


    boolean existsByUsernameEqualsIgnoreCaseAndPhoneNumber(String username, String phoneNumber);
    boolean existsByUsernameEqualsIgnoreCaseAndPhoneNumberAndIdNot(String username, String phoneNumber, UUID id);
}
