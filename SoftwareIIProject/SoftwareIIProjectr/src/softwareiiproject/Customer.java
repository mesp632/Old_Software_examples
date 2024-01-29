/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;


/**
 *
 * @author julie.taylor
 */
public class Customer {
    private String id;
    private  String name;
    private String address;
    private String zip;
    private String phone;
    private String addressID;
    private String city;
    private String country;
    
    
    public Customer(){
        
    }

    public Customer(String id, String name, String address, String zip, String phone, String addressID, String city, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.addressID = addressID;
        this.city = city;
        this.country = country;
       
    }
    
   

    public String getId() {
       
        return id;
    }

    public String getName() {
        
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public String getAddressID(){
        return addressID;
    }

    public void setCity(String city){
        this.city = city;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public String getCountry(){
        return this.country;
    }
}
