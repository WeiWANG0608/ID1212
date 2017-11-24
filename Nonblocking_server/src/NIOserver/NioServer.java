package NIOserver;
import java.util.*;
import java.io.*;
public class NioServer {  
    static String[] WORD_BASE;
    static ArrayList<String> USER_BASE = new ArrayList<String>();

    public static void main(String[] args) throws Exception{  
        File file = new File("D://words.txt");
        FileReader reader = new FileReader(file);
        int fileLen = (int)file.length();
        char[] chars = new char[fileLen];
        reader.read(chars);
        String txt = String.valueOf(chars);
        WORD_BASE = txt.split("\n"); 
        
        ServerThread server = new ServerThread();  
        new Thread(server).start();  

    }  
 public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
} 
