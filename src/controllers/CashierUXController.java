package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleNode;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jasper.JasperFrame;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import models.BillStatement;
import models.CashAdvance;
import models.Consultation;
import models.ERConsultation;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.Item;
import models.LaboratoryTest;
import models.Patient;
import models.Payment;
import models.RadiologyTest;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.FileKit;
import nse.dcfx.utils.NumberKit;
import nse.dcfx.utils.StringKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class CashierUXController implements Initializable, UIController {

    private StackPane mainStack;
    private MaskerPane maskerPane;
    private List<String> suggestedNames = new ArrayList();
    
    private List<HospitalChargeItem> chargeItems = new ArrayList();
    private static double SC_PERCENT = 0;
    private static double PWD_PERCENT = 0;
    private static double EMP_PERCENT = 0;
    private static double OT_PERCENT = 0;
    public static ScheduledFuture unpaidHCCounterTask = null;

    
    @FXML
    private JFXTabPane mainTabPane;
    
    @FXML
    private Tab terminalTab;

    @FXML
    private TextField t1chargeF;

    @FXML
    private JFXButton t1chargeBtn;

    @FXML
    private TextField t1patientF;

    @FXML
    private JFXButton t1patientBtn;

    @FXML
    private TableView<HospitalCharge> t1resultsTbl;

    @FXML
    private Label t1resultsLbl;

    @FXML
    private Label t1pnameLbl;

    @FXML
    private Label t1phidLbl;

    @FXML
    private TableView<?> t1pchargeitemsTbl;

    @FXML
    private Label t1chargescountLbl;

    @FXML
    private JFXButton t1clearBtn;

    @FXML
    private Label t1subtotalLbl;

    @FXML
    private JFXButton t1scpwddiscountBtn;
        
    @FXML
    private Label t1vatsalesLbl;

    @FXML
    private Label t1vatexLbl;

    @FXML
    private Label t1zeroLbl;

    @FXML
    private Label t1taxLbl;

    @FXML
    private Label t1netvatLbl;

    @FXML
    private Label t1lessvatLbl;

    @FXML
    private Label t1scpwdLbl;

    @FXML
    private Label t1empLbl;

    @FXML
    private Label t1otherLbl;

    @FXML
    private Label t1netLbl;
    
    @FXML
    private JFXButton t1accrecBtn;

    @FXML
    private JFXButton t1invoiceBtn;

    @FXML
    private JFXButton t1orBtn;

    @FXML
    private Tab chargesTab;

    @FXML
    private TableView<HospitalCharge> t2Tbl;

    @FXML
    private Label t2resLbl;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private Tab paymentsTab;

    @FXML
    private TableView<Payment> t3Tbl;

    @FXML
    private Label t3resLbl;

    @FXML
    private JFXTextField t3searchF;

    @FXML
    private Tab advancesTab;

    @FXML
    private TableView<CashAdvance> t4Tbl;

    @FXML
    private Label t4resLbl;

    @FXML
    private JFXTextField t4searchF;
    
    @FXML
    private Tab billingTab;

    @FXML
    private TableView<BillStatement> t5Tbl;

    @FXML
    private Label t5resLbl;

    @FXML
    private JFXTextField t5searchF;

    @FXML
    private JFXToggleNode terminalBtn;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode chargesBtn;

    @FXML
    private JFXToggleNode paymentHistoryBtn;

    @FXML
    private JFXToggleNode advancesBtn;
    
    @FXML
    private JFXToggleNode billingBtn;
    
    @FXML
    private JFXButton unpaidHCBtn;
        
    @FXML
    private JFXComboBox<String> pendingchargeCombo;
    
    @FXML
    private JFXButton t1payBtn;
    
    @FXML
    void showUnpaidHospitalCharges(ActionEvent event) {
        try{
            //java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusYears(5), LocalTime.of(0, 0, 0)));
            //java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
            //mainTabPane.getSelectionModel().select(chargesTab);
            //chargesBtn.setSelected(true);
            //loadChargesList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND ("+HospitalCharge.VOIDTIME+" IS NULL OR "+HospitalCharge.CANCELTIME+" IS NULL) ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
            chargesBtn.fire();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void showBillings(ActionEvent event) {
        try{
            mainTabPane.getSelectionModel().select(billingTab);
            loadBillingList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void addSCPWDDiscount(ActionEvent event) {
        try{      
            if(!chargeItems.isEmpty()){
                GridPane gcontainer = new GridPane();
                gcontainer.setMaxWidth(500);
                gcontainer.setMaxHeight(500);
                gcontainer.setAlignment(Pos.CENTER);
                gcontainer.setPadding(new Insets(5,0,5,5));   
                gcontainer.setVgap(20);
                gcontainer.setHgap(5);
                
                Patient patient = (Patient)SQLTable.get(Patient.class, chargeItems.get(0).getPatient_id());

                //SC
                JFXTextField scF = new JFXTextField((patient != null && !patient.getSeniorid().isEmpty())? patient.getSeniorid():"");
                scF.setPromptText("Senior Citizen ID #");    
                scF.setMinHeight(28);
                scF.setMinWidth(180);
                scF.setMaxWidth(180);
                scF.setPrefWidth(180);   
                scF.setLabelFloat(true);
                
                
                JFXTextField scP = new JFXTextField(String.valueOf(HospitalCharge.SC_Discount_Percentage));
                scP.setMinHeight(28);
                scP.setMinWidth(45);
                scP.setMaxWidth(45);
                scP.setPrefWidth(45);
                scP.setAlignment(Pos.CENTER);
                scP.setEditable(false);
                scP.setDisable(true);
                scP.setLabelFloat(true);
                scP.setPromptText("%");

                Label scL = new Label("0.00");
                scL.setMinHeight(28);
                scL.setMinWidth(100);
                scL.setMaxWidth(100);
                scL.setPrefWidth(100);   
                scL.setStyle("-fx-font-family:anton;-fx-font-size:22px");  
                scL.setAlignment(Pos.CENTER_RIGHT);
                scL.setTextAlignment(TextAlignment.RIGHT);

                //PWD
                JFXTextField pwdF = new JFXTextField();
                pwdF.setPromptText("PWD ID #");    
                pwdF.setMinHeight(28);
                pwdF.setMinWidth(180);
                pwdF.setMaxWidth(180);
                pwdF.setPrefWidth(180);   
                pwdF.setLabelFloat(true);

                JFXTextField pwdP = new JFXTextField(String.valueOf(HospitalCharge.PWD_Discount_Percentage));
                pwdP.setMinHeight(28);
                pwdP.setMinWidth(45);
                pwdP.setMaxWidth(45);
                pwdP.setPrefWidth(45);
                pwdP.setAlignment(Pos.CENTER);
                pwdP.setEditable(false);
                pwdP.setDisable(true);
                pwdP.setLabelFloat(true);
                pwdP.setPromptText("%");

                Label pwdL = new Label("0.00");
                pwdL.setMinHeight(28);
                pwdL.setMinWidth(100);
                pwdL.setMaxWidth(100);
                pwdL.setPrefWidth(100);   
                pwdL.setStyle("-fx-font-family:anton;-fx-font-size:22px");   
                pwdL.setAlignment(Pos.CENTER_RIGHT);
                pwdL.setTextAlignment(TextAlignment.RIGHT);        
                
                //EMP
                JFXTextField empF = new JFXTextField();
                empF.setPromptText("Employee Name/ID");    
                empF.setMinHeight(28);
                empF.setMinWidth(180);
                empF.setMaxWidth(180);
                empF.setPrefWidth(180);   
                empF.setLabelFloat(true);                
                
                JFXTextField empP = new JFXTextField("0");
                empP.setMinHeight(28);
                empP.setMinWidth(45);
                empP.setMaxWidth(45);
                empP.setPrefWidth(45);
                empP.setAlignment(Pos.CENTER);
                empP.setLabelFloat(true);
                empP.setPromptText("%");

                Label empL = new Label("0.00");
                empL.setMinHeight(28);
                empL.setMinWidth(100);
                empL.setMaxWidth(100);
                empL.setPrefWidth(100);   
                empL.setStyle("-fx-font-family:anton;-fx-font-size:22px");  
                empL.setAlignment(Pos.CENTER_RIGHT);
                empL.setTextAlignment(TextAlignment.RIGHT);
                
                //OTH
                JFXTextField otdF = new JFXTextField();
                otdF.setPromptText("Other Discount Remarks");    
                otdF.setMinHeight(28);
                otdF.setMinWidth(180);
                otdF.setMaxWidth(180);
                otdF.setPrefWidth(180);   
                otdF.setLabelFloat(true);                
                
                JFXTextField otdP = new JFXTextField("0");
                otdP.setMinHeight(28);
                otdP.setMinWidth(45);
                otdP.setMaxWidth(45);
                otdP.setPrefWidth(45);
                otdP.setAlignment(Pos.CENTER);
                otdP.setLabelFloat(true);
                otdP.setPromptText("%");

                Label odtL = new Label("0.00");
                odtL.setMinHeight(28);
                odtL.setMinWidth(100);
                odtL.setMaxWidth(100);
                odtL.setPrefWidth(100);   
                odtL.setStyle("-fx-font-family:anton;-fx-font-size:22px");  
                odtL.setAlignment(Pos.CENTER_RIGHT);
                odtL.setTextAlignment(TextAlignment.RIGHT);
                
                //Total
                Label discL = new Label("0.00");
                discL.setMinHeight(28);
                discL.setMinWidth(100);
                discL.setMaxWidth(100);
                discL.setPrefWidth(100);   
                discL.setStyle("-fx-font-family:anton;-fx-font-size:22px");   
                discL.setAlignment(Pos.CENTER_RIGHT);
                discL.setTextAlignment(TextAlignment.RIGHT);      

                double sales = 0;
                double vsales = 0;
                double disc1 = 0;
                double disc2 = 0;
                double disc3 = 0;
                double disc4 = 0;

                for(int i = 0;i < chargeItems.size();i++){
                    vsales += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                    sales += chargeItems.get(i).getTotalselling();
                    disc1 += chargeItems.get(i).getScdiscount();
                    disc2 += chargeItems.get(i).getPwddiscount();
                    disc3 += chargeItems.get(i).getEmpdiscount();
                    disc4 += chargeItems.get(i).getOtdiscount();
                }


                Label totalDisc = new Label("TOTAL Discount");
                totalDisc.getStyleClass().add("title-label");
                totalDisc.setStyle("-fx-font-size:22px;");

                Separator sp = new Separator();
                sp.setOrientation(Orientation.VERTICAL);
                sp.setMinWidth(20);

                Separator sph = new Separator();
                sph.setOrientation(Orientation.HORIZONTAL);

                GridPane.setValignment(sph, VPos.TOP);
                GridPane.setHgrow(sph, Priority.ALWAYS);
                GridPane.setValignment(totalDisc, VPos.BOTTOM);
                                
                
                //SC DISC
                gcontainer.add(scF, 0, 0,1,1);
                gcontainer.add(scP, 1, 0,1,1);
                gcontainer.add(scL, 3, 0, 1, 1);   
                //PWD DISC
                gcontainer.add(pwdF, 0, 1,1,1);
                gcontainer.add(pwdP, 1, 1,1,1);
                gcontainer.add(pwdL, 3, 1, 1, 1); 
                //EMP DISC
                gcontainer.add(empF, 0, 2,1,1);
                gcontainer.add(empP, 1, 2,1,1);                  
                gcontainer.add(empL, 3, 2, 1, 1);             
                //OT DISC
                gcontainer.add(otdF, 0, 3,1,1);
                gcontainer.add(otdP, 1, 3,1,1);
                gcontainer.add(odtL, 3, 3, 1, 1);   
                
                gcontainer.add(sph, 0, 4, 4, 1);  
                gcontainer.add(totalDisc, 0, 5, 2, 1);       
                gcontainer.add(discL, 3, 5, 1, 1);     
                //HORIZONTAL SEPARATOR
                gcontainer.add(sp, 2, 0, 1, 5);
                   
                

                JFXButton confirmBtn = new JFXButton("Confirm");                                    
                confirmBtn.getStyleClass().add("btn-danger");
                
                JFXButton remBtn = new JFXButton("Remove");                                    
                remBtn.getStyleClass().add("btn-danger");
                
                DoubleProperty scDiscount = new SimpleDoubleProperty(disc1);
                DoubleProperty pwdDiscount = new SimpleDoubleProperty(disc2);  
                DoubleProperty empDiscount = new SimpleDoubleProperty(disc3);              
                DoubleProperty otDiscount = new SimpleDoubleProperty(disc4);       
                DoubleProperty ttDiscount = new SimpleDoubleProperty(disc1+disc2+disc3+disc4);
                
                DoubleProperty empPerc = new SimpleDoubleProperty(EMP_PERCENT);              
                DoubleProperty otPerc = new SimpleDoubleProperty(OT_PERCENT);       
                
                
                Bindings.bindBidirectional(scL.textProperty(), scDiscount,  new NumberConverter());
                Bindings.bindBidirectional(pwdL.textProperty(), pwdDiscount,  new NumberConverter());
                Bindings.bindBidirectional(empL.textProperty(), empDiscount,  new NumberConverter());
                Bindings.bindBidirectional(odtL.textProperty(), otDiscount,  new NumberConverter());
                Bindings.bindBidirectional(discL.textProperty(), ttDiscount,  new NumberConverter());
                
                empP.textProperty().bindBidirectional(empPerc, new NumberConverter());
                otdP.textProperty().bindBidirectional(otPerc, new NumberConverter());
                
                FXField.addDoubleValidator(empP, 0.0, 15.0, 0.0);
                FXField.addDoubleValidator(otdP, 0.0, 100.0, 0.0);
                
                FXField.addFocusValidationListener(empP,otdP);
                
                /*
                if(patient != null && !patient.getSeniorid().isEmpty() && SC_PERCENT == 0 && PWD_PERCENT == 0){
                    double v = 0;
                    for(int i = 0;i < chargeItems.size();i++){
                        v += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                    }
                    double d = v*(HospitalCharge.SC_Discount_Percentage/100);
                    scDiscount.set(d);
                    pwdDiscount.set(0);
                    pwdF.setText("");
                }
                */
                
                scF.textProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null){
                        if(!scF.getText().isEmpty()){                            
                            double v = 0;
                            for(int i = 0;i < chargeItems.size();i++){
                                v += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                            }
                            double d = v*(HospitalCharge.SC_Discount_Percentage/100);
                            scDiscount.set(d);
                            pwdDiscount.set(0);
                            pwdF.setText("");
                        }else{
                            scDiscount.set(0);
                        }
                        ttDiscount.set(scDiscount.get()+pwdDiscount.get()+empDiscount.get()+otDiscount.get());
                    }
                });
                
                pwdF.textProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null){
                        if(!pwdF.getText().isEmpty()){
                            double v = 0;
                            for(int i = 0;i < chargeItems.size();i++){
                                v += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                            }
                            double d = v*(HospitalCharge.PWD_Discount_Percentage/100);
                            pwdDiscount.set(d);   
                            scF.setText(""); 
                            scDiscount.set(0);
                        }else{
                            pwdDiscount.set(0);
                        }
                        ttDiscount.set(scDiscount.get()+pwdDiscount.get()+empDiscount.get()+otDiscount.get());
                    }
                });
                
                empP.textProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null && !newVal.isEmpty()){
                        Platform.runLater(()->{
                            try{
                                double perc = empPerc.get();
                                double v = 0;
                                if(scDiscount.get() > 0 || pwdDiscount.get() > 0){
                                    for(int i = 0;i < chargeItems.size();i++){
                                        v += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                                    }
                                    v -= (scDiscount.get()+pwdDiscount.get());
                                    double d = v*(perc/100);
                                    empDiscount.set(d);
                                }else{
                                    for(int i = 0;i < chargeItems.size();i++){
                                        v += chargeItems.get(i).getTotalselling();
                                    }
                                    double d = v*(perc/100);
                                    empDiscount.set(d);
                                }

                                ttDiscount.set(scDiscount.get()+pwdDiscount.get()+empDiscount.get()+otDiscount.get());
                            }catch(NumberFormatException er){
                                System.out.println("Invalid Employee Discount Percentage");
                            }                                 
                        });
                    }
                });
                
                otdP.textProperty().addListener((obs,oldVal,newVal)->{
                    if(newVal != null && !newVal.isEmpty()){
                        Platform.runLater(()->{
                            try{
                                double perc = otPerc.get();
                                double v = 0;
                                if(scDiscount.get() > 0 || pwdDiscount.get() > 0){
                                    for(int i = 0;i < chargeItems.size();i++){
                                        v += (chargeItems.get(i).getVatsales()+chargeItems.get(i).getNonvatsales());
                                    }
                                    v -= (scDiscount.get()+pwdDiscount.get()+empDiscount.get());
                                    double d = v*(perc/100);
                                    otDiscount.set(d);
                                }else{
                                    for(int i = 0;i < chargeItems.size();i++){
                                        v += chargeItems.get(i).getTotalselling();
                                    }
                                    v -= empDiscount.get();
                                    double d = v*(perc/100);
                                    otDiscount.set(d);
                                }
                                
                                ttDiscount.set(scDiscount.get()+pwdDiscount.get()+empDiscount.get()+otDiscount.get());
                            }catch(NumberFormatException er){
                                System.out.println("Invalid Other Discount Percentage");
                            }
                        });                  
                    }
                });
                
                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Discounts",gcontainer,FXDialog.DANGER,remBtn,confirmBtn);
                scF.requestFocus();

                confirmBtn.setOnAction(evt->{
                    if(empP.validate() && otdP.validate()){         
                        chargeItems.stream().forEach((item) -> {                            
                            item.setScid(scF.getText());
                            item.setPwdid(pwdF.getText());
                            item.setEmpid(empF.getText());
                            item.setOtdiscountremarks(otdF.getText());
                            item.calculateNet(scF.getText().isEmpty() && pwdF.getText().isEmpty(), ((scF.getText().isEmpty())? 0:HospitalCharge.SC_Discount_Percentage), ((pwdF.getText().isEmpty())? 0:HospitalCharge.PWD_Discount_Percentage), empPerc.get(), otPerc.get());
                        });
                        SC_PERCENT = ((scF.getText().isEmpty())? 0:HospitalCharge.SC_Discount_Percentage);
                        PWD_PERCENT = ((pwdF.getText().isEmpty())? 0:HospitalCharge.PWD_Discount_Percentage);
                        EMP_PERCENT = empPerc.get();
                        OT_PERCENT = otPerc.get();
                        loadTransactionValues(chargeItems);
                        dialog.close();
                    }else{
                        
                    }       
                });    
                
                remBtn.setOnAction(evt->{
                    chargeItems.stream().forEach((item) -> {
                        item.setScid("");
                        item.setPwdid("");
                        item.setEmpid("");
                        item.setOtdiscountremarks("");
                        item.calculateNet(true,0, 0, 0, 0);
                    });
                    SC_PERCENT = 0;
                    PWD_PERCENT = 0;
                    EMP_PERCENT = 0;
                    OT_PERCENT = 0;
                    loadTransactionValues(chargeItems);
                    dialog.close();      
                });  
            }
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    

    @FXML
    void clearTransaction(ActionEvent event) {
        try{
            chargeItems.clear();
            /*
            t1resultsTbl.getItems().forEach(item->{
                item.setSelected(false);
            });
            */
            FXTable.setList(t1resultsTbl, new ArrayList());
            FXTable.setList(t1pchargeitemsTbl, new ArrayList());
            t1resultsLbl.setText("0");
            t1subtotalLbl.setText("0.00");
            t1vatsalesLbl.setText("0.00");
            t1vatexLbl.setText("0.00");
            t1zeroLbl.setText("0.00");
            t1taxLbl.setText("0.00");
            t1netvatLbl.setText("0.00");
            t1lessvatLbl.setText("0.00");
            t1scpwdLbl.setText("0.00");
            t1empLbl.setText("0.00");
            t1otherLbl.setText("0.00");
            t1netLbl.setText("0.00");
            t1pnameLbl.setText("");
            SC_PERCENT = 0;
            PWD_PERCENT = 0;
            EMP_PERCENT = 0;
            OT_PERCENT = 0;
            t1chargescountLbl.setText("0");
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    @FXML
    void addToAccountsRecievables(ActionEvent event) {
        try{                        
            if(getSelectedHospitalCharges().size() > 0){
                String printName = getSelectedHospitalCharges().get(0).getChargeto();
                
                List<Double> nets = chargeItems.stream().map(obj->obj.getNetsales()).collect(Collectors.toList());       

                //payable.set(netamt);            

                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(25);
                content.setPadding(new Insets(35,25,25,25));                               

                
                JFXTextField nameF = new JFXTextField(printName);
                nameF.setPromptText("Account To");    
                nameF.setMinHeight(24);
                nameF.setMinWidth(250);
                nameF.setMaxWidth(250);
                nameF.setPrefWidth(250);   
                nameF.setLabelFloat(true);

                FXField.addRequiredValidator(nameF);
                

                content.getChildren().addAll(nameF);

                JFXButton confirmBtn = new JFXButton("Confirm");                                    
                confirmBtn.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Accounts Recievable",content,FXDialog.DANGER,confirmBtn);
                nameF.requestFocus();

                confirmBtn.setOnAction(evt->{
                    if(nameF.validate()){                            
                        Care.process(()->{
                            try{
                                Platform.runLater(()->{
                                    t1orBtn.setDisable(true);
                                });

                                LocalDateTime now = LocalDateTime.now();
                                List<HospitalCharge> charges = getSelectedHospitalCharges();

                                charges.stream().forEach((charge) -> {
                                    List<HospitalChargeItem> items = new ArrayList();


                                    chargeItems.stream().forEach((item) -> {
                                        if(item.getChargenumber().equals(charge.getChargenumber())){
                                            items.add(item);                                        
                                            item.setCashier(Care.getUser().getName());
                                            item.setCashierid(Care.getUser().getId());  
                                            charge.setScid(item.getScid());
                                            charge.setPwdid(item.getPwdid());
                                            charge.setEmpid(item.getEmpid());
                                            charge.setOtdiscountremarks(item.getOtdiscountremarks());                                       
                                            item.update();
                                        }
                                    });

                                    charge.setChargetype("Accounts Recievable");
                                    charge.setCashier(Care.getUser().getName());
                                    charge.setCashierid(Care.getUser().getId());
                                    charge.setItems(items);
                                    charge.calculateTotal(items);     

                                    charge.update();
                                    
                                });
                                
                                Platform.runLater(()->{
                                    maskerPane.setVisible(true);
                                    clearTransaction(event);
                                    dialog.close();
                                });

                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }finally {
                                loadUnpaidHospitalChargesCounter();
                                Platform.runLater(()->{
                                    t1orBtn.setDisable(false);
                                    maskerPane.setVisible(false);
                                });
                            }
                        });
                    }    
                });

            }
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void printPayInvoice(ActionEvent event) {
        try{          
            if(getSelectedHospitalCharges().size() > 0){                
                String printName = getSelectedHospitalCharges().get(0).getChargeto();
                
                List<Double> nets = chargeItems.stream().map(obj->obj.getNetsales()).collect(Collectors.toList());                
                final double netamt = nets.stream().collect(Collectors.summingDouble(Double::doubleValue));
                
                DoubleProperty payable = new SimpleDoubleProperty();
                DoubleProperty tenderd = new SimpleDoubleProperty();
                DoubleProperty change = new SimpleDoubleProperty();

                payable.set(netamt);            

                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35,25,25,25));                               

                JFXTextField invoiceF = new JFXTextField();
                invoiceF.setPromptText("Invoice #");    
                invoiceF.setMinHeight(28);
                invoiceF.setMinWidth(250);
                invoiceF.setMaxWidth(250);
                invoiceF.setPrefWidth(250);   
                invoiceF.setLabelFloat(true);

                JFXTextField nameF = new JFXTextField(printName);
                nameF.setPromptText("Paid By");    
                nameF.setMinHeight(28);
                nameF.setMinWidth(250);
                nameF.setMaxWidth(250);
                nameF.setPrefWidth(250);   
                nameF.setLabelFloat(true);

                JFXTextField netF = new JFXTextField();
                netF.setPromptText("Net Payable");    
                netF.setMinHeight(28);
                netF.setMinWidth(250);
                netF.setMaxWidth(250);
                netF.setPrefWidth(250);   
                netF.setLabelFloat(true);
                netF.setEditable(false);

                JFXTextField tenderF = new JFXTextField();
                tenderF.setPromptText("Tendered Amount");    
                tenderF.setMinHeight(28);
                tenderF.setMinWidth(250);
                tenderF.setMaxWidth(250);
                tenderF.setPrefWidth(250);   
                tenderF.setLabelFloat(true);

                JFXTextField changeF = new JFXTextField();
                changeF.setPromptText("Change");    
                changeF.setMinHeight(28);
                changeF.setMinWidth(250);
                changeF.setMaxWidth(250);
                changeF.setPrefWidth(250);   
                changeF.setLabelFloat(true);

                netF.textProperty().bindBidirectional(payable, new NumberConverter());
                tenderF.textProperty().bindBidirectional(tenderd, new NumberConverter());
                changeF.textProperty().bindBidirectional(change, new NumberConverter());

                FXField.addRequiredValidator(nameF);
                FXField.addRequiredValidator(invoiceF);
                FXField.addRequiredValidator(tenderF);
                FXField.addDoubleValidator(tenderF, netamt, 9999999.0, 0.0);
                FXField.addFocusValidationListener(nameF,invoiceF,tenderF);

                tenderF.textProperty().addListener((obs,oldVal,newVal)->{
                    Platform.runLater(()->{
                        if(tenderd.get() <= payable.get()){
                            change.setValue(0);
                        }else{
                            change.setValue(tenderd.getValue()-payable.getValue());
                        }
                    });                    
                });
                
                payable.set(netamt);            

                content.getChildren().addAll(netF,invoiceF,nameF,tenderF,changeF);

                JFXButton printBtn = new JFXButton("Save & Print");                                    
                printBtn.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Pay & Print Invoice",content,FXDialog.DANGER,printBtn);
                invoiceF.requestFocus();

                printBtn.setOnAction(pruchaseEvt->{
                    if(invoiceF.validate() && tenderF.validate()){                        
                        Care.process(()->{
                            try{                                
                                Platform.runLater(()->{
                                    t1invoiceBtn.setDisable(true);
                                });
                                
                                LocalDateTime now = LocalDateTime.now();
                                List<HospitalCharge> charges = getSelectedHospitalCharges();

                                Payment payment = new Payment();
                                payment.setPatient(charges.get(0).getChargeto());
                                payment.setPaymenttype("Cash");
                                payment.setAmount(netamt);
                                payment.setPaidby(nameF.getText());
                                payment.setInvoicenumber(invoiceF.getText());
                                payment.setCashier(Care.getUser().getName());
                                payment.setEncoder(Care.getUser().getName());
                                payment.setPatient_id(charges.get(0).getPatient_id());
                                payment.setPaymenttime(now);

                                int pay_id = payment.save();

                                charges.stream().forEach((charge) -> {
                                    List<HospitalChargeItem> items = new ArrayList();
                                    chargeItems.stream().forEach((item) -> {
                                        if(item.getChargenumber().equals(charge.getChargenumber())){
                                            items.add(item);                                        
                                            item.setCashier(Care.getUser().getName());
                                            item.setCashierid(Care.getUser().getId());                                            
                                            charge.setScid(item.getScid());
                                            charge.setPwdid(item.getPwdid());
                                            charge.setEmpid(item.getEmpid());
                                            charge.setOtdiscountremarks(item.getOtdiscountremarks());
                                            item.update();
                                        }
                                    });

                                    charge.setPayment_id(pay_id);
                                    charge.setCashier(Care.getUser().getName());
                                    charge.setCashierid(Care.getUser().getId());
                                    charge.setInvoicenumber(invoiceF.getText());
                                    charge.setPaymenttype("Cash");
                                    charge.setPaymenttime(now);
                                    charge.setPaidamount(charge.getNetsales());
                                    charge.setItems(items);
                                    charge.calculateTotal(items);   

                                    charge.update();
                                });


                                Platform.runLater(()->{
                                    maskerPane.setVisible(true);                                    
                                    dialog.close();
                                });

                                try{
                                    printInvoice(false, chargeItems, invoiceF.getText(), nameF.getText(), now.toLocalDate());
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                }

                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }finally {
                                loadUnpaidHospitalChargesCounter();
                                Platform.runLater(()->{
                                    t1invoiceBtn.setDisable(false);
                                    maskerPane.setVisible(false);
                                    clearTransaction(event);
                                });
                            }
                        });
                    }             
                });

            }
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void printPayOR(ActionEvent event) {
        try{                        
            if(getSelectedHospitalCharges().size() > 0){
                String printName = getSelectedHospitalCharges().get(0).getChargeto();
                
                List<Double> nets = chargeItems.stream().map(obj->obj.getNetsales()).collect(Collectors.toList());                
                final double netamt = nets.stream().collect(Collectors.summingDouble(Double::doubleValue));
                
                DoubleProperty payable = new SimpleDoubleProperty();
                DoubleProperty tenderd = new SimpleDoubleProperty();
                DoubleProperty change = new SimpleDoubleProperty();

                //payable.set(netamt);            

                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(25);
                content.setPadding(new Insets(35,25,25,25));                               

                JFXTextField ornumF = new JFXTextField();
                ornumF.setPromptText("OR #");    
                ornumF.setMinHeight(24);
                ornumF.setMinWidth(250);
                ornumF.setMaxWidth(250);
                ornumF.setPrefWidth(250);   
                ornumF.setLabelFloat(true);

                JFXTextField nameF = new JFXTextField(printName);
                nameF.setPromptText("Paid By");    
                nameF.setMinHeight(24);
                nameF.setMinWidth(250);
                nameF.setMaxWidth(250);
                nameF.setPrefWidth(250);   
                nameF.setLabelFloat(true);
                
                JFXTextField addressF = new JFXTextField(printName);
                addressF.setPromptText("Address");    
                addressF.setMinHeight(24);
                addressF.setMinWidth(250);
                addressF.setMaxWidth(250);
                addressF.setPrefWidth(250);   
                addressF.setLabelFloat(true);
                
                JFXTextField tinF = new JFXTextField(printName);
                tinF.setPromptText("TIN");    
                tinF.setMinHeight(24);
                tinF.setMinWidth(250);
                tinF.setMaxWidth(250);
                tinF.setPrefWidth(250);   
                tinF.setLabelFloat(true);

                JFXTextField netpayF = new JFXTextField();
                netpayF.setPromptText("Net Payable");    
                netpayF.setMinHeight(24);
                netpayF.setMinWidth(250);
                netpayF.setMaxWidth(250);
                netpayF.setPrefWidth(250);   
                netpayF.setLabelFloat(true);
                netpayF.setEditable(false);

                JFXTextField tenderF = new JFXTextField();
                tenderF.setPromptText("Tendered Amount");    
                tenderF.setMinHeight(24);
                tenderF.setMinWidth(250);
                tenderF.setMaxWidth(250);
                tenderF.setPrefWidth(250);   
                tenderF.setLabelFloat(true);

                JFXTextField changeF = new JFXTextField();
                changeF.setPromptText("Change");    
                changeF.setMinHeight(24);
                changeF.setMinWidth(250);
                changeF.setMaxWidth(250);
                changeF.setPrefWidth(250);   
                changeF.setLabelFloat(true);

                netpayF.textProperty().bindBidirectional(payable, new NumberConverter());
                tenderF.textProperty().bindBidirectional(tenderd, new NumberConverter());
                changeF.textProperty().bindBidirectional(change, new NumberConverter());
                
                

                FXField.addRequiredValidator(nameF);
                FXField.addRequiredValidator(ornumF);
                FXField.addRequiredValidator(tenderF);
                FXField.addDoubleValidator(tenderF, netamt, 9999999.0, 0.0);
                FXField.addFocusValidationListener(nameF,ornumF,tenderF);

                tenderF.textProperty().addListener((obs,oldVal,newVal)->{
                    Platform.runLater(()->{
                        if(tenderd.get() <= payable.get()){
                            change.setValue(0);
                        }else{
                            change.setValue(tenderd.getValue()-payable.getValue());
                        }
                    });                    
                });
                
                payable.set(netamt);

                content.getChildren().addAll(netpayF,ornumF,nameF,tenderF,changeF);

                JFXButton confirmBtn = new JFXButton("Confirm");                                    
                confirmBtn.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Pay & Print OR",content,FXDialog.DANGER,confirmBtn);
                ornumF.requestFocus();

                confirmBtn.setOnAction(evt->{
                    if(ornumF.validate() && tenderF.validate()){                            
                        Care.process(()->{
                            try{
                                Platform.runLater(()->{
                                    t1orBtn.setDisable(true);
                                });

                                LocalDateTime now = LocalDateTime.now();
                                List<HospitalCharge> charges = getSelectedHospitalCharges();

                                Payment payment = new Payment();
                                payment.setPatient(charges.get(0).getChargeto());
                                payment.setPaymenttype("Cash");
                                payment.setAmount(netamt);
                                payment.setPaidby(nameF.getText());
                                payment.setOrnumber(ornumF.getText());
                                payment.setCashier(Care.getUser().getName());
                                payment.setEncoder(Care.getUser().getName());
                                payment.setPatient_id(charges.get(0).getPatient_id());
                                payment.setPaymenttime(now);

                                int pay_id = payment.save();

                                charges.stream().forEach((charge) -> {
                                    List<HospitalChargeItem> items = new ArrayList();


                                    chargeItems.stream().forEach((item) -> {
                                        if(item.getChargenumber().equals(charge.getChargenumber())){
                                            items.add(item);                                        
                                            item.setCashier(Care.getUser().getName());
                                            item.setCashierid(Care.getUser().getId());  
                                            charge.setScid(item.getScid());
                                            charge.setPwdid(item.getPwdid());
                                            charge.setEmpid(item.getEmpid());
                                            charge.setOtdiscountremarks(item.getOtdiscountremarks());                                       
                                            item.update();
                                        }
                                    });

                                    charge.setPayment_id(pay_id);
                                    charge.setCashier(Care.getUser().getName());
                                    charge.setCashierid(Care.getUser().getId());
                                    charge.setOrnumber(ornumF.getText());
                                    charge.setPaymenttype("Cash");
                                    charge.setPaymenttime(now);
                                    charge.setPaidamount(charge.getNetsales());
                                    charge.setItems(items);
                                    charge.calculateTotal(items);     

                                    charge.update();

                                    if(charge.getRecordtable().equals(LaboratoryTest.TABLE_NAME)){
                                        SQLTable.execute("UPDATE "+LaboratoryTest.TABLE_NAME+" SET "+LaboratoryTest.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+LaboratoryTest.ID+"="+charge.getRecordtableid());
                                    }else if(charge.getRecordtable().equals(RadiologyTest.TABLE_NAME)){
                                        SQLTable.execute("UPDATE "+RadiologyTest.TABLE_NAME+" SET "+RadiologyTest.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+RadiologyTest.ID+"="+charge.getRecordtableid());
                                    }else if(charge.getRecordtable().equals(Consultation.TABLE_NAME)){
                                        SQLTable.execute("UPDATE "+Consultation.TABLE_NAME+" SET "+Consultation.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+Consultation.ID+"="+charge.getRecordtableid());
                                    }else if(charge.getRecordtable().equals(ERConsultation.TABLE_NAME)){
                                        SQLTable.execute("UPDATE "+ERConsultation.TABLE_NAME+" SET "+ERConsultation.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+ERConsultation.ID+"="+charge.getRecordtableid());
                                    }
                                });

                                try{
                                    printOfficialReciept(false, charges, ornumF.getText(), nameF.getText(), "", "", "", now.toLocalDate());
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                }
                                Platform.runLater(()->{
                                    maskerPane.setVisible(true);
                                    clearTransaction(event);
                                    dialog.close();
                                });

                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }finally {
                                loadUnpaidHospitalChargesCounter();
                                Platform.runLater(()->{
                                    t1orBtn.setDisable(false);
                                    maskerPane.setVisible(false);
                                });
                            }
                        });
                    }    
                });

            }
            
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    @FXML
    void doPaymentOnly(ActionEvent event) {
        try{                        
            if(getSelectedHospitalCharges().size() > 0){
                String printName = getSelectedHospitalCharges().get(0).getChargeto();
                
                List<Double> nets = chargeItems.stream().map(obj->obj.getNetsales()).collect(Collectors.toList());                
                final double netamt = nets.stream().collect(Collectors.summingDouble(Double::doubleValue));
                
                DoubleProperty payable = new SimpleDoubleProperty();
                DoubleProperty tenderd = new SimpleDoubleProperty();
                DoubleProperty change = new SimpleDoubleProperty();

                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(25);
                content.setPadding(new Insets(35,25,25,25));                               

                JFXTextField ornumF = new JFXTextField();
                ornumF.setPromptText("OR #");    
                ornumF.setMinHeight(24);
                ornumF.setMinWidth(250);
                ornumF.setMaxWidth(250);
                ornumF.setPrefWidth(250);   
                ornumF.setLabelFloat(true);
                
                JFXTextField invF = new JFXTextField();
                invF.setPromptText("Invoice #");    
                invF.setMinHeight(24);
                invF.setMinWidth(250);
                invF.setMaxWidth(250);
                invF.setPrefWidth(250);   
                invF.setLabelFloat(true);

                JFXTextField nameF = new JFXTextField(printName);
                nameF.setPromptText("Paid By");    
                nameF.setMinHeight(24);
                nameF.setMinWidth(250);
                nameF.setMaxWidth(250);
                nameF.setPrefWidth(250);   
                nameF.setLabelFloat(true);

                JFXTextField netpayF = new JFXTextField();
                netpayF.setPromptText("Net Payable");    
                netpayF.setMinHeight(24);
                netpayF.setMinWidth(250);
                netpayF.setMaxWidth(250);
                netpayF.setPrefWidth(250);   
                netpayF.setLabelFloat(true);
                netpayF.setEditable(false);

                JFXTextField tenderF = new JFXTextField();
                tenderF.setPromptText("Tendered Amount");    
                tenderF.setMinHeight(24);
                tenderF.setMinWidth(250);
                tenderF.setMaxWidth(250);
                tenderF.setPrefWidth(250);   
                tenderF.setLabelFloat(true);

                JFXTextField changeF = new JFXTextField();
                changeF.setPromptText("Change");    
                changeF.setMinHeight(24);
                changeF.setMinWidth(250);
                changeF.setMaxWidth(250);
                changeF.setPrefWidth(250);   
                changeF.setLabelFloat(true);

                netpayF.textProperty().bindBidirectional(payable, new NumberConverter());
                tenderF.textProperty().bindBidirectional(tenderd, new NumberConverter());
                changeF.textProperty().bindBidirectional(change, new NumberConverter());

                FXField.addRequiredValidator(nameF);
                FXField.addRequiredValidator(tenderF);
                FXField.addDoubleValidator(tenderF, netamt, 9999999.0, 0.0);
                FXField.addFocusValidationListener(nameF,tenderF);

                tenderF.textProperty().addListener((obs,oldVal,newVal)->{
                    Platform.runLater(()->{
                        if(tenderd.get() <= payable.get()){
                            change.setValue(0);
                        }else{
                            change.setValue(tenderd.getValue()-payable.getValue());
                        }
                    });                    
                });
                
                payable.set(netamt);    

                content.getChildren().addAll(netpayF,ornumF,invF,nameF,tenderF,changeF);

                JFXButton confirmBtn = new JFXButton("Confirm");                                    
                confirmBtn.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Pay & Print OR",content,FXDialog.DANGER,confirmBtn);
                invF.requestFocus();

                confirmBtn.setOnAction(evt->{
                    if(nameF.validate() && tenderF.validate()){
                        Platform.runLater(()->{
                            t1payBtn.setDisable(true);
                            Care.process(()->{
                                try{
                                    LocalDateTime now = LocalDateTime.now();
                                    List<HospitalCharge> charges = getSelectedHospitalCharges();
                                    
                                    Payment payment = new Payment();
                                    payment.setPatient(charges.get(0).getChargeto());
                                    payment.setPaymenttype("Cash");
                                    payment.setAmount(netamt);
                                    payment.setPaidby(nameF.getText());
                                    payment.setOrnumber(ornumF.getText());
                                    payment.setInvoicenumber(invF.getText());
                                    payment.setCashier(Care.getUser().getName());
                                    payment.setEncoder(Care.getUser().getName());
                                    payment.setPatient_id(charges.get(0).getPatient_id());
                                    payment.setPaymenttime(now);

                                    int pay_id = payment.save();
                                    
                                    charges.stream().forEach((charge) -> {
                                        List<HospitalChargeItem> items = new ArrayList();


                                        chargeItems.stream().forEach((item) -> {
                                            if(item.getChargenumber().equals(charge.getChargenumber())){
                                                items.add(item);                                        
                                                item.setCashier(Care.getUser().getName());
                                                item.setCashierid(Care.getUser().getId());  
                                                charge.setScid(item.getScid());
                                                charge.setPwdid(item.getPwdid());
                                                charge.setEmpid(item.getEmpid());
                                                charge.setOtdiscountremarks(item.getOtdiscountremarks());                                       
                                                item.update();
                                            }
                                        });
                                        
                                        charge.setPayment_id(pay_id);
                                        charge.setCashier(Care.getUser().getName());
                                        charge.setCashierid(Care.getUser().getId());
                                        charge.setOrnumber(ornumF.getText());
                                        charge.setInvoicenumber(invF.getText());
                                        charge.setPaymenttype("Cash");
                                        charge.setPaymenttime(now);
                                        charge.setPaidamount(charge.getNetsales());
                                        charge.setItems(items);
                                        charge.calculateTotal(items);     

                                        charge.update();
                                        
                                        if(charge.getRecordtable().equals(LaboratoryTest.TABLE_NAME)){
                                            SQLTable.execute("UPDATE "+LaboratoryTest.TABLE_NAME+" SET "+LaboratoryTest.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+LaboratoryTest.ID+"="+charge.getRecordtableid());
                                        }else if(charge.getRecordtable().equals(RadiologyTest.TABLE_NAME)){
                                            SQLTable.execute("UPDATE "+RadiologyTest.TABLE_NAME+" SET "+RadiologyTest.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+RadiologyTest.ID+"="+charge.getRecordtableid());
                                        }else if(charge.getRecordtable().equals(Consultation.TABLE_NAME)){
                                            SQLTable.execute("UPDATE "+Consultation.TABLE_NAME+" SET "+Consultation.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+Consultation.ID+"="+charge.getRecordtableid());
                                        }else if(charge.getRecordtable().equals(ERConsultation.TABLE_NAME)){
                                            SQLTable.execute("UPDATE "+ERConsultation.TABLE_NAME+" SET "+ERConsultation.ORNUMBER+"='"+ornumF.getText()+"' WHERE "+ERConsultation.ID+"="+charge.getRecordtableid());
                                        }
                                    });



                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                        clearTransaction(event);
                                        dialog.close();
                                        FXDialog.showMessageDialog(mainStack, "Payment Registered", "Payment has been successfully saved!", FXDialog.SUCCESS);
                                    });


                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                }finally {
                                    loadUnpaidHospitalChargesCounter();
                                    Platform.runLater(()->{
                                        t1payBtn.setDisable(false);
                                        maskerPane.setVisible(false);
                                    });
                                }
                            });
                        });
                    }else{

                    }    
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchChargeNumber(ActionEvent event) {
        try{
            if(!t1chargeF.getText().isEmpty()){
                Care.process(()->{
                    try{
                        List<HospitalCharge> records = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGENUMBER+"='"+t1chargeF.getText()+"' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL LIMIT 1");
                        if(!records.isEmpty()){
                            HospitalCharge charge = records.get(0);
                            List<HospitalCharge> charges = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETO+"='"+charge.getChargeto()+"' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
                            Platform.runLater(()->{
                                FXTable.setList(t1resultsTbl, charges);
                                charges.stream().forEach(ccharge->{
                                    if(ccharge.getChargenumber().equals(charge.getChargenumber())){
                                        ccharge.setSelected(true);
                                    }
                                });
                                t1resultsLbl.setText(String.valueOf(charges.size()));
                                loadSelectedCharges();
                            });
                        }else{
                            Platform.runLater(()->{
                                FXTable.setList(t1resultsTbl, new ArrayList());                                
                                t1resultsLbl.setText("0");
                                loadSelectedCharges();
                            });
                        }
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchPatientCharges(ActionEvent event) {
        try{
            if(!t1patientF.getText().isEmpty()){
                Care.process(()->{
                    try{
                        List<HospitalCharge> records = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETO+"='"+t1patientF.getText()+"' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETIME+" DESC");    
                        if(!records.isEmpty()){
                            Platform.runLater(()->{
                                FXTable.setList(t1resultsTbl, records);    
                                t1resultsLbl.setText(String.valueOf(records.size()));
                            });
                        }else{
                            Platform.runLater(()->{
                                FXTable.setList(t1resultsTbl, new ArrayList());
                                t1resultsLbl.setText("0");
                            });
                        }
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void showAdvances(ActionEvent event) {
        mainTabPane.getSelectionModel().select(advancesTab);
        loadCashAdvanceList(null);
    }

    @FXML
    void showCharges(ActionEvent event) {
        mainTabPane.getSelectionModel().select(chargesTab);
        loadChargesList(HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL");
    }

    @FXML
    void showPaymentTerminal(ActionEvent event) {
        mainTabPane.getSelectionModel().select(terminalTab);
    }

    @FXML
    void showPaymentHistory(ActionEvent event) {
        mainTabPane.getSelectionModel().select(paymentsTab);
        loadPaymentList(null);
    }
    
    /**
     * Get the value of chargeItems
     *
     * @return the value of chargeItems
     */
    public List<HospitalChargeItem> getChargeItems() {
        return chargeItems;
    }

    /**
     * Set the value of chargeItems
     *
     * @param chargeItems new value of chargeItems
     */
    public void setChargeItems(List<HospitalChargeItem> chargeItems) {
        this.chargeItems = chargeItems;
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
        this.mainStack = stackpane;
    }

    @Override
    public StackPane getMainStack() {
        return this.mainStack;
    }

    @Override
    public void loadCustomizations() {
        try{
            //Payment Terminal
            loadSearchResultTable();
            t1chargeF.setOnKeyReleased(keyevt ->{
                if(keyevt.getCode() == KeyCode.ENTER){
                    t1chargeBtn.fire();
                    t1invoiceBtn.setDisable(false);
                    t1orBtn.setDisable(false);
                }
            });
            t1patientF.setOnKeyReleased(keyevt ->{
                if(keyevt.getCode() == KeyCode.ENTER){
                    t1patientBtn.fire();
                    t1invoiceBtn.setDisable(false);
                    t1orBtn.setDisable(false);
                }
            });
            
            
            t1patientF.focusedProperty().addListener((obs,oldVal,newVal)->{
                if(newVal){
                    Care.process(()->{
                        List<String> chargenames = SQLTable.distinct(HospitalCharge.class, HospitalCharge.CHARGETO,HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                        Platform.runLater(()->{                            
                            TextFields.bindAutoCompletion(t1patientF, chargenames);     
                        });                        
                    });
                }
            });
            
            loadTransactionItemsTable();
            
            //Charges Tab
            loadChargesTable();
            
            //Payments Tab
            loadPaymentTable();
            
            //Cash Advanced Tab
            loadCashAdvanceTable();
            
            //Billing Statement Tab
            loadBillsTable();
            
            //Remove FocusTab
            mainTabPane.addEventFilter(KeyEvent.ANY, event -> {
                if (event.getCode().isArrowKey() && event.getTarget() == mainTabPane) {
                    event.consume();
                }
            });
            //Disable Unselected Tab
            menuGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
                if (newVal == null) {
                    oldVal.setSelected(true);
                }
            });
                        
            if(unpaidHCCounterTask != null){
                unpaidHCCounterTask.cancel(true);
            }
            
            unpaidHCCounterTask = Care.processEvery(()->{
                loadUnpaidHospitalChargesCounter();
                System.out.println("Unpaid Counter Refresh");
            }, 0, 30, TimeUnit.SECONDS);
            
            
            
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
            loadDischargeTableFilters();
            loadPaymentTableFilters();
            loadCashAdvanceTableFilters();
            loadBillStatementTableFilters();
            
            pendingchargeCombo.getItems().addAll("","All Pending Charges","Pharmacy Charges","OPD Charges","Laboratory Charges","Radiology Charges","ER Charges");
            pendingchargeCombo.getSelectionModel().selectedIndexProperty().addListener((obs,oldVal,newVal)->{
                try{
                    if(newVal != null){
                        Care.process(()->{                            
                            try{
                                System.out.println("Selected : "+newVal);
                                Platform.runLater(()->{
                                    if(newVal.intValue() == 2){
                                        t1invoiceBtn.setDisable(false);
                                        t1orBtn.setDisable(true);
                                    }else{
                                        t1invoiceBtn.setDisable(true);
                                        t1orBtn.setDisable(false);
                                    }
                                });
                                switch (newVal.intValue()) {
                                    case 1:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    case 2:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGEFACILITY+"='Pharmacy' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    case 3:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGEFACILITY+"='OPD' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    case 4:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGEFACILITY+"='Laboratory' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    case 5:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGEFACILITY+"='Radiology' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    case 6:
                                        {
                                            List<HospitalCharge> records = SQLTable.list(HospitalCharge.class,HospitalCharge.CHARGEFACILITY+"='ER' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAIDAMOUNT+"<=0 AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY "+HospitalCharge.CHARGETO+" ASC");
                                            if(!records.isEmpty()){
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, records);
                                                    t1resultsLbl.setText(String.valueOf(records.size()));
                                                });
                                            }else{
                                                Platform.runLater(()->{
                                                    FXTable.setList(t1resultsTbl, new ArrayList());
                                                    t1resultsLbl.setText("0");
                                                });
                                            }       break;
                                        }
                                    default:
                                        break;
                                }
                            }catch(Exception er){
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        });
                    }
                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
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
        return this.maskerPane;
    }
    
    private void loadSearchResultTable(){
        try{
            t1resultsTbl.setEditable(true);
            t1resultsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn selCol = FXTable.addColumn(t1resultsTbl, " ", HospitalCharge::selectedProperty, false, 32, 32, 32);
            TableColumn desCol = FXTable.addColumn(t1resultsTbl, " ", HospitalCharge::chargenumberProperty, false);
            
            selCol.setCellFactory(column -> {
                return new TableCell<HospitalCharge, Boolean>() {

                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);

                        if (empty) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalCharge row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    JFXCheckBox selectBox = new JFXCheckBox("");
                                    selectBox.setMinSize(32, 32);

                                    selectBox.selectedProperty().bindBidirectional(row_data.selectedProperty());
                                    selectBox.setStyle("-fx-alignment:CENTER;-fx-cursor:HAND;");

                                    selectBox.selectedProperty().addListener((obs, oldVal, newVal) -> {                                        
                                        loadSelectedCharges();
                                    });

                                    setGraphic(selectBox);
                                    setStyle("");
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
            
            desCol.setCellFactory(column -> {
                return new TableCell<HospitalCharge, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalCharge row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    VBox container = new VBox();
                                    container.setPrefSize(Double.MAX_VALUE, 54);

                                    Label cnumber = new Label(row_data.getChargenumber()+" - "+row_data.getChargefacility());    
                                    cnumber.setPrefSize(Double.MAX_VALUE,18);
                                    cnumber.setTextAlignment(TextAlignment.LEFT);
                                    cnumber.setAlignment(Pos.CENTER_LEFT);                                
                                    
                                    //cnumber.setStyle("-fx-font-weight:bold;-fx-font-size:14px");

                                    Label ctime = new Label(DateTimeKit.toProperTimestamp(row_data.getChargetime())+ " / "+row_data.getUser());
                                    ctime.setPrefSize(Double.MAX_VALUE,18);
                                    ctime.setTextAlignment(TextAlignment.LEFT);
                                    ctime.setAlignment(Pos.CENTER_LEFT);
                                    
                                    
                                    HBox hcontainer1 = new HBox();
                                    hcontainer1.setPrefSize(350,18);
                                    //hcontainer1.minWidthProperty().bind(desCol.widthProperty());

                                    Label cname = new Label(row_data.getChargeto());
                                    cname.setPrefSize(Double.MAX_VALUE,18);
                                    cname.setMaxWidth(350);
                                    cname.setTextAlignment(TextAlignment.LEFT);
                                    cname.setAlignment(Pos.CENTER_LEFT);
                                    cname.setStyle("-fx-font-weight: bold;");
                                    
                                    Label optcharge = new Label(row_data.getCareto());
                                    optcharge.setPrefSize(Double.MAX_VALUE,18);
                                    optcharge.setTextAlignment(TextAlignment.LEFT);
                                    optcharge.setAlignment(Pos.CENTER_LEFT);
                                    optcharge.setStyle("-fx-text-fill:danger-color;");
                                    
                                    Label ctotal = new Label(NumberKit.toCurrency(row_data.getTotalgross()));
                                    ctotal.setPrefSize(90,18);
                                    ctotal.setMinSize(90,18);
                                    ctotal.setTextAlignment(TextAlignment.RIGHT);
                                    ctotal.setAlignment(Pos.CENTER_RIGHT);
                                    ctotal.setStyle("-fx-text-fill:primary-color;-fx-font-weight:bold;-fx-cursor:HAND;");
                                    ctotal.setOnMouseClicked(mouseCEvt->{
                                        Platform.runLater(()->{
                                            FXDialog.showConfirmDialog(mainStack,"Charge Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                        });
                                    });
                                    
                                    HBox.setHgrow(ctime, Priority.ALWAYS);
                                    //HBox.setHgrow(ctotal, Priority.ALWAYS);     
                                    HBox.setHgrow(cname, Priority.ALWAYS);
                                    HBox.setHgrow(optcharge, Priority.ALWAYS);
                                    
                                    hcontainer1.getChildren().addAll(cname,ctotal);
                                    
                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    
                                    if(row_data.getCareto().isEmpty()){
                                        container.getChildren().addAll(cnumber, ctime, hcontainer1);
                                    }else{
                                        container.getChildren().addAll(cnumber, ctime, hcontainer1,optcharge);
                                    }
                                    

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
    
    
    private void loadTransactionItemsTable(){
        try{
            t1pchargeitemsTbl.setEditable(true);
            t1pchargeitemsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn chrCol = FXTable.addColumn(t1pchargeitemsTbl, "Charge #", HospitalChargeItem::chargenumberProperty, false, 110, 110, 110);            
            TableColumn parCol = FXTable.addColumn(t1pchargeitemsTbl, "Particulars", HospitalChargeItem::descriptionProperty, false);            
            TableColumn disCol = FXTable.addColumn(t1pchargeitemsTbl, "Discounts", HospitalChargeItem::descriptionProperty, false,120,120,120);
            TableColumn totCol = FXTable.addColumn(t1pchargeitemsTbl, "Total", HospitalChargeItem::descriptionProperty, false,80,80,80);
            
            chrCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setPrefSize(Double.MAX_VALUE, 55);

                                    Label cnumber = new Label(row_data.getChargenumber());    
                                    cnumber.setPrefSize(Double.MAX_VALUE,18);
                                    cnumber.setTextAlignment(TextAlignment.LEFT);
                                    cnumber.setAlignment(Pos.CENTER_LEFT);          
                                    cnumber.setStyle("-fx-font-weight : bold;");
                                    
                                    Label cdep = new Label(row_data.getFacility());
                                    cdep.setPrefSize(Double.MAX_VALUE,18);
                                    cdep.setTextAlignment(TextAlignment.LEFT);
                                    cdep.setAlignment(Pos.CENTER_LEFT);
                                    
                                    Label cusr = new Label(row_data.getUser());
                                    cusr.setPrefSize(Double.MAX_VALUE,18);
                                    cusr.setTextAlignment(TextAlignment.LEFT);
                                    cusr.setAlignment(Pos.CENTER_LEFT);
                                    
                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(cnumber, cdep,cusr);

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
            
            parCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setPrefSize(Double.MAX_VALUE, 55);

                                    Label d1 = new Label(row_data.getDescription());    
                                    d1.setPrefSize(Double.MAX_VALUE,18);
                                    d1.setTextAlignment(TextAlignment.LEFT);
                                    d1.setAlignment(Pos.CENTER_LEFT);    
                                    if(row_data.getItemtable().equals(Item.TABLE_NAME) && !row_data.isVatable()){
                                        d1.setStyle("-fx-font-weight : bold;-fx-text-fill:danger-color;");
                                    }else{
                                        d1.setStyle("-fx-font-weight : bold;");
                                    }
                                    
                                    
                                    Label d2 = new Label("Qty : "+row_data.getQuantity()+" * Price : "+NumberKit.toCurrency(row_data.getSelling())+" = Amount : "+NumberKit.toCurrency(row_data.getTotalselling()));
                                    d2.setPrefSize(Double.MAX_VALUE,18);
                                    d2.setTextAlignment(TextAlignment.LEFT);
                                    d2.setAlignment(Pos.CENTER_LEFT);
                                    d2.setStyle("-fx-font-style : italic;");
                                    
                                    Label d3 = new Label();
                                    d3.setPrefSize(Double.MAX_VALUE,18);
                                    d3.setTextAlignment(TextAlignment.LEFT);
                                    d3.setAlignment(Pos.CENTER_LEFT);
                                    d3.setStyle("-fx-font-style : italic;");
                                    d3.textProperty().bind(Bindings.concat("VAT Sales : ",row_data.vatsalesProperty().asString("%,.2f")," / Non-VAT Sales : ",row_data.nonvatsalesProperty().asString("%,.2f")," / VAT : ",row_data.inputvatProperty().asString("%,.2f")," / LESS-VAT : ",row_data.lessvatProperty().asString("%,.2f")));
                                    
                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(d1, d2,d3);

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
            
            disCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setPrefSize(Double.MAX_VALUE, 55);

                                    Label d1 = new Label();    
                                    d1.setPrefSize(Double.MAX_VALUE,18);
                                    d1.setTextAlignment(TextAlignment.RIGHT);
                                    d1.setAlignment(Pos.CENTER_RIGHT);    
                                    d1.textProperty().bind(Bindings.concat("SC/PWD : ",row_data.scpwdvalueProperty().asString("%,.2f")));
                                    
                                    Label d2 = new Label();
                                    d2.setPrefSize(Double.MAX_VALUE,18);
                                    d2.setTextAlignment(TextAlignment.RIGHT);
                                    d2.setAlignment(Pos.CENTER_RIGHT);
                                    d2.textProperty().bind(Bindings.concat("EMP : ",row_data.empdiscountProperty().asString("%,.2f")));
                                    
                                    Label d3 = new Label();
                                    d3.setPrefSize(Double.MAX_VALUE,18);
                                    d3.setTextAlignment(TextAlignment.RIGHT);
                                    d3.setAlignment(Pos.CENTER_RIGHT);
                                    d3.textProperty().bind(Bindings.concat("OTHER : ",row_data.otdiscountProperty().asString("%,.2f")));
                                    
                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(d1, d2,d3);

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
            
            totCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setPrefSize(Double.MAX_VALUE, 55);

                                    Label d1 = new Label();    
                                    d1.setPrefSize(Double.MAX_VALUE,14);
                                    d1.setTextAlignment(TextAlignment.RIGHT);
                                    d1.setAlignment(Pos.CENTER_RIGHT);    
                                    
                                    
                                    Label d2 = new Label();
                                    d2.setPrefSize(Double.MAX_VALUE,14);
                                    d2.setTextAlignment(TextAlignment.RIGHT);
                                    d2.setAlignment(Pos.CENTER_RIGHT);
                                   
                                    
                                    Label d3 = new Label();
                                    d3.setPrefSize(Double.MAX_VALUE,26);
                                    d3.setTextAlignment(TextAlignment.RIGHT);
                                    d3.setAlignment(Pos.CENTER_RIGHT);
                                    d3.textProperty().bind(Bindings.concat(row_data.netsalesProperty().asString("%,.2f")));
                                    d3.setStyle("-fx-font-weight: bold;-fx-font-size:15px;");
                                    
                                    container.setFillWidth(true);
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(d1, d2,d3);

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
    
    private void loadSelectedCharges(){
        try{
            List<HospitalCharge> recs = t1resultsTbl.getItems();
            List<Boolean> selList = recs.stream().map(ch -> ch.isSelected()).collect(Collectors.toList());
            List<HospitalCharge> selCharges = new ArrayList();
            String[] chargeNums;
            int count = 0;
            for(int i = 0;i < recs.size();i++){
                if(recs.get(i).isSelected()){
                    selCharges.add(recs.get(i).getModelClone());
                    count++;
                }
                if(recs.get(i).getChargefacility().equalsIgnoreCase("Pharmacy")){
                    
                }
            }
            chargeNums = new String[count];
            count = 0;
            for(int i = 0;i < recs.size();i++){
                if(recs.get(i).isSelected()){
                    chargeNums[count] = "'"+recs.get(i).getChargenumber()+"'";
                    count++;
                }
            }
            /*
            SC_PERCENT = 0;
            PWD_PERCENT = 0;
            EMP_PERCENT = 0;
            OT_PERCENT = 0;
            */
            
            boolean hasPharmacy = selCharges.stream().anyMatch(t -> t.getChargefacility().equals("Pharmacy"));            
            t1orBtn.setDisable(hasPharmacy);
            t1invoiceBtn.setDisable(!hasPharmacy);
            
            if(selList.contains(true)){
                Care.process(()->{
                    try{
                        List<HospitalChargeItem> citems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGENUMBER+" IN ("+StringKit.comateArray(chargeNums)+") ORDER BY "+HospitalChargeItem.CHARGENUMBER+" ASC");
                        if(!citems.isEmpty()){
                            setChargeItems(citems);
                            Platform.runLater(()->{                            
                                //t1pnameLbl.setText(citems.get(0).getChargeto());
                                
                                t1phidLbl.setText(String.valueOf(selCharges.size()));
                                FXTable.setList(t1pchargeitemsTbl, chargeItems);
                                loadTransactionValues(chargeItems);
                            });
                        }
                        
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                });
            }else{
                setChargeItems(new ArrayList());
                FXTable.setList(t1pchargeitemsTbl, new ArrayList());
                t1phidLbl.setText("");
                loadTransactionValues(new ArrayList());
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private List<HospitalCharge> getSelectedHospitalCharges(){
        List<HospitalCharge> selCharges = new ArrayList();
        try{
            List<HospitalCharge> recs = t1resultsTbl.getItems();
            for(int i = 0;i < recs.size();i++){
                if(recs.get(i).isSelected()){
                    selCharges.add(recs.get(i).getModelClone());
                }
            }
            return selCharges;
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            return new ArrayList();
        }
    }
    
    private void loadTransactionValues(List<HospitalChargeItem> items){
        try{
            
            double gross = 0;
            double vatsales = 0;
            double vatexsales = 0;
            double zerosales = 0;
            double vat = 0;
            double lessvat = 0;
            double scpwd = 0;
            double emp = 0;
            double ot = 0;
            double net = 0;
            for(int i = 0;i < items.size();i++){
                gross += items.get(i).getTotalselling();
                vatsales += items.get(i).getVatsales();
                vatexsales += items.get(i).getNonvatsales();
                zerosales += items.get(i).getZeroratedsales();
                vat += items.get(i).getInputvat();
                lessvat += items.get(i).getLessvat();
                scpwd += items.get(i).getScdiscount()+items.get(i).getPwddiscount();
                emp += items.get(i).getEmpdiscount();
                ot += items.get(i).getOtdiscount();
                net += items.get(i).getNetsales();
            }
            t1subtotalLbl.setText(NumberKit.toCurrency(gross));
            t1vatsalesLbl.setText(NumberKit.toCurrency(vatsales));
            t1vatexLbl.setText(NumberKit.toCurrency(vatexsales));
            t1zeroLbl.setText(NumberKit.toCurrency(zerosales));
            t1taxLbl.setText(NumberKit.toCurrency(vat));
            t1netvatLbl.setText(NumberKit.toCurrency(vat));
            t1lessvatLbl.setText(NumberKit.toCurrency(lessvat));
            t1scpwdLbl.setText(NumberKit.toCurrency(scpwd));
            t1empLbl.setText(NumberKit.toCurrency(emp));
            t1otherLbl.setText(NumberKit.toCurrency(ot));
            t1netLbl.setText(NumberKit.toCurrency(net));
            t1chargescountLbl.setText(String.valueOf(items.size()));
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadChargesTable(){
        try{
            t2Tbl.setEditable(true);
            t2Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t2Tbl, "Timestamp", HospitalCharge::chargetimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t2Tbl, "Charge #", HospitalCharge::chargenumberProperty, false, 100, 100, 100);
            FXTable.addColumn(t2Tbl, "Department", HospitalCharge::chargefacilityProperty, false, 80, 80, 80);
            FXTable.addColumn(t2Tbl, "Type", HospitalCharge::chargetypeProperty, false,80,80,80);
            FXTable.addColumn(t2Tbl, "Charge To", HospitalCharge::chargetoProperty, false);
            //FXTable.addColumn(t2Tbl, "Care of", HospitalCharge::caretoProperty, false);
            //FXTable.addColumn(t2Tbl, "Encoder", HospitalCharge::userProperty, false);
            FXTable.addColumn(t2Tbl, "Voided", HospitalCharge::voidedProperty, false,100,100,100);
            FXTable.addColumn(t2Tbl, "NET Sales", HospitalCharge::netsalesProperty, false,80,80,80);
            FXTable.addColumn(t2Tbl, "OR #", HospitalCharge::ornumberProperty, false,80,80,80);
            FXTable.addColumn(t2Tbl, "Invoice #", HospitalCharge::invoicenumberProperty, false,80,80,80);
            FXTable.addColumn(t2Tbl, "Paid", HospitalCharge::paidamountProperty, false,80,80,80);
            TableColumn actCol = FXTable.addColumn(t2Tbl, "Actions", HospitalCharge::chargenumberProperty, false, 76, 76, 76);
            
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<HospitalCharge, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalCharge row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Charge Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Void this Transaction!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(mainStack, "Confirmation", new Label("Void Charge "+row_data.getChargenumber()+" ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            dl.close();
                                            row_data.setVoided(Care.getUser().getName());
                                            row_data.setVoidtime(LocalDateTime.now());
                                            List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class,HospitalChargeItem.HOSPITALCHARGE_ID+"='"+row_data.getId()+"'");
                                            if(row_data.update(true)){
                                                items.stream().forEach(itm->{                                                    
                                                    itm.setVoided(Care.getUser().getName());
                                                    itm.setVoidtime(LocalDateTime.now());
                                                    itm.update();
                                                    if(itm.getItemtable().equals(Item.TABLE_NAME)){
                                                        itm.addItemQuantity();
                                                    }
                                                });                                                
                                                FXDialog.showMessageDialog(mainStack, "Voided", "Charge has been voided!", FXDialog.SUCCESS);
                                                loadChargesList(HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL");
                                                loadUnpaidHospitalChargesCounter();
                                            }else{
                                                FXDialog.showMessageDialog(mainStack, "Failed", "Server Communication Failure", FXDialog.SUCCESS);
                                            }
                                        });
                                    });
                                    
                                    voidBtn.setDisable((row_data.getVoidtime()!=null));
                                    
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,voidBtn);

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
    
    private void loadPaymentTable(){
        try{
            t3Tbl.setEditable(true);
            t3Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t3Tbl, "Timestamp", Payment::paymenttimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t3Tbl, "Patient", Payment::patientProperty, false);
            FXTable.addColumn(t3Tbl, "OR #", Payment::ornumberProperty, false,80,80,80);
            FXTable.addColumn(t3Tbl, "Invoice #", Payment::invoicenumberProperty, false,80,80,80);
            FXTable.addColumn(t3Tbl, "Cashier", Payment::cashierProperty, false);
            FXTable.addColumn(t3Tbl, "Paid By", Payment::paidbyProperty, false);
            FXTable.addColumn(t3Tbl, "Cancelled", Payment::cancelledProperty, false);
            TableColumn cancelCol = FXTable.addColumn(t3Tbl, "Cancel Time", Payment::canceltimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t3Tbl, "Amount", Payment::amountProperty, false,80,80,80);
            TableColumn actCol = FXTable.addColumn(t3Tbl, "Actions", Payment::patientProperty, false, 112, 112, 112);
            
            FXTable.setTimestampColumn(timeCol);
            FXTable.setTimestampColumn(cancelCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Payment, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Payment row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(112, 40);
                                    container.setMaxSize(112, 40);
                                    container.setPrefSize(112, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Payment Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton printBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PRINT, "14px", evt -> {
                                        Care.process(()->{
                                            try{                                            
                                                String ref_or = row_data.getOrnumber();
                                                String ref_inv = row_data.getInvoicenumber();
                                                List<HospitalCharge> charges = new ArrayList();
                                                List<HospitalChargeItem> items = new ArrayList();
                                                if(!ref_or.isEmpty()){
                                                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PAYMENT_ID+"='"+row_data.getId()+"'");
                                                    printOfficialReciept(false, charges, row_data.getOrnumber(), row_data.getPaidby(), "", "", "", row_data.getPaymenttime().toLocalDate());
                                                }else{
                                                    charges = SQLTable.list(HospitalCharge.class, HospitalCharge.PAYMENT_ID+"='"+row_data.getId()+"'");
                                                    charges.stream().forEach(charge->{
                                                        List<HospitalChargeItem> citems = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+charge.getId());
                                                        for(HospitalChargeItem itm:citems){                                                            
                                                            items.add(itm);
                                                        }
                                                    });
                                                    if(items.size() > 0){
                                                        printInvoice(false, items, row_data.getInvoicenumber(), row_data.getPaidby(), row_data.getPaymenttime().toLocalDate());
                                                    }
                                                }
                                            }catch(Exception er){
                                                System.out.println("Error on print reciepts");
                                            }
                                        });
                                    });
                                    
                                    printBtn.setTooltip(new Tooltip("Print"));
                                    printBtn.getStyleClass().add("btn-success");
                                    printBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    printBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Void this Payment!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(mainStack, "Confirmation", new Label("Cancell & Void this transaction & payment ?"), FXDialog.WARNING,btn);
                                        btn.setOnAction(voidevt->{
                                            dl.close();
                                            Care.process(()->{
                                                try{
                                                    LocalDateTime now = LocalDateTime.now();
                                                    row_data.setCancelled(Care.getUser().getName());
                                                    row_data.setCanceltime(now);
                                                    if(row_data.update(true)){
                                                        HospitalCharge chr = (HospitalCharge)SQLTable.get(HospitalCharge.class, HospitalCharge.PAYMENT_ID, String.valueOf(row_data.getId()));
                                                        List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class,HospitalChargeItem.HOSPITALCHARGE_ID+"='"+chr.getId()+"'");
                                                        chr.setVoided(Care.getUser().getName());
                                                        chr.setVoidtime(now);
                                                        items.stream().forEach(itm->{                                                    
                                                            itm.setVoided(Care.getUser().getName());
                                                            itm.setVoidtime(now);
                                                            itm.update();
                                                            if(itm.getItemtable().equals(Item.TABLE_NAME)){
                                                                itm.addItemQuantity();
                                                            }
                                                        });                                                
                                                        FXDialog.showMessageDialog(mainStack, "Voided", "Payment & Charges has been voided!", FXDialog.SUCCESS);
                                                        loadPaymentList(null);                                                        
                                                    }else{
                                                        FXDialog.showMessageDialog(mainStack, "Failed", "Server Communication Failure", FXDialog.SUCCESS);
                                                    }
                                                }catch(Exception er){
                                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                }
                                            });
                                        });
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    voidBtn.setDisable(row_data.getCanceltime() != null);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,printBtn,voidBtn);

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
    
    private void loadBillsTable(){
        try{
            t5Tbl.setEditable(false);
            t5Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(t5Tbl);

            TableColumn timeCol = FXTable.addColumn(t5Tbl, "Timestamp", BillStatement::billtimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t5Tbl, "SOA no.", BillStatement::billnumberProperty, false, 80, 80, 80);
            FXTable.addColumn(t5Tbl, "Patient", BillStatement::patientnameProperty, false);
            FXTable.addColumn(t5Tbl, "Final Diagnosis", BillStatement::finaldiagnosisProperty, false);
            FXTable.addColumn(t5Tbl, "Other Diagnosis", BillStatement::otherdiagnosisProperty, false);
            FXTable.addColumn(t5Tbl, "Net Payable", BillStatement::netsalesProperty, false,80,80,80);
            FXTable.addColumn(t5Tbl, "Paid Amount", BillStatement::paidamountProperty, false,80,80,80);
            FXTable.addColumn(t5Tbl, "Cancelled", BillStatement::cancelledProperty, false,125,125,125);
            TableColumn ctimeCol = FXTable.addColumn(t5Tbl, "Cancelled Time", BillStatement::canceltimeProperty, false,125,125,125);
            FXTable.addColumn(t5Tbl, "Finalized", BillStatement::finalizedProperty, false);
            TableColumn ftimeCol = FXTable.addColumn(t5Tbl, "Finalized Time", BillStatement::finalizedtimeProperty, false,125,125,125);
            TableColumn actCol = FXTable.addColumn(t5Tbl, " ", BillStatement::patientnameProperty, false,72,72,72);
            
            FXTable.setTimestampColumn(timeCol);
            FXTable.setTimestampColumn(ctimeCol);
            FXTable.setTimestampColumn(ftimeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<BillStatement, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                BillStatement row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(72, 40);
                                    container.setMaxSize(72, 40);
                                    container.setPrefSize(72, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Billing Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TERMINAL, "16px", evt -> {
                                        
                                    });
                                    edtBtn.setTooltip(new Tooltip("Add Payment"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, edtBtn);

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
    
    private void loadCashAdvanceTable(){
        try{
            t4Tbl.setEditable(true);
            t4Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t4Tbl, "Timestamp", CashAdvance::paymenttimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t4Tbl, "Patient", CashAdvance::patientProperty, false);
            FXTable.addColumn(t4Tbl, "ACK #", CashAdvance::acknowledgementnumProperty, false,80,80,80);
            FXTable.addColumn(t4Tbl, "Cashier", CashAdvance::cashierProperty, false);
            FXTable.addColumn(t4Tbl, "Cancelled", CashAdvance::cancelledProperty, false,100,100,100);
            TableColumn cancelCol = FXTable.addColumn(t4Tbl, "Cancel Time", CashAdvance::canceltimeProperty, false, 125, 125, 125);
            FXTable.addColumn(t4Tbl, "Amount", CashAdvance::amountProperty, false,80,80,80);
            TableColumn actCol = FXTable.addColumn(t4Tbl, "Actions", CashAdvance::patientProperty, false, 76, 76, 76);
            
            FXTable.setTimestampColumn(timeCol);
            FXTable.setTimestampColumn(cancelCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<CashAdvance, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                CashAdvance row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Cash Advance Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        
                                    });
                                    
                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,voidBtn);

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
    
    private void loadChargesList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t2Tbl, new ArrayList());
                            t2Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<HospitalCharge> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(HospitalCharge.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<HospitalCharge> filteredRecords = FXTable.setFilteredList(t2Tbl, charges);
                            HospitalCharge.createTableFilter(t2searchF, filteredRecords);
                            t2resLbl.setText("Results : "+filteredRecords.size());
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPaymentList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t3Tbl, new ArrayList());
                            t3Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<Payment> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(Payment.class, Payment.PAYMENTTIME+">='"+t1+"' AND "+Payment.PAYMENTTIME+"<='"+t2+"' ORDER BY "+Payment.PAYMENTTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(Payment.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<Payment> filteredRecords = FXTable.setFilteredList(t3Tbl, charges);
                            Payment.createTableFilter(t3searchF, filteredRecords);
                            t3resLbl.setText("Results : "+filteredRecords.size());
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadCashAdvanceList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t4Tbl, new ArrayList());
                            t4Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<CashAdvance> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(CashAdvance.class, CashAdvance.PAYMENTTIME+">='"+t1+"' AND "+CashAdvance.PAYMENTTIME+"<='"+t2+"' ORDER BY "+CashAdvance.PAYMENTTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(CashAdvance.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<CashAdvance> filteredRecords = FXTable.setFilteredList(t4Tbl, charges);
                            CashAdvance.createTableFilter(t4searchF, filteredRecords);
                            t4resLbl.setText("Results : "+filteredRecords.size());
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBillingList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t5Tbl, new ArrayList());
                            t5Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<BillStatement> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(BillStatement.class, BillStatement.FINALIZEDTIME+" IS NULL OR ("+BillStatement.FINALIZEDTIME+">='"+t1+"' AND "+BillStatement.FINALIZEDTIME+"<='"+t2+"') ORDER BY "+BillStatement.BILLTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(HospitalCharge.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<BillStatement> filteredRecords = FXTable.setFilteredList(t5Tbl, charges);
                            BillStatement.createTableFilter(t5searchF, filteredRecords);
                            t5resLbl.setText("Results : "+filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t5Tbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public void printInvoice(boolean b,List<HospitalChargeItem> items,String invoice,String customer_name,LocalDate tr_date) {
        try {
            if (items.size() > 0) {
                List<Map<String, ?>> datasource = new ArrayList();

                double total_scpwd = 0;
                double total_net = 0;
                double total_gross = 0;
                double tax = 0;
                double less_vat = 0;
                double emp = 0;
                double ot = 0;
                double vat_sales = 0;
                double nonvat_sales = 0;
                int total_items = 0;
                String sc_id = "";
                String pwd_id = "";
                String ot_desc = "";

                for (int i = 0; i < 14; i++) {
                    Map<String, Object> m = new HashMap();
                    try{
                        HospitalChargeItem item = items.get(i);
                        m.put("qty", item.getQuantity());
                        m.put("unit", "");
                        m.put("particulars", item.getDescription());
                        m.put("unit_price", item.getSelling());
                        m.put("amount", NumberKit.round(item.getTotalselling(), 2));
                        m.put("cent", "");
                        total_gross += item.getTotalselling();
                        total_items += item.getQuantity();    
                        total_net += item.getNetsales();
                        total_scpwd += item.getScdiscount()+item.getPwddiscount();
                        emp += item.getEmpdiscount();
                        ot += item.getOtdiscount();
                        vat_sales += item.getVatsales();
                        nonvat_sales += item.getNonvatsales();
                        tax += item.getInputvat();
                        less_vat += item.getLessvat();
                        sc_id = item.getScid();
                        pwd_id = item.getPwdid();
                        ot_desc = item.getOtdiscountremarks();
                    }catch(Exception er){
                        m.put("qty", "");
                        m.put("unit", "");
                        m.put("particulars", "");
                        m.put("unit_price", "");
                        m.put("amount", "");
                        m.put("cent", "");
                    }                              
                    datasource.add(m);
                }

                JRDataSource fdatasource = new JRBeanCollectionDataSource(datasource);
                
                File template = new File(Care.CONFIG_DIR+File.separator+"invoice.jrxml");
                JasperReport jr = null;
                if(template.exists()){
                    try{
                        jr = JasperCompileManager.compileReport(new FileInputStream(template));
                    }finally{
                        
                    }
                }else{
                    jr = JasperCompileManager.compileReport(Toolkit.getDefaultToolkit().getClass().getResourceAsStream("/jasper/invoice.jrxml"));
                }
                
                Map<String, Object> datas = new HashMap();
                datas.put("customer_name", customer_name);
                datas.put("customer_address", "");//customer_address
                datas.put("business_style", "");//business_style
                datas.put("tr_date", tr_date.format(DateTimeFormatter.ofPattern("MMM dd")));//tr_date
                datas.put("tr_year", tr_date.format(DateTimeFormatter.ofPattern("YY")));//tr_year
                datas.put("customer_tin", "");//customer_tin
                datas.put("vat_sales", NumberKit.toCurrency(vat_sales));//vat_sales
                datas.put("vat_exp", NumberKit.toCurrency(nonvat_sales));//vat_exp
                datas.put("zero_rated", NumberKit.toCurrency(0));//zero_rated
                datas.put("vat", NumberKit.toCurrency(tax));//vat
                datas.put("less_vat", NumberKit.toCurrency(less_vat));//vat
                datas.put("total_sales", NumberKit.toCurrency(total_gross));//total_gross
                datas.put("total_net", NumberKit.toCurrency(total_net));//total_gross
                datas.put("discount", ((total_scpwd > 0)? NumberKit.toCurrency(total_scpwd):"0.00"));//discount
                datas.put("due", "0.00");//due
                datas.put("senior_id", sc_id);//senior_id
                datas.put("pwdosca_id", pwd_id);//pwdosca_id
                datas.put("emp_disc", ((emp > 0)? "EMP Discount : "+NumberKit.toCurrency(emp):""));//emp discount
                datas.put("ot_disc", ((ot > 0)? ot_desc+" : "+NumberKit.toCurrency(ot):""));//ot discount
                
                JasperPrint jp = JasperFillManager.fillReport(jr, datas, fdatasource);
                jp.setName("PTInvoice-"+invoice);
                if (b) {
                    new JasperFrame().showFrame(new JRViewer(jp));                    
                } else {
                    
                    try {
                        JasperPrintManager.printReport(jp, false);
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }                
            }

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void printOfficialReciept(boolean b,List<HospitalCharge> charges,String ornum,String customer_name,String address,String tin,String style,LocalDate date) {
        try {
            if (charges.size() > 0) {
                List<Map<String, ?>> datasource = new ArrayList();
                Map<String, Object> m = new HashMap();
                datasource.add(m);     
                
                double gross = 0;
                double discount = 0;
                double vat_sales = 0;
                double nonvat_sales = 0;
                double vat = 0;
                double net = 0;
                
                List<String> facilities = new ArrayList();
                
                for(HospitalCharge charge:charges){
                    gross += charge.getTotalgross();
                    discount += charge.getScdiscount()+charge.getEmpdiscount()+charge.getOtdiscount();
                    vat_sales += charge.getVatsales();
                    nonvat_sales += charge.getNonvatsales();
                    vat += charge.getInputvat();
                    net += charge.getNetsales();
                    
                    if(!facilities.contains(charge.getChargefacility())){
                        facilities.add(charge.getChargefacility());
                    }
                }
                
                String[] tmpf = new String[facilities.size()];
                tmpf = facilities.toArray(tmpf);
                
                String desc = StringKit.comateArray(tmpf)+" Charges";

                JRDataSource fdatasource = new JRBeanCollectionDataSource(datasource);
                
                File template = new File(Care.CONFIG_DIR+File.separator+"officialreceipt.jrxml");
                JasperReport jr = null;
                if(template.exists()){
                    try{
                        jr = JasperCompileManager.compileReport(new FileInputStream(template));
                    }finally{
                        
                    }
                }else{
                    jr = JasperCompileManager.compileReport(Toolkit.getDefaultToolkit().getClass().getResourceAsStream("/jasper/officialreceipt.jrxml"));
                }
                
                Map<String, Object> datas = new HashMap();
                datas.put("customer", customer_name.toUpperCase());
                datas.put("address", address.toUpperCase());
                datas.put("tin", tin);
                datas.put("style", style.toUpperCase());
                datas.put("description", desc.toUpperCase());
                datas.put("tr_date", date.format(DateTimeFormatter.ofPattern("MMM dd")));
                datas.put("tr_year", date.format(DateTimeFormatter.ofPattern("YY")));
                
                datas.put("total", NumberKit.toCurrency(gross));
                datas.put("discount", NumberKit.toCurrency(discount));
                
                datas.put("vatable", NumberKit.toCurrency(vat_sales));
                datas.put("vatex", NumberKit.toCurrency(nonvat_sales));
                datas.put("zerorated", "0.00");//zero_rated
                datas.put("tax", NumberKit.toCurrency(vat));
                datas.put("payable", NumberKit.toCurrency(net));
                datas.put("sum_words", NumberKit.toWords(net).toUpperCase()+" PESOS ONLY");
                                
                JasperPrint jp = JasperFillManager.fillReport(jr, datas, fdatasource);
                jp.setName("OR-"+ornum);
                if (b) {
                    new JasperFrame().showFrame(new JRViewer(jp));                    
                } else {                    
                    try {
                        JasperPrintManager.printReport(jp, false);
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
                
            }

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadUnpaidHospitalChargesCounter(){
        try{
            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusYears(5), LocalTime.of(0, 0, 0)));
            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
            List<HospitalCharge> charges = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGETYPE+"='Walk-In' AND "+HospitalCharge.PAYMENTTIME+" IS NULL AND "+HospitalCharge.VOIDTIME+" IS NULL ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
            Platform.runLater(()->{
                if(charges.size() > 0){
                    unpaidHCBtn.setVisible(true);
                    unpaidHCBtn.setDisable(false);
                    unpaidHCBtn.setText(String.valueOf(charges.size()));
                }else{
                    unpaidHCBtn.setVisible(false);
                    unpaidHCBtn.setDisable(true);
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadDischargeTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);
                    
                    JFXComboBox<String> typeC = new JFXComboBox();
                    typeC.setPromptText("Charge Type");
                    typeC.setMinHeight(28);
                    typeC.setMinWidth(250);
                    typeC.setMaxWidth(250);
                    typeC.setPrefWidth(250);
                    typeC.getItems().setAll(Arrays.asList(new String[]{"Walk-In","Bill","Internal","Accounts Recievable"}));
                    typeC.getSelectionModel().selectFirst();

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, tfrom, dto, tto,typeC, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        
                        String chtype = typeC.getSelectionModel().getSelectedItem();
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadChargesList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND "+HospitalCharge.CHARGETYPE+"='"+chtype+"' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadChargesList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalCharge> records = t2Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("charges.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(HospitalCharge.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t2Tbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadPaymentTableFilters() {
        try {
            
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> cashiers = SQLTable.distinct(Payment.class, Payment.CASHIER);
                    List<String> patients = SQLTable.distinct(Payment.class, Payment.PATIENT);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField cashierF = new JFXTextField();
                    cashierF.setPromptText("Cashier");
                    cashierF.setMinHeight(28);
                    cashierF.setMinWidth(250);
                    cashierF.setMaxWidth(250);
                    cashierF.setPrefWidth(250);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);
                    
                    TextFields.bindAutoCompletion(cashierF, cashiers);
                    TextFields.bindAutoCompletion(patientF, patients);

                    content.getChildren().addAll(cashierF,patientF,dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadPaymentList(Payment.PAYMENTTIME + ">='" + sqT1 + "' AND " + Payment.PAYMENTTIME + "<='" + sqT2 + "' AND "+Payment.CASHIER+" LIKE '%"+cashierF.getText()+"%' AND "+Payment.PATIENT+" LIKE '%"+patientF.getText()+"%' ORDER BY " + Payment.PAYMENTTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadPaymentList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Payment> records = t3Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("payments.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(Payment.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t3Tbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadCashAdvanceTableFilters() {
        try {
            
            GlyphIcon addIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.PLUS).size("13px").build();
            Label addLb = new Label("Add Cash Advance");
            addLb.setCursor(Cursor.HAND);
            addLb.setGraphic(addIcon);
            addLb.setOnMouseClicked(evt -> {
                try{
                    List<String> patients = new ArrayList();
                    List<Patient> patrecords = SQLTable.list(Patient.class);
                    patrecords.stream().forEach(patient->{
                        patients.add(patient.getFullname());
                    });
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);  
                    patientF.setLabelFloat(true);
                    
                    JFXTextField acknowledgementF = new JFXTextField();
                    acknowledgementF.setPromptText("Acknowledgement #");
                    acknowledgementF.setMinHeight(28);
                    acknowledgementF.setMinWidth(250);
                    acknowledgementF.setMaxWidth(250);
                    acknowledgementF.setPrefWidth(250);  
                    acknowledgementF.setLabelFloat(true);                                    
                    
                    JFXTextField amountF = new JFXTextField();
                    amountF.setPromptText("Amount");
                    amountF.setMinHeight(28);
                    amountF.setMinWidth(250);
                    amountF.setMaxWidth(250);
                    amountF.setPrefWidth(250);
                    amountF.setLabelFloat(true);
                    
                    DoubleProperty amtProp = new SimpleDoubleProperty(0);
                    amountF.textProperty().bindBidirectional(amtProp, new NumberConverter());
                    
                    TextFields.bindAutoCompletion(patientF, patients);
                    
                    FXField.addRequiredValidator(patientF);
                    FXField.addRequiredValidator(acknowledgementF);
                    FXField.addRequiredValidator(amountF);
                    FXField.addDoubleValidator(amountF, 0, 999999.0, 0);
                    
                    FXField.addFocusValidationListener(patientF,acknowledgementF,amountF);

                    content.getChildren().addAll(patientF, acknowledgementF, amountF);

                    JFXButton filter = new JFXButton("Save");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Add Cash Advance", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        if(patientF.validate() && acknowledgementF.validate() && amountF.validate()){
                            String pname = patientF.getText();
                            Patient sel_patient = null;
                            for(Patient p : patrecords){
                                if(p.getFullname().equals(pname)){
                                    sel_patient = p.getModelClone();
                                    break;
                                }
                            }
                            if(sel_patient != null){
                                final int PAT_ID = sel_patient.getId();
                                FXTask task = new FXTask() {
                                    @Override
                                    protected void load() {                           
                                        try{
                                            CashAdvance ca = new CashAdvance();
                                            ca.setPatient(pname);
                                            ca.setAcknowledgementnum(acknowledgementF.getText());
                                            ca.setAmount(amtProp.get());
                                            ca.setCashier(Care.getUser().getName());
                                            ca.setPatient_id(PAT_ID);
                                            
                                            if(ca.save() > 0){                                                
                                                Platform.runLater(()->{
                                                    dialog.close();
                                                    loadCashAdvanceList(null);
                                                    FXDialog.showMessageDialog(mainStack, "Successful", "Cash Advance Saved", FXDialog.SUCCESS);
                                                });                                                
                                            }else{
                                                Platform.runLater(()->{
                                                    FXDialog.showMessageDialog(mainStack, "Server Communitation Error", "Failed to save record!", FXDialog.DANGER);
                                                });
                                            }
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                            Platform.runLater(()->{
                                                FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                            });
                                        }
                                    }
                                };
                                Care.process(task);
                            }else{
                                FXDialog.showMessageDialog(mainStack, "Invalid Patient", "Patient record not found, Please select on suggested list!", FXDialog.DANGER);
                            }
                        }
                    });
                }catch(Exception er){
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> cashiers = SQLTable.distinct(CashAdvance.class, Payment.CASHIER);
                    List<String> patients = SQLTable.distinct(CashAdvance.class, Payment.PATIENT);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField cashierF = new JFXTextField();
                    cashierF.setPromptText("Cashier");
                    cashierF.setMinHeight(28);
                    cashierF.setMinWidth(250);
                    cashierF.setMaxWidth(250);
                    cashierF.setPrefWidth(250);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);
                    
                    TextFields.bindAutoCompletion(cashierF, cashiers);
                    TextFields.bindAutoCompletion(patientF, patients);

                    content.getChildren().addAll(cashierF,patientF,dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadCashAdvanceList(CashAdvance.PAYMENTTIME + ">='" + sqT1 + "' AND " + CashAdvance.PAYMENTTIME + "<='" + sqT2 + "' AND "+CashAdvance.CASHIER+" LIKE '%"+cashierF.getText()+"%' AND "+CashAdvance.PATIENT+" LIKE '%"+patientF.getText()+"%' ORDER BY " + CashAdvance.PAYMENTTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadCashAdvanceList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<CashAdvance> records = t4Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("cashadvances.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(CashAdvance.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t4Tbl, addLb, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadBillStatementTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXTimePicker tfrom = new JFXTimePicker();
                    tfrom.setPromptText("Time From");
                    tfrom.setMinHeight(28);
                    tfrom.setMinWidth(250);
                    tfrom.setMaxWidth(250);
                    tfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    JFXTimePicker tto = new JFXTimePicker();
                    tto.setPromptText("Time To");
                    tto.setMinHeight(28);
                    tto.setMinWidth(250);
                    tto.setMaxWidth(250);
                    tto.setPrefWidth(250);

                    Label warning = new Label("Invalid Time Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, tfrom, dto, tto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Time", content, FXDialog.DANGER, filter);
                    filter.setOnAction(deleteEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();
                        LocalTime t1 = tfrom.getValue();
                        LocalTime t2 = tto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;
                        t1 = (t1 == null) ? LocalTime.of(0, 0, 0) : t1;
                        t2 = (t2 == null) ? LocalTime.of(23, 59, 59) : t2;

                        LocalDateTime ts1 = LocalDateTime.of(d1, t1);
                        LocalDateTime ts2 = LocalDateTime.of(d2, t2);
                        if (ts1.isBefore(ts2)) {
                            dialog.close();
                            java.sql.Timestamp sqT1 = java.sql.Timestamp.valueOf(ts1);
                            java.sql.Timestamp sqT2 = java.sql.Timestamp.valueOf(ts2);
                            loadBillingList(BillStatement.FINALIZEDTIME+" IS NULL OR ("+BillStatement.FINALIZEDTIME + ">='" + sqT1 + "' AND " + BillStatement.FINALIZEDTIME + "<='" + sqT2 + "') ORDER BY " + BillStatement.FINALIZEDTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Todays Records");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadBillingList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<BillStatement> records = t5Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("billstatements.xlsx");
                    fileChooser.getExtensionFilters().setAll(
                         FileKit.XLSX
                    );

                    File sFile = fileChooser.showSaveDialog(Care.PRIMARY_STAGE);
                    if(sFile != null){
                        FXTask task = new FXTask() {
                            @Override
                            protected void load() {                           
                                try{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(true);
                                    });
                                    ExcelManager.export(BillStatement.class, records, sFile);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Successful", "File was exported on "+sFile.getAbsolutePath(), FXDialog.SUCCESS);
                                    });
                                }catch(Exception er){
                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                    Platform.runLater(()->{
                                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                                    });
                                }finally{
                                    Platform.runLater(()->{
                                        maskerPane.setVisible(false);
                                    });
                                }
                            }
                        };
                        Care.process(task);
                    }
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(mainStack, "Export Error", "Failed to export file!", FXDialog.DANGER);
                    });
                }
            });
            
            
            FXTable.addCustomTableMenu(t5Tbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
}