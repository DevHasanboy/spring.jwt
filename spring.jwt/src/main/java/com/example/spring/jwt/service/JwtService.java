package com.example.spring.jwt.service;

import com.example.spring.jwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

private final String SECRET_KEY="aaecc3cc8e3ccc17b146bec60d6160a77b8a2459850e1541c326bc7bc6d9521d";


public boolean isvalid(String token, UserDetails user){
    String username=extractUsername(token);
    return (username.equals(user.getUsername()) && isTokenExpired(token));
}


    private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
    return extractClaims(token,Claims::getExpiration);
    }



public String extractUsername(String token){
    return extractClaims(token,Claims::getSubject);
}


public <T> T extractClaims(String token, Function<Claims,T> resolver){

    Claims claims=extractAllClaims(token);
    return resolver.apply(claims);

}



private Claims extractAllClaims(String token){
    return Jwts
            .parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}


public String generateToken(User user){
    String token= Jwts
            .builder()
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
            .signWith(getSignKey())
            .compact();
    return token;
}


    private SecretKey getSignKey() {
    byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
    }



}
