package it.city.appedooserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
    private String token;
    private String tokenType = "Bearer";

    public JwtToken(String token) {
        this.token = token;
    }
}
