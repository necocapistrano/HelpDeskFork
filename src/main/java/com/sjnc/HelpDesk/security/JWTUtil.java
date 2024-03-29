package com.sjnc.HelpDesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value(value= "${jwt.expiration}")
	private Long expiration;
	@Value(value= "${jwt.secret}")
	private String secret;
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if(claims !=null) {
			String username = claims.getSubject();
			Date expirationsDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if(username !=null && expirationDate != null && now.before(expirationsDate)) {
			return true;			
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
			
		} catch (Exception e) {
			// TODO: handle exception
		};
	}

	public String getUsername(String token) {
		// TODO Auto-generated method stub
		return null;
	}
}
