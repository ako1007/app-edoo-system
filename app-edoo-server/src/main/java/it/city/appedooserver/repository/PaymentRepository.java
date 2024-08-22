package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
