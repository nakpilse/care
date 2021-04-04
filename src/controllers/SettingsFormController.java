package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.models.LocalOption;
import org.controlsfx.control.MaskerPane;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.SelectionMode;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FXTextFieldTableCell;
import nse.dcfx.files.DBConfig;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.xml.XMLFactory;
public class SettingsFormController implements Initializable, FormController {

    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private DBConfig dbconf = null;

    
    @FXML
    private ToggleGroup menuGroup;
    
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab globalTab;

    @FXML
    private TableView<GlobalOption> globalTbl;

    @FXML
    private JFXButton globalBtn;

    @FXML
    private Tab localTab;

    @FXML
    private TableView<LocalOption> localTbl;

    @FXML
    private JFXButton localBtn;

    @FXML
    private Tab ftpTab;

    @FXML
    private JFXTextField ftpaddressF;

    @FXML
    private JFXTextField ftpuserF;

    @FXML
    private JFXPasswordField ftppasswordF;

    @FXML
    private JFXTextField ftpportF;

    @FXML
    private JFXButton ftpBtn;

    @FXML
    private Tab dbTab;

    @FXML
    private JFXTextField dbaddressF;

    @FXML
    private JFXTextField dbportF;

    @FXML
    private JFXTextField dbuserF;

    @FXML
    private JFXTextField dbnameF;

    @FXML
    private JFXPasswordField dbpassF;

    @FXML
    private JFXButton dbBtn;

    @FXML
    private Tab infoTab;

    @FXML
    private JFXButton versionBtn;

    @FXML
    private Tab changelogTab;

    @FXML
    private TextArea changelogF;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXToggleNode dbMenu;

    @FXML
    private JFXToggleNode ftpMenu;

    @FXML
    private JFXToggleNode globalMenu;

    @FXML
    private JFXToggleNode localConfig;

    @FXML
    private JFXToggleNode infoMenu;

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void loadChangelog(ActionEvent event) {
         tabPane.getSelectionModel().select(changelogTab);
    }

