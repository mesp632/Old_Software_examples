/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiiproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.*; 
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Saelin
 */
public class AppointmentsController implements Initializable {
    
    @FXML
    private TableColumn<AppointmentDetails, String> ApptID;
    @FXML
    private TableColumn<AppointmentDetails, String> StartDateTime;
    @FXML
    private TableView <AppointmentDetails> ApptsTable;
    
    @FXML
    private TextField CustomerIDField;
    @FXML
    private TextField UserIDField;
    @FXML
    private TextField ApptIDField;
    @FXML
    private TextField TitleField;
    @FXML
    private TextField DescriptionText;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField DateField;
    @FXML
    private TextField StartField;
    @FXML
    private TextField ApptLengthField;
    
    @FXML
    private TextField EditCustomerIDField;
    @FXML
    private TextField EditUserIDField;
    @FXML
    private TextField EditApptIDField;
    @FXML
    private TextField EditTitleField;
    @FXML
    private TextField EditDescriptionText;
    @FXML
    private TextField EditTypeField;
    @FXML
    private TextField EditDateField;
    @FXML
    private TextField EditStartField;
    @FXML
    private TextField EditApptLengthField;
    @FXML
    private Label ErrorNotice;
    @FXML
    private Label EditErrorNotice;
    @FXML
    private TextField ConsultantID;
    
    ZoneId currentZone = ZoneId.systemDefault();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String CustID=CurrentCustomer.getCurrentCustomerID();
        CustomerIDField.setText(CurrentCustomer.getCurrentCustomerID());
       // System.out.println(CurrentUser.getCurrentUserID());
        UserIDField.setText(CurrentUser.getCurrentUserID());
        
        //String currentZone = TimeZone.getDefault().getID();
        
        
        //beginning create Appointments TableView
        
        ApptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
         StartDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
         Long month = 30L;
         ApptsTable.getItems().setAll(parseApptList(month)); /* takes the list from the parseApptsList()
         method, and addes the rows to the TableView */
         
         ApptsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue)->showApptDetails(newValue));
