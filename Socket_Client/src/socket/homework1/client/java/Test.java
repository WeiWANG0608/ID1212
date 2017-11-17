package socket.homework1.client.java;
import java.net.*;



class Test
{
    public static void main(String[] args) throws Exception
    {
        
        Socket client = new Socket(InetAddress.getLocalHost(), 20006);

       new ThreadWriter(client).start();
       new ThreadReader(client).start();

    
    }
    
}

