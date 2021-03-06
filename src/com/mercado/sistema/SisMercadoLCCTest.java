package com.mercado.sistema;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Produto;
import com.mercado.model.Usuario;
import com.mercado.persistence.GravadoraDeDados;

class SisMercadoLCCTest {
	SisMercado sis;
	GravadoraDeDados gravadoraDeDados;
	
	@Before
	public void inicialize(){
		this.sis = new SisMercadoLCC();
		this.gravadoraDeDados = new GravadoraDeDados();
	}
	
	@Test
	void criaUsuarioESetaTodosOsAtributos() {
		Usuario usuario = new Usuario("Leandro Franklin","leandro@gmail.com", "1234");
//		usuario.setLogin("leandro@gmail.com");
//		usuario.setNome("Leandro Franklin");
//		usuario.setSenha("1234");
		
		assertEquals("leandro@gmail.com", usuario.getLogin());
		assertEquals("Leandro Franklin", usuario.getNome());
		assertEquals("1234", usuario.getSenha());
	}

	@Test
	void criaUmClientePFESetaTodosOsAtributos() {
		Cliente cliente = new ClientePF("Leandro Franklin","12345678901");
//		cliente.setCodigo("8812");
		cliente.getNome();
		
		assertEquals("Leandro Franklin", cliente.getNome());
//		assertEquals("8812", cliente.getCodigo());
		assertEquals("12345678901", ((ClientePF) cliente).getCPF());
		
	}
	
	@Test
	void criaUmClientePJESetaTodosOsAtributos() {
		Cliente cliente = new ClientePJ("Leandro Franklin", "12345678901234");
//		cliente.getSetCodigo("8912");
		cliente.getNome();
		
		assertEquals("Leandro Franklin", cliente.getNome());
//		assertEquals("8912", cliente.getCodigo());
		assertEquals("12345678901234", ((ClientePJ) cliente).getCNPJ());
	}
	
//	@Test
//	void verificaToStringDoClientePJ() {
//		Cliente cliente = new ClientePJ("Leandro Franklin", "12345678901234");
//		cliente.setCodigo("9911");
//		
//		String toStringCliente = "nome: [Leandro Franklin]\n"
//				+ "cnpj: [9911]"
//				+ "codigo: [12345678901234]";
//		
//		assertEquals(toStringCliente, cliente.toString());
//		
//	}
	
