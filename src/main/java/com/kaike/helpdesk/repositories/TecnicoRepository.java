package com.kaike.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaike.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

	
}
