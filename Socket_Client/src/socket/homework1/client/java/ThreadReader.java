package socket.homework1.client.java;
import java.io.*;
import java.net.*;

public class ThreadReader extends Thread {
   Socket socket;
   public ThreadReader(Socket socket){
       this.socket = socket;
   }
    @Override
    public void run(){
       try{
    	   
           BufferedReader bufin =
                   new BufferedReader(new InputStreamReader(socket.getInputStream()));
           String str=null;
           while ((str=bufin.readLine())!=null){

               System.out.println(str);
           }
       }
       catch (IOException e){

       }
    }
}
