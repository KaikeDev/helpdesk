package com.kaike.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Pessoa;
import com.kaike.helpdesk.repositories.PessoaRepository;
import com.kaike.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceLmpl implements UserDetailsService{
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Pessoa> user = pessoaRepository.findByEmail(email);
		if(user.isPresent()) {
			System.out.println("Usuário encontrado: " + user.get().getEmail());
			System.out.println("Senha encontrado: " + user.get().getSenha());
		       	
			return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
		}
		throw new UsernameNotFoundException(email);
	}
	


}
