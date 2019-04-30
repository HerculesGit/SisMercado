package com.mercado.sistema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Produto;
import com.mercado.model.Usuario;
import com.mercado.model.Venda;
import com.mercado.persistence.GravadoraDeDados;

public class SisMercadoLCC implements SisMercado{
	List<Usuario> usuarios;
	List<Produto> listaDeProdutos;
	List<Cliente> clientes;
	List<Venda> vendas;
	GravadoraDeDados gravadoraDeDados;

	public SisMercadoLCC(){
		usuarios = new ArrayList<Usuario>();
		listaDeProdutos = new ArrayList<Produto>();
		clientes = new ArrayList<Cliente>();
		vendas = new ArrayList<Venda>();
		gravadoraDeDados = new GravadoraDeDados();
	}
	
	@Override
	public void cadastrarUsuario(Usuario usuario) throws UsuarioJaExisteException {
		// verificar se o usuario ja foi cadastrado
		for (Usuario u: usuarios) {
			System.out.println(u.toString());
			if(u.equals(usuario)) {
				
				throw new UsuarioJaExisteException("Usuário já cadastrado!");
			}
		}
		
		usuarios.add(usuario);
	}

	@Override
	public void cadastrarCliente(Cliente cliente) throws ClienteJaExisteException {
		// verificar se o cliente ja foi cadastrado
		for (Cliente c : clientes) {
			if (c.equals(cliente)) {

				throw new ClienteJaExisteException("Cliente já cadastrado!");
			}
		}
		clientes.add(cliente);

	}

	/* Não usar ainda
	@Override
	public void cadastrarProduto(Produto produto) {
		listaDeProdutos.add(produto);
	}

	@Override
	public void cadastrarVenda(Venda venda) {
		vendas.add(venda);
	}
	*/

	@Override
	public boolean verificarLogin(String login, String senha) {
		// forEach java
		for (Usuario user : usuarios) {

			String loginUsuario= user.getLogin();
			String senhaUsuario = user.getSenha();
			
			// senha e login que estou procurando eh igual a do usuario atual do for?
			if (login.equals(loginUsuario) &&  senha.equals(senhaUsuario)){
				return true;
			}
		}

		return false;
	}
	
	@Override
	public List<Usuario> pesquisaUsuariosComNomeComecandoCom(String prefixo) {
		
		// Se a lista estier vazia, ou seja, for igual a zero, nao preciso ir alem pra pesquisar
		// apenas retorno a lista novamente
		if(usuarios.size() == 0) {
			return usuarios;
		}
		
		int tamanhoDoPrefixo = prefixo.length();
		List<Usuario> usuariosAchados = new ArrayList<Usuario>();
		
		// forEach java
		for (Usuario user : usuarios) {
			
			String nome = user.getNome();
			//	nao estourar o indice caso informe um prefixo enooooooorme
			if(nome.length() > tamanhoDoPrefixo) {
				if(nome.substring(0, tamanhoDoPrefixo).equals(prefixo)) {
					usuariosAchados.add(user);
				}
			}
		}
		
		return usuariosAchados;
	}

	@Override
	public List<Cliente> obterListaDeClientes() {
		return clientes;
	}

	@Override
	public void recuperarDados() throws IOException {
		clientes = gravadoraDeDados.recuperarClientes();
		usuarios = gravadoraDeDados.recuperarUsuarios();
		
	}

	@Override
	public void gravarDados() throws IOException {
		// Algoritmo
		// 1- Gravar cliente
		// 2- Gravar usuários
		
		
		// 1- Gravar cliente
		for (Cliente cliente : clientes) {
			
			//descobrindo a class, assim dá pra saber quem é a subclass
			if(cliente.getClass().equals(ClientePF.class)){
				
				// fazendo cast
				ClientePF clientePF = (ClientePF) cliente;
				gravadoraDeDados.gravaClientesPF(clientePF);
			
			} else {
				// fazendo cast
				ClientePJ clientePJ = (ClientePJ) cliente;
				gravadoraDeDados.gravaClientesPJ(clientePJ);
			}
		}
		
		
		// 2- Gravar usuários
		for (Usuario usuario : usuarios) {
			gravadoraDeDados.gravaUsuarios(usuario);
		}
		
	}
	
	
}
