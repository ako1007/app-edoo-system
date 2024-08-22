package it.city.appedooserver.entity;

import it.city.appedooserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent extends AbsEntity {
    @OneToOne
    private Attachment attachment;

    @Column(nullable = false)
    private byte[] contentSize;
}
