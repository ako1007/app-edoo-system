package it.city.appedooserver.payload;


import it.city.appedooserver.entity.Group;
import it.city.appedooserver.entity.PayType;
import it.city.appedooserver.entity.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
public class ResPayment {
    private Date payDate;
    private double paySum;
    private PayType payType;
    private Student student;
    private Group group;
}
