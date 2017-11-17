package socket.homework1.server.java;


import static java.lang.String.valueOf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ServerThread implements Runnable {  

    private Socket client_Socket = null; 
	
	public ServerThread(Socket client_Socket){  
        this.client_Socket = client_Socket; 
    } 
	
	
	
	
	@Override  	
	public void run() {  
        try{
        	//getting the socket output, used to send to client
            BufferedWriter dataout = new BufferedWriter(new OutputStreamWriter(client_Socket.getOutputStream()));             
            //getting the socket input, used to receive data from client
            BufferedReader datain = new BufferedReader(new InputStreamReader(client_Socket.getInputStream())); 
            int score=0;
        	boolean Playing =true; 
        	while(Playing){ 
        		String welcome = "Server: Welcome to Hangman Game! ";
                dataout.write(welcome);
                dataout.newLine();
                dataout.flush();
                
               
                String Answer_Word = DataProcess.guessWord();
                char[] CharofAnswerWord=Answer_Word.toCharArray();
            	int attemptTime=Answer_Word.length();
            	int remainingAttemptTime=Answer_Word.length();
            	char[] enteredLetters = new char[Answer_Word.length()];
            	
            	for (int i = 0; i < enteredLetters.length; i++) {
            		enteredLetters[i] = '*';
                }
            	String ip = client_Socket.getInetAddress().getHostAddress();
            	System.out.println("Thw word client ( ip: "+ip+" ) need to guess is : "+Answer_Word);
            	
            	boolean wordIsGuessed = false;
            	String timeLeft = "You have "+attemptTime+" chances in total. " + remainingAttemptTime+ " times left.";
            	dataout.write(timeLeft);
                dataout.newLine();
                dataout.flush();
                
                while (!wordIsGuessed && remainingAttemptTime != 0 && Playing) {//didn't guess correct yet && didn't use up chances yet
                	String gs = "(You can quit game anytime if you type [Quit]). Enter a letter or a word: ";
                	gs = gs +valueOf(enteredLetters);
                	dataout.write(gs);
                	dataout.newLine();
                	dataout.flush();
                String rcv = datain.readLine();
                char input = rcv.charAt(0);
                
//Force Quit---------------------------------------------------------------------------------------                
                if(rcv.equals("Quit")||rcv.equals("quit"))
                	Playing =false;
//-------------------------------------------------------------------------------------------------    
                else{
                
                
                
                if (rcv.length()==Answer_Word.length()){//Try to guess by whole word
                	
                	if(rcv.equals(Answer_Word)){
                		score= score+1;
                		wordIsGuessed = true;
                		String WinForword= "You win! The answer is < " + Answer_Word + " >"+" You missed " + (attemptTime-remainingAttemptTime) +" time(s).  You score now is : " + score;
                		dataout.write(WinForword);
                		dataout.newLine();
                		dataout.flush();
                		}
                	else{
                		String failForword = "Wrong Answer. Continue...";
                		dataout.write(failForword);
                		dataout.flush();
                		remainingAttemptTime--;
                		}
                	}
                
                else{ //Try to guess by single letter
            			char userInput = rcv.charAt(0);            			
            			if (Answer_Word.contains(String.valueOf(userInput)) && !inEnteredLetters(userInput, enteredLetters)) {//include the letter but didn't write in yet
            				//find and fill the empty position for correct letters.        
            				for (int i = 0; i < CharofAnswerWord.length; i++) {
            				        	if (CharofAnswerWord[i] == input) {
            				        		enteredLetters[i] = input;
            				        		}
                            } 				
            				
            				if( inEqual(CharofAnswerWord,enteredLetters)){
            					score= score+1;
            					wordIsGuessed = true;
            					String winForletter= " Bingo! You Win ! The answer is < " + Answer_Word + " >"+" You missed " + (attemptTime-remainingAttemptTime) +" time(s). You score now is : " + score;
            					dataout.write(winForletter);
                            	dataout.newLine();
                            	dataout.flush();
                            }else{
                            	
            					String continueletter= "Continue...";
            					dataout.write(continueletter);
                            	dataout.newLine();
                            	dataout.flush();
            				}
                    		
                		}else if (inEnteredLetters(userInput, enteredLetters)) {
                				String alIn= "[ "+ String.valueOf(userInput) + " ]" + " is already in the word. " +  remainingAttemptTime + " times left.";
            				    dataout.write(alIn);
            				    dataout.flush();
                        	 }else{
                        		remainingAttemptTime--;
                        		String notIn= "[ "+String.valueOf(userInput) + " ]" + " is not in the word. " +  remainingAttemptTime + " times left.";
            				    dataout.write(notIn);
            				    dataout.newLine();
            				    dataout.flush();
            				    }
                			
            			}
                }   
                
               if(remainingAttemptTime == 0){
                score= score-1;
                String s5 = "You don't have chance to try, you already failed the game.";                	
                String s6 = new String("The word is < " + Answer_Word + " >");
                String s7 = new String("You score now is : " + score);
                String outputToClient = s5 +s6 + s7;
                dataout.write(outputToClient);
            	dataout.newLine();
            	dataout.flush();
            	}
               
        	}   
            	
        	
        	String pa = "Do you want to play another game? (YES/NO)";
        	dataout.write(pa);
        	dataout.newLine();
        	dataout.flush();
            String anotherGame = datain.readLine();
            if (anotherGame.equals("NO")||anotherGame.equals("no")||anotherGame.equals("No")) {
                Playing = false;
            }else{
            	Playing = true;
            }
            
//         }//(!wordIsGuessed && remainingAttemptTime != 0)
        }//(playing = true)      	
        	
        String go = "Game Over";
        dataout.write(go);
        dataout.newLine();
        dataout.flush();

        dataout.close();
        client_Socket.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	
    /* Check if letter is in enteredLetters array */
    public static boolean inEnteredLetters(char letter, char[] enteredLetters) {
    	return new String(enteredLetters).contains(String.valueOf(letter));
    	}

    
    
    public static boolean inEqual(char[] answer, char[] entered) {
    	boolean flag=true;
    	for(int i=0;i<answer.length;i++){
    		if (answer[i]!=entered[i])
    			flag=false;}
    	return flag;	
    		}
    }

    /* 
     * --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */   
   
