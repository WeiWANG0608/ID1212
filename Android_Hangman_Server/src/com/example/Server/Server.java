package com.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by wangw on 12/14/2017.
 */

public class Server {

	 /**
     * @param args the command line arguments
     */
  public static void main(String[] args) throws Exception{
        ArrayList<Socket> list = new ArrayList<Socket>();
        ServerSocket server = new ServerSocket(9999);
        while(true){
            System.out.println("start listening port 9999.");
            Socket socket=server.accept();
            list.add(socket);
            String hostname=socket.getInetAddress().getHostAddress();
            System.out.println(hostname+" is connected to server");
            new MultiThread(socket).start();
                    }
            
        }
}
