/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formpractice;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author julie.taylor
 */
public class FXMLDocumentController implements Initializable {
    
 
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
      
    
    @FXML /* port information over to the form */
    private void showCustomerDetails(Customer selectedCustomer) {
     
        idText.setText(selectedCustomer.getId());
        nameText.setText(selectedCustomer.getName());
        addressText.setText(selectedCustomer.getAddress());
        zipText.setText(selectedCustomer.getZip());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         custID.setCellValueFactory(new PropertyValueFactory<>("id"));
         custTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
         
         custTable.getItems().setAll(parseCustomerList()); /* takes the list from the parseCustomerList() 
         method, and addes the rows to the TableView */
         
         custTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->showCustomerDetails(newValue));
        
    }    
    
    private List<Customer> parseCustomerList() {
      
        String tId;
        String tName;
        String tAddress;
        String tZip;
        ArrayList<Customer> custList = new ArrayList();
        try(
            
            
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT customer.customerId, customer.customerName, "
                + "address.address, address.postalCode FROM customer, address WHERE customer.addressId = address.addressId;");
            ResultSet rs = statement.executeQuery();){
           
            
            while (rs.next()) {
                tId = rs.getString("customer.customerID");

                tName = rs.getString("customer.customerName");

                tAddress = rs.getString("address.address");
                
                tZip = rs.getString("address.postalCode");

                custList.add(new Customer(tId, tName, tAddress, tZip));

            }
          
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }

         
        return custList;

    }
    
    
    
}
