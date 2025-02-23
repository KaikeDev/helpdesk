	package com.kaike.helpdesk.security;
	
	import java.util.Date;
	
	import org.springframework.stereotype.Component;
	
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.SignatureAlgorithm;
	
	@Component
	public class JWTUtil {
		
		//@Value("${jwt.secret}")
		private String secret = "StringUsadaParaGerarToken";
		
		//@Value("${jwt.expiration}")
		private Long expiration = 180000L;
		
		
	
		public String generateToken(String email) {
			return Jwts.builder()
					.setSubject(email)
					.setExpiration(new Date(System.currentTimeMillis() + expiration)) // momento atual + 3 minutos que vem do application.properties 
					.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // SignatureAlgorithm.HS512 é o algortimo usado para embaralhar a chave secreta
					.compact();
		}
	}
