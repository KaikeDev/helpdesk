package com.kaike.helpdesk.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	// @Value("${jwt.secret}")
	private String secret = "StringUsadaParaGerarToken";

	// @Value("${jwt.expiration}")
	private Long expiration = 640800000L;

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis() + expiration)) // momento atual + 3 minutos que vem do 
				//application.properties																							
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // SignatureAlgorithm.HS512 é o algortimo usado
																		// para embaralhar a chave secreta
				.compact();
	}

	public boolean tokenValido(String token) {

		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {

		try {

			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

		} catch (Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {

		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}

		return null;
	}
}
