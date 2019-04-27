package com.subsystem;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import com.message.Message;
import com.message.RegisterUserMessage;
import com.subsystem.Dispatcher;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UDPComm extends Dispatcher implements Callable {

    private DatagramChannel datagramChannel = null;
    Queue receiveEnvelopeQueue = new ConcurrentLinkedQueue();
    private InetAddress IPAddress;

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
    }

    public void start() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
    }

    public boolean send(Envelope outgoingEnvelope) throws IOException {
        System.out.println("Searching Server Resolver UDP Comm");
        InetAddress hostIP = InetAddress.getLocalHost();
//        InetSocketAddress myAddress = new InetSocketAddress("10.0.0.54", 8089);
        InetSocketAddress myAddress = new InetSocketAddress(hostIP, 8086);
        System.out.println("myAddress" + myAddress);
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        System.out.println("datagramChannel" + outgoingEnvelope.getMessage());
//        System.out.println("string"+outgoingEnvelope.getMessage().getOutput());
        byte[] messageBytes = outgoingEnvelope.getMessage().encode();
        datagramChannel.send(ByteBuffer.wrap(messageBytes), myAddress);
        System.out.println("length: "+messageBytes.length);
        System.out.println("Sending request Via Search n create UDP");
        return true;
    }

    public Envelope receive() throws IOException {
        System.out.println("receiving.....");
        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 8089);
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
            return new Envelope(Message.decode(messageBytes), sourceSocketAddress);
        }
    }

    public void stop() throws IOException {
        datagramChannel.close();
    }

    @Override
    public Envelope call() throws Exception {
        Envelope en = null;
        try {
            System.out.println("Inside run method");
            en = receive();
            
            System.out.println(".........................." + en.getMessage());
            receiveEnvelopeQueue.add(en);
            System.out.println("Added env in queue");
            dispatch(en);

        } catch (Exception e) {

        }
        return en;
    }

//    @Override
//    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
//    public Envelope getEnvelope() {
//        // return message from queue with timeout
////        System.out.println("in get env");
////        System.out.println(receiveEnvelopeQueue.peek().getClass().getName());
//        Envelope env = (Envelope) receiveEnvelopeQueue.peek();
//        return env;
//    }
