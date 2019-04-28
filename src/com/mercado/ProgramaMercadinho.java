package com.mercado;

import java.util.List;
import java.util.Scanner;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Usuario;
import com.mercado.sistema.SisMercado;
import com.mercado.sistema.SisMercadoLCC;

public class ProgramaMercadinho {
	public static void main(String[] args) {
		
		//	Inicio
		SisMercado sistema = new SisMercadoLCC();
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		
		
		boolean continuar = true;
		while(continuar) {
			
			System.out.print("Cadastrar ou fazer login?[1]-cadastrar [2]-fazer login \n>");
			int input = scan.nextInt();
			
			if (input == 1) {
				Usuario usuario;
				// cadastrar
				System.out.print("Informe o seu nome: ");
				String nome = scanString.nextLine();
				
				
				System.out.print("Informe um email para logar: ");
				String login = scanString.nextLine();
				
				System.out.print("Informe a senha: ");
				String senha = scanString.nextLine();
				
				if (nome.length()<1 || login.length()<1 || senha.length()<1) {
					
				}
				usuario = new Usuario(nome, login, senha);
				sistema.cadastrarUsuario(usuario);
				
			} else if (input == 2) {
				// logar
				System.out.print("Login: ");
				String login = scanString.nextLine();

				System.out.print("Senha: ");
				String senha = scanString.nextLine();
				
				if(sistema.verificarLogin(login, senha)) {
					// Mostrar opções
					
					System.out.println(" ==== "+ login +" === [logado]");
					// 1- Cadastrar Cliente (PF ou PJ)
					// 2- Obter lista dos clientes
					// 3- Pesquisa usuarios com nome comecando com...
					System.out.println("Digite um número correspondente a ação desejada"
							+ "1- Cadastrar Cliente(PF PJ)\n"
							+ "2- Obter Lista dos Clientes"
							+ "3- Pesquisar usuário\n >");
					input = scan.nextInt();
					switch(input) {
					case 1:
						
						Cliente cliente = null;
						int tempEscolha = input;
						System.out.print("1-Pessoa Física\n"
								+ "2-Pessoa Jurídica"
								+ "0 - Voltar\n >");
						
						input = scan.nextInt();
						if(input == 1) {
							System.out.print("Nome: ");
							String nome= scanString.nextLine();
							System.out.print("CPF :");
							String CPF = scanString.nextLine();
							cliente = new ClientePF(nome, CPF);
							
							
						} else if (input == 2) {
							System.out.print("Nome: ");
							String nome = scanString.nextLine();
							System.out.print("CNPJ: ");
							String CNPJ = scanString.nextLine();
							cliente = new ClientePJ(nome, CNPJ);
								
						} else {
							input = tempEscolha;
						}
						
						if (input > 0) {
							sistema.cadastrarCliente(cliente);
						}
						break;
						
					case 2:
						
						List<Cliente> lista = sistema.obterListaDeClientes();
						if(lista.isEmpty()) {
							System.out.println("Lista de cliente vazia (:O) ");
						} else {
							System.out.println(formatarListaCliente(lista));
						}
						break;
						
					case 3:
						System.out.print("Informe o prefixo para acharmos o usuário\n >");
						String prefixo = scanString.nextLine();
						sistema.pesquisaUsuariosComNomeComecandoCom(prefixo);
						break;
						
					default:
						break;
					}
					
				} else {
					System.out.println("Login ou senha incorretos!!!");
				}
				
				
			} else {
				System.out.println("Informe 1 para Cadastrar ou 2 para Logar no sistema\n >");
			}
			
		} // end-while
		
		scanString.close();
		scan.close();
		
	}

	private static String formatarListaCliente(List<Cliente> lista) {
		String str = "";
		int indice = 0;
		for(Cliente c: lista) {
			str+= (indice+1) + "- " + c.toString();
		}
		return str;
	}
}
