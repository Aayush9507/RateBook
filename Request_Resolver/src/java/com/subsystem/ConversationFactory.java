package com.subsystem;

import com.message.ACKMessage;
import com.message.CreateProdMessage;
import com.message.FollowProdMessage;
import com.message.LoginUserMessage;
import com.message.Message;
import com.message.Message.MessageType;
import com.message.RateFeedMessage;
import com.message.RateProdMessage;
import com.message.RegisterUserMessage;
import com.message.SearchProductMessage;
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

    public Envelope CreateEnvelopee(ArrayList<String> msglist) throws IOException {
        System.out.println("Creating Envelope using factory");
        Message msg;

        EnvelopeFactory ef = new EnvelopeFactory();
        msg = ef.EnvelopeFactory(msglist);
        Envelope env = new Envelope(msg, new InetSocketAddress("localhost", 8082));
        return env;
    }

    public class EnvelopeFactory {

        public UUID uuid = UUID.randomUUID();

        public Message EnvelopeFactory(ArrayList<String> mylist) {

            String pos = mylist.get(0);

            if (pos.equals("RegisterUser")) {
                return new RegisterUserMessage(uuid, mylist.get(1), mylist.get(2), mylist.get(3), mylist.get(4), mylist.get(5));
            }

            if (pos.equals("LoginUser")) {
                return new LoginUserMessage(uuid, mylist.get(1), mylist.get(2));
            }

            if (pos.equals("CreateProd")) {
                return new CreateProdMessage(uuid, mylist.get(1), mylist.get(2), mylist.get(3), Short.valueOf(mylist.get(4)));
            }

            if (pos.equals("FollowProd")) {
                return new FollowProdMessage(uuid, mylist.get(1), mylist.get(2));
            }
            if (pos.equals("RateProd")) {
                return new RateProdMessage(uuid, mylist.get(1), Float.valueOf(mylist.get(2)), mylist.get(3));
            }
            if (pos.equals("RateFeed")) {
                return new RateFeedMessage(uuid, mylist.get(1), Short.valueOf(mylist.get(2)), mylist.get(3), mylist.get(4));
            }
            if (pos.equals("SearchProd")) {
                return new SearchProductMessage(uuid, mylist.get(1));
            }
            if (pos.equals("ACKMessage")) {
                return new ACKMessage(uuid);
            }
            return null;
        }
    }
}
