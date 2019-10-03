package com.subsystem;

import com.message.Message;
import com.subsystem.Encryption;
import com.subsystem.Decryption;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;


import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;

public class UDPComm implements Callable{

    private DatagramChannel datagramChannel;
    private static Cipher cipher = null;

    // TODO: define a queue
    Queue receiveEnvelopeQueue = new ConcurrentLinkedQueue();

    public UDPComm(DatagramChannel datagramChannel, InetSocketAddress address) throws IOException {
        super();
        this.datagramChannel = datagramChannel;
        if (address.getPort() != 0) {
            this.datagramChannel.bind(address);
        } else {
            this.datagramChannel.bind(null);
        }
    }

    public UDPComm() {
        System.out.println("default constructor");
    }

    public void start() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
    }
        
    public boolean send(Envelope outgoingEnvelope) throws IOException {
        System.out.println("Request Resolver UDP Comm");
        InetAddress hostIP = InetAddress.getLocalHost();
//        InetSocketAddress myAddress = new InetSocketAddress("10.0.0.54", 8089);
        InetSocketAddress myAddress = new InetSocketAddress(hostIP, 8089);
        System.out.println("myAddress" + myAddress);
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        System.out.println("datagramChannel" + outgoingEnvelope.getMessage());
        
        byte[] messageBytes = outgoingEnvelope.getMessage().encode();
        
        Encryption encrypter = new Encryption();
        byte[] encryptedBytes =  encrypter.encrypt(messageBytes);
        
        
        
        datagramChannel.send(ByteBuffer.wrap(encryptedBytes), myAddress);
        System.out.println("Sending request Via UDP");
        return true;
    }

    public Envelope receive() throws IOException {
        System.out.println("receiving.....");
        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 8086);
        DatagramChannel datagramChannel = DatagramChannel.open();
        DatagramSocket datagramSocket = datagramChannel.socket();
        datagramSocket.bind(address);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true) {
            InetSocketAddress sourceSocketAddress = (InetSocketAddress) datagramChannel.receive(buffer);
            byte[] messageBytes = Arrays.copyOf(buffer.array(), buffer.position());
            System.out.print("\nData...: " + messageBytes.length);
            buffer.clear();
//            datagramChannel.bind(null);
            System.out.println("Decoding received message");
            
            Decryption decrypter = new Decryption();
            byte[] decryptedBytes = decrypter.decrypt(messageBytes);
            
            return new Envelope(Message.decode(decryptedBytes), sourceSocketAddress);
        }
    }

    public void stop() throws IOException {
        datagramChannel.close();
    }

    public Envelope getEnvelope() {
        // return message from queue with timeout
        Envelope env = null;
        return env;
    }
    

    @Override
    public Object call() throws Exception {
        Envelope en = null;
        try {
            System.out.println("Inside run method");
            en = receive();
            System.out.println("Envelope received");
            receiveEnvelopeQueue.add(en);
            System.out.println("Added env in queue");
//            dispatch(en);

        } catch (Exception e) {

        }
        return en;
    }
}
