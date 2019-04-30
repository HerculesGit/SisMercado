package com.mercado.sistema;

import java.io.IOException;
import java.util.List;

import com.mercado.model.Cliente;
import com.mercado.model.Usuario;

/**
 * Todo que o SisMercado precisa fazer
 * */
public interface SisMercado {
	
	public void cadastrarUsuario(Usuario usuario) throws UsuarioJaExisteException;
	
	public void cadastrarCliente(Cliente cliente) throws ClienteJaExisteException;
	
	/* Não usar ainda
	public void cadastrarProduto(Produto produto);
	
	public void cadastrarVenda(Venda venda);
	*/
	public List<Usuario> pesquisaUsuariosComNomeComecandoCom(String prefixo);
	
	public List<Cliente> obterListaDeClientes();
	
	/**
	 * Verifica se existe algum usuario cadastrado com o login e senha informado
	 * */	
	public boolean verificarLogin(String login, String senha);
	
	public void recuperarDados() throws IOException;
	
	public void gravarDados() throws IOException;
	
	
}
