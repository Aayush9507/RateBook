package com.subsystem;

import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Conversation {

    String ConvId;
    String state;
    public int Timeout;
    public int MaxRetries;

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    public int getMaxRetries() {
        return MaxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        MaxRetries = maxRetries;
    }

    public String getConvId() {
        return ConvId;
    }

    public void setConvId(String convId) {
        ConvId = convId;
    }

    Conversation(String ConvId, String state, int Timeout, int MaxRetries) {
        this.ConvId = ConvId;
        this.state = state;
        this.Timeout = Timeout;
        this.MaxRetries = MaxRetries;
    }
}
