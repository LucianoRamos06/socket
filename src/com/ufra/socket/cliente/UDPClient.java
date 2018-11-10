package com.ufra.socket.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		while(true){
			System.out.println("Cliente preparado para enviar: ");
			//L� entrada do usu�rio
			String sentence = inFromUser.readLine();
			sendData = sentence.getBytes();
			//Cria pacote udp
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, 9876);
			//envia ao servidor
			clientSocket.send(sendPacket);
			//Recebe resposta do servidor
			DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("Recebido do servidor UDP:" + modifiedSentence);
			//Fecha conex�o: clientSocket.close();
		}
	}
}