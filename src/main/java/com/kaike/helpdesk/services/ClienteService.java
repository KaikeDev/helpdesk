package com.kaike.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Cliente;
import com.kaike.helpdesk.domain.Pessoa;
import com.kaike.helpdesk.domain.dtos.ClienteDTO;
import com.kaike.helpdesk.repositories.ClienteRepository;
import com.kaike.helpdesk.repositories.PessoaRepository;
import com.kaike.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.kaike.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();

	}

	public Cliente findByid(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! " + id));

	}

	public Cliente create(ClienteDTO oBJdto) {
		oBJdto.setId(null);
		oBJdto.setSenha(encoder.encode(oBJdto.getSenha()));
		validaCPFandEMAIL(oBJdto);
		Cliente newOBJ = new Cliente(oBJdto);
		return clienteRepository.save(newOBJ);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {

		objDTO.setId(id);
		Cliente oldOBJ = findByid(id);
		validaCPFandEMAIL(objDTO);
		oldOBJ = new Cliente(objDTO);

		return clienteRepository.save(oldOBJ);
	}

	private void validaCPFandEMAIL(ClienteDTO oBJdto) {

		Optional<Pessoa> obj = pessoaRepository.findByCpf(oBJdto.getCpf());
		if (obj.isPresent() && obj.get().getId() != oBJdto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}

		obj = pessoaRepository.findByEmail(oBJdto.getEmail());
		if (obj.isPresent() && obj.get().getId() != oBJdto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema");
		}

	}

	public void delete(Integer id) {

		Cliente obj = findByid(id);
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de servicço e não pode ser deletado");
		}

		clienteRepository.deleteById(id);
	}

}
