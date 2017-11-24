/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NIOclient;
import java.io.IOException;
import java.nio.ByteBuffer; 
import java.util.logging.Level;
import java.util.logging.Logger;
public class UserType extends Thread implements Runnable{

    public UserType() 
    {

    }
    
@Override

    public void run() { 
        try{
        ThreadReader.SOCKET_CHANNEL.write(
        		ByteBuffer.wrap(
        				ThreadWriter.userSend.getBytes()));
        
        } catch (IOException ex) {  
           Logger.getLogger(ThreadWriter.class.getName()).log(  
                   Level.SEVERE, null, ex);  
       }
    }
    
}
