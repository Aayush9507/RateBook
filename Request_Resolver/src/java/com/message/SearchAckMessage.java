package com.message;

import java.io.IOException;
import java.util.UUID;

public class SearchAckMessage extends Message {
    private String searchOutput;
    
    public SearchAckMessage(UUID uuid, String output) {
        super(MessageType.SearchAck, uuid);
        this.searchOutput = output;
    }

    public String getSearchOutput() {
        return searchOutput;
    }

    public void setSearchOutput(String searchOutput) {
        this.searchOutput = searchOutput;
    }

    @Override
    public byte[] encode() throws IOException {
        return new Encoder()
                .encodeMessageType(this.messageType)
                .encodeString(this.searchOutput)
                .encodeUUID(this.getConversationId())
                .toByteArray();
    }

    public static SearchAckMessage decode(byte[] messageBytes) {
                
        Decoder decoder = new Message.Decoder(messageBytes);

        if (decoder.decodeMessageType() != MessageType.SearchAck) {
            throw new IllegalArgumentException();
        }
       
        String test = decoder.decodeString();
        System.out.println("Response Received: "+test);
        UUID uuid = decoder.decodeUUID();
        return new SearchAckMessage(uuid, test);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
