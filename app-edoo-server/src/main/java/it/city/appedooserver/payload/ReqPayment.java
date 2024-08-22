package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ReqPayment {
    private Date payDate;
    private double paySum;
    private Integer payTypeId;
    private UUID studentId;
    private Integer groupId;

}
