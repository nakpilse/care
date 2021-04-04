/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import models.HospitalPersonel;
import models.Item;
import models.LaboratoryTest;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Nsoft
 */
public class LaboratoryTestFormController implements Initializable,FormController<LaboratoryTest> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private LaboratoryTest record = null;
    private List<HospitalPersonel> MEDTECHS = new ArrayList();
    private List<HospitalPersonel> PATHOLOGISTS = new ArrayList();
    private List<HospitalPersonel> PHYSICIANS = new ArrayList();

    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXTextField testnumF;

    @FXML
    private JFXDatePicker testdateF;

    @FXML
    private JFXTextField testnameF;

    @FXML
    private JFXTextField testcategF;

    @FXML
    private JFXTextField testgrpF;

    @FXML
    private JFXTextField physicianF;

    @FXML
    private JFXTextArea findingsF;

    @FXML
    private JFXTextArea remarksF;

    @FXML
    private JFXTextField medtechF;

    @FXML
    private JFXTextField pathoF;

    @FXML
    private JFXTextField orF;

    @FXML
    private JFXButton saveBtn;
    
    @FXML
    private JFXButton printBtn;

    @FXML
    private TableView<?> resultsTbl;
    
    @FXML
    private JFXTextField patientF;

    @FXML
    private JFXTextField ageF;

    @FXML
    private JFXTextField yearsF;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
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
                                closeBtn.setDisable(true);
                                saveBtn.setDisable(true);
                            });  
                            LocalTime t = (record.getTesttime() != null)? record.getTesttime().toLocalTime():LocalTime.now();
                            record.setTesttime(LocalDateTime.of(testdateF.getValue(), t));
                            if(record.update(true)){
                                Platform.runLater(()->{
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Successfull", "Labtest has been updated!", FXDialog.SUCCESS);
                                    postAction();                        
                                });
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Failure", "There has been a problem on server communication!", FXDialog.DANGER);                      
                                });
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
    void formPrint(ActionEvent event) {
        try{
            
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
            if(UI_CONTROLLER instanceof LaboratoryUXController){
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
    public void setFormObject(LaboratoryTest obj) {
        this.record = obj;
    }

    @Override
    public LaboratoryTest getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return testnumF.validate() && testdateF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            testnumF.textProperty().bindBidirectional(record.labtestnumberProperty());
            testnameF.textProperty().bindBidirectional(record.testnameProperty());
            testcategF.textProperty().bindBidirectional(record.testcategoryProperty());
            testgrpF.textProperty().bindBidirectional(record.testgroupProperty());
            physicianF.textProperty().bindBidirectional(record.physicianProperty());
            medtechF.textProperty().bindBidirectional(record.medicaltechnologistProperty());
            pathoF.textProperty().bindBidirectional(record.pathologistProperty());
            orF.textProperty().bindBidirectional(record.ornumberProperty());
            findingsF.textProperty().bindBidirectional(record.findingsProperty());
            remarksF.textProperty().bindBidirectional(record.remarksProperty());
            patientF.textProperty().bindBidirectional(record.patientProperty());
            ageF.textProperty().bindBidirectional(record.ageProperty(),new NumberConverter());
            yearsF.textProperty().bindBidirectional(record.agestringProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(testnumF);
            FXField.addRequiredValidator(testdateF);
            FXField.addDuplicateValidator(testnumF, record, LaboratoryTest.LABTESTNUMBER);
            FXField.addFocusValidationListener(testnumF,testdateF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            MEDTECHS = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Medical Technologist' ORDER BY "+HospitalPersonel.NAME+" ASC");
            PATHOLOGISTS = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Pathologist' ORDER BY "+HospitalPersonel.NAME+" ASC");
            PHYSICIANS = SQLTable.list(HospitalPersonel.class, HospitalPersonel.OCCUPATION+"='Physician' ORDER BY "+HospitalPersonel.NAME+" ASC");
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            if(record.getTesttime() != null){
                testdateF.setValue(record.getTesttime().toLocalDate());
            }
            
            List<String> med_names =  MEDTECHS.stream().map(HospitalPersonel::getName).collect(Collectors.toList());
            List<String> pat_names =  PATHOLOGISTS.stream().map(HospitalPersonel::getName).collect(Collectors.toList());
            List<String> phy_names =  PHYSICIANS.stream().map(HospitalPersonel::getName).collect(Collectors.toList());
            
            TextFields.bindAutoCompletion(medtechF, med_names);
            TextFields.bindAutoCompletion(pathoF, pat_names);
            TextFields.bindAutoCompletion(physicianF, phy_names);
            
            medtechF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null && !newVal.isEmpty()){
                    String name = newVal;
                    boolean found = false;
                    for(HospitalPersonel rec:MEDTECHS){
                        if(rec.getName().equals(newVal)){
                            record.setMedicaltechnologistlic(rec.getLicensenumber());
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        record.setMedicaltechnologistlic("");
                    }
                }
            });
            
            pathoF.textProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null && !newVal.isEmpty()){
                    String name = newVal;
                    boolean found = false;
                    for(HospitalPersonel rec:PATHOLOGISTS){
                        if(rec.getName().equals(newVal)){
                            record.setPathologistlic(rec.getLicensenumber());
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        record.setPathologistlic("");
                    }
                }
            });
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(LaboratoryTest item,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/LaboratoryTestForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, item, ui_controller, 900, 650);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(LaboratoryTestFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
    
    
}
