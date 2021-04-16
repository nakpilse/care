package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import models.ERConsultation;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.HospitalService;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.NumberKit;
import nse.dcfx.utils.StringKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ERConsultationFormController implements Initializable,FormController<ERConsultation> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private static MaskerPane maskerPane;
    private ERConsultation record = null;
    private List<HospitalChargeItem> CHARGE_ITEMS = new ArrayList();
    private List<HospitalChargeItem> REMOVE_ITEMS = new ArrayList();
    private HospitalCharge CHARGE = null;

    
    @FXML
    private Label titleLbl;
    
    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode recordMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode chargesMenu;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab recordTab;

    @FXML
    private JFXTextField ernumF;

    @FXML
    private JFXDatePicker dateF;

    @FXML
    private JFXTimePicker timeF;

    @FXML
    private JFXTextArea complainsF;

    @FXML
    private JFXTextArea statisticsF;

    @FXML
    private JFXTextField physicianF;

    @FXML
    private JFXTextArea diagnosisF;

    @FXML
    private JFXTextArea planF;

    @FXML
    private JFXTextField caseF;
    
    @FXML
    private JFXTextField admittedF;

    @FXML
    private Tab chargesTab;

    @FXML
    private JFXButton addchargesBtn;

    @FXML
    private TableView<HospitalChargeItem> chargesTbl;

    @FXML
    private Label totalChargesLbl;
    
     @FXML
    private Tab transferTab;

    @FXML
    private JFXTextField transferfromF;

    @FXML
    private JFXDatePicker tfromdateF;

    @FXML
    private JFXTimePicker tfromtimeF;

    @FXML
    private JFXTextArea transferfromnoteF;

    @FXML
    private JFXTextField transfertoF;

    @FXML
    private JFXDatePicker ttodateF;

    @FXML
    private JFXTextArea transfertonoteF;

    @FXML
    private JFXTimePicker ttotimeF;

    @FXML
    private JFXButton saveBtn;
    
    @FXML
    private JFXButton printchargeBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

    @FXML
    void addCharges(ActionEvent event) {
        try{
            List<HospitalService> services = SQLTable.list(HospitalService.class,HospitalService.FACILITY+"='ER'");
            if(!services.isEmpty()){           
                List<String> servicenames = new ArrayList();
                services.stream().forEach(serv->{
                    servicenames.add(serv.getDescription());
                });
                
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35,25,25,25));
                
                JFXTextField serviceInputF = new JFXTextField();
                serviceInputF.setPromptText("Select ER Charge");    
                serviceInputF.setMinHeight(28);
                serviceInputF.setMinWidth(250);
                serviceInputF.setMaxWidth(250);
                serviceInputF.setPrefWidth(250);   
                serviceInputF.setLabelFloat(true);

                JFXTextField amountF = new JFXTextField();
                amountF.setPromptText("Amount");    
                amountF.setMinHeight(28);
                amountF.setMinWidth(250);
                amountF.setMaxWidth(250);
                amountF.setPrefWidth(250);   
                amountF.setLabelFloat(true);
                
                TextFields.bindAutoCompletion(serviceInputF, servicenames);
                
                FXField.addRequiredValidator(serviceInputF);
                FXField.addRequiredValidator(amountF);
                FXField.addDoubleValidator(amountF, 0, 99999, 0);
                FXField.addFocusValidationListener(serviceInputF,amountF);
                
                HospitalChargeItem itm = new HospitalChargeItem();
                amountF.textProperty().bindBidirectional(itm.sellingProperty(), new NumberConverter());
                
                serviceInputF.textProperty().addListener((obs,oldVal,newVal) ->{
                    if(newVal != null && !newVal.isEmpty()){
                        boolean found = false;
                        for(HospitalService serv:services){
                            if(newVal.equalsIgnoreCase(serv.getDescription())){
                                itm.setChargenumber(CHARGE.getChargenumber());
                                itm.setChargetime(LocalDateTime.now());
                                itm.setDescription(serv.getDescription());
                                itm.setSelling(serv.getPrice());
                                itm.setQuantity(1);
                                itm.setService(true);
                                itm.setItemtype("ER Charge");
                                itm.setFacility("ER");
                                itm.setVatable(false);
                                itm.setUser(Care.getUser().getName());
                                itm.setChargeto(record.getPatientname());
                                itm.setPhysician(record.getPhysician());
                                itm.setUser_id(Care.getUser().getId());
                                itm.setPatient_id(record.getPatient_id());
                                itm.setHospitalcharge_id(CHARGE.getId());
                                found = true;
                                break;
                            }
                        }
                        
                        if(!found){
                            itm.setChargenumber(CHARGE.getChargenumber());
                            itm.setChargetime(LocalDateTime.now());
                            itm.setDescription(newVal);
                            itm.setQuantity(1);
                            itm.setService(true);
                            itm.setItemtype("ER Charge");
                            itm.setFacility("ER");
                            itm.setVatable(false);
                            itm.setUser(Care.getUser().getName());
                            itm.setChargeto(record.getPatientname());
                            itm.setUser_id(Care.getUser().getId());
                            itm.setPatient_id(record.getPatient_id());
                            itm.setHospitalcharge_id(CHARGE.getId());
                        }
                        
                        if(record.getId() > 0){
                            itm.setRecordtable(ERConsultation.TABLE_NAME);
                            itm.setRecordtableid(record.getId());
                        }
                    }
                });
                
                content.getChildren().setAll(serviceInputF,amountF);

                JFXButton confirmBtn = new JFXButton("Confirm");                                    
                confirmBtn.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(stackPane,"Add ER Charge",content,FXDialog.DANGER,confirmBtn);
                serviceInputF.requestFocus();

                confirmBtn.setOnAction(confEvt ->{
                    if(amountF.validate() && serviceInputF.validate()){
                        itm.calculateNet(false);
                        CHARGE_ITEMS.add(itm);
                        CHARGE.setItems(CHARGE_ITEMS);
                        CHARGE.calculateTotal(CHARGE_ITEMS);
                        FXTable.setList(chargesTbl, CHARGE_ITEMS);
                        totalChargesLbl.setText("Total Amount : "+NumberKit.toCurrency(CHARGE.getNetsales()));
                        dialog.close();
                    }
                });
            }
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void printCharge(ActionEvent event) {
        try{
            if(!CHARGE.getItems().isEmpty()){
                FXTask task = new FXTask(){
                @Override
                    protected void load() {
                        try{
                            Map<String,String> opts = GlobalOption.getMap("General");
                            CHARGE.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "ER", false);
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                };
                Care.process(task);
            }
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void formClose(ActionEvent event) {
        try{
            dialog.close();
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            if(isFieldInputsValid()){
                FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            saveBtn.setDisable(true);
                            closeBtn.setDisable(true);
                        });
                        Map<String,String> opts = GlobalOption.getMap("General");
                        if(record.getId() <= 0){
                            record.setUser_id(Care.getUser().getId());
                            record.setEncoder(Care.getUser().getName());
                            CHARGE.setOpt0(record.getPhysician());
                            if(dateF.getValue() != null && timeF.getValue() != null){
                                record.setConsultationtime(LocalDateTime.of(dateF.getValue(), timeF.getValue()));
                            }
                            if(tfromdateF.getValue() != null && tfromtimeF.getValue() != null){
                                record.setTransferfromtime(LocalDateTime.of(tfromdateF.getValue(), tfromtimeF.getValue()));
                            }
                            if(ttodateF.getValue() != null && ttotimeF.getValue() != null){
                                record.setTransfertotime(LocalDateTime.of(ttodateF.getValue(), ttotimeF.getValue()));
                            }
                            int id = record.save(true);
                            if(id > 0){
                                CHARGE.setRecordtable(ERConsultation.TABLE_NAME);
                                CHARGE.setRecordtableid(id);
                                CHARGE_ITEMS.stream().forEach(itms->{
                                    itms.setRecordtable(ERConsultation.TABLE_NAME);
                                    itms.setRecordtableid(id);
                                });
                                CHARGE.save(CHARGE_ITEMS);
                                Platform.runLater(()->{       
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Success", "New ER record has been registered!", FXDialog.SUCCESS);
                                });
                                CHARGE.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "ER", false);
                                postAction();
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Error", "Failed to save ER record!", FXDialog.DANGER);
                                });
                            }
                        }else{
                            if(dateF.getValue() != null && timeF.getValue() != null){
                                record.setConsultationtime(LocalDateTime.of(dateF.getValue(), timeF.getValue()));
                            }
                            if(tfromdateF.getValue() != null && tfromtimeF.getValue() != null){
                                record.setTransferfromtime(LocalDateTime.of(tfromdateF.getValue(), tfromtimeF.getValue()));
                            }
                            if(ttodateF.getValue() != null && ttotimeF.getValue() != null){
                                record.setTransfertotime(LocalDateTime.of(ttodateF.getValue(), ttotimeF.getValue()));
                            }
                            CHARGE.setOpt0(record.getPhysician());
                            if(record.update()){
                                CHARGE.update();
                                CHARGE_ITEMS.forEach(itm->{
                                    if(itm.getId() <= 0){
                                        itm.save();
                                    }else{
                                        itm.update();
                                    }
                                    
                                });
                                REMOVE_ITEMS.forEach(itm->{
                                    itm.delete();
                                });
                                Platform.runLater(()->{       
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Success", "ER record has been updated!", FXDialog.SUCCESS);
                                });
                                postAction();
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Error", "Failed to save ER record!", FXDialog.DANGER);
                                });
                            }
                        }
                        
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            saveBtn.setDisable(false);
                            closeBtn.setDisable(false);
                        });
                    }
                }
            };
            Care.process(task);
            }
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadCharges(ActionEvent event) {
        try{
            mainTabPane.getSelectionModel().select(chargesTab);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadRecord(ActionEvent event) {
        try{
            mainTabPane.getSelectionModel().select(recordTab);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void loadTransferInfo(ActionEvent event) {
        try{
            mainTabPane.getSelectionModel().select(transferTab);
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
    public void postAction() {
        try{
            if(UI_CONTROLLER instanceof ERUXController){
                UI_CONTROLLER.reloadReferences(0);
            }else if(UI_CONTROLLER instanceof AdmissionUXController){
                UI_CONTROLLER.reloadReferences(3);
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
    public void setFormObject(ERConsultation obj) {
        this.record = obj;
    }

    @Override
    public ERConsultation getFormObject() {
        return this.record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return ernumF.validate() && physicianF.validate() && dateF.validate() && timeF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            ernumF.textProperty().bindBidirectional(record.recordnumberProperty());
            physicianF.textProperty().bindBidirectional(record.physicianProperty());
            complainsF.textProperty().bindBidirectional(record.complainsProperty());
            statisticsF.textProperty().bindBidirectional(record.initialstatsProperty());
            diagnosisF.textProperty().bindBidirectional(record.diagnosisProperty());
            admittedF.textProperty().bindBidirectional(record.admittedbyProperty());
            caseF.textProperty().bindBidirectional(record.casecodeProperty());
            planF.textProperty().bindBidirectional(record.remarksProperty());
            transferfromF.textProperty().bindBidirectional(record.transferfromProperty());
            transferfromnoteF.textProperty().bindBidirectional(record.transferfromnoteProperty());
            transfertoF.textProperty().bindBidirectional(record.transfertoProperty());
            transfertonoteF.textProperty().bindBidirectional(record.transfertonoteProperty());
            
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
            
            if(record.getId() <= 0){
                addchargesBtn.setDisable(false);
                addchargesBtn.setVisible(true);
                
                LocalDateTime CHARGETIME = LocalDateTime.now();
                String CHARGENUM = StringKit.timeCode(CHARGETIME);
                
                HospitalChargeItem ercharge = new HospitalChargeItem();
                
                Map<String,String> opts = GlobalOption.getMap("ER");
                
                ercharge.setChargenumber(CHARGENUM);
                ercharge.setChargetime(CHARGETIME);
                ercharge.setDescription("ER Charge");
                
                LocalTime cur_time = LocalTime.now();
                double amt = ((cur_time.isAfter(LocalTime.of(5, 59)) && cur_time.isBefore(LocalTime.of(17, 59)))? Double.parseDouble(opts.get("ER_CHARGE_AM")):Double.parseDouble(opts.get("ER_CHARGE_PM")));
                
                ercharge.setSelling(amt);
                ercharge.setQuantity(1);
                ercharge.setService(true);
                ercharge.setItemtype("ER Charge");
                ercharge.setFacility("ER");
                ercharge.setVatable(false);
                ercharge.calculateNet(false);
                ercharge.setUser(Care.getUser().getName());
                ercharge.setChargeto(record.getPatientname());
                ercharge.setUser_id(Care.getUser().getId());
                ercharge.setPatient_id(record.getPatient_id());
                
                
                CHARGE = new HospitalCharge();
                CHARGE.setChargenumber(CHARGENUM);
                CHARGE.setChargetime(CHARGETIME);
                CHARGE.setChargeto(record.getPatientname());
                CHARGE.setChargefacility("ER");
                CHARGE.setChargetype("Walk-In");
                CHARGE.setUser(Care.getUser().getName());
                CHARGE.setUser_id(Care.getUser().getId());
                CHARGE.setPatient_id(record.getPatient_id());
                CHARGE_ITEMS.add(ercharge);
                CHARGE.setItems(CHARGE_ITEMS);
                CHARGE.calculateTotal(CHARGE_ITEMS);
                Platform.runLater(()->{
                    FXTable.setList(chargesTbl, CHARGE_ITEMS);
                    totalChargesLbl.setText("Total Amount : "+NumberKit.toCurrency(CHARGE.getNetsales()));
                    titleLbl.setText("ER Consultation Info - "+record.getPatientname());
                    printchargeBtn.setDisable(true);
                    printchargeBtn.setVisible(false);
                });
                
                Platform.runLater(()->{
                    dateF.setValue(LocalDate.now());
                    timeF.setValue(LocalTime.now());
                });
                
            }else{
                CHARGE = (HospitalCharge)SQLTable.get(HospitalCharge.class, HospitalCharge.RECORDTABLEID+"="+String.valueOf(record.getId())+" AND "+HospitalCharge.RECORDTABLE+"='"+ERConsultation.TABLE_NAME+"'");
                CHARGE_ITEMS = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"='"+CHARGE.getId()+"'");
                CHARGE.setItems(CHARGE_ITEMS);
                Platform.runLater(()->{
                    FXTable.setList(chargesTbl, CHARGE_ITEMS);
                    totalChargesLbl.setText("Total Amount : "+NumberKit.toCurrency(CHARGE.getNetsales()));
                    if(CHARGE.getPaymenttime() != null){
                        addchargesBtn.setDisable(true);
                        addchargesBtn.setVisible(false);
                    }
                    titleLbl.setText("ER Consultation Info - "+record.getPatientname());
                    printchargeBtn.setDisable(false);
                    printchargeBtn.setVisible(true);
                });
                
                Platform.runLater(()->{
                    dateF.setValue(record.getConsultationtime().toLocalDate());
                    timeF.setValue(record.getConsultationtime().toLocalTime());
                    
                    if(record.getTransferfromtime() != null){
                        tfromdateF.setValue(record.getTransferfromtime().toLocalDate());
                        tfromtimeF.setValue(record.getTransferfromtime().toLocalTime());
                    }
                    
                    if(record.getTransfertotime() != null){
                        ttodateF.setValue(record.getTransfertotime().toLocalDate());
                        ttotimeF.setValue(record.getTransfertotime().toLocalTime());
                    }
                });                
            }
            
            
            chargesTbl.setEditable(false);
            chargesTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(chargesTbl);

            FXTable.addColumn(chargesTbl, "Charge #", HospitalChargeItem::chargenumberProperty, false, 110, 110, 110);
            FXTable.addColumn(chargesTbl, "Particulars", HospitalChargeItem::descriptionProperty, false);
            FXTable.addColumn(chargesTbl, "Amount", HospitalChargeItem::netsalesProperty, false, 110, 110, 110);
            if(CHARGE.getPaymenttime() == null){
                TableColumn actCol = FXTable.addColumn(chargesTbl, " ", HospitalChargeItem::descriptionProperty, false, 86, 86, 86);
                actCol.setCellFactory(column -> {
                    return new TableCell<HospitalChargeItem, String>() {
                        @Override
                        protected void updateItem(String value, boolean empty) {
                            super.updateItem(value, empty);
                            if (empty || value == null || value.isEmpty()) {
                                setGraphic(null);
                                setStyle("");
                            } else {
                                try {
                                    HospitalChargeItem row_data = getTableView().getItems().get(getIndex());
                                    if (row_data != null) {
                                        HBox container = new HBox();
                                        container.setMinSize(86, 32);
                                        container.setMaxSize(86, 32);
                                        container.setPrefSize(86, 32);
                                        container.setSpacing(4);

                                        JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {                                        
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(35);
                                            content.setPadding(new Insets(35,25,25,25));

                                            JFXTextField amountF = new JFXTextField();
                                            amountF.setPromptText("Amount");    
                                            amountF.setMinHeight(28);
                                            amountF.setMinWidth(250);
                                            amountF.setMaxWidth(250);
                                            amountF.setPrefWidth(250);   
                                            amountF.setLabelFloat(true);

                                            content.getChildren().setAll(amountF);

                                            DoubleProperty amt = new SimpleDoubleProperty(row_data.getSelling());  
                                            amountF.textProperty().bindBidirectional(amt, new NumberConverter());

                                            JFXButton confirmBtn = new JFXButton("Confirm");                                    
                                            confirmBtn.getStyleClass().add("btn-danger");

                                            JFXDialog dialog = FXDialog.showConfirmDialog(stackPane,row_data.getDescription(),content,FXDialog.DANGER,confirmBtn);
                                            amountF.requestFocus();

                                            confirmBtn.setOnAction(confEvt ->{
                                                if(amountF.validate()){
                                                    Platform.runLater(()->{
                                                        row_data.setSelling(amt.get());
                                                        row_data.calculateNet();
                                                        CHARGE.calculateTotal(CHARGE_ITEMS);
                                                        totalChargesLbl.setText("Total Amount : "+NumberKit.toCurrency(CHARGE.getNetsales()));
                                                        dialog.close();
                                                    });
                                                }
                                            });
                                        });

                                        edtBtn.setTooltip(new Tooltip("Edit"));
                                        edtBtn.getStyleClass().add("btn-primary");
                                        edtBtn.setStyle("-jfx-button-type : FLAT;");
                                        edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                        JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {                                        
                                            Platform.runLater(()->{
                                                CHARGE_ITEMS.remove(row_data);
                                                REMOVE_ITEMS.add(row_data);
                                                CHARGE.setItems(CHARGE_ITEMS);
                                                CHARGE.calculateTotal(CHARGE_ITEMS);
                                                FXTable.setList(chargesTbl, CHARGE_ITEMS);
                                                totalChargesLbl.setText("Total Amount : "+NumberKit.toCurrency(CHARGE.getNetsales()));
                                            });
                                        });

                                        delBtn.setTooltip(new Tooltip("Delete"));
                                        delBtn.getStyleClass().add("btn-danger");
                                        delBtn.setStyle("-jfx-button-type : FLAT;");
                                        delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                        container.setAlignment(Pos.CENTER);
                                        container.getChildren().addAll(delBtn, edtBtn);

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
            }
            
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(ernumF);
            FXField.addRequiredValidator(physicianF);
            FXField.addRequiredValidator(dateF);
            FXField.addRequiredValidator(timeF);
            FXField.addFocusValidationListener(ernumF,physicianF,dateF,timeF);
                    
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            if(record.getId() <= 0){
                
                
            }else{          
                
            }
            
            List<HospitalPersonel> phys = SQLTable.list(HospitalPersonel.class);
            List<String> names = new ArrayList();
            phys.stream().forEach(ph->{
                names.add(ph.getName());
            });
            TextFields.bindAutoCompletion(physicianF, phys);
            
            physicianF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null && !newVal.isEmpty()){
                    boolean found = false;
                    for(HospitalPersonel person:phys){
                        if(newVal.equalsIgnoreCase(person.getName())){
                            record.setHospitalpersonel_id(person.getId());
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        record.setHospitalpersonel_id(0);
                    }
                }
            });
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(ERConsultation data,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/ERConsultationForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog,data, ui_controller, 900, 600);
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
    
}
