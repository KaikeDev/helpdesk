package com.kaike.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaike.helpdesk.domain.Chamado;
import com.kaike.helpdesk.domain.dtos.ChamadoDTO;
import com.kaike.helpdesk.services.ChamadoService;

import jakarta.servlet.Servlet;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		
		Chamado obj = chamadoService.findById(id);
		
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}
	
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> list = chamadoService.findAll();
		
		List<ChamadoDTO> listDTO  = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<ChamadoDTO> createChamado(@Valid @RequestBody ChamadoDTO novoCliente){
		
		Chamado obj = chamadoService.create(novoCliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(novoCliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}

}
