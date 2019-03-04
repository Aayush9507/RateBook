/**
 *
 * @author abhinavpandey
 */
package com.searchandcreate.items

import RateITProd.RateItems
import groovy.sql.Sql

class ItemController {
    def dataSource
    def index() { }
    
    def searchAndCreate(){
        try{
            print "Server Started"
            def db = new Sql(dataSource)
            def server = startServer()
            while(true){
                Socket connection = server.accept();
                ObjectInputStream serverIn = new ObjectInputStream(connection.getInputStream());
                def messageIn = []
                messageIn = serverIn.readObject();
                System.out.println("Message received from client: " + messageIn);
                def returnMessage = []
                def itemMap = [:]
                def msgType = messageIn[0]
                //Sending the response back to the client.
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                if (msgType == "3"){
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
                        bw.write(itemMap);
                    }
                }
                
                if (msgType == "4"){
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
                    bw.write(returnMessage);
                }

                println("Message sent to the client is "+returnMessage);
                bw.flush();
                bw.close();
            }
        }        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //                print "Closing Connection and Clean Up..."
                //                connection.close();
            }
            catch(Exception e){}
        }
        
    }
    
    def startServer() {
        String host = "localhost";
        int port = 8084;
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(host));
        return server
    }
}

