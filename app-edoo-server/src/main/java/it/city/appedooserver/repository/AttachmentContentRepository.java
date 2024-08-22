package it.city.appedooserver.repository;

import it.city.appedooserver.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
}
