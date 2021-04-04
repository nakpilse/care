package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
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
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import models.BillCategorySummary;
import models.BillStatement;
import models.BillStatementItem;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.PatientBenefit;
import models.Payment;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.FileKit;
import nse.dcfx.utils.NumberKit;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;
import utils.FXTextEntry;
import utils.ListSelect;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class BillingStatementFormController implements Initializable,FormController<BillStatement>,UIController {

    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;    
    private static MaskerPane maskerPane;
    private BillStatement record = null;
    private final List<BillStatementItem> DEL_ITEMS = new ArrayList();
    private final List<BillStatementItem> ROOM_ITEMS = new ArrayList();
    private final List<BillStatementItem> PHARMACY_ITEMS = new ArrayList();
    private final List<BillStatementItem> SUPPLY_ITEMS = new ArrayList();
    private final List<BillStatementItem> HOSPITAL_ITEMS = new ArrayList();
    private final List<BillStatementItem> PROFESSIONAL_ITEMS = new ArrayList();
    private List<Payment> PAYMENTS = new ArrayList();
    private List<Payment> DEL_PAYMENTS = new ArrayList();
    private List<PatientBenefit> BENEFITS = new ArrayList();
    private List<PatientBenefit> DEL_BENEFITS = new ArrayList();
    
    
    
    @FXML
    private Label titleLbl;

    @FXML
    private HBox btnContainer;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton excelBtn;

    @FXML
    private JFXButton printBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode billingMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode roomsMenu;

    @FXML
    private JFXToggleNode pharmacyMenu;

    @FXML
    private JFXToggleNode supplyMenu;

    @FXML
    private JFXToggleNode hospitalMenu;

    @FXML
    private JFXToggleNode professionalMenu;

    @FXML
    private JFXToggleNode benefitsMenu;

    @FXML
    private JFXToggleNode paymentsMenu;

    @FXML
    private JFXToggleNode validationMenu;

    @FXML
    private ToggleGroup menuGroup1;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab billingTab;

    @FXML
    private TextField t1billnumF;

    @FXML
    private TextField t1hospitalizationF;

    @FXML
    private TextField t1billtimeF;

    @FXML
    private TextField t1createdbyF;

    @FXML
    private TextField t1admssiontimeF;

    @FXML
    private TextField t1dischargedtimeF;

    @FXML
    private TextArea t1finaldiagF;

    @FXML
    private TextArea t1otherdiagF;

    @FXML
    private TextField t1firstcaserateF;

    @FXML
    private TextField t1secondcaserateF;

    @FXML
    private TableView<BillCategorySummary> t1table;

    @FXML
    private Label t1grossL;

    @FXML
    private Label t1vatsalesL;

    @FXML
    private Label t1nonvatsalesL;

    @FXML
    private Label t1scpwdL;

    @FXML
    private Label t1empotL;

    @FXML
    private Label t1firstcaserateL;

    @FXML
    private Label t1secondcaserateL;

    @FXML
    private Label t1otherbenefitsL;

    @FXML
    private Label t1vatamountL;

    @FXML
    private Label t1netpayablesL;

    @FXML
    private Label t1paidL;

    @FXML
    private Tab roomTab;

    @FXML
    private TableView<BillStatementItem> roomsTbl;

    @FXML
    private Label roomscountLbl;

    @FXML
    private Label roomsdetailLbl;

    @FXML
    private Tab pharmacyTab;

    @FXML
    private TableView<BillStatementItem> pharmacyTbl;

    @FXML
    private Label pharmacycountLbl;

    @FXML
    private Label pharmacydetailsLbl;

    @FXML
    private Tab supplyTab;

    @FXML
    private TableView<BillStatementItem> supplyTbl;

    @FXML
    private Label supplycountLbl;

    @FXML
    private Label supplydetailsLbl;

    @FXML
    private Tab hospitalTab;

    @FXML
    private TableView<BillStatementItem> hospitalTbl;

    @FXML
    private Label hospitalcountLbl;

    @FXML
    private Label hospitaldetailsLbl;

    @FXML
    private Tab professionalTab;

    @FXML
    private TableView<BillStatementItem> professionalTbl;

    @FXML
    private Label professionalcountLbl;

    @FXML
    private Label professionaldetailsLbl;

    @FXML
    private Tab benefitsTab;

    @FXML
    private TableView<PatientBenefit> benefitsTbl;

    @FXML
    private Label benefitscountLbl;

    @FXML
    private Label benefitsdetailsLbl;

    @FXML
    private Tab paymentsTab;

    @FXML
    private TableView<Payment> paymentsTbl;

    @FXML
    private Label paymentscountLbl;

    @FXML
    private Label paymentsdetailsLbl;

    @FXML
    private Tab validationTab;

    @FXML
    private TableView<HospitalCharge> validationTbl;

    @FXML
    private Label validationcountsLbl;

    @FXML
    private Label validationdetailsLbl;
    
    @FXML
    void saveBillingStatement(ActionEvent event) {
        try{
            List<String> phls = SQLTable.distinct(BillStatement.class, BillStatement.PHILHEALTHSTAFF);
            List<String> revs = SQLTable.distinct(BillStatement.class, BillStatement.REVIEWEDBY);
            List<String> nots = SQLTable.distinct(BillStatement.class, BillStatement.NOTEDBY);
            List<String> cass = SQLTable.distinct(BillStatement.class, BillStatement.CASHIERSTAFF);
            
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35,25,25,25));

            JFXTextField phlF = new JFXTextField();
            phlF.setPromptText("PHILHEALTH Staff");    
            phlF.setMinHeight(28);
            phlF.setMinWidth(250);
            phlF.setMaxWidth(250);
            phlF.setPrefWidth(250);   
            phlF.setLabelFloat(true);
            
            JFXTextField reviewF = new JFXTextField();
            reviewF.setPromptText("Reviewed By");    
            reviewF.setMinHeight(28);
            reviewF.setMinWidth(250);
            reviewF.setMaxWidth(250);
            reviewF.setPrefWidth(250);   
            reviewF.setLabelFloat(true);
            
            JFXTextField NotedF = new JFXTextField();
            NotedF.setPromptText("Noted By");    
            NotedF.setMinHeight(28);
            NotedF.setMinWidth(250);
            NotedF.setMaxWidth(250);
            NotedF.setPrefWidth(250);   
            NotedF.setLabelFloat(true);
            
            JFXTextField cashierF = new JFXTextField();
            cashierF.setPromptText("Noted By");    
            cashierF.setMinHeight(28);
            cashierF.setMinWidth(250);
            cashierF.setMaxWidth(250);
            cashierF.setPrefWidth(250);   
            cashierF.setLabelFloat(true);
            
            FXField.addRequiredValidator(phlF);
            FXField.addRequiredValidator(reviewF);
            FXField.addRequiredValidator(NotedF);
            FXField.addRequiredValidator(cashierF);
            
            TextFields.bindAutoCompletion(phlF, phls);
            TextFields.bindAutoCompletion(reviewF, revs);
            TextFields.bindAutoCompletion(NotedF, nots);
            TextFields.bindAutoCompletion(cashierF, cass);
            
            JFXButton save = new JFXButton("Save");
            save.getStyleClass().add("btn-success");
            
            
            JFXButton finalize = new JFXButton("Finalize & Save");
            finalize.getStyleClass().add("btn-danger");
            
            content.getChildren().addAll(phlF,reviewF,NotedF,cashierF);
            //new Label("Do you want to save changes?");
            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Save Bill Statement", content, FXDialog.PRIMARY, finalize, save);
            dialogx.setOnDialogOpened(evt->{
                dialogx.setOverlayClose(false);
            });
            save.setOnAction(saveEvt->{
                FXTask task = new FXTask() {
                    @Override
                    protected void load() {                           
                        try{
                            Platform.runLater(()->{
                                save.setDisable(true);
                            });
                            
                            List<BillStatementItem> items = new ArrayList();
                            items.addAll(ROOM_ITEMS);
                            items.addAll(PHARMACY_ITEMS);
                            items.addAll(SUPPLY_ITEMS);
                            items.addAll(HOSPITAL_ITEMS);
                            items.addAll(PROFESSIONAL_ITEMS);
                            record.calculateNet(items);
                            if(record.update(true)){
                                items.stream().forEach(itm->{
                                    if(itm.getId() <= 0){
                                        itm.save();
                                    }else{
                                        itm.update();
                                    }
                                });
                                PAYMENTS.stream().forEach(payment->{
                                    payment.update();
                                });
                                DEL_PAYMENTS.stream().forEach(payment->{
                                    payment.update();
                                });
                                BENEFITS.stream().forEach(ben->{
                                    if(ben.getId() <= 0){
                                        ben.save();
                                    }else{
                                        ben.update();
                                    }
                                });                                
                                DEL_BENEFITS.stream().forEach(ben->{
                                    ben.delete();
                                });
                                DEL_ITEMS.stream().forEach(itm->{
                                    itm.delete();
                                });
                                Platform.runLater(()->{
                                    dialogx.close();
                                    FXDialog.showMessageDialog(stackPane, "Successful", "Bill Statement has been updated!", FXDialog.SUCCESS);
                                });
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Failed", "Failed to communicate with database server!", FXDialog.DANGER);
                                });
                            }
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally{
                            Platform.runLater(()->{
                                save.setDisable(false);
                            });
                        }
                    }
                };
                Care.process(task);
            });
            
            finalize.setOnAction(saveEvt->{
                if(phlF.validate() && reviewF.validate() && NotedF.validate() && cashierF.validate()){
                    FXTask task = new FXTask() {
                        @Override
                        protected void load() {                           
                            try{
                                Platform.runLater(()->{
                                    save.setDisable(true);
                                });

                                List<BillStatementItem> items = new ArrayList();
                                items.addAll(ROOM_ITEMS);
                                items.addAll(PHARMACY_ITEMS);
                                items.addAll(SUPPLY_ITEMS);
                                items.addAll(HOSPITAL_ITEMS);
                                items.addAll(PROFESSIONAL_ITEMS);
                                record.setFinalized(Care.getUser().getName());
                                record.setFinalizedtime(LocalDateTime.now());
                                record.calculateNet(items);
                                record.setPhilhealthstaff(phlF.getText());
                                record.setReviewedby(reviewF.getText());
                                record.setNotedby(NotedF.getText());
                                record.setCashier(cashierF.getText());
                                if(record.update(true)){
                                    items.stream().forEach(itm->{
                                        if(itm.getId() <= 0){
                                            itm.save();
                                        }else{
                                            itm.update();
                                        }
                                    });
                                    DEL_ITEMS.stream().forEach(itm->{
                                        itm.delete();
                                    });
                                    Platform.runLater(()->{
                                        dialogx.close();
                                        FXDialog.showMessageDialog(stackPane, "Successful", "Bill Statement has been updated!", FXDialog.SUCCESS);
                                    });
                                }else{
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Failed", "Failed to communicate with database server!", FXDialog.DANGER);
                                    });
                                }
                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }finally{
                                Platform.runLater(()->{
                                    save.setDisable(false);
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
    void editPatientDetails(ActionEvent event) {
        try{
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35,25,25,25));

            FXTextEntry nameF = new FXTextEntry("Name");
            FXTextEntry ageF = new FXTextEntry("Age");
            FXTextEntry agesF = new FXTextEntry("Age String");
            FXTextEntry genF = new FXTextEntry("Gender");
            
            StringProperty nameV = new SimpleStringProperty(record.getPatientname());
            IntegerProperty ageV = new SimpleIntegerProperty(record.getAge());
            StringProperty agesV = new SimpleStringProperty(record.getAgestring());
            StringProperty genV = new SimpleStringProperty(record.getGender());
            
            nameF.getTextfieldComponent().textProperty().bindBidirectional(nameV);
            ageF.getTextfieldComponent().textProperty().bindBidirectional(ageV,new NumberConverter());
            agesF.getTextfieldComponent().textProperty().bindBidirectional(agesV);
            genF.getTextfieldComponent().textProperty().bindBidirectional(genV);
            
            FXField.addRequiredValidator(nameF.getTextfieldComponent());
            FXField.addRequiredValidator(ageF.getTextfieldComponent());
            FXField.addIntegerValidator(ageF.getTextfieldComponent(),1,100,1);
            FXField.addRequiredValidator(agesF.getTextfieldComponent());
            FXField.addRequiredValidator(genF.getTextfieldComponent());
            
            FXField.addFocusValidationListener(nameF.getTextfieldComponent(),ageF.getTextfieldComponent(),agesF.getTextfieldComponent(),genF.getTextfieldComponent());
            
            JFXButton save = new JFXButton("Save");
            save.getStyleClass().add("btn-success");
            
            content.getChildren().addAll(nameF,ageF,agesF,genF);
            //new Label("Do you want to save changes?");
            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Save Bill Statement", content, FXDialog.PRIMARY, save);
            save.setOnAction(editEvt->{
                if(nameF.getTextfieldComponent().validate() && ageF.getTextfieldComponent().validate() && agesF.getTextfieldComponent().validate() && genF.getTextfieldComponent().validate()){
                    record.setPatientname(nameV.get());
                    record.setAge(ageV.get());
                    record.setAgestring(agesV.get());
                    record.setGender(genV.get());
                    titleLbl.setText(record.getPatientname() +" - "+record.getGender()+" "+record.getAge()+" "+record.getAgestring());
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
        try{
            postAction();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
        
    }

    @FXML
    void loadBenefitsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(benefitsTab);
    }

    @FXML
    void loadBillingInfoMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(billingTab);
    }

    @FXML
    void loadHospitalMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(hospitalTab);
    }

    @FXML
    void loadPaymentsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(paymentsTab);
    }

    @FXML
    void loadPharmacyMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(pharmacyTab);
    }

    @FXML
    void loadProfessionalMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(professionalTab);
    }

    @FXML
    void loadRoomsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(roomTab);
    }

    @FXML
    void loadSupplyMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(supplyTab);
    }

    @FXML
    void loadValidationMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(validationTab);
        loadValidationList(null);
    }

    @FXML
    void printBillingStatement(ActionEvent event) {

    }

    @FXML
    void saveToExcel(ActionEvent event) {

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    @Override
    public void postAction() {
        try{
            if(UI_CONTROLLER instanceof BillingUXController){
                UI_CONTROLLER.reloadReferences(0);
            }
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setUIController(UIController controller) {
        UI_CONTROLLER = controller;
    }

    @Override
    public void setFormObject(BillStatement obj) {
        this.record = obj;
    }

    @Override
    public BillStatement getFormObject() {
        return this.record;
    }
    
    
    @Override
    public boolean isFieldInputsValid() {
        return true;
    }
    
    @Override
    public void loadBindings() {
        try{
            t1billnumF.textProperty().bindBidirectional(record.billnumberProperty());
            t1hospitalizationF.textProperty().bindBidirectional(record.hospitalizationplanProperty());
            t1createdbyF.textProperty().bindBidirectional(record.createdbyProperty());
            t1finaldiagF.textProperty().bindBidirectional(record.finaldiagnosisProperty());
            t1otherdiagF.textProperty().bindBidirectional(record.otherdiagnosisProperty());
            t1firstcaserateF.textProperty().bindBidirectional(record.firstcaserateProperty());
            t1secondcaserateF.textProperty().bindBidirectional(record.secondcaserateProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
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
            
            loadBillInfoTabCustomization();
            loadRoomTabCustomizations();
            loadPharmacyTabCustomizations();
            loadSupplyTabCustomizations();
            loadHospitalTabCustomizations();
            loadProfessionalTabCustomizations();
            loadPaymentTabCustomization();
            loadBenefitsTabCustomization();
            loadValidationTabCustomization();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            //Load Bill Items
            List<BillStatementItem> billitems = SQLTable.list(BillStatementItem.class, BillStatementItem.BILLSTATEMENT_ID+"="+record.getId());
            billitems.stream().forEach(itm->{
                switch (itm.getFacility()) {
                    case "Room":
                        ROOM_ITEMS.add(itm);
                        break;
                    case "Pharmacy":
                        if(itm.getItemtype().equals("Pharmacy")){
                            PHARMACY_ITEMS.add(itm);
                        } else if(itm.getItemtype().equals("Supply")){
                            SUPPLY_ITEMS.add(itm);
                        }   break;
                    case "Professional":
                        PROFESSIONAL_ITEMS.add(itm);
                        break;
                    default:
                        HOSPITAL_ITEMS.add(itm);
                        break;
                }
            });
            
            PAYMENTS = SQLTable.list(Payment.class,Payment.BILLSTATEMENT_ID+"="+record.getId());
            BENEFITS = SQLTable.list(PatientBenefit.class,PatientBenefit.BILLSTATEMENT_ID+"="+record.getId());
            
            Platform.runLater(()->{
                FXTable.setList(roomsTbl, ROOM_ITEMS);
                FXTable.setList(pharmacyTbl, PHARMACY_ITEMS);
                FXTable.setList(supplyTbl, SUPPLY_ITEMS);
                FXTable.setList(hospitalTbl, HOSPITAL_ITEMS);
                FXTable.setList(professionalTbl, PROFESSIONAL_ITEMS);
                FXTable.setList(paymentsTbl, PAYMENTS);
                FXTable.setList(benefitsTbl, BENEFITS);
                loadBillStatementItemAmounts();
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            Thread.sleep(1000);
            Platform.runLater(()->{
                loadRoomTableFilters();
                loadPharmacyTableFilters();
                loadSupplyTableFilters();
                loadHospitalTableFilters();
                loadProfessionalTableFilters();
                loadBenefitsTableFilters();
                loadPaymentTableFilters();
                loadValidationTableFilters();
            });
            t1billtimeF.setText(DateTimeKit.toProperTimestamp(record.getBilltime()));
            if(record.getAdmissiontime() != null){
                t1admssiontimeF.setText(DateTimeKit.toProperTimestamp(record.getAdmissiontime()));
            }
            if(record.getDischargedtime() != null){
                t1dischargedtimeF.setText(DateTimeKit.toProperTimestamp(record.getDischargedtime()));
            }
            titleLbl.setText(record.getPatientname() +" - "+record.getGender()+" "+record.getAge()+" "+record.getAgestring());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void reloadReferences(int val) {
        try{
            if(val == 0){
                if(mainTabPane.getSelectionModel().getSelectedItem() == billingTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == roomTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == pharmacyTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == supplyTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == hospitalTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == professionalTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == benefitsTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == paymentsTab){
                    
                }else if(mainTabPane.getSelectionModel().getSelectedItem() == validationTab){
                    
                }
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

     @Override
    public void setMainStack(StackPane stackpane) {
        stackPane = stackpane;
    }

    @Override
    public StackPane getMainStack() {
        return stackPane;
    }

    @Override
    public void setMaskerPane(MaskerPane masker) {
        maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }
    
    public static FXDialogTask showDialog(BillStatement data,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                dialog.setOverlayClose(false);
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/BillingStatementForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog,data, ui_controller, 1200, 620);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    maskerPane = masker;                    
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(ERConsultationFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
    private void loadBillInfoTabCustomization(){
        try{
            t1table.setEditable(false);
            t1table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(t1table);
            FXTable.addColumn(t1table, "Particulars", BillCategorySummary::categoryProperty, false);
            FXTable.addCurrencyColumn(t1table, "Actual Charges", BillCategorySummary::actualchargeProperty, false,95,95,95);
            FXTable.addCurrencyColumn(t1table, "Vat Excempt", BillCategorySummary::vatexcemptProperty, false,80,80,80);
            FXTable.addCurrencyColumn(t1table, "SCPWD", BillCategorySummary::scpwddiscountProperty, false,70,70,70);
            FXTable.addCurrencyColumn(t1table, "EMP/OT", BillCategorySummary::empotdiscountProperty, false,70,70,70);
            FXTable.addCurrencyColumn(t1table, "Other Benefits", BillCategorySummary::benefitdiscountProperty, false,80,80,80);
            FXTable.addCurrencyColumn(t1table, "FCR Amt", BillCategorySummary::firstcaserateProperty, false,80,80,80);
            FXTable.addCurrencyColumn(t1table, "SCR Amt", BillCategorySummary::secondcaserateProperty, false,80,80,80);
            /*
            TableColumn actCol = FXTable.addColumn(t1table, " ", BillCategorySummary::categoryProperty, false,40,40,40);
            actCol.setCellFactory(column -> {
                return new TableCell<BillCategorySummary, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillCategorySummary row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(40, 40);
                                    container.setMaxSize(40, 40);
                                    container.setPrefSize(40, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SIGN_OUT, "16px", evt -> {
                                        
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn);

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
            */
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRoomTabCustomizations(){
        try{
            roomsTbl.setEditable(false);
            roomsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(roomsTbl);
            FXTable.addColumn(roomsTbl, "Charge #", BillStatementItem::chargenumberProperty, false,120,120,120);
            FXTable.addColumn(roomsTbl, "Particulars", BillStatementItem::descriptionProperty, false,225,225,225);
            FXTable.addColumn(roomsTbl, "Qty", BillStatementItem::quantityProperty, false,65,65,65);
            FXTable.addCurrencyColumn(roomsTbl, "Price", BillStatementItem::priceProperty, false,80,80,80);
            FXTable.addCurrencyColumn(roomsTbl, "Amount", BillStatementItem::amountProperty, false,100,100,100);
            FXTable.addCurrencyColumn(roomsTbl, "Hospital Share", BillStatementItem::hospitalshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "PF Share", BillStatementItem::pfshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "PNF Amount", BillStatementItem::pnfamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "FCR Amount", BillStatementItem::firstcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "SCR Amount", BillStatementItem::secondcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "Benefit Amt", BillStatementItem::benefitamountProperty, false,120,120,120);
            FXTable.addColumn(roomsTbl, "Benefit Src", BillStatementItem::benefitsourceProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "VAT Sales", BillStatementItem::vatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "Non-VAT Sales", BillStatementItem::nonvatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "Zero Rated Sales", BillStatementItem::zeroratedsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "Input-VAT", BillStatementItem::inputvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "VAT Exempt", BillStatementItem::lessvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "SC Discount", BillStatementItem::scdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "PWD Discount", BillStatementItem::pwddiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "EMP Discount", BillStatementItem::empdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(roomsTbl, "NET Sales", BillStatementItem::netsalesProperty, false,120,120,120);
            TableColumn actCol = FXTable.addColumn(roomsTbl, " ", BillStatementItem::descriptionProperty, false,72,72,72);
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatementItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatementItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(15);
                                            content.setPadding(new Insets(25, 25, 25, 25));            

                                            FXTextEntry hsF = new FXTextEntry("Hospital Share");
                                            FXTextEntry pnfF = new FXTextEntry("PNF Amount");
                                            FXTextEntry pfF = new FXTextEntry("Professional Fee Share");
                                            FXTextEntry fcrF = new FXTextEntry("First Case Rate");
                                            FXTextEntry scrF = new FXTextEntry("Second Case Rate");
                                            
                                            DoubleProperty hsV = new SimpleDoubleProperty(row_data.getHospitalshare());
                                            DoubleProperty pnfV = new SimpleDoubleProperty(row_data.getPnfamount());
                                            DoubleProperty pfV = new SimpleDoubleProperty(row_data.getPfshare());
                                            DoubleProperty fcrV = new SimpleDoubleProperty(row_data.getFirstcaserateamount());
                                            DoubleProperty scrV = new SimpleDoubleProperty(row_data.getSecondcaserateamount());

                                            hsF.getTextfieldComponent().textProperty().bindBidirectional(hsV, new NumberConverter());
                                            pnfF.getTextfieldComponent().textProperty().bindBidirectional(pnfV, new NumberConverter());
                                            pfF.getTextfieldComponent().textProperty().bindBidirectional(pfV, new NumberConverter());
                                            fcrF.getTextfieldComponent().textProperty().bindBidirectional(fcrV, new NumberConverter());
                                            scrF.getTextfieldComponent().textProperty().bindBidirectional(scrV, new NumberConverter());
                                            
                                            FXField.addDoubleValidator(hsF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pnfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(fcrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(scrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            
                                            FXField.addFocusValidationListener(hsF.getTextfieldComponent(),pnfF.getTextfieldComponent(),pfF.getTextfieldComponent(),fcrF.getTextfieldComponent(),scrF.getTextfieldComponent());
                                            
                                            content.getChildren().addAll(hsF,pnfF,pfF,fcrF,scrF);

                                            JFXButton filter = new JFXButton("Update");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Update Bill Item", content, FXDialog.PRIMARY, filter);
                                            filter.setOnAction(deleteEvt -> {
                                                if(hsF.getTextfieldComponent().validate() && pnfF.getTextfieldComponent().validate() && pfF.getTextfieldComponent().validate() && fcrF.getTextfieldComponent().validate() && scrF.getTextfieldComponent().validate()){
                                                    row_data.setHospitalshare(hsV.get());
                                                    row_data.setPnfamount(pnfV.get());
                                                    row_data.setPfshare(pfV.get());
                                                    row_data.setFirstcaserateamount(fcrV.get());
                                                    row_data.setSecondcaserateamount(scrV.get());
                                                    loadBillStatementItemAmounts();
                                                    dialogx.close();
                                                }                                                
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Delete "+row_data.getDescription()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            ROOM_ITEMS.remove(row_data);
                                            DEL_ITEMS.add(row_data);
                                            FXTable.setList(roomsTbl, ROOM_ITEMS);
                                            //ReloadValues
                                            loadBillStatementItemAmounts();
                                            dl.close();
                                        });
                                    });
                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn,deleteBtn);

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
    
    private void loadPharmacyTabCustomizations(){
        try{
            pharmacyTbl.setEditable(false);
            pharmacyTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(pharmacyTbl);
            FXTable.addColumn(pharmacyTbl, "Charge #", BillStatementItem::chargenumberProperty, false,120,120,120);
            FXTable.addColumn(pharmacyTbl, "Particulars", BillStatementItem::descriptionProperty, false,225,225,225);
            FXTable.addColumn(pharmacyTbl, "Qty", BillStatementItem::quantityProperty, false,65,65,65);
            FXTable.addCurrencyColumn(pharmacyTbl, "Price", BillStatementItem::priceProperty, false,80,80,80);
            FXTable.addCurrencyColumn(pharmacyTbl, "Amount", BillStatementItem::amountProperty, false,100,100,100);
            FXTable.addCurrencyColumn(pharmacyTbl, "Hospital Share", BillStatementItem::hospitalshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "PF Share", BillStatementItem::pfshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "PNF Amount", BillStatementItem::pnfamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "FCR Amount", BillStatementItem::firstcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "SCR Amount", BillStatementItem::secondcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "Benefit Amt", BillStatementItem::benefitamountProperty, false,120,120,120);
            FXTable.addColumn(pharmacyTbl, "Benefit Src", BillStatementItem::benefitsourceProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "VAT Sales", BillStatementItem::vatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "Non-VAT Sales", BillStatementItem::nonvatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "Zero Rated Sales", BillStatementItem::zeroratedsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "Input-VAT", BillStatementItem::inputvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "VAT Exempt", BillStatementItem::lessvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "SC Discount", BillStatementItem::scdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "PWD Discount", BillStatementItem::pwddiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "EMP Discount", BillStatementItem::empdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(pharmacyTbl, "NET Sales", BillStatementItem::netsalesProperty, false,120,120,120);
            TableColumn actCol = FXTable.addColumn(pharmacyTbl, " ", BillStatementItem::descriptionProperty, false,72,72,72);
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatementItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatementItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(15);
                                            content.setPadding(new Insets(25, 25, 25, 25));            

                                            FXTextEntry hsF = new FXTextEntry("Hospital Share");
                                            FXTextEntry pnfF = new FXTextEntry("PNF Amount");
                                            FXTextEntry pfF = new FXTextEntry("Professional Fee Share");
                                            FXTextEntry fcrF = new FXTextEntry("First Case Rate");
                                            FXTextEntry scrF = new FXTextEntry("Second Case Rate");
                                            
                                            DoubleProperty hsV = new SimpleDoubleProperty(row_data.getHospitalshare());
                                            DoubleProperty pnfV = new SimpleDoubleProperty(row_data.getPnfamount());
                                            DoubleProperty pfV = new SimpleDoubleProperty(row_data.getPfshare());
                                            DoubleProperty fcrV = new SimpleDoubleProperty(row_data.getFirstcaserateamount());
                                            DoubleProperty scrV = new SimpleDoubleProperty(row_data.getSecondcaserateamount());

                                            hsF.getTextfieldComponent().textProperty().bindBidirectional(hsV, new NumberConverter());
                                            pnfF.getTextfieldComponent().textProperty().bindBidirectional(pnfV, new NumberConverter());
                                            pfF.getTextfieldComponent().textProperty().bindBidirectional(pfV, new NumberConverter());
                                            fcrF.getTextfieldComponent().textProperty().bindBidirectional(fcrV, new NumberConverter());
                                            scrF.getTextfieldComponent().textProperty().bindBidirectional(scrV, new NumberConverter());
                                            
                                            FXField.addDoubleValidator(hsF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pnfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(fcrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(scrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            
                                            FXField.addFocusValidationListener(hsF.getTextfieldComponent(),pnfF.getTextfieldComponent(),pfF.getTextfieldComponent(),fcrF.getTextfieldComponent(),scrF.getTextfieldComponent());
                                            
                                            content.getChildren().addAll(hsF,pnfF,pfF,fcrF,scrF);

                                            JFXButton filter = new JFXButton("Update");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Update Bill Item", content, FXDialog.PRIMARY, filter);
                                            filter.setOnAction(deleteEvt -> {
                                                if(hsF.getTextfieldComponent().validate() && pnfF.getTextfieldComponent().validate() && pfF.getTextfieldComponent().validate() && fcrF.getTextfieldComponent().validate() && scrF.getTextfieldComponent().validate()){
                                                    row_data.setHospitalshare(hsV.get());
                                                    row_data.setPnfamount(pnfV.get());
                                                    row_data.setPfshare(pfV.get());
                                                    row_data.setFirstcaserateamount(fcrV.get());
                                                    row_data.setSecondcaserateamount(scrV.get());
                                                    loadBillStatementItemAmounts();
                                                    dialogx.close();
                                                }                                                
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Delete "+row_data.getDescription()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            PHARMACY_ITEMS.remove(row_data);
                                            DEL_ITEMS.add(row_data);
                                            FXTable.setList(pharmacyTbl, PHARMACY_ITEMS);
                                            //ReloadValues
                                            loadBillStatementItemAmounts();
                                            dl.close();
                                        });
                                    });
                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn,deleteBtn);

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
    
    private void loadSupplyTabCustomizations(){
        try{
            supplyTbl.setEditable(false);
            supplyTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(supplyTbl);
            FXTable.addColumn(supplyTbl, "Charge #", BillStatementItem::chargenumberProperty, false,120,120,120);
            FXTable.addColumn(supplyTbl, "Particulars", BillStatementItem::descriptionProperty, false,225,225,225);
            FXTable.addColumn(supplyTbl, "Qty", BillStatementItem::quantityProperty, false,65,65,65);
            FXTable.addCurrencyColumn(supplyTbl, "Price", BillStatementItem::priceProperty, false,80,80,80);
            FXTable.addCurrencyColumn(supplyTbl, "Amount", BillStatementItem::amountProperty, false,100,100,100);
            FXTable.addCurrencyColumn(supplyTbl, "Hospital Share", BillStatementItem::hospitalshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "PF Share", BillStatementItem::pfshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "PNF Amount", BillStatementItem::pnfamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "FCR Amount", BillStatementItem::firstcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "SCR Amount", BillStatementItem::secondcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "Benefit Amt", BillStatementItem::benefitamountProperty, false,120,120,120);
            FXTable.addColumn(supplyTbl, "Benefit Src", BillStatementItem::benefitsourceProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "VAT Sales", BillStatementItem::vatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "Non-VAT Sales", BillStatementItem::nonvatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "Zero Rated Sales", BillStatementItem::zeroratedsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "Input-VAT", BillStatementItem::inputvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "VAT Exempt", BillStatementItem::lessvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "SC Discount", BillStatementItem::scdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "PWD Discount", BillStatementItem::pwddiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "EMP Discount", BillStatementItem::empdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(supplyTbl, "NET Sales", BillStatementItem::netsalesProperty, false,120,120,120);
            TableColumn actCol = FXTable.addColumn(supplyTbl, " ", BillStatementItem::descriptionProperty, false,72,72,72);
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatementItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatementItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(15);
                                            content.setPadding(new Insets(25, 25, 25, 25));            

                                            FXTextEntry hsF = new FXTextEntry("Hospital Share");
                                            FXTextEntry pnfF = new FXTextEntry("PNF Amount");
                                            FXTextEntry pfF = new FXTextEntry("Professional Fee Share");
                                            FXTextEntry fcrF = new FXTextEntry("First Case Rate");
                                            FXTextEntry scrF = new FXTextEntry("Second Case Rate");
                                            
                                            DoubleProperty hsV = new SimpleDoubleProperty(row_data.getHospitalshare());
                                            DoubleProperty pnfV = new SimpleDoubleProperty(row_data.getPnfamount());
                                            DoubleProperty pfV = new SimpleDoubleProperty(row_data.getPfshare());
                                            DoubleProperty fcrV = new SimpleDoubleProperty(row_data.getFirstcaserateamount());
                                            DoubleProperty scrV = new SimpleDoubleProperty(row_data.getSecondcaserateamount());

                                            hsF.getTextfieldComponent().textProperty().bindBidirectional(hsV, new NumberConverter());
                                            pnfF.getTextfieldComponent().textProperty().bindBidirectional(pnfV, new NumberConverter());
                                            pfF.getTextfieldComponent().textProperty().bindBidirectional(pfV, new NumberConverter());
                                            fcrF.getTextfieldComponent().textProperty().bindBidirectional(fcrV, new NumberConverter());
                                            scrF.getTextfieldComponent().textProperty().bindBidirectional(scrV, new NumberConverter());
                                            
                                            FXField.addDoubleValidator(hsF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pnfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(fcrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(scrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            
                                            FXField.addFocusValidationListener(hsF.getTextfieldComponent(),pnfF.getTextfieldComponent(),pfF.getTextfieldComponent(),fcrF.getTextfieldComponent(),scrF.getTextfieldComponent());
                                            
                                            content.getChildren().addAll(hsF,pnfF,pfF,fcrF,scrF);

                                            JFXButton filter = new JFXButton("Update");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Update Bill Item", content, FXDialog.PRIMARY, filter);
                                            filter.setOnAction(deleteEvt -> {
                                                if(hsF.getTextfieldComponent().validate() && pnfF.getTextfieldComponent().validate() && pfF.getTextfieldComponent().validate() && fcrF.getTextfieldComponent().validate() && scrF.getTextfieldComponent().validate()){
                                                    row_data.setHospitalshare(hsV.get());
                                                    row_data.setPnfamount(pnfV.get());
                                                    row_data.setPfshare(pfV.get());
                                                    row_data.setFirstcaserateamount(fcrV.get());
                                                    row_data.setSecondcaserateamount(scrV.get());
                                                    loadBillStatementItemAmounts();
                                                    dialogx.close();
                                                }                                                
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Delete "+row_data.getDescription()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            SUPPLY_ITEMS.remove(row_data);
                                            DEL_ITEMS.add(row_data);
                                            FXTable.setList(supplyTbl, SUPPLY_ITEMS);
                                            //ReloadValues
                                            loadBillStatementItemAmounts();
                                            dl.close();
                                        });
                                    });
                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn,deleteBtn);

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
    
    private void loadHospitalTabCustomizations(){
        try{
            hospitalTbl.setEditable(false);
            hospitalTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(hospitalTbl);  
            FXTable.addColumn(hospitalTbl, "Charge #", BillStatementItem::chargenumberProperty, false,120,120,120);
            FXTable.addColumn(hospitalTbl, "Department", BillStatementItem::facilityProperty, false,150,150,150);
            FXTable.addColumn(hospitalTbl, "Particulars", BillStatementItem::descriptionProperty, false,225,225,225);
            FXTable.addColumn(hospitalTbl, "Qty", BillStatementItem::quantityProperty, false,65,65,65);
            FXTable.addCurrencyColumn(hospitalTbl, "Price", BillStatementItem::priceProperty, false,80,80,80);
            FXTable.addCurrencyColumn(hospitalTbl, "Amount", BillStatementItem::amountProperty, false,100,100,100);
            FXTable.addCurrencyColumn(hospitalTbl, "Hospital Share", BillStatementItem::hospitalshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "PF Share", BillStatementItem::pfshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "PNF Amount", BillStatementItem::pnfamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "FCR Amount", BillStatementItem::firstcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "SCR Amount", BillStatementItem::secondcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "Benefit Amt", BillStatementItem::benefitamountProperty, false,120,120,120);
            FXTable.addColumn(hospitalTbl, "Benefit Src", BillStatementItem::benefitsourceProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "VAT Sales", BillStatementItem::vatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "Non-VAT Sales", BillStatementItem::nonvatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "Zero Rated Sales", BillStatementItem::zeroratedsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "Input-VAT", BillStatementItem::inputvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "VAT Exempt", BillStatementItem::lessvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "SC Discount", BillStatementItem::scdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "PWD Discount", BillStatementItem::pwddiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "EMP Discount", BillStatementItem::empdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(hospitalTbl, "NET Sales", BillStatementItem::netsalesProperty, false,120,120,120);
            TableColumn actCol = FXTable.addColumn(hospitalTbl, " ", BillStatementItem::descriptionProperty, false,72,72,72);
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatementItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatementItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(15);
                                            content.setPadding(new Insets(25, 25, 25, 25));            

                                            FXTextEntry hsF = new FXTextEntry("Hospital Share");
                                            FXTextEntry pnfF = new FXTextEntry("PNF Amount");
                                            FXTextEntry pfF = new FXTextEntry("Professional Fee Share");
                                            FXTextEntry fcrF = new FXTextEntry("First Case Rate");
                                            FXTextEntry scrF = new FXTextEntry("Second Case Rate");
                                            
                                            DoubleProperty hsV = new SimpleDoubleProperty(row_data.getHospitalshare());
                                            DoubleProperty pnfV = new SimpleDoubleProperty(row_data.getPnfamount());
                                            DoubleProperty pfV = new SimpleDoubleProperty(row_data.getPfshare());
                                            DoubleProperty fcrV = new SimpleDoubleProperty(row_data.getFirstcaserateamount());
                                            DoubleProperty scrV = new SimpleDoubleProperty(row_data.getSecondcaserateamount());

                                            hsF.getTextfieldComponent().textProperty().bindBidirectional(hsV, new NumberConverter());
                                            pnfF.getTextfieldComponent().textProperty().bindBidirectional(pnfV, new NumberConverter());
                                            pfF.getTextfieldComponent().textProperty().bindBidirectional(pfV, new NumberConverter());
                                            fcrF.getTextfieldComponent().textProperty().bindBidirectional(fcrV, new NumberConverter());
                                            scrF.getTextfieldComponent().textProperty().bindBidirectional(scrV, new NumberConverter());
                                            
                                            FXField.addDoubleValidator(hsF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pnfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(fcrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(scrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            
                                            FXField.addFocusValidationListener(hsF.getTextfieldComponent(),pnfF.getTextfieldComponent(),pfF.getTextfieldComponent(),fcrF.getTextfieldComponent(),scrF.getTextfieldComponent());
                                            
                                            content.getChildren().addAll(hsF,pnfF,pfF,fcrF,scrF);

                                            JFXButton filter = new JFXButton("Update");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Update Bill Item", content, FXDialog.PRIMARY, filter);
                                            filter.setOnAction(deleteEvt -> {
                                                if(hsF.getTextfieldComponent().validate() && pnfF.getTextfieldComponent().validate() && pfF.getTextfieldComponent().validate() && fcrF.getTextfieldComponent().validate() && scrF.getTextfieldComponent().validate()){
                                                    row_data.setHospitalshare(hsV.get());
                                                    row_data.setPnfamount(pnfV.get());
                                                    row_data.setPfshare(pfV.get());
                                                    row_data.setFirstcaserateamount(fcrV.get());
                                                    row_data.setSecondcaserateamount(scrV.get());
                                                    loadBillStatementItemAmounts();
                                                    dialogx.close();
                                                }                                                
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Delete "+row_data.getDescription()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            HOSPITAL_ITEMS.remove(row_data);
                                            DEL_ITEMS.add(row_data);
                                            FXTable.setList(hospitalTbl, HOSPITAL_ITEMS);
                                            //ReloadValues
                                            loadBillStatementItemAmounts();
                                            dl.close();
                                        });
                                    });
                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn,deleteBtn);

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
    
    private void loadProfessionalTabCustomizations(){
        try{
            professionalTbl.setEditable(false);
            professionalTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(professionalTbl);            
            FXTable.addColumn(professionalTbl, "Charge #", BillStatementItem::chargenumberProperty, false,120,120,120);
            FXTable.addColumn(professionalTbl, "Particulars", BillStatementItem::descriptionProperty, false,225,225,225);
            FXTable.addColumn(professionalTbl, "Qty", BillStatementItem::quantityProperty, false,65,65,65);
            FXTable.addCurrencyColumn(professionalTbl, "Price", BillStatementItem::priceProperty, false,80,80,80);
            FXTable.addCurrencyColumn(professionalTbl, "Amount", BillStatementItem::amountProperty, false,100,100,100);
            FXTable.addCurrencyColumn(professionalTbl, "Hospital Share", BillStatementItem::hospitalshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "PF Share", BillStatementItem::pfshareProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "PNF Amount", BillStatementItem::pnfamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "FCR Amount", BillStatementItem::firstcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "SCR Amount", BillStatementItem::secondcaserateamountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "Benefit Amt", BillStatementItem::benefitamountProperty, false,120,120,120);
            FXTable.addColumn(professionalTbl, "Benefit Src", BillStatementItem::benefitsourceProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "VAT Sales", BillStatementItem::vatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "Non-VAT Sales", BillStatementItem::nonvatsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "Zero Rated Sales", BillStatementItem::zeroratedsalesProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "Input-VAT", BillStatementItem::inputvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "VAT Exempt", BillStatementItem::lessvatProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "SC Discount", BillStatementItem::scdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "PWD Discount", BillStatementItem::pwddiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "EMP Discount", BillStatementItem::empdiscountProperty, false,120,120,120);
            FXTable.addCurrencyColumn(professionalTbl, "NET Sales", BillStatementItem::netsalesProperty, false,120,120,120);
            TableColumn actCol = FXTable.addColumn(professionalTbl, " ", BillStatementItem::descriptionProperty, false,72,72,72);
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatementItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatementItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(15);
                                            content.setPadding(new Insets(25, 25, 25, 25));            

                                            FXTextEntry hsF = new FXTextEntry("Hospital Share");
                                            FXTextEntry pnfF = new FXTextEntry("PNF Amount");
                                            FXTextEntry pfF = new FXTextEntry("Professional Fee Share");
                                            FXTextEntry fcrF = new FXTextEntry("First Case Rate");
                                            FXTextEntry scrF = new FXTextEntry("Second Case Rate");
                                            
                                            DoubleProperty hsV = new SimpleDoubleProperty(row_data.getHospitalshare());
                                            DoubleProperty pnfV = new SimpleDoubleProperty(row_data.getPnfamount());
                                            DoubleProperty pfV = new SimpleDoubleProperty(row_data.getPfshare());
                                            DoubleProperty fcrV = new SimpleDoubleProperty(row_data.getFirstcaserateamount());
                                            DoubleProperty scrV = new SimpleDoubleProperty(row_data.getSecondcaserateamount());

                                            hsF.getTextfieldComponent().textProperty().bindBidirectional(hsV, new NumberConverter());
                                            pnfF.getTextfieldComponent().textProperty().bindBidirectional(pnfV, new NumberConverter());
                                            pfF.getTextfieldComponent().textProperty().bindBidirectional(pfV, new NumberConverter());
                                            fcrF.getTextfieldComponent().textProperty().bindBidirectional(fcrV, new NumberConverter());
                                            scrF.getTextfieldComponent().textProperty().bindBidirectional(scrV, new NumberConverter());
                                            
                                            FXField.addDoubleValidator(hsF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pnfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(pfF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(fcrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            FXField.addDoubleValidator(scrF.getTextfieldComponent(), 0, 999999.00, 0);
                                            
                                            FXField.addFocusValidationListener(hsF.getTextfieldComponent(),pnfF.getTextfieldComponent(),pfF.getTextfieldComponent(),fcrF.getTextfieldComponent(),scrF.getTextfieldComponent());
                                            
                                            content.getChildren().addAll(hsF,pnfF,pfF,fcrF,scrF);

                                            JFXButton filter = new JFXButton("Update");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Update Bill Item", content, FXDialog.PRIMARY, filter);
                                            filter.setOnAction(deleteEvt -> {
                                                if(hsF.getTextfieldComponent().validate() && pnfF.getTextfieldComponent().validate() && pfF.getTextfieldComponent().validate() && fcrF.getTextfieldComponent().validate() && scrF.getTextfieldComponent().validate()){
                                                    row_data.setHospitalshare(hsV.get());
                                                    row_data.setPnfamount(pnfV.get());
                                                    row_data.setPfshare(pfV.get());
                                                    row_data.setFirstcaserateamount(fcrV.get());
                                                    row_data.setSecondcaserateamount(scrV.get());
                                                    loadBillStatementItemAmounts();
                                                    dialogx.close();
                                                }                                                
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Delete!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Delete "+row_data.getDescription()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            PROFESSIONAL_ITEMS.remove(row_data);
                                            DEL_ITEMS.add(row_data);
                                            FXTable.setList(professionalTbl, PROFESSIONAL_ITEMS);
                                            //ReloadValues
                                            loadBillStatementItemAmounts();
                                            dl.close();
                                        });
                                    });
                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn,deleteBtn);

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
    
    private void loadBenefitsTabCustomization(){
        try{
            benefitsTbl.setEditable(true);
            benefitsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(benefitsTbl, "SOA no.", PatientBenefit::billnumberProperty, false, 135, 135, 135);
            FXTable.addColumn(benefitsTbl, "Patient", PatientBenefit::patientProperty, false);
            FXTable.addColumn(benefitsTbl, "Source", PatientBenefit::benefitsourceProperty, false);
            FXTable.addColumn(benefitsTbl, "Code", PatientBenefit::benefitcodeProperty, false);
            FXTable.addCurrencyColumn(benefitsTbl, "Amount", PatientBenefit::amountProperty, false,100,100,100);
            FXTable.addColumn(benefitsTbl, "Created By", PatientBenefit::createdbyProperty, false);
            TableColumn createCol = FXTable.addColumn(benefitsTbl, "Created", PatientBenefit::created_atProperty, false, 135, 135, 135);
            TableColumn actCol = FXTable.addColumn(benefitsTbl, "Actions", PatientBenefit::patientProperty, false, 76, 76, 76);
            
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
                                        FXDialog.showConfirmDialog(stackPane,"Benefit Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete Benefit");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(stackPane, "Confirm", new Label("Do you want to delete this benefit?"), FXDialog.DANGER, b);
                                        b.setOnAction(remEvt->{
                                            BENEFITS.remove(row_data);
                                            DEL_BENEFITS.add(row_data);
                                            FXTable.setList(benefitsTbl, BENEFITS);
                                            loadBillStatementItemAmounts();
                                            d.close();
                                        });
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
    
    private void loadPaymentTabCustomization(){
        try{
            paymentsTbl.setEditable(true);
            paymentsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);            
            FXTable.disableColumnReorder(paymentsTbl);
            TableColumn timeCol = FXTable.addColumn(paymentsTbl, "Timestamp", Payment::paymenttimeProperty, false, 135, 135, 135);
            FXTable.addColumn(paymentsTbl, "Patient", Payment::patientProperty, false);
            FXTable.addColumn(paymentsTbl, "OR #", Payment::ornumberProperty, false);
            FXTable.addColumn(paymentsTbl, "Invoice #", Payment::invoicenumberProperty, false);
            FXTable.addColumn(paymentsTbl, "Cashier", Payment::cashierProperty, false);
            FXTable.addColumn(paymentsTbl, "Paid By", Payment::paidbyProperty, false);
            FXTable.addCurrencyColumn(paymentsTbl, "Amount", Payment::amountProperty, false,135,135,135);
            TableColumn actCol = FXTable.addColumn(paymentsTbl, "Actions", Payment::patientProperty, false, 76, 76, 76);
            
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
                                        FXDialog.showConfirmDialog(stackPane,"Payment Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Remove this Payment");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(stackPane, "Confirm", new Label("Do you want to remove this payment?"), FXDialog.DANGER, b);
                                        b.setOnAction(remEvt->{
                                            PAYMENTS.remove(row_data);
                                            DEL_PAYMENTS.add(row_data);
                                            FXTable.setList(paymentsTbl, PAYMENTS);
                                            loadBillStatementItemAmounts();
                                            d.close();
                                        });
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Remove"));
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
    
    private void loadValidationTabCustomization(){
        try{
            validationTbl.setEditable(true);
            validationTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);            
            FXTable.disableColumnReorder(validationTbl);
            TableColumn timeCol = FXTable.addColumn(validationTbl, "Timestamp", HospitalCharge::chargetimeProperty, false, 135, 135, 135);
            FXTable.addColumn(validationTbl, "Charge #", HospitalCharge::chargenumberProperty, false);
            FXTable.addColumn(validationTbl, "Charge Type", HospitalCharge::chargetypeProperty, false);
            FXTable.addColumn(validationTbl, "Department", HospitalCharge::chargefacilityProperty, false);
            FXTable.addColumn(validationTbl, "Encoder", HospitalCharge::userProperty, false);
            FXTable.addCurrencyColumn(validationTbl, "Net Amount", HospitalCharge::netsalesProperty, false,135,135,135);
            TableColumn selCol = FXTable.addColumn(validationTbl, "Billed", HospitalCharge::selectedProperty, false, 45, 45, 45);
            TableColumn actCol = FXTable.addColumn(validationTbl, " ", HospitalCharge::chargenumberProperty, false, 112, 112, 112);
            
            FXTable.setTimestampColumn(timeCol);
            
            selCol.setCellFactory(column -> {
                return new TableCell<HospitalCharge, Boolean>() {

                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);

                        if (empty) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalCharge row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    JFXCheckBox selectBox = new JFXCheckBox("");
                                    //selectBox.getStyleClass().add("btn-success");
                                    selectBox.setMinSize(32, 32);

                                    selectBox.selectedProperty().bindBidirectional(row_data.selectedProperty());
                                    selectBox.setStyle("-fx-alignment:CENTER;");

                                    selectBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                                        /*
                                        
                                        */
                                    });
                                    selectBox.setDisable(true);
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
                                    container.setMinSize(112, 40);
                                    container.setMaxSize(112, 40);
                                    container.setPrefSize(112, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(stackPane,"Charge Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton listBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.DATABASE, "14px", evt -> {
                                        
                                    });
                                    
                                    listBtn.setTooltip(new Tooltip("View Items"));
                                    listBtn.getStyleClass().add("btn-info");
                                    listBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    listBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton addBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PLUS, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Bill this Charge");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(stackPane, "Confirm", new Label("Do you want to bill this charge?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {                                            
                                            d.close();         
                                            FXTask task = new FXTask() {
                                                @Override
                                                protected void load() {                           
                                                    try{
                                                        if(row_data.getChargefacility().equals("Room")){
                                                            List<BillStatementItem> pItems = new ArrayList();
                                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+row_data.getId());
                                                            for(HospitalChargeItem bItems:bChargeItems){
                                                                BillStatementItem b = bItems.toBillStatementItem();
                                                                b.setBillstatement_id(record.getId());
                                                                b.setAddedby(Care.getUser().getName());
                                                                pItems.add(b);                                                
                                                            }

                                                            pItems.stream().forEach(itm->{
                                                                ROOM_ITEMS.add(itm);
                                                            });

                                                            Platform.runLater(()->{
                                                                FXTable.setList(roomsTbl, ROOM_ITEMS);                                            
                                                                roomscountLbl.setText("Items : "+ROOM_ITEMS.size());
                                                                List<Double> psales = ROOM_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                                                                double ptotal = psales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                                                                roomsdetailLbl.setText("Net Payables : "+NumberKit.toCurrency(ptotal));

                                                                loadBillStatementItemAmounts();
                                                            });
                                                        }else if(row_data.getChargefacility().equals("Pharmacy")){
                                                            List<BillStatementItem> pItems = new ArrayList();
                                                            List<BillStatementItem> sItems = new ArrayList();
                                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+row_data.getId());
                                                            for(HospitalChargeItem bItems:bChargeItems){
                                                                BillStatementItem b = bItems.toBillStatementItem();
                                                                b.setBillstatement_id(record.getId());
                                                                b.setAddedby(Care.getUser().getName());
                                                                if(b.getItemtype().equals("Pharmacy")){
                                                                    pItems.add(b);
                                                                }else if(b.getItemtype().equals("Supply")){
                                                                    sItems.add(b);
                                                                }

                                                            }

                                                            pItems.stream().forEach(itm->{
                                                                PHARMACY_ITEMS.add(itm);
                                                            });
                                                            sItems.stream().forEach(itm->{
                                                                SUPPLY_ITEMS.add(itm);
                                                            });

                                                            Platform.runLater(()->{
                                                                FXTable.setList(pharmacyTbl, PHARMACY_ITEMS);                                            
                                                                pharmacycountLbl.setText("Items : "+PHARMACY_ITEMS.size());
                                                                List<Double> psales = PHARMACY_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                                                                double ptotal = psales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                                                                pharmacydetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(ptotal));

                                                                FXTable.setList(supplyTbl, SUPPLY_ITEMS);        
                                                                supplycountLbl.setText("Items : "+SUPPLY_ITEMS.size());
                                                                List<Double> ssales = SUPPLY_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                                                                double stotal = ssales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                                                                supplydetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(stotal));

                                                                loadBillStatementItemAmounts();
                                                            });
                                                        }else if(row_data.getChargefacility().equals("Professional")){
                                                            List<BillStatementItem> pItems = new ArrayList();
                                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+row_data.getId());
                                                            for(HospitalChargeItem bItems:bChargeItems){
                                                                BillStatementItem b = bItems.toBillStatementItem();
                                                                b.setBillstatement_id(record.getId());
                                                                b.setAddedby(Care.getUser().getName());
                                                                pItems.add(b);
                                                            }

                                                            pItems.stream().forEach(itm->{
                                                                PROFESSIONAL_ITEMS.add(itm);
                                                            });

                                                            Platform.runLater(()->{
                                                                FXTable.setList(professionalTbl, PROFESSIONAL_ITEMS);                                            
                                                                professionalcountLbl.setText("Items : "+PROFESSIONAL_ITEMS.size());
                                                                List<Double> psales = PROFESSIONAL_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                                                                double ptotal = psales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                                                                professionaldetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(ptotal));

                                                                loadBillStatementItemAmounts();
                                                            });
                                                        }else{
                                                            List<BillStatementItem> pItems = new ArrayList();
                                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+row_data.getId());
                                                            for(HospitalChargeItem bItems:bChargeItems){
                                                                BillStatementItem b = bItems.toBillStatementItem();
                                                                b.setBillstatement_id(record.getId());
                                                                b.setAddedby(Care.getUser().getName());
                                                                pItems.add(b);                                                
                                                            }

                                                            pItems.stream().forEach(itm->{
                                                                HOSPITAL_ITEMS.add(itm);
                                                            });

                                                            Platform.runLater(()->{
                                                                FXTable.setList(hospitalTbl, HOSPITAL_ITEMS);                                            
                                                                hospitalcountLbl.setText("Items : "+HOSPITAL_ITEMS.size());
                                                                List<Double> psales = HOSPITAL_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                                                                double ptotal = psales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                                                                hospitaldetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(ptotal));

                                                                loadBillStatementItemAmounts();
                                                            });
                                                        }
                                                    }catch(Exception er){
                                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                    }finally{
                                                        loadValidationList(null);
                                                    }
                                                }
                                            };
                                            Care.process(task);
                                        });
                                        
                                    });
                                    
                                    addBtn.setTooltip(new Tooltip("Add to Billing"));
                                    addBtn.getStyleClass().add("btn-info");
                                    addBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    addBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    addBtn.setDisable(row_data.isSelected());
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(addBtn,viewBtn,listBtn);

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
    
    private void loadValidationList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(validationTbl, new ArrayList());
                            validationTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<HospitalCharge> records;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusYears(5), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
                        }else{
                            records = SQLTable.list(HospitalCharge.class,conditions);
                        }      
                        Platform.runLater(() -> {                            
                            List<BillStatementItem> items = new ArrayList();
                            items.addAll(ROOM_ITEMS);
                            items.addAll(PHARMACY_ITEMS);
                            items.addAll(SUPPLY_ITEMS);
                            items.addAll(HOSPITAL_ITEMS);
                            items.addAll(PROFESSIONAL_ITEMS);
                            
                            records.stream().forEach(charge->{
                                for(BillStatementItem item:items){
                                    if(charge.getChargenumber().equals(item.getChargenumber())){
                                        charge.setSelected(true);
                                        break;
                                    }
                                }
                            });
                            
                            FXTable.setList(validationTbl, records);
                            validationcountsLbl.setText("Charges : "+records.size());
                        });                        
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            validationTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRoomTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<HospitalCharge> charges = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<HospitalCharge> lsv = new ListSelectionView();            
                    lsv.setMinWidth(750);
                    lsv.setMaxWidth(750);
                    lsv.setPrefWidth(750);   
                    lsv.setSourceHeader(new Label("Unbilled Charges"));
                    lsv.setTargetHeader(new Label("Selected Unbilled Charges"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<HospitalCharge> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends HospitalCharge> c) -> {
                        List<HospitalCharge> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Room' AND "+HospitalCharge.CHARGETIME+">='"+datefrom+"' ORDER BY "+HospitalCharge.CHARGETIME);
                    
                    List<BillStatementItem> billitems = new ArrayList();
                    billitems.addAll(ROOM_ITEMS);
                    billitems.addAll(PHARMACY_ITEMS);
                    billitems.addAll(SUPPLY_ITEMS);
                    billitems.addAll(HOSPITAL_ITEMS);
                    billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                    for(BillStatementItem lsitem:billitems){
                        for(int i = 0;i < charges.size();i++){    
                            if(lsitem.getChargenumber().equals(charges.get(i).getChargenumber())){
                                charges.remove(charges.get(i));
                                break;
                            }
                        }
                    }
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(charges));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<HospitalCharge> chargesA = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Room' AND "+HospitalCharge.CHARGETIME+">='"+datefromA+"' ORDER BY "+HospitalCharge.CHARGETIME);

                                        List<BillStatementItem> billitems = new ArrayList();
                                        billitems.addAll(ROOM_ITEMS);
                                        billitems.addAll(PHARMACY_ITEMS);
                                        billitems.addAll(SUPPLY_ITEMS);
                                        billitems.addAll(HOSPITAL_ITEMS);
                                        billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                                        for(BillStatementItem item:billitems){
                                            for(HospitalCharge chargeA:chargesA){                                            
                                                if(chargeA.getChargenumber().equals(item.getChargenumber())){
                                                    chargesA.remove(chargeA);
                                                    break;
                                                }
                                            }
                                        }
                                                                                
                                        Platform.runLater(()->{
                                            List<HospitalCharge> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(HospitalCharge lsitem:listedItems){
                                                for(int i = 0;i < chargesA.size();i++){    
                                                    if(lsitem.getChargenumber().equals(chargesA.get(i).getChargenumber())){
                                                        chargesA.remove(chargesA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(chargesA);
                                        });
                                        
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                    
                    
                    TableView<HospitalChargeItem> chargeItemsTbl = new TableView();
                    chargeItemsTbl.setMinWidth(750);
                    chargeItemsTbl.setMaxWidth(750);
                    chargeItemsTbl.setPrefWidth(750);   
                    
                    chargeItemsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    FXTable.disableColumnReorder(chargeItemsTbl);
                    FXTable.addColumn(chargeItemsTbl, "Description", HospitalChargeItem::descriptionProperty, false,200,200,200);
                    FXTable.addColumn(chargeItemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,50,50,50);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Price", HospitalChargeItem::sellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Amount", HospitalChargeItem::totalsellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Sales", HospitalChargeItem::vatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "NonVAT Sales", HospitalChargeItem::nonvatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Tax", HospitalChargeItem::inputvatProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Excempt", HospitalChargeItem::lessvatProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "SC Disc", HospitalChargeItem::scdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "PWD Disc", HospitalChargeItem::pwddiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "EMP Disc", HospitalChargeItem::empdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "OT Disc", HospitalChargeItem::otdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Net Sales", HospitalChargeItem::netsalesProperty, false,80,80,80);
                    
                    ListSelect selAct = new ListSelect(lsv);
                    selAct.setSourceChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    selAct.setTargetChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    lsv.getActions().add(selAct);
                    
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    VBox.setMargin(chargeItemsTbl, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv, chargeItemsTbl);

                    JFXButton filter = new JFXButton("Add Charges");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Bill Room Charges", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        List<HospitalCharge> toBills = lsv.getTargetItems();
                                        List<BillStatementItem> pItems = new ArrayList();
                                        for(HospitalCharge bCharge:toBills){
                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+bCharge.getId());
                                            for(HospitalChargeItem bItems:bChargeItems){
                                                BillStatementItem b = bItems.toBillStatementItem();
                                                b.setBillstatement_id(record.getId());
                                                b.setAddedby(Care.getUser().getName());
                                                pItems.add(b);                                                
                                            }
                                        }
                                        
                                        pItems.stream().forEach(itm->{
                                            ROOM_ITEMS.add(itm);
                                        });
                                        
                                        Platform.runLater(()->{
                                            FXTable.setList(roomsTbl, ROOM_ITEMS);                                                                                 
                                            
                                            loadBillStatementItemAmounts();
                                            dialog.close();
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
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
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatementItem> records = roomsTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("roombills.xlsx");
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
                                    ExcelManager.export(BillStatementItem.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(roomsTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPharmacyTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<HospitalCharge> charges = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMinWidth(800);
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<HospitalCharge> lsv = new ListSelectionView();            
                    lsv.setMinWidth(710);
                    lsv.setMaxWidth(710);
                    lsv.setPrefWidth(710);   
                    lsv.setSourceHeader(new Label("Unbilled Charges"));
                    lsv.setTargetHeader(new Label("Selected Unbilled Charges"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<HospitalCharge> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends HospitalCharge> c) -> {
                        List<HospitalCharge> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Pharmacy' AND "+HospitalCharge.CHARGETIME+">='"+datefrom+"' ORDER BY "+HospitalCharge.CHARGETIME);
                    
                    List<BillStatementItem> billitems = new ArrayList();
                    billitems.addAll(ROOM_ITEMS);
                    billitems.addAll(PHARMACY_ITEMS);
                    billitems.addAll(SUPPLY_ITEMS);
                    billitems.addAll(HOSPITAL_ITEMS);
                    billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                    for(BillStatementItem lsitem:billitems){
                        for(int i = 0;i < charges.size();i++){    
                            if(lsitem.getChargenumber().equals(charges.get(i).getChargenumber())){
                                charges.remove(charges.get(i));
                                break;
                            }
                        }
                    }
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(charges));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<HospitalCharge> chargesA = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Pharmacy' AND "+HospitalCharge.CHARGETIME+">='"+datefromA+"' ORDER BY "+HospitalCharge.CHARGETIME);

                                        List<BillStatementItem> billitems = new ArrayList();
                                        billitems.addAll(ROOM_ITEMS);
                                        billitems.addAll(PHARMACY_ITEMS);
                                        billitems.addAll(SUPPLY_ITEMS);
                                        billitems.addAll(HOSPITAL_ITEMS);
                                        billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                                        for(BillStatementItem item:billitems){
                                            for(HospitalCharge chargeA:chargesA){                                            
                                                if(chargeA.getChargenumber().equals(item.getChargenumber())){
                                                    chargesA.remove(chargeA);
                                                    break;
                                                }
                                            }
                                        }
                                                                                
                                        Platform.runLater(()->{
                                            List<HospitalCharge> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(HospitalCharge lsitem:listedItems){
                                                for(int i = 0;i < chargesA.size();i++){    
                                                    if(lsitem.getChargenumber().equals(chargesA.get(i).getChargenumber())){
                                                        chargesA.remove(chargesA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(chargesA);
                                        });
                                        
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                    
                    
                    TableView<HospitalChargeItem> chargeItemsTbl = new TableView();
                    chargeItemsTbl.setMinWidth(710);
                    chargeItemsTbl.setMaxWidth(710);
                    chargeItemsTbl.setPrefWidth(710);   
                    
                    chargeItemsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    FXTable.disableColumnReorder(chargeItemsTbl);
                    FXTable.addColumn(chargeItemsTbl, "Description", HospitalChargeItem::descriptionProperty, false,200,200,200);
                    FXTable.addColumn(chargeItemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,50,50,50);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Price", HospitalChargeItem::sellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Amount", HospitalChargeItem::totalsellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Sales", HospitalChargeItem::vatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "NonVAT Sales", HospitalChargeItem::nonvatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Tax", HospitalChargeItem::inputvatProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Excempt", HospitalChargeItem::lessvatProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "SC Disc", HospitalChargeItem::scdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "PWD Disc", HospitalChargeItem::pwddiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "EMP Disc", HospitalChargeItem::empdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "OT Disc", HospitalChargeItem::otdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Net Sales", HospitalChargeItem::netsalesProperty, false,80,80,80);
                    
                    ListSelect selAct = new ListSelect(lsv);
                    selAct.setSourceChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    selAct.setTargetChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    lsv.getActions().add(selAct);
                    
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    VBox.setMargin(chargeItemsTbl, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv, chargeItemsTbl);

                    JFXButton filter = new JFXButton("Add Charges");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Bill Pharmacy Charges", content, FXDialog.DANGER, filter);
                    
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        List<HospitalCharge> toBills = lsv.getTargetItems();
                                        List<BillStatementItem> pItems = new ArrayList();
                                        List<BillStatementItem> sItems = new ArrayList();
                                        for(HospitalCharge bCharge:toBills){
                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+bCharge.getId());
                                            for(HospitalChargeItem bItems:bChargeItems){
                                                BillStatementItem b = bItems.toBillStatementItem();
                                                b.setBillstatement_id(record.getId());
                                                b.setAddedby(Care.getUser().getName());
                                                if(b.getItemtype().equals("Pharmacy")){
                                                    pItems.add(b);
                                                }else if(b.getItemtype().equals("Supply")){
                                                    sItems.add(b);
                                                }
                                                
                                            }
                                        }
                                        
                                        pItems.stream().forEach(itm->{
                                            PHARMACY_ITEMS.add(itm);
                                        });
                                        sItems.stream().forEach(itm->{
                                            SUPPLY_ITEMS.add(itm);
                                        });
                                        
                                        Platform.runLater(()->{
                                            FXTable.setList(pharmacyTbl, PHARMACY_ITEMS);                                         
                                            
                                            loadBillStatementItemAmounts();
                                            dialog.close();
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
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
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatementItem> records = pharmacyTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("pharmacybills.xlsx");
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
                                    ExcelManager.export(BillStatementItem.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(pharmacyTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadSupplyTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<HospitalCharge> charges = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<HospitalCharge> lsv = new ListSelectionView();            
                    lsv.setMinWidth(750);
                    lsv.setMaxWidth(750);
                    lsv.setPrefWidth(750);   
                    lsv.setSourceHeader(new Label("Unbilled Charges"));
                    lsv.setTargetHeader(new Label("Selected Unbilled Charges"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<HospitalCharge> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends HospitalCharge> c) -> {
                        List<HospitalCharge> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Pharmacy' AND "+HospitalCharge.CHARGETIME+">='"+datefrom+"' ORDER BY "+HospitalCharge.CHARGETIME);
                    
                    List<BillStatementItem> billitems = new ArrayList();
                    billitems.addAll(ROOM_ITEMS);
                    billitems.addAll(PHARMACY_ITEMS);
                    billitems.addAll(SUPPLY_ITEMS);
                    billitems.addAll(HOSPITAL_ITEMS);
                    billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                    for(BillStatementItem lsitem:billitems){
                        for(int i = 0;i < charges.size();i++){    
                            if(lsitem.getChargenumber().equals(charges.get(i).getChargenumber())){
                                charges.remove(charges.get(i));
                                break;
                            }
                        }
                    }
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(charges));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<HospitalCharge> chargesA = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Pharmacy' AND "+HospitalCharge.CHARGETIME+">='"+datefromA+"' ORDER BY "+HospitalCharge.CHARGETIME);

                                        List<BillStatementItem> billitems = new ArrayList();
                                        billitems.addAll(ROOM_ITEMS);
                                        billitems.addAll(PHARMACY_ITEMS);
                                        billitems.addAll(SUPPLY_ITEMS);
                                        billitems.addAll(HOSPITAL_ITEMS);
                                        billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                                        for(BillStatementItem item:billitems){
                                            for(HospitalCharge chargeA:chargesA){                                            
                                                if(chargeA.getChargenumber().equals(item.getChargenumber())){
                                                    chargesA.remove(chargeA);
                                                    break;
                                                }
                                            }
                                        }
                                                                                
                                        Platform.runLater(()->{
                                            List<HospitalCharge> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(HospitalCharge lsitem:listedItems){
                                                for(int i = 0;i < chargesA.size();i++){    
                                                    if(lsitem.getChargenumber().equals(chargesA.get(i).getChargenumber())){
                                                        chargesA.remove(chargesA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(chargesA);
                                        });
                                        
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                    
                    
                    TableView<HospitalChargeItem> chargeItemsTbl = new TableView();
                    chargeItemsTbl.setMinWidth(750);
                    chargeItemsTbl.setMaxWidth(750);
                    chargeItemsTbl.setPrefWidth(750);   
                    
                    chargeItemsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    FXTable.disableColumnReorder(chargeItemsTbl);
                    FXTable.addColumn(chargeItemsTbl, "Description", HospitalChargeItem::descriptionProperty, false,200,200,200);
                    FXTable.addColumn(chargeItemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,50,50,50);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Price", HospitalChargeItem::sellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Amount", HospitalChargeItem::totalsellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Sales", HospitalChargeItem::vatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "NonVAT Sales", HospitalChargeItem::nonvatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Tax", HospitalChargeItem::inputvatProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Excempt", HospitalChargeItem::lessvatProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "SC Disc", HospitalChargeItem::scdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "PWD Disc", HospitalChargeItem::pwddiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "EMP Disc", HospitalChargeItem::empdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "OT Disc", HospitalChargeItem::otdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Net Sales", HospitalChargeItem::netsalesProperty, false,80,80,80);
                    
                    ListSelect selAct = new ListSelect(lsv);
                    selAct.setSourceChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    selAct.setTargetChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    lsv.getActions().add(selAct);
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    VBox.setMargin(chargeItemsTbl, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv, chargeItemsTbl);

                    JFXButton filter = new JFXButton("Add Charges");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Bill Pharmacy Charges", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        List<HospitalCharge> toBills = lsv.getTargetItems();
                                        List<BillStatementItem> pItems = new ArrayList();
                                        List<BillStatementItem> sItems = new ArrayList();
                                        for(HospitalCharge bCharge:toBills){
                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+bCharge.getId());
                                            for(HospitalChargeItem bItems:bChargeItems){
                                                BillStatementItem b = bItems.toBillStatementItem();
                                                b.setBillstatement_id(record.getId());
                                                b.setAddedby(Care.getUser().getName());
                                                if(b.getItemtype().equals("Pharmacy")){
                                                    pItems.add(b);
                                                }else if(b.getItemtype().equals("Supply")){
                                                    sItems.add(b);
                                                }
                                                
                                            }
                                        }
                                        
                                        pItems.stream().forEach(itm->{
                                            PHARMACY_ITEMS.add(itm);
                                        });
                                        sItems.stream().forEach(itm->{
                                            SUPPLY_ITEMS.add(itm);
                                        });
                                        
                                        Platform.runLater(()->{
                                            FXTable.setList(pharmacyTbl, PHARMACY_ITEMS);                                         
                                            
                                            loadBillStatementItemAmounts();
                                            dialog.close();
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
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
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatementItem> records = supplyTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("supplybills.xlsx");
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
                                    ExcelManager.export(BillStatementItem.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(supplyTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadHospitalTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<HospitalCharge> charges = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<HospitalCharge> lsv = new ListSelectionView();            
                    lsv.setMinWidth(750);
                    lsv.setMaxWidth(750);
                    lsv.setPrefWidth(750);   
                    lsv.setSourceHeader(new Label("Unbilled Charges"));
                    lsv.setTargetHeader(new Label("Selected Unbilled Charges"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<HospitalCharge> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends HospitalCharge> c) -> {
                        List<HospitalCharge> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGETIME+">='"+datefrom+"' AND ("+HospitalCharge.CHARGEFACILITY+"<>'Room' AND "+HospitalCharge.CHARGEFACILITY+"<>'Pharmacy' AND "+HospitalCharge.CHARGEFACILITY+"<>'Professional') ORDER BY "+HospitalCharge.CHARGETIME);
                    
                    List<BillStatementItem> billitems = new ArrayList();
                    billitems.addAll(ROOM_ITEMS);
                    billitems.addAll(PHARMACY_ITEMS);
                    billitems.addAll(SUPPLY_ITEMS);
                    billitems.addAll(HOSPITAL_ITEMS);
                    billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                    for(BillStatementItem lsitem:billitems){
                        for(int i = 0;i < charges.size();i++){    
                            if(lsitem.getChargenumber().equals(charges.get(i).getChargenumber())){
                                charges.remove(charges.get(i));
                                break;
                            }
                        }
                    }
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(charges));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<HospitalCharge> chargesA = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGETIME+">='"+datefromA+"' AND ("+HospitalCharge.CHARGEFACILITY+"<>'Room' AND "+HospitalCharge.CHARGEFACILITY+"<>'Pharmacy' AND "+HospitalCharge.CHARGEFACILITY+"<>'Professional') ORDER BY "+HospitalCharge.CHARGETIME);

                                        List<BillStatementItem> billitems = new ArrayList();
                                        billitems.addAll(ROOM_ITEMS);
                                        billitems.addAll(PHARMACY_ITEMS);
                                        billitems.addAll(SUPPLY_ITEMS);
                                        billitems.addAll(HOSPITAL_ITEMS);
                                        billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                                        for(BillStatementItem item:billitems){
                                            for(HospitalCharge chargeA:chargesA){                                            
                                                if(chargeA.getChargenumber().equals(item.getChargenumber())){
                                                    chargesA.remove(chargeA);
                                                    break;
                                                }
                                            }
                                        }
                                                                                
                                        Platform.runLater(()->{
                                            List<HospitalCharge> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(HospitalCharge lsitem:listedItems){
                                                for(int i = 0;i < chargesA.size();i++){    
                                                    if(lsitem.getChargenumber().equals(chargesA.get(i).getChargenumber())){
                                                        chargesA.remove(chargesA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(chargesA);
                                        });
                                        
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                    
                    
                    TableView<HospitalChargeItem> chargeItemsTbl = new TableView();
                    chargeItemsTbl.setMinWidth(750);
                    chargeItemsTbl.setMaxWidth(750);
                    chargeItemsTbl.setPrefWidth(750);   
                    
                    chargeItemsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    FXTable.disableColumnReorder(chargeItemsTbl);
                    FXTable.addColumn(chargeItemsTbl, "Description", HospitalChargeItem::descriptionProperty, false,200,200,200);
                    FXTable.addColumn(chargeItemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,50,50,50);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Price", HospitalChargeItem::sellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Amount", HospitalChargeItem::totalsellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Sales", HospitalChargeItem::vatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "NonVAT Sales", HospitalChargeItem::nonvatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Tax", HospitalChargeItem::inputvatProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Excempt", HospitalChargeItem::lessvatProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "SC Disc", HospitalChargeItem::scdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "PWD Disc", HospitalChargeItem::pwddiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "EMP Disc", HospitalChargeItem::empdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "OT Disc", HospitalChargeItem::otdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Net Sales", HospitalChargeItem::netsalesProperty, false,80,80,80);
                    
                    ListSelect selAct = new ListSelect(lsv);
                    selAct.setSourceChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    selAct.setTargetChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    lsv.getActions().add(selAct);
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    VBox.setMargin(chargeItemsTbl, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv, chargeItemsTbl);

                    JFXButton filter = new JFXButton("Add Charges");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Bill Hospital Charges", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        List<HospitalCharge> toBills = lsv.getTargetItems();
                                        List<BillStatementItem> pItems = new ArrayList();
                                        for(HospitalCharge bCharge:toBills){
                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+bCharge.getId());
                                            for(HospitalChargeItem bItems:bChargeItems){
                                                BillStatementItem b = bItems.toBillStatementItem();
                                                b.setBillstatement_id(record.getId());
                                                b.setAddedby(Care.getUser().getName());
                                                pItems.add(b);                                                
                                            }
                                        }
                                        
                                        pItems.stream().forEach(itm->{
                                            HOSPITAL_ITEMS.add(itm);
                                        });
                                        
                                        Platform.runLater(()->{
                                            FXTable.setList(hospitalTbl, HOSPITAL_ITEMS);                                      
                                            
                                            loadBillStatementItemAmounts();
                                            dialog.close();
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
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
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatementItem> records = hospitalTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("hospitalbills.xlsx");
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
                                    ExcelManager.export(BillStatementItem.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(hospitalTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadProfessionalTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<HospitalCharge> charges = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<HospitalCharge> lsv = new ListSelectionView();            
                    lsv.setMinWidth(750);
                    lsv.setMaxWidth(750);
                    lsv.setPrefWidth(750);   
                    lsv.setSourceHeader(new Label("Unbilled Charges"));
                    lsv.setTargetHeader(new Label("Selected Unbilled Charges"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<HospitalCharge> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends HospitalCharge> c) -> {
                        List<HospitalCharge> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Professional' AND "+HospitalCharge.CHARGETIME+">='"+datefrom+"' ORDER BY "+HospitalCharge.CHARGETIME);
                    
                    List<BillStatementItem> billitems = new ArrayList();
                    billitems.addAll(ROOM_ITEMS);
                    billitems.addAll(PHARMACY_ITEMS);
                    billitems.addAll(SUPPLY_ITEMS);
                    billitems.addAll(HOSPITAL_ITEMS);
                    billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                    for(BillStatementItem lsitem:billitems){
                        for(int i = 0;i < charges.size();i++){    
                            if(lsitem.getChargenumber().equals(charges.get(i).getChargenumber())){
                                charges.remove(charges.get(i));
                                break;
                            }
                        }
                    }
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(charges));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<HospitalCharge> chargesA = SQLTable.list(HospitalCharge.class, HospitalCharge.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL AND "+HospitalCharge.CANCELTIME+" IS NULL AND "+HospitalCharge.CHARGEFACILITY+"='Professional' AND "+HospitalCharge.CHARGETIME+">='"+datefromA+"' ORDER BY "+HospitalCharge.CHARGETIME);

                                        List<BillStatementItem> billitems = new ArrayList();
                                        billitems.addAll(ROOM_ITEMS);
                                        billitems.addAll(PHARMACY_ITEMS);
                                        billitems.addAll(SUPPLY_ITEMS);
                                        billitems.addAll(HOSPITAL_ITEMS);
                                        billitems.addAll(PROFESSIONAL_ITEMS);
                                        
                                        for(BillStatementItem item:billitems){
                                            for(HospitalCharge chargeA:chargesA){                                            
                                                if(chargeA.getChargenumber().equals(item.getChargenumber())){
                                                    chargesA.remove(chargeA);
                                                    break;
                                                }
                                            }
                                        }
                                                                                
                                        Platform.runLater(()->{
                                            List<HospitalCharge> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(HospitalCharge lsitem:listedItems){
                                                for(int i = 0;i < chargesA.size();i++){    
                                                    if(lsitem.getChargenumber().equals(chargesA.get(i).getChargenumber())){
                                                        chargesA.remove(chargesA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(chargesA);
                                        });
                                        
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                    
                    
                    TableView<HospitalChargeItem> chargeItemsTbl = new TableView();
                    chargeItemsTbl.setMinWidth(750);
                    chargeItemsTbl.setMaxWidth(750);
                    chargeItemsTbl.setPrefWidth(750);   
                    
                    chargeItemsTbl.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    FXTable.disableColumnReorder(chargeItemsTbl);
                    FXTable.addColumn(chargeItemsTbl, "Description", HospitalChargeItem::descriptionProperty, false,200,200,200);
                    FXTable.addColumn(chargeItemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,50,50,50);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Price", HospitalChargeItem::sellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Amount", HospitalChargeItem::totalsellingProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Sales", HospitalChargeItem::vatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "NonVAT Sales", HospitalChargeItem::nonvatsalesProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Tax", HospitalChargeItem::inputvatProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "VAT Excempt", HospitalChargeItem::lessvatProperty, false,80,80,80);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "SC Disc", HospitalChargeItem::scdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "PWD Disc", HospitalChargeItem::pwddiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "EMP Disc", HospitalChargeItem::empdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "OT Disc", HospitalChargeItem::otdiscountProperty, false,65,65,65);
                    FXTable.addCurrencyColumn(chargeItemsTbl, "Net Sales", HospitalChargeItem::netsalesProperty, false,80,80,80);
                    
                    ListSelect selAct = new ListSelect(lsv);
                    selAct.setSourceChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    selAct.setTargetChangeListener((obs,oldVal,newVal)->{
                         if(newVal != null){                             
                             FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        HospitalCharge chr = (HospitalCharge)newVal;
                                        List<HospitalChargeItem> itms = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+chr.getId());
                                        Platform.runLater(()->{
                                            FXTable.setList(chargeItemsTbl, itms);
                                        });
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
                                    }
                                }
                            };
                            Care.process(task);
                         }
                     });
                    
                    lsv.getActions().add(selAct);
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    VBox.setMargin(chargeItemsTbl, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv, chargeItemsTbl);

                    JFXButton filter = new JFXButton("Add Charges");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Bill Professional Fees", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        List<HospitalCharge> toBills = lsv.getTargetItems();
                                        List<BillStatementItem> pItems = new ArrayList();
                                        for(HospitalCharge bCharge:toBills){
                                            List<HospitalChargeItem> bChargeItems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+bCharge.getId());
                                            for(HospitalChargeItem bItems:bChargeItems){
                                                BillStatementItem b = bItems.toBillStatementItem();
                                                b.setBillstatement_id(record.getId());
                                                b.setAddedby(Care.getUser().getName());
                                                pItems.add(b);
                                            }
                                        }
                                        
                                        pItems.stream().forEach(itm->{
                                            PROFESSIONAL_ITEMS.add(itm);
                                        });
                                        
                                        Platform.runLater(()->{
                                            FXTable.setList(professionalTbl, PROFESSIONAL_ITEMS);                                    
                                            
                                            loadBillStatementItemAmounts();
                                            dialog.close();
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        
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
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatementItem> records = professionalTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("pfbills.xlsx");
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
                                    ExcelManager.export(BillStatementItem.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(professionalTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBenefitsTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<PatientBenefit> records = benefitsTbl.getItems();
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
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(benefitsTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPaymentTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Item");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<Payment> payments = new ArrayList();
                    
                    VBox content = new VBox();
                    content.setMaxWidth(800);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER_LEFT);
                    content.setSpacing(35);
                    content.setPadding(new Insets(25,25,25,25));
                                        
                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Charges From");
                    dto.setMinHeight(28);
                    dto.setMinWidth(180);
                    dto.setMaxWidth(180);
                    dto.setPrefWidth(180);
                    dto.setValue((record.getAdmissiontime()!= null)? record.getAdmissiontime().toLocalDate():LocalDate.now());
                                        
                    ListSelectionView<Payment> lsv = new ListSelectionView();            
                    lsv.setMinWidth(750);
                    lsv.setMaxWidth(750);
                    lsv.setPrefWidth(750);   
                    lsv.setSourceHeader(new Label("Registered Payment"));
                    lsv.setTargetHeader(new Label("Selected Registered Payment"));
                    
                    final Label totalLb = new Label("Total Amount : 0.00");
                    totalLb.getStyleClass().add("anton-font");
                    lsv.setTargetFooter(totalLb);
                    
                    ObservableList<Payment> ob = lsv.getTargetItems();
                    ob.addListener((javafx.collections.ListChangeListener.Change<? extends Payment> c) -> {
                        List<Payment> selcharges = lsv.getTargetItems();
                        List<Double> sales = selcharges.stream().map(itm -> itm.getAmount()).collect(Collectors.toList());
                        double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                        totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
                    });
                    
                    java.sql.Timestamp datefrom = java.sql.Timestamp.valueOf(LocalDateTime.of(dto.getValue(), LocalTime.of(0, 0, 0)));
                    payments = SQLTable.list(Payment.class, Payment.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+Payment.PAYMENTTIME+">='"+datefrom+"' AND "+Payment.BILLSTATEMENT_ID+"<>"+record.getId()+" ORDER BY "+Payment.PAYMENTTIME);
                    
                    lsv.setSourceItems(FXCollections.observableArrayList(payments));
                    
                    dto.valueProperty().addListener((obs,oldVal,newVal)->{
                        if(newVal != null){
                            FXTask task = new FXTask() {
                                @Override
                                protected void load() {                           
                                    try{
                                        java.sql.Timestamp datefromA = java.sql.Timestamp.valueOf(LocalDateTime.of(newVal, LocalTime.of(0, 0, 0)));
                                        List<Payment> paymentsA = SQLTable.list(Payment.class, Payment.PATIENT_ID+"='"+record.getPatient_id()+"' AND "+Payment.PAYMENTTIME+">='"+datefromA+"' AND "+Payment.BILLSTATEMENT_ID+"<>"+record.getId()+" ORDER BY "+Payment.PAYMENTTIME);
                                                                                
                                        Platform.runLater(()->{
                                            List<Payment> listedItems = new ArrayList();
                                            listedItems.addAll(lsv.getTargetItems());
                                            listedItems.addAll(lsv.getSourceItems());
                                            
                                            for(Payment lsitem:listedItems){
                                                for(int i = 0;i < paymentsA.size();i++){    
                                                    if(lsitem.getId()== paymentsA.get(i).getId()){
                                                        paymentsA.remove(paymentsA.get(i));
                                                        break;
                                                    }
                                                }
                                            }
                                            lsv.getSourceItems().addAll(paymentsA);
                                        });
                                        
                                    }catch(Exception er){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    }
                                }
                            };
                            Care.process(task);
                        }
                    });
                                        
                    
                    VBox.setMargin(dto, new Insets(0,0,0,0));
                    VBox.setMargin(lsv, new Insets(0,0,0,0));
                    
                    
                    content.getChildren().addAll(dto, lsv);

                    JFXButton filter = new JFXButton("Add Payment");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialogx = FXDialog.showConfirmDialog(stackPane, "Add Payment", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(lsv.getTargetItems().size() > 0){                            
                            List<Payment> pays = lsv.getTargetItems();
                            pays.stream().forEach(pay->{
                                pay.setBillstatement_id(record.getId());
                                PAYMENTS.add(pay);
                            });
                            loadBillStatementItemAmounts();
                            dialogx.close();
                        }
                    });
                    
                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Payment> records = paymentsTbl.getItems();
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
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(paymentsTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadValidationTableFilters() {
        try {
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label addLb = new Label("Filter");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalCharge> records = validationTbl.getItems();
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
                                        FXDialog.showMessageDialog(stackPane, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
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
                        FXDialog.showMessageDialog(stackPane, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            FXTable.addCustomTableMenu(validationTbl, addLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBillStatementItemAmounts(){
        try{
            double gross = 0;
            double vatsales = 0;
            double nonvatsales = 0;
            double scpwd = 0;
            double empot = 0;
            double fcr = 0;
            double scr = 0;
            double benefits = 0;
            double tax = 0;
            double net = 0;
            
            List<BillStatementItem> items = new ArrayList();
            items.addAll(ROOM_ITEMS);
            items.addAll(PHARMACY_ITEMS);
            items.addAll(SUPPLY_ITEMS);
            items.addAll(HOSPITAL_ITEMS);
            items.addAll(PROFESSIONAL_ITEMS);
            
            for(BillStatementItem item:items){
                gross+= item.getAmount();
                vatsales+= item.getVatsales();
                nonvatsales+= item.getNonvatsales();
                scpwd+= item.getScdiscount()+item.getPwddiscount();
                empot+= item.getEmpdiscount()+item.getOtdiscount();
                fcr+= item.getFirstcaserateamount();
                scr+= item.getSecondcaserateamount();
                benefits += item.getBenefitamount();
                tax += item.getInputvat();
                net += item.getNetsales();
            }
            
            t1grossL.setText(NumberKit.toCurrency(gross));
            t1vatsalesL.setText(NumberKit.toCurrency(vatsales));
            t1nonvatsalesL.setText(NumberKit.toCurrency(nonvatsales));
            t1scpwdL.setText(NumberKit.toCurrency(scpwd));
            t1empotL.setText(NumberKit.toCurrency(empot));
            t1firstcaserateL.setText(NumberKit.toCurrency(fcr));
            t1secondcaserateL.setText(NumberKit.toCurrency(scr));
            t1otherbenefitsL.setText(NumberKit.toCurrency(benefits));
            t1vatamountL.setText(NumberKit.toCurrency(tax));
            t1netpayablesL.setText(NumberKit.toCurrency(net));
            
            BillCategorySummary roomC = new BillCategorySummary("Room Charges");
            BillCategorySummary pharC = new BillCategorySummary("Pharmacy Charges");
            BillCategorySummary supC = new BillCategorySummary("Central Supply");
            BillCategorySummary labC = new BillCategorySummary("Laboratory Charges");
            BillCategorySummary radC = new BillCategorySummary("Radiology Charges");
            BillCategorySummary proC = new BillCategorySummary("Procedures");
            BillCategorySummary erC = new BillCategorySummary("ER/Ward Charges");
            BillCategorySummary otC = new BillCategorySummary("Hospital Charges");
            BillCategorySummary docC = new BillCategorySummary("Professional Fees");
            List<BillCategorySummary> summaryList = new ArrayList();
            
            boolean lab = false;
            boolean rad = false;
            boolean op = false;
            boolean er = false;
            boolean ot = false;
            
            ROOM_ITEMS.stream().forEach(itm->{
                roomC.add(itm);
                if(!summaryList.contains(roomC)){
                    summaryList.add(roomC);
                }
            });
            PHARMACY_ITEMS.stream().forEach(itm->{
                pharC.add(itm);
                if(!summaryList.contains(pharC)){
                    summaryList.add(pharC);
                }
            });
            SUPPLY_ITEMS.stream().forEach(itm->{
                supC.add(itm);
                if(!summaryList.contains(supC)){
                    summaryList.add(supC);
                }
            });
            HOSPITAL_ITEMS.stream().forEach(itm->{
                if(itm.getFacility().equals("Laboratory")){
                    labC.add(itm);
                    if(!summaryList.contains(labC)){
                        summaryList.add(labC);
                    }
                }else if(itm.getFacility().equals("Radiology")){
                    radC.add(itm);     
                    if(!summaryList.contains(radC)){
                        summaryList.add(radC);
                    }
                }else if(itm.getFacility().equals("Operating")){
                    proC.add(itm);
                    if(!summaryList.contains(proC)){
                        summaryList.add(proC);
                    }
                }else if(itm.getFacility().equals("ER") || itm.getFacility().equals("Ward")){
                    erC.add(itm);
                    if(!summaryList.contains(erC)){
                        summaryList.add(erC);
                    }
                }else{
                    otC.add(itm);
                    if(!summaryList.contains(otC)){
                        summaryList.add(otC);
                    }
                }
            });
            PROFESSIONAL_ITEMS.stream().forEach(itm->{
                docC.add(itm);
                if(!summaryList.contains(docC)){
                    summaryList.add(docC);
                }
            });
            
            FXTable.setList(t1table, summaryList);
            
            roomscountLbl.setText("Items : "+ROOM_ITEMS.size());
            List<Double> rsales = ROOM_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            double rtotal = rsales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            roomsdetailLbl.setText("Net Payables : "+NumberKit.toCurrency(rtotal));
            
            pharmacycountLbl.setText("Items : "+PHARMACY_ITEMS.size());
            List<Double> rphsales = PHARMACY_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            double phtotal = rphsales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            pharmacydetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(phtotal));
            
            supplycountLbl.setText("Items : "+SUPPLY_ITEMS.size());
            List<Double> sssales = SUPPLY_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            double sstotal = sssales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            supplydetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(sstotal));
            
            hospitalcountLbl.setText("Items : "+HOSPITAL_ITEMS.size());
            List<Double> hsales = HOSPITAL_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            double htotal = hsales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            hospitaldetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(htotal));
            
            professionalcountLbl.setText("Items : "+PROFESSIONAL_ITEMS.size());
            List<Double> psales = PROFESSIONAL_ITEMS.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            double ptotal = psales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            professionaldetailsLbl.setText("Net Payables : "+NumberKit.toCurrency(ptotal));
            
            benefitscountLbl.setText("Items : "+BENEFITS.size());
            List<Double> bsales = BENEFITS.stream().map(itm -> itm.getAmount()).collect(Collectors.toList());
            double btotal = bsales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            benefitsdetailsLbl.setText("Total Amount : "+NumberKit.toCurrency(btotal));
            
            paymentscountLbl.setText("Items : "+PAYMENTS.size());
            List<Double> paysales = PAYMENTS.stream().map(itm -> itm.getAmount()).collect(Collectors.toList());
            double paytotal = paysales.stream().collect(Collectors.summingDouble(Double::doubleValue));
            paymentsdetailsLbl.setText("Total Amount : "+NumberKit.toCurrency(paytotal));
            t1paidL.setText("PAID "+NumberKit.toCurrency(paytotal));
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
