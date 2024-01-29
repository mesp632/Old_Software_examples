/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

import static javafx.application.Application.launch;
import java.io.File;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Saelin
 */
public class SoftwareIIProject extends Application {
    
    private static Connection connection;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
    // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("/TestDir/FXMLDocument.fxml"));
       // System.out.println(new File("./TestDir").getAbsoluteFile());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection.init();
        connection = DBConnection.getConn();
        
        launch(args);
        DBConnection.closeConn();
    }
    
}
