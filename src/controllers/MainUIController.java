package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nse.dcfx.authentication.LoginFrameController;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.Roles;
import nse.dcfx.models.SystemLog;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class MainUIController implements Initializable, UIController {
    
    private UIController currentUI = null;

    /**
     * Get the value of currentUI
     *
     * @return the value of currentUI
     */
    public UIController getCurrentUI() {
        return currentUI;
    }

    /**
     * Set the value of currentUI
     *
     * @param currentUI new value of currentUI
     */
    public void setCurrentUI(UIController currentUI) {
        this.currentUI = currentUI;
    }

    
    
    private JFXSnackbar infoBar;
    
    @FXML
    private Label systemTitle;
    
    @FXML
    private Label systemDesc;
    
    @FXML
    private ImageView systemLogo;
    
    @FXML
    private AnchorPane primaryPane;
    
    @FXML
    private AnchorPane navPane;
    
    @FXML
    private MaskerPane maskerPane;
    
    @FXML
    private StackPane mainStack;

    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Circle profileCircle;
    
    @FXML
    private JFXButton msgBtn;

    @FXML
    private JFXToggleNode dashboardMenu;

    @FXML
    private ToggleGroup navigationGroup;

    @FXML
    private JFXToggleNode patientsMenu;

    @FXML
    private JFXToggleNode pharmacyMenu;

    @FXML
    private JFXToggleNode inventoryMenu;
    
    @FXML
    private JFXToggleNode laboratoryMenu;

    @FXML
    private JFXToggleNode radiologyMenu;

    @FXML
    private JFXToggleNode servicesMenu;

    @FXML
    private JFXToggleNode physiciansMenu;

    @FXML
    private JFXToggleNode transactionsMenu;

    @FXML
    private JFXToggleNode billsMenu;

    @FXML
    private JFXToggleNode accountingMenu;

    @FXML
    private JFXToggleNode reportsMenu;

    @FXML
    private JFXToggleNode managementMenu;

    @FXML
    private JFXButton settingsMenu;

    @FXML
    void loadAccounting(ActionEvent event) {
        loadPrimaryScene("/views/AccountingUX.fxml", event);
    }


    @FXML
    void loadBills(ActionEvent event) {
        loadPrimaryScene("/views/BillingUX.fxml", event);
    }

    @FXML
    void loadDashboard(ActionEvent event) {
        loadPrimaryScene("/views/DashboardUI.fxml", event);
    }


    @FXML
    void loadInventory(ActionEvent event) {
        loadPrimaryScene("/views/InventoryUI.fxml", event);
    }
    
    @FXML
    void loadLaboratory(ActionEvent event) {
        loadPrimaryScene("/views/LaboratoryUX.fxml", event);
    }

    @FXML
    void loadManagement(ActionEvent event) {
        loadPrimaryScene("/views/ManagementUI.fxml", event);
    }

    @FXML
    void loadMsg(ActionEvent event) {
        try{
            /*
            JFXListView<JFXButton> list = new JFXListView<>();
            list.getItems().add(new JFXButton("MSG 1"));
            list.getItems().add(new JFXButton("MSG 2"));
            list.getItems().add(new JFXButton("MSG 3"));
            JFXPopup msgPop = new JFXPopup(list);
            msgPop.show(msgBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT,0,42);
            */
            //Care.promptUser("this is your message!!");
            
            
            //Notifications.create().title("title").text("Message info").show();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadPharmacy(ActionEvent event) {
        loadPrimaryScene("/views/PharmacyUX.fxml", event);
    }
    
    @FXML
    void loadRadiology(ActionEvent event) {
        loadPrimaryScene("/views/RadiologyUX.fxml", event);
    }

    @FXML
    void loadHospitalPersonels(ActionEvent event) {
        loadPrimaryScene("/views/HospitalPersonelsUI.fxml", event);
    }

    @FXML
    void loadPatients(ActionEvent event) {
        loadPrimaryScene("/views/AdmissionUX.fxml", null);
    }

    @FXML
    void loadReports(ActionEvent event) {
        loadPrimaryScene("/views/ReportsUX.fxml", event);
    }

    @FXML
    void loadSettings(ActionEvent event) {
        SettingsFormController.showDialog(mainStack,maskerPane, this);
    }

    @FXML
    void loadTransactions(ActionEvent event) {        
        loadPrimaryScene("/views/CashierUX.fxml", null);
    }
    
    @FXML
    void loadServices(ActionEvent event) {        
        loadPrimaryScene("/views/ServicesUI.fxml", event);
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
        
    }

    @Override
    public void setMainStack(StackPane stackpane) {
        
    }

    @Override
    public StackPane getMainStack() {
        return mainStack;
    }

    @Override
    public void loadCustomizations() {
        try {
            navigationGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                if (newVal == null) {
                    oldVal.setSelected(true);
                }
            });
            
            /* Messaging */
            infoBar = new JFXSnackbar(mainStack);
            Care.TOAST = infoBar;
            Care.STACKPANE = mainStack;
            Care.MAINPANE = mainPane;
            FXDialogTask.BLURREDPANE = primaryPane;
            /* Profile Icon */
            loadProfileIcon();
            systemTitle.setText(Care.SYSTEM_NAME);
            systemDesc.setText("Hospital Information System "+Care.SYSTEM_VERSION);
            if(Care.CLIENT_LOGO != null){
                systemLogo.setImage(Care.CLIENT_LOGO);
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        
    }

    @Override
    public void setUIController(UIController controller) {
        
    }
    
    private void loadInitialScene(){
        try{
            navPane.setVisible(true);
            System.out.println("Loading Initial Scene");
            switch (Care.getUser().getRole()) {
                case Roles.PHARMACY_ASSITANT:
                    //primaryPane.getChildren().remove(navPane);
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/PharmacyUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.CASHIER:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/CashierUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.OPD_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/OPDUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.ER_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/ERUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.ADMISSION_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/AdmissionUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.STATION_NURSE:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/AdmissionUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;                    
                case Roles.LABORATORY_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/LaboratoryUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;        
                case Roles.RADIOLOGY_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/RadiologyUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.BILLING_STAFF:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/BillingUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                case Roles.BILLING_MANAGER:
                    navPane.setVisible(false);
                    loadPrimaryScene("/views/BillingUX.fxml", null);
                    AnchorPane.setLeftAnchor(mainPane, 0.0);
                    break;
                default:
                    AnchorPane.setLeftAnchor(mainPane, 200.0);
                    loadPrimaryScene("/views/DashboardUI.fxml", null);
                    break;
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadProfileIcon(){
        try{
            Image img = new Image(getClass().getResource("/assets/avatar.png").toURI().toString());
            ImagePattern p = new ImagePattern(img);
            profileCircle.setFill(p);
            
            PopOver profilePopover = new PopOver();
            profilePopover.setHideOnEscape(true);            
            profilePopover.setArrowLocation(ArrowLocation.TOP_RIGHT);
            
            mainStack.getStylesheets().add(PopOver.class.getResource("popover.css").toExternalForm());
            mainStack.getStyleClass().add("popover"); 
            
            
            VBox vbox = new VBox();
            vbox.setMinSize(150, 65);
            
            vbox.setAlignment(Pos.CENTER_LEFT);
            vbox.setPadding(new Insets(15,15,15,15));
            
            Label usernameLbl = new Label("Username");
            usernameLbl.getStyleClass().add("cpopover-title");
            
            Label roleLbl = new Label("Role");
            roleLbl.getStyleClass().add("cpopover-description");
            
            JFXButton myBtn = new JFXButton("My Profile");
            GlyphIcon myIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.USER).build();
            myBtn.setGraphic(myIcon);
            myBtn.setPrefSize(120, 30);
            myBtn.getStyleClass().add("gbtn-primary");            
            myBtn.setAlignment(Pos.CENTER_LEFT);
            myBtn.setFocusTraversable(false);
            
            myBtn.setOnAction(evt->{                
                try{            
                    profilePopover.hide();
                    UserInfoFormController.showDialog(Care.getUser().getModelClone(), mainStack, maskerPane, MainUIController.this);

                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
                        
            JFXButton lgBtn = new JFXButton("Logout");
            GlyphIcon loginIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.SIGN_OUT).build();
            lgBtn.setGraphic(loginIcon);
            lgBtn.setPrefSize(120, 30);
            lgBtn.getStyleClass().add("gbtn-default");            
            lgBtn.setAlignment(Pos.CENTER_LEFT);
            lgBtn.setFocusTraversable(false);
            
            lgBtn.setOnAction(evt->{                
                try{            
                    profilePopover.hide();
                    JFXButton logoutformBtn = new JFXButton("Logout");
                    logoutformBtn.getStyleClass().add("btn-info");
                    Image lgIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Exit_52px.png").toURI().toString()); 

                    JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Logout", new Label("Do you want to logout?"), FXDialog.INFO, lgIcon,logoutformBtn);            
                    logoutformBtn.setOnAction(evt1 ->{                
                        Care.PRIMARY_STAGE.hide();
                        LoginFrameController login = Care.createLogin(Care.PRIMARY_STAGE);
                        login.setOnAuthenticate(cevt->{
                            Care.setUser(login.getAuthenticatedUser());
                            SystemLog.setCurrentUser(Care.getUser().getName());
                            SystemLog.setCurrentUserID(Care.getUser().getId());
                            Care.PRIMARY_STAGE.show();
                            Care.MAIN_CONTROLLER.loadUIFixes();
                        });
                        if(CashierUXController.unpaidHCCounterTask != null){
                            CashierUXController.unpaidHCCounterTask.cancel(true);
                        }
                        login.show();
                        d.close();
                    });

                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            
            
            JFXButton exitBtn = new JFXButton("Exit");
            GlyphIcon exitIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.POWER_OFF).build();
            exitBtn.setGraphic(exitIcon);
            exitBtn.setPrefSize(120, 30);
            exitBtn.getStyleClass().add("gbtn-danger");            
            exitBtn.setAlignment(Pos.CENTER_LEFT);
            exitBtn.setFocusTraversable(false);
            
            exitBtn.setOnAction(evt ->{
                try{            
                    profilePopover.hide();
                    JFXButton exitformBtn = new JFXButton("Exit");
                    exitformBtn.getStyleClass().add("btn-danger");
                    Image exIcon =new Image(Toolkit.getDefaultToolkit().getClass().getResource("/assets/Shutdown_52px.png").toURI().toString()); 

                    JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Exit", new Label("Do you want to Exit?"), FXDialog.DANGER, exIcon,exitformBtn);            
                    exitformBtn.setOnAction(evt1 ->{  
                        d.close();
                        try{                    
                            Care.EXECUTOR.shutdownNow();
                            Care.SCHEDULED_EXECUTOR.shutdownNow();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                        Platform.exit();  
                    });

                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            
            
            
            vbox.getChildren().setAll(usernameLbl,roleLbl,new Separator(),myBtn,lgBtn,exitBtn);  
            profilePopover.setContentNode(vbox); 
            
            
            profileCircle.setOnMouseEntered(evt ->{                     
                usernameLbl.setText(Care.getUser().getName());     
                roleLbl.setText(Care.getUser().getRole());
                profilePopover.show(profileCircle);  
                ((Parent) profilePopover.getSkin().getNode()).getStylesheets().add(getClass().getResource("/views/Styles.css").toExternalForm());   
                ((Parent) profilePopover.getSkin().getNode()).setOnMouseExited(ext->{
                    profilePopover.hide();
                });
                profilePopover.show(profileCircle);  
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    
    public void loadPrimaryScene(String scene, ActionEvent btn_evnt) {
        FXTask task = new FXTask() {
            private Node TO_PARENT;
            private final Node FROM_PARENT = mainPane.getCenter();
            private UIController ctrl;

            @Override
            protected void load() {
                try {
                    maskerPane.setVisible(true);
                    Thread.sleep(200);
                    FXMLLoader LOADER = new FXMLLoader(Care.class.getResource(scene));
                    LOADER.setClassLoader(Care.CACHE_FXMLCLASSLOADER);
                    LOADER.load();
                    ctrl = LOADER.getController();
                    Parent parent = LOADER.getRoot();
                    ctrl.setMainStack(mainStack);
                    ctrl.setMaskerPane(maskerPane);
                    ctrl.loadCustomizations();
                    ctrl.loadResources();
                    TO_PARENT = parent;
                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }finally{
                    maskerPane.setVisible(false);
                }
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                switchCenter(FROM_PARENT, TO_PARENT,ctrl);
            }

            @Override
            protected void cancelled() {
                super.cancelled();
            }

            @Override
            protected void failed() {
                super.failed();
            }
        };
        //task.setDISABLED_NODES(new Node[]{(JFXToggleNode) btn_evnt.getSource()});
        Care.process(task);
        //Platform.runLater(task);
    }
    
    public void switchCenter(Node from, Node to,UIController controller) {
        if (from != null) {
            FadeTransition fadeTrans = new FadeTransition();
            fadeTrans.setDuration(Duration.millis(300));
            fadeTrans.setNode(from);
            fadeTrans.setFromValue(1);
            fadeTrans.setToValue(0);
            fadeTrans.play();

            fadeTrans.setOnFinished(evt -> {
                to.setOpacity(0);
                mainPane.setCenter(to);
                FadeTransition fadeoutTrans = new FadeTransition();
                fadeoutTrans.setDuration(Duration.millis(300));
                fadeoutTrans.setNode(to);
                fadeoutTrans.setFromValue(0);
                fadeoutTrans.setToValue(1);
                fadeoutTrans.play();
                fadeoutTrans.setOnFinished(fevt->{
                    controller.loadUIFixes();
                });
            });
        } else {
            to.setOpacity(0);
            mainPane.setCenter(to);
            FadeTransition fadeoutTrans = new FadeTransition();
            fadeoutTrans.setDuration(Duration.millis(300));
            fadeoutTrans.setNode(to);
            fadeoutTrans.setFromValue(0);
            fadeoutTrans.setToValue(1);
            fadeoutTrans.play();
            fadeoutTrans.setOnFinished(fevt->{
                controller.loadUIFixes();
            });
        }
    }

    @Override
    public void setMaskerPane(MaskerPane masker) {
        
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }

    @Override
    public void loadUIFixes() {
        try{
            loadInitialScene();
            /*
            Care.processEvery(()->{
                System.gc();                
                System.out.println("System Garbage Collector");
            }, 0, 10, TimeUnit.SECONDS);
            */
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
     
    
}

