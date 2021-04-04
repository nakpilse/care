package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.paint.Color;
import models.Room;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.utils.ColorKit;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class RoomFormController implements Initializable,FormController<Room> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Room record = null;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXTextField nameF;

    @FXML
    private JFXTextField typeF;

    @FXML
    private JFXTextField floorF;

    @FXML
    private JFXColorPicker colorF;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

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
                            JFXSpinner spinner = new JFXSpinner();
                            spinner.setPrefSize(24, 24);
                            saveBtn.setGraphic(spinner);
                        });
                        Thread.sleep(200);
                        if(record.getId() <= 0){                            
                            record.setColorvalue(ColorKit.toRGBCode(colorF.getValue()));
                            if(record.save(true) > 0){
                                Platform.runLater(()->{       
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Success", "New room has been registered!", FXDialog.SUCCESS);
                                });
                                postAction();
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Error", "Failed to save new room!", FXDialog.DANGER);
                                });
                            }
                        }else{
                            record.setColorvalue(ColorKit.toRGBCode(colorF.getValue()));
                            if(record.update(true)){
                                Platform.runLater(()->{       
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Success", "Room has been updated!", FXDialog.SUCCESS);
                                });
                                postAction();
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Error", "Failed to update room!", FXDialog.DANGER);
                                });
                            }
                        }
                        
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            saveBtn.setDisable(false);
                            saveBtn.setGraphic(saveIcon);
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
            if(UI_CONTROLLER instanceof ManagementUIController ){
                UI_CONTROLLER.reloadReferences(1);
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
    public void setFormObject(Room obj) {
        this.record = obj;
    }

    @Override
    public Room getFormObject() {
        return this.record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return nameF.validate() && typeF.validate() && floorF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            nameF.textProperty().bindBidirectional(record.nameProperty());
            typeF.textProperty().bindBidirectional(record.typeProperty());
            floorF.textProperty().bindBidirectional(record.floorProperty());
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
            FXField.addRequiredValidator(nameF);
            FXField.addRequiredValidator(typeF);
            FXField.addRequiredValidator(floorF);
            FXField.addDuplicateValidator(nameF, record, Room.NAME);
            FXField.addFocusValidationListener(nameF,typeF,floorF);
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
            if(record.getId() > 0){
                colorF.setValue(Color.valueOf(record.getColorvalue()));
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(Room obj,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/RoomForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog,obj, ui_controller, 400, 500);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(RoomFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
