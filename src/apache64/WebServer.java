/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apache64;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author 10070124
 */
public final class WebServer {

    public static void main(String argv[]) throws Exception {
        // Get the port number from the command line.
        int port;
        if(argv.length > 0){
            port = (new Integer(argv[0]));
        } else {
            port = 8008;
        }

        System.out.println("Porta: " + port);
        // Establish the listen socket.
        ServerSocket socket = new ServerSocket(port);

        // Process HTTP service requests in an infinite loop.
        while (true) {
            // Listen for a TCP connection request.
            Socket connection = socket.accept();
            
            // Construct an object to process the HTTP request message.
            HttpRequest request = new HttpRequest(connection);

            // Create a new thread to process the request.
            Thread thread = new Thread(request);

            // Start the thread.
            thread.start();
        }
    }
}
