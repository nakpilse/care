/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.Admission;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.HospitalService;
import models.LaboratoryTest;
import models.Patient;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.NumberKit;
import nse.dcfx.utils.StringKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Nsoft
 */
public class CreateLabChargeFormController implements Initializable,FormController<Object> {

    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private static MaskerPane  maskerPane = null;
    private Object record = null;
    private Map<String,String> opts;
    private List<HospitalService> services = new ArrayList();
    private List<HospitalPersonel> persons = new ArrayList();
    private List<Patient> patients = new ArrayList();
    private List<String> categories = new ArrayList();
            
    
    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private Tab singleTab;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private Tab multiTab;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private JFXTextField t1patientF;

    @FXML
    private JFXComboBox<String> t1genderF;

    @FXML
    private JFXTextField t1ageF;

    @FXML
    private JFXTextField t1physicianF;
    
    @FXML
    private JFXComboBox<String> t1categF;

    @FXML
    private JFXComboBox<HospitalService> t1testF;

    @FXML
    private JFXTextField t1priceF;

    @FXML
    private JFXButton t1addBtn;

    @FXML
    private JFXButton t1createBtn;

    @FXML
    private TableView<HospitalService> t1Table;
    
    @FXML
    private Label t1errorLb;

    @FXML
    private Label t1resultLb;

    @FXML
    private Label t1totalLb;

    @FXML
    private JFXTextField t2patientF;

    @FXML
    private JFXComboBox<String> t2genderF;

    @FXML
    private JFXTextField t2ageF;

    @FXML
    private JFXTextField t2physicianF;

    @FXML
    private JFXTextField t2testF;

    @FXML
    private JFXTextField t2priceF;
    
    @FXML
    private JFXButton t2addBtn;

    @FXML
    private JFXButton t2createBtn;

    @FXML
    private TableView<HospitalService> t2Table;

    @FXML
    private Label t2resultLb;

    @FXML
    private Label t2totalLb;
    
    @FXML
    private Label t2errorLb;

    @FXML
    private JFXTextField t2chargedtoF;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void loadMultiTab(ActionEvent event) {
        mainTabPane.getSelectionModel().select(multiTab);
    }

    @FXML
    void loadSingleTab(ActionEvent event) {
        mainTabPane.getSelectionModel().select(singleTab);
    }

    @FXML
    void saveMultipleRecords(ActionEvent event) {
        if(t2chargedtoF.validate() && t2Table.getItems().size() > 0){
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            t2createBtn.setDisable(true);
                        });
                        
                        Map<String,String> opts = GlobalOption.getMap("General");
                        List<HospitalService> selservices = t2Table.getItems();                        
                        LocalDateTime CHARGETIME = LocalDateTime.now();
                        HospitalCharge CHARGE = new HospitalCharge();
                        String CHARGENUM = StringKit.timeCode(CHARGETIME);

                        

                        CHARGE.setChargenumber(CHARGENUM);
                        CHARGE.setChargetime(CHARGETIME);
                        CHARGE.setChargeto(t2chargedtoF.getText());
                        CHARGE.setChargefacility("Laboratory");
                        CHARGE.setChargetype("Walk-In");
                        CHARGE.setUser(Care.getUser().getName());
                        CHARGE.setUser_id(Care.getUser().getId());
                        CHARGE.setPatient_id(0);
                        CHARGE.setCareto(t2chargedtoF.getText());
                        CHARGE.setChargenotes("Multiple Patient Laboratory Test");
                        
                        int rec_num = SQLTable.getMaxValue(LaboratoryTest.class, LaboratoryTest.LABTESTNUMBER);   


