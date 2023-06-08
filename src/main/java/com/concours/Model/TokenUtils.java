package com.concours.Model;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenUtils {

    // Secret key used to sign the token
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token validity duration (e.g., 1 day)
    private static final long VALIDITY_DURATION_MS = 24 * 60 * 60 * 1000;

    public static String generateToken(Object principal) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_DURATION_MS);

        byte[] secretKeyBytes = SECRET_KEY.getEncoded();

        return Jwts.builder()
                .setSubject(principal.toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public static Object validateToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
