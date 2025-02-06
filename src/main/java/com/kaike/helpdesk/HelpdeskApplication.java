package com.kaike.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kaike.helpdesk.domain.Chamado;
import com.kaike.helpdesk.domain.Cliente;
import com.kaike.helpdesk.domain.Tecnico;
import com.kaike.helpdesk.domain.enums.Perfil;
import com.kaike.helpdesk.domain.enums.Prioridade;
import com.kaike.helpdesk.domain.enums.StatusEnum;
import com.kaike.helpdesk.repositories.ChamadoRepository;
import com.kaike.helpdesk.repositories.ClienteRepository;
import com.kaike.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private  ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Kaike Tuerpe", "123456789", "kaike@gmail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Luysa", "987654321", "luysa@gmail.com", "321");
		
		Chamado c1 = new Chamado(null, Prioridade.MÉDIA, StatusEnum.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
	}

}
