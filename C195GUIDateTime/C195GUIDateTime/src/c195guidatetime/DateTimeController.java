/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195guidatetime;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author amy.antonucci
 */
public class DateTimeController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cboHour;

    @FXML
    private ComboBox<String> cboMin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String start = accessDB();
        System.out.println(start);
        String date = start.substring(0, 10);
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        String hour = start.substring(11, 13);
        String minute = start.substring(14, 16);
        LocalDate ldDate = LocalDate.of(year, month, day);
        System.out.println(hour + " " + minute + " " + date);
        datePicker.setValue(ldDate);

        for (int i = 1; i < 24; i++) {
            cboHour.getItems().add(Integer.toString(i));
        }

        cboHour.getSelectionModel().select(hour);

        cboMin.getItems().add("00");
        cboMin.getItems().add("15");
        cboMin.getItems().add("30");
        cboMin.getItems().add("45");

        cboMin.getSelectionModel().select(minute);

    }

    public String accessDB() {
        //Connection String
        //Server name:  52.206.157.109
        //Database name:  U03QIu
        //Username:  U03QIu
        //Password:  53688051379
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://52.206.157.109/U03QIu";

        //  Database credentials
        final String DBUSER = "U03QIu";
        final String DBPASS = "53688051379";

        Connection conn;
        boolean res = false;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);

            Statement stmt;

            ResultSet rs = null;
            try {

                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT start FROM appointment");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            rs.next();
            return rs.getString(1);

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
