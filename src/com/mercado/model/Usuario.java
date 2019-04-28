package com.mercado.model;

public class Usuario {
	private String login;
	private String nome;
	private String senha;

	public Usuario(String nome, String login, String senha) {
		this.login = login;
		this.nome = nome;
		this.senha = senha;
	}
	public Usuario(String nome, String login) {
		// Reaproveita o outro construtor, passando o ultimo parametro
		// que eh a 'senha' como vazio
		this(nome, login, "");
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
