/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrmiclient;
import java.util.*;
import testrmi.RMIinter;
import java.rmi.*;
/**
 *
 * @author user
 */
public class Opration {
    public static String output;
    public static String result;
    public static void Operation() throws Exception{
        Scanner sc = new Scanner(System.in);
        RMIinter userOperation=(RMIinter)Naming.lookup(TESTRMIClient.URL);
        System.out.println("Choose to what to do: 1-upload 2-download 3-update 4-logout 5-unregister 6-delete");
        String Secondchance= sc.nextLine();
        if(Secondchance.equals("1")){
           System.out.println("choose the file to upload");           
           output="<upload:"+sc.nextLine()+">";
            System.out.println("choose the file level: 1-private 2-read only 3-all okay");
            output=output+sc.nextLine()+"~";
             //System.out.println(output);
           result = userOperation.operationSystem(output);          
           System.out.println(result);
        }
        else if(Secondchance.equals("2")){
           System.out.println("choose the file to download");
           output="<download:"+sc.nextLine()+">";
           result = userOperation.operationSystem(output);
           System.out.println(result);
        }
        else if(Secondchance.equals("3")){
           System.out.println("choose the file to update");
           output="<update:"+sc.nextLine()+">";
           result = userOperation.operationSystem(output);
           System.out.println(result);
        }
        else if(Secondchance.equals("4")){
           System.out.println("ARE you sure? Y/N");
           if(sc.nextLine().equals("Y")){
               output="QUIT";
               result = userOperation.operationSystem(output);
               System.out.println(result);
               System.exit(0);
           }
        }
        else if(Secondchance.equals("5")){
           System.out.println("ARE you sure? Y/N");
           if(sc.nextLine().equals("Y")){
               output="EXIT";
               result = userOperation.operationSystem(output);
               System.out.println(result);
               System.exit(0);
           }
           
           
        }
        else if(Secondchance.equals("6")){
            System.out.println("choose the file to delete");
           output="<delete:"+sc.nextLine()+">";
           result = userOperation.operationSystem(output);
           System.out.println(result);
           }
        else{System.out.println("Wrong choice");}
        
    }
    
}
