/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195datepickertolocaldatetime;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author amy.antonucci
 */
public class MainController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cboHour;

    @FXML
    private ComboBox<String> cboMin;

    @FXML
    private Button btnOk;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        cboHour.setItems(hours);
        cboMin.setItems(minutes);
    }

    @FXML
    void handleButton(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        String hour = cboHour.getValue();
        String minute = cboMin.getValue();

        LocalDateTime ldt = LocalDateTime.of(date.getYear(), date.getMonthValue(),
                date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Time and Date");
        alert.setHeaderText(null);
        alert.setContentText("you chose " + ldt.toString());

        alert.showAndWait();
    }

    @FXML
    void handleDatePicker(ActionEvent event) {

    }

    @FXML
    void handleHour(ActionEvent event) {

    }

    @FXML
    void handleMinute(ActionEvent event) {

    }
}
