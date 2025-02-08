package com.kaike.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.repositories.TecnicoRepository;
import com.kaike.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();

	}

	public Tecnico findByid(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! "+ id));

	}
}
