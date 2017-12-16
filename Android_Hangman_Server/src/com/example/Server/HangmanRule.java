package com.example.Server;

import static java.lang.String.valueOf;

/**
 * Created by wangw on 12/14/2017.
 */

public class HangmanRule {

    String outinfo;
    String wordAfterGuess;
    String Answer_Word = DataProcess.guessWord();
    int attemptTime=Answer_Word.length();
    int remainingAttemptTime;
    double percentage;

    public void Game(String input){

        try{
            boolean Playing =true;
            while(Playing){

                char[] CharofAnswerWord=Answer_Word.toCharArray();
                char[] enteredLetters = new char[Answer_Word.length()];

                for (int i = 0; i < enteredLetters.length; i++) {
                    enteredLetters[i] = '*';
                }

                boolean wordIsGuessed = false;

                while (!wordIsGuessed && remainingAttemptTime != 0 && Playing) {//didn't guess correct yet && didn't use up chances yet
                    String gs = "(You can quit game anytime if you type [Quit]). Enter a letter or a word: ";
                    gs += valueOf(enteredLetters);
                    outinfo = gs;
                    wordAfterGuess = (valueOf(enteredLetters));

                    String rcv = input;
                    char in = rcv.charAt(0);

//Force Quit---------------------------------------------------------------------------------------
                    if(rcv.equals("Quit")||rcv.equals("quit"))
                        Playing =false;
//-------------------------------------------------------------------------------------------------
                    else{



                        if (rcv.length()==Answer_Word.length()){//Try to guess by whole word

                            if(rcv.equals(Answer_Word)){
                                wordIsGuessed = true;
                                String WinForword= "You win! The answer is < " + Answer_Word + " >"+" You missed " + (attemptTime-remainingAttemptTime) +" time(s).";
                                outinfo = WinForword;
                                wordAfterGuess = (valueOf(enteredLetters));
                                checkResult(Answer_Word,enteredLetters);
                            }
                            else{
                                String failForword = "Wrong Answer. Continue...";
                                outinfo = failForword;
                                wordAfterGuess = (valueOf(enteredLetters));
                                remainingAttemptTime--;
                                checkResult(Answer_Word,enteredLetters);
                            }
                        }

                        else{ //Try to guess by single letter
                            char userInput = rcv.charAt(0);
                            if (Answer_Word.contains(valueOf(userInput)) && !inEnteredLetters(userInput, enteredLetters)) {//include the letter but didn't write in yet
                                //find and fill the empty position for correct letters.
                                for (int i = 0; i < CharofAnswerWord.length; i++) {
                                    if (CharofAnswerWord[i] == in) {
                                        enteredLetters[i] = in;
                                    }
                                }

                                if( inEqual(CharofAnswerWord,enteredLetters)){
                                    wordIsGuessed = true;
                                    String winForletter= " Bingo! You Win ! The answer is < " + Answer_Word + " >"+" You missed " + (attemptTime-remainingAttemptTime) +" time(s). " ;
                                    outinfo = winForletter;
                                    wordAfterGuess = (valueOf(enteredLetters));
                                    checkResult(Answer_Word,enteredLetters);
                                }else{

                                    String continueletter= "Continue...";
                                    outinfo = continueletter;
                                    wordAfterGuess = (valueOf(enteredLetters));
                                    checkResult(Answer_Word,enteredLetters);
                                }

                            }else if (inEnteredLetters(userInput, enteredLetters)) {
                                String alIn= "[ "+ valueOf(userInput) + " ]" + " is already in the word. " +  remainingAttemptTime + " times left.";
                                outinfo = alIn;
                                wordAfterGuess = (valueOf(enteredLetters));
                                checkResult(Answer_Word,enteredLetters);
                            }else{
                                remainingAttemptTime--;
                                String notIn= "[ "+ valueOf(userInput) + " ]" + " is not in the word. " +  remainingAttemptTime + " times left.";
                                outinfo = notIn;
                                wordAfterGuess = (valueOf(enteredLetters));
                                checkResult(Answer_Word,enteredLetters);
                            }

                        }
                    }

                    if(remainingAttemptTime == 0){
                        String s5 = "You don't have chance to try, you already failed the game.";
                        String s6 = new String("The word is < " + Answer_Word + " >");
                        String outputToClient = s5 +s6 ;
                        outinfo = outputToClient;
                        wordAfterGuess = (valueOf(enteredLetters));
                        checkResult(Answer_Word,enteredLetters);
                    }

                }


                String pa = "Do you want to play another game? (YES/NO)";
                outinfo = pa;
                String anotherGame = input;
                if (anotherGame.equals("NO")||anotherGame.equals("no")||anotherGame.equals("No")) {
                    Playing = false;
                }else{
                    Playing = true;
                }

//         }//(!wordIsGuessed && remainingAttemptTime != 0)
            }//(playing = true)

            String go = "Game Over";
            outinfo = go;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Check if letter is in enteredLetters array */
    public static boolean inEnteredLetters(char letter, char[] enteredLetters) {
        return new String(enteredLetters).contains(valueOf(letter));
    }



    public static boolean inEqual(char[] answer, char[] entered) {
        boolean flag=true;
        for(int i=0;i<answer.length;i++){
            if (answer[i]!=entered[i])
                flag=false;}
        return flag;
    }

    private void checkResult(String Answer_Word,char[] enteredLetters) {
        String answer = Answer_Word;
        char[] left = enteredLetters;
        int length_answer = answer.length();
        int length_guessed = answer.length();
        for (int i = 1; i < length_answer; i++) {
            if (left[i] == '*') {
                length_guessed--;
            }
        }
        percentage = (length_guessed / length_answer) * 100;

    }
}
