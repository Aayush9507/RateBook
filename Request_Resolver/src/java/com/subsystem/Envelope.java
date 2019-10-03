package com.subsystem;

import com.message.Message;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Envelope {
    private Message message;

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }
    private InetSocketAddress inetSocketAddress;

    public Envelope(Message message, InetSocketAddress inetSocketAddress) {
        this.message = message;
        this.inetSocketAddress = inetSocketAddress;
    }

    public Message getMessage() {
        return this.message;
    }

    public InetSocketAddress getSourceInetSocketAddress() {
        return this.inetSocketAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Envelope envelope = (Envelope) o;
        return Objects.equals(message, envelope.message) &&
                Objects.equals(inetSocketAddress, envelope.inetSocketAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, inetSocketAddress);
    }

    InetSocketAddress getSrcInetSocketAddress() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

