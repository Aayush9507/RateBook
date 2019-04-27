/**
 *
 * @author abhinavpandey
 */
package com.searchandcreate.items

import RateITProd.RateItems
import com.subsystem.CommSubSystem
import com.subsystem.ConversationFactory
import com.subsystem.ConversationDictionary
import com.message.Message
import com.subsystem.Envelope
import com.subsystem.UDPComm
import grails.converters.JSON
import groovy.sql.Sql
import java.util.concurrent.Future
import sun.org.mozilla.javascript.internal.Callable
import java.util.concurrent.FutureTask; 
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ItemController {
    def dataSource
    def index() {

    }
    def search(String messageIn){
        String returnMessage = null;
        def db = new Sql(dataSource)
        def query = '%'+messageIn+'%'
        def itemQuery = "SELECT * FROM rate_items WHERE item_name LIKE $query"
        def resQueryItem=db.rows(itemQuery)
        
                    
        if(!resQueryItem){
            println "Item not exist"
            returnMessage = "Item Not Found"
            return returnMessage
            
        }
        else{
            println "Item exist"
            itemMap.put("id",resQueryItem[0][0])
            itemMap.put("itemDesc",resQueryItem[0][2])
            itemMap.put("itemFollowCount",resQueryItem[0][3])
            itemMap.put("itemLastUpdated",resQueryItem[0][4])
            itemMap.put("itemName",resQueryItem[0][5])
            itemMap.put("itemReviewCount",resQueryItem[0][6])
            itemMap.put("itemType",resQueryItem[0][7])
            itemMap.put("userEmail",resQueryItem[0][8])
            println("itemMap "+itemMap) 
        }
        
    }
    def create(){
        def itemObj = new RateItems()
        itemObj.itemName = messageIn[1]
        itemObj.itemType = messageIn[2]
        itemObj.itemDesc = messageIn[3]
        itemObj.itemFollowCount = messageIn[4]
        itemObj.itemReviewCount = messageIn[5]
        itemObj.userEmail = messageIn[6]
        itemObj.itemLastUpdated = new Date()
        if(itemObj.save(flush:true,failOnError: true)){
            returnMessage.add("Product Created Successfully")
        }else{
            valUser.errors.allErrors.each{
                println it 
            }
        }
    }
    
    def sendEnvelope(Env){
        print "Sending Envelope Back"
        UDPComm UDPCommobj = new UDPComm()
        UDPCommobj.send(Env)
    }
    
    def createEnvelope(cf,newmsgType,r){
        print "rrrrrrrrrr"+r
        def msgList = []
        
        
        if (newmsgType.equals("SearchAck"))
        {
            msgList.add("SearchAck")
            msgList.add(r)

        print "Creating Envelope inside Search and create item controller"
        def res = cf.CreateEnvelopee(msgList)
        return res
        }
        }
        
    
    
    def createConversation(cf){
        return cf.CreateConversation()
    }
    def searchAndCreate(){
        def json = [status : 0, message : ""]
        UDPComm c1 = new UDPComm();
        
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Envelope> future = service.submit(c1);
        Envelope env = future.get();
        service.shutdown();        
        String msgType = env.getMessage().getMessageType();
        System.out.println("Message Type: "+msgType);
        
        System.out.println("Message: "+env.getMessage());
        System.out.println("Product Name: "+env.getMessage().getProdName());
        String prodName = env.getMessage().getProdName();
        
        if (msgType.equals("SearchProd"))
        {
            String r = search(prodName);
            try{
            
            ConversationFactory cf = new ConversationFactory();
            def newmsgType = "SearchAck"
            println "MessageType assigned"+newmsgType;
            
            def conv = ConversationDictionary.getConversation(env.getMessage().getConversationId());
            def convId = conv.getConvId()      
            
            println "Conv. id assigned"
            println "r message is"+r
            def env2 = createEnvelope(cf,newmsgType,r)
            
            
            System.out.println("New envelope created");
            sendEnvelope(env2)

        }catch(Exception ex){
        }
            
        }
        if (msgType.equals("CreateProd"))
        {
            create();
        }
        
        
    }
    }