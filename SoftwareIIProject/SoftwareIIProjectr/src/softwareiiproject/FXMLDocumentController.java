/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 *
 * @author Saelin
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableColumn<User, String> userID;
    @FXML
    private TableColumn<User, String> userTableName;
    @FXML
    private TableView <User> userTable;
    
    
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label loginError;
    

    @FXML
    private TextField userNameText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField addressText;

    ArrayList<User> userList = new ArrayList();
    
    @FXML /* port information over to the form */
    private void showUserDetails(User selectedUser) {
     
        userNameText.setText(selectedUser.getName());
        passwordText.setText(selectedUser.getPassword());
//        addressText.setText(selectedUser.getCreatedBy());
      

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         userID.setCellValueFactory(new PropertyValueFactory<>("id"));
         userTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
         
         userTable.getItems().setAll(parseUserList()); /* takes the list from the parseUserList() 
         method, and addes the rows to the TableView */
        
         
         
         userTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->showUserDetails(newValue));//listener with lambda expression as a short way to populate login field according to selection
        
    }    
    
    private List<User> parseUserList() {
      
        String tId;
        String tName;
        String tAddress;
        String tZip;
        String tPassword;
        try(
            
            
        Statement statement = DBConnection.getConn().createStatement();
            ){
           ResultSet rs = statement.executeQuery("SELECT userId, username, createBy, password FROM user");
            
            while (rs.next()) {
                tId = rs.getString("userId");

                tName = rs.getString("userName");

                tAddress = rs.getString("user.createBy");
                
                tPassword = rs.getString("user.password");
             

                userList.add(new User(tId, tName,tPassword));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }

        
        return userList;

    }   
    
    //buttons
    
    //login button
    @FXML
    public void handleLogin(){
        
        Locale locale = Locale.getDefault();
    //    locale.setDefault(new Locale("es"));//uncomment in order to change language to spanish
        System.out.println(locale.getDisplayLanguage().equalsIgnoreCase("English"));
        String userName;
        String userPass;
        String userNameID;
        
        //login begin
        try(
         PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT userName, password, userid FROM user");
            ){
           ResultSet rs2 = statement.executeQuery();
            
            while (rs2.next()) {
                userName = rs2.getString("user.userName");

                userPass = rs2.getString("user.password");
                userNameID = rs2.getString("user.userID");
                System.out.println("userNameID: "+userNameID);
                
                if(userName.equals(userNameText.getText()) && userPass.equals(passwordText.getText())){
                    //open customers page
                     try {
                   CurrentUser.setCurrentUserName(userName,userNameID);
                   
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(FXMLDocumentController.this.getClass().getResource("CustomerRecords.fxml"));
                   /*
                   * if "fx:controller" is not set in fxml
                   * fxmlLoader.setController(NewWindowController);
                   */
BufferedWriter bwTimestamp = null;

      try {
        
         bwTimestamp = new BufferedWriter(new FileWriter("LoginTimestamp.txt", true));
	 bwTimestamp.write(userName + ": " + LocalDateTime.now());
	 bwTimestamp.newLine();
	 bwTimestamp.flush();
      } catch (IOException ioe) {
	 ioe.printStackTrace();
      } finally {                       //close the file
	 if (bwTimestamp != null) try {
	    bwTimestamp.close();
	 } catch (IOException ioe2) {
	    
	 }
      }


        
                   Scene addPartScene = new Scene(fxmlLoader.load(), 600, 600);
                   Stage stage = new Stage();
                   stage.setTitle("Customers");
                   stage.setScene(addPartScene);
                   stage.show();
                   
                   
                   
               } catch (IOException e) {
                   Logger logger = Logger.getLogger(FXMLDocumentController.this.getClass().getName());
                   logger.log(Level.SEVERE, "Failed to create new Window.", e);
               }
           
                }
                else{
               if(locale.getDisplayLanguage().equalsIgnoreCase("English")){
                 loginError.setText("The Username or Password does not match our records.");
               }
               else{
                 loginError.setText("El nombre de usuario o la contraseña no coinciden " + System.lineSeparator() + "con nuestros registros.");
            
               }
                }


            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL3");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
        
        
    }
    
    //create test user button username: test password: test
    
    @FXML
    public List<User> createTestUser(){
        String userName;
        String password;
        String createdBy;            
            
            
            // Statement stmt1 = conn1.createStatement();
             LocalDateTime currentDateTime = LocalDateTime.now();
             Timestamp timestamp = Timestamp.valueOf(currentDateTime);
             

        String apptUserID = "ALTER TABLE appointment ADD userid INT;";//update appt table with userid to match Project Database ERD
       
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(apptUserID);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String apptType = "ALTER TABLE appointment ADD type TEXT;";//update appt table with type to match Project Database ERD
       
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(apptType);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //test user insert
        String sql = "INSERT INTO user (userid, userName, password, active, createBy,createDate,lastUpdatedBy) VALUES (1,'test', 'test', 1, 'test',now(),'test')";
       ExecuteInsert.ExecuteInsert(sql);
        //end test user insert

        
        //insert default countries (United States and England)
        String sql1 = "INSERT INTO country (countryid, country, createDate, createdBy, lastUpdateBy) VALUES (1,'United States', now(), 'test','test')";
        String sql2 = "INSERT INTO country (countryid, country, createDate, createdBy, lastUpdateBy) VALUES (2,'England', now(), 'test','test')";

        ExecuteInsert.ExecuteInsert(sql1);
        ExecuteInsert.ExecuteInsert(sql2);
        //end insert default countries
        
        //insert default cities (New York, New York), (Phoenix, Arizona) and London
        String sql3 = "INSERT INTO city (cityid, city, countryid,createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (1,'New York, New York',1 ,now(), 'test',now(),'test')";
        String sql4 = "INSERT INTO city (cityid, city, countryid,createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (2,'Phoenix, Arizona',1 ,now(), 'test',now(),'test')";
        String sql5 = "INSERT INTO city (cityid, city, countryid,createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (3,'London',2 ,now(), 'test',now(),'test')";
        ExecuteInsert.ExecuteInsert(sql3);
        ExecuteInsert.ExecuteInsert(sql4);
        ExecuteInsert.ExecuteInsert(sql5);
        
        //end insert cities
        
        //check that insertion succeeded
        String tId;
        String tName;
        String tAddress;
        String tZip;
        ArrayList<User> userList = new ArrayList();
        
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT userName, password, createBy FROM user");
            ){
           ResultSet rs2 = statement.executeQuery();
            
            while (rs2.next()) {
                tId = rs2.getString("user.userName");

                tName = rs2.getString("user.password");

                tAddress = rs2.getString("user.createBy");
                
    

                userList.add(new User(tId, tName, tAddress));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL3");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }

         userTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        // userTable.getColumns().clear();
         userTable.getItems().setAll(userList); /* takes the list from the parseCustomerList() 
         method, and addes the rows to the TableView */
         
//         userTable.getSelectionModel().selectedItemProperty().addListener(
//            (observable, oldValue, newValue)->showCustomerDetails(newValue));
         
         
        return userList;
    }
    
}
