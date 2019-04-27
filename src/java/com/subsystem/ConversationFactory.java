package com.subsystem;
import com.message.ACKMessage;
import com.message.CreateProdMessage;
import com.message.Message;
import com.message.RateProdMessage;
import com.message.RegisterUserMessage;
import com.message.SearchAckMessage;
import com.message.SearchProductMessage;
import static com.subsystem.Conversation.PossibleState;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.UUID;

public class ConversationFactory {
    private HashMap<Message.MessageType, UUID> _typeMappings = new HashMap<Message.MessageType, UUID>(); // Doubt

    public CommSubSystem ManagingSubsystem;
    public int DefaultMaxRetries;
    public int DefaultTimeout;
    public void Initialize() {

    }

    public CommSubSystem getManagingSubsystem() {
        return ManagingSubsystem;
    }

    public void setManagingSubsystem(CommSubSystem managingSubsystem) {
        ManagingSubsystem = managingSubsystem;
    }

    public int getDefaultMaxRetries() {
        return DefaultMaxRetries;
    }

    public void setDefaultMaxRetries(int defaultMaxRetries) {
        DefaultMaxRetries = defaultMaxRetries;
    }

    public int getDefaultTimeout() {
        return DefaultTimeout;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        DefaultTimeout = defaultTimeout;
    }

    protected void Add(Message.MessageType msgType, UUID convId)
    {
        if (!_typeMappings.containsKey(msgType))
            _typeMappings.put(msgType, convId);
    }

    public Conversation CreateFromConversationType() {
        Conversation conv = new Conversation();
        conv.ConvId = conv.getConvId();
        conv.MaxRetries = 2;
        conv.State = Conversation.PossibleState.Working;
        conv.MaxRetries = 4;
        ConversationDictionary.addConversation(conv.ConvId,conv);
        return conv;
    }

    public boolean canIncomingMessageStartConversation(String messageType) {
        return _typeMappings.containsKey(messageType);
    }

    public  Conversation CreateFromEnvelope(Envelope envelope) {
        Conversation conversation = new Conversation();
        conversation.inetSocketAddress = envelope.inetSocketAddress;
        conversation.ConvId = UUID.randomUUID();
        conversation.setState(PossibleState.NotInitialized);
        return conversation;
        
        
        
//        Message.MessageType messageType = envelope.getMessage().getMessageType();
//        if (messageType != null && _typeMappings.get(messageType))
//            conversation = CreateResponderConversation(_typeMappings[messageType], envelope);

    }
    public Envelope CreateEnvelopee(ArrayList<String> msglist) throws IOException {
        System.out.println("Creating Envelope using factory"+msglist.get(0));
//        System.out.println("Creating Envelope using factory"+msglist.get(1).getClass().getName());
//        System.out.println("Creating Envelope using factory"+msglist.get(1));
        Message msg;

        EnvelopeFactory ef = new EnvelopeFactory();
        msg = ef.EnvelopeFactory(msglist);
        System.out.println("Got Message from Envelope factory");
        Envelope env = new Envelope(msg, new InetSocketAddress("localhost", 8089));
        return env;
    }
        public class EnvelopeFactory 
        {

        public UUID uuid = UUID.randomUUID();

        public Message EnvelopeFactory(ArrayList<String> mylist) {

            String pos = mylist.get(0);
            
            if (pos.equals("SearchAck")) {
//                System.out.println("Inside env factoryyyy"+mylist.get(1));
//                System.out.println("Inside env factoryyyy"+mylist.get(1).getClass().getName());
                return new SearchAckMessage(uuid, mylist.get(1));
            }
            if (pos.equals("ACKMessage")) {
                return new SearchProductMessage(uuid, mylist.get(0));
            }
            
            return null;
        }
    }

    public Conversation CreateResponderConversation(String s, Envelope envelope){
    return new Conversation();
    }


}
