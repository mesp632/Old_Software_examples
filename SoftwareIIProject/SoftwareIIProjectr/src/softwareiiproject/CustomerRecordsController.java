/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Saelin
 */
public class CustomerRecordsController implements Initializable {

@FXML
    private TableColumn<Customer, String> custID;
    @FXML
    private TableColumn<Customer, String> custTableName;
    @FXML
    private TableView <Customer> custTable;
    
    
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label zipLabel;

    @FXML
    private TextField idText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField zipText;
    @FXML
    private TextField addressIDText;
    @FXML
    private TextField phoneText;
    
    @FXML
    private ChoiceBox countryOptions;
    @FXML
    private ChoiceBox cityOptions;
    
    //
    @FXML
    private Label idLabel2;
    @FXML
    private Label nameLabel2;
    @FXML
    private Label addressLabel2;
    @FXML
    private Label zipLabel2;

    @FXML
    private TextField idText2;
    @FXML
    private TextField nameText2;
    @FXML
    private TextField addressText2;
    @FXML
    private TextField zipText2;
    @FXML
    private TextField addressIDText2;
    @FXML
    private TextField phoneText2;
    
    @FXML
    private ChoiceBox countryOptions2;
    @FXML
    private ChoiceBox cityOptions2;
    
    @FXML
    private Button addCustomer;
    @FXML
    private Button deleteCustomer;
    @FXML
    private Button updateCustomer;
      
    //lists for countries and cities
    ListProperty<String> countryList = new SimpleListProperty<String>(FXCollections.<String>observableArrayList());
    ListProperty<String> cityList = new SimpleListProperty<String>(FXCollections.<String>observableArrayList());
    ArrayList<Customer> custList = new ArrayList();
    String outputCountry= null;
    String outputCountry2=null;
    ZoneId currentZone = ZoneId.systemDefault();
            
    public CustomerRecordsController() {
        
    }
    @FXML /* port information over to the form */
    private void showCustomerDetails(Customer selectedCustomer) {
     
        idText2.setText(selectedCustomer.getId());
        nameText2.setText(selectedCustomer.getName());
        addressText2.setText(selectedCustomer.getAddress());
        zipText2.setText(selectedCustomer.getZip());
        phoneText2.setText(selectedCustomer.getPhone());
        addressIDText2.setText(selectedCustomer.getAddressID());
       
        countryOptions2.setValue(selectedCustomer.getCountry());
        System.out.println("show customer details country: " + selectedCustomer.getCountry());
        
        cityOptions2.setValue(selectedCustomer.getCity());
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //check for appointment in 15 minutes
        

        
        List<AppointmentDetails> ApptList = null;
    try {
        ApptList = parseApptList(2L); //get appointments for the next year to check against
    } catch (SQLException ex) {
        Logger.getLogger(CustomerRecordsController.class.getName()).log(Level.SEVERE, null, ex);
    }
        for(AppointmentDetails row: ApptList){
            if(row.getStart().toLocalDate().equals(LocalDateTime.now().toLocalDate())){
            LocalDateTime start = row.getStart();
            
            
             if ((start.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now().plusMinutes(15)))) {
               infoBox("You have an appointment within 15 minutes", "Notice", null);
             }
            }
            
            }
        //end check for appointment within 15 minutes
        
        
        
         custID.setCellValueFactory(new PropertyValueFactory<>("id"));
         custTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
         
         custTable.getItems().setAll(parseCustomerList()); /* takes the list from the parseCustomerList() 
         method, and addes the rows to the TableView */
         addCustomer.setDisable(false);
         deleteCustomer.setDisable(true);
         updateCustomer.setDisable(true);
         
