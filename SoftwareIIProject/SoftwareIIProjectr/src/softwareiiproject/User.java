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
public class User {
    private String id;
    private  String name;
    private String createdBy;
    private String lastUpdate;
    private String password;
    
    
    public User(){
        
    }

    public User(String id, String name, String Password) {
        this.id = id;
        this.name = name;
     //   this.createdBy = createdBy;
        this.password = password;
  //      this.lastUpdate = lastUpdate;
       
    }
    
   

    public String getId() {
       
        return id;
    }

    public String getName() {
        
        return name;
    }


   public String getPassword(){
       return password;
   }

}
