package com.ufal.br.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ufal.br.model.Consulta;
import com.ufal.br.model.Horario;
import com.ufal.br.model.Threadex;

public class Servidor {
	
	private ServerSocket serverSocket;

	private void criarServerSocket(int porta) throws IOException{
		serverSocket = new ServerSocket(porta);
	}
	
	private Socket esperaConexao() throws IOException {
		Socket socket = serverSocket.accept();
		return socket;
	}
	
	private void fecharSocket(Socket socket) throws IOException {
		socket.close();
	}
	
	private void tratarConexao(Socket socket) throws IOException, ClassNotFoundException {
		try {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			Horario h1 = new Horario();
			Horario h2 = new Horario();
			Horario h3 = new Horario();
			
			while(true) {
				Consulta consulta = (Consulta) input.readObject();
				Threadex t = new Threadex(h1,h2,h3, consulta);
				t.start();			
				}
			
		}catch(IOException e) {
			System.out.println("Problema no tratamento da conex�o");
			System.out.println("Erro: " + e.getMessage());
		}finally {
			fecharSocket(socket);
		}
	}
	
	public static void main(String[] args) {
		try {
			Servidor servidor = new Servidor();
			System.out.println("Aguardando Conex�o");
			servidor.criarServerSocket(5555);
			Socket socket = servidor.esperaConexao();
			System.out.println("Cliente Conectado");
			servidor.tratarConexao(socket);
			System.out.println("Cliente Finalizado");
		} catch (IOException e) {
			System.out.println("Erro no servidor: " + e.getMessage());
		}catch (ClassNotFoundException e) {
			System.out.println("Erro no cast" + e.getMessage());
		}
		
	}
}
