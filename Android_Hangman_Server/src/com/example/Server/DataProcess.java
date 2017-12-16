package com.example.Server;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by wangw on 12/13/2017.
 */

public class DataProcess {

    public static String[] textReader(){
        

        String[] wordsList = null;
        String pathname = "C:\\Users\\wangw\\AndroidStudioProjects\\Hangman1\\app\\src\\main\\java\\com\\example\\hangman1\\words.txt"; 
        File filename = new File(pathname);

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

    public static char[] guesschar(){
        char[] enteredLetter= new char[guessWord().length()];
        for (int i = 0; i < enteredLetter.length; i++) {
            enteredLetter[i] = '*';
        }

        return enteredLetter;
    }


}
