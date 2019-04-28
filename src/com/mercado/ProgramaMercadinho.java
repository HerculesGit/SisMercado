package com.mercado;

import java.util.List;
import java.util.Scanner;

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
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		
		boolean logado = false;
		boolean continuar = true;
		while(continuar) {
			
			String cadOuLogin = JOptionPane.showInputDialog(null, 
					"Selecione uma das opções abaixo:\n\n[1]-cadastrar [2]-fazer login\n", "<SisMercado>", 
					JOptionPane.PLAIN_MESSAGE);
			if (logado) {
				
			}
			// CADASTRAR
			if (cadOuLogin == null) {
				if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "Sair?")) {
					continuar = false;
				}
			} else if (cadOuLogin.equals("1")) {
				Usuario usuario;
				String nome = JOptionPane.showInputDialog("Informe o nome");
				String login = JOptionPane.showInputDialog("Informe um email para logar");
				String senha = JOptionPane.showInputDialog("Informe a senha");
				
				if (nome.length()<1 || login.length()<1 || senha.length()<1) {
					JOptionPane.showMessageDialog(null, "Ops! Alguns campus ficaram em branco", "Erro", 
							JOptionPane.ERROR_MESSAGE);
				}
				usuario = new Usuario(nome, login, senha);
				
				try {
					sistema.cadastrarUsuario(usuario);
				} catch (UsuarioJaExisteException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", 
							JOptionPane.ERROR_MESSAGE);
				}
				
			} else if (cadOuLogin.equals("2")) {
				// logar
				
				String login = JOptionPane.showInputDialog("Informe a login");
				String senha = JOptionPane.showInputDialog("Informe a senha");
				
				if(sistema.verificarLogin(login, senha)) {
					// Mostrar opções
					
					
					JOptionPane.showMessageDialog(null, "Ok! Logado com sucesso!", "", 
							JOptionPane.PLAIN_MESSAGE);
					// 1- Cadastrar Cliente (PF ou PJ)
					// 2- Obter lista dos clientes
					// 3- Pesquisa usuarios com nome comecando com...
					String escolhaUsuario = JOptionPane.showInputDialog(null, 
							"Informe um número correspondente a ação desejada\n\n" +
									"1- Cadastrar Cliente(PF PJ)\n" + 
									"2- Obter Lista dos Clientes\n" + 
									"3- Pesquisar usuário\n", "<SisMercado> Usuário["+login+"]",
							JOptionPane.PLAIN_MESSAGE);
					
//					input = scan.nextInt();
					switch(escolhaUsuario) {
					case "1":
						boolean cadastrar = true;
						String cpfOuCnpj = JOptionPane.showInputDialog(null, 
								"1-Pessoa Física\n" + 
								"2-Pessoa Jurídica\n" + 
								"0 - Voltar\n", "<SisMercado> Usuário["+login+"]",
								JOptionPane.PLAIN_MESSAGE);
						Cliente cliente = null;
						
						if (cpfOuCnpj.equals("1")) {
							String nome = JOptionPane.showInputDialog("Nome");
							String CPF = JOptionPane.showInputDialog("CPF");
							
							cliente = new ClientePF(nome, CPF);

						} else if (cpfOuCnpj.equals("2")) {
							String nome = JOptionPane.showInputDialog("Nome");
							String CNPJ = JOptionPane.showInputDialog("CNPJ");
							
							cliente = new ClientePJ(nome, CNPJ);
								
						} else {
							JOptionPane.showMessageDialog(null, "Opção inválida", "Erro", JOptionPane.ERROR_MESSAGE);
							cadastrar = false;
						}
						
						if (cadastrar) {
							try {
								sistema.cadastrarCliente(cliente);
							} catch (ClienteJaExisteException e) {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao cadastrar",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						break;
						
					case "2": //2- Obter lista dos clientes
						
						List<Cliente> lista = sistema.obterListaDeClientes();
						if(lista.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Lista de cliente vazia :O ");
						} else {
							JOptionPane.showMessageDialog(null, formatarListaCliente(lista), "Lista",
									JOptionPane.PLAIN_MESSAGE);
						}
						break;
						
					case "3": // 3- Pesquisa usuarios com nome comecando com...
						
						String prefixo = JOptionPane.showInputDialog(null,
								"Informe o prefixo que achamos os usuários correspondentes", "Pesquisar Usuários",
								JOptionPane.PLAIN_MESSAGE);

						JOptionPane.showMessageDialog(null,
								formatarListaUsuarios(sistema.pesquisaUsuariosComNomeComecandoCom(prefixo)), "Lista",
								JOptionPane.PLAIN_MESSAGE);
						break;

					default:
						break;
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Login ou senha incorreto!!!", "Erro", 
							JOptionPane.ERROR_MESSAGE);
				}
				
				
			} else {
				JOptionPane.showMessageDialog(null, "\n Informe 1 para Cadastrar ou 2 para Logar no sistema", "Opção inválida!", JOptionPane.ERROR_MESSAGE);
			}
			
		} // end-while
		
		scanString.close();
		scan.close();
		
	}

	/**
	 * Tela do usuário já logado no sistema
	 * */
	private static void dashboard(String escolhaDoUsuario) {
		
	}
	
	private static String formatarListaCliente(List<Cliente> lista) {
		String str = "";
		int indice = 0;
		for(Cliente c: lista) {
			str+= (indice+1) + "- " + c.toString();
		}
		return str;
	}
	
	private static String formatarListaUsuarios(List<Usuario> lista) {
		String str = "";
		int indice = 0;
		for(Usuario u: lista) {
			str+= (indice+1) + "- " + u.toString();
		}
		return str;
	}
	
	
}
