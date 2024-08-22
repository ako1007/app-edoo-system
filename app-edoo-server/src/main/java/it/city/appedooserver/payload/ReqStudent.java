package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ReqStudent {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer age;
    private Date registrationDay;
    private double discount;
    private String userStatus;
    private String cameFrom;
    private Integer groupId;
    private Integer awareId;

}
