package com.ufal.br.cliente;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufal.br.model.Consulta;

public class Cliente {
	public Consulta agendarConsulta() {
		System.out.println("-----AGENDAR CONSULTA-------");
		Scanner ler = new Scanner(System.in);
		
		System.out.println("Digite o Seu nome: ");
		String nomePaciente = ler.next();
		
		System.out.println("Descreva a consulta: ");
		String descricao = ler.next();
		
		System.out.println("Id da consulta: ");
		int id = ler.nextInt();
		
		Consulta consulta = new Consulta(id,nomePaciente,descricao);
		
		return consulta;
	}
	
	public static void main(String[] args) {
		
		try{
			//criando o socket para rodar na máquina local(localhost) e na porta 5555
			Socket socket = new Socket("localhost", 5555);
			Cliente cliente = new Cliente();
			//instânciando o recebimento
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			//instânciando o envio
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			while(true) {
				Consulta consulta = cliente.agendarConsulta();
				output.writeObject(consulta);
				output.flush();
			}
			

		}catch(IOException e) {
			//tratando o erro
			System.out.println("Erro no cliente: " + e);
			Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}

}
