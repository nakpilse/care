package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import models.Admission;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.HospitalService;
import models.LaboratoryTest;
import models.Patient;
import models.RadiologyTest;
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
import utils.FXDateEntry;
import utils.FXTextEntry;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class RadiologyUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
        
    @FXML
    private JFXTabPane mainTabPane;
    
    @FXML
    private JFXTextField t1searchF;

    @FXML
    private TableView<RadiologyTest> t1table;

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
            CreateRadChargeFormController.showDialog(mainStack, maskerPane, RadiologyUXController.this);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void addPatient(ActionEvent event) {
        try{
            PatientRegistrationFormController.showDialog(mainStack, maskerPane, RadiologyUXController.this,t1addPatientBtn);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchPatientMasterRecords(ActionEvent event) {
        try{
            if(t1searchF.getText() != null){
                String key = t1searchF.getText();
                String con = RadiologyTest.PATIENT+" LIKE '%"+key+"%' ORDER BY "+RadiologyTest.TESTTIME+" DESC";
                loadRadTestList(con);
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
                loadRadTestList(null);
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
            
            TableColumn selCol = FXTable.addColumn(t1table, " ", RadiologyTest::selectedProperty, false, 45, 45, 45);
            TableColumn timeCol = FXTable.addColumn(t1table, "Timestamp", RadiologyTest::testtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t1table, "Patient Name", RadiologyTest::patientProperty, false);
            FXTable.addColumn(t1table, "Physician", RadiologyTest::physicianProperty, false);
            FXTable.addColumn(t1table, "Exam", RadiologyTest::testnameProperty, false);
            FXTable.addColumn(t1table, "Category", RadiologyTest::testcategoryProperty, false);
            FXTable.addColumn(t1table, "Encoder", RadiologyTest::encoderProperty, false);
            FXTable.addColumn(t1table, "OR #", RadiologyTest::ornumberProperty, false);
            TableColumn actCol = FXTable.addColumn(t1table, " ", RadiologyTest::patientProperty, false, 112, 112, 112);
            
            selCol.setCellFactory(column -> {
                return new TableCell<RadiologyTest, Boolean>() {

                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);

                        if (empty) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                RadiologyTest row_data = getTableView().getItems().get(getIndex());
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
                return new TableCell<RadiologyTest, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                RadiologyTest row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(112, 40);
                                    container.setMaxSize(112, 40);
                                    container.setPrefSize(112, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        RadiologyTestFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, RadiologyUXController.this);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton patientBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.USER, "14px", evt -> {
                                        try{
                                            Patient record = (Patient)SQLTable.get(Patient.class, row_data.getPatient_id());
                                            if(record != null){
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
                                                                            loadRadTestList(null);
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
                                            }
                                            
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    
                                    patientBtn.setTooltip(new Tooltip("Edit Patient Info"));
                                    patientBtn.getStyleClass().add("btn-primary");
                                    patientBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    patientBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    patientBtn.setDisable(row_data.getPatient_id() <= 0);
                                    
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
                                                        HospitalChargeItem chitem = (HospitalChargeItem)SQLTable.get(HospitalChargeItem.class, HospitalChargeItem.RECORDTABLE+"='"+RadiologyTest.TABLE_NAME+"' AND "+HospitalChargeItem.RECORDTABLEID+"="+row_data.getId());
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
                                                                        FXDialog.showMessageDialog(mainStack, "Deleted", "Radiology Record/Charge has been deleted!", FXDialog.SUCCESS);
                                                                        loadRadTestList(null);
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
                                    container.getChildren().addAll(patientBtn,viewBtn);

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
                        loadRadTestList(null);
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
                                                    chr.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Radiology", true);
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
                                                    SQLTable.execute("DELETE FROM "+RadiologyTest.TABLE_NAME+" WHERE "+RadiologyTest.ID+"='"+itm.getRecordtableid()+"'");
                                                    itm.setCancelled(Care.getUser().getName());
                                                    itm.setCanceltime(LocalDateTime.now());
                                                    itm.update();
                                                });                                                
                                                FXDialog.showMessageDialog(mainStack, "Voided", "Radiology Charge has been voided!", FXDialog.SUCCESS);
                                                loadRadChargesList(null);
                                            }else{
                                                FXDialog.showMessageDialog(mainStack, "Failed", "Server Communication Failure", FXDialog.SUCCESS);
                                            }
                                        });
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    voidBtn.setDisable(row_data.getPaymenttime() != null);
                                    
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
                            loadRadChargesList(HospitalCharge.CHARGEFACILITY+"='Radiology' AND "+HospitalCharge.CHARGETO+" LIKE '%"+t2searchF.getText()+"%' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(searchEvt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t2searchF.setText("");
                        loadRadChargesList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
            
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null){
                    if(newVal==chargeTab){
                        loadRadChargesList(null);
                    }else if(newVal==testsTab){
                        loadRadTestList(null);
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
            loadRadTestList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadRadtestRecordFilters();
            loadRadChargeRecordFilters();
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
    
    
    private void loadRadTestList(String conditions){
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
                        List<RadiologyTest> records;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(RadiologyTest.class, RadiologyTest.TESTTIME+">='"+t1+"' AND "+RadiologyTest.TESTTIME+"<='"+t2+"' ORDER BY "+RadiologyTest.TESTTIME+" DESC");                            
                        }else{
                            records = SQLTable.list(RadiologyTest.class,conditions);
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
    
    private void loadRadChargesList(String conditions){
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
                            records = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' AND "+HospitalCharge.CHARGEFACILITY+"='Radiology' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
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
    
    private void loadRadtestRecordFilters() {
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
                            loadRadTestList(RadiologyTest.TESTTIME + ">='" + sqT1 + "' AND " + RadiologyTest.TESTTIME + "<='" + sqT2 + "' ORDER BY " + RadiologyTest.TESTTIME + " DESC");
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
                loadRadTestList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<RadiologyTest> records = t1table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("rabtests.xlsx");
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
                                    ExcelManager.export(RadiologyTest.class, records, sFile);
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
    
    private void loadRadChargeRecordFilters() {
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
                            loadRadChargesList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGEFACILITY+"='Radiology' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
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
                loadRadChargesList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalCharge> records = t2table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("radcharges.xlsx");
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
