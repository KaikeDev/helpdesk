package com.kaike.helpdesk.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.domain.dtos.TecnicoDTO;
import com.kaike.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping()
	public ResponseEntity<List<Tecnico>> findAll() {

		return ResponseEntity.ok().body(tecnicoService.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico obj = this.tecnicoService.findByid(id);

		return ResponseEntity.ok().body(new TecnicoDTO(obj));

	}

}
