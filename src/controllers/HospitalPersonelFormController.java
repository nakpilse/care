package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import models.HospitalPersonel;
import models.Patient;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author Duskmourne
 */
public class HospitalPersonelFormController implements Initializable, FormController<HospitalPersonel>{
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private HospitalPersonel record = null;
    
    @FXML
    private JFXTextField nameF;

    @FXML
    private JFXDatePicker bdateF;

    @FXML
    private JFXButton bdateBtn;

    @FXML
    private JFXComboBox<String> genderF;

    @FXML
    private JFXTextField occupationF;

    @FXML
    private JFXTextField specializationF;

    @FXML
    private JFXTextField licenseF;

    @FXML
    private JFXTextField consultationF;

    @FXML
    private JFXTextField emailF;

    @FXML
    private JFXTextField mobileF;

    @FXML
    private JFXTextField phoneF;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

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
                            });
                            if(record.getId()<=0){
                                if(record.save(true) > 0){
                                    Platform.runLater(()->{       
                                        dialog.close();
                                        FXDialog.showMessageDialog(stackPane, "Success", "New personel has been registered!", FXDialog.SUCCESS);
                                    });
                                    postAction();
                                }else{
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Error", "Failed to save new personel!", FXDialog.DANGER);
                                    });
                                }
                            }else{
                                if(record.update(true)){
                                    Platform.runLater(()->{       
                                        dialog.close();
                                        FXDialog.showMessageDialog(stackPane, "Success", "personel has been updated!", FXDialog.SUCCESS);
                                    });
                                    postAction();
                                }else{
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(stackPane, "Error", "Failed to update personel!", FXDialog.DANGER);
                                    });
                                }
                            }
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally{
                            Platform.runLater(()->{                                
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void postAction() {
        try{
            if(UI_CONTROLLER instanceof HospitalPersonelsUIController){
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
    public void setFormObject(HospitalPersonel obj) {
        this.record = obj;
    }

    @Override
    public HospitalPersonel getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return nameF.validate() && bdateF.validate() && genderF.validate() && occupationF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            nameF.textProperty().bindBidirectional(record.nameProperty());
            bdateF.valueProperty().bindBidirectional(record.birthdateProperty());
            genderF.valueProperty().bindBidirectional(record.genderProperty());
            occupationF.textProperty().bindBidirectional(record.occupationProperty());
            specializationF.textProperty().bindBidirectional(record.specializationProperty());
            licenseF.textProperty().bindBidirectional(record.licensenumberProperty());
            consultationF.textProperty().bindBidirectional(record.consultationfeeProperty(), new NumberConverter());
            emailF.textProperty().bindBidirectional(record.emailProperty());
            mobileF.textProperty().bindBidirectional(record.mobileProperty());
            phoneF.textProperty().bindBidirectional(record.landlineProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            genderF.getItems().setAll("Male","Female");
            List<String> occups = Arrays.asList(new String[]{
                "Physician",
                "Accounting Staff",
                "Accounting Manager",
                "Pharmacy Staff",
                "Pharmacy Manager",
                "Pharmacist",
                "Medical Records Staff",
                "OPD Staff",
                "Nurse",
                "Head Nurse",
                "Medical Technologist",
                "Radiologist",
                "Pathologist",                
            });
            TextFields.bindAutoCompletion(occupationF, occups);
            bdateBtn.setOnAction(evt -> {
                Platform.runLater(() -> {
                    bdateF.show();
                });
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(nameF);
            FXField.addRequiredValidator(bdateF);
            FXField.addRequiredValidator(genderF);
            FXField.addRequiredValidator(occupationF);            
            FXField.addDuplicateValidator(nameF, record, HospitalPersonel.NAME);
            
            FXField.addFocusValidationListener(nameF,bdateF,genderF,occupationF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            
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
    
    public static FXDialogTask showDialog(HospitalPersonel obj,StackPane stackpane, MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/HospitalPersonelForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, obj, ui_controller, 450, 600);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(HospitalPersonelFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
