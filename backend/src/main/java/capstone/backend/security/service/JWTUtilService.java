package capstone.backend.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTUtilService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    long duration = 4 * 60 * 60 * (long) 1000;


    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public String createToken(HashMap<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMillis(duration))))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET )
                .compact();

    }
}
