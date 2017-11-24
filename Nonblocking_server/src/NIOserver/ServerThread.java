package NIOserver;

import java.io.*;  
import java.net.*;  
import java.nio.*;  
import java.nio.channels.*; 
import java.util.*; 
import java.util.logging.*;

public class ServerThread extends Thread implements Runnable{  
    static boolean USER_FLG;
    private Selector selector;  
    StringBuffer stringByte = new StringBuffer();  
    SelectionKey ssKey;  

    public ServerThread() {  
  

    }  

    public void init() {  
        try {  
            selector = Selector.open();  
            ServerSocketChannel ssChannel = ServerSocketChannel.open();  
            ssChannel.configureBlocking(false);  
            ssChannel.socket().bind(new InetSocketAddress(9999));  
            ssKey = ssChannel.register(selector, SelectionKey.OP_ACCEPT);  
            System.out.println("server is starting..." + new Date());  
        } catch (IOException ex) {  
            Logger.getLogger(NioServer.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    } 
     public void execute() {  
        try {  
            while (true) {  
                int num = selector.select();  
                if (num > 0) {  
                    Iterator<SelectionKey> it = selector.selectedKeys()  
                            .iterator();  
                    while (it.hasNext()) {  
                        SelectionKey key = it.next();  
                        it.remove();  
                        if (!key.isValid())  
                            continue;  
                        if (key.isAcceptable()) {   
                            getConn(key);  
                        } else if (key.isReadable()|key.isWritable()) {  
                            readWriteMsg(key);  
                        }  
                        else 
                            break;  

                    }  

                }  
          
            }  

        } catch (IOException ex) {  
            Logger.getLogger(NioServer.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    }  

    private void getConn(SelectionKey key) throws IOException {  
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();  
        SocketChannel sc = ssChannel.accept();  
        sc.configureBlocking(false);  
        sc.register(selector, SelectionKey.OP_READ);  
        System.out.println("build connection :" 
                + sc.socket().getRemoteSocketAddress());
        DataProcessing.initial();
        int wordLength = DataProcessing.GUESS_WORD.length();
        System.out.println("Guessing word:"+DataProcessing.GUESS_WORD+" "+wordLength+" letters");
        //Encapsulating the welcome message that will be sent to the client
        String sendCTX = "("+sc.socket().getRemoteSocketAddress().toString()+")"+"{on}"+"["+"Game starts!"+
                ":"+DataProcessing.SEND_UNDERLINE+" <"+
                wordLength+" letters>"+" You can quit the game just type \"QUIT\" Please guess a letter: ]";
        System.out.println(sc.socket().getRemoteSocketAddress().toString());
        //Storing the user data into USER_BASE
        String userIP = sc.socket().getRemoteSocketAddress().toString()+")";
        userIP = NioServer.getCTX(userIP,"/",")");
        int userAttempt = wordLength;
        String userData = "/"+userIP+")"+"<"+userAttempt+">"+"!0?"+"["+
                DataProcessing.SEND_UNDERLINE+"]"+"{"+DataProcessing.GUESS_WORD+"}";
        NioServer.USER_BASE.add(userData);
        System.out.println(NioServer.USER_BASE);
        sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
               
        }
        
        
      

    private void readWriteMsg(SelectionKey key) throws IOException {  
        stringByte.delete(0, stringByte.length());  
        SocketChannel sc = (SocketChannel) key.channel();  
  //      System.out.print(sc.socket().getRemoteSocketAddress() + " ");  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        buffer.clear();  
        int len = 0;  
        StringBuffer stringByte = new StringBuffer();  
        while ((len = sc.read(buffer)) > 0) {  
            buffer.flip();  
            stringByte.append(new String(buffer.array(), 0, len));  
        }  
        
        if(stringByte.length()>0)
        {
        String rcvCTX = stringByte.toString();
        String sendCTX = null;
                
                System.out.println("server recevied:"+ rcvCTX);
                String continueFLG = NioServer.getCTX(rcvCTX,"[","]");
                
                switch (continueFLG) {
                    case "YES":
                        //If the client wants to continue playing the game (the content of 
                        //the message is "YES"), the server will initialize the game again
                        //and start a new round.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    DataProcessing.initial();
                                    int wordLength=DataProcessing.GUESS_WORD.length();
                                    int userAttempt = wordLength;
                                    String userWord=DataProcessing.GUESS_WORD;
                                    String userUnderline = DataProcessing.SEND_UNDERLINE;
                                    NioServer.USER_BASE.remove(i);
                                    String userData;
                                    userData = "/"+baseIP+")"+"<"+userAttempt+">"+"!"+userRecord+
                                            "?"+"["+userUnderline+"]"+"{"+userWord+"}";
                                    NioServer.USER_BASE.add(userData);
                                    System.out.println(NioServer.USER_BASE);
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{on}"+"["+"New round: Please guess a letter"+
                                            userUnderline+" <"+wordLength+" letters>"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
                                    break;
                                }
                                
                            }
                            break;
                        }
                    case "NO":
                        //If the client wants to exit the game (the content of the message 
                        //is "NO", the server will send a goodbye message which contains 
                        //the score to the client and delete all the user data.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    NioServer.USER_BASE.remove(i);
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                            "!"+" You have "+userRecord+" points!"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
                                    key.cancel();   //close
                                    sc.close();  
                                    sc.socket().close(); 
                                    break;
                                }
                                
                            }       
                            break;
                        }
                    case "QUIT":
                        //If the client wants to temporarily exit the game (the content of the message 
                        //is "QUIT", the server will send a goodbye message which contains 
                        //the score to the client and delete the user data.
                        {
                            String rcvIP = NioServer.getCTX(rcvCTX,"/",")");
                            for(int i=0;i<NioServer.USER_BASE.size();i++)
                            {
                                
                                String temp = NioServer.USER_BASE.get(i);
                                String baseIP = NioServer.getCTX(temp,"/",")");
                                
                                if(rcvIP.equals(baseIP))
                                {
                                    NioServer.USER_BASE.remove(i);
                                    String userRecord = NioServer.getCTX(temp,"!","?");
                                    sendCTX = null;
                                    sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                            "!"+" You have "+userRecord+" points!"+"]";
                                    System.out.println("server sent:"+ sendCTX);
                                    sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
                                    key.cancel();   //close
                                    sc.close();  
                                    sc.socket().close(); 
                                    break;
                                }
                                
                            }       
                            break;
                        }
                    default:
                        sendCTX = DataBase.dataProcess(rcvCTX);
                        System.out.println("server sent:"+ sendCTX);
                sc.write(ByteBuffer.wrap(sendCTX.getBytes()));
                        break;
                }
        }   
                
   

    }  

@Override    
    public void run() {  
        init();  
        execute();  
    }  
}
