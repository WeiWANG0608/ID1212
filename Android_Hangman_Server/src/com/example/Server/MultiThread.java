package com.example.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static java.lang.String.valueOf;

/**
 * Created by wangw on 12/14/2017.
 */

public class MultiThread extends Thread{

    Socket socket;
    public MultiThread (Socket socket){
        this.socket=socket;

    }

    @Override
    public void run() {

        try {
            int score = 0;
            BufferedReader bufin =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufout =
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String num2 = valueOf(score);
                String sc = "Your Score :" + num2 + "  " + "Do you want to start playing? (YES or NO)";


                bufout.write(sc);
                bufout.newLine();
                bufout.flush();//ask if start the game

                String line = bufin.readLine();
                String ip = socket.getInetAddress().getHostAddress();

                if ("NO".equals(line))
                    break;
                else if ("YES".equals(line)) {

                    HangmanRule H = new HangmanRule();
                    int lefttime = H.remainingAttemptTime;
                    String wordaftergame = H.wordAfterGuess;
                    double check = H.percentage;
                    if (lefttime == 0) {
                        String result = H.outinfo + "@"+wordaftergame + "@"+ valueOf(check);
                        bufout.write(result);
                        bufout.newLine();
                        bufout.flush();

                    } else {
                        lefttime = lefttime - 1;
                        String result = H.outinfo + "You have " + valueOf(lefttime) + " times left." +  "@"+wordaftergame + "@"+ valueOf(check);
                        bufout.write(result);
                        bufout.newLine();
                        bufout.flush();

                    }


                    if (H.Answer_Word.equals(H.wordAfterGuess)) {
                        score = score + 1;
                    }

                }

            }

            socket.close();;
        }
        catch (Exception e)
        {

        }

    }


}
