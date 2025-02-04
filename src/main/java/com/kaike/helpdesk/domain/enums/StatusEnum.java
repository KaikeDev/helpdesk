package com.kaike.helpdesk.domain.enums;

public enum StatusEnum {

	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

	private Integer codigo;
	private String descricao;

	private StatusEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static StatusEnum toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (StatusEnum x : StatusEnum.values()) { // pega os valor de Perfil (ADMIN, CLIENTE, TECNICO)
			if (cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Status inválido");
	}

}
