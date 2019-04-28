package com.mercado.model;

public class Cliente {
	private String nome;
	
	//	nao entendi pra queh o codigo
	private String codigo;
	
	public Cliente(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String getCodigo() {
		return codigo;
	}
	
	@Override
	public String toString(){
		return "nome ["+getNome()+"]" +"["+ getCodigo() + "]";
	}
	
}
