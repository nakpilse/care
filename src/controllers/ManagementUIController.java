package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import com.sun.javafx.collections.ObservableListWrapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.Toolkit;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import models.Bed;
import models.Item;
import models.Room;
import nse.dcfx.models.Facility;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.LocalDateTimeConverter;
import nse.dcfx.models.SystemLog;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ManagementUIController implements Initializable ,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    
    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private TableView<User> usersTbl;

    @FXML
    private JFXTextField usersearchF;

    @FXML
    private JFXButton userfilterBtn;

    @FXML
    private Label usersresultLbl;

    @FXML
    private JFXButton useraddBtn;

    @FXML
    private TableView<Room> roomsTbl;

    @FXML
    private JFXTextField roomsearchF;

    @FXML
    private JFXButton roomfilterBtn;

    @FXML
    private Label roomresultsLbl;

    @FXML
    private JFXButton addroomBtn;

    @FXML
    private TableView<Bed> bedTbl;

    @FXML
    private Label bedresultsLbl;

    @FXML
    private TableView<?> employeesTbl;
    
    @FXML
    private TableView<Facility> facilityTbl;
    
    @FXML
    private TableView<?> logsTbl;

    @FXML
    private JFXTextField employeessearchF;
    
    @FXML
    private JFXTextField facilitysearchF;
    
    @FXML
    private JFXTextField logssearchF;

    @FXML
    private JFXButton employeesfilterBtn;
    
    @FXML
    private JFXButton facilityfilterBtn;
    
    @FXML
    private JFXButton logsfilterBtn;

    @FXML
    private Label employeesresultLbl;
    
    @FXML
    private Label facilityresultLbl;
    
    @FXML
    private Label logsresultLbl;

    @FXML
    private JFXButton addemployeeBtn;
    
    @FXML
    private JFXButton addfacilityBtn;

    @FXML
    private ToggleGroup menuGroup;
    
    @FXML
    private Tab userTab;

    @FXML
    private Tab roomTab;
    
    @FXML
    private Tab employeeTab;
    
    @FXML
    private Tab facilityTab;
    
    @FXML
    private Tab logsTab;

    @FXML
    private JFXToggleNode userMenu;

    @FXML
    private JFXToggleNode roomMenu;
    
    @FXML
    private JFXToggleNode facilityMenu;

    @FXML
    private JFXToggleNode employeeMenu;
    
    @FXML
    private JFXToggleNode logsMenu;
    
    @FXML
    void addEmployee(ActionEvent event) {

    }

    @FXML
    void addRoom(ActionEvent event) {
        try{
            RoomFormController.showDialog(new Room(), mainStack, maskerPane, ManagementUIController.this);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    @FXML
    void addFacility(ActionEvent event) {        
        try{
            FacilityFormController.showDialog(new Facility(), mainStack, maskerPane, ManagementUIController.this);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void addUser(ActionEvent event) {
        try{
            UserRegisterFormController.showDialog(mainStack,maskerPane, this);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void filterEmployeesTable(ActionEvent event) {

    }

    @FXML
    void filterRoomTable(ActionEvent event) {

    }
    
    @FXML
    void filterFacilityTable(ActionEvent event) {

    }

    @FXML
    void filterUserTable(ActionEvent event) {

    }
    
    @FXML
    void filterLogsTable(ActionEvent event) {

    }

    @FXML
    void loadEmployees(ActionEvent event) {
        mainTabPane.getSelectionModel().select(employeeTab);
    }

    @FXML
    void loadRooms(ActionEvent event) {
        mainTabPane.getSelectionModel().select(roomTab);
        loadRoomList();
    }
    
    @FXML
    void loadFacilities(ActionEvent event) {
        mainTabPane.getSelectionModel().select(facilityTab);
        loadFacilityList();
    }
    
    
    @FXML
    void loadLogs(ActionEvent event) {
        mainTabPane.getSelectionModel().select(logsTab);
        loadSystemLogList();
    }

    @FXML
    void loadUsers(ActionEvent event) {
        mainTabPane.getSelectionModel().select(userTab);
        loadUserList();
    }
    
    private void loadUserList(){
        try{
            FXTask task;
            task = new FXTask(){
                @Override
                protected void load() {
                    try{            
                        
                        FXTable.setFilteredList(usersTbl, new ArrayList());
                        Platform.runLater(()->{
                            usersTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<User> users = SQLTable.list(User.class);
                        FilteredList<User> filteredRecords = FXTable.setFilteredList(usersTbl, users);
                        User.createTableFilter(usersearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends User> c) -> {                   
                            usersresultLbl.setText("Result : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            usersresultLbl.setText("Result : "+filteredRecords.size());                 
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            usersTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadFacilityList(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        FXTable.setFilteredList(facilityTbl, new ArrayList());
                        Platform.runLater(()->{
                            facilityTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<Facility> facilities = SQLTable.list(Facility.class);
                        FilteredList<Facility> filteredRecords = FXTable.setFilteredList(facilityTbl, facilities);
                        Facility.createTableFilter(facilitysearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends Facility> c) -> {                   
                            facilityresultLbl.setText("Result : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            facilityresultLbl.setText("Result : "+filteredRecords.size());                 
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            facilityTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadRoomList(){
        try{
            FXTask task;
            task = new FXTask(){
                @Override
                protected void load() {
                    try{            
                        
                        FXTable.setFilteredList(roomsTbl, new ArrayList());
                        Platform.runLater(()->{
                            roomsTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<Room> rooms = SQLTable.list(Room.class);
                        FilteredList<Room> filteredRecords = FXTable.setFilteredList(roomsTbl, rooms);
                        Room.createTableFilter(roomsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends Room> c) -> {                   
                            roomresultsLbl.setText("Result : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            roomresultsLbl.setText("Result : "+filteredRecords.size());                 
                        });
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            roomsTbl.setPlaceholder(null);                            
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBedList(){
        try{
            Room room = roomsTbl.getSelectionModel().getSelectedItem();
            if(room != null){
                FXTask task;
                task = new FXTask(){
                    @Override
                    protected void load() {
                        try{            

                            FXTable.setFilteredList(bedTbl, new ArrayList());
                            Platform.runLater(()->{
                                bedTbl.setPlaceholder(new MaskerPane());                            
                            });
                            Thread.sleep(200);
                            List<Bed> beds = SQLTable.list(Bed.class,Bed.ROOM_ID+"='"+room.getId()+"'");
                            FXTable.setList(bedTbl, beds);
                            Platform.runLater(()->{
                                bedresultsLbl.setText("Result : "+beds.size());                 
                            });
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }finally {
                            Platform.runLater(()->{
                                bedTbl.setPlaceholder(null);                            
                            });
                        }
                    }
                };
                Care.process(task);
            }else{
                FXTable.setList(bedTbl, new ArrayList());
            }
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    private void loadSystemLogList(){
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
                        List<SystemLog> logs = SQLTable.list(SystemLog.class,SystemLog.LOGTIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(30))+"'");
                        FilteredList<SystemLog> filteredRecords = FXTable.setFilteredList(logsTbl, logs);
                        SystemLog.createTableFilter(logssearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends SystemLog> c) -> {                   
                            logsresultLbl.setText("Result : "+filteredRecords.size());
                        });
                        Platform.runLater(()->{
                            logsresultLbl.setText("Result : "+filteredRecords.size());                 
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
            switch (val) {
                case 0:
                    loadUserList();
                    break;
                case 1:
                    loadRoomList();
                    break;
                case 2:
                    loadBedList();
                    break;
                case 3:
                    loadFacilityList();
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
            
            mainTabPane.addEventFilter(KeyEvent.ANY, event -> {
                if (event.getCode().isArrowKey() && event.getTarget() == mainTabPane) {
                    event.consume();
                }
            });
            
            menuGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                if (newVal == null) {
                    oldVal.setSelected(true);
                }
            });
            
            //User Tab
            usersTbl.getColumns().setAll(FXTable.createTableViewColumns(User.class, false, true));
            FXTable.setSize(FXTable.getColumn(usersTbl, "Id"), 65, 65, 65);
            createUserTableActionColumn();
            usersTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);          
            
            //Facilities Tab
            facilityTbl.getColumns().setAll(FXTable.createTableViewColumns(Facility.class, false, true));
            FXTable.setSize(FXTable.getColumn(facilityTbl, "Id"), 65, 65, 65);
            FXTable.setSize(FXTable.getColumn(facilityTbl, "Name"), 100, 350, 180);
            FXTable.getColumn(facilityTbl, "Core").setVisible(false);
            createFacilityTableActionColumn();
            facilityTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            //Rooms Tab
            roomsTbl.getColumns().setAll(FXTable.createTableViewColumns(Room.class, false, true));
            FXTable.setSize(FXTable.getColumn(roomsTbl, "Id"), 65, 65, 65);
            createRoomsTableActionColumn();
            roomsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);     
            roomsTbl.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
                if(newVal != null){
                    loadBedList();
                }
            });
            
            //Bed Tab
            bedTbl.getColumns().setAll(FXTable.createTableViewColumns(Bed.class, false, true));
            FXTable.getColumn(bedTbl, "Id").setVisible(false);
            FXTable.getColumn(bedTbl, "Available").setVisible(false);
            FXTable.getColumn(bedTbl, "Room_id").setVisible(false);
            createBedsTableActionColumn();
            bedTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);     
            
            //System Logs
            TableColumn timeCol = FXTable.addColumn(logsTbl, "Timestamp", SystemLog::logtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(logsTbl, "Record", SystemLog::logtableProperty, false, 100, 200, 150);
            TableColumn logCol = FXTable.addColumn(logsTbl, "Log", SystemLog::descriptionProperty, false);
            FXTable.addColumn(logsTbl, "Type", SystemLog::logtypeProperty, false, 100, 150, 120);
            FXTable.addColumn(logsTbl, "User", SystemLog::userProperty, false, 100, 200, 150);
            //FXTable.setTimestampColumn(timeCol);
            timeCol.setCellFactory(tc ->{
                TableCell<SystemLog, String> cell = new TableCell<>();
                Label text = new Label();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                cell.setAlignment(Pos.CENTER_LEFT);
                text.setWrapText(true);
                text.textProperty().bindBidirectional(cell.itemProperty(),(StringConverter)new LocalDateTimeConverter());
                
                return cell ;
            });
            
            logCol.setCellFactory(tc -> {
                TableCell<SystemLog, String> cell = new TableCell<>();
                Text text = new Text();
                text.wrappingWidthProperty().bind(logCol.widthProperty().subtract(8));
                //text.setWrapText(true);
                text.textProperty().bind(cell.itemProperty());
                cell.setGraphic(text);
                text.getStyleClass().add("text");
                //text.setPrefHeight(Control.USE_COMPUTED_SIZE);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                return cell ;
            });
            logsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        userMenu.fire();
    }

    @Override
    public void setUIController(UIController controller) {
        
    }
    
    private void createUserTableActionColumn(){
        try{
            TableColumn actionCol = FXTable.addColumn(usersTbl, "Action", User::idProperty, true, 112, 112, 112);
            actionCol.setCellFactory(column -> {
                return new TableCell<User, Integer>() {
                    @Override
                    protected void updateItem(Integer value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                User row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(4);
                                    hbox.setAlignment(Pos.CENTER_LEFT);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.EDIT, "16px", null);
                                    editBtn.getStyleClass().add("btn-success");
                                    editBtn.setStyle("-jfx-button-type: FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    editBtn.setOnAction(evt ->{
                                        UserUpdateFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ManagementUIController.this);
                                    });
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.EYE, "16px", null);
                                    viewBtn.getStyleClass().add("btn-primary");
                                    viewBtn.setStyle("-jfx-button-type: FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    viewBtn.setOnAction(evt ->{
                                        UserInfoFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ManagementUIController.this);
                                    });
                                    
                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.TRASH, "16px", null);
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type: FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setOnAction(evt ->{
                                        try {
                                            JFXButton exitformBtn = new JFXButton("Delete");
                                            exitformBtn.getStyleClass().add("btn-danger");
                                            Image exIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Disposal_48px.png").toURI().toString());
                                                
                                            JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Delete", new Label("Do you want to delete "+row_data.getName()+"?"), FXDialog.DANGER, exIcon,exitformBtn);
                                            exitformBtn.setOnAction(delEvt ->{
                                                if(row_data.delete(true)){
                                                    loadUserList();
                                                    d.close();
                                                }
                                            });
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(ManagementUIController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    
                                    delBtn.setDisable(true);
                                    hbox.getChildren().setAll(editBtn,viewBtn,delBtn);
                                    
                                    setGraphic(hbox);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
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
    
    
    private void createFacilityTableActionColumn(){
        try{
            TableColumn actionCol = FXTable.addColumn(facilityTbl, "Action", Facility::idProperty, true, 76, 76, 76);
            actionCol.setCellFactory(column -> {
                return new TableCell<Facility, Integer>() {
                    @Override
                    protected void updateItem(Integer value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Facility row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(4);
                                    hbox.setAlignment(Pos.CENTER_LEFT);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.EDIT, "16px", null);
                                    editBtn.getStyleClass().add("btn-success");
                                    editBtn.setStyle("-jfx-button-type: FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    editBtn.setOnAction(evt ->{
                                        FacilityFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ManagementUIController.this);
                                    });
                                                                        
                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.TRASH, "16px", null);
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type: FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setOnAction(evt ->{
                                        try {
                                            JFXButton exitformBtn = new JFXButton("Delete");
                                            exitformBtn.getStyleClass().add("btn-danger");
                                            Image exIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Disposal_48px.png").toURI().toString());
                                                
                                            JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Delete", new Label("Do you want to delete "+row_data.getName()+"?"), FXDialog.DANGER, exIcon,exitformBtn);
                                            exitformBtn.setOnAction(delEvt ->{
                                                if(row_data.delete(true)){
                                                    loadFacilityList();
                                                    d.close();
                                                }
                                            });
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(ManagementUIController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    
                                    delBtn.setDisable(row_data.isCore());
                                    hbox.getChildren().setAll(editBtn,delBtn);
                                    
                                    setGraphic(hbox);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
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
    
    private void createRoomsTableActionColumn(){
        try{
            TableColumn actionCol = FXTable.addColumn(roomsTbl, "Action", Room::idProperty, true, 112, 112, 112);
            actionCol.setCellFactory(column -> {
                return new TableCell<Room, Integer>() {
                    @Override
                    protected void updateItem(Integer value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Room row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(4);
                                    hbox.setAlignment(Pos.CENTER_LEFT);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.EDIT, "16px", null);
                                    editBtn.getStyleClass().add("btn-success");
                                    editBtn.setStyle("-jfx-button-type: FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    editBtn.setOnAction(evt ->{
                                        RoomFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ManagementUIController.this);
                                    });
                                    
                                    JFXButton addBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.PLUS, "16px", null);
                                    addBtn.getStyleClass().add("btn-primary");
                                    addBtn.setStyle("-jfx-button-type: FLAT;");
                                    addBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    addBtn.setOnAction(evt ->{
                                        Bed bed = new Bed();
                                        bed.setRoom_id(row_data.getId());
                                        BedFormController.showDialog(bed, mainStack, maskerPane, ManagementUIController.this);
                                    });
                                                                        
                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.TRASH, "16px", null);
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type: FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setOnAction(evt ->{
                                        try {
                                            JFXButton exitformBtn = new JFXButton("Delete");
                                            exitformBtn.getStyleClass().add("btn-danger");
                                            Image exIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Disposal_48px.png").toURI().toString());
                                                
                                            JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Delete", new Label("Do you want to delete "+row_data.getName()+"?"), FXDialog.DANGER, exIcon,exitformBtn);
                                            exitformBtn.setOnAction(delEvt ->{
                                                if(row_data.delete(true)){
                                                    SQLTable.execute("DELETE FROM "+Bed.TABLE_NAME+" WHERE "+Bed.ROOM_ID+"='"+row_data.getId()+"'");
                                                    FXTable.setList(bedTbl, new ArrayList());
                                                    loadRoomList();
                                                    d.close();
                                                }
                                            });
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(ManagementUIController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    
                                    //delBtn.setDisable(true);
                                    hbox.getChildren().setAll(editBtn,addBtn,delBtn);
                                    
                                    setGraphic(hbox);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
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
    
    private void createBedsTableActionColumn(){
        try{
            TableColumn actionCol = FXTable.addColumn(bedTbl, "Action", Bed::idProperty, true, 76, 76, 76);
            actionCol.setCellFactory(column -> {
                return new TableCell<Bed, Integer>() {
                    @Override
                    protected void updateItem(Integer value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Bed row_data = getTableView().getItems().get(getIndex());
                                if(row_data != null){
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(4);
                                    hbox.setAlignment(Pos.CENTER_LEFT);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.EDIT, "16px", null);
                                    editBtn.getStyleClass().add("btn-success");
                                    editBtn.setStyle("-jfx-button-type: FLAT;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    editBtn.setOnAction(evt ->{
                                        BedFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ManagementUIController.this);
                                    });
                                                                        
                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 30, "cell-btn", FontAwesomeIcon.TRASH, "16px", null);
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type: FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    delBtn.setOnAction(evt ->{
                                        try {
                                            JFXButton exitformBtn = new JFXButton("Delete");
                                            exitformBtn.getStyleClass().add("btn-danger");
                                            Image exIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Disposal_48px.png").toURI().toString());
                                                
                                            JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Delete", new Label("Do you want to delete "+row_data.getName()+"?"), FXDialog.DANGER, exIcon,exitformBtn);
                                            exitformBtn.setOnAction(delEvt ->{
                                                if(row_data.delete(true)){                                                    
                                                    loadBedList();
                                                    d.close();
                                                }
                                            });
                                        } catch (URISyntaxException ex) {
                                            Logger.getLogger(ManagementUIController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                    
                                    //delBtn.setDisable(true);
                                    hbox.getChildren().setAll(editBtn,delBtn);
                                    
                                    setGraphic(hbox);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                }else{
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
    public void setMaskerPane(MaskerPane masker) {
        this.maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }

    @Override
    public void loadUIFixes() {
        
    }
    
}
