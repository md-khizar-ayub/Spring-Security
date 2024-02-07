package com.sc.security.securityutils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class JwtUtils {
    private JwtUtils(){}

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final String secret= "===========Mohammed_Khizar_Ayub===========";

    private static final String ISSUER = "AYUB";
    private static  final SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));


    //retrieve user name from jwt token

    public static boolean validateToken(String jwtToken){
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken){
                JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
        try {
            return  Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        }catch (Exception e){
            log.error("JWT Exception Occurred");
        }

        return Optional.empty();
    }



    public static Optional<String> getUsernameFromToken(String jwtToken){

        Optional<Claims> claimsOptional = parseToken(jwtToken);

        if(claimsOptional.isPresent())
            return Optional.of(claimsOptional.get().getSubject());

        return Optional.empty();
    }


    public static String generateToken(String userName){
        Date currentDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(userName)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .compact();
    }

}