	@Test
	void criaUmProdutoESetaSeusAtributos() {
		// Atacado vendo em caixa
		// Varejo vende em unidade(s)
		
		Produto prod = new Produto();
		prod.setNome("Acucar Ouro Branco");
		prod.setCodigo("7891");
		prod.setPrecoAtacado(1.20);
		prod.setPrecoVarejo(1.99);
		
		assertEquals("Acucar Ouro Branco", prod.getNome());
		assertEquals("7891", prod.getCodigo());
		assertEquals(1.20, prod.getPrecoAtacado());
		assertEquals(1.99, prod.getPrecoVarejo());
		
	}
	
	
	@Test
	void pesquisaUsuariosComNomeComecandoComMari() throws UsuarioJaExisteException {
		SisMercado sis = new SisMercadoLCC();
		sis.cadastrarUsuario(new Usuario("Maria Madalena", "1"));
		sis.cadastrarUsuario(new Usuario("Joelson Marionho", "12"));
		sis.cadastrarUsuario(new Usuario("Mariana Duarte", "1212"));
		sis.cadastrarUsuario(new Usuario("Manuel Pinto", "121212"));
		sis.cadastrarUsuario(new Usuario("Camila Pereira", "12121212"));
		sis.cadastrarUsuario(new Usuario("João Azevedo", "1212121221"));
		
		for(Usuario u: sis.pesquisaUsuariosComNomeComecandoCom("Manuel")) {
			System.out.println(u.getNome());
		}
		
	}
	
	
	/**
	 * Deve gerar exceptin
	 * @throws UsuarioJaExisteException 
	 * */
	@Test
	public void geraExcecaoUsuarioJahCadastradoException() throws UsuarioJaExisteException {
		SisMercado sis = new SisMercadoLCC();
		
		sis.cadastrarUsuario(new Usuario("Maria Madalena", "mlena@gm"));
		
		sis.cadastrarUsuario(new Usuario("Joelson Marionho", "joenson@gm"));
		
		sis.cadastrarUsuario(new Usuario("Mairicio Vieira", "ma_cio@gm"));
		
		Assertions.assertThrows(UsuarioJaExisteException.class,
				() -> sis.cadastrarUsuario(new Usuario("Maria Madalena", "mlena@gm")));

	}
	
	
	/**
	 * Deve gerar exceptin
	 * ClienteJaExisteException 
	 * @throws ClienteJaExisteException 
	 * */
	@Test
	public void geraExcecaoClienteJahCadastradoException() throws ClienteJaExisteException {
		SisMercado sis = new SisMercadoLCC();
		
		sis.cadastrarCliente(new ClientePF("Marcelo", "992819291"));
		sis.cadastrarCliente(new ClientePF("Amando Costa", "001912"));
		sis.cadastrarCliente(new ClientePF("Zeze de Camarco", "992819291"));
		
		Assertions.assertThrows(ClienteJaExisteException.class,
				() -> sis.cadastrarCliente(new ClientePF("Amando Costa", "001912")));

	}
	
	
	@Test
	public void gravarDadosClientePJ() throws IOException {
		List<ClientePJ> l = new ArrayList<ClientePJ>();
		try {
			for (int i = 0; i < 1; i++) {
				l.add(new ClientePJ("Hercules", "12345"));
			}
			
			gravadoraDeDados = new GravadoraDeDados();
			gravadoraDeDados.gravaClientesPJ(l);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	@Test
	public void gravarDadosClientePF() throws IOException {
		gravadoraDeDados = new GravadoraDeDados();
		List<ClientePF> l = new ArrayList<ClientePF>();
		try {
			
			for (int i = 0; i < 100; i++) {
				l.add(new ClientePF("Hercules", "1234"));
			}
			gravadoraDeDados.gravaClientesPF(l);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	@Test
	public void leituraDeDadosDosClientesPJePF() throws IOException {
		gravadoraDeDados = new GravadoraDeDados();
		String[] str =gravadoraDeDados.formataString("Hercules;1234;Hercules1234");
		System.out.println(str[0]);
		
		
		List<Cliente> l = gravadoraDeDados.recuperarClientes();
		
		for (Cliente cliente : l) {
			System.out.println(cliente.toString());
		}
		assertEquals(101, l.size());
		
		
	}
	
	
	@Test
	public void test() {
		Cliente cliente = new ClientePF("Hercules", "123");
		Cliente clienteJ = new ClientePJ("Hercules", "123");
		System.out.println(cliente.getClass().equals(ClientePF.class));
		System.out.println(clienteJ.getClass().equals(ClientePJ.class));
		
		
	}
	
	@Test
	public void gravaUsuarios() throws IOException {
		gravadoraDeDados = new GravadoraDeDados();
		
		Usuario u1 = new Usuario("Hercules Silva", "h@gmail", "123");
		Usuario u2 = new Usuario("Oilzon Nascimento", "o@gmail", "123");
		Usuario u3 = new Usuario("Mariana Google da Silva", "m@gmail", "123");
		
		List<Usuario> l = new ArrayList<Usuario>();
		l.add(u1);
		l.add(u2);
		l.add(u3);
		
		gravadoraDeDados.gravaUsuarios(l);
		
	}
	
	@Test
	public void recuperaUsuarios() throws IOException {
		gravadoraDeDados = new GravadoraDeDados();
		Usuario u1 = new Usuario("Hercules Silva", "h@gmail", "123");
		Usuario u2 = new Usuario("Oilzon Nascimento", "o@gmail", "123");
		Usuario u3 = new Usuario("Mariana Google da Silva", "m@gmail", "123");
		
		List<Usuario> l = new ArrayList<Usuario>();
		l.add(u1);
		l.add(u2);
		l.add(u3);
		
		gravadoraDeDados.gravaUsuarios(l);
		
		
		
		List<Usuario> listaUsuario = gravadoraDeDados.recuperarUsuarios();
		for (Usuario usuario : listaUsuario) {
			System.out.println(usuario.toString());
		}
		
	}
	
	
	
	
	
}
