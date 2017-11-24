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
    
    public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
} 
