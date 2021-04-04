package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import models.HospitalService;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.Facility;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ServiceFormController implements Initializable,FormController<HospitalService> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private HospitalService record = null;

    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXTextField descF;

    @FXML
    private JFXTextField facilityF;

    @FXML
    private JFXTextField categoryF;

    @FXML
    private JFXTextField grpF;

    @FXML
    private JFXTextField priceF;

    @FXML
    private JFXButton saveBtn;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            if(isFieldInputsValid()){
                JFXButton btn = new JFXButton("Yes, I am certain!");
                btn.getStyleClass().add("btn-success");
                JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Confirmation", new Label("Saving "+record.getDescription()+" with price="+record.getPrice()+" ?"), FXDialog.WARNING,btn);
                btn.setOnAction(evt->{
                    dl.close();
                    Care.process(saveTask);
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private final FXTask saveTask = new FXTask() {
        @Override
        protected void load() {
            try{
                Platform.runLater(()->{
                    closeBtn.setDisable(true);
                    saveBtn.setDisable(true);
                });    
                if(record.getId() > 0){
                    if(record.update()){
                        SystemLog log = new SystemLog();
                        log.setLogtable(HospitalService.TABLE_NAME);
                        log.setLogtableid(record.getId());
                        log.setDescription(record.toJSON().toJSONString());
                        log.setLogtype(SystemLog.UPDATE);
                        log.setUser(SystemLog.getCurrentUser());
                        log.setUser_id(SystemLog.getCurrentUserID());
                        log.save();
                        Platform.runLater(()->{
                            dialog.close();
                            FXDialog.showMessageDialog(stackPane, "Successfull", record.getDescription()+" has been updated!", FXDialog.SUCCESS);
                            postAction();                        
                        });
                    }else{
                        Platform.runLater(()->{
                            FXDialog.showMessageDialog(stackPane, "Failure", "There has been a problem on server communication!", FXDialog.DANGER);                      
                        });                    
                    }
                }else{
                    if(record.save() > 0){
                        SystemLog log = new SystemLog();
                        log.setLogtable(HospitalService.TABLE_NAME);
                        log.setLogtableid(record.getId());
                        log.setDescription(record.toJSON().toJSONString());
                        log.setLogtype(SystemLog.SAVE);
                        log.setUser(SystemLog.getCurrentUser());
                        log.setUser_id(SystemLog.getCurrentUserID());
                        log.save();
                        Platform.runLater(()->{
                            dialog.close();
                            FXDialog.showMessageDialog(stackPane, "Successfull", record.getDescription()+" has been saved!", FXDialog.SUCCESS);
                            postAction();                        
                        });
                    }else{
                        Platform.runLater(()->{
                            FXDialog.showMessageDialog(stackPane, "Failure", "There has been a problem on server communication!", FXDialog.DANGER);                      
                        });                    
                    }
                }
                
            }catch(Exception er){
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            }finally{
                Platform.runLater(()->{
                    saveBtn.setDisable(false);
                    closeBtn.setDisable(false);
                });
            }
        }
    };

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
            if(UI_CONTROLLER instanceof ServicesUIController){
                if(record.getFacility().equalsIgnoreCase("laboratory")){
                    UI_CONTROLLER.reloadReferences(1);
                }else if(record.getFacility().equalsIgnoreCase("radiology")){
                    UI_CONTROLLER.reloadReferences(2);
                }else if(record.getFacility().equalsIgnoreCase("admission") || record.getFacility().equalsIgnoreCase("room")){
                    UI_CONTROLLER.reloadReferences(3);
                }else{
                    UI_CONTROLLER.reloadReferences(0);
                }
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
    public void setFormObject(HospitalService obj) {
        this.record = obj;
    }

    @Override
    public HospitalService getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return descF.validate() && facilityF.validate() && priceF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            descF.textProperty().bindBidirectional(record.descriptionProperty());
            facilityF.textProperty().bindBidirectional(record.facilityProperty());
            categoryF.textProperty().bindBidirectional(record.categoryProperty());
            grpF.textProperty().bindBidirectional(record.grpProperty());
            priceF.textProperty().bindBidirectional(record.priceProperty(), new NumberConverter());
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
            FXField.addRequiredValidator(descF);
            FXField.addRequiredValidator(facilityF);
            FXField.addRequiredValidator(priceF);
            FXField.addDoubleValidator(priceF, 1, 999999, 1);
            
            FXField.addFocusValidationListener(descF,facilityF,priceF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            List<String> facilities = SQLTable.distinct(Facility.class, Facility.NAME);
            List<String> categories = SQLTable.distinct(HospitalService.class, HospitalService.CATEGORY);
            List<String> grp = SQLTable.distinct(HospitalService.class, HospitalService.GRP);
            TextFields.bindAutoCompletion(facilityF, facilities);
            TextFields.bindAutoCompletion(categoryF, categories);
            TextFields.bindAutoCompletion(grpF, grp);
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
    
    public static FXDialogTask showDialog(HospitalService record,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/ServiceForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, record, ui_controller, 500, 550);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(ServiceFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
