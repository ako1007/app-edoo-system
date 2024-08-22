package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUser {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
    private String prePassword;
}
