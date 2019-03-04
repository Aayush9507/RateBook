/**
 *
 * @author abhinavpandey
 */
package SearchAndCreateProduct

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
        def clientOut = getOutputStream()

        try{
            println "Sending Request to server"
            def messageOut = [];
            messageOut.add(params.msgType)
            messageOut.add(params.query)
            clientOut[1].writeObject(messageOut);

            println "Receiving Response from the server"
            def reponse = getInputStream(clientOut[0], clientOut[1])
            json = [status : 1, message : reponse]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def create() {
        println "params"+params
        println "session['user']"+session['user']
        def json = [status : 0, message : ""]
        def clientOut = getOutputStream()
        try{
            // Sending Request to server
            def messageOut = [];
            messageOut.add(params.msgType)
            messageOut.add(params.itemName)
            messageOut.add(params.itemType)
            messageOut.add(params.itemDesc)
            messageOut.add(params.itemFollowCount)
            messageOut.add(params.itemReviewCount)
            //                    def userId = Users.findByEmail(session['user'])
            //                    println userId
            messageOut.add(session['user'])
            clientOut[1].writeObject(messageOut);

            // Receiving Response from the server
            def reponse = getInputStream(clientOut[0], clientOut[1])
            print response
            json = [status : 1, message : reponse]
            render json as JSON

        }catch(Exception ex){
            json = [status : 0, message : ex.localizedMessage]
            render json as JSON
        }
    }
    
    def getOutputStream(){
        String host = "localhost";
        int port = 8084;
        Socket client = new Socket(host, port);
        ObjectOutputStream clientOut = new ObjectOutputStream(client.getOutputStream());
        return [client,clientOut]
    }
    
    def getInputStream(client, clientOut){
        print "saving response"
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        def res = reader.readLine();
        println "res "+res
        clientOut.flush();
        return res
    }
}
