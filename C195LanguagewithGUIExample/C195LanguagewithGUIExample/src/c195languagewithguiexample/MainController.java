/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195languagewithguiexample;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author amy.antonucci
 */
public class MainController implements Initializable {

    @FXML
    private Label lblName;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnOk;

    ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        System.out.println(Locale.getDefault());

        lblName.setText(rb.getString("prompt"));
        btnOk.setText(rb.getString("button"));
        C195LanguagewithGUIExample.getStage().setTitle(rb.getString("title"));
    }

    @FXML
    void handleOk(ActionEvent event) {

        Alert a = new Alert(AlertType.INFORMATION);
        a.setContentText(rb.getString("greeting") + ", " + txtName.getText() + "!");
        a.setHeaderText(null);
        a.showAndWait();
    }
}
