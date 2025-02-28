package com.kaike.helpdesk.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			String header = request.getHeader("Authorization");
			
			if(header != null && header.startsWith("Bearer ")) {
				UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7)); // ele pega a partir do sétimo caractere da string HEADER, isso seria depois do BEARER 
			
				if(authToken != null) {
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		if(jwtUtil.tokenValido(token)) {
			String userName = jwtUtil.getUsername(token);
			UserDetails details = userDetailsService.loadUserByUsername(userName);
			
			  Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
		        
		        // Debugging
		        System.out.println("Authorities: " + authorities); // Adicione um print para depurar
		        // Log para verificar roles
		        System.out.println("UserRoles: " + details.getAuthorities());

			return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
		}
		return null;
		
	}
	
	 private String getToken(HttpServletRequest request) {
	        String authHeader = request.getHeader("Authorization");
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            return authHeader.substring(7); // Remove "Bearer " e retorna o token
	        }
	        return null;
	    }

}
