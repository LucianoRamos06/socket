package com.ufra.socket.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static ServerSocket welcomeSocket;

	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		
		welcomeSocket = new ServerSocket(6789);
		
		while (true) {
			System.out.println("Servidor TCP ouvindo...");
			//Aceitando conex�es de clientes.
			Socket connectionSocket = welcomeSocket.accept();
			
			//Lendo dados recebidos.
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			//Abrindo canal de comunica��o para escrita no socket.
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
			//A resposta ser� a mesma mensagem, por�m captalizada.
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}
}