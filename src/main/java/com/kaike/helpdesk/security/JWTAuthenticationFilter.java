package com.kaike.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaike.helpdesk.domain.dtos.CredentialsDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}


	// Isso vai verificar a autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	        throws AuthenticationException {
	    System.out.println("Entrou no filtro de autenticação!");

	    try {
	        CredentialsDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredentialsDTO.class);
	        System.out.println("Credenciais recebidas: " + creds.getEmail());

	        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                creds.getEmail(), creds.getSenha(), new ArrayList<>());
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);

	        System.out.println("Autenticação bem-sucedida!");
	        return authentication;
	    } catch (Exception e) {
	        e.printStackTrace();  // Adicione um printStackTrace para ver se há exceções
	        throw new RuntimeException(e);
	    }
	}

	// Segue esse fluxo se a autenticação der certo
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String userName = ((UserSS) authResult.getPrincipal()).getUsername();

		String token = jwtUtil.generateToken(userName);
		response.setHeader("access-control-expose-headers", "Authorization");

		response.setHeader("Authorization", "Bearer " + token); // retorna o token para o usuario
	}

	// Segue esse fluxo se a autenticação der errado
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}

	private CharSequence json() {

		long date = new Date().getTime();
		return "{" + "\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
	}

}
