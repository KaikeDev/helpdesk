package com.kaike.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findByid(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		
		return obj.orElse(null);
		
		
	}
}
