/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formpractice;


/**
 *
 * @author julie.taylor
 */
public class Customer {
    private String id;
    private  String name;
    private String address;
    private String zip;
    
    
    public Customer(){
        
    }

    public Customer(String id, String name, String address, String zip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
       
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

}
