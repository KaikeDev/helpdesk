package com.kaike.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.domain.dtos.TecnicoDTO;
import com.kaike.helpdesk.services.TecnicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping()
	public ResponseEntity<List<TecnicoDTO>> findAll() {

		List<Tecnico> tecnicos = tecnicoService.findAll();
		List<TecnicoDTO> tecnicosDTO = tecnicos.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(tecnicosDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) { // @PathVariable POR QUE ESTÁ VINDO UM ID
																			// JUNTO DA URL
		Tecnico obj = this.tecnicoService.findByid(id);

		return ResponseEntity.ok().body(new TecnicoDTO(obj));

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<TecnicoDTO> createTecnico(@Valid @RequestBody TecnicoDTO OBJdto) {
	    System.out.println("POST request received");

		Tecnico newOBJ = tecnicoService.create(OBJdto);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(newOBJ.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}


	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> updateTecnico(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
		
		Tecnico obj = tecnicoService.update(id, objDTO);
		
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
		
	}


	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> deleteTecnico(@PathVariable Integer id){
		
		tecnicoService.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
