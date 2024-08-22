package it.city.appedooserver.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.city.appedooserver.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecretKey}")
    private String secretKey;

    @Value("${app.jwtExpireInMilSec}")
    private Long expireTime;

    public String generateToken(User user) {
        Date durationLife = new Date(new Date().getTime() + expireTime);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", user.getRoleSet())
                .setIssuedAt(new Date())
                .setExpiration(durationLife)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
