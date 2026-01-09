package com.example.jeogieottae.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class PaymentTokenUtil {

    @Value("${spring.payment.token-key}")
    private String tokenKey;

    private SecretKey key;

    @PostConstruct
    public void initKey() {
        byte[] bytes = Decoders.BASE64.decode(tokenKey);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String create(Long userId, Long reservationId, LocalDateTime deadline) {

        return Jwts.builder()
                .setSubject("payment")
                .setExpiration(Date.from(deadline.toInstant(ZoneOffset.UTC)))
                .claim("uid", userId)
                .claim("rid", reservationId)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public boolean verify(String token, Long userId) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims claims = jws.getBody();
        Long uid = claims.get("uid", Long.class);


        return uid.equals(userId);
    }

    public Long extractReservationId(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return jws.getBody().get("rid", Long.class);
    }

}
