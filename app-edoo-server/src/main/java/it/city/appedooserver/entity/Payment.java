package it.city.appedooserver.entity;

import it.city.appedooserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment extends AbsEntity {//Behruz & Zokirov

    private Date payDate;

    private double paySum;

    @ManyToOne(optional = false)
    private PayType payType;

    @ManyToOne(optional = false)
    private Student student;

    @ManyToOne(optional = false)
    private Group group;


}
