/**
 *
 * @author abhinavpandey
 */
package SearchAndCreateProduct

import com.subsystem.CommSubSystem
import com.subsystem.ConversationFactory
import com.subsystem.UDPComm
import grails.converters.JSON
import groovy.sql.Sql
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
class SearchAndCreateController {
    static allowedMethods =[search:"POST",create:"POST"]
    
    //    def beforeInterceptor = [action: this.&auth]
    //    def afterIntereceptor = [action: this.&auth]
    //
    //    private auth() {
    //        if(params.uid!='qwertyasdfzxcv007'){
    //            def json = [status : 0, message : "Unauthorized to use APIs"]
    //            render json as JSON
    //            return false
    //        }
    //    }
    def index() { 
    
    }
    
    def search() { 
        println params 
        def json = [status : 0, message : ""]

        try{
            println "Sending Request to searching server"
            CommSubSystem subsystemObj = new CommSubSystem()
            ConversationFactory cf = new ConversationFactory()
            def msgType = params.msgType
            def conv = createConversation(cf)
            def env = createEnvelope(cf,msgType)
            sendEnvelope(subsystemObj,env)

            json = [status : 1, message : "Search Response"]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def create() {
        println "create params"+params

        def json = [status : 0, message : ""]
        try{
            // Sending Request to server
            def messageOut = [];
            messageOut.add(params.msgType)
            messageOut.add(params.itemName)
            messageOut.add(params.itemType)
            messageOut.add(params.itemDesc)
            messageOut.add(params.itemFollowCount)
            messageOut.add(params.itemReviewCount)
            json = [status : 1, message : reponse]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def createConversation(cf){
        return cf.CreateConversation()
    }
    
    def createEnvelope(cf,msgType){
        def msgList = []
        
        if (msgType.equals("CreateProd")){
            msgList.add("CreateProd")
            msgList.add(params.userId)
            msgList.add(params.name)
            msgList.add(params.prodId)
            msgList.add(params.price)
        }
        if (msgType.equals("FollowProd")){
            msgList.add("FollowProd")
            msgList.add(params.userId)
            msgList.add(params.prodId)
        }
        if (msgType.equals("RateProd")){
            msgList.add("RateProd")
            msgList.add(params.prodId)
            msgList.add(params.rating)
            msgList.add(params.review)
        }
        if (msgType.equals("SearchProd")){
            msgList.add("SearchProd")
            msgList.add(params.prodName)
            msgList.add(params.prodId)
        }
        
        
        print "Creating Envelope inside search and create controller..............."
        def res = cf.CreateEnvelopee(msgList)
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
