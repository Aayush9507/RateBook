package com.subsystem;

import com.message.Message;
import com.message.Message.MessageType;
import com.message.RegisterUserMessage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.UUID;

public class ConversationFactory {

    public Conversation CreateConversation() {
        
        Conversation conv = new Conversation(UUID.randomUUID().toString(), "Working", 4000, 2);
        ConversationDictionary.addConversation(conv);
        return conv;
    }

    public Envelope CreateEnvelopee(String convId,String userId, String Name, String emailId, String password, String areaOfInterest) throws IOException {
        System.out.println("Creating Envelope using factory");
        RegisterUserMessage rum;
        String msgId = UUID.randomUUID().toString();
        rum = new RegisterUserMessage(userId, Name, emailId, password, areaOfInterest);
        System.out.println("rummmmm"+rum.getAreaOfInterest()+rum.getName()+rum.getEmailId()+rum.getUserId()+rum.getPassword());
        Envelope env = new Envelope(rum, new InetSocketAddress("localhost", 8082));
//        byte [] b = rum.encode();
//        System.out.println("No. of bytes after creating and encoding the object"+b.length);
        return env;
    }
}