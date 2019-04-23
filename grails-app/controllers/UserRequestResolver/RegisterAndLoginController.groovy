/**
 *
 * @author abhinavpandey
 */
package UserRequestResolver

import grails.converters.JSON
import groovy.sql.Sql
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import com.message.*;
import com.subsystem .*;

class RegisterAndLoginController {
    static allowedMethods =[register:"POST",login:"POST"]
    def index() { 
        print "in heree"
    }
    
    def register() { 
        println "Registering"
        CommSubSystem subsystemObj = new CommSubSystem()
        ConversationFactory cf = new ConversationFactory()
        println "cf"+cf
        def json = [status : 0, message : ""]

        try{
            // Sending Request to metadata server
            def msgType = params.msgType
            def conv = createConversation(cf)
            def convId = conv.getConvId()
            def env = createEnvelope(cf,msgType)
//            print "envelope created....."+env.getMessage().getClass().getName()
//            byte [] b = env.getMessage().getAreaOfInterest()
            System.out.println("No. of bytes after creating and encoding the object controller"+env.getMessage().conversationId);
            sendEnvelope(subsystemObj,env)

            // Receiving Response from the metadata server
            session['user'] = params.email
            json = [status : 1, message : "Success"]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def login() {
        def json = [status : 0, message : ""]
        def clientOut = getOutputStream()
        try{
            // Sending Request to server
            def messageOut = [];
            messageOut.add(params.msgType)
            messageOut.add(params.email)
            messageOut.add(params.pass)

            clientOut[1].writeObject(messageOut);

            // Receiving Response from the server
            def reponse = getInputStream(clientOut[0], clientOut[1])
            session['user'] = params.email
            json = [status : 1, message : reponse]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def createConversation(cf){
        print  "Creating Conversation"+cf
        return cf.CreateConversation()
    }
    
    def createEnvelope(cf,msgType){
        
        def msgList = []
        msgList.add("RegisterUser")
        msgList.add(params.userId)
        msgList.add(params.name)
        msgList.add(params.emailId)
        msgList.add(params.password)
        msgList.add(params.areaOfInterest)
        print "Creating Envelope inside RR controller..............."
        def res = cf.CreateEnvelopee(msgList)
        print "response"+res
        return res
    }
    
    def sendEnvelope(subsystemObj,Env){
        print "Sending Envelope"+subsystemObj
        //        print subsystemObj.Initialize()
        //        print subsystemObj.setUdpComm(new UDPComm())
        //        print "sendingggg"+subsystemObj.getUdpComm()
        UDPComm UDPCommobj = new UDPComm()
        UDPCommobj.send(Env)
    }
}

