/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testrmi;

/**
 *
 * @author user
 */
import java.rmi.*;
import java.util.Vector;
/**
 *
 * @author user
 */
public interface RMIinter extends Remote{

    
    Vector<String> getFileList() throws RemoteException;
    String  print() throws RemoteException;
	String operationSystem(String input);
	int getFileLength(String fileName) throws RemoteException;	
	String login(String loginname) throws RemoteException;
    String register(String registername) throws RemoteException;
	byte[] getFile(String fileName, int start, int length) throws RemoteException;	
   
}