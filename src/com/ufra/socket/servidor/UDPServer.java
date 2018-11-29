package com.ufra.socket.servidor;

import com.ufra.socket.criptografia.Criptografia;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    
    public static void main(String args[]) throws Exception {
        //Cria um servidor UDP na porta 9876
        DatagramSocket serverSocket = new DatagramSocket(9876);
        //Sockets apenas enviam bytes
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            System.out.println("Servidor UDP ouvindo...");
            //Recebe as mensagens dos clientes
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            System.out.println("tamanho: " + receivePacket.getData().length);
            System.out.println("string: " + new String(receivePacket.getData()).trim().length());
            String sentence = Criptografia.decrypt(receivePacket.getData());
            System.out.println("Recebido: " + sentence);
            //Responde ao mesmo IP e Porta do pacote recebido.
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);

            for (int i = 0; i < receiveData.length; i++) {
                receiveData[i] = 0;
            }
        }
    }

  
}
