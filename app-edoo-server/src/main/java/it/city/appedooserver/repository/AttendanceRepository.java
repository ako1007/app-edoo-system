package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance,UUID> {
}
