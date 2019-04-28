package com.mercado.model;

public class ClientePJ extends Cliente {
	private String CNPJ;
	
	public ClientePJ(String nome) {
		super(nome);
	}
	
	public ClientePJ(String nome, String CNPJ) {
		super(nome);
		this.CNPJ = CNPJ;
	}

	@Override
	public String getCodigo() {
		return super.getCodigo();
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
}
