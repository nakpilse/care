package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import models.Supplier;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class NewSupplierFormController implements Initializable,FormController<Supplier> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Supplier record = null;
    
    @FXML
    private JFXTextField codeF;

    @FXML
    private JFXTextField cpersonF;

    @FXML
    private JFXTextField cnumberF;

    @FXML
    private JFXTextField cemailF;

    @FXML
    private JFXTextField termsF;

    @FXML
    private JFXTextField nameF;

    @FXML
    private JFXTextField addressF;

    @FXML
    private JFXTextField tinF;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        if(isFieldInputsValid()){
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            saveBtn.setDisable(true);
                            closeBtn.setDisable(true);
                        });
                        Thread.sleep(200);
                        if(record.save(true) > 0){
                            Platform.runLater(()->{       
                                dialog.close();
                                FXDialog.showMessageDialog(stackPane, "Success", "New supplier has been registered!", FXDialog.SUCCESS);
                            });
                            postAction();
                        }else{
                            Platform.runLater(()->{
                                FXDialog.showMessageDialog(stackPane, "Error", "Failed to save new supplier!", FXDialog.DANGER);
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
            if(UI_CONTROLLER instanceof InventoryUIController ){
                UI_CONTROLLER.reloadReferences(2);
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
    public void setFormObject(Supplier obj) {
        this.record = obj;
    }

    @Override
    public Supplier getFormObject() {
        return this.record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return codeF.validate() && nameF.validate() && cemailF.validate() && termsF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            codeF.textProperty().bindBidirectional(record.codeProperty());
            nameF.textProperty().bindBidirectional(record.nameProperty());
            tinF.textProperty().bindBidirectional(record.tinProperty());
            addressF.textProperty().bindBidirectional(record.addressProperty());
            cpersonF.textProperty().bindBidirectional(record.contactpersonProperty());
            cnumberF.textProperty().bindBidirectional(record.contactnumberProperty());
            cemailF.textProperty().bindBidirectional(record.contactemailProperty());
            termsF.textProperty().bindBidirectional(record.paymenttermsProperty(), new NumberConverter());
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            FXField.setCommonCharactersOnly(nameF);
            FXField.setCommonCharactersOnly(addressF);
            FXField.setNameCharactersOnly(cpersonF);
            
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(codeF);
            FXField.addRequiredValidator(nameF);
            FXField.addIntegerValidator(termsF, 0, 999999, 0);
            FXField.addEmailValidator(cemailF);
            FXField.addDuplicateValidator(codeF, record, Supplier.CODE);
            FXField.addDuplicateValidator(nameF, record, Supplier.NAME);
            
            FXField.addFocusValidationListener(codeF,nameF,cemailF,termsF);
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
    
    public static FXDialogTask showDialog(StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/NewSupplierForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, new Supplier(), ui_controller, 550, 600);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(NewSupplierFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
