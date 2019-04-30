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

public class GravadoraDeDados {
	private final String SEPARADOR = ";";
	private final String CLIENTEPJ = "clientesPJ.txt";
	private final String CLIENTEPF = "clientesPF.txt";
	
	public void gravaClientesPJ(ClientePJ clientePJ) throws IOException {
		gravarDados(null, clientePJ);
		
	}
	
	public void gravaClientesPF(ClientePF clientePF) throws IOException {
		gravarDados(clientePF, null);
	}
	
	private void gravarDados(ClientePF clientePF, ClientePJ clientePJ) throws IOException {
		String nomeArquivo = "";
		String dadosAEscrever = "";
		List<Cliente> listaRecuperados;
		
		if (clientePJ != null) {
			nomeArquivo = CLIENTEPJ;
			
			listaRecuperados = recuperarAntesDeGravar(nomeArquivo);
			
			for (int i = 0; i < listaRecuperados.size(); i++) {
				ClientePJ cli  = (ClientePJ) listaRecuperados.get(i);
				dadosAEscrever+= new StringBuilder(cli.getNome()).append(SEPARADOR).append(cli.getCNPJ())
						.append(SEPARADOR).append(cli.getCodigo()).append("\n").toString();
			}
			
			dadosAEscrever+= new StringBuilder(clientePJ.getNome()).append(SEPARADOR).append(clientePJ.getCNPJ())
					.append(SEPARADOR).append(clientePJ.getCodigo()).toString();

		} else {
			nomeArquivo = CLIENTEPF;

			listaRecuperados = recuperarAntesDeGravar(nomeArquivo);
			
			// Código muito parecido... talvez possa refatorar
			for (int i = 0; i < listaRecuperados.size(); i++) {
				ClientePF cli = (ClientePF) listaRecuperados.get(i);
				dadosAEscrever+=new StringBuilder(cli.getNome()).append(SEPARADOR).append(cli.getCPF())
						.append(SEPARADOR).append(cli.getCodigo()).append("\n").toString();
			}
			
			dadosAEscrever+= new StringBuilder(clientePF.getNome()).append(SEPARADOR).append(clientePF.getCPF())
					.append(SEPARADOR).append(clientePF.getCodigo()).toString();
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

		// criando uma nova linha para ficar pronto para o próximo cliente
		bw.newLine();

		// fechando arquivo informando ao sistema que nao será mais utilizado
		bw.close();
	}
	
	private List<Cliente> recuperarAntesDeGravar(String nomeArquivo) throws IOException {
		List<Cliente> l = new ArrayList<Cliente>();
		if (nomeArquivo.equals(CLIENTEPF)) {
			l = pegarDadosComBufferedReader(CLIENTEPF);
			return l;
		} 
		
		return pegarDadosComBufferedReader(CLIENTEPJ);
	}
	
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
	
	
	public void recuperarUsuarios() {
		
	}
	
	
	
	
}
