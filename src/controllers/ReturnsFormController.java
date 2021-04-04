package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.Item;
import models.Patient;
import models.ReturnedItem;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTextFieldTableCell;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.LocalOption;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import test.Person;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ReturnsFormController implements Initializable,FormController {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Map<String,HospitalChargeItem> RETURN_MAP;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

    @FXML
    private JFXTextField pnameF;

    @FXML
    private JFXToggleNode _7daysBtn;

    @FXML
    private JFXToggleNode _15daysBtn;

    @FXML
    private JFXToggleNode _30daysBtn;

    @FXML
    private TableView<HospitalChargeItem> itemsTbl;

    @FXML
    private JFXTextField returnbyF;

    @FXML
    private JFXTextField recievedbyF;

    @FXML
    private JFXTextField remarksF;    
    
    @FXML
    private ToggleGroup menuGroup;

    @FXML
    void formClose(ActionEvent event) {        
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            if(isFieldInputsValid()){
                Platform.runLater(()->{
                    saveBtn.setDisable(true);
                    Care.process(()->{
                        try{
                            String retby = returnbyF.getText();
                            String recby = recievedbyF.getText();
                            String rem = remarksF.getText();
                            LocalDateTime now = LocalDateTime.now();
                            
                            List<HospitalChargeItem> chargeItems = itemsTbl.getItems();
                            for(HospitalChargeItem item:chargeItems){
                                if(item.getTmpcount() > 0){
                                    ReturnedItem retitem = item.asReturnedItem();
                                    retitem.setQuantity(item.getTmpcount());
                                    retitem.setReturnedby(retby);
                                    retitem.setRecievedby(recby);
                                    retitem.setRemarks(rem);
                                    retitem.setUser(Care.getUser().getName());
                                    retitem.setUser_id(Care.getUser().getId());
                                    retitem.setReturntime(now);
                                    retitem.save();
                                    retitem.addItemQuantity();                                     
                                }
                            }
                            
                            Platform.runLater(()->{
                                dialog.close();
                                FXDialog.showMessageDialog(stackPane,"Success","Return Saved!",FXDialog.SUCCESS);
                                //Care.createNotification("Charge Info", "Your Charge to patient info has been saved!", 3000, true);
                                System.out.println("Return Saved");
                                postAction();
                            });
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally{
                            Platform.runLater(()->{
                                saveBtn.setDisable(false);
                            });                            
                        }
                    });
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void show15DaysItems(ActionEvent event) {
        try{
            if(!pnameF.getText().isEmpty()){                    
                String pname = pnameF.getText();
                List<HospitalChargeItem> retitems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGETO+"='"+pname+"' AND "+HospitalChargeItem.CHARGETIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(15), LocalTime.now()))+"' ORDER BY "+HospitalChargeItem.DESCRIPTION+" ASC");
                Platform.runLater(()->{
                    FXTable.setList(itemsTbl, retitems);
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void show30DaysItems(ActionEvent event) {
        try{
            if(!pnameF.getText().isEmpty()){                    
                String pname = pnameF.getText();
                List<HospitalChargeItem> retitems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGETO+"='"+pname+"' AND "+HospitalChargeItem.CHARGETIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.now()))+"' ORDER BY "+HospitalChargeItem.DESCRIPTION+" ASC");
                Platform.runLater(()->{
                    FXTable.setList(itemsTbl, retitems);
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void show7DaysItems(ActionEvent event) {
        try{
            if(!pnameF.getText().isEmpty()){                    
                String pname = pnameF.getText();
                List<HospitalChargeItem> retitems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGETO+"='"+pname+"' AND "+HospitalChargeItem.CHARGETIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.now()))+"' ORDER BY "+HospitalChargeItem.DESCRIPTION+" ASC");
                Platform.runLater(()->{
                    FXTable.setList(itemsTbl, retitems);
                });
            }
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
            if(UI_CONTROLLER instanceof PatientInfoUIController){
                UI_CONTROLLER.reloadReferences(4);
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
    public void setFormObject(Object obj) {
        
    }

    @Override
    public Object getFormObject() {
        return null;
    }

    @Override
    public boolean isFieldInputsValid() {
        return pnameF.validate() && recievedbyF.validate() && returnbyF.validate() && remarksF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            List<String> patients = SQLTable.distinct(HospitalChargeItem.class,HospitalChargeItem.CHARGETO);    
            List<String> persons = SQLTable.distinct(HospitalPersonel.class, HospitalPersonel.NAME);
            TextFields.bindAutoCompletion(pnameF, patients);
            TextFields.bindAutoCompletion(recievedbyF, persons);
            TextFields.bindAutoCompletion(returnbyF, persons);
            pnameF.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if(!newVal){
                    if(!pnameF.getText().isEmpty()){
                        JFXToggleNode tg = (JFXToggleNode)menuGroup.getSelectedToggle();
                        String con = tg.getText();
                        int days = 0;
                        if(con.equalsIgnoreCase("Prev 15 Days")){
                            days = 15;
                        }else if(con.equalsIgnoreCase("Prev 30 Days")){
                            days = 30;
                        }else{
                            days = 7;
                        }
                        String pname = pnameF.getText();
                        List<HospitalChargeItem> retitems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGETO+"='"+pname+"' AND "+HospitalChargeItem.CHARGETIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.now()))+"' ORDER BY "+HospitalChargeItem.DESCRIPTION+" ASC");
                        Platform.runLater(()->{
                            FXTable.setList(itemsTbl, retitems);
                        });
                    }
                }
            });
            
            itemsTbl.setEditable(false);
            itemsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(itemsTbl, "Charge #", HospitalChargeItem::chargenumberProperty, false,125,125,125);
            FXTable.addColumn(itemsTbl, "Description", HospitalChargeItem::descriptionProperty, false);
            FXTable.addColumn(itemsTbl, "Qty", HospitalChargeItem::quantityProperty, false,45,45,45);
            TableColumn ctrlCol = FXTable.addColumn(itemsTbl, "_", HospitalChargeItem::descriptionProperty, true,76,76,76);
            FXTable.addColumn(itemsTbl, "Return", HospitalChargeItem::tmpcountProperty, true,55,55,55);
            
            ctrlCol.setCellFactory(column -> {
                return new TableCell<HospitalChargeItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalChargeItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton subBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.MINUS, "18px", evt -> {
                                        if(row_data.getTmpcount() > 0){
                                            row_data.setTmpcount(row_data.getTmpcount()-1);
                                        }
                                    });
                                    
                                    subBtn.getStyleClass().add("btn-primary");
                                    subBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    subBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton addBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PLUS, "18px", evt -> {
                                        if(row_data.getTmpcount() < row_data.getQuantity()){
                                            row_data.setTmpcount(row_data.getTmpcount()+1);
                                        }
                                    });
                                    
                                    addBtn.getStyleClass().add("btn-primary");
                                    addBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    addBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(subBtn,addBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                    }
                };
            });
            
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(pnameF);
            FXField.addRequiredValidator(returnbyF);
            FXField.addRequiredValidator(recievedbyF);
            FXField.addRequiredValidator(remarksF);
            FXField.addFocusValidationListener(pnameF,returnbyF,recievedbyF,remarksF);
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
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/ReturnsForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, null, ui_controller, 600, 600);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(ReturnsFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
}
