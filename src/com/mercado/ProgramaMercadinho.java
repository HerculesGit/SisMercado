package com.mercado;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Usuario;
import com.mercado.sistema.ClienteJaExisteException;
import com.mercado.sistema.SisMercado;
import com.mercado.sistema.SisMercadoLCC;
import com.mercado.sistema.UsuarioJaExisteException;

public class ProgramaMercadinho {
	public static void main(String[] args) {
		
		//	Inicio
		SisMercado sistema = new SisMercadoLCC();
		
		
		Usuario usuario = null;
		
		boolean logado = false;
		boolean continuar = true;
		String msgErrorWindowsPlatform = "Ops! Agora deu errado!\n Por favor, entre em contato com o desenvolvedor para solucionar o problema\n\n"
				+ ""
				+ "Possível erro: \n"
				+ "1- Você está usando Windows - Já estamos resolvendo esse problema";
		
		// carregar dados
		try {
			sistema.recuperarDados();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					msgErrorWindowsPlatform,
					"Erro ao carregar dados", JOptionPane.ERROR_MESSAGE);
			
			int resposta = JOptionPane.showConfirmDialog(null, 
					"Deseja iniciar a aplicação após o erro?", "", JOptionPane.QUESTION_MESSAGE);
		
			if (resposta != JOptionPane.YES_OPTION) {
				continuar = false;
			}
		}
		
		
		while(continuar) {
			
			if (logado) {
				String opcaoUsuario= opcoesUsuarioScreen(usuario.getLogin());
				if (opcaoUsuario == null) {
					JOptionPane.showMessageDialog(null, "Opção inválida");
				} else if (opcaoUsuario.equals("0")){
					logado = false;
				}else {
					dashboardScreen(sistema, opcaoUsuario, usuario.getLogin());
				}
				
			} else { // Logar ou cadastrar 
				
				String cadOuLogin = JOptionPane.showInputDialog(null, 
						"Selecione uma das opções abaixo:\n\n[1]-cadastrar [2]-fazer login\n", "<SisMercado>", 
						JOptionPane.PLAIN_MESSAGE);
				
				if (cadOuLogin == null) {
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "Sair?")) {
						logado = false;
						continuar = false;
						
						try {
							sistema.gravarDados();
							JOptionPane.showMessageDialog(null, "Suas inclusões e/ou alterações foram realizadas com sucesso!");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, msgErrorWindowsPlatform, 
									"Erro ao salvar informações", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (cadOuLogin.equals("1")) {
					
					String nome = JOptionPane.showInputDialog("Informe o nome");
					String login = JOptionPane.showInputDialog("Informe um email para logar");
					String senha = JOptionPane.showInputDialog("Informe a senha");
					
					if (nome != null && login != null && senha != null) {
						
						if (nome.length()>0 || login.length()>0 || senha.length()>0) {
							usuario = new Usuario(nome, login, senha);
							
							try {
								sistema.cadastrarUsuario(usuario);
								JOptionPane.showMessageDialog(null, usuario.getNome()+" cadastrado com sucesso!");
							} catch (UsuarioJaExisteException e) {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", 
										JOptionPane.ERROR_MESSAGE);
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "Ops! Alguns campus ficaram em branco", "Erro", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (cadOuLogin.equals("2")) {
					// logar
					String login = JOptionPane.showInputDialog("Informe o login");
					String senha = JOptionPane.showInputDialog("Informe a senha");
					if (login != null && senha != null) {
						if(sistema.verificarLogin(login, senha)) {
							// Mostrar opções
							logado = true;
							JOptionPane.showMessageDialog(null, "Ok! Logado com sucesso!", "", 
									JOptionPane.PLAIN_MESSAGE);						
							
							String opcaoUsuario;
							if (usuario != null) { // como ele se logou nao teve como dar um 'new' no usuario pra pegar o login com usuario.getLogin()
								
								opcaoUsuario= opcoesUsuarioScreen(usuario.getLogin());
							} else {
								opcaoUsuario= opcoesUsuarioScreen(login);
							}
							if (opcaoUsuario == null || opcaoUsuario.equals("0")) {
								logado = false;
							} else {
								dashboardScreen(sistema, opcaoUsuario, usuario.getLogin());
							}
							
							
						} else {
							JOptionPane.showMessageDialog(null, "Login ou senha incorreto!!!", "Erro", 
									JOptionPane.ERROR_MESSAGE);
						}
					} 
				} else {
					JOptionPane.showMessageDialog(null, "\n Informe 1 para Cadastrar ou 2 para Logar no sistema", "Opção inválida!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
			
		} // end-while
		
		
	}

	private static String opcoesUsuarioScreen(String login) {
		// 1- Cadastrar Cliente (PF ou PJ)
		// 2- Obter lista dos clientes
		// 3- Pesquisa usuarios com nome comecando com...
		String escolhaUsuario = JOptionPane.showInputDialog(null, 
				"Informe um número correspondente a ação desejada\n\n" +
						"1- Cadastrar Cliente(PF PJ)\n" + 
						"2- Obter Lista dos Clientes\n" + 
						"3- Pesquisar usuário\n"+
						"0 - Voltar", "<SisMercado>",
				JOptionPane.PLAIN_MESSAGE);
		return escolhaUsuario;
	}
	
	/**
	 * Tela do usuário já logado no sistema
	 * */
	private static void dashboardScreen(SisMercado sistema, String escolhaDoUsuario, String login) {
		switch (escolhaDoUsuario) {
		case "1":
			boolean cadastrar = true;
			String cpfOuCnpj = JOptionPane.showInputDialog(null,
					"1-Pessoa Física\n" + "2-Pessoa Jurídica\n" + "0 - Voltar\n", "<SisMercado> Usuário[" + login + "]",
					JOptionPane.PLAIN_MESSAGE);
			Cliente cliente = null;
			
			// para nao gerar erro
			if (cpfOuCnpj == null) {
				return;
			}

			if (cpfOuCnpj.equals("1")) {
				String nome = JOptionPane.showInputDialog("Nome");
				String CPF = JOptionPane.showInputDialog("CPF");

				if ((nome !=null && CPF!=null) && (!nome.isEmpty()) || !CPF.isEmpty()) {
					cliente = new ClientePF(nome, CPF);
				} else {cadastrar = false;}
				

			} else if (cpfOuCnpj.equals("2")) {
				String nome = JOptionPane.showInputDialog("Nome");
				String CNPJ = JOptionPane.showInputDialog("CNPJ");
				
				if ((nome !=null && CNPJ!=null) && (!nome.isEmpty() || !CNPJ.isEmpty())) {
					cliente = new ClientePJ(nome, CNPJ);
				} else {cadastrar = false;}


			} else {
				JOptionPane.showMessageDialog(null, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
				cadastrar = false;
			}

			if (cadastrar) {
				try {
					sistema.cadastrarCliente(cliente);
					JOptionPane.showMessageDialog(null, cliente.getNome()+" cadastrado com sucesso!");
				} catch (ClienteJaExisteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;

		case "2": // 2- Obter lista dos clientes

			List<Cliente> lista = sistema.obterListaDeClientes();
			if (lista.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Lista de cliente vazia :O ");
			} else {
				JOptionPane.showMessageDialog(null, formatarListaClienteOuUsuario(lista, null), "Lista",
						JOptionPane.PLAIN_MESSAGE);
			}
			break;

		case "3": // 3- Pesquisa usuarios com nome comecando com...

			String prefixo = JOptionPane.showInputDialog(null,
					"Informe o prefixo que achamos os usuários correspondentes", "Pesquisar Usuários",
					JOptionPane.PLAIN_MESSAGE);

			JOptionPane.showMessageDialog(null,
					formatarListaClienteOuUsuario(null, sistema.pesquisaUsuariosComNomeComecandoCom(prefixo)), "Lista",
					JOptionPane.PLAIN_MESSAGE);
			break;
			
			
		default:
			break;
		}
	}
	
	
	
	private static String formatarListaClienteOuUsuario(List<Cliente> listaCliente , List<Usuario> listaUsuario) {
		String str = "";
		int indice = 0;
		if (listaCliente != null){
			for(Cliente c: listaCliente) {
				str+= (indice+1) + "- " + c.toString() + "\n";
			}
		} else {
			for(Usuario u: listaUsuario) {
				str+= (indice+1) + "- " + u.toString() + "\n";
			}
		}
		
		return str;
	}
	
	
}
