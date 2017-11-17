package socket.homework1.server.java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataProcess {
	
	public static String[] textReader(){
        /* ����TXT�ļ� */  
       String[] wordsList = null;
       String pathname = "C:\\Users\\wangw\\Google drive\\KTH Reference\\Network Programming\\homework\\words.txt"; // ����·�������·�������ԣ������Ǿ���·��.
       File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�
       try {
    	   BufferedReader brword = new BufferedReader(new InputStreamReader(
    			   new FileInputStream(filename)));
    	   ArrayList<String> readList = new ArrayList<String>();
    	   String temp=null;
    	   try {
    		   while((temp=brword.readLine())!=null &&!"".equals(temp)){
    			   readList.add(temp);
    			   }
    		   } catch (IOException e) {
    			   e.printStackTrace();
    			   }
    	   wordsList = (String[])readList.toArray(new String[readList.size()]);
    	   } catch (FileNotFoundException e) {
    		   e.printStackTrace();
    		   }
       return wordsList;
       }
	
	public static String guessWord(){
		int randomWordNumber = (int) (Math.random() * textReader().length);    // Create an array to store already entered letters
    	String words=textReader()[randomWordNumber];
    	return words;
	}

}
