package it.city.appedooserver.entity;

import it.city.appedooserver.entity.enums.CameFromEnum;
import it.city.appedooserver.entity.enums.UserStatus;
import it.city.appedooserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends AbsEntity {//Muhammad

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Date registrationDay;//ruyhatdan utgan sanasi

    private double discount;//oyik tulov chekirma foizi

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;//studentning holati uqiyapdi, kutayapdi va hakoza

    @Enumerated(EnumType.STRING)
    private CameFromEnum cameFromEnum;//bizni qayerdan kurib kelgani

    @ManyToOne(optional = false)
    private Group group;//qaysi guruhda uqishligi

    @ManyToOne(optional = false)
    private Aware aware;
}
