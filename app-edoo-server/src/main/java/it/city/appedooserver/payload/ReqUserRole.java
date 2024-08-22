package it.city.appedooserver.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class ReqUserRole {
    private UUID id;
    private String roleName;
}
