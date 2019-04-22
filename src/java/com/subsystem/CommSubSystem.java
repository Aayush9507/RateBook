package com.subsystem;
import java.io.IOException;
import java.util.HashMap;

public class CommSubSystem {
    private ConversationFactory convFact;
    private ConversationDictionary convDict;
    public UDPComm UdpComm;
    private static int currentPort = 8080;
    
    public ConversationFactory getConvFact() {
        return convFact;
    }

    public void setConvFact(ConversationFactory convFact) {
        this.convFact = convFact;
    }

    public ConversationDictionary getConvDict() {
        return convDict;
    }

    public UDPComm getUdpComm() {
        System.out.println("getUdpComm");
        return UdpComm;
    }

    public void setUdpComm(UDPComm udpComm) {
        System.out.println("settting UdpComm");
        UdpComm = udpComm;
    }
 
    public void Initialize() throws IOException {
        System.out.println("UDP Started");
        UdpComm.start();
    }

    public void stop(int flag) throws IOException {
        if (UdpComm != null)
        {
            UdpComm.stop();
            UdpComm = null;
        }
    }
}