                        List<HospitalChargeItem> chargeItems = new ArrayList();
                        for(int i = 0;i < selservices.size();i++){
                            rec_num++;
                            HospitalService srv = selservices.get(i);
                            //Test
                            LaboratoryTest test = new LaboratoryTest();
                            test.setLabtestnumber((rec_num > 0)? String.valueOf(rec_num):"");
                            test.setPatient((String)srv.getResource("patientname"));
                            test.setGender((String)srv.getResource("patientgender"));
                            test.setAge(Integer.parseInt((String)srv.getResource("patientage")));
                            test.setTestname(srv.getDescription());
                            test.setTestcategory(srv.getCategory());
                            test.setTestgroup(srv.getGrp());
                            test.setTesttime(CHARGETIME);
                            test.setPhysician((String)srv.getResource("physician"));
                            test.setEncoder(Care.getUser().getName());
                            test.setUser_id(Care.getUser().getId());
                            test.setPatient_id(0);     
                            test.setHospitalservice_id(srv.getId());

                            int test_id = test.save(true);
                            if(test_id > 0){
                                //Charge
                                HospitalChargeItem labcharge = new HospitalChargeItem();
                                labcharge.setChargenumber(CHARGENUM);
                                labcharge.setChargetime(CHARGETIME);
                                labcharge.setDescription(srv.getCategory().substring(0,2)+"-"+srv.getDescription()+" / "+(String)srv.getResource("patientname"));
                                labcharge.setSelling(srv.getPrice());
                                labcharge.setQuantity(1);
                                labcharge.setService(true);
                                labcharge.setItemtype("Laboratory Charge");
                                labcharge.setFacility("Laboratory");
                                labcharge.setUser(Care.getUser().getName());
                                labcharge.setChargeto((String)srv.getResource("patientname"));
                                labcharge.setUser_id(Care.getUser().getId());
                                labcharge.setPatient_id(0);     
                                labcharge.setRecordtable(LaboratoryTest.TABLE_NAME);
                                labcharge.setRecordtableid(test_id);
                                labcharge.setCareto(t2chargedtoF.getText());
                                labcharge.setPhysician((String)srv.getResource("physician"));
                                labcharge.setVatable(false);
                                labcharge.calculateNet(false);
                                
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
                                postAction();
                                FXDialog.showMessageDialog(stackPane, "Success", "New Laboratory records has been registered!", FXDialog.SUCCESS);
                            });
                            CHARGE.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Laboratory", false);
                        }
                        
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            t2createBtn.setDisable(false);
                        });
                    }
                }
            };
            Care.process(task);
        }   
    }

    @FXML
    void saveSinglePatient(ActionEvent event) {
        if(t1patientF.validate()){
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            t1createBtn.setDisable(true);
                        });
                        Patient selPatient = null;
                        for(Patient p:patients){
                            if(p.getFullname().equals(t1patientF.getText())){
                                selPatient = p;
                                break;
                            }
                        }
                        if(selPatient != null && t1Table.getItems().size() > 0){
                            Map<String,String> opts = GlobalOption.getMap("General");
                            List<HospitalService> selservices = t1Table.getItems();                        
                            LocalDateTime CHARGETIME = LocalDateTime.now();
                            HospitalCharge CHARGE = new HospitalCharge();
                            String CHARGENUM = StringKit.timeCode(CHARGETIME);

                            Admission admission = ((selPatient.isAdmitted())? selPatient.getLatestAdmission():null);
                            String phys = t1physicianF.getText();

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
                                    labcharge.setPhysician(phys);
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
                                    postAction();
                                    FXDialog.showMessageDialog(stackPane, "Success", "New Laboratory records has been registered!", FXDialog.SUCCESS);
                                });
                                CHARGE.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Laboratory", false);

                            }
                        }
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            t1createBtn.setDisable(false);
                        });
                    }
                }
            };
            Care.process(task);
        }   
    }

    @FXML
    void t1addService(ActionEvent event) {
        if(t1testF.validate() && t1patientF.validate() && t1ageF.validate() && t1genderF.validate()){         
            HospitalService selSrv = t1testF.getSelectionModel().getSelectedItem();            
            if(selSrv != null){
                t1Table.getItems().add(selSrv.getModelClone());
                t1Table.refresh();                
                loadT1TableTotals();
            }else{
                t1errorLb.setText("Laboratory Service not found!");
            }
        }
    }
    
    @FXML
    void t2addService(ActionEvent event) {
        if(t2testF.validate() && t2patientF.validate() && t2ageF.validate() && t2genderF.validate() && t2chargedtoF.validate()){
            boolean found = false;
            String srvname = t2testF.getText();
            String patientname = t2patientF.getText();
            HospitalService selSrv = null;
            for(HospitalService srv:services){
                if(srvname.equals(srv.getCategory()+"-"+srv.getDescription())){
                    t2priceF.setText(NumberKit.toCurrency(srv.getPrice()));
                    selSrv = srv.getModelClone();
                    found = true;
                    break;
                }
            }
            
            
            
            if(found && selSrv != null){
                boolean patientfound = false;
                for(Patient pat:patients){
                    if(patientname.equals(pat.getFullname())){
                        selSrv.setPatienttemp(pat.getFullname());
                        selSrv.addResource("patientname", pat.getFullname());
                        selSrv.addResource("patientid", pat.getId());
                        selSrv.addResource("chargedto", t2chargedtoF.getText());
                        selSrv.addResource("physician", t2physicianF.getText());
                        patientfound = true;
                        break;
                    }
                }
                
                if(!patientfound){
                    selSrv.setPatienttemp(patientname);
                    selSrv.addResource("patientname", patientname);
                    selSrv.addResource("patientage", t2ageF.getText());
                    selSrv.addResource("patientgender", t2genderF.getSelectionModel().getSelectedItem());
                    selSrv.addResource("chargedto", t2chargedtoF.getText());
                    selSrv.addResource("physician", t2physicianF.getText());
                }
                
                t2Table.getItems().add(selSrv);
                t2Table.refresh();
                t2patientF.requestFocus();
                t2patientF.selectAll();
                loadT2TableTotals();
            }else{
                t2errorLb.setText("Laboratory Service not found!");
            }
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
            if(UI_CONTROLLER instanceof LaboratoryUXController){
                UI_CONTROLLER.reloadReferences(0);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setUIController(UIController controller) {
        try{
            UI_CONTROLLER = controller;
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setFormObject(Object obj) {
        this.record = obj;
    }

    @Override
    public Object getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return true;
    }

    @Override
    public void loadBindings() {
        try{
            
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
            
            t1Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t1Table, "Category", HospitalService::categoryProperty, false,130,130,130);
            FXTable.addColumn(t1Table, "Test", HospitalService::descriptionProperty, false);
            FXTable.addCurrencyColumn(t1Table, "Price", HospitalService::priceProperty, false,80,80,80);
            TableColumn actCol = FXTable.addColumn(t1Table, " ", HospitalService::descriptionProperty, false, 40, 40, 40);            
            
            FXTable.setList(t1Table, new ArrayList());
            
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
                                        t1Table.getItems().remove(row_data);
                                        t1Table.refresh();
                                        loadT1TableTotals();
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
            
            t2Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t2Table, "Patient", HospitalService::patienttempProperty, false,280,280,280);
            FXTable.addColumn(t2Table, "Category", HospitalService::categoryProperty, false,130,130,130);
            FXTable.addColumn(t2Table, "Test", HospitalService::descriptionProperty, false);
            FXTable.addCurrencyColumn(t2Table, "Price", HospitalService::priceProperty, false,80,80,80);
            TableColumn act2Col = FXTable.addColumn(t2Table, " ", HospitalService::descriptionProperty, false, 40, 40, 40);            
            
            FXTable.setList(t2Table, new ArrayList());
            
            act2Col.setCellFactory(column -> {
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
                                        t2Table.getItems().remove(row_data);
                                        t2Table.refresh();
                                        loadT2TableTotals();
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
            
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(t1patientF);
            FXField.addRequiredValidator(t2patientF);
            FXField.addRequiredValidator(t2chargedtoF);
            FXField.addRequiredValidator(t1testF);
            FXField.addRequiredValidator(t2testF);
            FXField.addIntegerValidator(t1ageF, 1, 150, 1);
            FXField.addIntegerValidator(t2ageF, 1, 150, 1);
            FXField.addRequiredValidator(t1genderF);
            FXField.addRequiredValidator(t2genderF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            persons = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Physician'");
            services = SQLTable.list(HospitalService.class, HospitalService.FACILITY+"='LABORATORY' ORDER BY "+HospitalService.CATEGORY+" ASC,"+HospitalService.DESCRIPTION+" ASC");
            patients = SQLTable.list(Patient.class);
            categories = SQLTable.distinct(HospitalService.class, HospitalService.CATEGORY,HospitalService.FACILITY+"='LABORATORY' ORDER BY "+HospitalService.CATEGORY+" ASC");
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            List<String> spersons = persons.stream().map(obj -> obj.getName()).collect(Collectors.toList());
            List<String> sservices = services.stream().map(obj -> obj.getCategory()+"-"+obj.getDescription()).collect(Collectors.toList());
            List<String> spatients = patients.stream().map(obj -> obj.getFullname()).collect(Collectors.toList());
            
            
            
            t1genderF.getItems().setAll("Male","Female");
            t2genderF.getItems().setAll("Male","Female");
            t1categF.getItems().setAll(categories);
            
            TextFields.bindAutoCompletion(t1patientF, spatients);
            TextFields.bindAutoCompletion(t1physicianF, spersons);
            
            TextFields.bindAutoCompletion(t2patientF, spatients);
            TextFields.bindAutoCompletion(t2physicianF, spersons);
            TextFields.bindAutoCompletion(t2testF, sservices);
            
            mainTabPane.getSelectionModel().select(singleTab);
            
            t1categF.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null && !newVal.isEmpty()){
                    Platform.runLater(()->{
                        List<HospitalService> t = new ArrayList();
                        for(HospitalService s:services){
                            if(newVal.equals(s.getCategory())){
                                t.add(s.getModelClone());
                            }
                        }
                        t1testF.getItems().setAll(t);
                        if(t.size()>0){
                            t1testF.getSelectionModel().selectFirst();
                        }
                    });
                }
            });
            
            t1testF.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null){
                    t1priceF.setText(NumberKit.toCurrency(newVal.getPrice()));
                }
            });
            
            if(categories.size() > 0){
                t1categF.getSelectionModel().selectFirst();
            }
            
            /*
            t1testF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal!=null && !newVal.isEmpty()){
                    boolean found = false;
                    for(HospitalService srv:services){
                        if(newVal.equals(srv.getCategory()+"-"+srv.getDescription())){
                            t1priceF.setText(NumberKit.toCurrency(srv.getPrice()));
                            found= true;
                            break;
                        }
                    }
                    if(found){
                        
                    }else{
                        t1priceF.setText("0.00");
                    }
                    t1errorLb.setText("");
                }
            });
            */
            
            t2testF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal!=null && !newVal.isEmpty()){
                    boolean found = false;
                    for(HospitalService srv:services){
                        if(newVal.equals(srv.getCategory()+"-"+srv.getDescription())){
                            t2priceF.setText(NumberKit.toCurrency(srv.getPrice()));
                            found= true;
                            break;
                        }
                    }
                    if(found){
                        
                    }else{
                        t2priceF.setText("0.00");
                    }
                    t2errorLb.setText("");
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/CreateLabChargeForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, new Object(), ui_controller, 900, 600);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    maskerPane = masker;
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(CreateLabChargeFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
    private void loadT1TableTotals(){
        try{
            List<HospitalService> srvs = t1Table.getItems();
            List<Double> prices = srvs.stream().map(obj->obj.getPrice()).collect(Collectors.toList());
            double total = prices.stream().collect(Collectors.summingDouble(Double::doubleValue));
            t1resultLb.setText("Total Tests : "+srvs.size());
            t1totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadT2TableTotals(){
        try{
            List<HospitalService> srvs = t2Table.getItems();
            List<Double> prices = srvs.stream().map(obj->obj.getPrice()).collect(Collectors.toList());
            double total = prices.stream().collect(Collectors.summingDouble(Double::doubleValue));
            t2resultLb.setText("Total Tests : "+srvs.size());
            t2totalLb.setText("Total Amount : "+NumberKit.toCurrency(total));
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
