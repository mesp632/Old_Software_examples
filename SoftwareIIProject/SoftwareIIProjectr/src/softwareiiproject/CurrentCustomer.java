/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

/**
 *
 * @author Saelin
 */
public class CurrentCustomer {
    private static String newCurrentCustomer=null;
    private static String newCurrentCustomerID=null;
    public static void setCurrentCustomer(String currentCustomer, String customerID){
        newCurrentCustomer=currentCustomer;
        newCurrentCustomerID=customerID;
    }
    public static String getCurrentCustomer(){
        return newCurrentCustomer;
    }
    
    public static String getCurrentCustomerID(){
        return newCurrentCustomerID;
    }
    
}
