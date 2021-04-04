package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.LocalDateTimeConverter;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.SystemLog;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLTable;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class UserInfoFormController implements Initializable,FormController<User> {
    
    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private User record = null;
    private static PopOver popover = new PopOver();
    
    @FXML
    private JFXTabPane mainTabPane;
    
    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode profileMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode logsMenu;

    @FXML
    private Tab profileTab;

    @FXML
    private JFXTextField idF;

    @FXML
    private JFXTextField userF;

    @FXML
    private JFXTextField nameF;

    @FXML
    private JFXTextField roleF;

    @FXML
    private Circle imageCircle;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private FontAwesomeIconView saveIcon;

    @FXML
    private Tab logsTab;

    @FXML
    private TableView<SystemLog> logsTbl;

    @FXML
    private JFXTextField logsearchF;

    @FXML
    private JFXButton logsfilterBtn;

    @FXML
    void filterLogsTable(ActionEvent event) {

    }

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        if(isFieldInputsValid()){
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            saveBtn.setDisable(true);
                            closeBtn.setDisable(true);
                            JFXSpinner spinner = new JFXSpinner();
                            spinner.setPrefSize(18, 18);
                            saveBtn.setGraphic(spinner);
                        });
                        Thread.sleep(200);
                        if(record.update(true,User.NAME)){
                            Platform.runLater(()->{       
                                FXDialog.showMessageDialog(stackPane, "Success", "User has been updated!", FXDialog.SUCCESS);
                            });
                            postAction();
                        }else{
                            Platform.runLater(()->{
                                FXDialog.showMessageDialog(stackPane, "Error", "Failed to update user!", FXDialog.DANGER);
                            });
                        }
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            saveBtn.setDisable(false);
                            saveBtn.setGraphic(saveIcon);
                            closeBtn.setDisable(false);
                        });
                    }
                }
            };
            Care.process(task);
        }
    }

    @FXML
    void loadLogs(ActionEvent event) {
        mainTabPane.getSelectionModel().select(logsTab);
        loadSystemLogList();
    }

    @FXML
    void loadProfile(ActionEvent event) {
        mainTabPane.getSelectionModel().select(profileTab);
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
            if(UI_CONTROLLER instanceof ManagementUIController){
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
    public void setFormObject(User obj) {
        this.record = obj;
    }

    @Override
    public User getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return nameF.validate();
    }

    @Override
    public void loadBindings() {
        try{
            idF.textProperty().bindBidirectional(record.idProperty(), new NumberConverter());
            userF.textProperty().bindBidirectional(record.usernameProperty());
            nameF.textProperty().bindBidirectional(record.nameProperty());
            roleF.textProperty().bindBidirectional(record.roleProperty());
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try{
            TableColumn timeCol = FXTable.addColumn(logsTbl, "Timestamp", SystemLog::logtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(logsTbl, "Record", SystemLog::logtableProperty,false, 100, 200, 125).setVisible(false);
            FXTable.addColumn(logsTbl, "Deskcription", SystemLog::descriptionProperty, false).setVisible(false);
            TableColumn logCol = FXTable.addColumn(logsTbl, "Log", SystemLog::descriptionProperty, false);
            FXTable.addColumn(logsTbl, "Type", SystemLog::logtypeProperty,false, 100, 150, 125).setVisible(false);
            FXTable.setTimestampColumn(timeCol);
            
            logCol.setCellFactory(column -> {
                    return new TableCell<SystemLog, String>() {
                        @Override
                        protected void updateItem(String value, boolean empty) {
                            super.updateItem(value, empty);
                            if (empty) {
                                setGraphic(null);
                                setStyle("");
                            } else {
                                try {
                                    SystemLog row_data = getTableView().getItems().get(getIndex());
                                    if(row_data != null){
                                        
                                        Label type = new Label(row_data.getLogtype()+"\n");
                                        Label src = new Label(row_data.getLogtable()+"\n");                                        
                                        Label log = new Label(row_data.getDescription());     
                                        log.setWrapText(true);
                                        
                                        
                                        VBox tf = new VBox();                                        
                                        setPrefHeight(tf.prefHeight(logCol.getWidth()) + 4);
                                        logCol.widthProperty().addListener((v, o, n) -> setPrefHeight(tf.prefHeight(n.doubleValue()) + 4));
                                        
                                        tf.getChildren().addAll(type,src,log);
                                        
                                        log.setOnMouseEntered(evt->{
                                            Text t = new Text();
                                            t.wrappingWidthProperty().setValue(250);
                                            t.setText(row_data.getDescription());
                                            TextFlow f = new TextFlow();
                                            f.setPadding(new Insets(10,10,10,10));
                                            f.setMaxWidth(250);
                                            f.setPrefWidth(250);
                                            
                                            f.getChildren().add(t);
                                            
                                            popover.setContentNode(f);
                                            popover.show(log);  
                                            ((Parent) popover.getSkin().getNode()).getStylesheets().add(getClass().getResource("/views/Styles.css").toExternalForm());                                               
                                            popover.show(log);  
                                        });
                                        
                                        
                                        setGraphic(tf);   
                                        setStyle("");      
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
            
            logsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            
            //Popover
            popover.setHideOnEscape(true);     
            popover.setAutoHide(true);            
            popover.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
            
            mainTabPane.getStylesheets().add(PopOver.class.getResource("popover.css").toExternalForm());
            mainTabPane.getStyleClass().add("popover"); 
            
            
            //Icon
            Image img = new Image(getClass().getResource("/assets/avatar.png").toURI().toString());
            ImagePattern p = new ImagePattern(img);
            imageCircle.setFill(p);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.addRequiredValidator(nameF);
            FXField.addFocusValidationListener(nameF);
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
            if(record.getId() == Care.getUser().getId()){
                nameF.setEditable(true);
                saveBtn.setVisible(true);
            }else{
                nameF.setEditable(false);
                saveBtn.setVisible(false);
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static FXDialogTask showDialog(User user,StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();                
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/UserInfoForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog,user, ui_controller, 450, 600);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;             
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(UserUpdateFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }
    
    private void loadSystemLogList(){
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        logsTbl.getItems().clear();
                        Platform.runLater(()->{
                            logsTbl.setPlaceholder(new MaskerPane());                            
                        });
                        Thread.sleep(200);
                        List<SystemLog> logs = SQLTable.list(SystemLog.class,SystemLog.LOGTIME+">='"+java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(30))+"' AND "+SystemLog.USER_ID+"="+record.getId());
                        FXTable.setList(logsTbl, logs);
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
}