         //lamda expression in the following listeners simplifies the code
         //customer table listeners here
         custTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->showCustomerDetails(newValue));
         
        
        custTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)-> 
        deleteCustomer.setDisable(false));//listeners that update the delete customer button when customer is selected
        custTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)-> 
        updateCustomer.setDisable(false));//listeners that update the updatecustomer button when customer is selected
        cityOptions.setDisable(true);
        
        
        custTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observableValue, Customer oldValue, Customer newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (custTable.getSelectionModel().getSelectedItem() != null) {
                Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
                CurrentCustomer.setCurrentCustomer(selectedCustomer.getName(),selectedCustomer.getId());
                
            }
         });//this listener sets the currently selected customer to whatever the user chooses
        
        //end of customer table listeners

        
        
        
         //create country list from query
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT country FROM country");
            ){
           ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                countryList.add(rs.getString("country.country"));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
         //end query to create country list
         //send countries to optionbox
         countryOptions.setItems(FXCollections.observableArrayList(countryList));
         countryOptions2.setItems(FXCollections.observableArrayList(countryList));
         
        
    }    
    

    private List<Customer> parseCustomerList() {
      
        String tId = null;
        String tName=null;
        String tAddress=null;
        String tZip=null;
        String tPhone=null;
        String tAddressID=null;
        String tCityID=null;
        String tCity=null;
        String tCountryID = null;
        String tCountry=null;
        ArrayList<Customer> custList = new ArrayList();
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT customer.customerId, customer.customerName, address.phone, address.addressId,"
                + "address.address, address.postalCode, city.city, country.country FROM customer, address,city,country WHERE customer.addressId = address.addressId AND address.cityid=city.cityid AND city.countryID=country.countryID;");
            ResultSet rs = statement.executeQuery();){
           
            
            
            while (rs.next()) {
                tId = rs.getString("customer.customerID");

                tName = rs.getString("customer.customerName");

                tAddress = rs.getString("address.address");
                
                tZip = rs.getString("address.postalCode");
                
                tPhone=rs.getString("address.phone");
                
                tAddressID=rs.getString("address.addressId");
                
             //   tCityID=rs.getString("address.cityID");
                
                tCity=rs.getString("city.city");
                
                tCountry=rs.getString("country.country");
                
                
        
        custList.add(new Customer(tId, tName, tAddress, tZip,tPhone,tAddressID,tCity, tCountry));
         
                

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
        
        
         
        return custList;

    }
    
    //add customer
    
    @FXML
    public void addCustomer(ActionEvent event) throws SQLException{
        Integer customerID = null;
        String customerName = null;
        Integer addressID = null;
        String address1 = null;
        Integer cityID = null;
        String postalCode = null;
        String phone = null;
        try{
        customerID = Integer.parseInt(idText.getText());
        customerName = nameText.getText();
        addressID = Integer.parseInt(addressIDText.getText());
       
        address1 = addressText.getText();
        postalCode = zipText.getText();
        phone = phoneText.getText();
        }
        catch(NumberFormatException e){
            idText.setStyle("-fx-text-inner-color: red;");
            idText.setText("e.g. 1");
            
            addressIDText.setStyle("-fx-text-inner-color: red;");
            addressIDText.setText("e.g. 1");
            
        }
        
        //get cityID
        String outputCity = cityOptions.getSelectionModel().getSelectedItem().toString();//get selected city
        System.out.println("current city:"+ outputCity);
        //if a country is selected
        if(outputCity != null){
            //query for country ID
            
            try(PreparedStatement cityIDRequest = DBConnection.getConn().prepareStatement("SELECT cityid FROM city WHERE city = ?");
            
            ){
                cityIDRequest.setString(1,outputCity);
                ResultSet rs = cityIDRequest.executeQuery();
            
               while (rs.next()) {
                cityID = rs.getInt("cityid");
                System.out.println("cityID:" + cityID);
            }
              
               
               
            } catch(SQLException sqe){
                
            System.out.println("Check your SQL for countryID");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
        //end get cityID
         //begin insert address info
       String addressSQL = "INSERT INTO address (addressid, address, address2, cityID, postalcode,phone,createdate, createdBy,lastupdate, lastUpdateBy) VALUES (?,?, '', ?, ?,?,now(),'test',now(),'test')";
      try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(addressSQL);
            ps.setInt(1, addressID);
            ps.setString(2,address1);
            ps.setInt(3, cityID);
            ps.setString(4,postalCode);
            ps.setString(5,phone);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
      //begin add customer info
         String customerSQL = "INSERT INTO customer (customerid, customerName, addressID, active,createdate, createdBy,lastupdate, lastUpdateBy) VALUES (?,?,?, 1,now(),'test',now(),'test')";
      try (PreparedStatement ps = DBConnection.getConn().prepareStatement(customerSQL);){
            
            ps.setInt(1, customerID);
            ps.setString(2,customerName);
            ps.setInt(3, addressID);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        }
      //  custTable.getItems().clear();
        custTable.getItems().setAll(parseCustomerList());
    }
    //delete customer handler
    @FXML
    protected void deleteCustomer(ActionEvent event) throws SQLException{
        Integer custIDToDelete = Integer.parseInt(custTable.getSelectionModel().getSelectedItem().getId());
        
        System.out.println("id to delete: " +custIDToDelete);
        String custDeleteSQL = "DELETE FROM customer where customerid = ?";
        
        try(PreparedStatement deleteCustSQL = DBConnection.getConn().prepareStatement(custDeleteSQL)){
            deleteCustSQL.setInt(1, custIDToDelete);
            deleteCustSQL.execute();
        } catch(SQLException e){
            System.out.println("Check your SQL for customerIDdelete");
        } catch(Exception o){
            System.out.println("other exeption in delete handler");
        }
        System.out.println("null test1");
       // custTable.getItems().clear();
        System.out.println("null test2");
        custTable.getItems().setAll(parseCustomerList());
        System.out.println("null test 3");
    }
    
    //update customer record handler
    @FXML
    protected void updateCustomer(ActionEvent event) throws SQLException{
        Integer updateCustID = Integer.parseInt(custTable.getSelectionModel().getSelectedItem().getId());
        
                Integer customerID = Integer.parseInt(idText2.getText());
        String customerName = nameText2.getText();
        Integer addressID = Integer.parseInt(addressIDText2.getText());
        System.out.println(addressID);
        String address1 = addressText2.getText();
        Integer cityID = null;
        String postalCode = zipText2.getText();
        String phone = phoneText2.getText();
        
        //get cityID
        String outputCity = cityOptions2.getSelectionModel().getSelectedItem().toString();//get selected city
        System.out.println("current city:"+ outputCity);
        //if a country is selected
        if(outputCity != null){
            //query for country ID
            
            try(PreparedStatement cityIDRequest = DBConnection.getConn().prepareStatement("SELECT cityid FROM city WHERE city = ?");
            
            ){
                cityIDRequest.setString(1,outputCity);
                ResultSet rs = cityIDRequest.executeQuery();
            
               while (rs.next()) {
                cityID = rs.getInt("cityid");
                System.out.println("cityID:" + cityID);
            }
              
               
               
            } catch(SQLException sqe){
                
            System.out.println("Check your SQL for cityID");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
        //end get cityID
         //begin insert address info
       String addressSQL = "UPDATE address SET address = ?, address2 = '', cityID = ?, postalcode = ?,phone = ?,lastupdate=now(), lastUpdateBy='test' WHERE addressid=?";
      try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(addressSQL);
            
            ps.setString(1,address1);
            ps.setInt(2, cityID);
            ps.setString(3,postalCode);
            ps.setString(4,phone);
            ps.setInt(5, addressID);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
      //begin add customer info
         String customerSQL = "UPDATE customer SET customerName=?, addressID=?, lastupdate=now(), lastUpdateBy='test' WHERE customerid = ?";
      try (PreparedStatement ps = DBConnection.getConn().prepareStatement(customerSQL);){
            
            
            ps.setString(1,customerName);
            ps.setInt(2, addressID);
            ps.setInt(3, customerID);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        }
      //  custTable.getItems().clear();
        custTable.getItems().setAll(parseCustomerList());
        
    }
    
    
    //select country handler
    
    @FXML
    protected void SelectCountry( ActionEvent event ) throws SQLException { 
          outputCountry = (String) countryOptions.getSelectionModel().getSelectedItem();
          outputCountry2 = (String) countryOptions2.getSelectionModel().getSelectedItem();
          System.out.println(outputCountry);
//          setCityOptionList(outputCountry);
          
         Integer countryID=null;//reserved for countryID
        //if a country is selected
        if(outputCountry != null){
            //query for country ID
            
            try(PreparedStatement countryIDRequest = DBConnection.getConn().prepareStatement("SELECT countryid FROM country WHERE country = ?");
            
            ){
               
                countryIDRequest.setString(1,outputCountry);
                ResultSet rs = countryIDRequest.executeQuery();
                
                
               while (rs.next()) {
                countryID = rs.getInt("countryid");

            }
              
               System.out.println("countryID:" + countryID);
               
            } catch(SQLException sqe){
                
            System.out.println("Check your SQL for countryID");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
                     //query for list of cities
         try(
         PreparedStatement statementCity = DBConnection.getConn().prepareStatement("SELECT city FROM city WHERE city.countryid= ?");
            ){
             statementCity.setInt(1,countryID);
           ResultSet rsCity = statementCity.executeQuery();
            cityList.clear();
            while (rsCity.next()) {
                cityList.add(rsCity.getString("city.city"));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for city options");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
         //end query to create city list
         //send countries to optionbox
         System.out.println(cityList);
         cityOptions.setItems(cityList);
         cityOptions.getSelectionModel().selectFirst();
         
    }

    }
    
    
    @FXML
    protected void SelectCountry2( ActionEvent event ) throws SQLException { 
          
          outputCountry2 = (String) countryOptions2.getSelectionModel().getSelectedItem();
          System.out.println(outputCountry2);
//          setCityOptionList(outputCountry);
          
         Integer countryID=null;//reserved for countryID
        //if a country is selected
        if(outputCountry2 != null){
            //query for country ID
            
            try(PreparedStatement countryIDRequest = DBConnection.getConn().prepareStatement("SELECT countryid FROM country WHERE country = ?");
            
            ){
               
                countryIDRequest.setString(1,outputCountry2);
                ResultSet rs = countryIDRequest.executeQuery();
                
                
               while (rs.next()) {
                countryID = rs.getInt("countryid");

            }
              
               System.out.println("countryID:" + countryID);
               
            } catch(SQLException sqe){
                
            System.out.println("Check your SQL for countryID");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
                     //query for list of cities
         try(
         PreparedStatement statementCity = DBConnection.getConn().prepareStatement("SELECT city FROM city WHERE city.countryid= ?");
            ){
             statementCity.setInt(1,countryID);
           ResultSet rsCity = statementCity.executeQuery();
            cityList.clear();
            while (rsCity.next()) {
                cityList.add(rsCity.getString("city.city"));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL for city options");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
         //end query to create city list
         //send countries to optionbox
         
         cityOptions2.setItems(cityList);
         cityOptions2.getSelectionModel().selectFirst();
    }

    }
    
   
    
    
    @FXML
    protected void SelectCity(ActionEvent event){
      
        
        
        cityOptions.setDisable(false);
        cityOptions2.setDisable(false);
        String outputCity=null;
        if(!cityOptions.getSelectionModel().isEmpty()){
         outputCity = cityOptions.getSelectionModel().getSelectedItem().toString();
        }
        if(!cityOptions2.getSelectionModel().isEmpty()){
         String outputCity2 = cityOptions2.getSelectionModel().getSelectedItem().toString();
        }
        System.out.println(outputCity);
        
        
    }
    
    @FXML
    protected void openApptMenu(ActionEvent event){
         try {
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(CustomerRecordsController.this.getClass().getResource("Appointments.fxml"));
                   /*
                   * if "fx:controller" is not set in fxml
                   * fxmlLoader.setController(NewWindowController);
                   */
                   Scene addPartScene = new Scene(fxmlLoader.load(), 600, 650);
                   Stage stage = new Stage();
                   stage.setTitle("Appointments");
                   stage.setScene(addPartScene);
                   stage.show();
                   
                   
                   
               } catch (IOException e) {
                   Logger logger = Logger.getLogger(CustomerRecordsController.this.getClass().getName());
                   logger.log(Level.SEVERE, "Failed to create new Window.", e);
               }
    }
    
    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    private List<AppointmentDetails> parseApptList( Long wmview) throws SQLException {
      
    
        
        String tapptID;
        String tcustID;
        String tuserID;
        String ttitle;
        String tdescription;
        String ttype;
        Timestamp tstart;
        Timestamp tend;
        ArrayList<AppointmentDetails> apptList = new ArrayList();
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT appointment.appointmentid, appointment.customerid, appointment.userid, appointment.title,"
                + "appointment.description, appointment.type, appointment.start, appointment.end FROM appointment");
            ResultSet rs = statement.executeQuery();){
           
           
                
            
            while (rs.next()) {
                tapptID = rs.getString("appointment.appointmentid");
                tcustID = rs.getString("appointment.customerid");
                tuserID = rs.getString("appointment.userid");
                ttitle = rs.getString("appointment.title"); 
                tdescription=rs.getString("appointment.description");
                ttype=rs.getString("appointment.type");
                tstart=rs.getTimestamp("appointment.start");
                tend=rs.getTimestamp("appointment.end");
                
                
                Instant timestamp = tstart.toInstant();//convert timestamp to instant
                ZonedDateTime testst= timestamp.atZone(currentZone);//convert instant to zoneddatetime
                ZonedDateTime UTCst = testst.withZoneSameLocal(ZoneId.of("UTC"));//change timezone to UTC
               
                ZonedDateTime lst = UTCst.withZoneSameInstant(currentZone);//convert UTC datetime to localZoneID datetime
                LocalDateTime startldt =lst.toLocalDateTime();//variable for start localdateTime
                System.out.println(startldt);
                
                //end timestamp
                System.out.println(tend);
                Instant etimestamp = tend.toInstant();//convert timestamp to instant
                ZonedDateTime etestst= etimestamp.atZone(currentZone);//convert instant to zoneddatetime
                ZonedDateTime eUTCst = etestst.withZoneSameLocal(ZoneId.of("UTC"));//change timezone to UTC
               
                ZonedDateTime elst = eUTCst.withZoneSameInstant(currentZone);//convert UTC datetime to localZoneID datetime
                LocalDateTime endldt =elst.toLocalDateTime();//variable for end localdateTime
                System.out.println(endldt);
                
                 int res = startldt.compareTo(LocalDateTime.now().plusDays(wmview));//if appointment is within time window (week or month)//if appointment is within time window (week or month)
                 
            if(res <=0){     
            apptList.add(new AppointmentDetails(tapptID,tcustID,tuserID,ttitle,tdescription,
            ttype,startldt,endldt));
            }//this will only add the appointment if it is in the correct window of time
         
                

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
        
        
        
         
        

    
        return apptList;
    }
}

