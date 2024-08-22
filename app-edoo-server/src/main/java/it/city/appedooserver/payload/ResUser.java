package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResUser {
    private UUID id;
    private String name;
    private String username;
    private String phoneNumber;
    private String role;
}
