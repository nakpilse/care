package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import models.DeliveryItem;
import models.Item;
import models.Supplier;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class DeliveryFormController implements Initializable,FormController<DeliveryItem> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private static int RELOAD = 0;
    private DeliveryItem record = null;

    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXComboBox<Supplier> supplierC;

    @FXML
    private JFXTextField refF;

    @FXML
    private JFXDatePicker deliverydateF;

    @FXML
    private JFXTextField deliveredbyF;

    @FXML
    private JFXTextField recievedbyF;

    @FXML
    private JFXTextField returnedbyF;

    @FXML
    private JFXTextField returnedtoF;

    @FXML
    private JFXButton deliverydateBtn;

    @FXML
    private JFXTextField descriptionF;

    @FXML
    private JFXTextField qtyF;

    @FXML
    private JFXTextField unitF;

    @FXML
    private JFXTextField unitcostF;

    @FXML
    private JFXTextField totalF;

    @FXML
    private Label itemLbl;

    @FXML
    private JFXTextField itemqtyF;

    @FXML
    private JFXTextField itemcostF;

    @FXML
    private JFXTextField itemsellingF;

    @FXML
    private JFXDatePicker expirationF;

    @FXML
    private JFXDatePicker returndateF;

    @FXML
    private JFXTextField remarksF;

    @FXML
    private JFXButton returndateBtn;

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
                if(record.getItemcost()<= 0 || record.getItemcost()<= 0 || record.getItemcost() >= record.getItemselling()){
                    JFXButton btn = new JFXButton("Yes, I am certain!");
                    JFXDialog dl = FXDialog.showConfirmDialog(stackPane, "Warning", new Label("Saving item with cost="+record.getCost()+" & selling="+record.getItemselling()+" ?"), FXDialog.WARNING,btn);
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
                if(record.getId() <= 0){
                    record.setUser(Care.getUser().getName());
                    record.setUser_id(Care.getUser().getId());                    
                }
                if(record.save(true) > 0){                    
                    Item itm = (Item)SQLTable.get(Item.class, record.getItem_id());
                    if(record.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED)){                        
                        itm.setQuantity(itm.getQuantity()+record.getItemqty());
                        itm.setCost(record.getItemcost());
                        itm.setPrice(record.getItemselling());                        
                    }else{
                        itm.setQuantity(itm.getQuantity()-record.getItemqty());
                        itm.setCost(record.getItemcost());
                        itm.setPrice(record.getItemselling());       
                    }
                    if(itm.update(Item.QUANTITY,Item.COST,Item.PRICE)){
                        SystemLog log = new SystemLog();
                        log.setLogtable(Item.TABLE_NAME);
                        log.setLogtableid(itm.getId());
                        log.setDescription(itm.toJSON().toJSONString());
                        log.setLogtype(SystemLog.UPDATE);
                        log.setUser(SystemLog.getCurrentUser());
                        log.setUser_id(SystemLog.getCurrentUserID());
                        log.setRemarks((record.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED))? "Recieved "+record.getItemqty()+" from "+record.getSupplier():"Returned "+record.getItemqty()+" to "+record.getSupplier());
                        log.save();
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
                UI_CONTROLLER.reloadReferences(RELOAD);
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
    public void setFormObject(DeliveryItem obj) {
        this.record = obj;
    }

    @Override
    public DeliveryItem getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return supplierC.validate() && refF.validate() && deliverydateF.validate() && deliveredbyF.validate() && recievedbyF.validate() && descriptionF.validate() && qtyF.validate() && unitF.validate() && totalF.validate() && itemqtyF.validate() && itemcostF.validate() && itemsellingF.validate() && returndateF.validate() && returnedbyF.validate() && returnedtoF.validate() && remarksF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            refF.textProperty().bindBidirectional(record.referrenceProperty());
            deliverydateF.valueProperty().bindBidirectional(record.deliverydateProperty());
            deliveredbyF.textProperty().bindBidirectional(record.deliveredbyProperty());
            recievedbyF.textProperty().bindBidirectional(record.recievedbyProperty());
            returnedbyF.textProperty().bindBidirectional(record.returnedbyProperty());
            returnedtoF.textProperty().bindBidirectional(record.returnedtoProperty());
            descriptionF.textProperty().bindBidirectional(record.descriptionProperty());
            qtyF.textProperty().bindBidirectional(record.quantityProperty(), new NumberConverter());
            unitF.textProperty().bindBidirectional(record.unitsProperty());
            unitcostF.textProperty().bindBidirectional(record.costProperty(), new NumberConverter());
            expirationF.valueProperty().bindBidirectional(record.expirationProperty());
            totalF.textProperty().bindBidirectional(record.amountProperty(),new NumberConverter());
            
            itemqtyF.textProperty().bindBidirectional(record.itemqtyProperty(), new NumberConverter());
            itemcostF.textProperty().bindBidirectional(record.itemcostProperty(),new NumberConverter());
            itemsellingF.textProperty().bindBidirectional(record.itemsellingProperty(), new NumberConverter());
            
            returndateF.valueProperty().bindBidirectional(record.returndateProperty());
            remarksF.textProperty().bindBidirectional(record.remarksProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            deliverydateBtn.setOnAction(evt -> {
                Platform.runLater(() -> {
                    deliverydateF.show();
                });
            });
            returndateBtn.setOnAction(evt -> {
                Platform.runLater(() -> {
                    returndateF.show();
                });
            });
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            if(record.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED)){                     
                FXField.addRequiredValidator(supplierC);
                FXField.addRequiredValidator(refF);
                FXField.addRequiredValidator(deliverydateF);
                FXField.addRequiredValidator(deliveredbyF);
                FXField.addRequiredValidator(recievedbyF);
                
                FXField.addRequiredValidator(descriptionF);
                FXField.addRequiredValidator(qtyF);
                FXField.addRequiredValidator(unitF);
                //FXField.addRequiredValidator(unitcostF);
                FXField.addRequiredValidator(totalF);
                
                FXField.addIntegerValidator(qtyF, 1, 999999999, 1);                
                //FXField.addDoubleValidator(unitcostF, 0.0, 999999999.0, 0.0);
                FXField.addDoubleValidator(totalF, 0.0, 999999999.0, 0.0);
                
                FXField.addRequiredValidator(itemqtyF);
                FXField.addRequiredValidator(itemcostF);
                FXField.addRequiredValidator(itemsellingF);
                FXField.addIntegerValidator(itemqtyF, 1, 999999999, 1);                
                FXField.addDoubleValidator(itemcostF, 0.0, 999999999.0, 0.0);
                FXField.addDoubleValidator(itemsellingF, 0.0, 999999999.0, 0.0);
                
                FXField.addFocusValidationListener(supplierC,refF,deliverydateF,deliveredbyF,recievedbyF,descriptionF,qtyF,unitF,totalF,itemqtyF,itemcostF,itemsellingF);
            }else{
                FXField.addRequiredValidator(supplierC);
                FXField.addRequiredValidator(refF);
                FXField.addRequiredValidator(deliverydateF);
                FXField.addRequiredValidator(deliveredbyF);
                FXField.addRequiredValidator(recievedbyF);
                
                FXField.addRequiredValidator(descriptionF);
                FXField.addRequiredValidator(qtyF);
                FXField.addRequiredValidator(unitF);
                //FXField.addRequiredValidator(unitcostF);
                FXField.addRequiredValidator(totalF);
                
                FXField.addIntegerValidator(qtyF, 1, 999999999, 1);                
                //FXField.addDoubleValidator(unitcostF, 0.0, 999999999.0, 0.0);
                FXField.addDoubleValidator(totalF, 0.0, 999999999.0, 0.0);
                
                FXField.addRequiredValidator(itemqtyF);
                FXField.addRequiredValidator(itemcostF);
                FXField.addRequiredValidator(itemsellingF);
                FXField.addIntegerValidator(itemqtyF, 1, 999999999, 1);                
                FXField.addDoubleValidator(itemcostF, 0.0, 999999999.0, 0.0);
                FXField.addDoubleValidator(itemsellingF, 0.0, 999999999.0, 0.0);                
                
                FXField.addRequiredValidator(returndateF);                
                FXField.addRequiredValidator(returnedbyF);                
                FXField.addRequiredValidator(returnedtoF);                
                FXField.addRequiredValidator(remarksF);
                
                FXField.addFocusValidationListener(supplierC,refF,deliverydateF,deliveredbyF,recievedbyF,descriptionF,qtyF,unitF,totalF,itemqtyF,itemcostF,itemsellingF,returndateF,returnedbyF,returnedtoF,remarksF);
            }
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
            Platform.runLater(()->{
                List<Supplier> suppliers = SQLTable.list(Supplier.class);
                supplierC.getItems().setAll(suppliers);
                
                supplierC.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null){
                        record.setSupplier(newVal.getName());
                        record.setSupplier_id(newVal.getId());
                        if(record.getId() <= 0){
                            record.setDeliveredby(newVal.getContactperson());
                        }
                    }
                });
                
                if(record.getId() >= 0 ){
                    supplierC.getItems().stream().forEach(sup->{
                        if(sup.getId() == record.getSupplier_id()){
                            supplierC.getSelectionModel().select(sup);
                        }
                    });
                }else{
                    deliverydateF.setValue(LocalDate.now());
                    supplierC.getSelectionModel().selectFirst();
                }
                
                if(record.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED)){
                    itemLbl.setText("Inventory Item Entry");
                    returnedbyF.setVisible(false);
                    returnedtoF.setVisible(false);
                    returndateF.setVisible(false);
                    returndateBtn.setVisible(false);
                    //remarksF.setVisible(false);                    
                }else if(record.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RETURNED)){
                    itemLbl.setText("Inventory Item Return");
                }else{
                    if(record.getReturndate() != null){
                        itemLbl.setText("Inventory Item Return");
                    }else{
                        itemLbl.setText("Inventory Item Entry");
                    }
                }
                
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(DeliveryItem obj,StackPane stackpane,MaskerPane masker, UIController ui_controller, int reload) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/DeliveryForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, obj, ui_controller, 800, 630);
                task.setLOADER(LOADER);
                //task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    RELOAD = reload;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(DeliveryFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
}
