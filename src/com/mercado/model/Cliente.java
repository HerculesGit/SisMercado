package com.mercado.model;

public abstract class Cliente {
	private String nome;
	
	//	nao entendi pra queh o codigo
	//private String codigo;
	
	public Cliente(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public abstract String getCodigo();
	
	@Override
	public String toString(){
		return "nome ["+getNome()+"]" +"["+ getCodigo() + "]";
	}
	
}
