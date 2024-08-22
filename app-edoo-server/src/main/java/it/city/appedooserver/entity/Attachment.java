package it.city.appedooserver.entity;

import it.city.appedooserver.entity.template.AbsEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long size;
}
