package it.city.appedooserver.entity;

import it.city.appedooserver.entity.enums.Weekday;
import it.city.appedooserver.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group extends AbsNameEntity {//Hosilbek & Akobir
    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private Timestamp startHour;//soat nechida  bulish vaqti
    @Column(nullable = false)
    private Timestamp finishHour;//soat nechida tugash vaqti

    private Date lessonStartedDate;//shu guruhga darslar qachon boshlanganligi sanasi

    private Date lessonFinishDate;//shu guruhga darslar qachon tugash sanasi

    @ManyToOne(optional = false)
    private  User teacher;// kim dasr utadi bu groupga

    @ElementCollection
    private List<String> weekdays;//uqish kunlarini nomi

    @Enumerated(EnumType.STRING)
    private Weekday weekday;//juft yoki toq kunlarda bulishi juft bulsa true toq bulsa false

    private boolean active;//ishlayapdi yoki ishlamayapdi
}