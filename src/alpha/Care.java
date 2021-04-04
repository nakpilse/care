package alpha;


import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import controllers.MainUIController;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Toolkit;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nse.dcfx.authentication.LoginFrameController;
import nse.dcfx.files.DBConfig;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLConnectionFactory;
import nse.dcfx.mysql.SQLConnectionPool;
import nse.dcfx.mysql.SQLServer;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.system.SystemUtils;
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import models.Admission;
import nse.dcfx.controls.FXClassLoader;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.models.LocalOption;
import nse.dcfx.models.SystemLog;
import nse.dcfx.xml.XMLFactory;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Duskmourne
 */
public class Care extends Application {
    
    public static SQLConnectionPool CONNECTION_POOL = null;
    public static Stage PRIMARY_STAGE;
    public static MainUIController MAIN_CONTROLLER = null;
    public static LoginFrameController LOGIN_CONTROLLER = null;
    public static final ObservableList<Screen> SCREENS = Screen.getScreens();//Get list of SCREENS
    public static final Screen SECONDSCREEN = ((Screen.getScreens().size() > 1)? SCREENS.get(SCREENS.size()-1):null);
    
    
    public static String SYSTEM_NAME = "Health Care Informant";
    public static String SYSTEM_VERSION = "v1.0.0";
    public static final String SYSTEM_DESCRIPTION = "This information systems provide a common source of information about a patient's health history. The system keeps its data encrypted  and in a secure place and controls who can reach the data in certain circumstances.";
    public static final String SYSTEM_BRANDING = "© Nakpil Software Enterprise "+LocalDate.now().getYear();
    public static final String SYSTEM_LOGO = "/assets/HeartPulse_64px.png";
    public static Image CLIENT_LOGO = null;
    public static Image CLIENT_ICON = null;
    
    public static final String FACILITYNAME = "FACILITYNAME";
    public static final String FACILITYADDRESS = "FACILITYADDRESS";
    public static final String FACILITYCITYMUNICIPALITY = "FACILITYCITYMUNICIPALITY";
    public static final String FACILITYSTATEPROVINCE = "FACILITYSTATEPROVINCE";
    public static final String FACILITYLANDLINE = "FACILITYLANDLINE";
    public static final String FACILITYTIN = "FACILITYTIN";
    
    
    public static final String APP_DIR = SystemUtils.getAppDir();
    public static final String CONFIG_DIR = APP_DIR+File.separator+"conf";
    public static final String DB_CONFIG_FILE = "dbconfig.xml";
    public static final String DB_CONFIG_PATH = CONFIG_DIR+File.separator+DB_CONFIG_FILE;
    public static final String APP_LOGO = CONFIG_DIR+File.separator+"logo.png";    
    public static final String APP_ICON = CONFIG_DIR+File.separator+"icon.png";
    
    public static final String LOCAL_OPTIONS_FILE = "localoptions.xml";
    public static final String LOCAL_OPTIONS_PATH = CONFIG_DIR+File.separator+LOCAL_OPTIONS_FILE;
    
    
    public static ClassLoader CACHE_FXMLCLASSLOADER = new FXClassLoader(FXMLLoader.getDefaultClassLoader()); 
    
    public static JFXSnackbar TOAST = null;
    public static StackPane STACKPANE = null;
    public static BorderPane MAINPANE = null;
    public static AnchorPane PRIMARYPANE = null;
    public static Scene MAINSCENE = null;
    
    private static User user = null;


    public static DBConfig CONFIG = new DBConfig() {
        {
            this.setAddress("127.0.0.1");
            this.setPort("3306");
            this.setDatabase("care");
            this.setUsername("root");
            this.setPassword("dwr2rufd7ezj");
        }
    };
    
    public static List<LocalOption> LOCAL_OPTIONS = new ArrayList(){
        {
            this.add(new LocalOption("machineid",""));
            this.add(new LocalOption("usbdrawerport",""));
        }
    };
    
    public static final File getDBConfigFile(){
        return new File(DB_CONFIG_PATH);
    }
    
    public static final File getLocalOptionsFile(){
        return new File(LOCAL_OPTIONS_PATH);
    }

    public static final ExecutorService EXECUTOR = Executors.newWorkStealingPool();
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR =  Executors.newSingleThreadScheduledExecutor();

