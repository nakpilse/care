package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.Admission;
import models.BillStatement;
import models.Consultation;
import models.ERConsultation;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.Item;
import models.LaboratoryTest;
import models.Patient;
import models.Payment;
import models.RadiologyTest;
import nse.dcfx.controls.UIController;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ReportsUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;

    @FXML
    private JFXComboBox<String> moduleF;

    @FXML
    private JFXButton generateBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private AnchorPane tablePane;

    @FXML
    private FlowPane resultsPane;
    
    private Class module = null;

    /**
     * Get the value of module
     *
     * @return the value of module
     */
    public Class getModule() {
        return module;
    }

    /**
     * Set the value of module
     *
     * @param module new value of module
     */
    public void setModule(Class module) {
        this.module = module;
    }


    @FXML
    void generateReport(ActionEvent event) {

    }

    @FXML
    void saveReport(ActionEvent event) {

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
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        
    }

    @Override
    public void loadUIFixes() {
        try{
            moduleF.getItems().setAll(
                    "Patients",
                    "Admissions",
                    "OPD Consultations",
                    "ER Consultations",
                    "Laboratory Records",
                    "Radiology Records",
                    "Pharmacy Items",
                    "Hospital Supplies",
                    "Hospital Charges",
                    "Sold Items",
                    "Billstatements",
                    "Payments",
                    "Expenses"
            );
            
            moduleF.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null){
                    switch (newVal) {
                        case "Patients":
                            setModule(Patient.class);
                            break;
                        case "Admissions":
                            setModule(Admission.class);
                            break;
                        case "OPD Consultations":
                            setModule(Consultation.class);
                            break;
                        case "ER Consultations":
                            setModule(ERConsultation.class);
                            break;
                        case "Laboratory Records":
                            setModule(LaboratoryTest.class);
                            break;
                        case "Radiology Records":
                            setModule(RadiologyTest.class);
                            break;
                        case "Pharmacy Items":
                            setModule(Item.class);
                            break;
                        case "Hospital Supplies":
                            setModule(Item.class);
                            break;
                        case "Hospital Charges":
                            setModule(HospitalCharge.class);
                            break;
                        case "Sold Items":
                            setModule(HospitalChargeItem.class);
                            break;
                        case "Billstatements":
                            setModule(BillStatement.class);
                            break;
                        case "Payments":
                            setModule(Payment.class);
                            break;
                        default:
                            setModule(null);
                            break;
                    }
                }else{
                    setModule(null);
                }
            });
                    
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
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
        return maskerPane;
    }
    
    private String patientDialog(){
        try{
            
            return "";
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
    
    private void generatePatientReport(){
        try{
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
