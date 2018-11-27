package com.ufra.socket.servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class UDPServer {

    static String IV = "AAAAAAAAAAAAAAAA";
    static String chaveencriptacao = "0123456789abcdef";
    
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
            String sentence = UDPServer.decrypt(receivePacket.getData(), chaveencriptacao);
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

    public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado), "UTF-8");
    }
}
