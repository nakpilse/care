package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import models.ContactPerson;
import models.Item;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class NewItemFormController implements Initializable,FormController<Item> {

    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Item record = null;
    
    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXTextField codeF;

    @FXML
    private JFXCheckBox pnfC;

    @FXML
    private JFXTextField descriptionF;

    @FXML
    private JFXTextField genericF;

    @FXML
    private JFXTextField typeF;

    @FXML
    private JFXTextField measureF;
    
    @FXML
    private JFXTextField formF;

    @FXML
    private JFXTextField categoryF;

    @FXML
    private JFXTextField strengthF;

    @FXML
    private JFXTextField opt1F;

    @FXML
    private JFXTextField opt2F;

    @FXML
    private JFXTextField qtyF;

    @FXML
    private JFXTextField reorderF;

    @FXML
    private JFXCheckBox stockedC;

    @FXML
    private JFXTextField costF;

    @FXML
    private JFXTextField sellingF;

    @FXML
    private JFXCheckBox vatableF;

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
                if(record.getCost() <= 0 || record.getPrice() <= 0 || record.getCost() >= record.getPrice()){
                    JFXButton btn = new JFXButton("Yes, I am certain!");
                    JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Warning", new Label("Saving item with cost="+record.getCost()+" & selling="+record.getPrice()+" ?"), FXDialog.WARNING,btn);
                    btn.setOnAction(evt->{
                        dl.close();
                        Care.process(saveTask);
                    });
                }else{
                    Care.process(saveTask);
                }
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
                if(record.save(true) > 0){
                    Platform.runLater(()->{
                        dialog.close();
                        FXDialog.showMessageDialog(stackPane, "Successfull", record.getDescription()+" has been registered!", FXDialog.SUCCESS);
                        postAction();                        
                    });
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(stackPane, "Failure", "There has been a problem on server communication!", FXDialog.DANGER);                      
                    });                    
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
            if(UI_CONTROLLER instanceof InventoryUIController){
                if(record.isSupply()){
                    UI_CONTROLLER.reloadReferences(1);
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
    public void setFormObject(Item obj) {
        this.record = obj;
    }

    @Override
    public Item getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return codeF.validate() && descriptionF.validate() && qtyF.validate() && reorderF.validate() && costF.validate() && sellingF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            codeF.textProperty().bindBidirectional(record.codeProperty());
            pnfC.selectedProperty().bindBidirectional(record.pnfProperty());
            descriptionF.textProperty().bindBidirectional(record.descriptionProperty());
            genericF.textProperty().bindBidirectional(record.genericnameProperty());
            typeF.textProperty().bindBidirectional(record.typeProperty());
            measureF.textProperty().bindBidirectional(record.unitmeasureProperty());
            formF.textProperty().bindBidirectional(record.formProperty());
            categoryF.textProperty().bindBidirectional(record.categoryProperty());
            strengthF.textProperty().bindBidirectional(record.strengthProperty());
            opt1F.textProperty().bindBidirectional(record.opt1Property());
            opt2F.textProperty().bindBidirectional(record.opt2Property());
            qtyF.textProperty().bindBidirectional(record.quantityProperty(), new NumberConverter());
            reorderF.textProperty().bindBidirectional(record.reorderquantityProperty(), new NumberConverter());
            stockedC.selectedProperty().bindBidirectional(record.stockedProperty());
            costF.textProperty().bindBidirectional(record.costProperty(), new NumberConverter());
            sellingF.textProperty().bindBidirectional(record.priceProperty(), new NumberConverter());
            vatableF.selectedProperty().bindBidirectional(record.vatableProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            FXField.setCommonCharactersOnly(typeF);
            FXField.setCommonCharactersOnly(categoryF);            
            FXField.addTrimOnFocusLost(codeF,descriptionF,genericF,typeF,categoryF,measureF,formF,strengthF,opt1F,opt2F);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(codeF);
            FXField.addRequiredValidator(descriptionF);
            FXField.addRequiredValidator(qtyF);
            FXField.addRequiredValidator(reorderF);
            FXField.addRequiredValidator(costF);
            FXField.addRequiredValidator(sellingF);
            FXField.addIntegerValidator(qtyF, -999999999, 999999999, 1);
            FXField.addIntegerValidator(reorderF, 0, 999999999, 0);
            FXField.addDoubleValidator(costF, 0.0, 999999999.99, 0.0);
            FXField.addDoubleValidator(sellingF, 0.0, 999999999.99, 0.0);
            FXField.addDuplicateValidator(codeF, record, Item.CODE);
            FXField.addDuplicateValidator(descriptionF, record, Item.DESCRIPTION);
            FXField.addFocusValidationListener(codeF,descriptionF,qtyF,reorderF,costF,sellingF);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{            
            Map<String,String> opts = GlobalOption.getMap(GlobalOption.ITEM_CATEGORY);
            opt1F.setPromptText(opts.get("ITEM_OPT1"));
            opt2F.setPromptText(opts.get("ITEM_OPT2"));
            
            List<String> types = SQLTable.distinct(Item.class, Item.TYPE);
            List<String> categories = SQLTable.distinct(Item.class, Item.CATEGORY);
            List<String> forms = SQLTable.distinct(Item.class, Item.FORM);
            List<String> measures = SQLTable.distinct(Item.class, Item.UNITMEASURE);
            List<String> strs = SQLTable.distinct(Item.class, Item.STRENGTH);
            List<String> opt1s = SQLTable.distinct(Item.class, Item.OPT1);
            List<String> opt2s = SQLTable.distinct(Item.class, Item.OPT2);
            
            TextFields.bindAutoCompletion(typeF, types);
            TextFields.bindAutoCompletion(categoryF, categories);
            TextFields.bindAutoCompletion(measureF, measures);
            TextFields.bindAutoCompletion(formF, forms);
            TextFields.bindAutoCompletion(strengthF, strs);
            TextFields.bindAutoCompletion(opt1F, opt1s);
            TextFields.bindAutoCompletion(opt2F, opt2s);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            Platform.runLater(()->{
                if(record.isSupply()){
                    titleLbl.setText("New Hospital Supply Registration");                    
                }else{
                    titleLbl.setText("New Pharmacy Item Registration");
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(Item item,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/NewItemForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, item, ui_controller, 800, 620);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(NewItemFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
}
