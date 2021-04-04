package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Admission;
import models.Bed;
import models.HospitalCharge;
import models.HospitalPersonel;
import models.LaboratoryTest;
import models.Patient;
import models.RadiologyTest;
import models.Room;
import models.RoomRecord;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class AdmissionFormController implements Initializable,UIController,FormController<Admission> {    
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;    
    private static MaskerPane maskerPane;
    private Admission record = null;
    private List<HospitalPersonel> PHYSICIANS;
    private boolean isDischarging = false;
    private LocalDateTime dischargedTime = null;

    @FXML
    private TitledPane preadminPane;
    
    @FXML
    private TitledPane finaldiagPane;
    
    @FXML
    private TitledPane dischargePane;
    
    @FXML
    private Accordion admissionAccordion;
    
    @FXML
    private Label titleLbl;

    @FXML
    private Tab admissionTab;

    @FXML
    private JFXTextField admissionnumF;

    @FXML
    private JFXTextField admissionphysicianF;

    @FXML
    private JFXDatePicker admissiondateF;

    @FXML
    private JFXTimePicker admissiontimeF;

    @FXML
    private JFXTextField admittedbyF;

    @FXML
    private JFXTextField relationF;

    @FXML
    private JFXTextArea padmissioncompF;

    @FXML
    private JFXTextArea padmissionconF;

    @FXML
    private JFXTextArea preadmissiondiagF;

    @FXML
    private JFXDatePicker dischargeddateF;

    @FXML
    private JFXTimePicker dischargedtimeF;

    @FXML
    private JFXTextField dischargedbyF;

    @FXML
    private JFXTextArea provisionaldiagF;

    @FXML
    private JFXTextArea dischargedsumF;

    @FXML
    private JFXTextArea dischargenoteF;

    @FXML
    private JFXTextArea finaldiagnosisF;

    @FXML
    private JFXTextArea otherdiagnosisF;

    @FXML
    private JFXTextArea caseF;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

    @FXML
    private Tab roomTab;

    @FXML
    private TableView<RoomRecord> roomsTbl;

    @FXML
    private Tab physicianTab;

    @FXML
    private TableView<?> physiciansTbl;

    @FXML
    private Tab laboratoryTab;

    @FXML
    private TableView<LaboratoryTest> laboratoryTbl;

    @FXML
    private Tab radiologyTab;

    @FXML
    private TableView<RadiologyTest> radiologyTbl;

    @FXML
    private Tab doctorsplanTab;

    @FXML
    private TableView<?> doctorsplanTbl;

    @FXML
    private Tab proceduresTab;

    @FXML
    private TableView<?> proceduresTbl;

    @FXML
    private Tab medicationsTab;

    @FXML
    private TableView<?> medicationsTbl;

    @FXML
    private Tab chargesTab;

    @FXML
    private TableView<HospitalCharge> chargesTbl;

    @FXML
    private JFXToggleNode admissionMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode roomsMenu;

    @FXML
    private JFXToggleNode physiciansMenu;

    @FXML
    private JFXToggleNode laboratoryMenu;

    @FXML
    private JFXToggleNode radiologyMenu;

    @FXML
    private JFXToggleNode doctorspanMenu;

    @FXML
    private JFXToggleNode proceduresMenu;

    @FXML
    private JFXToggleNode medicationsMenu;

    @FXML
    private JFXToggleNode chargesMenu;

    @FXML
    private JFXButton closeBtn;
    
    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
        postAction();
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            if(isFieldInputsValid()){
                FXTask task = new FXTask() {
                    @Override
                    protected void load() {
                        try {                        
                            Platform.runLater(() -> {
                                saveBtn.setDisable(true);
                            });
                            record.setAdmissiontime(LocalDateTime.of(admissiondateF.getValue(), admissiontimeF.getValue()));
                            for(int i =0;i < PHYSICIANS.size();i++){
                                if(PHYSICIANS.get(i).getName().equalsIgnoreCase(record.getPhysician())){
                                    record.setHospitalpersonel_id(PHYSICIANS.get(i).getId());
                                    break;
                                }
                            }
                            if(record.update(true)){
                                if(isDischarging && dischargedTime == null && record.getDischargetime() != null){
                                    List<RoomRecord> roomrecs = SQLTable.list(RoomRecord.class, RoomRecord.ADMISSION_ID+"="+record.getId());
                                    roomrecs.stream().forEach(rrec->{
                                        if(rrec.getTimeend() == null){
                                            rrec.setTimeend(record.getDischargetime());
                                            rrec.update();
                                        }
                                    });
                                    java.sql.Timestamp recadmin = java.sql.Timestamp.valueOf(record.getAdmissiontime());
                                    SQLTable.execute("UPDATE "+Patient.TABLE_NAME+" SET "+Patient.ROOM+"='',"+Patient.BED+"='',"+Patient.RECENTADMISSION+"='"+recadmin+"',"+Patient.ADMITTED+"=0 WHERE "+Patient.ID+"="+record.getPatient_id());
                                    Admission.updateDischargedCounter();
                                }
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Successful", "Admission Record has been updated", FXDialog.SUCCESS);
                                });                                
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Connection Error", "Failed to communicate to database server!", FXDialog.DANGER);
                                });
                            }
                            
                        } catch (Exception er) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        } finally {
                            Platform.runLater(() -> {
                                saveBtn.setDisable(false);
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
    void loadAdmissionMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(admissionTab);
    }

    @FXML
    void loadChargesMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(chargesTab);
        loadChargeRecords(null);
    }

    @FXML
    void loadDoctorsPlanMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(doctorsplanTab);
    }

    @FXML
    void loadLaboratoryMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(laboratoryTab);
        loadLaboratoryRecords(null);
    }

    @FXML
    void loadMedicationsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(medicationsTab);
    }

    @FXML
    void loadPhysiciansMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(physicianTab);
    }

    @FXML
    void loadProceduresMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(proceduresTab);
    }

    @FXML
    void loadRadiologyMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(radiologyTab);
        loadRadiologyRecords(null);
    }

    @FXML
    void loadRoomsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(roomTab);
        loadRoomRecords(null);
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
            if(UI_CONTROLLER instanceof AdmissionUXController){
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
    public void setFormObject(Admission obj) {
        this.record = obj;
    }

    @Override
    public Admission getFormObject() {
        return this.record;
    }

    @Override
    public boolean isFieldInputsValid() {
        if(admissionnumF.validate() == false || admissiondateF.validate() == false && admissionphysicianF.validate() == false && admissiontimeF.validate() == false){
            preadminPane.setExpanded(true);            
            return false;
        }
        return true;
    }

    @Override
    public void loadBindings() {
        try{
            admissionnumF.textProperty().bindBidirectional(record.admissionnumberProperty());
            admissionphysicianF.textProperty().bindBidirectional(record.physicianProperty());
            admittedbyF.textProperty().bindBidirectional(record.admittedbyProperty());
            relationF.textProperty().bindBidirectional(record.admittedbyrelationProperty());
            padmissioncompF.textProperty().bindBidirectional(record.preadmissioncomplainsProperty());
            padmissionconF.textProperty().bindBidirectional(record.preadmissionconditionProperty());
            preadmissiondiagF.textProperty().bindBidirectional(record.preadmissiondiagnosisProperty());
            dischargedbyF.textProperty().bindBidirectional(record.dischargedbyProperty());
            dischargedsumF.textProperty().bindBidirectional(record.dischargesummaryProperty());
            dischargenoteF.textProperty().bindBidirectional(record.dischargenotesProperty());
            provisionaldiagF.textProperty().bindBidirectional(record.provisionaldiagnosisProperty());
            finaldiagnosisF.textProperty().bindBidirectional(record.finaldiagnosisProperty());
            otherdiagnosisF.textProperty().bindBidirectional(record.otherdiagnosisProperty());
            caseF.textProperty().bindBidirectional(record.admissioncaseProperty());
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
            
            
            loadRoomTabCustomizations();
            loadHospitalChargesTabCustomizations();
            loadLaboratoryTabCustomizations();
            loadRadiologyTabCustomizations();
            
            if(record.getDischargetime() == null){
                dischargeddateF.valueProperty().addListener((obs,oldVal,newVal)->{
                    if(oldVal == null && newVal != null){
                        JFXButton confBtn = new JFXButton("Confirm Discharge");
                        confBtn.getStyleClass().add("btn-success");
                        JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Discharge Confirmation", new Label("You are about to mark this record for discharge!"), FXDialog.DANGER, confBtn);
                        confBtn.setOnAction(confEvt ->{
                            isDischarging = true;
                            dialog.close();
                        });
                    }else if(oldVal != null && newVal == null){
                        isDischarging = true;
                    }

                    if(newVal == null){
                        record.setDischargetime(null);
                    }else{
                        if(dischargedtimeF.getValue() != null){
                            record.setDischargetime(LocalDateTime.of(newVal, dischargedtimeF.getValue()));
                        }else{
                            record.setDischargetime(LocalDateTime.of(newVal, LocalTime.now()));
                        }
                    }
                });

                dischargedtimeF.valueProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null){
                        if(dischargeddateF.getValue() != null){
                            record.setDischargetime(LocalDateTime.of(dischargeddateF.getValue(), newVal));
                        }
                    }
                });
            }
            
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(admissionnumF);
            FXField.addRequiredValidator(admissionphysicianF);
            FXField.addRequiredValidator(admittedbyF);
            FXField.addRequiredValidator(admissiondateF);
            FXField.addRequiredValidator(admissiontimeF);
            FXField.addFocusValidationListener(admissionnumF,admissionphysicianF,admittedbyF,admissiondateF,admissiontimeF);
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            PHYSICIANS = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Physician'");
            List<String> plist = new ArrayList();
            PHYSICIANS.stream().forEach(phy->{
                plist.add(phy.getName());
            });
            
            TextFields.bindAutoCompletion(admissionphysicianF, plist);
            
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            Thread.sleep(1000);
            Platform.runLater(()->{
                loadRoomTableActions();
                loadPhysicianTableActions();
                loadLaboratoryTableActions();
                loadRadiologyTableActions();
                loadPlansTableActions();
                loadProceduresTableActions();
                loadMedicationTableActions();
                loadChargesTableActions();
                preadminPane.setExpanded(true);
            });
            
            dischargedTime = ((record.getDischargetime() == null)? null:LocalDateTime.of(record.getDischargetime().toLocalDate(), record.getDischargetime().toLocalTime()));
            
            if(record.getAdmissiontime() != null){
                admissiondateF.setValue(record.getAdmissiontime().toLocalDate());
                admissiontimeF.setValue(record.getAdmissiontime().toLocalTime());
            }
            
            if(record.getDischargetime() != null){
                dischargeddateF.setValue(record.getDischargetime().toLocalDate());
                dischargedtimeF.setValue(record.getDischargetime().toLocalTime());
            }
            titleLbl.setText(record.getPatientname() +" - "+record.getGender()+" "+record.getAge()+" "+record.getAgestring());
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    @Override
    public void reloadReferences(int val) {
        try{
            
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
    
    public static FXDialogTask showDialog(Admission data,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                dialog.setOverlayClose(false);
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/AdmissionForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog,data, ui_controller, 1200, 600);
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
    
    public void loadRoomTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Room Record");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    List<RoomRecord> roomrecs = roomsTbl.getItems();
                    boolean all_ended = true;
                    for(int i = 0;i < roomrecs.size();i++){
                        if(roomrecs.get(i).getTimeend()==null){
                            all_ended = false;
                            break;
                        }
                    }
                    if(all_ended){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {
                                try {                        
                                    List<Patient> pats = SQLTable.list(Patient.class, Patient.ROOM+"=''");
                                    List<Room> rooms = SQLTable.list(Room.class);
                                    List<Bed> beds = SQLTable.list(Bed.class);
                                    if(rooms.size() > 0){
                                        VBox content = new VBox();
                                        content.setMaxWidth(500);
                                        content.setMaxHeight(600);
                                        content.setAlignment(Pos.CENTER);
                                        content.setSpacing(35);
                                        content.setPadding(new Insets(35, 25, 25, 25));

                                        JFXComboBox<Room> roomF = new JFXComboBox();
                                        roomF.setMinHeight(28);
                                        roomF.setMinWidth(250);
                                        roomF.setMaxWidth(250);
                                        roomF.setPrefWidth(250);
                                        roomF.setPromptText("Select Room");

                                        JFXComboBox<Bed> bedF = new JFXComboBox();
                                        bedF.setMinHeight(28);
                                        bedF.setMinWidth(250);
                                        bedF.setMaxWidth(250);
                                        bedF.setPrefWidth(250);
                                        roomF.setPromptText("Select Bed");

                                        JFXDatePicker dfrom = new JFXDatePicker();
                                        dfrom.setPromptText("Date");
                                        dfrom.setMinHeight(28);
                                        dfrom.setMinWidth(250);
                                        dfrom.setMaxWidth(250);
                                        dfrom.setPrefWidth(250);
                                        dfrom.setValue(LocalDate.now());

                                        JFXTimePicker tfrom = new JFXTimePicker();
                                        tfrom.setPromptText("Time");
                                        tfrom.setMinHeight(28);
                                        tfrom.setMinWidth(250);
                                        tfrom.setMaxWidth(250);
                                        tfrom.setPrefWidth(250);
                                        tfrom.setValue(LocalTime.now());

                                        FXField.addRequiredValidator(roomF);
                                        FXField.addRequiredValidator(bedF);
                                        FXField.addRequiredValidator(dfrom);
                                        FXField.addRequiredValidator(tfrom);
                                        FXField.addFocusValidationListener(roomF, bedF,dfrom,tfrom);

                                        roomF.getItems().setAll(rooms);
                                        roomF.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                                            if(newVal!= null){
                                                List<Bed> roomBeds = new ArrayList();
                                                beds.stream().forEach(bed->{
                                                    if(newVal.getId() == bed.getRoom_id()){
                                                        roomBeds.add(bed.getModelClone());
                                                    }
                                                });

                                                roomBeds.stream().forEach(bed->{
                                                    pats.stream().forEach(patient->{
                                                        if(patient.getRoom().equals(newVal.getName()) && patient.getBed().equals(bed.getName())){
                                                            roomBeds.remove(bed);
                                                        }
                                                    });
                                                });

                                                if(roomBeds.size() > 0){
                                                    bedF.getItems().setAll(roomBeds);
                                                }else{
                                                    bedF.getItems().clear();
                                                }
                                            }
                                        });


                                        content.getChildren().addAll(roomF, bedF,dfrom,tfrom);

                                        JFXButton filter = new JFXButton("Confirm");
                                        filter.getStyleClass().add("btn-info");

                                        Platform.runLater(()->{
                                            JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Add Room Record", content, FXDialog.DANGER, filter);
                                            filter.setOnAction(filterEvt -> {
                                                if(roomF.validate() && bedF.validate() && dfrom.validate() && tfrom.validate()){
                                                    LocalDateTime selTime = LocalDateTime.of(dfrom.getValue(), tfrom.getValue());
                                                    RoomRecord roomrec = new RoomRecord();
                                                    roomrec.setRoom(roomF.getValue().getName());
                                                    roomrec.setBed(bedF.getValue().getName());
                                                    roomrec.setTimestart(selTime);
                                                    roomrec.setBasis(bedF.getValue().getBasis());
                                                    roomrec.setRate(bedF.getValue().getRate()); 
                                                    roomrec.setEncoder(Care.getUser().getName());
                                                    roomrec.setAdmission_id(record.getId());

                                                    if(roomrec.save() > 0){
                                                        SQLTable.execute("UPDATE "+Patient.TABLE_NAME+" SET "+Patient.ROOM+"='"+roomrec.getRoom()+"',"+Patient.BED+"='"+roomrec.getBed()+"' WHERE "+Patient.ID+"="+record.getPatient_id());
                                                        loadRoomRecords(null);
                                                        dialog.close();
                                                    }
                                                }
                                            });
                                        });


                                    }
                                } catch (Exception er) {
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                } 
                            }
                        };
                        Care.process(task);
                    }else{
                        FXDialog.showMessageDialog(stackPane, "Warning", "Cannot add new room record please end previous room records!", FXDialog.DANGER);
                    }
                }                
            });    
            FXTable.addCustomTableMenu(roomsTbl, addLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadPhysicianTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Physician");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            });            
            FXTable.addCustomTableMenu(physiciansTbl, addLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadLaboratoryTableActions(){
        try{
            GlyphIcon printIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PRINT).size("13px").build();
            Label printLb = new Label("Print Selected");
            printLb.setCursor(Cursor.HAND);
            printLb.setGraphic(printIco);
            printLb.setOnMouseClicked(evt -> {
                
            });            
            FXTable.addCustomTableMenu(laboratoryTbl, printLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public void loadRadiologyTableActions(){
        try{
            GlyphIcon printIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PRINT).size("13px").build();
            Label printLb = new Label("Print Selected");
            printLb.setCursor(Cursor.HAND);
            printLb.setGraphic(printIco);
            printLb.setOnMouseClicked(evt -> {
                
            });            
            FXTable.addCustomTableMenu(radiologyTbl, printLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public void loadPlansTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Plan");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            });            
            FXTable.addCustomTableMenu(doctorsplanTbl, addLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadProceduresTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Procedures");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            });            
            FXTable.addCustomTableMenu(proceduresTbl, addLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadMedicationTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Medications");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            });            
            FXTable.addCustomTableMenu(medicationsTbl, addLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public void loadChargesTableActions(){
        try{
            GlyphIcon addIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Hospital Charge");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIco);
            addLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            });    
            GlyphIcon phIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label phLb = new Label("Add Pharmacy Charge");
            phLb.setCursor(Cursor.HAND);
            phLb.setGraphic(phIco);
            phLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            }); 
            GlyphIcon spIco = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label spLb = new Label("Add Pharmacy Charge");
            spLb.setCursor(Cursor.HAND);
            spLb.setGraphic(spIco);
            spLb.setOnMouseClicked(evt -> {
                if(record.getDischargetime() == null){
                    
                }
            }); 
            FXTable.addCustomTableMenu(chargesTbl, addLb,phLb,spLb);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadRoomRecords(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(roomsTbl, new ArrayList());
                            roomsTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<RoomRecord> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(RoomRecord.class, RoomRecord.ADMISSION_ID + "='"+record.getId()+"' ORDER BY " + RoomRecord.TIMESTART + " ASC");
                        } else {
                            records = SQLTable.list(RoomRecord.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FXTable.setList(roomsTbl, records);
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            roomsTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadLaboratoryRecords(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(laboratoryTbl, new ArrayList());
                            laboratoryTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<LaboratoryTest> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(LaboratoryTest.class, LaboratoryTest.ADMISSION_ID + "='"+record.getId()+"' ORDER BY " + LaboratoryTest.TESTTIME + " ASC");
                        } else {
                            records = SQLTable.list(LaboratoryTest.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FXTable.setList(laboratoryTbl, records);
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            laboratoryTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
        
    private void loadRadiologyRecords(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(radiologyTbl, new ArrayList());
                            radiologyTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<RadiologyTest> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(RadiologyTest.class, RadiologyTest.ADMISSION_ID + "='"+record.getId()+"' ORDER BY " + RadiologyTest.TESTTIME + " ASC");
                        } else {
                            records = SQLTable.list(RadiologyTest.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FXTable.setList(radiologyTbl, records);
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            radiologyTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadChargeRecords(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(chargesTbl, new ArrayList());
                            chargesTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<HospitalCharge> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalCharge.class, HospitalCharge.ADMISSION_ID + "='"+record.getId()+"' ORDER BY " + HospitalCharge.CHARGETIME + " ASC");
                        } else {
                            records = SQLTable.list(HospitalCharge.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FXTable.setList(chargesTbl, records);
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            chargesTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRoomTabCustomizations(){
        try{            
            roomsTbl.setEditable(false);
            roomsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(roomsTbl);
            FXTable.addColumn(roomsTbl, "Room", RoomRecord::roomProperty, false);
            FXTable.addColumn(roomsTbl, "Bed", RoomRecord::bedProperty, false);
            FXTable.addColumn(roomsTbl, "Rate", RoomRecord::rateProperty, false);
            FXTable.addColumn(roomsTbl, "Basis", RoomRecord::basisProperty, false);
            TableColumn occuCol = FXTable.addColumn(roomsTbl, "Occupied", RoomRecord::timestartProperty, false);
            TableColumn unoccuCol = FXTable.addColumn(roomsTbl, "UnOccupied", RoomRecord::timeendProperty, false);
            TableColumn actCol = FXTable.addColumn(roomsTbl, "Actions", RoomRecord::roomProperty, false,112,112,112);
            
            FXTable.setTimestampColumn(occuCol);
            FXTable.setTimestampColumn(unoccuCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<RoomRecord, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                RoomRecord row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(158, 36);
                                    container.setMaxSize(158, 36);
                                    container.setPrefSize(158, 36);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SIGN_OUT, "16px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(600);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(35);
                                            content.setPadding(new Insets(35, 25, 25, 25));

                                            JFXDatePicker dfrom = new JFXDatePicker();
                                            dfrom.setPromptText("Date");
                                            dfrom.setMinHeight(28);
                                            dfrom.setMinWidth(250);
                                            dfrom.setMaxWidth(250);
                                            dfrom.setPrefWidth(250);
                                            dfrom.setValue(LocalDate.now());

                                            JFXTimePicker tfrom = new JFXTimePicker();
                                            tfrom.setPromptText("Time");
                                            tfrom.setMinHeight(28);
                                            tfrom.setMinWidth(250);
                                            tfrom.setMaxWidth(250);
                                            tfrom.setPrefWidth(250);
                                            tfrom.setValue(LocalTime.now());
                                            
                                            Label warning = new Label("Date cannot be before occupation date!");
                                            warning.setTextFill(Color.RED);
                                            warning.setMinHeight(28);
                                            warning.setMinWidth(250);
                                            warning.setMaxWidth(250);
                                            warning.setPrefWidth(250);
                                            warning.setVisible(false);

                                            
                                            FXField.addRequiredValidator(dfrom);
                                            FXField.addRequiredValidator(tfrom);
                                            FXField.addFocusValidationListener(dfrom,tfrom);

                                            content.getChildren().addAll(dfrom, tfrom,warning);

                                            JFXButton filter = new JFXButton("Unoccupy");
                                            filter.getStyleClass().add("btn-info");

                                            JFXDialog dialog = FXDialog.showConfirmDialog(stackPane, "Unoccupy Room", content, FXDialog.DANGER, filter);
                                            filter.setOnAction(filterEvt -> {
                                                if(dfrom.validate() && tfrom.validate()){
                                                    LocalDateTime endTime = LocalDateTime.of(dfrom.getValue(), tfrom.getValue());
                                                    if(endTime.isAfter(row_data.getTimestart())){
                                                        RoomRecord tmp_rec = row_data.getModelClone();
                                                        tmp_rec.setTimeend(endTime);
                                                        if(tmp_rec.update()){
                                                            SQLTable.execute("UPDATE "+Patient.TABLE_NAME+" SET "+Patient.ROOM+"='',"+Patient.BED+"='' WHERE "+Patient.ID+"="+record.getPatient_id());
                                                            loadRoomRecords(null);
                                                            dialog.close();
                                                        }
                                                    }else{
                                                        warning.setVisible(true);
                                                    }
                                                }
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });
                                    viewBtn.setTooltip(new Tooltip("Unoccupy"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(stackPane, "Confirm", new Label("Do you want to delete this record?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    if(row_data.getTimeend() != null){
                                        viewBtn.setDisable(true);
                                    }
                                    
                                    if(record.getDischargetime() != null){
                                        viewBtn.setDisable(true);
                                        edtBtn.setDisable(true);
                                        delBtn.setDisable(true);
                                    }

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
    
    private void loadHospitalChargesTabCustomizations(){
        try{            
            chargesTbl.setEditable(false);
            chargesTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(chargesTbl);
            
            TableColumn timeCol = FXTable.addColumn(chargesTbl, "Time", HospitalCharge::chargetimeProperty, false,135,135,135);
            FXTable.addColumn(chargesTbl, "Charged From", HospitalCharge::chargefacilityProperty, false);
            FXTable.addColumn(chargesTbl, "Encoder", HospitalCharge::userProperty, false);
            FXTable.addColumn(chargesTbl, "Voided", HospitalCharge::voidedProperty, false);
            FXTable.addColumn(chargesTbl, "Total Amount", HospitalCharge::netsalesProperty, false);
            TableColumn actCol = FXTable.addColumn(chargesTbl, "Actions", HospitalCharge::userProperty, false,72,72,72);
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
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        
                                    });
                                    viewBtn.setTooltip(new Tooltip("View Item"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Item"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    if(record.getDischargetime() != null){
                                        viewBtn.setDisable(true);
                                    }

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(viewBtn, edtBtn);

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
    
    private void loadLaboratoryTabCustomizations(){
        try{
            laboratoryTbl.setEditable(true);
            laboratoryTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            TableColumn selCol = FXTable.addColumn(laboratoryTbl, " ", LaboratoryTest::selectedProperty, false, 45, 45, 45);
            TableColumn timeCol = FXTable.addColumn(laboratoryTbl, "Timestamp", LaboratoryTest::testtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(laboratoryTbl, "Test", LaboratoryTest::testnameProperty, false);
            FXTable.addColumn(laboratoryTbl, "Category", LaboratoryTest::testcategoryProperty, false);
            FXTable.addColumn(laboratoryTbl, "Encoder", LaboratoryTest::encoderProperty, false);
            TableColumn actCol = FXTable.addColumn(laboratoryTbl, " ", LaboratoryTest::patientProperty, false, 44, 44, 44);
            
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
                                    container.setMinSize(40, 40);
                                    container.setMaxSize(40, 40);
                                    container.setPrefSize(40, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRadiologyTabCustomizations(){
        try{
            radiologyTbl.setEditable(true);
            radiologyTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            TableColumn selCol = FXTable.addColumn(radiologyTbl, " ", RadiologyTest::selectedProperty, false, 45, 45, 45);
            TableColumn timeCol = FXTable.addColumn(radiologyTbl, "Timestamp", RadiologyTest::testtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(radiologyTbl, "Examination", RadiologyTest::testnameProperty, false);
            FXTable.addColumn(radiologyTbl, "Category", RadiologyTest::testcategoryProperty, false);
            FXTable.addColumn(radiologyTbl, "Encoder", RadiologyTest::encoderProperty, false);
            TableColumn actCol = FXTable.addColumn(radiologyTbl, " ", RadiologyTest::patientProperty, false, 44, 44, 44);
            
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
                                    container.setMinSize(40, 40);
                                    container.setMaxSize(40, 40);
                                    container.setPrefSize(40, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
