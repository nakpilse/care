package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Control;
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
import javafx.scene.text.Text;
import models.DeliveryItem;
import models.HospitalChargeItem;
import models.Item;
import models.ReturnedItem;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.NumberKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ItemFormController implements Initializable,FormController<Item> {

    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Item record = null;
    private Map<String,String> opts;
    
    @FXML
    private Label titleLbl;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode infoMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode soldMenu;

    @FXML
    private JFXToggleNode soldMenu1;

    @FXML
    private JFXToggleNode deliveryMenu;

    @FXML
    private JFXToggleNode logsMenu;

    @FXML
    private Tab infoTab;

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
    private JFXTextField remarksF;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private Tab soldTab;

    @FXML
    private TableView<HospitalChargeItem> soldTbl;

    @FXML
    private JFXTextField soldsearchF;

    @FXML
    private Label soldresultLbl;

    @FXML
    private Label soldqtyLbl;

    @FXML
    private Label soldcostsLbl;

    @FXML
    private Label soldamtsLbl;

    @FXML
    private Tab returnsTab;

    @FXML
    private TableView<ReturnedItem> returnsTbl;

    @FXML
    private JFXTextField returnsearchF;

    @FXML
    private Label returnresultLbl;


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
    
    @FXML
    private JFXTabPane mainTab;
    
     @FXML
    private Label opt1T;

    @FXML
    private Label opt2T;

    @FXML
    private Label idL;

    @FXML
    private Label codeL;

    @FXML
    private Label descriptionL;

    @FXML
    private Label genericnameL;

    @FXML
    private Label measureL;

    @FXML
    private Label formL;

    @FXML
    private Label strL;

    @FXML
    private Label typeL;

    @FXML
    private Label categoryL;

    @FXML
    private Label opt1L;
    
    @FXML
    private Label opt2L;

    @FXML
    private Label qtyL;

    @FXML
    private Label reorderL;

    @FXML
    private Label costL;

    @FXML
    private Label sellingL;

    @FXML
    private Label pnfL;

    @FXML
    private Label vatableL;

    @FXML
    private Label stockedL;

    @FXML
    private Label availableL;

    @FXML
    private Label archievedL;

    @FXML
    private Label supplyL;

    @FXML
    private Label remarksL;

    @FXML
    private Label updatedbyL;


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
                    btn.getStyleClass().add("btn-success");
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
                if(record.update()){
                    SystemLog log = new SystemLog();
                    log.setLogtable(Item.TABLE_NAME);
                    log.setLogtableid(record.getId());
                    log.setDescription(record.toJSON().toJSONString());
                    log.setLogtype(SystemLog.UPDATE);
                    log.setRemarks(remarksF.getText());
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
        loadItemSystemLogs();
    }

    @FXML
    void loadReturns(ActionEvent event) {
        mainTab.getSelectionModel().select(returnsTab);
        loadItemReturns();
    }

    @FXML
    void loadSold(ActionEvent event) {
        mainTab.getSelectionModel().select(soldTab);
        loadItemSolds();
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
            if(UI_CONTROLLER instanceof InventoryUIController){
                UI_CONTROLLER.reloadReferences(0);
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
            
            //System Logs
            TableColumn timeCol = FXTable.addColumn(logsTbl, "Timestamp", SystemLog::logtimeProperty, false, 140, 140, 140);
            TableColumn remarksCol = FXTable.addColumn(logsTbl, "Remarks", SystemLog::remarksProperty,false);
            FXTable.setTimestampColumn(timeCol);
            remarksCol.setCellFactory(tc -> {
                TableCell<SystemLog, String> cell = new TableCell<>();
                Text text = new Text();
                text.wrappingWidthProperty().bind(remarksCol.widthProperty().subtract(8));
                text.textProperty().bind(cell.itemProperty());
                cell.setGraphic(text);
                text.getStyleClass().add("text");
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                return cell ;
            });
            logsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            logsTbl.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                
                if(newVal != null){
                    Platform.runLater(()->{
                        Item i = new Item().parseJSON(newVal.getDescription());      
                        if(i != null){
                            idL.setText(String.valueOf(i.getId()));
                            codeL.setText(i.getCode());
                            descriptionL.setText(i.getDescription());
                            genericnameL.setText(i.getGenericname());
                            measureL.setText(i.getUnitmeasure());
                            formL.setText(i.getForm());
                            strL.setText(i.getStrength());
                            typeL.setText(i.getType());
                            categoryL.setText(i.getCategory());
                            opt1L.setText(i.getOpt1());
                            opt2L.setText(i.getOpt2());
                            qtyL.setText(String.valueOf(i.getQuantity()));
                            reorderL.setText(String.valueOf(i.getReorderquantity()));
                            costL.setText(NumberKit.toCurrency(i.getCost()));
                            sellingL.setText(NumberKit.toCurrency(i.getPrice()));

                            pnfL.setText((i.isPnf())? "Yes":"No");
                            pnfL.setStyle((i.isPnf())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            vatableL.setText((i.isVatable())? "Yes":"No");
                            vatableL.setStyle((i.isVatable())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            stockedL.setText((i.isStocked())? "Yes":"No");
                            stockedL.setStyle((i.isStocked())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            availableL.setText((i.isAvailable())? "Yes":"No");
                            availableL.setStyle((i.isAvailable())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            archievedL.setText((i.isArchive())? "Yes":"No");
                            archievedL.setStyle((i.isArchive())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            supplyL.setText((i.isSupply())? "Yes":"No");
                            supplyL.setStyle((i.isSupply())? "-fx-text-fill:success-color;":"-fx-text-fill:dark-color;");

                            remarksL.setText(newVal.getRemarks());
                            updatedbyL.setText(newVal.getUser()+" / "+DateTimeKit.toProperTimestamp(newVal.getLogtime()));
                        }else{
                            Platform.runLater(()->{
                                idL.setText("");
                                codeL.setText("");
                                descriptionL.setText("");
                                genericnameL.setText("");
                                measureL.setText("");
                                formL.setText("");
                                strL.setText("");
                                typeL.setText("");
                                categoryL.setText("");
                                opt1L.setText("");
                                opt2L.setText("");
                                qtyL.setText("");
                                reorderL.setText("");
                                costL.setText("");
                                sellingL.setText("");
                                pnfL.setText("");
                                vatableL.setText("");
                                stockedL.setText("");
                                availableL.setText("");
                                archievedL.setText("");
                                supplyL.setText("");
                                remarksL.setText("");
                                updatedbyL.setText("");
                            });
                        }
                    });                    
                }else{
                    Platform.runLater(()->{
                        idL.setText("");
                        codeL.setText("");
                        descriptionL.setText("");
                        genericnameL.setText("");
                        measureL.setText("");
                        formL.setText("");
                        strL.setText("");
                        typeL.setText("");
                        categoryL.setText("");
                        opt1L.setText("");
                        opt2L.setText("");
                        qtyL.setText("");
                        reorderL.setText("");
                        costL.setText("");
                        sellingL.setText("");
                        pnfL.setText("");
                        vatableL.setText("");
                        stockedL.setText("");
                        availableL.setText("");
                        archievedL.setText("");
                        supplyL.setText("");
                        remarksL.setText("");
                        updatedbyL.setText("");
                    });
                    
                }
            });
            //Delivery
            TableColumn deltimeCol = FXTable.addColumn(deliveryTbl, "Date", DeliveryItem::deliverydateProperty, false, 85, 85, 85);
            FXTable.addColumn(deliveryTbl, "Supplier", DeliveryItem::supplierProperty,false);
            FXTable.addColumn(deliveryTbl, "Qty", DeliveryItem::itemqtyProperty,false,65,65,65);
            FXTable.addColumn(deliveryTbl, "Type", DeliveryItem::typeProperty,false,80,80,80);
            FXTable.addColumn(deliveryTbl, "Remarks", DeliveryItem::remarksProperty,false);
            TableColumn delcancelCol = FXTable.addColumn(deliveryTbl, "Canceled", DeliveryItem::canceldateProperty,false,150,150,150);
            TableColumn actCol = FXTable.addColumn(deliveryTbl, "View", DeliveryItem::descriptionProperty, false, 44, 44, 44);
            FXTable.setDateColumn(deltimeCol);
            FXTable.setTimestampColumn(delcancelCol);
            
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
            
            //SoldItems
            soldTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(soldTbl, "Charge #", HospitalChargeItem::chargenumberProperty, false);
            FXTable.addColumn(soldTbl, "Charged to", HospitalChargeItem::chargetoProperty, false);
            FXTable.addColumn(soldTbl, "Quantity", HospitalChargeItem::quantityProperty, false);
            TableColumn soldactCol = FXTable.addColumn(soldTbl, " ", HospitalChargeItem::chargenumberProperty, false, 40, 40, 40);
                        
            soldactCol.setCellFactory(column -> {
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
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(stackPane,"Charge Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        }
                    }
                };
            });
            
            //ReturnedItems
            returnsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn returntimeCol = FXTable.addColumn(returnsTbl, "Timestamp", ReturnedItem::returntimeProperty, false);
            FXTable.addColumn(returnsTbl, "Returned By", ReturnedItem::returnedbyProperty, false);
            FXTable.addColumn(returnsTbl, "Quantity", ReturnedItem::quantityProperty, false);
            TableColumn returnactCol = FXTable.addColumn(returnsTbl, " ", ReturnedItem::descriptionProperty, false, 40, 40, 40);
            FXTable.setTimestampColumn(returntimeCol);
                        
            returnactCol.setCellFactory(column -> {
                return new TableCell<ReturnedItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                ReturnedItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(stackPane,"Return Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
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
            opts = GlobalOption.getMap(GlobalOption.ITEM_CATEGORY);
            opt1F.setPromptText(opts.get("ITEM_OPT1"));
            opt2F.setPromptText(opts.get("ITEM_OPT2"));
            opt1T.setText(opts.get("ITEM_OPT1")+" : ");
            opt2T.setText(opts.get("ITEM_OPT2")+" : ");
            
            List<String> types = SQLTable.distinct(Item.class, Item.TYPE);
            List<String> categories = SQLTable.distinct(Item.class, Item.CATEGORY);
            List<String> measures = SQLTable.distinct(Item.class, Item.UNITMEASURE);
            List<String> forms = SQLTable.distinct(Item.class, Item.FORM);
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
                    titleLbl.setText("Hospital Supply Information");                    
                }else{
                    titleLbl.setText("Pharmacy Item Information");
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
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/ItemForm.fxml"));
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
    
    private void loadItemSystemLogs(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        FXTable.setFilteredList(logsTbl, new ArrayList());
                        Platform.runLater(()->{
                            logsTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<SystemLog> logs = SQLTable.list(SystemLog.class,SystemLog.LOGTABLE+"='"+Item.TABLE_NAME+"' AND "+SystemLog.LOGTABLEID+"='"+record.getId()+"' ORDER BY "+SystemLog.LOGTIME+" DESC");
                        FilteredList<SystemLog> filteredRecords = FXTable.setFilteredList(logsTbl, logs);
                        SystemLog.createTableFilter(logsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends SystemLog> c) -> {                   
                            logresultsLbl.setText("Results : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            logresultsLbl.setText("Results : "+filteredRecords.size());                 
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            logsTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
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
                        List<DeliveryItem> records = SQLTable.list(DeliveryItem.class,DeliveryItem.ITEM_ID+"='"+record.getId()+"' ORDER BY "+DeliveryItem.ID+" DESC");
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
    
    private void loadItemSolds(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        FXTable.setFilteredList(soldTbl, new ArrayList());
                        Platform.runLater(()->{
                            soldTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<HospitalChargeItem> records = SQLTable.list(HospitalChargeItem.class,HospitalChargeItem.ITEMTABLEID+"='"+record.getId()+"' ORDER BY "+HospitalChargeItem.CHARGETIME+" DESC");
                        FilteredList<HospitalChargeItem> filteredRecords = FXTable.setFilteredList(soldTbl, records);
                        HospitalChargeItem.createTableFilter(soldsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends HospitalChargeItem> c) -> {                   
                            soldresultLbl.setText("Results : "+filteredRecords.size());
                            List<Integer> soldqtys= filteredRecords.stream().map(d -> d.getQuantity()).collect(Collectors.toList());
                            List<Double> soldcosts = filteredRecords.stream().map(d -> d.getTotalcost()).collect(Collectors.toList());
                            List<Double> soldprices = filteredRecords.stream().map(d -> d.getNetsales()).collect(Collectors.toList());
                            int soldCount = soldqtys.stream().collect(Collectors.summingInt(Integer::intValue));
                            double totalcosts = soldcosts.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            double totalsellings = soldprices.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            soldqtyLbl.setText("Quantity : "+soldCount);
                            soldcostsLbl.setText("Total Sold Cost : "+NumberKit.toCurrency(totalcosts));
                            soldamtsLbl.setText("Total Amount Sold : "+NumberKit.toCurrency(totalsellings));
                        });
                        Platform.runLater(()->{
                            soldresultLbl.setText("Results : "+filteredRecords.size());
                            List<Integer> soldqtys= filteredRecords.stream().map(d -> d.getQuantity()).collect(Collectors.toList());
                            List<Double> soldcosts = filteredRecords.stream().map(d -> d.getTotalcost()).collect(Collectors.toList());
                            List<Double> soldprices = filteredRecords.stream().map(d -> d.getNetsales()).collect(Collectors.toList());
                            int soldCount = soldqtys.stream().collect(Collectors.summingInt(Integer::intValue));
                            double totalcosts = soldcosts.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            double totalsellings = soldprices.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            soldqtyLbl.setText("Quantity : "+soldCount);
                            soldcostsLbl.setText("Total Sold Cost : "+NumberKit.toCurrency(totalcosts));
                            soldamtsLbl.setText("Total Amount Sold : "+NumberKit.toCurrency(totalsellings));
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            soldTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadItemReturns(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        FXTable.setFilteredList(returnsTbl, new ArrayList());
                        Platform.runLater(()->{
                            returnsTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<ReturnedItem> records = SQLTable.list(ReturnedItem.class,ReturnedItem.ITEMTABLEID+"='"+record.getId()+"' ORDER BY "+ReturnedItem.RETURNTIME+" DESC");
                        FilteredList<ReturnedItem> filteredRecords = FXTable.setFilteredList(returnsTbl, records);
                        ReturnedItem.createTableFilter(returnsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends ReturnedItem> c) -> {                   
                            returnresultLbl.setText("Results : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            returnresultLbl.setText("Results : "+filteredRecords.size());
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            returnsTbl.setPlaceholder(null);                            
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
