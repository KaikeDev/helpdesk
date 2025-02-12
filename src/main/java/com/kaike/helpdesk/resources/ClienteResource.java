package com.kaike.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kaike.helpdesk.domain.Cliente;
import com.kaike.helpdesk.domain.dtos.ClienteDTO;
import com.kaike.helpdesk.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<Cliente> Clientes = clienteService.findAll();
		List<ClienteDTO> ClientesDTO = Clientes.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(ClientesDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) { // @PathVariable POR QUE ESTÁ VINDO UM ID
																			// JUNTO DA URL
		Cliente obj = this.clienteService.findByid(id);

		return ResponseEntity.ok().body(new ClienteDTO(obj));

	}

	@PostMapping()
	public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO OBJdto) {
		Cliente newOBJ = clienteService.create(OBJdto);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(newOBJ.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
		Cliente obj = clienteService.update(id, objDTO);
		
		return ResponseEntity.ok().body(new ClienteDTO(obj));
		
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> deleteCliente(@PathVariable Integer id){
		
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
