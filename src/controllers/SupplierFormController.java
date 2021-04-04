package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.DeliveryItem;
import models.Supplier;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;

/**
 *
 * @author Duskmourne
 */
public class SupplierFormController implements Initializable,FormController<Supplier>{
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Supplier record = null;
    
    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode infoMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode invoiceMenu;

    @FXML
    private JFXToggleNode deliveryMenu;

    @FXML
    private JFXToggleNode logsMenu;

    @FXML
    private JFXTabPane mainTab;

    @FXML
    private Tab infoTab;

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
    private JFXButton saveBtn;

    @FXML
    private Tab invoiceTab;

    @FXML
    private TableView<?> invTbl;

    @FXML
    private JFXTextField invsearchF;

    @FXML
    private Label invresultLbl;

    @FXML
    private Label invunsettledcountLbl;

    @FXML
    private Label invunsettledamtsLbl;

    @FXML
    private Label invamtsLbl;

    @FXML
    private Tab deliveryTab;

    @FXML
    private TableView<DeliveryItem> deliveryTbl;

    @FXML
    private JFXTextField deliverysearchF;

    @FXML
    private Label deliveryresultLbl;

    @FXML
    private Label deliveryreturnLbl;

    @FXML
    private Label deliveryrecieveLbl;

    @FXML
    private Tab logsTab;

    @FXML
    private TableView<SystemLog> logsTbl;

    @FXML
    private JFXTextField logsearchF;

    @FXML
    private Label logresultsLbl;
    
    private final FXTask saveTask = new FXTask() {
        @Override
        protected void load() {
            try{
                Platform.runLater(()->{
                    closeBtn.setDisable(true);
                    saveBtn.setDisable(true);
                });      
                if(record.update(true)){                    
                    Platform.runLater(()->{
                        dialog.close();
                        FXDialog.showMessageDialog(stackPane, "Successfull", record.getName()+" has been updated!", FXDialog.SUCCESS);
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

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        try{
            if(isFieldInputsValid()){
                Care.process(saveTask);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    

    @FXML
    void loadDelivery(ActionEvent event) {
        mainTab.getSelectionModel().select(deliveryTab);
        loadItemDeliveries();
    }

    @FXML
    void loadInfo(ActionEvent event) {
        mainTab.getSelectionModel().select(infoTab);
    }

    @FXML
    void loadLogs(ActionEvent event) {
        mainTab.getSelectionModel().select(logsTab);
    }

    @FXML
    void loadInvoice(ActionEvent event) {
        mainTab.getSelectionModel().select(invoiceTab);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
            
            loadDeliveryCustomizations();
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
            infoMenu.fire();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(Supplier obj,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/SupplierForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, obj, ui_controller, 1000, 620);
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
    
    private void loadDeliveryCustomizations(){
        try{
            TableColumn deltimeCol = FXTable.addColumn(deliveryTbl, "Date", DeliveryItem::deliverydateProperty, false, 85, 85, 85);
            FXTable.addColumn(deliveryTbl, "Ref #", DeliveryItem::referrenceProperty,false,80,80,80);
            FXTable.addColumn(deliveryTbl, "Item", DeliveryItem::descriptionProperty,false);
            TableColumn unitCol = FXTable.addColumn(deliveryTbl, "Units", DeliveryItem::unitsProperty,false,80,80,80);
            FXTable.addColumn(deliveryTbl, "Type", DeliveryItem::typeProperty,false,60,60,60);
            FXTable.addColumn(deliveryTbl, "Remarks", DeliveryItem::remarksProperty,false);
            TableColumn delcancelCol = FXTable.addColumn(deliveryTbl, "Canceled", DeliveryItem::canceldateProperty,false,150,150,150);
            TableColumn actCol = FXTable.addColumn(deliveryTbl, "View", DeliveryItem::descriptionProperty, false, 44, 44, 44);
            FXTable.setDateColumn(deltimeCol);
            FXTable.setTimestampColumn(delcancelCol);
            
            unitCol.setCellFactory(column -> {
                return new TableCell<DeliveryItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                DeliveryItem row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    setText(row_data.getQuantity()+" "+row_data.getUnits());
                                    //setGraphic(null);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }                                
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });
            
            actCol.setCellFactory(column -> {
                return new TableCell<DeliveryItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                DeliveryItem row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox container = new HBox();
                                    container.setMaxWidth(46);
                                    container.setMaxWidth(46);
                                    container.setPrefWidth(46);
                                    container.setSpacing(4);
                                    
                                    JFXButton vwBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt->{
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(stackPane, "Delivery Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    
                                    vwBtn.setTooltip(new Tooltip("View Delivery"));
                                    vwBtn.getStyleClass().add("btn-control");
                                    vwBtn.setStyle("-jfx-button-type : FLAT;");
                                    vwBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);                                    
                                    
                                    
                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(vwBtn);
                                    
                                    setGraphic(container);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }                                
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });
            deliveryTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }catch(Exception er){
            
        }
    }
    
    private void loadItemDeliveries(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        FXTable.setFilteredList(deliveryTbl, new ArrayList());
                        Platform.runLater(()->{
                            deliveryTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<DeliveryItem> records = SQLTable.list(DeliveryItem.class,DeliveryItem.SUPPLIER_ID+"='"+record.getId()+"' ORDER BY "+DeliveryItem.ID+" DESC");
                        FilteredList<DeliveryItem> filteredRecords = FXTable.setFilteredList(deliveryTbl, records);
                        DeliveryItem.createTableFilter(deliverysearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends DeliveryItem> c) -> {                   
                            deliveryresultLbl.setText("Results : "+filteredRecords.size());
                            List<Integer> recvals = filteredRecords.stream().map(d -> (d.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED) && d.getCanceldate() == null)? 1:0).collect(Collectors.toList());
                            List<Integer> retvals = filteredRecords.stream().map(d -> (d.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RETURNED) && d.getCanceldate() != null)? 1:0).collect(Collectors.toList());
                            int recieveCount = recvals.stream().collect(Collectors.summingInt(Integer::intValue));
                            int returnCount = retvals.stream().collect(Collectors.summingInt(Integer::intValue));
                            deliveryrecieveLbl.setText("Recieved : "+recieveCount);
                            deliveryreturnLbl.setText("Returned : "+returnCount);
                        });
                        Platform.runLater(()->{
                            deliveryresultLbl.setText("Results : "+filteredRecords.size());      
                            List<Integer> recvals = filteredRecords.stream().map(d -> (d.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED) && d.getCanceldate() == null)? 1:0).collect(Collectors.toList());
                            List<Integer> retvals = filteredRecords.stream().map(d -> (d.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RETURNED) && d.getCanceldate() == null)? 1:0).collect(Collectors.toList());
                            int recieveCount = recvals.stream().collect(Collectors.summingInt(Integer::intValue));
                            int returnCount = retvals.stream().collect(Collectors.summingInt(Integer::intValue));
                            deliveryrecieveLbl.setText("Recieved : "+recieveCount);
                            deliveryreturnLbl.setText("Returned : "+returnCount);
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            deliveryTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
