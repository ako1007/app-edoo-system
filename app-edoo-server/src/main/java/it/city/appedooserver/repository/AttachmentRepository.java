package it.city.appedooserver.repository;

import it.city.appedooserver.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
