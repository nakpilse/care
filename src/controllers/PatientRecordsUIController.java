package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Patient;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class PatientRecordsUIController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    private PatientInfoUIController patientInfoController = null;
    
    @FXML
    private JFXTabPane mainTabPane;
    
    @FXML
    private Tab recordsTab;

    @FXML
    private TableView<Patient> recordsTbl;

    @FXML
    private JFXTextField recordsearchF;
    
    @FXML
    private JFXButton recordsearchBtn;
    
    @FXML
    private Label recordsearchLbl;

    @FXML
    private Label recordsresultLbl;

    @FXML
    private BorderPane recordsPane;

    @FXML
    private Tab consultationsTab;

    @FXML
    private Tab admissionsTab;

    @FXML
    private Tab erTab;

    @FXML
    private Tab dischargeTab;

    @FXML
    private Tab newbornTab;

    @FXML
    private JFXToggleNode recordsMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode consultationsMenu;

    @FXML
    private JFXToggleNode admissionsMenu;

    @FXML
    private JFXToggleNode erMenu;

    @FXML
    private JFXToggleNode dischargeMenu;

    @FXML
    private JFXToggleNode newbornMenu;

    @FXML
    private JFXButton regnewbornBtn;

    @FXML
    private JFXButton regpatientBtn;
    
    
    @FXML
    void searchPatientMasterRecords(ActionEvent event) {
        try{
            if(!recordsearchF.getText().isEmpty()){
                FXTask searchTask = new FXTask() {
                @Override
                    protected void load() {
                        try{
                            Platform.runLater(()->{
                                recordsearchBtn.setDisable(true);
                            });
                            String searchVal = recordsearchF.getText();
                            List<Patient> records = SQLTable.list(Patient.class, Patient.FIRSTNAME+" LIKE '%"+searchVal+"%' OR "+Patient.MIDDLENAME+" LIKE '%"+searchVal+"%' OR "+Patient.LASTNAME+" LIKE '%"+searchVal+"%' OR "+Patient.ID+" LIKE '%"+searchVal+"%' OR "+Patient.CODE+" LIKE '%"+searchVal+"%'");
                            Platform.runLater(()->{
                                FXTable.setList(recordsTbl, records);
                                recordsresultLbl.setText("Results : "+records.size());
                            });
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally{
                            Platform.runLater(()->{
                                recordsearchBtn.setDisable(false);
                            });
                        }
                    }
                };
                Care.process(searchTask);
            }            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void loadAdmissions(ActionEvent event) {

    }

    @FXML
    void loadConsultations(ActionEvent event) {

    }

    @FXML
    void loadDischarged(ActionEvent event) {

    }

    @FXML
    void loadEmergency(ActionEvent event) {

    }

    @FXML
    void loadNewBorn(ActionEvent event) {

    }

    @FXML
    void loadRecords(ActionEvent event) {
        try{
            recordsPane.setCenter(null);
            loadRecordUIPatientInfoController();
            FXTable.setList(recordsTbl, new ArrayList());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void registerNewBorn(ActionEvent event) {

    }

    @FXML
    void registerPatient(ActionEvent event) {
        PatientRegistrationFormController.showDialog(mainStack, maskerPane, PatientRecordsUIController.this,regpatientBtn);
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
        try {
            if(val == 0){
                
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setMainStack(StackPane stackpane) {
        this.mainStack = stackpane;
    }

    @Override
    public StackPane getMainStack() {
       return mainStack;
    }

    @Override
    public void loadCustomizations() {
        try {
            
            mainTabPane.addEventFilter(KeyEvent.ANY, event -> {
                if (event.getCode().isArrowKey() && event.getTarget() == mainTabPane) {
                    event.consume();
                }
            });
            menuGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                if (newVal == null) {
                    oldVal.setSelected(true);
                }
            });
            
            loadMasterRecordCustomizations();
            
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {        
        
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
    
    private void loadRecordUIPatientInfoController(){
        try{
            FXMLLoader LOADER = new FXMLLoader(Care.class.getResource("/views/PatientInfoUI.fxml"));
            LOADER.setClassLoader(Care.CACHE_FXMLCLASSLOADER);
            LOADER.load();
            UIController ctrl = LOADER.getController();
            Parent parent = LOADER.getRoot();
            ctrl.setMainStack(mainStack);
            ctrl.setMaskerPane(maskerPane);
            ctrl.loadCustomizations();
            ctrl.loadResources();
            recordsPane.setCenter(parent);
            patientInfoController = (PatientInfoUIController) ctrl;
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            recordsMenu.fire();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadMasterRecordCustomizations(){
        try{
            //Records
            TableColumn patientCol = FXTable.addColumn(recordsTbl, "Patients", Patient::fullnameProperty, false);
            patientCol.setCellFactory(column -> {
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
                                if(row_data != null){
                                    VBox container = new VBox();
                                    container.setMinSize(310, 65);
                                    container.setMaxSize(1000, 65);
                                    container.setPrefSize(1000, 65);
                                    
                                    GlyphIcon genderIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(((row_data.getGender().equalsIgnoreCase("Male"))? FontAwesomeIcon.MALE:FontAwesomeIcon.FEMALE)).size("12px").build();
                                    Label name = new Label();
                                    name.textProperty().bind(row_data.fullnameProperty());
                                    name.setGraphic(genderIcon);
                                    name.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    name.setGraphicTextGap(6);
                                    
                                    GlyphIcon idIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.BARCODE).size("12px").build();
                                    Label id = new Label();
                                    id.textProperty().bind(row_data.idProperty().asString("%012d"));
                                    id.setGraphic(idIcon);
                                    id.setGraphicTextGap(5);
                                    
                                    
                                    GlyphIcon giftIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.GIFT).size("12px").build();
                                    Label birth = new Label();
                                    birth.setText(DateTimeKit.toProperDate(row_data.getBirthdate())+" / "+((row_data.getAge() <= 0)? "1 yr old.":row_data.getAge()+" yrs old."));
                                    birth.setGraphic(giftIcon);
                                    birth.setGraphicTextGap(8);
                                    
                                    container.getChildren().addAll(name,id,birth);
                                    VBox.setMargin(name, new Insets(0,0,0,2));
                                    VBox.setMargin(birth, new Insets(0,0,0,1));
                                    
                                    setGraphic(container);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
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
            
            recordsTbl.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null && patientInfoController != null){                    
                    Platform.runLater(()->{
                        Patient selPatient = newVal;
                        System.out.println("Selected : "+selPatient.getFullname());
                        patientInfoController.setFormObject(selPatient);
                        patientInfoController.setDisplayName(selPatient.getFullname());
                        patientInfoController.setDisplayBirth(selPatient.getBirthdate());
                        patientInfoController.setDisplayAge(selPatient.getAge());
                        patientInfoController.setDisplayID(selPatient.getId());                        
                    });                    
                }
            });
            
            recordsearchF.setOnKeyReleased(kyevt ->{
                if(kyevt.getCode() == KeyCode.ENTER){
                    recordsearchBtn.fire();
                }
            });
            
            recordsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
}
