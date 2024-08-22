package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Group;
import it.city.appedooserver.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findAllByGroup(Group group);
}
