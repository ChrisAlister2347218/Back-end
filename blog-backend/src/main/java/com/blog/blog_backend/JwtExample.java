package com.blog.blog_backend; // Adjust the package according to your project structure

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtExample {
    private static final String SECRET_KEY = "werfderfdrgf"; // Use the same secret key you configured

    public static void main(String[] args) {
        // Generate token
        String token = generateToken("test12");
        System.out.println("Generated Token: " + token);

        // Validate token
        boolean isValid = validateToken(token);
        System.out.println("Is token valid? " + isValid);
    }

    private static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return false;
    }
}
