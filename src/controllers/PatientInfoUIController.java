package controllers;

import com.jfoenix.controls.JFXDialog;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import models.Patient;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.utils.DateTimeKit;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class PatientInfoUIController implements Initializable,UIController,FormController<Patient> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private StackPane mainStack;
    private MaskerPane maskerPane;
    private Patient record;
    
    @FXML
    private Label displayNameLbl;

    @FXML
    private Label displayBirthLbl;

    @FXML
    private Label displayAgeLbl;

    @FXML
    private Label displayIdLbl;

    
    @FXML
    private Rectangle profileView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        this.mainStack = stackpane;
    }

    @Override
    public StackPane getMainStack() {
        return mainStack;
    }


    @Override
    public void loadCustomizations() {
        try{
            Image img = new Image(getClass().getResource("/assets/avatar_square.png").toURI().toString());
            ImagePattern p = new ImagePattern(img);
            profileView.setFill(p);
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
    public void setUIController(UIController controller) {
        UI_CONTROLLER = controller;
    }

    
    @Override
    public void setMaskerPane(MaskerPane masker) {
        this.maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }

    @Override
    public void postAction() {
        try{
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setFormObject(Patient obj) {
        this.record = obj;
    }

    @Override
    public Patient getFormObject() {
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
    public void loadValidators() {
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
    
    public void setDisplayName(String name){
        this.displayNameLbl.setText(name);
    }
    
    public void setDisplayBirth(LocalDate date){
        this.displayBirthLbl.setText(DateTimeKit.toProperDate(date));
    }
    
    public void setDisplayAge(int age){
        this.displayAgeLbl.setText((age <= 1)? "1 Yr old":age+" Yrs old");
    }
    
    public void setDisplayID(int id){
        this.displayIdLbl.setText(String.format("%012d", id));
    }
}