    @FXML
    void loadDatabase(ActionEvent event) {
        tabPane.getSelectionModel().select(dbTab);
        try{
            dbconf = DBConfig.load(Care.getDBConfigFile());
            if(dbconf != null){
                dbaddressF.textProperty().unbind();
                dbportF.textProperty().unbind();
                dbnameF.textProperty().unbind();
                dbuserF.textProperty().unbind();
                dbpassF.textProperty().unbind();
                
                dbaddressF.textProperty().bindBidirectional(dbconf.addressProperty());
                dbportF.textProperty().bindBidirectional(dbconf.portProperty());
                dbnameF.textProperty().bindBidirectional(dbconf.databaseProperty());
                dbuserF.textProperty().bindBidirectional(dbconf.usernameProperty());
                dbpassF.textProperty().bindBidirectional(dbconf.passwordProperty());
            }
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadFTP(ActionEvent event) {
         tabPane.getSelectionModel().select(ftpTab);
    }

    @FXML
    void loadGlobal(ActionEvent event) {
         tabPane.getSelectionModel().select(globalTab);
         try{
             FXTask task = new FXTask(){
                 @Override
                 protected void load() {
                     try {
                        FXTable.setList(globalTbl, new ArrayList());
                        Platform.runLater(()->{
                            globalTbl.setPlaceholder(new MaskerPane());
                        });                         
                         
                         Thread.sleep(500);
                         List<GlobalOption> opts = SQLTable.list(GlobalOption.class);   
                         FXTable.setList(globalTbl, opts);
                     } catch (InterruptedException ex) {
                         Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, ex);
                     }finally {
                        Platform.runLater(()->{
                            globalTbl.setPlaceholder(null);
                        });  
                     }
                 }                 
             };
             Care.process(task);
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadInfo(ActionEvent event) {
         tabPane.getSelectionModel().select(infoTab);
    }

    @FXML
    void loadLocal(ActionEvent event) {
        tabPane.getSelectionModel().select(localTab);         
        try{
            List<LocalOption> opts = new ArrayList();
            Care.LOCAL_OPTIONS.stream().forEach(opt->{
                opts.add(opt.getModelClone());
            });
            FXTable.setList(localTbl, opts);            
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void saveDatabase(ActionEvent event) {
        try{
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            dbBtn.setDisable(true);
                        });     
                        dbconf.save(Care.getDBConfigFile());
                        Care.CONFIG = dbconf;
                        Care.loadConnection();                   
                    }catch(Exception er){
                        Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            dbBtn.setDisable(false);                            
                        });
                    }
                }
                
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void saveFTP(ActionEvent event) {
        try{
            
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void saveGlobalOptions(ActionEvent event) {
        try{
            
            FXTask task = new FXTask(){
                @Override
                protected void load() {
                    try{
                        Platform.runLater(()->{
                            globalBtn.setDisable(true);
                        });       
                        List<GlobalOption> opts = globalTbl.getItems();
                        opts.stream().forEach(opt->{
                            if(opt.getId() > 0){
                                opt.update();
                            }else{
                                opt.save();
                            }
                        });
                    }catch(Exception er){
                        Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
                    }finally {
                        Platform.runLater(()->{
                            globalBtn.setDisable(false);                            
                        });
                    }
                }
                
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void saveLocalOptions(ActionEvent event) {
        try{
            XMLFactory.createLocalOptions(Care.getLocalOptionsFile(), localTbl.getItems());
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
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
        return true;
    }

    @Override
    public void loadBindings() {
        try{
            
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try {
            //Local Options
            localTbl.getColumns().setAll(FXTable.createTableViewColumns(LocalOption.class, true, false));
            
            FXTable.getColumn(localTbl, "Id").setVisible(false);
            TableColumn localValueCol = FXTable.getColumn(localTbl, "Value");
            localValueCol.setCellFactory(FXTextFieldTableCell.forTableColumn());
            
            
            localValueCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<LocalOption, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<LocalOption, String> t) {
                    LocalOption opt = (LocalOption) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    opt.setValue(t.getNewValue());
                }
            });
            
            localTbl.setEditable(true);
            localTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            localTbl.getSelectionModel().setCellSelectionEnabled(true);
            localTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            //Global Options
            globalTbl.getColumns().setAll(FXTable.createTableViewColumns(GlobalOption.class, true, false));
            
            
            FXTable.getColumn(globalTbl, "Id").setVisible(false);
            TableColumn globalValueCol = FXTable.getColumn(globalTbl, "Value");
            globalValueCol.setCellFactory(FXTextFieldTableCell.forTableColumn());
            
            
            globalValueCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GlobalOption, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<GlobalOption, String> t) {
                    GlobalOption opt = (GlobalOption) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    opt.setValue(t.getNewValue());
                }
            });
            
            globalTbl.setEditable(true);
            globalTbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            globalTbl.getSelectionModel().setCellSelectionEnabled(true);
            globalTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
        } catch (Exception er) {
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try{
            FXField.setNetworkAddressOnly(dbaddressF);
            FXField.addRequiredValidator(dbaddressF);
            FXField.addRequiredValidator(dbportF);
            FXField.addRequiredValidator(dbnameF);
            FXField.addRequiredValidator(dbuserF);
            FXField.addRequiredValidator(dbpassF);
            FXField.addIntegerValidator(dbportF, 1024, 65535, 3306);
            
            FXField.addFocusValidationListener(dbaddressF,dbportF,dbnameF,dbuserF,dbpassF);
        }catch(Exception er){
            Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        
    }

    @Override
    public void loadUIFixes() {
        dbMenu.fire();
    }

    public static FXDialogTask showDialog(StackPane stackpane,MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/SettingsForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, null, ui_controller, 800, 550);
                task.setLOADER(LOADER);
                task.setDISABLED_NODES(nodes);
                task.setOnSucceeded(evt -> {
                    stackPane = stackpane;
                    dialog.show(stackpane);
                });
                task.show(masker);
            } catch (Exception er) {
                Logger.getLogger(SettingsFormController.class.getName()).log(Level.SEVERE, null, er);
                return null;
            }
        }
        return null;
    }

}
