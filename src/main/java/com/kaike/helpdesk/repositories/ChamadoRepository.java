package com.kaike.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaike.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

	
}
