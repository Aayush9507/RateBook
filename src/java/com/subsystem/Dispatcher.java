package com.subsystem;

public abstract class Dispatcher{
    UDPComm udpComm;
    ConversationFactory cf;
    private boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
    public void dispatch(Envelope env) {
        try {
            System.out.println("Inside dispatcher's dispatch method");

            System.out.println("Message Type in Dispatcher " + env.getMessage().getMessageType());
            Conversation c = ConversationDictionary.getConversation(env.getMessage().getConversationId());
            System.out.println("conv Id:" + c);
            
            if (c == null) 
            {   
                cf = new ConversationFactory();
                System.out.println("Inside If condition");
                c = cf.CreateFromEnvelope(env);
                ConversationDictionary.addConversation(env.getMessage().getConversationId(), c);
                System.out.println("Conv. added to dictionary");
                
            }
            else
            {
                    cf.CreateFromEnvelope(env);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return env.getMessage();
    }
}
