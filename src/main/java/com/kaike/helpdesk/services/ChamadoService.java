package com.kaike.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaike.helpdesk.domain.Chamado;
import com.kaike.helpdesk.domain.Cliente;
import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.domain.dtos.ChamadoDTO;
import com.kaike.helpdesk.domain.enums.Prioridade;
import com.kaike.helpdesk.domain.enums.StatusEnum;
import com.kaike.helpdesk.repositories.ChamadoRepository;
import com.kaike.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado " + id));
	}

	public List<Chamado> findAll() {

		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO novoCliente) {

		return chamadoRepository.save(newChamado(novoCliente));
	}
	
	public Chamado update(Integer id, @Valid ChamadoDTO novoChamado) {
		novoChamado.setId(id);
		
		Chamado oldObj = findById(id);
		oldObj = newChamado(novoChamado);
		return chamadoRepository.save(oldObj);
	}

	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findByid(obj.getTecnico());
		Cliente cliente = clienteService.findByid(obj.getCliente());

		Chamado chamado = new Chamado();

		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}

		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);

		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(StatusEnum.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());

		return chamado;
	}

	

}
