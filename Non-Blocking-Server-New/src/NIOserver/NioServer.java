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
 public static String getString(String original,String firstSplit,String secondSplit){
        String result = original.substring(original.lastIndexOf(firstSplit), 
        original.lastIndexOf(secondSplit));
        result = result.substring(1,result.length());
        return result;
    }
} 
