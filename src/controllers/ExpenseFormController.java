package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
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
import models.Expense;
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
 * @author Duskmourne
 */
public class ExpenseFormController implements Initializable,FormController<Expense> {
    
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Expense record = null;
    
    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXDatePicker dateF;

    @FXML
    private JFXTextField voucherF;

    @FXML
    private JFXTextField invoiceF;

    @FXML
    private JFXTextField vendorF;

    @FXML
    private JFXTextField descriptionF;

    @FXML
    private JFXTextField categoryF;

    @FXML
    private JFXTextField totalsalesF;

    @FXML
    private JFXTextField vatsalesF;

    @FXML
    private JFXTextField nonvatsalesF;

    @FXML
    private JFXTextField taxF;

    @FXML
    private JFXTextField discountF;

    @FXML
    private JFXTextField ewtF;

    @FXML
    private JFXTextField lessvatF;

    @FXML
    private JFXTextField netsalesF;

    @FXML
    private JFXButton saveBtn;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            saveBtn.setDisable(true);
                        });
                        if(record.getId() <= 0){
                            record.setEncoder(Care.getUser().getName());
                            if(record.save(true) > 0){
                                Platform.runLater(()->{
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Successful", "Expense has been saved!", FXDialog.SUCCESS);
                                    postAction();
                                });
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Failed", "Failed to communicate to database server!", FXDialog.DANGER);                                    
                                });
                            }
                        }else{
                            if(record.update(true)){
                                Platform.runLater(()->{
                                    dialog.close();
                                    FXDialog.showMessageDialog(stackPane, "Successful", "Expense has been updated!", FXDialog.SUCCESS);
                                    postAction();
                                });
                            }else{
                                Platform.runLater(()->{
                                    FXDialog.showMessageDialog(stackPane, "Failed", "Failed to communicate to database server!", FXDialog.DANGER);                                    
                                });
                            }
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
            if(UI_CONTROLLER instanceof AccountingUXController){
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
    public void setFormObject(Expense obj) {
        this.record = obj;
    }

    @Override
    public Expense getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return true;
    }

    @Override
    public void loadBindings() {
        try{
            dateF.valueProperty().bindBidirectional(record.expensedateProperty());
            voucherF.textProperty().bindBidirectional(record.voucherProperty());
            invoiceF.textProperty().bindBidirectional(record.invoicenumberProperty());
            vendorF.textProperty().bindBidirectional(record.vendorProperty());
            descriptionF.textProperty().bindBidirectional(record.descriptionProperty());
            categoryF.textProperty().bindBidirectional(record.categoryProperty());
            totalsalesF.textProperty().bindBidirectional(record.totalsalesProperty(), new NumberConverter());
            vatsalesF.textProperty().bindBidirectional(record.vatsalesProperty(), new NumberConverter());
            nonvatsalesF.textProperty().bindBidirectional(record.nonvatsalesProperty(), new NumberConverter());
            taxF.textProperty().bindBidirectional(record.inputvatProperty(),new NumberConverter());
            discountF.textProperty().bindBidirectional(record.discountProperty(), new NumberConverter());
            ewtF.textProperty().bindBidirectional(record.ewtProperty(), new NumberConverter());
            lessvatF.textProperty().bindBidirectional(record.lessvatProperty(), new NumberConverter());
            netsalesF.textProperty().bindBidirectional(record.netsalesProperty(), new NumberConverter());
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
            FXField.addRequiredValidator(dateF);
            FXField.addRequiredValidator(vendorF);
            FXField.addRequiredValidator(totalsalesF);
            FXField.addRequiredValidator(vatsalesF);
            FXField.addRequiredValidator(nonvatsalesF);
            FXField.addRequiredValidator(taxF);
            FXField.addRequiredValidator(discountF);
            FXField.addRequiredValidator(ewtF);
            FXField.addRequiredValidator(lessvatF);
            FXField.addRequiredValidator(netsalesF);
            
            FXField.addDoubleValidator(totalsalesF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(vatsalesF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(nonvatsalesF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(taxF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(discountF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(ewtF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(lessvatF, 0.0, 999999999.999, 0.0);
            FXField.addDoubleValidator(netsalesF, 0.0, 999999999.999, 0.0);
            
            FXField.addFocusValidationListener(dateF,vendorF,totalsalesF,vatsalesF,nonvatsalesF,taxF,discountF,ewtF,lessvatF,netsalesF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{            
            List<String> vendors = SQLTable.distinct(Expense.class, Expense.VENDOR);
            List<String> category = SQLTable.distinct(Expense.class, Expense.CATEGORY);
            
            Platform.runLater(()->{
                TextFields.bindAutoCompletion(vendorF, vendors);
                TextFields.bindAutoCompletion(categoryF, category);
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            if(record.getId() <= 0){
                dateF.setValue(LocalDate.now());
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(Expense item,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/ExpenseForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, item, ui_controller, 500, 620);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(ExpenseFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
