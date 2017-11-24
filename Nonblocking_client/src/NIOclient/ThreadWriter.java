package NIOclient;
import java.io.*; 
import java.nio.ByteBuffer;  
import java.util.logging.*;  
import java.util.*;

public class ThreadWriter extends Thread implements Runnable{
    public static String userSend;
    public ThreadWriter() {  
    }
 
@Override        
    public void run() {  
  Scanner scan = new Scanner(System.in);
   while (true) {  
       try {  
           String sendCTX = scan.nextLine();
           sendCTX = ThreadReader.SOCKET_CHANNEL.getLocalAddress().toString()+")"+"["+sendCTX+"]"; 
           userSend = sendCTX;
           UserType type = new UserType();
           new Thread(type).start();
        //   ThreadReader.SOCKET_CHANNEL.write(ByteBuffer.wrap(sendCTX.getBytes())); 

       } catch (IOException ex) {  
           Logger.getLogger(ThreadWriter.class.getName()).log(  
                   Level.SEVERE, null, ex);  
       }  
   }  

} 
          
}
