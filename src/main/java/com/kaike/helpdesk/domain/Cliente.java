package com.kaike.helpdesk.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kaike.helpdesk.domain.enums.Perfil;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "cliente")
	private List<Chamado> chamado =  new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
		// TODO Auto-generated constructor stub
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);

		addPerfil(Perfil.CLIENTE);
		// TODO Auto-generated constructor stub
	}

	public List<Chamado> getChamado() {
		return chamado;
	}

	public void setChamado(List<Chamado> chamado) {
		this.chamado = chamado;
	}
	
	
}
