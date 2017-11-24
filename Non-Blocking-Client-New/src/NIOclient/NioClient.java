package NIOclient;
import java.util.*;
public class NioClient {  
    
    public static String SERVER_ADDRESS;
    
    
    public static void main(String[] args) {  
        System.out.println("Welcome to HangMan Game! Please enter the server address:");
        Scanner scan = new Scanner(System.in);
        SERVER_ADDRESS = scan.nextLine();
        ThreadReader read = new ThreadReader();
        ThreadWriter write = new ThreadWriter();
        
        new Thread(read).start(); 
        new Thread(write).start();
        
        
    }  
    
    public static String getString(String original,String firstSplit,String secondSplit){
        String result = original.substring(original.lastIndexOf(firstSplit), 
        original.lastIndexOf(secondSplit));
        result = result.substring(1,result.length());
        return result;
    }
} 
