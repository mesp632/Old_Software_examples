/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

import java.time.LocalDateTime;

/**
 *
 * @author Saelin
 */
public class AppointmentDetails {
    private String apptID;
    private String custID;
    private String userID;
    private String title;
    private String description;
    
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    
    public AppointmentDetails(String apptID,String custID,String userID,String title, String description,
            String type,LocalDateTime start,LocalDateTime end){
        this.apptID = apptID;
        this.custID = custID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    
    public void setApptID(String apptID){
        this.apptID = apptID;
    }
    public String getApptID(){
        return this.apptID;
    }
    public void setCustID(String custID){
        this.custID = custID;
    }
    public String getCustID(){
        return this.custID;
    }
    public void setuserID(String userID){
        this.userID = userID;
    }
    public String getUserID(){
        return this.userID;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescritpion(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setStart(LocalDateTime start){
        this.start = start;
    }
    public LocalDateTime getStart(){
        return this.start;
    }
    public void setEnd(LocalDateTime end){
        this.end = end;
    }
    public LocalDateTime getEnd(){
        return this.end;
    }
    
    
        @Override
    public String toString() {
        return "AppointmentDetails{" +
                "customer ID=" + custID +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
