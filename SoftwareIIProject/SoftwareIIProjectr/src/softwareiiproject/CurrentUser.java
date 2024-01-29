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
public class CurrentUser {
    private static String newcurrentUserName=null;
    private static String newUserID=null; 
    public static void setCurrentUserName(String currentUserName, String UserID){
        newcurrentUserName = currentUserName;
        newUserID=UserID;
    }
    
    public static String getCurrentUserName(){
        return newcurrentUserName;
    }
    public static String getCurrentUserID(){
        return newUserID;
    }
}
