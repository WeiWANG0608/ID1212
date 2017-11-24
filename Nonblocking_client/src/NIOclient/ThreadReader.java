package NIOclient;
import java.io.*; 
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.*; 
import java.util.logging.*; 
public class ThreadReader extends Thread implements Runnable{
    
    Selector selector;   

    public static SocketChannel SOCKET_CHANNEL;
    
    public ThreadReader() {    

    }  
    public void init() {  
        try {  
            SOCKET_CHANNEL = SocketChannel.open();  
            SOCKET_CHANNEL.configureBlocking(false);  
            SOCKET_CHANNEL.connect(new InetSocketAddress(NioClient.SERVER_ADDRESS, 9999));  

        } catch (IOException ex) {  
            Logger.getLogger(NioClient.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    }  
    public void execute() {  

        int num = 0;  
        try {  
            while (!SOCKET_CHANNEL.finishConnect()) {  
            }  
        } catch (IOException ex) {  
            Logger.getLogger(NioClient.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
 
        while (true) {  
            try {  

                ByteBuffer buffer = ByteBuffer.allocate(1024);  
                buffer.clear();  

                StringBuffer stringByte = new StringBuffer();  
             

                while ((num = SOCKET_CHANNEL.read(buffer)) > 0) {  
                    stringByte.append(new String(buffer.array(), 0, num));  
                    buffer.clear();  
                }  
                if (stringByte.length()>0)
                {
                    String rcvCTX = stringByte.toString();
                    String rcvIP = NioClient.getCTX(rcvCTX,"/",")");
                    String localIP =SOCKET_CHANNEL.getLocalAddress().toString()+")";
                    localIP = NioClient.getCTX(localIP,"/",")");
                    if(rcvIP.equals(localIP))
                    {
                        String endLabel = NioClient.getCTX(rcvCTX,"{","}");

                        if(endLabel.equals("end")){
                            String ctx = NioClient.getCTX(rcvCTX,"[","]");
                            System.out.println(ctx);
                            SOCKET_CHANNEL.close();  
                            SOCKET_CHANNEL.socket().close();  
                            System.exit(0);
                        }
                        else{
                            String ctx = NioClient.getCTX(rcvCTX,"[","]");
                            System.out.println(ctx);
                        }
                    } 
                } 
              
            } catch (IOException ex) {  
                Logger.getLogger(NioClient.class.getName()).log(  
                        Level.SEVERE, null, ex);  
            }  
        }  

    }  

    @Override
    public void run() {  
        
        init();  
        execute();  
    }  
    
}
