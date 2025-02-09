package com.kaike.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Pessoa;
import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.domain.dtos.TecnicoDTO;
import com.kaike.helpdesk.repositories.PessoaRepository;
import com.kaike.helpdesk.repositories.TecnicoRepository;
import com.kaike.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.kaike.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	PessoaRepository pessoaRepository;

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();

	}

	public Tecnico findByid(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! " + id));

	}

	public Tecnico create(TecnicoDTO oBJdto) {
		oBJdto.setId(null);
		validaCPFandEMAIL(oBJdto);
		Tecnico newOBJ = new Tecnico(oBJdto);
		return tecnicoRepository.save(newOBJ);
	}

	private void validaCPFandEMAIL(TecnicoDTO oBJdto) {

		Optional<Pessoa> obj = pessoaRepository.findByCpf(oBJdto.getCpf());
		if(obj.isPresent() && obj.get().getId() != oBJdto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(oBJdto.getEmail());
		if(obj.isPresent() && obj.get().getId() != oBJdto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema");
		}
		
	}
}
