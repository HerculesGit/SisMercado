package com.mercado.model;

public class Produto {
	private String codigo;
	private String nome;
	private double precoAtacado;
	private double precoVarejo;
	
	public Produto(String codigo, String nome, double precoAtacado, double precoVarejo) {
		this.codigo = codigo;
		this.nome = nome;
		this.precoAtacado = precoAtacado;
		this.precoVarejo = precoVarejo;
	}
	
	
	public Produto() {
		this("", "", 0.0, 0.0);
	}



	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPrecoAtacado() {
		return precoAtacado;
	}
	public void setPrecoAtacado(double precoAtacado) {
		this.precoAtacado = precoAtacado;
	}
	public double getPrecoVarejo() {
		return precoVarejo;
	}
	public void setPrecoVarejo(double precoVarejo) {
		this.precoVarejo = precoVarejo;
	}
	
	
	
	
	
}
