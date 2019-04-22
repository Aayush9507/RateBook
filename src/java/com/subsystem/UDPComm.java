package com.subsystem;

import com.message.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;

public class UDPComm {

    private DatagramChannel datagramChannel;

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
        InetSocketAddress myAddress = new InetSocketAddress(hostIP, 8089);
        System.out.println("myAddress"+myAddress);
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        System.out.println("datagramChannel"+outgoingEnvelope.getMessage());
        byte [] messageBytes = outgoingEnvelope.getMessage().encode();
        System.out.println("Envelope Message class name"+outgoingEnvelope.getMessage().getClass().getName());
        System.out.println("outgoingEnvelope conversation Id"+outgoingEnvelope.getMessage().getConversationId());
        System.out.println("messageBytes"+messageBytes.length);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        String str = "Example String";
        datagramChannel.send(buffer.put(messageBytes), myAddress);
        System.out.println("Sending request Via UDP");
        return true;
    }

    public Envelope receive() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4098);
        buffer.clear();
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) datagramChannel.receive(buffer);
        byte[] messageBytes = Arrays.copyOf(buffer.array(), buffer.position());
        return new Envelope(Message.decode(messageBytes), sourceSocketAddress);
    }

    public void stop() throws IOException {
        datagramChannel.close();
    }

    public void run() {
        try {
            Envelope e = receive();
            // TODO: put into a queue
            receiveEnvelopeQueue.add(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Envelope getEnvelope() {
        // return message from queue with timeout
        Envelope env = null;
        return env;
    }
}
