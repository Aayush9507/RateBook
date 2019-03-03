/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.setup.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abhinavpandey
 */
public class SetTcpCon {
    SetTcpCon() throws IOException{
        String host = "localhost";
        int port = 8082;
        try {
          System.out.println("Setting TCP Connection");
          Socket  client = new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(SetTcpCon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
