package com.mercado.sistema;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Produto;
import com.mercado.model.Usuario;

class SisMercadoLCCTest {

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
	void pesquisaUsuariosComNomeComecandoComMari() {
		SisMercado sis = new SisMercadoLCC();
		sis.cadastrarUsuario(new Usuario("Maria Madalena", ""));
		sis.cadastrarUsuario(new Usuario("Joelson Marionho", ""));
		sis.cadastrarUsuario(new Usuario("Mariana Duarte", ""));
		sis.cadastrarUsuario(new Usuario("Manuel Pinto", ""));
		sis.cadastrarUsuario(new Usuario("Camila Pereira", ""));
		sis.cadastrarUsuario(new Usuario("João Azevedo", ""));
		
		for(Usuario u: sis.pesquisaUsuariosComNomeComecandoCom("Manuel")) {
			System.out.println(u.getNome());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
