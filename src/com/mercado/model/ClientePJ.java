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
		return super.getNome() + getCNPJ();
	}
	
	public String getCNPJ() {
		return CNPJ;
	}

	@Override
	public String toString() {
		return "ClientePJ [nome = "+getNome() +", CNPJ = "+getCNPJ()+", código = "+getCodigo()+"]";
	}
	
	
	
}
