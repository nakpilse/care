package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleNode;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.Admission;
import models.BillStatement;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalService;
import models.Patient;
import models.PatientBenefit;
import models.Payment;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class BillingUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;

    @FXML
    private JFXToggleNode billingMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode chargeMenu;

    @FXML
    private JFXToggleNode paymentMenu;

    @FXML
    private JFXToggleNode benefitsMenu;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab billingTab;

    @FXML
    private JFXTextField t1searchF;

    @FXML
    private JFXButton newt1Btn;

    @FXML
    private TableView<BillStatement> t1table;

    @FXML
    private Label t1resLbl;

    @FXML
    private Tab chargeTab;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private JFXButton newt2Btn;

    @FXML
    private TableView<HospitalCharge> t2table;

    @FXML
    private Label t2resLbl;

    @FXML
    private Tab paymentsTab;

    @FXML
    private JFXTextField t3searchF;

    @FXML
    private TableView<Payment> t3table;

    @FXML
    private Label t3resLbl;

    @FXML
    private Tab benefitsTab;

    @FXML
    private JFXTextField t4searchF;

    @FXML
    private TableView<PatientBenefit> t4table;

    @FXML
    private Label t4resLbl;

    @FXML
    void addNewBillStatement(ActionEvent event) {
        //BillingStatementFormController.showDialog(new BillStatement(), mainStack, maskerPane, BillingUXController.this, newt1Btn);
        try {
            List<Patient> patients = SQLTable.list(Patient.class);
            List<String> patientnames = new ArrayList();
            BillStatement rec = new BillStatement();
            
            patients.stream().forEach(patient->{
                patientnames.add(patient.getFullname());
            });
            
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35, 25, 25, 25));            
            
            JFXTextField soaF = new JFXTextField();
            soaF.setPromptText("SOA no.");
            soaF.setMinHeight(28);
            soaF.setMinWidth(250);
            soaF.setMaxWidth(250);
            soaF.setPrefWidth(250);
            soaF.setLabelFloat(true);

            JFXTextField patientF = new JFXTextField();
            patientF.setPromptText("Select Patient");
            patientF.setMinHeight(28);
            patientF.setMinWidth(250);
            patientF.setMaxWidth(250);
            patientF.setPrefWidth(250);
            patientF.setLabelFloat(true);
                        
            JFXComboBox<Admission> admissionC = new JFXComboBox();
            admissionC.setMinHeight(28);
            admissionC.setMinWidth(250);
            admissionC.setMaxWidth(250);
            admissionC.setPrefWidth(250);
            admissionC.setPromptText("Select Admission");
            admissionC.setLabelFloat(true);
            
            patientF.textProperty().addListener((olb,oldVal,newVal)->{
                String name = newVal;
                if(newVal != null && !newVal.isEmpty()){
                    Patient sel_patient = null;
                    for(Patient pat:patients){
                        if(name.equals(pat.getFullname())){
                            sel_patient = pat;
                            break;
                        }
                    }
                    
                    if(sel_patient != null){
                        final int patient_id = sel_patient.getId();
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {
                                try {                        
                                    List<Admission> admissions = SQLTable.list(Admission.class, Admission.PATIENT_ID+"='"+patient_id+"' ORDER BY "+Admission.ADMISSIONTIME+" DESC");
                                    Platform.runLater(()->{
                                        admissionC.setItems(FXCollections.observableArrayList(admissions));
                                    });
                                } catch (Exception er) {
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                } finally {
                                    
                                }
                            }
                        };
                        Care.process(task);
                    }else{
                        admissionC.getItems().clear();
                    }
                }else{
                    admissionC.getItems().clear();
                }
            });
            
            soaF.textProperty().bindBidirectional(rec.billnumberProperty());
            TextFields.bindAutoCompletion(patientF, patientnames);
            
            FXField.addRequiredValidator(soaF);
            FXField.addRequiredValidator(patientF);
            FXField.addDuplicateValidator(soaF, rec, BillStatement.BILLNUMBER);
            FXField.addFocusValidationListener(patientF,soaF);
            

            content.getChildren().addAll(soaF,patientF,admissionC);

            JFXButton filter = new JFXButton("Create");
            filter.getStyleClass().add("btn-info");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Create Billing Statement", content, FXDialog.PRIMARY, filter);
            filter.setOnAction(deleteEvt -> {
                if(patientF.validate()){
                    String name = patientF.getText();
                    if(name != null && !name.isEmpty()){
                        Patient sel_patient = null;
                        for(Patient pat:patients){
                            if(name.equals(pat.getFullname())){
                                sel_patient = pat;
                                break;
                            }
                        }
                        if(sel_patient != null){
                            Admission admrecord = admissionC.getSelectionModel().getSelectedItem();
                            
                            rec.setPatientname(sel_patient.getFullname());
                            rec.setPatient_id(sel_patient.getId());
                            rec.setAge(sel_patient.getAge());
                            rec.setAgestring("Yrs Old");
                            rec.setGender(sel_patient.getGender());
                            rec.setUser(Care.getUser().getName());
                            rec.setCreatedby(Care.getUser().getName());
                            rec.setUser_id(Care.getUser().getId());
                            
                            if(admrecord != null){
                                rec.setAdmission_id(admrecord.getId());
                                rec.setAdmissiontime(admrecord.getAdmissiontime());
                                rec.setDischargedtime(admrecord.getDischargetime());         
                                rec.setFinaldiagnosis(admrecord.getFinaldiagnosis());
                                rec.setOtherdiagnosis(admrecord.getOtherdiagnosis());
                            }
                            
                            int id = rec.save(true);
                            if(id > 0){
                                dialog.close();
                                rec.setId(id);
                                BillingStatementFormController.showDialog(rec, mainStack, maskerPane, BillingUXController.this, newt1Btn);
                            }else{
                                FXDialog.showMessageDialog(mainStack, "Communication Failure", "Failed to connecto to database server!", FXDialog.DANGER);
                            }
                            
                        }else{
                            FXDialog.showMessageDialog(mainStack, "Invalid Patient", "Please select a patient from suggested list!", FXDialog.DANGER);
                        }
                    }
                }
            });

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void addNewHospitalCharge(ActionEvent event) {
        try{
            
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadBenefitsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(benefitsTab);
        loadBenefitList(null);
    }

    @FXML
    void loadBillingMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(billingTab);
        loadBillstatementList(null);
    }

    @FXML
    void loadChargeMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(chargeTab);
        loadChargesList(null);
    }

    @FXML
    void loadPaymentMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(paymentsTab);
        loadPaymentList(null);
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
                loadBillstatementList(null);
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
            loadChargesTabCustomizations();
            loadPaymentTable();
            loadBillingTabCustomizations();
            loadBenefitsTable();
            
            //Remove FocusTab
            mainTabPane.addEventFilter(KeyEvent.ANY, event -> {
                if (event.getCode().isArrowKey() && event.getTarget() == mainTabPane) {
                    event.consume();
                }
            });
            //Disable Unselected Tab
            menuGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                if (newVal == null) {
                    oldVal.setSelected(true);
                }
            });
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            loadBillstatementList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadChargesTableFilters();
            loadPaymentTableFilters();
            loadBillingTableFilters();
            loadBenefitTableFilters();
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
    
    private void loadBillingTabCustomizations(){
        try{
            t1table.setEditable(false);
            t1table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(t1table);

            TableColumn timeCol = FXTable.addColumn(t1table, "Timestamp", BillStatement::billtimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t1table, "SOA no.", BillStatement::billnumberProperty, false, 80, 80, 80);
            FXTable.addColumn(t1table, "Patient", BillStatement::patientnameProperty, false);
            FXTable.addColumn(t1table, "Net Payable", BillStatement::netsalesProperty, false,100,100,100);
            FXTable.addColumn(t1table, "Paid Amount", BillStatement::paidamountProperty, false,100,100,100);
            FXTable.addColumn(t1table, "Cancelled", BillStatement::cancelledProperty, false,125,125,125);
            FXTable.addColumn(t1table, "Finalized", BillStatement::finalizedProperty, false);
            TableColumn actCol = FXTable.addColumn(t1table, " ", BillStatement::patientnameProperty, false,120,120,120);
            
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatement, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatement row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(120, 40);
                                    container.setMaxSize(120, 40);
                                    container.setPrefSize(120, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Billing Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        BillingStatementFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, BillingUXController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Item"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Cancel");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to cancel this bill?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            row_data.setCancelled(Care.getUser().getName());
                                            row_data.setCanceltime(LocalDateTime.now());
                                            row_data.update(true, BillStatement.CANCELLED,BillStatement.CANCELTIME);
                                            d.close();
                                        });
                                    });
                                    
                                    delBtn.setTooltip(new Tooltip("Void"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setDisable(row_data.getFinalizedtime()!= null || row_data.getCanceltime() != null);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

                                    setGraphic(container);
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
    
    private void loadChargesTabCustomizations(){
        try{
            t2table.setEditable(false);
            t2table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(t2table);

            TableColumn timeCol = FXTable.addColumn(t2table, "Timestamp", HospitalCharge::chargetimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t2table, "Charge #", HospitalCharge::chargenumberProperty, false, 120, 120, 120);
            FXTable.addColumn(t2table, "Facility", HospitalCharge::chargefacilityProperty, false, 120, 120, 120);
            FXTable.addColumn(t2table, "Type", HospitalCharge::chargetypeProperty, false);
            FXTable.addColumn(t2table, "Patient", HospitalCharge::chargetoProperty, false);
            //FXTable.addColumn(t2table, "Admission ID", HospitalCharge::admission_idProperty, false,65,65,65);
            FXTable.addColumn(t2table, "Invoice #", HospitalCharge::invoicenumberProperty, false,70,70,70);
            FXTable.addColumn(t2table, "OR #", HospitalCharge::ornumberProperty, false,70,70,70);
            FXTable.addColumn(t2table, "Net Amount", HospitalCharge::netsalesProperty, false,80,80,80);
            FXTable.addColumn(t2table, "Paid Amount", HospitalCharge::paidamountProperty, false,80,80,80);
            FXTable.addColumn(t2table, "Voided", HospitalCharge::voidedProperty, false);
            TableColumn actCol = FXTable.addColumn(t2table, " ", HospitalCharge::chargenumberProperty, false, 120, 120, 120);
            
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
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
                                    container.setMinSize(120, 40);
                                    container.setMaxSize(120, 40);
                                    container.setPrefSize(120, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Charge Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Item"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);


                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Void");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to void this item?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            row_data.setVoided(Care.getUser().getName());
                                            row_data.setVoidtime(LocalDateTime.now());
                                            row_data.update(true, HospitalCharge.VOIDED,HospitalCharge.VOIDTIME);
                                            d.close();
                                            loadChargesList(null);
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Void"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setDisable(row_data.getVoidtime() != null);
                                    

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

                                    setGraphic(container);
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
    
    private void loadPaymentTable(){
        try{
            t3table.setEditable(true);
            t3table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t3table, "Timestamp", Payment::paymenttimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t3table, "Patient", Payment::patientProperty, false);
            FXTable.addColumn(t3table, "OR #", Payment::ornumberProperty, false);
            FXTable.addColumn(t3table, "Invoice #", Payment::invoicenumberProperty, false);
            FXTable.addColumn(t3table, "Cashier", Payment::cashierProperty, false);
            FXTable.addColumn(t3table, "Paid By", Payment::paidbyProperty, false);
            FXTable.addColumn(t3table, "Cancelled", Payment::cancelledProperty, false);
            FXTable.addColumn(t3table, "Amount", Payment::amountProperty, false,135,135,135);
            TableColumn actCol = FXTable.addColumn(t3table, "Actions", Payment::patientProperty, false, 76, 76, 76);
            
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Payment, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Payment row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Payment Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Cancel"));
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
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBenefitsTable(){
        try{
            t4table.setEditable(true);
            t4table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t4table, "SOA no.", PatientBenefit::billnumberProperty, false, 135, 135, 135);
            FXTable.addColumn(t4table, "Patient", PatientBenefit::patientProperty, false);
            FXTable.addColumn(t4table, "Source", PatientBenefit::benefitsourceProperty, false);
            FXTable.addColumn(t4table, "Code", PatientBenefit::benefitcodeProperty, false);
            FXTable.addColumn(t4table, "Amount", PatientBenefit::amountProperty, false);
            FXTable.addColumn(t4table, "Created By", PatientBenefit::createdbyProperty, false);
            TableColumn createCol = FXTable.addColumn(t4table, "Created", PatientBenefit::created_atProperty, false, 135, 135, 135);
            TableColumn actCol = FXTable.addColumn(t4table, "Actions", PatientBenefit::patientProperty, false, 76, 76, 76);
            
            FXTable.setTimestampColumn(createCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<PatientBenefit, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                PatientBenefit row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Benefit Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Delete"));
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
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadChargesList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t2table, new ArrayList());
                            t2table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<HospitalCharge> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(HospitalCharge.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<HospitalCharge> filteredRecords = FXTable.setFilteredList(t2table, charges);
                            HospitalCharge.createTableFilter(t2searchF, filteredRecords);
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
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPaymentList(String conditions){
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
                        List<Payment> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(Payment.class, Payment.PAYMENTTIME+">='"+t1+"' AND "+Payment.PAYMENTTIME+"<='"+t2+"' ORDER BY "+Payment.PAYMENTTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(Payment.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<Payment> filteredRecords = FXTable.setFilteredList(t3table, charges);
                            Payment.createTableFilter(t3searchF, filteredRecords);
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
    
    private void loadBillstatementList(String conditions){
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
                        List<BillStatement> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(BillStatement.class, BillStatement.BILLTIME+">='"+t1+"' AND "+BillStatement.BILLTIME+"<='"+t2+"' ORDER BY "+BillStatement.BILLTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(BillStatement.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<BillStatement> filteredRecords = FXTable.setFilteredList(t1table, charges);
                            BillStatement.createTableFilter(t1searchF, filteredRecords);
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
    
    private void loadBenefitList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t4table, new ArrayList());
                            t4table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<PatientBenefit> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(PatientBenefit.class, PatientBenefit.CREATED_AT+">='"+t1+"' AND "+PatientBenefit.CREATED_AT+"<='"+t2+"' ORDER BY "+PatientBenefit.CREATED_AT+" DESC");                            
                        }else{
                            charges = SQLTable.list(PatientBenefit.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<PatientBenefit> filteredRecords = FXTable.setFilteredList(t4table, charges);
                            PatientBenefit.createTableFilter(t4searchF, filteredRecords);
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
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadChargesTableFilters() {
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
                            loadChargesList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
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
                loadChargesList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalCharge> records = t2table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("charges.xlsx");
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
    
    private void loadPaymentTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> cashiers = SQLTable.distinct(Payment.class, Payment.CASHIER);
                    List<String> patients = SQLTable.distinct(Payment.class, Payment.PATIENT);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField cashierF = new JFXTextField();
                    cashierF.setPromptText("Cashier");
                    cashierF.setMinHeight(28);
                    cashierF.setMinWidth(250);
                    cashierF.setMaxWidth(250);
                    cashierF.setPrefWidth(250);
                    cashierF.setLabelFloat(true);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);
                    patientF.setLabelFloat(true);

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
                    
                    TextFields.bindAutoCompletion(cashierF, cashiers);
                    TextFields.bindAutoCompletion(patientF, patients);

                    content.getChildren().addAll(cashierF,patientF,dfrom, tfrom, dto, tto, warning);

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
                            loadPaymentList(Payment.PAYMENTTIME + ">='" + sqT1 + "' AND " + Payment.PAYMENTTIME + "<='" + sqT2 + "' AND "+Payment.CASHIER+" LIKE '%"+cashierF.getText()+"%' AND "+Payment.PATIENT+" LIKE '%"+patientF.getText()+"%' ORDER BY " + Payment.PAYMENTTIME + " DESC");
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
                loadPaymentList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Payment> records = t3table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("payments.xlsx");
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
                                    ExcelManager.export(Payment.class, records, sFile);
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
    
    
    private void loadBillingTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> cashiers = SQLTable.distinct(Payment.class, Payment.CASHIER);
                    List<String> patients = SQLTable.distinct(Payment.class, Payment.PATIENT);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField cashierF = new JFXTextField();
                    cashierF.setPromptText("Cashier");
                    cashierF.setMinHeight(28);
                    cashierF.setMinWidth(250);
                    cashierF.setMaxWidth(250);
                    cashierF.setPrefWidth(250);
                    cashierF.setLabelFloat(true);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);
                    patientF.setLabelFloat(true);

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
                    
                    TextFields.bindAutoCompletion(cashierF, cashiers);
                    TextFields.bindAutoCompletion(patientF, patients);

                    content.getChildren().addAll(cashierF,patientF,dfrom, tfrom, dto, tto, warning);

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
                            loadBillstatementList(BillStatement.BILLTIME + ">='" + sqT1 + "' AND " + BillStatement.BILLTIME + "<='" + sqT2 + "' AND "+BillStatement.CASHIER+" LIKE '%"+cashierF.getText()+"%' AND "+BillStatement.PATIENTNAME+" LIKE '%"+patientF.getText()+"%' ORDER BY " + BillStatement.BILLTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon unfinalIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label unfinalLb = new Label("Non-Finalized Records");
            unfinalLb.setCursor(Cursor.HAND);
            unfinalLb.setGraphic(unfinalIcon);
            unfinalLb.setOnMouseClicked(evt -> {
                loadBillstatementList(BillStatement.FINALIZEDTIME+" IS NULL ORDER BY " + BillStatement.BILLTIME + " DESC");
            });
            
            GlyphIcon finalIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label finalLb = new Label("Finalized Records");
            finalLb.setCursor(Cursor.HAND);
            finalLb.setGraphic(finalIcon);
            finalLb.setOnMouseClicked(evt -> {
                loadBillstatementList(BillStatement.FINALIZEDTIME+" IS NOT NULL ORDER BY " + BillStatement.BILLTIME + " DESC");
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatement> records = t1table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("billstatements.xlsx");
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
                                    ExcelManager.export(BillStatement.class, records, sFile);
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
            
            
            FXTable.addCustomTableMenu(t1table, filterLb, unfinalLb,finalLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBenefitTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> createdby = SQLTable.distinct(PatientBenefit.class, PatientBenefit.CREATEDBY);
                    List<String> patients = SQLTable.distinct(PatientBenefit.class, PatientBenefit.PATIENT);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField createdbyF = new JFXTextField();
                    createdbyF.setPromptText("Created By");
                    createdbyF.setMinHeight(28);
                    createdbyF.setMinWidth(250);
                    createdbyF.setMaxWidth(250);
                    createdbyF.setPrefWidth(250);
                    createdbyF.setLabelFloat(true);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);
                    patientF.setLabelFloat(true);

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
                    
                    TextFields.bindAutoCompletion(createdbyF, createdby);
                    TextFields.bindAutoCompletion(patientF, patients);

                    content.getChildren().addAll(createdbyF,patientF,dfrom, tfrom, dto, tto, warning);

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
                            loadBenefitList(PatientBenefit.CREATED_AT + ">='" + sqT1 + "' AND " + PatientBenefit.CREATED_AT + "<='" + sqT2 + "' AND "+PatientBenefit.CREATEDBY+" LIKE '%"+createdbyF.getText()+"%' AND "+PatientBenefit.PATIENT+" LIKE '%"+patientF.getText()+"%' ORDER BY " + PatientBenefit.CREATED_AT + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Last 30 Days");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadBenefitList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<PatientBenefit> records = t4table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("benefits.xlsx");
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
                                    ExcelManager.export(PatientBenefit.class, records, sFile);
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
    
    
}
