package com.mercado.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;
import com.mercado.model.Usuario;

public class GravadoraDeDados {
	private final String SEPARADOR = ";";
	private final String CLIENTEPJ = "clientesPJ.txt";
	private final String CLIENTEPF = "clientesPF.txt";
	private final String USUARIO = "usuarios.txt";
	
	public void gravaClientesPJ(List<ClientePJ> clientesPJ) throws IOException {
		gravarDados(null, clientesPJ, null);
	}
	
	public void gravaClientesPF(List<ClientePF> clientesPF) throws IOException {
		gravarDados(clientesPF, null, null);
	}
	
	public void gravaUsuarios(List<Usuario> usuarios) throws IOException {
		gravarDados(null, null, usuarios);
	}
	
	
	private void gravarDados(List<ClientePF> clientesPF, List<ClientePJ> clientesPJ, List<Usuario> usuarios) throws IOException {
		String nomeArquivo = "";
		String dadosAEscrever = "";
		
		if (clientesPJ != null) {
			nomeArquivo = CLIENTEPJ;
			
			for (ClientePJ cli : clientesPJ) {
				dadosAEscrever+= new StringBuilder(cli.getNome()).append(SEPARADOR).append(cli.getCNPJ())
						.append(SEPARADOR).append(cli.getCodigo()).append("\n").toString();
			}

		} else if (clientesPF != null) {
			nomeArquivo = CLIENTEPF;
			
			for (ClientePF cli : clientesPF) {
				dadosAEscrever+=new StringBuilder(cli.getNome()).append(SEPARADOR).append(cli.getCPF())
						.append(SEPARADOR).append(cli.getCodigo()).append("\n").toString();
			}
		
		} else {
			nomeArquivo = USUARIO;
			
			for (Usuario usua : usuarios) {
				dadosAEscrever+=new StringBuilder(usua.getNome()).append(SEPARADOR).append(usua.getLogin())
						.append(SEPARADOR).append(usua.getSenha()).append("\n").toString();	
			}
		}
		
		
		// pegar arquivo
		File arquivo = new File(nomeArquivo);
		if (!arquivo.exists()) {
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// inicializando classe para escrever no arquivo
		BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

		// texto a ser escrito
		bw.write(dadosAEscrever);
		
		// fechando arquivo informando ao sistema que nao será mais utilizado
		bw.close();
	}
	
//	@Deprecated
//	private List<Cliente> recuperarAntesDeGravar(String nomeArquivo) throws IOException {
//		List<Cliente> l = new ArrayList<Cliente>();
//		if (nomeArquivo.equals(CLIENTEPF)) {
//			l = pegarDadosComBufferedReader(CLIENTEPF);
//			return l;
//		} 
//		
//		return pegarDadosComBufferedReader(CLIENTEPJ);
//	}
	
	public List<Cliente> recuperarClientes() throws IOException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		
		listaClientes = pegarDadosComBufferedReader(CLIENTEPF);
		
		listaClientes.addAll(pegarDadosComBufferedReader(CLIENTEPJ));
		
		return listaClientes;
		
	}
	
	private List<Cliente> pegarDadosComBufferedReader(String nomeArquivo) throws IOException {
		List<Cliente> lista = new ArrayList<Cliente>();
		
		File arquivo = new File(nomeArquivo);
		if(!arquivo.exists()) {
			return lista;
		}
		FileReader fr = new FileReader(arquivo);
		
		BufferedReader br = new BufferedReader(fr);
		
		
		
		
		// enquanto houver mais linhas
		while(br.ready()) {
			String linha = br.readLine();
			Cliente cliente;
			
			String[] str = formataString(linha);
			if(CLIENTEPF.equals(nomeArquivo)) {
				cliente = new ClientePF(str[0], str[1]);
			} else {
				cliente = new ClientePJ(str[0], str[1]);
			}
			lista.add(cliente);
		}
		
		// fechando
		fr.close();
		br.close();
		return lista;
	}
	
	
	
	
	public String[] formataString(String linha) {
		// exemplo:
		// Leandro Franklin;1234;Leandro Franklin1234
		return linha.split(SEPARADOR);
		
	}
	
	
	
	public List<Usuario> recuperarUsuarios() throws IOException {
		
		return pegarDadosDosUsuariosComBufferedReader(USUARIO);
		
	}
	
	private List<Usuario> pegarDadosDosUsuariosComBufferedReader(String nomeArquivo) throws IOException {
		List<Usuario> lista = new ArrayList<Usuario>();
		
		File arquivo = new File(nomeArquivo);
		if(!arquivo.exists()) {
			return lista;
		}
		FileReader fr = new FileReader(arquivo);
		
		BufferedReader br = new BufferedReader(fr);
		
		// enquanto houver mais linhas
		while(br.ready()) {
			String linha = br.readLine();
			String[] str = formataString(linha);
			Usuario usuario = new Usuario(str[0], str[1], str[2]);
			
			lista.add(usuario);
		}
		
		// fechando
		fr.close();
		br.close();
		return lista;
	}
	
	
	
	
}
