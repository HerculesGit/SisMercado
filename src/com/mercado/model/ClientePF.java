package com.mercado.model;

public class ClientePF extends Cliente {
	private String CPF;
	
	public ClientePF(String nome, String CPF) {
		super(nome);
		this.CPF = CPF;
	}

	public String getCPF() {
		return CPF;
	}

	@Override
	public String getCodigo() {
		return super.getNome() + getCPF();
	}
	
	@Override
	public String toString() {
		return"[nome = "+getNome() +", CPF = "+ getCPF() +", código= " + getCodigo() ;
	}
}
