/**
 *
 * @author abhinavpandey
 */
package com.searchandcreate.items

import RateITProd.RateItems
import com.subsystem.CommSubSystem
import com.subsystem.ConversationFactory
import com.subsystem.UDPComm
import groovy.sql.Sql

class ItemController {
    def dataSource
    def index() {

    }
    def search(){
        def query = '%'+messageIn[1]+'%'
        print "query "+query
        def itemQuery = "SELECT * FROM rate_items WHERE item_name LIKE $query"
        def resQueryItem=db.rows(itemQuery)

        print "resQueryItem "+resQueryItem
                    
        if(!resQueryItem){
            println "Item not exist"
            returnMessage.add("Item Not Found, Please Create It to review")
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
    def searchAndCreate(){
        def json = [status : 0, message : ""]
//        CommSubSystem subsystemObj = new CommSubSystem()
//        ConversationFactory cf = new ConversationFactory()
        
        UDPComm c1 = new UDPComm();
//         while(true){
//            print "Listening...."
//            c1.receive()
//        }
//        println "cf"+cf
        Thread udpCommThread =  new Thread(c1);
        udpCommThread.start();
//        t.start()
       
        def msgType = params.msgType
        if (msgType.equals("SearchProd"))
        {
            search();
        }
        if (msgType.equals("CreateProd"))
        {
            create();
        }
    }
}