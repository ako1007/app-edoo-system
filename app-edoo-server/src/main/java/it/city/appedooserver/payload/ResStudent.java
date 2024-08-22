package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class ResStudent {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer age;
    private Date registrationDay;
    private double discount;
    private String userStatus;
    private String cameFrom;
    private Integer groupId;
    private String groupName;
    private Integer awareId;
    private String awareName;
}
