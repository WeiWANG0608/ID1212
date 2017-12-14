/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.Moneymoney;
import java.text.DecimalFormat;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author user
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    public static double Finaloutputmoney=0;
     @PersistenceContext(unitName = "currency")
     private EntityManager em;
     
     public String moneyconvert(String inputcurrency, String outputcurrency, double money){
         String convertresult;
         double calculateresult;
         DecimalFormat dFormat = new DecimalFormat("#0.00");
         System.out.println("Source currency: "+inputcurrency);
        System.out.println("Destiny Currency: "+outputcurrency);
        System.out.println("Currency Amount: "+money);
        Moneymoney newmodelin=em.find(Moneymoney.class, inputcurrency);
        Moneymoney newmodelout=em.find(Moneymoney.class, outputcurrency);       
        System.out.println("Start conversion!");
        double Inputrate =  newmodelin.getmoney();
        double outputrate =  newmodelout.getmoney();
        calculateresult=(outputrate/Inputrate)*money;
        convertresult=dFormat.format(calculateresult);                                    
         return convertresult;
         
     } 
    
}
