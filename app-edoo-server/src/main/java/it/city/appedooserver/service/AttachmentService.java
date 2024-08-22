package it.city.appedooserver.service;

import it.city.appedooserver.DTOs.AttachmentResDto;
import it.city.appedooserver.entity.Attachment;
import it.city.appedooserver.repository.AttachmentContentRepository;
import it.city.appedooserver.repository.AttachmentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class AttachmentService {

    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;

    public static final Path root = Paths.get("D:\\IMG-AppEdoo");

    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    public UUID upload(MultipartFile multipartFile) throws IOException {
        Files.copy(multipartFile.getInputStream(), root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        return attachmentRepository.save(new Attachment(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize()
        )).getId();
    }

    public AttachmentResDto getFile(UUID id) throws MalformedURLException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetAttachment"));
        Path file = root.resolve(attachment.getName());
        Resource resource = new UrlResource(file.toUri());
        return new AttachmentResDto(
                attachment.getId(),
                resource,
                attachment
        );
    }
}