    @Override
    public void start(Stage primaryStage) {
        Platform.setImplicitExit(false);
        try {
            System.out.println("Application Dir = '"+SystemUtils.getAppDir()+"'");  
            System.out.println("Config File = '"+getDBConfigFile().getAbsolutePath()+"'");  
            System.out.println("Option File = '"+getLocalOptionsFile().getAbsolutePath()+"'");  
            if(getDBConfigFile().exists()){
                CONFIG = DBConfig.load(getDBConfigFile());
            }else{
                getDBConfigFile().getParentFile().mkdirs();                              
                CONFIG.save(getDBConfigFile());
            }  
            
            if(getLocalOptionsFile().exists()){
                LOCAL_OPTIONS = XMLFactory.loadLocalOptions(getLocalOptionsFile());
            }else{
                getLocalOptionsFile().getParentFile().mkdirs();   
                System.out.println("Creating File : "+getLocalOptionsFile().getAbsolutePath());
                XMLFactory.createLocalOptions(getLocalOptionsFile(), LOCAL_OPTIONS);
            }  
            
            if(new File(APP_LOGO).exists()){
                CLIENT_LOGO = new Image(new File(APP_LOGO).toURI().toString());
            }
            
            FXDialogTask.setClassLoader(CACHE_FXMLCLASSLOADER);
            FXDialogTask.setExecutorSrv(EXECUTOR);
            
            loadConnection(); 
            initializeSystemLogOptions();
            
            
            primaryStage.initStyle(StageStyle.UNDECORATED); 
            
            SYSTEM_VERSION = getVersion();     
            
            //Stage Icons
            if(new File(APP_ICON).exists()){
                CLIENT_ICON = new Image(new File(APP_ICON).toURI().toString());
                primaryStage.getIcons().add(CLIENT_ICON);
            }
            
            LoginFrameController login = createLogin(primaryStage);
            login.setOnAuthenticate((evt)->{
                maximizeStage(primaryStage);                
                setUser(login.getAuthenticatedUser());
                SystemLog.setCurrentUser(user.getName());
                SystemLog.setCurrentUserID(user.getId());
                System.out.println(user.getDebugInfo());
                
                //showOnSecondaryScreen(primaryStage);
                PRIMARY_STAGE.show();
                MAIN_CONTROLLER.loadUIFixes();
            });
            login.show();
            

            primaryStage.setOnCloseRequest((evt)->{
                try{                    
                    EXECUTOR.shutdownNow();
                    SCHEDULED_EXECUTOR.shutdownNow();
                }catch(Exception er){
                    Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
                }
                Platform.exit();                            
            });
                        
        } catch (Exception ex) {
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showOnSecondaryScreen(Stage primaryStage){
        try{
            if(Screen.getScreens().size() > 1){
                Rectangle2D bounds = Care.SECONDSCREEN.getVisualBounds();
                        
                primaryStage.setX(bounds.getMinX());
                primaryStage.setY(bounds.getMinY());
                primaryStage.setWidth(bounds.getWidth());
                primaryStage.setHeight(bounds.getHeight());
                primaryStage.setFullScreen(true);                
            }            
        }catch(Exception er){
            
        }
    }
    
    private static void createMainLoader(Stage stg){
        try{
            FXMLLoader MAIN_LOADER = new FXMLLoader(Care.class.getResource("/views/MainUI.fxml"));
            MAIN_LOADER.load();
            MAIN_CONTROLLER = MAIN_LOADER.getController();
            MAINSCENE = new Scene(MAIN_LOADER.getRoot());
            MAIN_CONTROLLER.loadCustomizations();
            stg.setTitle(SYSTEM_NAME+" "+SYSTEM_VERSION);
            stg.setScene(MAINSCENE);            
            PRIMARY_STAGE = stg;
        }catch(Exception er){
             Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static LoginFrameController createLogin(Stage stg) {
        try{
            String sname = "";
            try{
                Map<String,String> opts = GlobalOption.getMap("General");
                if(!opts.isEmpty()){
                    sname = opts.get(FACILITYNAME);
                    SYSTEM_NAME = ((sname == null || sname.isEmpty())? SYSTEM_NAME:sname);
                }
            }catch(Exception er){
                System.out.println("Failed to fetch Global Options");
            }            
            createMainLoader(stg);            
            FXMLLoader LOGIN_LOADER = new FXMLLoader(LoginFrameController.class.getResource("/nse/dcfx/authentication/LoginFrame.fxml"));
            LOGIN_LOADER.load();
            LoginFrameController login = LOGIN_LOADER.getController();
            login.setConfig(CONFIG);
            login.setExecutor(EXECUTOR);
            login.setConfigfile(new File(DB_CONFIG_PATH));
            login.setSystemName(SYSTEM_NAME);
            login.setSystemDescription(SYSTEM_DESCRIPTION);
            login.setBranding(SYSTEM_BRANDING);
            login.setLogo((CLIENT_LOGO == null)?new Image(Toolkit.getDefaultToolkit().getClass().getResource(SYSTEM_LOGO).toURI().toString()):CLIENT_LOGO);
            login.setIcon(CLIENT_ICON);
            
            List<String> users = SQLTable.distinct(User.class, User.USERNAME);
            TextFields.bindAutoCompletion(login.getUsernameField(), users);
            return login;
        }catch(Exception er){
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }

    private void maximizeStage(Stage primaryStage){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());

        primaryStage.setMaxWidth(primaryScreenBounds.getWidth());
        primaryStage.setMinWidth(primaryScreenBounds.getWidth());
        primaryStage.setWidth(primaryScreenBounds.getWidth());

        primaryStage.setMaxHeight(primaryScreenBounds.getHeight());
        primaryStage.setMinHeight(primaryScreenBounds.getHeight());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
        
    }
    
    public static void loadConnection(){
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            CONNECTION_POOL = new SQLConnectionPool("com.mysql.jdbc.Driver", CONFIG.getDatabaseHost(),CONFIG.getUsername(),CONFIG.getPassword(), 20);
            
            SQLConnectionFactory.setConnectionPool(CONNECTION_POOL);
            SQLConnectionFactory.setServerType(SQLServer.MYSQL);
        }catch(Exception er){
            System.out.println("ERROR LOADING CONNECTION");
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public static void process(Runnable task){
        EXECUTOR.submit(task);
    }
    
    public static ScheduledFuture processAfter(Callable callable,int t,java.util.concurrent.TimeUnit a){     
        return SCHEDULED_EXECUTOR.schedule(callable, t, a);
    }
    
    public static ScheduledFuture processEvery(Runnable task,int initial_delay,int every,java.util.concurrent.TimeUnit a){        
        return SCHEDULED_EXECUTOR.scheduleAtFixedRate(task, initial_delay, every, a);        
    }
    
    
    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public static User getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public static void setUser(User user) {
        Care.user = user;
    }
    
    public static void initializeSystemLogOptions(){
        try{
            User.setLogged(true);
        }catch(Exception er){
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void promptUser(String msg){
        if(TOAST != null){
            Label m = new Label(msg);
            m.getStyleClass().add("fx-toast");
            m.getStyleClass().add("anton-font");
            m.setTextAlignment(TextAlignment.CENTER);
            m.setAlignment(Pos.CENTER);
            m.setMinWidth(300);
            m.setPrefWidth(300);
            m.setGraphicTextGap(10);
            GlyphIcon icon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.INFO_CIRCLE).size("16px").build();
            icon.setFill(Color.WHITE);
            m.setGraphic(icon);
            TOAST.enqueue(new SnackbarEvent(m,Duration.millis(4000),null));
        }
    }
    
    public static void createNotification(String title,String text,int milis,boolean hideclose){
        try{
            if(hideclose){                
                Notifications.create()
                .title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.millis(milis))
                .hideCloseButton()
                .show();
            }else{
                Notifications.create()
                .title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.millis(milis))
                .show();
            }
        }catch(Exception er){
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    
    public static String getVersion(){
        Scanner scanner = null;
        try{
            File[] files = new File(APP_DIR).listFiles();
            String updater_file = "";
            for(int i = 0;i < files.length;i++){
                if(files[i].isFile() && files[i].getName().contains("Updater.ini")){
                    updater_file = files[i].getName();
                }
            }
            if(!updater_file.isEmpty()){
                scanner = new Scanner(new File(APP_DIR+File.separator+updater_file));
                while(scanner.hasNextLine()){
                    String data = scanner.nextLine();
                    if(data.contains("ApplicationVersion")){
                        return data.replace("ApplicationVersion=", "");
                    }
                }
                return "";
            }else{
                return "";
            }
            
                   
            
            
        }catch(Exception er){
            System.out.println("Failed to get version!");
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
            return "";
        }finally{
            if(scanner != null){
                scanner.close();
            }            
        }
    }
    
    private void loadStageIcon(){
        try{
            
            
        }catch(Exception er){
            Logger.getLogger(Care.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
