package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import models.Admission;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.HospitalService;
import models.LaboratoryTest;
import models.Patient;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import nse.dcfx.utils.NumberKit;
import nse.dcfx.utils.StringKit;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class LaboratoryUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
        
    @FXML
    private JFXTabPane mainTabPane;
    
    @FXML
    private JFXTextField t1searchF;

    @FXML
    private TableView<LaboratoryTest> t1table;

    @FXML
    private Label t1resultsLbl;

    @FXML
    private JFXButton t1searchBtn;    

    @FXML
    private JFXButton t1addPatientBtn;

    @FXML
    private JFXButton t1addConsultationBtn;
    
    @FXML
    private JFXTextField t2searchF;

    @FXML
    private TableView<HospitalCharge> t2table;

    @FXML
    private Label t2resultsLbl;

    @FXML
    private JFXButton t2searchBtn;
    
    
    @FXML
    private Tab chargeTab;
    
    @FXML
    private Tab testsTab;

    @FXML
    void addConsultation(ActionEvent event) {
        try{
            CreateLabChargeFormController.showDialog(mainStack, maskerPane, LaboratoryUXController.this);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void addMultipleChargeToOne(ActionEvent event) {
        try{
            VBox content = new VBox();
            content.setMaxWidth(800);
            content.setMaxHeight(600);
            content.setAlignment(Pos.CENTER_LEFT);
            content.setSpacing(35);
            content.setPadding(new Insets(15,40,10,10));

            List<Patient> patients = SQLTable.list(Patient.class);
            List<HospitalPersonel> persons = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Physician'");
            List<HospitalService> services = SQLTable.list(HospitalService.class, HospitalService.FACILITY+"='LABORATORY' ORDER BY "+HospitalService.CATEGORY+" ASC,"+HospitalService.DESCRIPTION+" ASC");
            List<HospitalService> selservices = new ArrayList();
                
            List<String> chargesuggestions = new ArrayList();
            patients.stream().forEach(rec->{
                chargesuggestions.add(rec.getFullname());
            });
            
            List<String> physuggestions = new ArrayList();
            persons.stream().forEach(rec->{
                physuggestions.add(rec.getName());
            });
            
            List<String> servsuggestions = new ArrayList();
            services.stream().forEach(rec->{
                servsuggestions.add(rec.getCategory()+"-"+rec.getDescription());
            });
            
            
                        
            JFXTextField chargeToF = new JFXTextField();
            chargeToF.setPromptText("Charged To");    
            chargeToF.setMinHeight(28);
            chargeToF.setMinWidth(250);
            chargeToF.setMaxWidth(250);
            chargeToF.setPrefWidth(250);   
            chargeToF.setLabelFloat(true);
            
            HBox pcontainer = new HBox();
            pcontainer.setPrefSize(450, 28);
            pcontainer.setSpacing(10);

            JFXTextField patientNameF = new JFXTextField();
            patientNameF.setPromptText("Patient Name");    
            patientNameF.setMinHeight(28);
            patientNameF.setMinWidth(250);
            patientNameF.setMaxWidth(250);
            patientNameF.setPrefWidth(250);   
            patientNameF.setLabelFloat(true);
            
            JFXComboBox<String> genderF = new JFXComboBox();
            genderF.setPromptText("Gender");    
            genderF.setMinHeight(28);
            genderF.setMinWidth(80);
            genderF.setMaxWidth(80);
            genderF.setPrefWidth(80);   
            genderF.setLabelFloat(true);
            genderF.getItems().addAll("Male","Female");
            
            JFXTextField ageF = new JFXTextField();
            ageF.setPromptText("Age");    
            ageF.setMinHeight(28);
            ageF.setMinWidth(65);
            ageF.setMaxWidth(65);
            ageF.setPrefWidth(65);   
            ageF.setLabelFloat(true);
            
            JFXTextField physicianNameF = new JFXTextField();
            physicianNameF.setPromptText("Physician's Name");    
            physicianNameF.setMinHeight(28);
            physicianNameF.setMinWidth(250);
            physicianNameF.setMaxWidth(250);
            physicianNameF.setPrefWidth(250);   
            physicianNameF.setLabelFloat(true);
            
            HBox fcontainer = new HBox();
            fcontainer.setPrefSize(450, 28);
            fcontainer.setSpacing(10);
            
            
            JFXTextField filterF = new JFXTextField();
            filterF.setPromptText("Select Laboratory Service");    
            filterF.setMinHeight(28);
            filterF.setMinWidth(285);
            filterF.setMaxWidth(285);
            filterF.setPrefWidth(285);   
            filterF.setLabelFloat(true);
            
            JFXTextField amountF = new JFXTextField("0.00");
            amountF.setPromptText("Price");    
            amountF.setMinHeight(28);
            amountF.setMinWidth(80);
            amountF.setMaxWidth(80);
            amountF.setPrefWidth(80);   
            amountF.setLabelFloat(true);
            amountF.setEditable(false);
            
            Label errLb = new Label("");
            errLb.setStyle("-fx-text-fill: danger-color;");
            
            JFXButton addBtn = new JFXButton("ADD");
            addBtn.setMinHeight(28);
            addBtn.setMinWidth(65);
            addBtn.setMaxWidth(65);
            addBtn.setPrefWidth(65);  
            addBtn.getStyleClass().add("btn-success");
            
            TextFields.bindAutoCompletion(filterF, servsuggestions);
            
            
            filterF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal!=null && !newVal.isEmpty()){
                    boolean found = false;
                    for(HospitalService srv:services){
                        if(newVal.equals(srv.getCategory()+"-"+srv.getDescription())){
                            amountF.setText(NumberKit.toCurrency(srv.getPrice()));
                            found= true;
                            break;
                        }
                    }
                    if(found){
                        
                    }else{
                        amountF.setText("0.00");
                    }
                    errLb.setText("");
                }
            });
            
            TableView<HospitalService> testTbl = new TableView();
            testTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn desCol = FXTable.addColumn(testTbl, "Record", HospitalService::categoryProperty, false);
            TableColumn actCol = FXTable.addColumn(testTbl, " ", HospitalService::descriptionProperty, false, 40, 40, 40);
            testTbl.setPrefHeight(250);
            
            FXTable.setList(testTbl, selservices);
            
            addBtn.setOnAction(evt->{
                if(!filterF.getText().isEmpty()){
                    boolean found = false;
                    String srvname = filterF.getText();
                    HospitalService selSrv = null;
                    for(HospitalService srv:services){
                        if(srvname.equals(srv.getCategory()+"-"+srv.getDescription())){
                            amountF.setText(NumberKit.toCurrency(srv.getPrice()));
                            selSrv = srv.getModelClone();
                            found = true;
                            break;
                        }
                    }
                    if(found && selSrv != null){
                        testTbl.getItems().add(selSrv.getModelClone());
                        testTbl.refresh();
                        filterF.setText("");
                        filterF.requestFocus();
                    }else{
                        errLb.setText("Laboratory Service not found!");
                    }
                    
                }
            });
            
            actCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(40, 40);
                                    container.setMaxSize(40, 40);
                                    container.setPrefSize(40, 40);
                                    container.setSpacing(4);
                                    
                                    
                                    JFXButton removeBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        testTbl.getItems().remove(row_data);
                                        testTbl.refresh();
                                    });
                                    
                                    removeBtn.setTooltip(new Tooltip("Remove"));
                                    removeBtn.getStyleClass().add("btn-danger");
                                    removeBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    removeBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(removeBtn);

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
            
            desCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    VBox container = new VBox();
                                    

                                    Label ctest = new Label(row_data.getCategory()+" - "+row_data.getDescription());    
                                    ctest.setPrefSize(Double.MAX_VALUE,18);
                                    ctest.setTextAlignment(TextAlignment.LEFT);
                                    ctest.setAlignment(Pos.CENTER_LEFT);          
                                    ctest.setStyle("-fx-font-weight : bold;");

                                    Label cname = new Label((String)row_data.getResource("patient"));
                                    cname.setPrefSize(Double.MAX_VALUE,18);
                                    cname.setTextAlignment(TextAlignment.LEFT);
                                    cname.setAlignment(Pos.CENTER_LEFT);

                                    Label charge = new Label((String)row_data.getResource("chargeto"));
                                    charge.setPrefSize(Double.MAX_VALUE,18);
                                    charge.setTextAlignment(TextAlignment.LEFT);
                                    charge.setAlignment(Pos.CENTER_LEFT);
                                    charge.setStyle("-fx-text-fill:danger-color;");

                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    if(((String)row_data.getResource("chargeto")).isEmpty()){
                                        container.setPrefSize(Double.MAX_VALUE, 35);
                                        container.getChildren().addAll(ctest, cname);
                                    }else{
                                        container.setPrefSize(Double.MAX_VALUE, 55);
                                        container.getChildren().addAll(ctest, cname,charge);
                                    }

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
            
            
            

            TextFields.bindAutoCompletion(patientNameF, chargesuggestions);
            TextFields.bindAutoCompletion(physicianNameF, physuggestions);

            FXField.addRequiredValidator(patientNameF);
            FXField.addIntegerValidator(ageF, 1, 150, 1);
            //FXField.addFocusValidationListener(patientNameF);

            pcontainer.getChildren().addAll(physicianNameF,genderF,ageF);
            fcontainer.getChildren().addAll(filterF,amountF,addBtn);
            content.getChildren().addAll(chargeToF,pcontainer,physicianNameF,fcontainer,errLb,testTbl);
            
            VBox.setMargin(testTbl, new Insets(-25,0,0,0));

            JFXButton createBtn = new JFXButton("Create");                                    
            createBtn.getStyleClass().add("btn-danger");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Create Laboratory Test Records",content,FXDialog.DANGER,createBtn);

            createBtn.setOnAction(crtEvt->{
                if(patientNameF.validate()){
                    
                    //
                    FXTask task = new FXTask(){
                    @Override
                    protected void load() {
                        try{
                            Platform.runLater(()->{
                                createBtn.setDisable(true);
                            });
                            Patient selPatient = null;
                            for(Patient p:patients){
                                if(p.getFullname().equals(patientNameF.getText())){
                                    selPatient = p;
                                    break;
                                }
                            }
                            if(selPatient != null && testTbl.getItems().size() > 0){
                                Map<String,String> opts = GlobalOption.getMap("General");
                                List<HospitalService> selservices = testTbl.getItems();                        
                                LocalDateTime CHARGETIME = LocalDateTime.now();
                                HospitalCharge CHARGE = new HospitalCharge();
                                String CHARGENUM = StringKit.timeCode(CHARGETIME);

                                Admission admission = ((selPatient.isAdmitted())? selPatient.getLatestAdmission():null);
                                String phys = physicianNameF.getText();

                                CHARGE.setChargenumber(CHARGENUM);
                                CHARGE.setChargetime(CHARGETIME);
                                CHARGE.setChargeto(selPatient.getFullname());
                                CHARGE.setChargefacility("Laboratory");
                                CHARGE.setChargetype((admission != null)? "Bill":"Walk-In");
                                CHARGE.setUser(Care.getUser().getName());
                                CHARGE.setUser_id(Care.getUser().getId());
                                CHARGE.setPatient_id(selPatient.getId());
                                if(admission != null){
                                    CHARGE.setAdmission_id(admission.getId());
                                }
                                
                                int rec_num = SQLTable.getMaxValue(LaboratoryTest.class, LaboratoryTest.LABTESTNUMBER);   
                                

                                List<HospitalChargeItem> chargeItems = new ArrayList();
                                for(int i = 0;i < selservices.size();i++){
                                    rec_num++;
                                    HospitalService srv = selservices.get(i);
                                    //Test
                                    LaboratoryTest test = new LaboratoryTest();
                                    test.setLabtestnumber((rec_num > 0)? String.valueOf(rec_num):"");
                                    test.setPatient(selPatient.getFullname());
                                    test.setGender(selPatient.getGender());
                                    test.setAge(selPatient.getAge());
                                    test.setTestname(srv.getDescription());
                                    test.setTestcategory(srv.getCategory());
                                    test.setTestgroup(srv.getGrp());
                                    test.setTesttime(CHARGETIME);
                                    test.setPhysician(phys);
                                    test.setEncoder(Care.getUser().getName());
                                    test.setUser_id(Care.getUser().getId());
                                    test.setPatient_id(selPatient.getId());     
                                    test.setHospitalservice_id(srv.getId());
                                    
                                    if(admission != null){
                                        test.setAdmission_id(admission.getId());
                                    }

                                    int test_id = test.save(true);
                                    if(test_id > 0){
                                        //Charge
                                        HospitalChargeItem labcharge = new HospitalChargeItem();
                                        labcharge.setChargenumber(CHARGENUM);
                                        labcharge.setChargetime(CHARGETIME);
                                        labcharge.setDescription(srv.getCategory()+"-"+srv.getDescription());
                                        labcharge.setSelling(srv.getPrice());
                                        labcharge.setQuantity(1);
                                        labcharge.setService(true);
                                        labcharge.setItemtype("Laboratory Charge");
                                        labcharge.setFacility("Laboratory");
                                        labcharge.setUser(Care.getUser().getName());
                                        labcharge.setChargeto(selPatient.getFullname());
                                        labcharge.setUser_id(Care.getUser().getId());
                                        labcharge.setPatient_id(selPatient.getId());     
                                        labcharge.setRecordtable(LaboratoryTest.TABLE_NAME);
                                        labcharge.setRecordtableid(test_id);
                                        labcharge.setVatable(false);
                                        labcharge.calculateNet(false);

                                        if(admission != null){
                                            labcharge.setAdmission_id(admission.getId());
                                        }

                                        chargeItems.add(labcharge);
                                        
                                        CHARGE.setRecordtable(LaboratoryTest.TABLE_NAME);
                                        CHARGE.setRecordtableid(test_id);
                                    }
                                }
                                CHARGE.setItems(chargeItems);
                                CHARGE.calculateTotal(chargeItems);
                                if(CHARGE.save(chargeItems)> 0){
                                    Platform.runLater(()->{       
                                        dialog.close();
                                        FXDialog.showMessageDialog(mainStack, "Success", "New Laboratory records has been registered!", FXDialog.SUCCESS);
                                    });
                                    CHARGE.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Laboratory", false);
                                    loadLabTestList(null);
                                }
                            }
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally {
                            Platform.runLater(()->{
                                createBtn.setDisable(false);
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
    }

    @FXML
    void addPatient(ActionEvent event) {
        try{
            PatientRegistrationFormController.showDialog(mainStack, maskerPane, LaboratoryUXController.this,t1addPatientBtn);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchPatientMasterRecords(ActionEvent event) {
        try{
            if(t1searchF.getText() != null){
                String key = t1searchF.getText();
                String con = LaboratoryTest.PATIENT+" LIKE '%"+key+"%' ORDER BY "+LaboratoryTest.TESTTIME+" DESC";
                loadLabTestList(con);
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
            if(val == 0){
                loadLabTestList(null);
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
            t1table.setEditable(true);
            t1table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            TableColumn selCol = FXTable.addColumn(t1table, " ", LaboratoryTest::selectedProperty, false, 45, 45, 45);
            TableColumn timeCol = FXTable.addColumn(t1table, "Timestamp", LaboratoryTest::testtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t1table, "Patient Name", LaboratoryTest::patientProperty, false);
            FXTable.addColumn(t1table, "Physician", LaboratoryTest::physicianProperty, false);
            FXTable.addColumn(t1table, "Test", LaboratoryTest::testnameProperty, false);
            FXTable.addColumn(t1table, "Category", LaboratoryTest::testcategoryProperty, false);
            FXTable.addColumn(t1table, "Encoder", LaboratoryTest::encoderProperty, false);
            FXTable.addColumn(t1table, "OR #", LaboratoryTest::ornumberProperty, false);
            TableColumn actCol = FXTable.addColumn(t1table, " ", LaboratoryTest::patientProperty, false, 76, 76, 76);
            
            selCol.setCellFactory(column -> {
                return new TableCell<LaboratoryTest, Boolean>() {

                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);

                        if (empty) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                LaboratoryTest row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    JFXCheckBox selectBox = new JFXCheckBox("");
                                    //selectBox.getStyleClass().add("btn-success");
                                    selectBox.setMinSize(32, 32);

                                    selectBox.selectedProperty().bindBidirectional(row_data.selectedProperty());
                                    selectBox.setStyle("-fx-alignment:CENTER;");

                                    selectBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                                        /*
                                        List<Item> recs = recordTable.getItems();
                                        List<Boolean> selList = recs.stream().map(lab -> lab.isSelected()).collect(Collectors.toList());
                                        printbarcodeBtn.setVisible(selList.contains(true));
                                        */
                                    });

                                    setGraphic(selectBox);
                                    setStyle("");
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
            
            actCol.setCellFactory(column -> {
                return new TableCell<LaboratoryTest, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                LaboratoryTest row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        LaboratoryTestFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, LaboratoryUXController.this);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete this record & charge!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(mainStack, "Confirmation", new Label("Confirm deletion of record & charge ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            dl.close();
                                            FXTask task = new FXTask(){
                                                @Override
                                                protected void load() {
                                                    try{
                                                        Platform.runLater(()->{
                                                            maskerPane.setVisible(true);
                                                            btn.setDisable(true);
                                                        });
                                                        HospitalChargeItem chitem = (HospitalChargeItem)SQLTable.get(HospitalChargeItem.class, HospitalChargeItem.RECORDTABLE+"='"+LaboratoryTest.TABLE_NAME+"' AND "+HospitalChargeItem.RECORDTABLEID+"="+row_data.getId());
                                                        if(chitem != null){
                                                            HospitalCharge ch = (HospitalCharge)SQLTable.get(HospitalCharge.class, HospitalCharge.ID, String.valueOf(chitem.getHospitalcharge_id()));
                                                            if(ch != null){
                                                                List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class,HospitalChargeItem.HOSPITALCHARGE_ID+"='"+ch.getId()+"'");
                                                                for(HospitalChargeItem chi:items){
                                                                    if(chi.getId()== chitem.getId()){
                                                                        items.remove(chi);
                                                                        break;
                                                                    }
                                                                }
                                                                ch.setItems(items);
                                                                ch.calculateTotal(items);
                                                                if(ch.update()){
                                                                    chitem.delete();
                                                                    row_data.delete();
                                                                    Platform.runLater(()->{
                                                                        FXDialog.showMessageDialog(mainStack, "Deleted", "Laboratory Record/Charge has been deleted!", FXDialog.SUCCESS);
                                                                        loadLabTestList(null);
                                                                    });
                                                                }
                                                            }
                                                        }else{

                                                        }
                                                    }catch(Exception er){
                                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                    }finally {
                                                        Platform.runLater(()->{
                                                            maskerPane.setVisible(false);
                                                            btn.setDisable(false);
                                                        });
                                                    }
                                                }
                                            };
                                            Care.process(task);
                                        });
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,voidBtn);

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
            
            t1searchF.setOnKeyReleased(evt ->{
                if(evt.getCode() == KeyCode.ENTER){
                    if(!t1searchF.getText().isEmpty()){
                        try{
                            t1searchBtn.fire();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(evt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t1searchF.setText("");
                        loadLabTestList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
            
            
            //TAB2
            t2table.setEditable(true);
            t2table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            TableColumn timeCol2 = FXTable.addColumn(t2table, "Timestamp", HospitalCharge::chargetimeProperty, false, 130, 130, 130);
            FXTable.addColumn(t2table, "Charge #", HospitalCharge::chargenumberProperty, false, 125, 125, 125);
            FXTable.addColumn(t2table, "Patient Name", HospitalCharge::chargetoProperty, false);
            FXTable.addColumn(t2table, "User", HospitalCharge::userProperty, false);
            FXTable.addColumn(t2table, "Admission ID #", HospitalCharge::admission_idProperty, false,100,100,100);
            FXTable.addColumn(t2table, "Cancelled", HospitalCharge::cancelledProperty, false);
            TableColumn timeCol3 =  FXTable.addColumn(t2table, "Cancel Time", HospitalCharge::canceltimeProperty, false,130,130,130);
            FXTable.addColumn(t2table, "Net Payable", HospitalCharge::netsalesProperty, false,80,80,80);
            FXTable.addColumn(t2table, "OR #", HospitalCharge::ornumberProperty, false,80,80,80);
            TableColumn actCol2 = FXTable.addColumn(t2table, " ", HospitalCharge::chargetoProperty, false, 76, 76, 76);
            
            FXTable.setTimestampColumn(timeCol2);
            FXTable.setTimestampColumn(timeCol3);
            
            actCol2.setCellFactory(column -> {
                return new TableCell<HospitalCharge, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalCharge row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                                                        
                                    JFXButton printBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PRINT, "14px", evt -> {
                                        FXTask printTask = new FXTask() {
                                            @Override
                                            protected void load() {
                                                try{
                                                    Map<String,String> opts = GlobalOption.getMap("General");
                                                    List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"='"+row_data.getId()+"'");
                                                    HospitalCharge chr = row_data.getModelClone();
                                                    chr.setItems(itms);
                                                    chr.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Laboratory", true);
                                                }catch(Exception er){
                                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                }
                                            }
                                        };
                                        Care.process(printTask);
                                    });
                                    
                                    printBtn.setTooltip(new Tooltip("Print"));
                                    printBtn.getStyleClass().add("btn-control");
                                    printBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    printBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Void this Transaction!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(mainStack, "Confirmation", new Label("Void Charge "+row_data.getChargenumber()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            dl.close();
                                            row_data.setCancelled(Care.getUser().getName());
                                            row_data.setCanceltime(LocalDateTime.now());
                                            List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class,HospitalChargeItem.HOSPITALCHARGE_ID+"='"+row_data.getId()+"'");
                                            if(row_data.update(true)){
                                                items.stream().forEach(itm->{
                                                    SQLTable.execute("DELETE FROM "+LaboratoryTest.TABLE_NAME+" WHERE "+LaboratoryTest.ID+"='"+itm.getRecordtableid()+"'");
                                                    itm.setCancelled(Care.getUser().getName());
                                                    itm.setCanceltime(LocalDateTime.now());
                                                    itm.update();
                                                });                                                
                                                FXDialog.showMessageDialog(mainStack, "Voided", "Laboratory Charge has been voided!", FXDialog.SUCCESS);
                                                loadLabChargesList(null);
                                            }else{
                                                FXDialog.showMessageDialog(mainStack, "Failed", "Server Communication Failure", FXDialog.SUCCESS);
                                            }
                                        });
                                    });
                                    voidBtn.setDisable((row_data.getVoidtime()!=null));
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(printBtn,voidBtn);

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
            
            t2searchF.setOnKeyReleased(searchEvt ->{
                if(searchEvt.getCode() == KeyCode.ENTER){
                    if(!t2searchF.getText().isEmpty()){
                        try{
                            loadLabChargesList(HospitalCharge.CHARGEFACILITY+"='Laboratory' AND "+HospitalCharge.CHARGETO+" LIKE '%"+t2searchF.getText()+"%' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(searchEvt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t2searchF.setText("");
                        loadLabChargesList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
            
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null){
                    if(newVal==chargeTab){
                        loadLabChargesList(null);
                    }else if(newVal==testsTab){
                        loadLabTestList(null);
                    }
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            loadLabTestList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadLabtestRecordFilters();
            loadLabChargeRecordFilters();
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
    
    
    private void loadLabTestList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t1table, new ArrayList());
                            t1table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<LaboratoryTest> records;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(LaboratoryTest.class, LaboratoryTest.TESTTIME+">='"+t1+"' AND "+LaboratoryTest.TESTTIME+"<='"+t2+"' ORDER BY "+LaboratoryTest.TESTTIME+" DESC");                            
                        }else{
                            records = SQLTable.list(LaboratoryTest.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t1table, records);
                            t1resultsLbl.setText("Results : "+records.size());
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
    
    private void loadLabChargesList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t2table, new ArrayList());
                            t2table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<LaboratoryTest> records;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' AND "+HospitalCharge.CHARGEFACILITY+"='Laboratory' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
                        }else{
                            records = SQLTable.list(HospitalCharge.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t2table, records);
                            t2resultsLbl.setText("Results : "+records.size());
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
    
    private void loadLabtestRecordFilters() {
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
                            loadLabTestList(LaboratoryTest.TESTTIME + ">='" + sqT1 + "' AND " + LaboratoryTest.TESTTIME + "<='" + sqT2 + "' ORDER BY " + LaboratoryTest.TESTTIME + " DESC");
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
                loadLabTestList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<LaboratoryTest> records = t1table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("labtests.xlsx");
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
                                    ExcelManager.export(LaboratoryTest.class, records, sFile);
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
            
            FXTable.addCustomTableMenu(t1table, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadLabChargeRecordFilters() {
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
                            loadLabChargesList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGEFACILITY+"='Laboratory' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
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
                loadLabChargesList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalCharge> records = t2table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("labcharges.xlsx");
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
                                    ExcelManager.export(HospitalCharge.class, records, sFile);
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
    
}
