package com.kaike.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaike.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	
}
