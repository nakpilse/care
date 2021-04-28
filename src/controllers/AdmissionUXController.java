package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import models.Admission;
import models.Bed;
import models.Consultation;
import models.ERConsultation;
import models.Patient;
import models.Room;
import models.RoomRecord;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.FileKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;
import utils.FXDateEntry;
import utils.FXTextEntry;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class AdmissionUXController implements Initializable,UIController  {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab admissionTab;

    @FXML
    private TableView<Patient> t1table;

    @FXML
    private Label t1resultLbl;

    @FXML
    private JFXTextField t1searchF;

    @FXML
    private JFXButton t1searchBtn;

    @FXML
    private JFXButton t1addBtn;
    
    @FXML
    private Tab dischargedTab;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private TableView<Admission> t2table;

    @FXML
    private Label t2resultsLbl;

    @FXML
    private JFXButton t2searchBtn;

    @FXML
    private BorderPane t1BorderPane;
    
    @FXML
    private Tab opdTab;
    
    @FXML
    private JFXTextField t3searchF;

    @FXML
    private TableView<Consultation> t3table;

    @FXML
    private Label t3resultsLbl;

    @FXML
    private JFXButton t3searchBtn;

    @FXML
    private JFXButton t3addPatientBtn;

    @FXML
    private JFXButton t3addConsultationBtn;
    
    @FXML
    private Tab erTab;
    
    @FXML
    private JFXTextField t4searchF;

    @FXML
    private TableView<ERConsultation> t4table;

    @FXML
    private Label t4resultsLbl;

    @FXML
    private JFXButton t4searchBtn;

    @FXML
    private JFXButton t4addPatientBtn;

    @FXML
    private JFXButton t4addERConsultationBtn;

    @FXML
    private Tab patientTab;

    @FXML
    private JFXTextField t5searchF;

    @FXML
    private TableView<Patient> t5table;

    @FXML
    private Label t5resultsLbl;

    @FXML
    private JFXButton t5searchBtn;

    @FXML
    private JFXButton t5addPatientBtn;
    
    private FlowPane terminalFlowPane;

    private ScrollPane terminalFlowScrollPane;
    
    
    @FXML
    void addConsultation(ActionEvent event) {
        try{
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35,25,25,25));

            List<Patient> patients = SQLTable.list(Patient.class);

            List<String> chargesuggestions = new ArrayList();
            patients.stream().forEach(rec->{
                chargesuggestions.add(rec.getFullname());
            });


            JFXTextField patientNameF = new JFXTextField();
            patientNameF.setPromptText("Patient Name");    
            patientNameF.setMinHeight(28);
            patientNameF.setMinWidth(250);
            patientNameF.setMaxWidth(250);
            patientNameF.setPrefWidth(250);   
            patientNameF.setLabelFloat(true);
            

            TextFields.bindAutoCompletion(patientNameF, chargesuggestions);

            FXField.addRequiredValidator(patientNameF);
            FXField.addFocusValidationListener(patientNameF);

            content.getChildren().addAll(patientNameF);

            JFXButton createBtn = new JFXButton("Create");                                    
            createBtn.getStyleClass().add("btn-danger");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Create OPD Record",content,FXDialog.DANGER,createBtn);

            createBtn.setOnAction(crtEvt->{
                if(patientNameF.validate()){
                    Patient selPatient = null;
                    for(Patient p:patients){
                        if(p.getFullname().equals(patientNameF.getText())){
                            selPatient = p;
                            break;
                        }
                    }
                    if(selPatient != null){
                        int rec_num = SQLTable.getMaxValue(Consultation.class, Consultation.RECORDNUMBER);
                        Consultation rec = new Consultation();
                        rec.setRecordnumber((rec_num > 0)? String.valueOf(rec_num+1):"");
                        rec.setPatient_id(selPatient.getId());
                        rec.setPatientname(selPatient.getFullname());
                        dialog.close();
                        ConsultationFormController.showDialog(rec, mainStack, maskerPane, this);
                    }
                }
            });
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void addERConsultation(ActionEvent event) {
        try{
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35,25,25,25));

            List<Patient> patients = SQLTable.list(Patient.class);

            List<String> chargesuggestions = new ArrayList();
            patients.stream().forEach(rec->{
                chargesuggestions.add(rec.getFullname());
            });

            JFXTextField patientNameF = new JFXTextField();
            patientNameF.setPromptText("Patient Name");    
            patientNameF.setMinHeight(28);
            patientNameF.setMinWidth(250);
            patientNameF.setMaxWidth(250);
            patientNameF.setPrefWidth(250);   
            patientNameF.setLabelFloat(true);
            

            TextFields.bindAutoCompletion(patientNameF, chargesuggestions);

            FXField.addRequiredValidator(patientNameF);
            FXField.addFocusValidationListener(patientNameF);

            content.getChildren().addAll(patientNameF);

            JFXButton createBtn = new JFXButton("Create");                                    
            createBtn.getStyleClass().add("btn-danger");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Create ER Record",content,FXDialog.DANGER,createBtn);

            createBtn.setOnAction(crtEvt->{
                if(patientNameF.validate()){
                    Patient selPatient = null;
                    for(Patient p:patients){
                        if(p.getFullname().equals(patientNameF.getText())){
                            selPatient = p;
                            break;
                        }
                    }
                    if(selPatient != null){
                        int rec_num = SQLTable.getMaxValue(ERConsultation.class, ERConsultation.RECORDNUMBER);
                        ERConsultation rec = new ERConsultation();
                        rec.setRecordnumber((rec_num > 0)? String.valueOf(rec_num+1):"");
                        rec.setPatient_id(selPatient.getId());
                        rec.setPatientname(selPatient.getFullname());
                        dialog.close();
                        ERConsultationFormController.showDialog(rec, mainStack, maskerPane, this);
                    }
                }
            });
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    

    @FXML
    void addPatient(ActionEvent event) {
        try{
            PatientRegistrationFormController.showDialog(mainStack, maskerPane, AdmissionUXController.this,t1addBtn);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchAdmission(ActionEvent event) {

    }
    
    @FXML
    void searchConsultationRecords(ActionEvent event) {
        try{
            if(t3searchF.getText() != null){
                String key = t3searchF.getText();
                String con = Consultation.PATIENTNAME+" LIKE '%"+key+"%' ORDER BY "+Consultation.CONSULTATIONTIME+" DESC";
                loadConsultationList(con);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void searchERConsultationRecords(ActionEvent event) {
        try{
            if(t4searchF.getText() != null){
                String key = t4searchF.getText();
                String con = ERConsultation.PATIENTNAME+" LIKE '%"+key+"%' ORDER BY "+ERConsultation.CONSULTATIONTIME+" DESC";
                loadERConsultationList(con);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void searchPatientRecords(ActionEvent event) {
        try{
            if(t5searchF.getText() != null){
                String key = t5searchF.getText();
                String con = Patient.FIRSTNAME+" LIKE '%"+key+"%' OR "+Patient.MIDDLENAME+" LIKE '%"+key+"%' OR "+Patient.LASTNAME+" LIKE '%"+key+"%' ORDER BY "+Patient.LASTNAME+" ASC,"+Patient.FIRSTNAME+" ASC, "+Patient.MIDDLENAME+" ASC";
                loadPatientList(con);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void reloadReferences(int val) {
        try{
            switch (val) {
                case 0:
                    loadAdmissionMap();
                    loadAdmittedList(null);
                    break;
                case 1:
                    break;
                case 2:
                    loadConsultationList(null);
                    break;
                case 3:
                    loadERConsultationList(null);
                    break;
                case 4:
                    loadPatientList(null);
                    break;
                default:
                    break;
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @Override
    public void setMainStack(StackPane stackpane) {
        this.mainStack = stackpane;
    }

    @Override
    public StackPane getMainStack() {
        return this.mainStack;
    }

    @Override
    public void loadCustomizations() {
        try{
            loadAdmissionCustomizations();
            loadDischargedTabCustomizations();
            
            loadConsultationTabCustomizations();
            loadERConsultationTabCustomizations();
            
            loadPatientTabCustomizations();
            
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal == admissionTab){
                    loadAdmittedList(null);
                    loadAdmissionMap();
                }else if(newVal == dischargedTab){
                    loadDischargedList(null);
                }else if(newVal == opdTab){
                    loadConsultationList(null);
                }else if(newVal == erTab){
                    loadERConsultationList(null);
                }else if(newVal == patientTab){
                    loadPatientList(null);
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            loadAdmissionMap();
            loadAdmittedList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadDischargeTableFilters();
            loadOPDRecordFilters();
            loadERRecordFilters();
            loadPatientRecordFilters();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setUIController(UIController controller) {
        
    }

    
    @Override
    public void setMaskerPane(MaskerPane masker) {
        this.maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return this.maskerPane;
    }
    
    private void loadAdmissionCustomizations(){
        try{
            loadAdmissionMapCustomization();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadAdmissionMapCustomization(){
        try{
            terminalFlowPane = new FlowPane();

            //terminalFlowPane.setPadding(new Insets(25, 25, 25, 25));
            terminalFlowPane.setStyle("-fx-background-color:white;");
            terminalFlowPane.setHgap(15);
            terminalFlowPane.setVgap(5);
            terminalFlowScrollPane = new ScrollPane();
            terminalFlowScrollPane.setContent(terminalFlowPane);
            terminalFlowScrollPane.setPannable(true);
            t1BorderPane.setCenter(terminalFlowScrollPane);
            terminalFlowPane.autosize();
            terminalFlowPane.setPrefWidth(Care.MAINSCENE.getWidth() - 410);
            terminalFlowPane.setPrefHeight(Care.MAINSCENE.getHeight() - 190);
            terminalFlowPane.setPadding(new Insets(10,10,10,10));
            
            TableColumn desCol = FXTable.addColumn(t1table, "Patient", Patient::fullnameProperty, false, 350, 350, 350);
            desCol.setCellFactory(column -> {
                return new TableCell<Patient, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Patient row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    BorderPane mainCon = new BorderPane();
                                    mainCon.setMinSize(330, 65);
                                    mainCon.setMaxSize(400, 65);
                                    mainCon.setPrefSize(350, 65);
                                    mainCon.setPadding(new Insets(0,2,0,2));
                                    
                                    VBox container = new VBox();
                                    container.setMinSize(308, 65);
                                    container.setMaxSize(308, 65);
                                    container.setPrefSize(308, 65);

                                    Label name = new Label();
                                    name.textProperty().bind(row_data.fullnameProperty());
                                    name.setStyle("-fx-font-weight:bold;-fx-font-size:13px");

                                    Label agen = new Label();
                                    agen.setText(row_data.getGender()+" - "+row_data.getAge() + ((row_data.getAge()> 1)? " Yrs old":" Yr old"));
                                    
                                    Label addate = new Label();
                                    addate.setText("Date : "+DateTimeKit.toProperTimestamp(row_data.getRecentadmission()));

                                    Label robed = new Label();                                   
                                    robed.setText(row_data.getRoom()+" - "+row_data.getBed());

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(name,agen, addate, robed);
                                    
                                    VBox nodes = new VBox();
                                    nodes.setMinSize(34, 65);
                                    nodes.setMaxSize(34, 65);
                                    nodes.setPrefSize(34, 65);
                                    nodes.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 24, 24, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        try{
                                            Admission admission = row_data.getLatestAdmission();
                                            if(admission!=null){
                                                AdmissionFormController.showDialog(admission, mainStack, maskerPane, AdmissionUXController.this);
                                            }else{
                                                //record not found
                                            }
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-success");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton endBtn = FXButtonsBuilderFactory.createButton("", 24, 24, "cell-btn", FontAwesomeIcon.EXCLAMATION_CIRCLE, "14px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(35);
                                            content.setPadding(new Insets(35,25,25,25));

                                            JFXDatePicker dateF = new JFXDatePicker();
                                            dateF.setPromptText("Date");    
                                            dateF.setMinHeight(28);
                                            dateF.setMinWidth(250);
                                            dateF.setMaxWidth(250);
                                            dateF.setPrefWidth(250);   
                                            dateF.setValue(LocalDate.now());

                                            JFXTimePicker timeF = new JFXTimePicker();
                                            timeF.setPromptText("Time");    
                                            timeF.setMinHeight(28);
                                            timeF.setMinWidth(250);
                                            timeF.setMaxWidth(250);
                                            timeF.setPrefWidth(250);  
                                            timeF.setValue(LocalTime.now());

                                            FXField.addRequiredValidator(dateF);
                                            FXField.addRequiredValidator(timeF);
                                            FXField.addFocusValidationListener(dateF,timeF);


                                            content.getChildren().addAll(dateF,timeF);

                                            JFXButton endBtnn = new JFXButton("Discharge");                                    
                                            endBtnn.getStyleClass().add("btn-danger");

                                            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Discharge Patient",content,FXDialog.PRIMARY,endBtnn);

                                            endBtnn.setOnAction(crtEvt->{
                                                if(dateF.validate() && timeF.validate()){
                                                    FXTask task = new FXTask() {
                                                        @Override
                                                        protected void load() {
                                                            try {                        
                                                                Platform.runLater(() -> {
                                                                    maskerPane.setVisible(true);
                                                                });
                                                                Admission record = row_data.getLatestAdmission();
                                                                if(record!= null){
                                                                    record.setDischargetime(LocalDateTime.of(dateF.getValue(), timeF.getValue()));
                                                                    if(record.update(true)){
                                                                        List<RoomRecord> roomrecs = SQLTable.list(RoomRecord.class, RoomRecord.ADMISSION_ID+"="+record.getId());
                                                                        roomrecs.stream().forEach(rrec->{
                                                                            if(rrec.getTimeend() == null){
                                                                                rrec.setTimeend(record.getDischargetime());
                                                                                rrec.update();
                                                                            }
                                                                        });
                                                                        java.sql.Timestamp recadmin = java.sql.Timestamp.valueOf(record.getAdmissiontime());
                                                                        SQLTable.execute("UPDATE "+Patient.TABLE_NAME+" SET "+Patient.ROOM+"='',"+Patient.BED+"='',"+Patient.RECENTADMISSION+"='"+recadmin+"',"+Patient.ADMITTED+"=0 WHERE "+Patient.ID+"="+record.getPatient_id());
                                                                        
                                                                        Platform.runLater(()->{
                                                                            dialog.close();
                                                                            loadAdmissionMap();
                                                                            loadAdmittedList(null);
                                                                            FXDialog.showMessageDialog(mainStack, "Successful", "Admission Record has been updated", FXDialog.SUCCESS);
                                                                        });
                                                                        Admission.updateDischargedCounter();
                                                                    }else{
                                                                        Platform.runLater(()->{
                                                                            FXDialog.showMessageDialog(mainStack, "Connection Error", "Failed to communicate to database server!", FXDialog.DANGER);
                                                                        });
                                                                    }
                                                                }
                                                                

                                                            } catch (Exception er) {
                                                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                            } finally {
                                                                Platform.runLater(() -> {
                                                                    maskerPane.setVisible(false);
                                                                });
                                                            }
                                                        }
                                                    };
                                                Care.process(task);
                                                }
                                                
                                            });
                                            
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    
                                    endBtn.setTooltip(new Tooltip("Discharge"));
                                    endBtn.getStyleClass().add("btn-danger");
                                    endBtn.setStyle("-jfx-button-type : FLAT;");
                                    endBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    nodes.getChildren().addAll(viewBtn, endBtn);
                                    
                                    mainCon.setCenter(container);
                                    mainCon.setRight(nodes);

                                    setGraphic(mainCon);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                    }
                };
            });
            
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadAdmissionMap(){
        try{
            terminalFlowPane.getChildren().clear();
            List<Room> rooms = SQLTable.list(Room.class);    
            List<Patient> patients = SQLTable.list(Patient.class);
            
            for(int i = 0;i < rooms.size();i++){
                final Room room = rooms.get(i);
                VBox container = new VBox();
                container.setMinSize(225, 300);
                container.setMaxSize(225, 1000);
                container.setPrefSize(225, 300);
                
                Label lb = new Label(room.getName());
                lb.setMinSize(225, 65);
                lb.setPrefSize(225, 65);
                lb.setStyle("-fx-background-color : primary-color;-fx-font-family : 'Anton';-fx-font-size:24px;-fx-font-weight : bold;-fx-text-fill : white;");
                lb.setTextAlignment(TextAlignment.CENTER);
                lb.setAlignment(Pos.CENTER);
                lb.setWrapText(true);
                container.getChildren().add(lb);
                
                List<Bed> beds = SQLTable.list(Bed.class, Bed.ROOM_ID+"='"+room.getId()+"'");
                
                for(int z = 0;z < beds.size();z++){
                    final Bed bed = beds.get(z);
                    final Patient p = new Patient();
                    for(int x = 0;x < patients.size();x++){
                        if(patients.get(x).getRoom().equals(room.getName()) && patients.get(x).getBed().equals(bed.getName())){
                            //p = patients.get(x);
                            p.copy(patients.get(x));
                            break;
                        }
                    }
                    
                    JFXButton b = new JFXButton(bed.getName()+" : "+((p.getId() > 0)? p.getFullname():"Unoccupied ["+bed.getRate()+"/"+bed.getBasis()+"h]"));
                    b.setMinSize(225, 30);
                    b.setPrefSize(225, 30);
                    b.setFocusTraversable(false);   
                    b.getStyleClass().add(((p.getId() > 0)? "admission-occupied":"admission-available"));
                    b.setTextAlignment(TextAlignment.LEFT);
                    b.setAlignment(Pos.CENTER_LEFT);
                    b.setContentDisplay(ContentDisplay.RIGHT);
                    
                    b.setOnAction(clickEvt->{
                        if(p.getId() > 0){
                            Admission admission = p.getLatestAdmission();
                            if(admission!=null){
                                AdmissionFormController.showDialog(admission, mainStack, maskerPane, AdmissionUXController.this, b);
                            }else{
                                //record not found
                            }
                        }else{
                            try{
                                VBox content = new VBox();
                                content.setMaxWidth(500);
                                content.setMaxHeight(500);
                                content.setAlignment(Pos.CENTER);
                                content.setSpacing(35);
                                content.setPadding(new Insets(35,25,25,25));


                                List<String> namesuggestions = new ArrayList();
                                patients.stream().forEach(rec->{
                                    if(!rec.isAdmitted()){
                                        namesuggestions.add(rec.getFullname());
                                    }
                                });

                                JFXTextField patientNameF = new JFXTextField();
                                patientNameF.setPromptText("Patient Name");    
                                patientNameF.setMinHeight(28);
                                patientNameF.setMinWidth(250);
                                patientNameF.setMaxWidth(250);
                                patientNameF.setPrefWidth(250);   
                                patientNameF.setLabelFloat(true);
                                
                                JFXDatePicker dateF = new JFXDatePicker();
                                dateF.setPromptText("Date");    
                                dateF.setMinHeight(28);
                                dateF.setMinWidth(250);
                                dateF.setMaxWidth(250);
                                dateF.setPrefWidth(250);   
                                dateF.setValue(LocalDate.now());
                                
                                JFXTimePicker timeF = new JFXTimePicker();
                                timeF.setPromptText("Time");    
                                timeF.setMinHeight(28);
                                timeF.setMinWidth(250);
                                timeF.setMaxWidth(250);
                                timeF.setPrefWidth(250);  
                                timeF.setValue(LocalTime.now());


                                TextFields.bindAutoCompletion(patientNameF, namesuggestions);

                                FXField.addRequiredValidator(patientNameF);
                                FXField.addRequiredValidator(dateF);
                                FXField.addRequiredValidator(timeF);
                                FXField.addFocusValidationListener(patientNameF);
                                

                                content.getChildren().addAll(patientNameF,dateF,timeF);

                                JFXButton createBtn = new JFXButton("Create");                                    
                                createBtn.getStyleClass().add("btn-danger");

                                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Occupy Room "+room.getName()+"-"+bed.getName(),content,FXDialog.PRIMARY,createBtn);

                                createBtn.setOnAction(crtEvt->{
                                    if(patientNameF.validate() && dateF.validate() && timeF.validate()){
                                        Patient selPatient = null;
                                        for(Patient pat:patients){
                                            if(pat.getFullname().equals(patientNameF.getText())){
                                                selPatient = pat;
                                                System.out.println("Pat : "+pat.getDebugInfo());
                                                System.out.println("PatSel : "+selPatient.getDebugInfo());
                                                break;
                                            }
                                        }
                                        
                                        if(selPatient != null){
                                            //Create Admission
                                            int rec_num = SQLTable.getMaxValue(Admission.class, Admission.ADMISSIONNUMBER);                                            
                                            Admission rec = new Admission();
                                            rec.setAdmissionnumber((rec_num > 0)? String.valueOf(rec_num+1):"");
                                            rec.setPatientname(selPatient.getFullname());
                                            rec.setPatient_id(selPatient.getId());
                                            rec.setAge(selPatient.getAge());
                                            rec.setAgestring("Yr/s old");
                                            rec.setGender(selPatient.getGender());
                                            rec.setAdmissiontime(LocalDateTime.of(dateF.getValue(), timeF.getValue()));
                                            rec.setEncoder(Care.getUser().getName());
                                            rec.setUser_id(Care.getUser().getId());
                                            
                                            RoomRecord roomrec = new RoomRecord();
                                            roomrec.setRoom(room.getName());
                                            roomrec.setBed(bed.getName());
                                            roomrec.setTimestart(rec.getAdmissiontime());
                                            roomrec.setBasis(bed.getBasis());
                                            roomrec.setRate(bed.getRate()); 
                                            roomrec.setEncoder(Care.getUser().getName());
                                            
                                            selPatient.setAdmitted(true);
                                            selPatient.setRoom(room.getName());
                                            selPatient.setBed(bed.getName());
                                            selPatient.setRecentadmission(rec.getAdmissiontime());
                                            
                                            int id = rec.save();
                                            
                                            if(id > 0){
                                                roomrec.setAdmission_id(id);
                                                roomrec.save();
                                                selPatient.update();
                                                dialog.close();    
                                                loadAdmissionMap();
                                                loadAdmittedList(null);
                                                Admission.updateAdmissionCounter();
                                            }
                                                                                 
                                        }
                                    }
                                });
                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        }
                    });
                    container.getChildren().add(b);
                }              
                terminalFlowPane.getChildren().add(container);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    
    private void loadAdmittedList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t1table, new ArrayList());
                            t1table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<Patient> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(Patient.class, Patient.ADMITTED + "='1' ORDER BY " + Patient.RECENTADMISSION + " DESC");
                        } else {
                            records = SQLTable.list(Patient.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<Patient> filteredRecords = FXTable.setFilteredList(t1table, records);
                            Patient.createTableFilter(t1searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends Patient> c) -> {
                                t1resultLbl.setText("Result : " + filteredRecords.size());
                            });
                            t1resultLbl.setText("Result : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t1table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadDischargedTabCustomizations(){
        try{
            t2table.setEditable(false);
            t2table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(t2table);
            
            TableColumn adCol = FXTable.addColumn(t2table, "Admission", Admission::admissiontimeProperty, false, 135, 135, 135);            
            TableColumn diCol = FXTable.addColumn(t2table, "Discharged", Admission::dischargetimeProperty, false, 135, 135, 135);
            FXTable.addColumn(t2table, "Patient", Admission::patientnameProperty, false);
            FXTable.addColumn(t2table, "Physician", Admission::physicianProperty, false);
            FXTable.addColumn(t2table, "Encoder", Admission::encoderProperty, false);
            FXTable.addColumn(t2table, "Case", Admission::admissioncaseProperty, false);
            TableColumn actCol = FXTable.addColumn(t2table, " ", Admission::patientnameProperty, false,42,42,42);
            
            FXTable.setTimestampColumn(adCol);
            FXTable.setTimestampColumn(diCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Admission, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try{
                                Admission row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox container = new HBox();
                                    container.setMinSize(42, 42);
                                    container.setMaxSize(42, 42);
                                    container.setPrefSize(42, 42);
                                    container.setSpacing(4);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        AdmissionFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, AdmissionUXController.this);
                                    });
                                    
                                    edtBtn.setTooltip(new Tooltip("View Item"));
                                    edtBtn.getStyleClass().add("btn-control");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                                    
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(edtBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
                                    setGraphic(null);
                                    setStyle("");
                                }
                            }catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                    }
                };
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadConsultationTabCustomizations(){
        try{
            t3table.setEditable(true);
            t3table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t3table, "Timestamp", Consultation::consultationtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t3table, "Patient Name", Consultation::patientnameProperty, false);
            FXTable.addColumn(t3table, "Physician", Consultation::physicianProperty, false);
            FXTable.addColumn(t3table, "Case", Consultation::casecodeProperty, false);
            FXTable.addColumn(t3table, "Encoder", Consultation::encoderProperty, false);
            FXTable.addColumn(t3table, "OR #", Consultation::ornumberProperty, false);
            TableColumn actCol = FXTable.addColumn(t3table, " ", Consultation::patientnameProperty, false, 44, 44, 44);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Consultation, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Consultation row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        ConsultationFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, AdmissionUXController.this);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    /*
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        
                                    });
                                    
                                    editBtn.setTooltip(new Tooltip("Void"));
                                    editBtn.getStyleClass().add("btn-danger");
                                    editBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    */
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        }
                    }
                };
            });
            
            FXTable.setTimestampColumn(timeCol);
            
            t3searchF.setOnKeyReleased(evt ->{
                if(evt.getCode() == KeyCode.ENTER){
                    if(!t3searchF.getText().isEmpty()){
                        try{
                            t3searchBtn.fire();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(evt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t3searchF.setText("");
                        loadConsultationList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadERConsultationTabCustomizations(){
        try{
            t4table.setEditable(true);
            t4table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t4table, "Timestamp", ERConsultation::consultationtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t4table, "Patient Name", ERConsultation::patientnameProperty, false);
            FXTable.addColumn(t4table, "Physician", ERConsultation::physicianProperty, false);
            FXTable.addColumn(t4table, "Case", ERConsultation::casecodeProperty, false);
            FXTable.addColumn(t4table, "Encoder", ERConsultation::encoderProperty, false);
            FXTable.addColumn(t4table, "OR #", ERConsultation::ornumberProperty, false);
            TableColumn actCol = FXTable.addColumn(t4table, " ", ERConsultation::patientnameProperty, false, 44, 44, 44);
            
            actCol.setCellFactory(column -> {
                return new TableCell<ERConsultation, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                ERConsultation row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        ERConsultationFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, AdmissionUXController.this);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    /*
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        
                                    });
                                    
                                    editBtn.setTooltip(new Tooltip("Void"));
                                    editBtn.getStyleClass().add("btn-danger");
                                    editBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    */
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        }
                    }
                };
            });
            
            FXTable.setTimestampColumn(timeCol);
            
            t4searchF.setOnKeyReleased(evt ->{
                if(evt.getCode() == KeyCode.ENTER){
                    if(!t4searchF.getText().isEmpty()){
                        try{
                            t4searchBtn.fire();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(evt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t4searchF.setText("");
                        loadERConsultationList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPatientTabCustomizations(){
        try{
            t5table.setEditable(true);
            t5table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t5table, "ID", Patient::idProperty, false, 65, 65, 65);
            FXTable.addColumn(t5table, "HOSPITAL ID", Patient::codeProperty, false, 110, 110, 110);
            FXTable.addColumn(t5table, "Patient Name", Patient::fullnameProperty, false);
            FXTable.addColumn(t5table, "Gender", Patient::genderProperty, false,70,70,70);
            FXTable.addColumn(t5table, "Age", Patient::ageProperty, false,65,65,65);
            FXTable.addColumn(t5table, "Mobile #", Patient::mobileProperty, false,80,80,80);
            FXTable.addColumn(t5table, "Email", Patient::emailProperty, false,150,150,150);
            TableColumn actCol = FXTable.addColumn(t5table, " ", Patient::fullnameProperty, false, 72, 72, 72);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Patient, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Patient row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Expense Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        try{
                                            Patient record = row_data.getModelClone();
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(10);
                                            content.setPadding(new Insets(35,25,25,25));

                                            FXTextEntry fnameF = new FXTextEntry("Firstname");
                                            FXTextEntry mnameF = new FXTextEntry("Middlename");
                                            FXTextEntry lnameF = new FXTextEntry("Lastname");
                                            FXTextEntry genF = new FXTextEntry("Gender");
                                            FXDateEntry dateF = new FXDateEntry("Birthdate");
                                            FXTextEntry addressF = new FXTextEntry("Address");
                                            FXTextEntry municipalityF = new FXTextEntry("Municipality");
                                            FXTextEntry provinceF = new FXTextEntry("Province");
                                            FXTextEntry civilF = new FXTextEntry("Civil Status");
                                            FXTextEntry relF = new FXTextEntry("Religion");
                                            FXTextEntry mobileF = new FXTextEntry("Mobile");
                                            FXTextEntry phoneF = new FXTextEntry("Landline");
                                            FXTextEntry emailF = new FXTextEntry("Email");

                                            StringProperty fnameV = new SimpleStringProperty(record.getFirstname());
                                            StringProperty mnameV = new SimpleStringProperty(record.getMiddlename());
                                            StringProperty lnameV = new SimpleStringProperty(record.getLastname());
                                            StringProperty genV = new SimpleStringProperty(record.getGender());
                                            ObjectProperty<LocalDate> dateV = new SimpleObjectProperty(record.getBirthdate());
                                            StringProperty addressV = new SimpleStringProperty(record.getAddress());
                                            StringProperty muniV = new SimpleStringProperty(record.getCitymunicipality());
                                            StringProperty proviV = new SimpleStringProperty(record.getStateprovince());
                                            StringProperty civilV = new SimpleStringProperty(record.getCivilstatus());
                                            StringProperty relV = new SimpleStringProperty(record.getReligion());
                                            StringProperty mobileV = new SimpleStringProperty(record.getMobile());
                                            StringProperty phoneV = new SimpleStringProperty(record.getLandline());
                                            StringProperty emailV = new SimpleStringProperty(record.getEmail());

                                            fnameF.getTextfieldComponent().textProperty().bindBidirectional(fnameV);
                                            mnameF.getTextfieldComponent().textProperty().bindBidirectional(mnameV);
                                            lnameF.getTextfieldComponent().textProperty().bindBidirectional(lnameV);
                                            genF.getTextfieldComponent().textProperty().bindBidirectional(genV);
                                            dateF.getTextfieldComponent().valueProperty().bindBidirectional(dateV);
                                            addressF.getTextfieldComponent().textProperty().bindBidirectional(addressV);
                                            municipalityF.getTextfieldComponent().textProperty().bindBidirectional(muniV);
                                            provinceF.getTextfieldComponent().textProperty().bindBidirectional(proviV);
                                            civilF.getTextfieldComponent().textProperty().bindBidirectional(civilV);
                                            relF.getTextfieldComponent().textProperty().bindBidirectional(relV);
                                            mobileF.getTextfieldComponent().textProperty().bindBidirectional(mobileV);
                                            phoneF.getTextfieldComponent().textProperty().bindBidirectional(phoneV);
                                            emailF.getTextfieldComponent().textProperty().bindBidirectional(emailV);

                                            FXField.addRequiredValidator(fnameF.getTextfieldComponent());
                                            //FXField.addRequiredValidator(mnameF.getTextfieldComponent());
                                            FXField.addRequiredValidator(lnameF.getTextfieldComponent());
                                            FXField.addRequiredValidator(genF.getTextfieldComponent());
                                            FXField.addRequiredValidator(dateF.getTextfieldComponent());

                                            FXField.addFocusValidationListener(fnameF.getTextfieldComponent(),lnameF.getTextfieldComponent(),dateF.getTextfieldComponent(),genF.getTextfieldComponent());

                                            JFXButton save = new JFXButton("Update");
                                            save.getStyleClass().add("btn-success");

                                            content.getChildren().addAll(fnameF,mnameF,lnameF,genF,dateF,addressF,municipalityF,provinceF,civilF,relF,mobileF,phoneF,emailF);
                                            //new Label("Do you want to save changes?");
                                            JFXDialog dialogx = FXDialog.showConfirmDialog(mainStack, "Update Patient Info", content, FXDialog.PRIMARY, save);
                                            save.setOnAction(editEvt->{
                                                if(fnameF.getTextfieldComponent().validate() && lnameF.getTextfieldComponent().validate() && genF.getTextfieldComponent().validate() && dateF.getTextfieldComponent().validate()){
                                                    FXTask task = new FXTask() {
                                                        @Override
                                                        protected void load() {      
                                                            try{
                                                                record.setFirstname(fnameV.get());
                                                                record.setMiddlename(mnameV.get());
                                                                record.setLastname(lnameV.get());
                                                                record.setGender(genV.get());
                                                                record.setBirthdate(dateV.get());
                                                                record.setAddress(addressV.get());
                                                                record.setCitymunicipality(muniV.get());
                                                                record.setStateprovince(proviV.get());
                                                                record.setStateprovince(civilV.get());
                                                                record.setStateprovince(relV.get());
                                                                record.setStateprovince(mobileV.get());
                                                                record.setStateprovince(phoneV.get());
                                                                record.setStateprovince(emailV.get());
                                                                if(record.update()){
                                                                    Patient.updateAllPatientReferrenceNames(record.getId(), record.getFirstname(), record.getMiddlename(), record.getLastname());
                                                                    Platform.runLater(()->{
                                                                        dialogx.close();
                                                                        FXDialog.showMessageDialog(mainStack, "Successful", "Patient Info has been updated!", FXDialog.SUCCESS);
                                                                        loadPatientList(null);
                                                                    });
                                                                }
                                                            }catch(Exception er){
                                                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                            }
                                                        }
                                                    };
                                                    Care.process(task);
                                                }
                                            });
                                        }catch(Exception er){
                                            
                                        }
                                    });
                                    
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-danger");
                                    editBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,editBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        }
                    }
                };
            });
            
            
            t5searchF.setOnKeyReleased(evt ->{
                if(evt.getCode() == KeyCode.ENTER){
                    if(!t5searchF.getText().isEmpty()){
                        try{
                            t5searchBtn.fire();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(evt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t5searchF.setText("");
                        loadPatientList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadDischargedList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t2table, new ArrayList());
                            t2table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<Admission> records;
                        if (conditions == null || conditions.isEmpty()) {
                            java.sql.Timestamp from = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp to = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(Admission.class, Admission.DISCHARGETIME + " IS NOT NULL AND "+Admission.DISCHARGETIME+">='"+from+"' AND "+Admission.DISCHARGETIME+"<='"+to+"' ORDER BY " + Admission.DISCHARGETIME + " DESC");
                        } else {
                            records = SQLTable.list(Admission.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<Admission> filteredRecords = FXTable.setFilteredList(t2table, records);
                            Admission.createTableFilter(t2searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends Admission> c) -> {
                                t2resultsLbl.setText("Result : " + filteredRecords.size());
                            });
                            t2resultsLbl.setText("Result : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t2table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadConsultationList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t3table, new ArrayList());
                            t3table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<Consultation> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(Consultation.class, Consultation.CONSULTATIONTIME+">='"+t1+"' AND "+Consultation.CONSULTATIONTIME+"<='"+t2+"' ORDER BY "+Consultation.CONSULTATIONTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(Consultation.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t3table, charges);
                        });                        
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t3table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadERConsultationList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t4table, new ArrayList());
                            t4table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<ERConsultation> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(ERConsultation.class, ERConsultation.CONSULTATIONTIME+">='"+t1+"' AND "+ERConsultation.CONSULTATIONTIME+"<='"+t2+"' ORDER BY "+ERConsultation.CONSULTATIONTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(ERConsultation.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t4table, charges);
                        });                        
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t4table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPatientList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t5table, new ArrayList());
                            t5table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<Patient> charges;
                        if( conditions == null || conditions.isEmpty()){
                            charges = SQLTable.list(Patient.class, Patient.ID+"<>0 ORDER BY "+Patient.ID+" DESC");                            
                        }else{
                            charges = SQLTable.list(Patient.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t5table, charges);
                        });                        
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t5table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadDischargeTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadDischargedList(Admission.DISCHARGETIME + " IS NOT NULL AND "+Admission.DISCHARGETIME + ">='" + sqT1 + "' AND " + Admission.DISCHARGETIME + "<='" + sqT2 + "' ORDER BY " + Admission.DISCHARGETIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadDischargedList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Admission> records = t2table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("discharged.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(Admission.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t2table, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadOPDRecordFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadConsultationList(Consultation.CONSULTATIONTIME + ">='" + sqT1 + "' AND " + Consultation.CONSULTATIONTIME + "<='" + sqT2 + "' ORDER BY " + Consultation.CONSULTATIONTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadConsultationList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Consultation> records = t3table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("consultations.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(Consultation.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            FXTable.addCustomTableMenu(t3table, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadERRecordFilters(){
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadERConsultationList(ERConsultation.CONSULTATIONTIME + ">='" + sqT1 + "' AND " + ERConsultation.CONSULTATIONTIME + "<='" + sqT2 + "' ORDER BY " + ERConsultation.CONSULTATIONTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadERConsultationList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<ERConsultation> records = t4table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("erconsultations.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(ERConsultation.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t4table, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPatientRecordFilters(){
        try {
                        
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("All Patients");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadPatientList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Patient> records = t5table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("patients.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(Patient.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t5table, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
}
