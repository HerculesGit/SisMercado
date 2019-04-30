package com.mercado.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.mercado.model.Cliente;
import com.mercado.model.ClientePF;
import com.mercado.model.ClientePJ;

public class GravadoraDeDados {
	
	public void gravaClientesPJ(ClientePJ clientePJ) throws IOException {
		gravarDados(null, clientePJ);
		
	}
	
	public void gravaClientesPF(ClientePF clientePF) throws IOException {
		gravarDados(clientePF, null);
	}
	
	private void gravarDados(ClientePF clientePF, ClientePJ clientePJ) throws IOException {
		String nomeArquivo = "";
		String dadosAEscrever = "";
		
		if (clientePJ != null) {
			nomeArquivo = "clientesPJ.txt";
			dadosAEscrever = clientePJ.getNome() + "|" + clientePJ.getCNPJ() + "|" + clientePJ.getCodigo();
		} else {
			nomeArquivo = "clientesPF.txt";
			dadosAEscrever = clientePF.getNome() + "|" + clientePF.getCPF() + "|" + clientePF.getCodigo();
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
	
	
	public List<Cliente> recuperarClientes() {
		
		return null;
		
	}
	
	public void recuperarUsuarios() {
		
	}
	
	
	
	
}