//a lambda expression in the listener is the shortest and simplest(makes code more readable) way to change the old values to any new ones that appear
         
        
       
        
        
         ApptsTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AppointmentDetails> observableValue, AppointmentDetails oldValue, AppointmentDetails newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (ApptsTable.getSelectionModel().getSelectedItem() != null) {
                AppointmentDetails selectedAppointment = ApptsTable.getSelectionModel().getSelectedItem();
                CurrentCustomer.setCurrentCustomer(selectedAppointment.getApptID(),selectedAppointment.getStart().toString());
                
            }
         });
        
        

        
     
         
        
    }    
    
        @FXML /* port information over to the form */
    private void showApptDetails(AppointmentDetails selectedAppointment) {
     
        EditApptIDField.setText(selectedAppointment.getApptID());
        EditCustomerIDField.setText(selectedAppointment.getCustID());
        EditUserIDField.setText(selectedAppointment.getUserID());
        EditTitleField.setText(selectedAppointment.getTitle());
        EditDescriptionText.setText(selectedAppointment.getDescription());
        EditTypeField.setText(selectedAppointment.getType());
        EditDateField.setText(selectedAppointment.getStart().toLocalDate().format(dateFormat));
        EditStartField.setText(selectedAppointment.getStart().toLocalTime().toString());
        long minutes = selectedAppointment.getStart().until(selectedAppointment.getEnd(), ChronoUnit.MINUTES);
        EditApptLengthField.setText(Long.toString(minutes));
       
    }
    

    private List<AppointmentDetails> parseApptList( Long wmview) {
      
    
        
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
                 
            if(res <=0 && startldt.isAfter(LocalDateTime.now().minusDays(1L))){     
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
        
        
     
    
    @FXML
    public void addAppointment(ActionEvent event) throws IOException, SQLException{
        Integer apptid = Integer.parseInt(ApptIDField.getText());
        Integer custid = Integer.parseInt(CustomerIDField.getText());
        Integer userid = Integer.parseInt(UserIDField.getText());
        String title = TitleField.getText();
        String description = DescriptionText.getText();
        String type = TypeField.getText();
        String date = DateField.getText();
        //check Date Pattern
        LocalDate startDate = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try{
        startDate = LocalDate.parse(DateField.getText(), dateFormat); 
        }
        catch(DateTimeParseException e){
         DateField.setText("MM/DD/YYYY");
        }
       
        
        DateTimeFormatter startTimeFormat = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime startTime = LocalTime.parse(StartField.getText(),startTimeFormat);
        
        System.out.println("currentZone:" + currentZone);
        
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(startDate, startTime, currentZone);
        Instant instant = zonedStartDateTime.toInstant();//convert appt date and time to UTC
        LocalDateTime startUTC = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);// convert appt date and time to LocalDateTime variable
        
        
        LocalDateTime endUTC = startUTC.plusMinutes(Integer.parseInt(ApptLengthField.getText()));
        
        Timestamp startUTCTimestamp = Timestamp.valueOf(startUTC);//convert localDateTime to Timestamp variable to insert into sql
        Timestamp endUTCTimestamp = Timestamp.valueOf(endUTC);
        
        //check for overlapping appointments
        LocalTime endTime = startTime.plusMinutes(Long.parseLong(ApptLengthField.getText()));
        List<AppointmentDetails> ApptList = parseApptList(365L);//get appointments for the next year to check against
        for(AppointmentDetails row: ApptList){
            if(row.getStart().toLocalDate().equals(startUTC.toLocalDate())){
            row.getStart();
            row.getEnd();
            //check to make sure appointments don't overlap
             if ((startTime.isAfter(row.getStart().toLocalTime()) && startTime.isBefore(row.getEnd().toLocalTime()))
                    || (endTime.isAfter(row.getStart().toLocalTime()) && endTime.isBefore(row.getEnd().toLocalTime()))
                     || startTime.equals(row.getStart().toLocalTime()) || endTime.equals(row.getEnd().toLocalTime())) {
               ErrorNotice.setText("Appointment overlaps with another");
                throw new IOException("Appointment overlaps with another");
             }
            }
            
            }
       //get business hours
    String string1 = "07:59";
    LocalTime time1 = LocalTime.parse(string1,startTimeFormat);
    System.out.println("test" + time1);

    String string2 = "16:01";
    LocalTime time2 = LocalTime.parse(string2,startTimeFormat);

    if (startTime.isAfter(time1) && endTime.isBefore(time2)) {
        //checkes whether the current time is between 09:00:00 and 16:00:00.
        

        String insertAppt = "INSERT INTO appointment (appointmentid,customerid,userid,title,description,location,contact,type,url,start,end,createdate,createdby,lastupdate,lastupdateby)" +
                "VALUES (?,?,?,?,?,'','test',?,'',?,?,now(),'test',now(),'test')";
           try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(insertAppt);
            ps.setInt(1, apptid);
            ps.setInt(2,custid);
            ps.setInt(3, userid);
            ps.setString(4,title);
            ps.setString(5,description);
            ps.setString(6,type);
            ps.setTimestamp(7, startUTCTimestamp);
            ps.setTimestamp(8, endUTCTimestamp);
            ps.execute();
            ErrorNotice.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        }
    else{
        ErrorNotice.setText("Appt must be between 08:00-16:00");
        throw new IOException("Time is outside business hours");
        
    }
     ApptsTable.getItems().clear();//clear Appt Table
     ApptsTable.getItems().setAll(parseApptList(30L));//repopulate Appt table with updated data
            }
    @FXML
    private void MonthView(ActionEvent event) throws SQLException{
    Long month = 30L;
    ApptsTable.getItems().clear();
    ApptsTable.getItems().setAll(parseApptList(month));
    }
    @FXML
    private void WeekView(ActionEvent event) throws SQLException{
        Long week = 7L;
        ApptsTable.getItems().clear();
        ApptsTable.getItems().setAll(parseApptList(week));
    }
    @FXML
    private void editAppointment(ActionEvent event) throws IOException, SQLException{
        Integer apptid = Integer.parseInt(EditApptIDField.getText());
        Integer custid = Integer.parseInt(EditCustomerIDField.getText());
        Integer userid = Integer.parseInt(EditUserIDField.getText());
        String title = EditTitleField.getText();
        String description = EditDescriptionText.getText();
        String type = EditTypeField.getText();
        String date = EditDateField.getText();
        //check Date Pattern
        LocalDate startDate = null;
        
        try{
        startDate = LocalDate.parse(EditDateField.getText(), dateFormat); 
        }
        catch(DateTimeParseException e){
         EditDateField.setText("MM/DD/YYYY");
        }
        DateTimeFormatter startTimeFormat = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime startTime = LocalTime.parse(EditStartField.getText(),startTimeFormat);
        
        System.out.println("currentZone:" + currentZone);
        
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(startDate, startTime, currentZone);
        Instant instant = zonedStartDateTime.toInstant();//convert appt date and time to UTC
        LocalDateTime startUTC = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);// convert appt date and time to LocalDateTime variable
        LocalDateTime endUTC = startUTC.plusMinutes(Integer.parseInt(EditApptLengthField.getText()));
        Timestamp startUTCTimestamp = Timestamp.valueOf(startUTC);//convert localDateTime to Timestamp variable to insert into sql
        Timestamp endUTCTimestamp = Timestamp.valueOf(endUTC);
        
        
                LocalTime endTime = startTime.plusMinutes(Long.parseLong(EditApptLengthField.getText()));
                String selectedAppt = ApptsTable.getSelectionModel().getSelectedItem().getApptID();
        List<AppointmentDetails> ApptList = parseApptList(365L);//get appointments for the next year to check against
        for(AppointmentDetails row: ApptList){
            if(row.getStart().toLocalDate().equals(startUTC.toLocalDate()) && !row.getApptID().equals(selectedAppt)){//if same local date but not same appointment
            row.getStart();
            row.getEnd();
            //check to make sure appointments don't overlap
             if ((startTime.isAfter(row.getStart().toLocalTime()) && startTime.isBefore(row.getEnd().toLocalTime()))
                    || (endTime.isAfter(row.getStart().toLocalTime()) && endTime.isBefore(row.getEnd().toLocalTime()))
                     || startTime.equals(row.getStart().toLocalTime()) || endTime.equals(row.getEnd().toLocalTime())) {
               EditErrorNotice.setText("Appointment overlaps with another");
                throw new IOException("Appointment overlaps with another");
             }
            }
            
            }
       //get business hours
    String string1 = "08:00";
    LocalTime time1 = LocalTime.parse(string1,startTimeFormat);
    System.out.println("test" + time1);

    String string2 = "16:00";
    LocalTime time2 = LocalTime.parse(string2,startTimeFormat);

    if (startTime.isAfter(time1) && endTime.isBefore(time2)) {
        //checkes whether the current time is between 09:00:00 and 16:00:00.
        
        
        String insertAppt = "UPDATE appointment SET title = ?,description = ?,type = ?,start=?,end=?,lastupdate=now() WHERE appointmentid=?";
           try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(insertAppt);
            
          
            ps.setString(1,title);
            ps.setString(2,description);
            ps.setString(3,type);
            ps.setTimestamp(4, startUTCTimestamp);
            ps.setTimestamp(5, endUTCTimestamp);
            ps.setInt(6,apptid);
            
            ps.execute();
            EditErrorNotice.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        }
    else{
        EditErrorNotice.setText("Appt must be between 08:00-16:00");
        throw new IOException("Time is outside business hours");
        
    }
     ApptsTable.getItems().clear();//clear Appt Table
     ApptsTable.getItems().setAll(parseApptList(30L));//repopulate Appt table with updated data
   }
    
    @FXML
    private void generateTypeLog(ActionEvent event) throws IOException, SQLException{
        String count = null;
        File destination = new File("AppointmentTypeCount.txt");
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT COUNT(DISTINCT appointment.type) AS count FROM appointment");//"SELECT COUNT(type) AS count FROM Appointment"
            ){
           ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                count = Integer.toString(rs.getInt("count"));
            }
                System.out.println(count);
        }
                
               
         
             
      try (BufferedWriter writer = new BufferedWriter(
                   new FileWriter(destination))) {
         
            writer.write("Number of distinc appointment types Over the next 30 days:" + count);//writes number of types to log called AppointmentTypeCount.txt
            writer.newLine();
         }
      
         
      }
    @FXML
    private void apptByConsultant(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException{
        String tapptID;
        String tcustID;
        String tuserID;
        String ttitle;
        String tdescription;
        String ttype;
        String tconsultant;
        Timestamp tstart;
        Timestamp tend;
        ArrayList<AppointmentDetails> apptList = new ArrayList();
        Stream<AppointmentDetails> stream = null;
        PrintWriter writer = new PrintWriter("Appointment.txt", "UTF-8");
        writer.println("Next 30 days of appointments for consultant ID" + ConsultantID.getText());//put stream into report here
        
        
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT appointment.appointmentid, appointment.customerid, appointment.title,"
                + "appointment.description, appointment.type, appointment.start, appointment.end FROM appointment WHERE appointment.userid=?");
            ){
            statement.setString(1,ConsultantID.getText());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tapptID = rs.getString("appointment.appointmentid");
                tcustID = rs.getString("appointment.customerid");
               
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
                
                 int res = startldt.compareTo(LocalDateTime.now().plusDays(30));//if appointment is within time window (week or month)//if appointment is within time window (week or month)
                 
            if(res <=0 && startldt.isAfter(LocalDateTime.now().minusDays(1L))){     
                
                 writer.println("Start" + startldt + "| End:" + endldt);
                 
                 
            }//this will only add the appointment if it is in the correct window of time
         
                

            }
            
            
          writer.close();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
    }
    
    @FXML
    private void deleteAppointment(ActionEvent event){
        Integer apptToDelete = Integer.parseInt(ApptsTable.getSelectionModel().getSelectedItem().getApptID());
        
        
        String apptDeleteSQL = "DELETE FROM appointment where appointmentid = ?";
        
        try(PreparedStatement deleteCustSQL = DBConnection.getConn().prepareStatement(apptDeleteSQL)){
            deleteCustSQL.setInt(1, apptToDelete);
            deleteCustSQL.execute();
        } catch(SQLException e){
            System.out.println("Check your SQL for apptIDdelete");
        } catch(Exception o){
            System.out.println("other exeption in delete handler");
        }
        ApptsTable.getItems().clear();//clear Appt Table
        ApptsTable.getItems().setAll(parseApptList(30L));//repopulate Appt table with updated data
    }
     
    @FXML
    private void generateConsultantIDs(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException{
        String tapptID;
        String tcustID;
        String tuserID;
        String ttitle;
        String tdescription;
        String ttype;
        
        Timestamp tstart;
        Timestamp tend;
        ArrayList<AppointmentDetails> apptList = new ArrayList();
        Stream<AppointmentDetails> stream = null;
        PrintWriter writer = new PrintWriter("ConsultantIDs.txt", "UTF-8");
        writer.println("Consultant IDs");//put stream into report here
        
        
        try(
        PreparedStatement statement = DBConnection.getConn().prepareStatement("SELECT DISTINCT user.userid FROM user");
            ){
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tuserID = rs.getString("user.userid");
                 writer.println(tuserID);
            }
            
            
          writer.close();
        } catch (SQLException sqe) {
            System.out.println("Check your SQL");
        } catch (Exception e) {
            System.out.println("Something besides the SQL went wrong.");
        }
    }
          
       
    
}
