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
class RegisterAndLoginController {
    static allowedMethods =[register:"POST",login:"POST"]
    
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
    
    def register() { 
        println params
        def json = [status : 0, message : ""]
        def clientOut = getOutputStream()

        try{
            // Sending Request to server
            def messageOut = [];
            messageOut.add(params.msgType)
            messageOut.add(params.email)
            messageOut.add(params.name)
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
    
    def getOutputStream(){
        String host = "localhost";
        int port = 8082;
        Socket client = new Socket(host, port);
        ObjectOutputStream clientOut = new ObjectOutputStream(client.getOutputStream());
        return [client,clientOut]
    }
    
    def getInputStream(client, clientOut){
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        def res = reader.readLine();
        clientOut.flush();
        return res
    }
}

