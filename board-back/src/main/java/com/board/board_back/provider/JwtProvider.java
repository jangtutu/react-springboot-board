package com.board.board_back.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component //의존성 주입
public class JwtProvider {

    private String secretKey = "S3cr3tK3y";

    public String create(String email) { //생성

        Date expiredDate = Date.from(Instant.now().plus(1,ChronoUnit.HOURS)); //만료기간
        
        String jwt = Jwts.builder() //JWT 생성
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
            .compact();

        return jwt;
    }

    public String validate(String jwt) { //검증

        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwt).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }
    
}