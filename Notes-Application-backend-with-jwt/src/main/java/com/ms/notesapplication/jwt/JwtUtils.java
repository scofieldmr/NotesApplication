package com.ms.notesapplication.jwt;

import com.ms.notesapplication.entity.CustomerUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${authentication.app.secretKey}")
    private String secretKey;

    @Value("${authentication.app.expirationInMs}")
    private int expirationInMs;

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    //Generate Token
    public String generateJwtToken(Authentication authentication) {

        CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map((r)-> r.getAuthority()).collect(Collectors.toSet());

        Date expiration = new Date(new Date().getTime() + expirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("roles", roles)
                .setExpiration(expiration)
                .signWith(key())
                .compact();

    }

    //Generate username
    public String extractUsernameFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();

    }

    public Set<String> extractRolesFromToken(String token) {

        List<String> extractRole= Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);

        return new HashSet<>(extractRole);
    }

    //Valid token
    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key()).build().parseClaimsJws(authToken);

            return true;
        }
        catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
