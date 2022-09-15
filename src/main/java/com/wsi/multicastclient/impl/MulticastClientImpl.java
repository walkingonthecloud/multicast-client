package com.wsi.multicastclient.impl;

import com.wsi.multicastclient.api.MulticastClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

@Component
@Slf4j
public class MulticastClientImpl implements MulticastClient, CommandLineRunner {

    public MulticastClientImpl() {}

    @Override
    public void receiveMessage() throws IOException {
        byte[] buffer=new byte[1024];
        int port = 6500;
        MulticastSocket socket=new MulticastSocket(port);
        String IP = "230.0.0.0";
        InetAddress group = InetAddress.getByName(IP);
        socket.joinGroup(group);
        while(true){
            System.out.println("Waiting for multicast message...");
            DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(),packet.getLength());
            System.out.println("[Multicast UDP message received] >> " + msg);
            if("OK".equals(msg)) {
                System.out.println("No more message. Exiting : " + msg);
                break;
            }
        }
        socket.leaveGroup(group);
        socket.close();
    }

    @Override
    public void run(String... args) throws Exception {
        this.receiveMessage();
    }
}
