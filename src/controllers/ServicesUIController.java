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
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import models.HospitalService;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ServicesUIController implements Initializable,UIController {

    private StackPane mainStack;
    private MaskerPane maskerPane;
    @FXML
    private JFXTabPane mainTab;

    @FXML
    private Tab servicesTab;

    @FXML
    private TableView<HospitalService> t1Tbl;

    @FXML
    private JFXTextField t1searchF;

    @FXML
    private JFXButton t1addBtn;

    @FXML
    private Label t1resultLbl;

    @FXML
    private Tab labservicesTab;

    @FXML
    private TableView<HospitalService> t2Tbl;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private JFXButton t2addBtn;

    @FXML
    private Label t2resultLbl;

    @FXML
    private Tab radservicesTab;

    @FXML
    private TableView<HospitalService> t3Tbl;

    @FXML
    private JFXTextField t3searchF;

    @FXML
    private JFXButton t3addBtn;

    @FXML
    private Label t3resultLbl;

    @FXML
    private Tab roomsTab;

    @FXML
    private TableView<HospitalService> t4Tbl;

    @FXML
    private JFXTextField t4searchF;

    @FXML
    private JFXButton t4addBtn;

    @FXML
    private Label t4resultLbl;

    @FXML
    private Tab packagesTab;

    @FXML
    private TableView<?> t5Tbl;

    @FXML
    private JFXTextField t5searchF;

    @FXML
    private JFXButton t5addBtn;

    @FXML
    private Label t5resultLbl;

    @FXML
    private JFXToggleNode servicesBtn;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode labservicesBtn;

    @FXML
    private JFXToggleNode radservicesBtn;

    @FXML
    private JFXToggleNode roomratesBtn;

    @FXML
    private JFXToggleNode packagesBtn;

    @FXML
    void addLabService(ActionEvent event) {        
        HospitalService srv = new HospitalService();
        srv.setFacility("LABORATORY");
        ServiceFormController.showDialog(srv, mainStack, maskerPane, this);
    }

    @FXML
    void addPackage(ActionEvent event) {

    }

    @FXML
    void addRadService(ActionEvent event) {
        HospitalService srv = new HospitalService();
        srv.setFacility("RADIOLOGY");
        ServiceFormController.showDialog(srv, mainStack, maskerPane, this);
    }

    @FXML
    void addRoomRate(ActionEvent event) {

    }

    @FXML
    void addService(ActionEvent event) {
        ServiceFormController.showDialog(new HospitalService(), mainStack, maskerPane, this);
    }

    @FXML
    void allServices(ActionEvent event) {
        mainTab.getSelectionModel().select(servicesTab);
        loadServicesList(null);
    }
        
    @FXML
    void labServices(ActionEvent event) {
        mainTab.getSelectionModel().select(labservicesTab);
        loadLaboratorySericeList(null);
    }
    
    @FXML
    void radServices(ActionEvent event) {
        mainTab.getSelectionModel().select(radservicesTab);
        loadRadiologySericeList(null);
    }

    @FXML
    void roomRates(ActionEvent event) {
        mainTab.getSelectionModel().select(roomsTab);
        loadRoomRatesList(null);
    }

    @FXML
    void packages(ActionEvent event) {
        mainTab.getSelectionModel().select(packagesTab);
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
            loadServicesList(null);
            switch (val) {
                case 0:                    
                    break;
                case 1:
                    loadLaboratorySericeList(null);
                    break;
                case 2:
                    loadRadiologySericeList(null);
                    break;
                case 3:
                    loadRoomRatesList(null);
                    break;
                default:
                    break;
            }
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
            loadHospitalServicesTab();
            loadLaboratoryServicesTab();
            loadRadiologyServicesTab();
            loadRoomRatesTab();
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
            loadServicesList("");
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

    private void loadHospitalServicesTab(){
        try{
            t1Tbl.setEditable(true);
            t1Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t1Tbl, "ID", HospitalService::idProperty, false, 100, 100, 100);
            FXTable.addColumn(t1Tbl, "Service Name", HospitalService::descriptionProperty, false);
            FXTable.addColumn(t1Tbl, "Department", HospitalService::facilityProperty,  false);
            FXTable.addColumn(t1Tbl, "Category", HospitalService::categoryProperty,  false);
            FXTable.addColumn(t1Tbl, "Group", HospitalService::grpProperty,  false);
            FXTable.addColumn(t1Tbl, "Price", HospitalService::priceProperty,  false,120,120,120);
            TableColumn ActCol = FXTable.addColumn(t1Tbl, " ", HospitalService::descriptionProperty, false,108,108,108);
                    
            ActCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(158, 32);
                                    container.setMaxSize(158, 32);
                                    container.setPrefSize(158, 32);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Service Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ServiceFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ServicesUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this service?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

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
    
    private void loadLaboratoryServicesTab(){
        try{
            t2Tbl.setEditable(true);
            t2Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t2Tbl, "ID", HospitalService::idProperty, false, 100, 100, 100);
            FXTable.addColumn(t2Tbl, "Service Name", HospitalService::descriptionProperty, false);
            FXTable.addColumn(t2Tbl, "Category", HospitalService::categoryProperty,  false);
            FXTable.addColumn(t2Tbl, "Group", HospitalService::grpProperty,  false);
            FXTable.addColumn(t2Tbl, "Price", HospitalService::priceProperty,  false,120,120,120);
            TableColumn ActCol = FXTable.addColumn(t2Tbl, " ", HospitalService::descriptionProperty, false,108,108,108);
                    
            ActCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(158, 32);
                                    container.setMaxSize(158, 32);
                                    container.setPrefSize(158, 32);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Service Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ServiceFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ServicesUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this service?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            
                                        });
                                    });
                                    
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

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
    
    private void loadRadiologyServicesTab(){
        try{
            t3Tbl.setEditable(true);
            t3Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t3Tbl, "ID", HospitalService::idProperty, false, 100, 100, 100);
            FXTable.addColumn(t3Tbl, "Service Name", HospitalService::descriptionProperty, false);
            FXTable.addColumn(t3Tbl, "Category", HospitalService::categoryProperty,  false);
            FXTable.addColumn(t3Tbl, "Group", HospitalService::grpProperty,  false);
            FXTable.addColumn(t3Tbl, "Price", HospitalService::priceProperty,  false,120,120,120);
            TableColumn ActCol = FXTable.addColumn(t3Tbl, " ", HospitalService::descriptionProperty, false,108,108,108);
                    
            ActCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(158, 32);
                                    container.setMaxSize(158, 32);
                                    container.setPrefSize(158, 32);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Service Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ServiceFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ServicesUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this service?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

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
    
    private void loadRoomRatesTab(){
        try{
            t4Tbl.setEditable(true);
            t4Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.addColumn(t4Tbl, "ID", HospitalService::idProperty, false, 100, 100, 100);
            FXTable.addColumn(t4Tbl, "Room Rate", HospitalService::descriptionProperty, false);
            FXTable.addColumn(t4Tbl, "Price", HospitalService::priceProperty,  false,120,120,120);
            TableColumn ActCol = FXTable.addColumn(t4Tbl, " ", HospitalService::descriptionProperty, false,108,108,108);
                    
            ActCol.setCellFactory(column -> {
                return new TableCell<HospitalService, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalService row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(158, 32);
                                    container.setMaxSize(158, 32);
                                    container.setPrefSize(158, 32);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Room Rate Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ServiceFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ServicesUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this rate?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn, delBtn);

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
    
    private void loadServicesList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        FXTable.setFilteredList(t1Tbl, new ArrayList());
                        Platform.runLater(() -> {
                            t1Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<HospitalService> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalService.class);
                        } else {
                            records = SQLTable.list(HospitalService.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<HospitalService> filteredRecords = FXTable.setFilteredList(t1Tbl, records);
                            HospitalService.createTableFilter(t1searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends HospitalService> c) -> {
                                t1resultLbl.setText("Result : " + filteredRecords.size());
                            });
                            t1resultLbl.setText("Result : " + filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t1Tbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadLaboratorySericeList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        FXTable.setFilteredList(t2Tbl, new ArrayList());
                        Platform.runLater(() -> {
                            t2Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<HospitalService> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalService.class, HospitalService.FACILITY+" LIKE '%laboratory%'");
                        } else {
                            records = SQLTable.list(HospitalService.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<HospitalService> filteredRecords = FXTable.setFilteredList(t2Tbl, records);
                            HospitalService.createTableFilter(t2searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends HospitalService> c) -> {
                                t2resultLbl.setText("Result : " + filteredRecords.size());
                            });
                            t2resultLbl.setText("Result : " + filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t2Tbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRadiologySericeList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        FXTable.setFilteredList(t3Tbl, new ArrayList());
                        Platform.runLater(() -> {
                            t3Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<HospitalService> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalService.class, HospitalService.FACILITY+" LIKE '%radiology%'");
                        } else {
                            records = SQLTable.list(HospitalService.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<HospitalService> filteredRecords = FXTable.setFilteredList(t3Tbl, records);
                            HospitalService.createTableFilter(t3searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends HospitalService> c) -> {
                                t3resultLbl.setText("Result : " + filteredRecords.size());
                            });
                            t3resultLbl.setText("Result : " + filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t3Tbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRoomRatesList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        FXTable.setFilteredList(t4Tbl, new ArrayList());
                        Platform.runLater(() -> {
                            t4Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<HospitalService> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalService.class, HospitalService.FACILITY+" LIKE '%room%'");
                        } else {
                            records = SQLTable.list(HospitalService.class, conditions);
                        }
                        
                        Platform.runLater(() -> {
                            FilteredList<HospitalService> filteredRecords = FXTable.setFilteredList(t4Tbl, records);
                            HospitalService.createTableFilter(t4searchF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends HospitalService> c) -> {
                                t4resultLbl.setText("Result : " + filteredRecords.size());
                            });
                            t4resultLbl.setText("Result : " + filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t4Tbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
}
