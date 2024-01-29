/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195insertexample;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author amy.antonucci
 */
public class InsertController implements Initializable {

    @FXML
    private TableView<Country> table;

    @FXML
    private TableColumn<Country, String> colCountry;

    @FXML
    private Button btnInsert;

    Connection conn;
    Country selected;

    ObservableList<Country> countryList = FXCollections.observableArrayList();

    ;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String countryName = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter New Country");
        dialog.setHeaderText("Please enter a new country name");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            countryName = result.get();
        }
        String countryId = insertDB(countryName);
        countryList.add(new Country(new SimpleStringProperty(countryId), new SimpleStringProperty(countryName)));
        table.getItems().clear();
        table.getItems().addAll(countryList);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accessDB();

        colCountry.setCellValueFactory(cellData -> {
            return cellData.getValue().getCountry();
        });
        table.getItems().addAll(countryList);
    }

    public void accessDB() {
//        Server name:  52.206.157.109 
//Database name:  U03QIu
//Username:  U03QIu
//Password:  53688051379
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://52.206.157.109:3306/U04pwU";

        //  Database credentials: please fill in your own database information! 
        final String DBUSER = "U04pwU";
        final String DBPASS = "53688311282";

        boolean res = false;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            try {
                countryList.clear();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT countryId, country FROM country");

                while (rs.next()) {
                    countryList.add(new Country(new SimpleStringProperty(rs.getString(1)), new SimpleStringProperty(rs.getString(2))));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    String insertDB(String newCountry) {

        //Note that this assumes an autoincrement on the country id column! 
        String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, now(), 'test', now(), 'test')";
        String countryId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newCountry);

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM country"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            countryId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return countryId;
    }
}
