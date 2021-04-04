package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.ECart;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.Item;
import models.Patient;
import models.ReturnedItem;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.models.Facility;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import nse.dcfx.utils.NumberKit;
import nse.dcfx.utils.StringKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;
import utils.PrintManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class PharmacyUXController implements Initializable, UIController {

    private StackPane mainStack;
    private MaskerPane maskerPane;
    private Patient NEW_SELECTED_PATIENT = null;
    private boolean SUCCESS_ACTION = false;

    private boolean checkingOut = false;
    private boolean chargingPatient = false;
    private boolean chargingECart = false;
    private boolean chargingInternal = false;
    private boolean returningCharge = false;

    private Map<String, HospitalChargeItem> PURCHASE_MAP = new LinkedHashMap();    
    private Map<String, HospitalChargeItem> HOLD_MAP = new LinkedHashMap();

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab terminalTab;

    @FXML
    private TextField t1odeF;

    @FXML
    private JFXButton s1codeBtn;

    @FXML
    private TextField t1keyF;

    @FXML
    private JFXButton s1keyBtn;

    @FXML
    private TableView<Item> t1sTbl;

    @FXML
    private TableView<HospitalChargeItem> t1pTbl;

    @FXML
    private Label t1subtotalLbl;

    @FXML
    private Label t1discamtLbl;

    @FXML
    private JFXButton t1clearBtn;

    @FXML
    private JFXButton t1ecartBtn;

    @FXML
    private JFXButton t1consumeBtn;

    @FXML
    private JFXButton t1chargeBtn;

    @FXML
    private JFXButton t1CheckoutBtn;

    @FXML
    private JFXButton t1returnBtn;
    
    @FXML
    private JFXButton t1holdBtn;

    @FXML
    private Tab transactionsTab;

    @FXML
    private TableView<HospitalCharge> t2Tbl;

    @FXML
    private Label t2resLbl;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private Tab soldsTab;

    @FXML
    private TableView<HospitalChargeItem> t3Tbl;

    @FXML
    private Label t3resLbl;

    @FXML
    private JFXTextField t3searchF;

    @FXML
    private JFXTextField t4searchF;

    @FXML
    private Tab returnsTab;

    @FXML
    private TableView<ReturnedItem> t4Tbl;

    @FXML
    private Label t4resLlb;

    @FXML
    private Tab ecartsTab;

    @FXML
    private TableView<ECart> t5Tbl;

    @FXML
    private Label t5resLbl;

    @FXML
    private JFXTextField t5searchF;
    
    @FXML
    private Tab ordersTab;
    
    @FXML
    private TableView<?> t6Tbl;

    @FXML
    private Label t6resLbl;

    @FXML
    private JFXTextField t6searchF;

    @FXML
    private Label userLbl;

    @FXML
    private JFXButton newreturnitemBtn;    
    
    @FXML
    private FontAwesomeIconView holdIcon;
    
    @FXML
    void createReturns(ActionEvent event) {
        ReturnsFormController.showDialog(mainStack, maskerPane, this);
    }

    @FXML
    void holdItems(ActionEvent event) {
        try{
            if(HOLD_MAP.isEmpty()){
                if(!PURCHASE_MAP.isEmpty()){
                    //HOLD
                    JFXButton addScanned = new JFXButton("Confirm");
                    addScanned.getStyleClass().add("btn-success");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Hold Transaction", new Label("Confim Hold Purchase Items"), FXDialog.PRIMARY, addScanned);
                    addScanned.setOnAction(confirmEvt->{
                        HOLD_MAP.putAll(PURCHASE_MAP);
                        clearPurchaseList();
                        dialog.close();                    
                        t1holdBtn.setText(" RESUME           F5");
                        holdIcon.setIcon(FontAwesomeIcon.PLAY);
                        t1holdBtn.getStyleClass().remove("btn-default");
                        t1holdBtn.getStyleClass().add("btn-control-light");
                    });
                }                
            }else{
                //RESUME
                JFXButton addScanned = new JFXButton("Confirm");
                addScanned.getStyleClass().add("btn-success");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Resume Held Transacction", new Label("Confim resume previous held transaction?"), FXDialog.PRIMARY, addScanned);
                addScanned.setOnAction(confirmEvt->{
                    PURCHASE_MAP.clear();                    
                    PURCHASE_MAP.putAll(HOLD_MAP);
                    setPurchaseList(PURCHASE_MAP);
                    HOLD_MAP.clear();
                    dialog.close();
                    t1holdBtn.setText(" HOLD                F5");
                    holdIcon.setIcon(FontAwesomeIcon.PAUSE);
                    t1holdBtn.getStyleClass().remove("btn-control-light");
                    t1holdBtn.getStyleClass().add("btn-default");
                });
            }
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void returnItems(ActionEvent event) {
        try {
            if (!PURCHASE_MAP.isEmpty()) {
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35, 25, 25, 25));

                Map<String, String> opts = GlobalOption.getMap("General");
                List<Patient> patients = SQLTable.list(Patient.class);
                List<String> facility = SQLTable.distinct(Facility.class, Facility.NAME);
                List<String> users = SQLTable.distinct(User.class, User.NAME);

                List<String> chargesuggestions = new ArrayList();
                patients.stream().forEach(rec -> {
                    chargesuggestions.add(rec.getFullname());
                });

                facility.stream().forEach(rec -> {
                    chargesuggestions.add(rec);
                });

                users.stream().forEach(rec -> {
                    chargesuggestions.add(rec);
                });

                JFXTextField chargenumberF = new JFXTextField();
                chargenumberF.setPromptText("Charge #");
                chargenumberF.setMinHeight(28);
                chargenumberF.setMinWidth(250);
                chargenumberF.setMaxWidth(250);
                chargenumberF.setPrefWidth(250);
                chargenumberF.setLabelFloat(true);

                JFXTextField chargetoF = new JFXTextField();
                chargetoF.setPromptText("Charged TO");
                chargetoF.setMinHeight(28);
                chargetoF.setMinWidth(250);
                chargetoF.setMaxWidth(250);
                chargetoF.setPrefWidth(250);
                chargetoF.setLabelFloat(true);

                JFXTextField invF = new JFXTextField();
                invF.setPromptText("Invoice #");
                invF.setMinHeight(28);
                invF.setMinWidth(250);
                invF.setMaxWidth(250);
                invF.setPrefWidth(250);
                invF.setLabelFloat(true);

                JFXTextField orF = new JFXTextField();
                orF.setPromptText("OR #");
                orF.setMinHeight(28);
                orF.setMinWidth(250);
                orF.setMaxWidth(250);
                orF.setPrefWidth(250);
                orF.setLabelFloat(true);

                JFXTextField retbyF = new JFXTextField();
                retbyF.setPromptText("Returned By");
                retbyF.setMinHeight(28);
                retbyF.setMinWidth(250);
                retbyF.setMaxWidth(250);
                retbyF.setPrefWidth(250);
                retbyF.setLabelFloat(true);

                JFXTextField recbyF = new JFXTextField();
                recbyF.setPromptText("Recieved By");
                recbyF.setMinHeight(28);
                recbyF.setMinWidth(250);
                recbyF.setMaxWidth(250);
                recbyF.setPrefWidth(250);
                recbyF.setLabelFloat(true);

                JFXTextField remarkF = new JFXTextField();
                remarkF.setPromptText("Remarks");
                remarkF.setMinHeight(28);
                remarkF.setMinWidth(250);
                remarkF.setMaxWidth(250);
                remarkF.setPrefWidth(250);
                remarkF.setLabelFloat(true);

                TextFields.bindAutoCompletion(chargetoF, chargesuggestions);
                TextFields.bindAutoCompletion(retbyF, users);
                TextFields.bindAutoCompletion(recbyF, users);

                FXField.addRequiredValidator(retbyF);
                FXField.addRequiredValidator(recbyF);
                FXField.addRequiredValidator(remarkF);
                FXField.addFocusValidationListener(retbyF, recbyF, remarkF);

                content.getChildren().addAll(chargetoF, chargenumberF, new Separator(), invF, orF, new Separator(), retbyF, recbyF, remarkF);

                JFXButton addScanned = new JFXButton("Confirm");
                addScanned.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Return Items", content, FXDialog.DANGER, addScanned);
                chargetoF.requestFocus();
                dialog.setOnDialogClosed(evt -> {
                    setReturningCharge(false);
                });
                setReturningCharge(true);

                addScanned.setOnAction(pruchaseEvt -> {
                    if (retbyF.validate() && recbyF.validate() && remarkF.validate()) {
                        List<HospitalChargeItem> items = new ArrayList(PURCHASE_MAP.values());
                        List<ReturnedItem> ritems = new ArrayList();
                        items.stream().forEach(item -> {
                            ReturnedItem ritem = item.asReturnedItem();
                            ritem.setChargenumber(chargenumberF.getText());
                            ritem.setChargeto(chargetoF.getText());
                            ritem.setInvoice(invF.getText());
                            ritem.setOrnumber(orF.getText());
                            ritem.setRecievedby(recbyF.getText());
                            ritem.setReturnedby(retbyF.getText());
                            ritem.setRemarks(remarkF.getText());
                            ritem.setFacility("Pharmacy");
                            ritem.setUser(Care.getUser().getName());
                            ritem.setUser_id(Care.getUser().getId());
                            ritems.add(ritem);
                        });

                        Care.process(() -> {
                            try {
                                ritems.stream().forEach(item -> {
                                    item.save();
                                    item.addItemQuantity();
                                });

                                Platform.runLater(() -> {
                                    clearPurchaseList();
                                    dialog.close();
                                    FXDialog.showMessageDialog(mainStack, "Success", "Return Saved!", FXDialog.SUCCESS);
                                    //Care.createNotification("Charge Info", "Your Charge to patient info has been saved!", 3000, true);
                                    System.out.println("Transaction Saved");
                                });
                            } catch (Exception er) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                dialog.close();
                            }
                        });

                    }
                });
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void chargeToPatient(ActionEvent event) {
        try {
            if (!PURCHASE_MAP.isEmpty()) {
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35, 25, 25, 25));

                Map<String, String> opts = GlobalOption.getMap("General");
                List<Patient> patients = SQLTable.list(Patient.class);

                JFXTextField customerF = new JFXTextField();
                customerF.setPromptText("Patient");
                customerF.setMinHeight(28);
                customerF.setMinWidth(250);
                customerF.setMaxWidth(250);
                customerF.setPrefWidth(250);
                customerF.setLabelFloat(true);

                NEW_SELECTED_PATIENT = null;

                JFXAutoCompletePopup<Patient> autoCompletePopup = new JFXAutoCompletePopup<>();
                autoCompletePopup.getSuggestions().addAll(patients);
                autoCompletePopup.setSelectionHandler(evt -> {
                    try {
                        NEW_SELECTED_PATIENT = evt.getObject();
                        if (NEW_SELECTED_PATIENT != null) {
                            customerF.setText(evt.getObject().toString());
                        } else {
                            customerF.setText("");
                        }
                    } catch (Exception er) {
                        NEW_SELECTED_PATIENT = null;
                        customerF.setText("");
                    }
                });

                FXField.addAutocompleteListeners(customerF, autoCompletePopup);

                content.getChildren().addAll(customerF);

                JFXButton addScanned = new JFXButton("Confirm");
                addScanned.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Charge to Patient", content, FXDialog.PRIMARY, addScanned);
                customerF.requestFocus();
                dialog.setOnDialogClosed(evt -> {
                    setChargingPatient(false);
                });
                setChargingPatient(true);

                Label warn = new Label();
                warn.setText("");
                warn.setStyle("-fx-text-fill:danger-color;");

                addScanned.setOnAction(pruchaseEvt -> {
                    boolean inlist = false;
                    String pname = customerF.getText();
                    for (int i = 0; i < patients.size(); i++) {
                        if (pname.equalsIgnoreCase(patients.get(i).getFullname())) {
                            inlist = true;
                            break;
                        }
                    }

                    if (NEW_SELECTED_PATIENT != null && !pname.isEmpty() && inlist) {
                        List<HospitalChargeItem> items = new ArrayList(PURCHASE_MAP.values());
                        HospitalCharge charge = new HospitalCharge();
                        charge.setChargenumber(StringKit.timeCode(LocalDateTime.now()));
                        charge.setChargetime(LocalDateTime.now());
                        charge.setChargefacility("Pharmacy");
                        charge.setChargetype("Bill");
                        charge.setChargeto(NEW_SELECTED_PATIENT.getFullname());

                        charge.setPatient_id(NEW_SELECTED_PATIENT.getId());

                        charge.setUser(Care.getUser().getName());
                        charge.setUser_id(Care.getUser().getId());

                        charge.setItems(items);
                        charge.calculateTotal(items);
                        charge.getItems().stream().forEach(item -> {
                            item.setChargenumber(charge.getChargenumber());
                            item.setChargetime(charge.getChargetime());
                            item.setChargeto(NEW_SELECTED_PATIENT.getFullname());
                            item.setFacility("Pharmacy");
                            item.setUser_id(Care.getUser().getId());
                            item.setUser(Care.getUser().getName());
                            item.setPatient_id(NEW_SELECTED_PATIENT.getId());
                        });

                        Care.process(() -> {
                            try {
                                if (charge.save(items) > 0) {
                                    charge.getItems().stream().forEach(item -> {
                                        item.deductItemQuantity();
                                    });
                                    Platform.runLater(() -> {
                                        clearPurchaseList();
                                        dialog.close();
                                        FXDialog.showMessageDialog(mainStack, "Success", "Transaction Saved!", FXDialog.SUCCESS);
                                        //Care.createNotification("Charge Info", "Your Charge to patient info has been saved!", 3000, true);
                                        System.out.println("Transaction Saved");
                                    });
                                    //charge.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Pharmacy", false);
                                } else {
                                    Platform.runLater(() -> {
                                        dialog.close();
                                        System.out.println("Transaction Exited");
                                    });
                                }
                            } catch (Exception er) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        });
                    } else {
                        warn.setText("Please select patient.");
                    }
                });
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void checkOut(ActionEvent event) {
        try {
            if (!PURCHASE_MAP.isEmpty()) {
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35, 25, 25, 25));

                Map<String, String> opts = GlobalOption.getMap("General");
                List<Patient> patients = SQLTable.list(Patient.class);
                List<String> users = SQLTable.distinct(User.class, User.NAME);
                List<String> careof = SQLTable.distinct(HospitalCharge.class, HospitalCharge.CARETO);

                List<String> chargesuggestions = new ArrayList();
                patients.stream().forEach(rec -> {
                    chargesuggestions.add(rec.getFullname());
                });

                users.stream().forEach(rec -> {
                    chargesuggestions.add(rec);
                });

                JFXTextField customerF = new JFXTextField();
                customerF.setPromptText("Customer");
                customerF.setMinHeight(28);
                customerF.setMinWidth(250);
                customerF.setMaxWidth(250);
                customerF.setPrefWidth(250);
                customerF.setLabelFloat(true);

                JFXTextField careF = new JFXTextField();
                careF.setPromptText("Care of");
                careF.setMinHeight(28);
                careF.setMinWidth(250);
                careF.setMaxWidth(250);
                careF.setPrefWidth(250);
                careF.setLabelFloat(true);

                TextFields.bindAutoCompletion(customerF, chargesuggestions);
                TextFields.bindAutoCompletion(careF, careof);

                FXField.addRequiredValidator(customerF);
                FXField.addFocusValidationListener(customerF);

                content.getChildren().addAll(customerF, careF);

                JFXButton addScanned = new JFXButton("Checkout");
                addScanned.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Check-Out", content, FXDialog.DANGER, addScanned);
                dialog.setOnDialogClosed(evt -> {
                    setCheckingOut(false);
                });
                customerF.requestFocus();
                setCheckingOut(true);

                addScanned.setOnAction(pruchaseEvt -> {
                    if (customerF.validate()) {

                        List<HospitalChargeItem> items = new ArrayList(PURCHASE_MAP.values());
                        HospitalCharge charge = new HospitalCharge();
                        charge.setChargenumber(StringKit.timeCode(LocalDateTime.now()));
                        charge.setChargetime(LocalDateTime.now());
                        charge.setChargefacility("Pharmacy");
                        charge.setChargetype("Walk-In");
                        charge.setChargeto(customerF.getText());
                        charge.setCareto(careF.getText());

                        patients.stream().forEach(pat -> {
                            if (pat.getFullname().equalsIgnoreCase(customerF.getText())) {
                                charge.setPatient_id(pat.getId());
                            }
                        });

                        charge.setUser(Care.getUser().getName());
                        charge.setUser_id(Care.getUser().getId());

                        charge.setItems(items);
                        charge.calculateTotal(items);
                        charge.getItems().stream().forEach(item -> {
                            item.setChargenumber(charge.getChargenumber());
                            item.setChargetime(charge.getChargetime());
                            item.setChargeto(customerF.getText());
                            item.setFacility("Pharmacy");
                            item.setUser_id(Care.getUser().getId());
                            item.setUser(Care.getUser().getName());
                            item.setPatient_id(charge.getPatient_id());
                        });
                        Care.process(() -> {
                            try {
                                Platform.runLater(() -> {
                                    ((JFXButton) event.getSource()).setDisable(true);
                                });

                                if (charge.save(items) > 0) {
                                    charge.getItems().stream().forEach(item -> {
                                        item.deductItemQuantity();
                                    });
                                    Platform.runLater(() -> {
                                        dialog.close();
                                        clearPurchaseList();
                                        FXDialog.showMessageDialog(mainStack, "Success", "Transaction Saved!", FXDialog.SUCCESS);
                                        //Care.createNotification("Check-Out Info", "Your check-out transaction has been saved!", 3000, true);
                                        System.out.println("Transaction Saved");
                                    });

                                    charge.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Pharmacy", false);
                                } else {
                                    Platform.runLater(() -> {
                                        dialog.close();
                                        System.out.println("Transaction Exited");
                                    });
                                }
                            } catch (Exception er) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            } finally {
                                Platform.runLater(() -> {
                                    ((JFXButton) event.getSource()).setDisable(false);
                                });
                            }
                        });
                    }
                });
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void clearPurchaseList(ActionEvent event) {
        clearPurchaseList();
    }

    @FXML
    void ecartCheckout(ActionEvent event) {
        try {
            if (!PURCHASE_MAP.isEmpty()) {
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35, 25, 25, 25));

                List<String> depts = SQLTable.distinct(Facility.class, Facility.NAME);
                List<String> users = SQLTable.distinct(User.class, User.NAME);
                Map<String, String> opts = GlobalOption.getMap("General");

                JFXTextField departmentF = new JFXTextField();
                departmentF.setPromptText("Department");
                departmentF.setMinHeight(28);
                departmentF.setMinWidth(250);
                departmentF.setMaxWidth(250);
                departmentF.setPrefWidth(250);
                departmentF.setLabelFloat(true);

                JFXTextField requestorF = new JFXTextField();
                requestorF.setPromptText("Requested By");
                requestorF.setMinHeight(28);
                requestorF.setMinWidth(250);
                requestorF.setMaxWidth(250);
                requestorF.setPrefWidth(250);
                requestorF.setLabelFloat(true);

                JFXTextField approvedF = new JFXTextField();
                approvedF.setPromptText("Approved By");
                approvedF.setMinHeight(28);
                approvedF.setMinWidth(250);
                approvedF.setMaxWidth(250);
                approvedF.setPrefWidth(250);
                approvedF.setLabelFloat(true);

                TextFields.bindAutoCompletion(departmentF, depts);
                TextFields.bindAutoCompletion(requestorF, users);
                TextFields.bindAutoCompletion(approvedF, users);

                FXField.addRequiredValidator(departmentF);
                FXField.addRequiredValidator(requestorF);
                FXField.addRequiredValidator(approvedF);
                FXField.addFocusValidationListener(departmentF, requestorF, approvedF);

                content.getChildren().addAll(departmentF, requestorF, approvedF);

                JFXButton addScanned = new JFXButton("Confirm");
                addScanned.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Transfer to ECart ", content, FXDialog.NORMAL, addScanned);
                departmentF.requestFocus();
                dialog.setOnDialogClosed(evt -> {
                    setChargingECart(false);
                });
                setChargingECart(true);

                addScanned.setOnAction(pruchaseEvt -> {
                    if (departmentF.validate() && requestorF.validate() && approvedF.validate()) {
                        List<HospitalChargeItem> items = new ArrayList(PURCHASE_MAP.values());
                        List<ECart> ecarts = new ArrayList();

                        String timecode = StringKit.timeCode(LocalDateTime.now());
                        LocalDateTime time = LocalDateTime.now();

                        items.stream().forEach(item -> {
                            ECart i = item.asECartItem();
                            i.setEcartnumber(timecode);
                            i.setEcarttime(time);
                            i.setFromfacility("Pharmacy");
                            i.setTofacility(departmentF.getText());
                            i.setRequestedby(requestorF.getText());
                            i.setApprovedby(approvedF.getText());
                            i.setUser_id(Care.getUser().getId());
                            i.setUser(Care.getUser().getName());
                            ecarts.add(i);
                        });

                        Care.process(() -> {
                            try {

                                ecarts.stream().forEach(item -> {
                                    item.save();
                                });

                                Platform.runLater(() -> {
                                    clearPurchaseList();
                                    dialog.close();
                                    FXDialog.showMessageDialog(mainStack, "Success", "Transaction Saved!", FXDialog.SUCCESS);
                                    //Care.createNotification("ECart Transfer Info", "Your transfer information has been saved!", 3000, true);

                                    System.out.println("Transaction Saved");
                                });

                                PrintManager.printECartTransfer(maskerPane, opts.get("FACILITYNAME"), "Pharmacy", ecarts, false);
                            } catch (Exception er) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        });
                    }
                });
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void internalCheckout(ActionEvent event) {
        try {
            if (!PURCHASE_MAP.isEmpty()) {
                VBox content = new VBox();
                content.setMaxWidth(500);
                content.setMaxHeight(500);
                content.setAlignment(Pos.CENTER);
                content.setSpacing(35);
                content.setPadding(new Insets(35, 25, 25, 25));

                List<String> depts = SQLTable.distinct(Facility.class, Facility.NAME);
                List<String> users = SQLTable.distinct(User.class, User.NAME);
                Map<String, String> opts = GlobalOption.getMap("General");

                JFXTextField departmentF = new JFXTextField();
                departmentF.setPromptText("Department");
                departmentF.setMinHeight(28);
                departmentF.setMinWidth(250);
                departmentF.setMaxWidth(250);
                departmentF.setPrefWidth(250);
                departmentF.setLabelFloat(true);

                JFXTextField requestorF = new JFXTextField();
                requestorF.setPromptText("Requested By");
                requestorF.setMinHeight(28);
                requestorF.setMinWidth(250);
                requestorF.setMaxWidth(250);
                requestorF.setPrefWidth(250);
                requestorF.setLabelFloat(true);

                JFXTextField approvedF = new JFXTextField();
                approvedF.setPromptText("Approved By");
                approvedF.setMinHeight(28);
                approvedF.setMinWidth(250);
                approvedF.setMaxWidth(250);
                approvedF.setPrefWidth(250);
                approvedF.setLabelFloat(true);

                TextFields.bindAutoCompletion(departmentF, depts);
                TextFields.bindAutoCompletion(requestorF, users);
                TextFields.bindAutoCompletion(approvedF, users);

                FXField.addRequiredValidator(departmentF);
                FXField.addRequiredValidator(requestorF);
                FXField.addRequiredValidator(approvedF);
                FXField.addFocusValidationListener(departmentF, requestorF, approvedF);

                content.getChildren().addAll(departmentF, requestorF, approvedF);

                JFXButton addScanned = new JFXButton("Consume");
                addScanned.getStyleClass().add("btn-danger");

                JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Internal Consumption", content, FXDialog.DANGER, addScanned);
                departmentF.requestFocus();
                dialog.setOnDialogClosed(evt -> {
                    setChargingInternal(false);
                });
                setChargingInternal(true);
                
                addScanned.setOnAction(pruchaseEvt -> {
                    if (departmentF.validate() && requestorF.validate() && approvedF.validate()) {
                        List<HospitalChargeItem> items = new ArrayList(PURCHASE_MAP.values());
                        HospitalCharge charge = new HospitalCharge();
                        charge.setChargenumber(StringKit.timeCode(LocalDateTime.now()));
                        charge.setChargetime(LocalDateTime.now());
                        charge.setChargefacility("Pharmacy");
                        charge.setChargetype("Internal");
                        charge.setChargeto(departmentF.getText());
                        charge.setChargenotes(requestorF.getText());
                        charge.setRemarks(approvedF.getText());

                        charge.setUser(Care.getUser().getName());
                        charge.setUser_id(Care.getUser().getId());

                        charge.setItems(items);
                        charge.calculateTotal(items);
                        charge.getItems().stream().forEach(item -> {
                            item.setChargenumber(charge.getChargenumber());
                            item.setChargetime(charge.getChargetime());
                            item.setChargeto(departmentF.getText());
                            item.setFacility("Pharmacy");
                            item.setUser_id(Care.getUser().getId());
                            item.setUser(Care.getUser().getName());
                        });

                        Care.process(() -> {
                            try {
                                if (charge.save(items) > 0) {
                                    charge.getItems().stream().forEach(item -> {
                                        item.deductItemQuantity();
                                    });
                                    Platform.runLater(() -> {
                                        clearPurchaseList();
                                        dialog.close();
                                        FXDialog.showMessageDialog(mainStack, "Success", "Transaction Saved!", FXDialog.SUCCESS);
                                        //Care.createNotification("Internal Consumption Info", "Your internal consumption information has been saved!", 3000, true);
                                        System.out.println("Transaction Saved");
                                    });
                                    charge.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Pharmacy", false);
                                } else {
                                    Platform.runLater(() -> {
                                        dialog.close();
                                        System.out.println("Transaction Exited");
                                    });
                                }
                            } catch (Exception er) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                            }
                        });
                    }
                });
            }
        } catch (Exception er) {
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
        try {
            switch (val) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    loadReturnedItemList(null);
                    break;
                default:
                    break;
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
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
        try {
            loadSearchTable();
            loadKeyboardShortcuts();

            //Search Code
            t1odeF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!t1odeF.getText().isEmpty()) {
                        Platform.runLater(() -> {
                            String searccode = t1odeF.getText();
                            Care.process(() -> {
                                List<Item> res = SQLTable.list(Item.class, Item.CODE + " ='" + searccode + "' AND " + Item.AVAILABLE + "=1 ORDER BY " + Item.DESCRIPTION + " ASC");
                                Platform.runLater(() -> {
                                    FXTable.setList(t1sTbl, res);
                                });
                            });
                        });
                    }
                }else if (evt.getCode() == KeyCode.DOWN) {
                    if (!t1sTbl.getItems().isEmpty()) {
                        t1sTbl.requestFocus();
                        t1sTbl.getSelectionModel().selectFirst();
                    }
                }else if(evt.getCode() == KeyCode.F2){
                    t1keyF.requestFocus();
                    t1keyF.selectAll();
                }else if(evt.getCode() == KeyCode.F5){
                    if (!t1sTbl.getItems().isEmpty()) {
                        t1holdBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F6){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1returnBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F7){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1ecartBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F8){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1consumeBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F9){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1chargeBtn.fire();
                    }
                }
            });
            
            

            t1keyF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!t1keyF.getText().isEmpty()) {
                        Platform.runLater(() -> {
                            String searccode = t1keyF.getText();
                            Care.process(() -> {
                                List<Item> res = SQLTable.list(Item.class, "(" + Item.DESCRIPTION + " LIKE '%" + searccode + "%' OR " + Item.GENERICNAME + " LIKE '%" + searccode + "%') AND " + Item.AVAILABLE + "=1 ORDER BY " + Item.DESCRIPTION + " ASC");
                                Platform.runLater(() -> {
                                    FXTable.setList(t1sTbl, res);
                                });
                            });
                        });
                    }
                } else if (evt.getCode() == KeyCode.DOWN) {
                    if (!t1sTbl.getItems().isEmpty()) {
                        t1sTbl.requestFocus();
                        t1sTbl.getSelectionModel().selectFirst();
                    }
                }else if(evt.getCode() == KeyCode.F1){
                    t1odeF.requestFocus();
                    t1odeF.selectAll();
                }else if(evt.getCode() == KeyCode.F5){
                    if (!t1sTbl.getItems().isEmpty()) {
                        t1holdBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F6){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1returnBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F7){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1ecartBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F8){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1consumeBtn.fire();
                    }
                }else if(evt.getCode() == KeyCode.F9){
                    if (!t1pTbl.getItems().isEmpty()) {
                        t1chargeBtn.fire();
                    }
                }
                
                
            });

            s1codeBtn.setOnAction(evt -> {
                if (!t1odeF.getText().isEmpty()) {
                    Platform.runLater(() -> {
                        String searccode = t1odeF.getText();
                        Care.process(() -> {
                            List<Item> res = SQLTable.list(Item.class, Item.CODE + " ='" + searccode + "' AND " + Item.AVAILABLE + "=1 ORDER BY " + Item.DESCRIPTION + " ASC");
                            FXTable.setList(t1sTbl, res);
                        });
                    });
                }
            });

            s1keyBtn.setOnAction(evt -> {
                if (!t1keyF.getText().isEmpty()) {
                    Platform.runLater(() -> {
                        String searccode = t1keyF.getText();
                        Care.process(() -> {
                            List<Item> res = SQLTable.list(Item.class, "(" + Item.DESCRIPTION + " LIKE '%" + searccode + "%' OR " + Item.GENERICNAME + " LIKE '%" + searccode + "%') AND " + Item.AVAILABLE + "=1 ORDER BY " + Item.DESCRIPTION + " ASC");
                            FXTable.setList(t1sTbl, res);
                        });
                    });
                }
            });

            loadPurchaseTable();
            //Transactions Tab
            loadTransactionTable();
            transactionsTab.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    loadTransactionList(null);
                }
            });
            //Sold Items Tab
            loadTransactionItemTable();
            soldsTab.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    loadTransactionItemList(null);
                }
            });
            //ECart Tab
            loadECartTable();
            ecartsTab.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    loadECartList(null);
                }
            });
            //ReturnItem Tab
            loadReturnsTable();
            returnsTab.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    loadReturnedItemList(null);
                }
            });

            //Remove FocusTab
            mainTabPane.addEventFilter(KeyEvent.ANY, event -> {
                if (event.getCode().isArrowKey() && event.getTarget() == mainTabPane) {
                    event.consume();
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {

    }

    @Override
    public void loadUIFixes() {
        try {
            loadTransactionFilters();
            loadSoldItemsFilters();
            loadECartFilters();
            loadReturnFilters();
            userLbl.setText("User : " + Care.getUser().getName());
        } catch (Exception er) {
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

    private void loadSearchTable() {
        try {
            t1sTbl.setEditable(false);
            t1sTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn descCol = FXTable.addColumn(t1sTbl, "Description", Item::descriptionProperty, false, 150, 1000, 300);
            //TableColumn qtyprcCol = FXTable.addColumn(t1sTbl, "Pricing", Item::descriptionProperty, false, 80, 180, 90);            
            TableColumn buyCol = FXTable.addColumn(t1sTbl, " ", Item::descriptionProperty, false, 45, 45, 45);

            descCol.setCellFactory(column -> {
                return new TableCell<Item, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    BorderPane borPane = new BorderPane();
                                    borPane.setMinSize(350, 55);
                                    borPane.setMaxSize(32767, 55);
                                    borPane.setPrefSize(350, 55);

                                    VBox container = new VBox();
                                    container.setMinSize(200, 55);
                                    container.setMaxSize(32767, 55);
                                    container.setPrefSize(310, 55);

                                    VBox container1 = new VBox();
                                    container1.setMinSize(150, 55);
                                    container1.setMaxSize(200, 55);
                                    container1.setPrefSize(150, 55);

                                    Label pnf = new Label();
                                    pnf.setText((row_data.isPnf()) ? "PNF" : " ");
                                    pnf.setStyle("-fx-font-weight:bold;-fx-font-size:12px;-fx-text-fill:danger-color;");

                                    Label qty = new Label();
                                    qty.textProperty().bindBidirectional(row_data.quantityProperty(), new NumberConverter());
                                    qty.setStyle("-fx-font-weight:bold;-fx-font-size:10px");
                                    ImageView qtyimg = new ImageView();
                                    qtyimg.setImage(new Image(getClass().getResource("/assets/NewProduct_48px.png").toURI().toString()));
                                    qtyimg.setPreserveRatio(true);
                                    qtyimg.setFitWidth(11);
                                    qty.setGraphic(qtyimg);

                                    Label prc = new Label();
                                    prc.textProperty().bindBidirectional(row_data.priceProperty(), new NumberConverter());
                                    prc.setStyle("-fx-font-weight:bold;-fx-font-size:17px");
                                    ImageView prcimg = new ImageView();
                                    prcimg.setImage(new Image(getClass().getResource("/assets/PesoSymbol_48px.png").toURI().toString()));
                                    prcimg.setPreserveRatio(true);
                                    prcimg.setFitWidth(19);
                                    prc.setGraphic(prcimg);

                                    Label name = new Label();
                                    name.textProperty().bind(row_data.descriptionProperty());
                                    name.setStyle("-fx-font-weight:bold;-fx-font-size:14px");

                                    Label generic = new Label();
                                    generic.textProperty().bind(row_data.genericnameProperty());

                                    Label categtype = new Label();
                                    String bctype = row_data.getCategory() + ((!row_data.getType().isEmpty() && !row_data.getCategory().isEmpty()) ? " - " : "") + row_data.getType();
                                    categtype.setText(bctype);
                                    //StringBinding bcatype = Bindings.createStringBinding(()->row_data.categoryProperty().get()+" - "+row_data.typeProperty().get(),row_data.categoryProperty(),row_data.typeProperty());
                                    //categtype.textProperty().bind(bcatype);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(name, generic, categtype);

                                    container1.setAlignment(Pos.CENTER_RIGHT);
                                    container1.getChildren().addAll(pnf, qty, prc);

                                    borPane.setCenter(container);
                                    borPane.setRight(container1);

                                    setGraphic(borPane);
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

            buyCol.setCellFactory(column -> {
                return new TableCell<Item, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(45, 55);
                                    container.setMaxSize(45, 55);
                                    container.setPrefSize(45, 55);

                                    JFXButton buyBtn = FXButtonsBuilderFactory.createButton("", 38, 38, "cell-btn", FontAwesomeIcon.SHOPPING_BASKET, "16px", evt -> {
                                        showPurchaseQuantityDialog(row_data);
                                    });

                                    buyBtn.setTooltip(new Tooltip("Purchase"));
                                    buyBtn.getStyleClass().add("btn-primary");
                                    buyBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    buyBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(buyBtn);

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

            t1sTbl.setOnKeyTyped(event -> {
                TablePosition<Item, Void> pos = t1sTbl.getFocusModel().getFocusedCell();
                if (pos != null) {
                    if (event.getCode() == KeyCode.DOWN) {
                        int index = t1sTbl.getSelectionModel().getSelectedIndex();
                        if (index < t1sTbl.getItems().size()) {
                            t1sTbl.getSelectionModel().selectNext();
                        }
                    } else if (event.getCode() == KeyCode.UP) {
                        int index = t1sTbl.getSelectionModel().getSelectedIndex();
                        if (index != 0) {
                            t1sTbl.getSelectionModel().selectPrevious();
                        }
                    }
                }
            });
            
            t1sTbl.setOnKeyReleased(event -> {
                TablePosition<Item, Void> pos = t1sTbl.getFocusModel().getFocusedCell();
                if (pos != null) {
                    if (event.getCode() == KeyCode.ENTER) {
                        Item row_data = t1sTbl.getSelectionModel().getSelectedItem();
                        if (row_data != null) {
                            showPurchaseQuantityDialog(row_data);
                        }
                    }
                }
            });
            
            

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadPurchaseTable() {
        try {
            t1pTbl.setEditable(false);
            t1pTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            FXTable.addColumn(t1pTbl, "Particulars", HospitalChargeItem::descriptionProperty, false);
            FXTable.addColumn(t1pTbl, "Quantity", HospitalChargeItem::quantityProperty, false, 80, 80, 80);
            FXTable.addColumn(t1pTbl, "Price", HospitalChargeItem::sellingProperty, false, 80, 80, 80);
            FXTable.addColumn(t1pTbl, "Total", HospitalChargeItem::totalsellingProperty, false, 120, 120, 120);

            TableColumn actCol = FXTable.addColumn(t1pTbl, " ", HospitalChargeItem::descriptionProperty, false, 76, 76, 76);
            actCol.setCellFactory(column -> {
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

                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {

                                        VBox content = new VBox();
                                        content.setMaxWidth(500);
                                        content.setMaxHeight(500);
                                        content.setAlignment(Pos.CENTER);
                                        content.setSpacing(35);
                                        content.setPadding(new Insets(35, 25, 25, 25));

                                        JFXTextField qty = new JFXTextField();
                                        qty.setPromptText("Change Quantity");
                                        qty.setMinHeight(28);
                                        qty.setMinWidth(250);
                                        qty.setMaxWidth(250);
                                        qty.setPrefWidth(250);
                                        qty.setLabelFloat(true);

                                        IntegerProperty quantity = new SimpleIntegerProperty(row_data.getQuantity());

                                        qty.textProperty().bindBidirectional(quantity, new NumberConverter());

                                        FXField.addIntegerValidator(qty, 1, 99999, 1);
                                        FXField.addFocusValidationListener(qty);

                                        content.getChildren().addAll(qty);

                                        JFXButton addScanned = new JFXButton("Confirm");
                                        addScanned.getStyleClass().add("btn-danger");

                                        JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, row_data.getDescription() + " - " + row_data.getSelling(), content, FXDialog.DANGER, addScanned);
                                        qty.requestFocus();
                                        qty.selectAll();

                                        addScanned.setOnAction(pruchaseEvt -> {
                                            if (qty.validate()) {
                                                row_data.setQuantity(quantity.get());
                                                row_data.calculateNet();
                                                dialog.close();
                                                loadPurchaseTotalAmount();
                                            }
                                        });
                                    });

                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-control");
                                    editBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        PURCHASE_MAP.remove(row_data.getItemcode());
                                        FXTable.setList(t1pTbl, new ArrayList(PURCHASE_MAP.values()));
                                        loadPurchaseTotalAmount();
                                    });

                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-primary");
                                    delBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(editBtn, delBtn);

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

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void showPurchaseQuantityDialog(Item item) {
        try {
            HospitalChargeItem i = item.purchase(1);
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35, 25, 25, 25));

            JFXTextField qty = new JFXTextField();
            qty.setPromptText("Change Quantity");
            qty.setMinHeight(28);
            qty.setMinWidth(250);
            qty.setMaxWidth(250);
            qty.setPrefWidth(250);
            qty.setLabelFloat(true);

            qty.textProperty().bindBidirectional(i.quantityProperty(), new NumberConverter());

            FXField.addIntegerValidator(qty, 1, 99999, 1);
            FXField.addFocusValidationListener(qty);

            content.getChildren().addAll(qty);

            JFXButton addScanned = new JFXButton("Add Purchase");
            addScanned.getStyleClass().add("btn-danger");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, i.getDescription() + " - " + i.getSelling(), content, FXDialog.DANGER, addScanned);
            qty.requestFocus();
            qty.selectAll();
            
            dialog.setOnDialogClosed(clsEvt->{
                t1keyF.requestFocus();
                t1keyF.selectAll();
                t1sTbl.getSelectionModel().clearSelection();
            });

            addScanned.setOnAction(pruchaseEvt -> {
                if (qty.validate()) {
                    i.calculateNet();
                    addToPurchaseList(i);
                    dialog.close();
                }
            });

            qty.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    addScanned.fire();
                } else if (evt.getCode() == KeyCode.ESCAPE) {
                    dialog.close();
                }
            });

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void addToPurchaseList(HospitalChargeItem item) {
        try {
            if (PURCHASE_MAP.containsKey(item.getItemcode())) {
                HospitalChargeItem p = PURCHASE_MAP.get(item.getItemcode());
                p.setQuantity(p.getQuantity() + item.getQuantity());
                p.calculateNet(true);
                PURCHASE_MAP.put(p.getItemcode(), p);
            } else {
                PURCHASE_MAP.put(item.getItemcode(), item);
            }
            FXTable.setList(t1pTbl, new ArrayList(PURCHASE_MAP.values()));
            loadPurchaseTotalAmount();
            t1pTbl.refresh();
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void setPurchaseList(Map<String, HospitalChargeItem> items) {
        try {
            FXTable.setList(t1pTbl, new ArrayList(PURCHASE_MAP.values()));
            loadPurchaseTotalAmount();
            t1pTbl.refresh();
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadPurchaseTotalAmount() {
        try {
            List<HospitalChargeItem> purchases = new ArrayList(PURCHASE_MAP.values());
            List<Double> amts = purchases.stream().map(itm -> itm.getTotalselling()).collect(Collectors.toList());
            double total_amount = amts.stream().collect(Collectors.summingDouble(Double::doubleValue));
            t1subtotalLbl.setText("SUB TOTAL : " + NumberKit.toCurrency(total_amount));

            List<HospitalChargeItem> dpurchases = new ArrayList();
            System.out.println("PURCHASES");
            purchases.stream().forEach(itm -> {
                System.out.println(itm.getDebugInfo());
                HospitalChargeItem temp_item = itm.getModelClone();
                temp_item.calculateNet(true, 20, 0, 0, 0);
                dpurchases.add(temp_item);
            });
            System.out.println("DISCOUNTED");
            dpurchases.stream().forEach(itm -> {
                System.out.println(itm.getDebugInfo());
            });

            List<Double> damts = dpurchases.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
            List<Double> lvats = dpurchases.stream().map(itm -> itm.getLessvat()).collect(Collectors.toList());
            List<Double> dvals = dpurchases.stream().map(itm -> itm.getScdiscount()).collect(Collectors.toList());

            double dtotal_amount = damts.stream().collect(Collectors.summingDouble(Double::doubleValue));
            double lessvat_amount = lvats.stream().collect(Collectors.summingDouble(Double::doubleValue));
            double sc_amount = dvals.stream().collect(Collectors.summingDouble(Double::doubleValue));

            t1discamtLbl.setText("LessVAT : " + NumberKit.toCurrency(lessvat_amount) + " / Discount : " + NumberKit.toCurrency(sc_amount) + " / SC/PWD Discounted Amt : " + NumberKit.toCurrency(dtotal_amount));
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    public void clearPurchaseList() {
        try {
            t1keyF.setText("");
            t1odeF.setText("");
            FXTable.setList(t1sTbl, new ArrayList());
            PURCHASE_MAP.clear();
            FXTable.setList(t1pTbl, new ArrayList());
            t1subtotalLbl.setText("SUB TOTAL : 0.00");
            t1discamtLbl.setText("SC/PWD Discounted Amt : 0.00");
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadTransactionTable() {
        try {
            t2Tbl.setEditable(true);
            t2Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t2Tbl, "Timestamp", HospitalCharge::chargetimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t2Tbl, "Charge #", HospitalCharge::chargenumberProperty, false, 105, 105, 105);
            FXTable.addColumn(t2Tbl, "Customer", HospitalCharge::chargetoProperty, false);
            FXTable.addColumn(t2Tbl, "Type", HospitalCharge::chargetypeProperty, false);
            FXTable.addColumn(t2Tbl, "Encoder", HospitalCharge::userProperty, false);
            FXTable.addColumn(t2Tbl, "Voided", HospitalCharge::voidedProperty, false);
            FXTable.addColumn(t2Tbl, "Invoice #", HospitalCharge::invoicenumberProperty, false);
            FXTable.addColumn(t2Tbl, "OR #", HospitalCharge::ornumberProperty, false);
            FXTable.addColumn(t2Tbl, "NET Sales", HospitalCharge::netsalesProperty, false, 120, 120, 120);
            TableColumn actCol = FXTable.addColumn(t2Tbl, "Actions", HospitalCharge::chargenumberProperty, false, 112, 112, 112);

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
                                    container.setMinSize(112, 40);
                                    container.setMaxSize(112, 40);
                                    container.setPrefSize(112, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack, "Charge Info", ViewForm.create(row_data, 450, 450), FXDialog.PRIMARY);
                                    });

                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton printBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PRINT, "14px", evt -> {                                        
                                        Care.process(()->{
                                            try{                                                
                                                Map<String, String> opts = GlobalOption.getMap("General");
                                                HospitalCharge charge = row_data.getModelClone();
                                                List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"="+charge.getId());
                                                charge.setItems(items);
                                                charge.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "Pharmacy", false);
                                            }catch(Exception er){
                                                System.out.println("Error on print charge");
                                            }
                                        });  
                                    });

                                    printBtn.setTooltip(new Tooltip("Print"));
                                    printBtn.getStyleClass().add("btn-success");
                                    printBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    printBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "14px", evt -> {
                                        JFXButton btn = new JFXButton("Yes, Void this Transaction!");
                                        btn.getStyleClass().add("btn-success");
                                        JFXDialog dl = FXDialog.showConfirmDialog(mainStack, "Confirmation", new Label("Void Charge " + row_data.getChargenumber() + " ?"), FXDialog.WARNING, btn);
                                        btn.setOnAction(voidevt -> {
                                            dl.close();
                                            row_data.setVoided(Care.getUser().getName());
                                            row_data.setVoidtime(LocalDateTime.now());
                                            List<HospitalChargeItem> items = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID + "='" + row_data.getId() + "'");
                                            if (row_data.update(true)) {
                                                items.stream().forEach(itm -> {
                                                    itm.setVoided(Care.getUser().getName());
                                                    itm.setVoidtime(LocalDateTime.now());
                                                    itm.update();
                                                });
                                                FXDialog.showMessageDialog(mainStack, "Voided", "Charge has been voided!", FXDialog.SUCCESS);
                                                loadTransactionList(null);
                                            } else {
                                                FXDialog.showMessageDialog(mainStack, "Failed", "Server Communication Failure", FXDialog.SUCCESS);
                                            }
                                        });
                                    });
                                    voidBtn.setDisable((row_data.getVoidtime() != null));

                                    voidBtn.setTooltip(new Tooltip("Void"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    voidBtn.setDisable(row_data.getVoidtime() != null || row_data.getCanceltime() != null);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, printBtn,voidBtn);

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

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadTransactionItemTable() {
        try {
            t3Tbl.setEditable(true);
            t3Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t3Tbl, "Timestamp", HospitalChargeItem::chargetimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t3Tbl, "Charge #", HospitalChargeItem::chargenumberProperty, false, 105, 105, 105);
            FXTable.addColumn(t3Tbl, "Customer", HospitalChargeItem::chargetoProperty, false);
            FXTable.addColumn(t3Tbl, "Item", HospitalChargeItem::descriptionProperty, false);
            FXTable.addColumn(t3Tbl, "Qty", HospitalChargeItem::quantityProperty, false, 80, 80, 80);
            FXTable.addColumn(t3Tbl, "Encoder", HospitalChargeItem::userProperty, false);
            FXTable.addColumn(t3Tbl, "NET Sales", HospitalChargeItem::netsalesProperty, false, 120, 120, 120);
            TableColumn actCol = FXTable.addColumn(t3Tbl, "Actions", HospitalChargeItem::chargenumberProperty, false, 76, 76, 76);
            FXTable.setTimestampColumn(timeCol);

            actCol.setCellFactory(column -> {
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
                                        FXDialog.showConfirmDialog(mainStack, "Charge Item Info", ViewForm.create(row_data, 450, 450), FXDialog.PRIMARY);
                                    });

                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton returnBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.REPLY, "14px", evt -> {
                                        try{
                                            VBox content = new VBox();
                                            content.setMaxWidth(500);
                                            content.setMaxHeight(500);
                                            content.setAlignment(Pos.CENTER);
                                            content.setSpacing(35);
                                            content.setPadding(new Insets(35, 25, 25, 25));

                                            Map<String, String> opts = GlobalOption.getMap("General");
                                            List<String> users = SQLTable.distinct(User.class, User.NAME);

                                            JFXTextField qtyF = new JFXTextField();
                                            qtyF.setPromptText("Returned Qty");
                                            qtyF.setMinHeight(28);
                                            qtyF.setMinWidth(250);
                                            qtyF.setMaxWidth(250);
                                            qtyF.setPrefWidth(250);
                                            qtyF.setLabelFloat(true);

                                            JFXTextField invF = new JFXTextField();
                                            invF.setPromptText("Invoice #");
                                            invF.setMinHeight(28);
                                            invF.setMinWidth(250);
                                            invF.setMaxWidth(250);
                                            invF.setPrefWidth(250);
                                            invF.setLabelFloat(true);

                                            JFXTextField orF = new JFXTextField();
                                            orF.setPromptText("OR #");
                                            orF.setMinHeight(28);
                                            orF.setMinWidth(250);
                                            orF.setMaxWidth(250);
                                            orF.setPrefWidth(250);
                                            orF.setLabelFloat(true);

                                            JFXTextField retbyF = new JFXTextField();
                                            retbyF.setPromptText("Returned By");
                                            retbyF.setMinHeight(28);
                                            retbyF.setMinWidth(250);
                                            retbyF.setMaxWidth(250);
                                            retbyF.setPrefWidth(250);
                                            retbyF.setLabelFloat(true);

                                            JFXTextField recbyF = new JFXTextField();
                                            recbyF.setPromptText("Recieved By");
                                            recbyF.setMinHeight(28);
                                            recbyF.setMinWidth(250);
                                            recbyF.setMaxWidth(250);
                                            recbyF.setPrefWidth(250);
                                            recbyF.setLabelFloat(true);

                                            JFXTextField remarkF = new JFXTextField();
                                            remarkF.setPromptText("Remarks");
                                            remarkF.setMinHeight(28);
                                            remarkF.setMinWidth(250);
                                            remarkF.setMaxWidth(250);
                                            remarkF.setPrefWidth(250);
                                            remarkF.setLabelFloat(true);
                                            
                                            IntegerProperty quantity = new SimpleIntegerProperty(1);
                                            qtyF.textProperty().bindBidirectional(quantity, new NumberConverter());

                                            TextFields.bindAutoCompletion(retbyF, users);
                                            TextFields.bindAutoCompletion(recbyF, users);

                                            FXField.addIntegerValidator(qtyF, 1, row_data.getQuantity(), 1);
                                            FXField.addRequiredValidator(retbyF);
                                            FXField.addRequiredValidator(recbyF);
                                            FXField.addRequiredValidator(remarkF);
                                            FXField.addFocusValidationListener(qtyF,retbyF, recbyF, remarkF);

                                            content.getChildren().addAll( qtyF, new Separator(), invF, orF, new Separator(), retbyF, recbyF, remarkF);

                                            JFXButton addScanned = new JFXButton("Confirm");
                                            addScanned.getStyleClass().add("btn-danger");

                                            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Return Items", content, FXDialog.DANGER, addScanned);
                                            qtyF.requestFocus();
                                            dialog.setOnDialogClosed(dlgevt -> {
                                                setReturningCharge(false);
                                            });
                                            setReturningCharge(true);

                                            addScanned.setOnAction(pruchaseEvt -> {
                                                if (qtyF.validate() && retbyF.validate() && recbyF.validate() && remarkF.validate()) {
                                                    ReturnedItem ritem = row_data.asReturnedItem();
                                                    ritem.setChargenumber(row_data.getChargenumber());
                                                    ritem.setChargeto(row_data.getChargeto());
                                                    ritem.setInvoice(invF.getText());
                                                    ritem.setOrnumber(orF.getText());
                                                    ritem.setRecievedby(recbyF.getText());
                                                    ritem.setReturnedby(retbyF.getText());
                                                    ritem.setRemarks(remarkF.getText());
                                                    ritem.setQuantity(quantity.get());
                                                    ritem.setFacility("Pharmacy");
                                                    ritem.setUser(Care.getUser().getName());
                                                    ritem.setUser_id(Care.getUser().getId());

                                                    Care.process(() -> {
                                                        try {
                                                            ritem.save();
                                                            ritem.addItemQuantity();
                                                            Platform.runLater(() -> {                                                                
                                                                dialog.close();
                                                                FXDialog.showMessageDialog(mainStack, "Success", "Return Saved!", FXDialog.SUCCESS);
                                                            });
                                                        } catch (Exception er) {
                                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                            dialog.close();
                                                        }
                                                    });
                                                }
                                            });
                                        }catch(Exception er){
                                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                        }
                                    });

                                    returnBtn.setTooltip(new Tooltip("Return"));
                                    returnBtn.getStyleClass().add("btn-danger");
                                    returnBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    returnBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    returnBtn.setDisable(row_data.getVoidtime() != null || row_data.getCanceltime() != null);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, returnBtn);

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

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadECartTable() {
        try {
            t5Tbl.setEditable(true);
            t5Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t5Tbl, "Timestamp", ECart::ecarttimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t5Tbl, "ECart #", ECart::ecartnumberProperty, false, 105, 105, 105);
            FXTable.addColumn(t5Tbl, "Facility", ECart::tofacilityProperty, false);
            FXTable.addColumn(t5Tbl, "Item", ECart::descriptionProperty, false);
            FXTable.addColumn(t5Tbl, "Qty", ECart::quantityProperty, false, 80, 80, 80);
            FXTable.addColumn(t5Tbl, "Encoder", ECart::userProperty, false);
            FXTable.addColumn(t5Tbl, "Requested By", ECart::requestedbyProperty, false);
            TableColumn actCol = FXTable.addColumn(t5Tbl, "Actions", ECart::descriptionProperty, false, 76, 76, 76);
            FXTable.setTimestampColumn(timeCol);

            actCol.setCellFactory(column -> {
                return new TableCell<ECart, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                ECart row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack, "ECart Item Info", ViewForm.create(row_data, 450, 450), FXDialog.PRIMARY);
                                    });

                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton voidBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this ecart?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            ECart delEx = row_data.getModelClone();
                                            if(delEx.delete(true)){
                                                d.close();
                                                loadECartList(null);
                                            }                                            
                                        });
                                    });

                                    voidBtn.setTooltip(new Tooltip("Delete"));
                                    voidBtn.getStyleClass().add("btn-danger");
                                    voidBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    voidBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, voidBtn);

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

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadReturnsTable() {
        try {
            t4Tbl.setEditable(true);
            t4Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t4Tbl, "Timestamp", ReturnedItem::returntimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t4Tbl, "Item", ReturnedItem::descriptionProperty, false);
            FXTable.addColumn(t4Tbl, "Qty", ReturnedItem::quantityProperty, false, 80, 80, 80);
            FXTable.addColumn(t4Tbl, "Charged To", ReturnedItem::chargetoProperty, false);
            FXTable.addColumn(t4Tbl, "Charged #", ReturnedItem::chargenumberProperty, false);
            FXTable.addColumn(t4Tbl, "Returned By", ReturnedItem::returnedbyProperty, false);
            FXTable.addColumn(t4Tbl, "Recieved By", ReturnedItem::recievedbyProperty, false);
            TableColumn actCol = FXTable.addColumn(t4Tbl, "Actions", ReturnedItem::descriptionProperty, false, 76, 76, 76);
            FXTable.setTimestampColumn(timeCol);

            actCol.setCellFactory(column -> {
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
                                        FXDialog.showConfirmDialog(mainStack, "Returned Item Info", ViewForm.create(row_data, 450, 450), FXDialog.PRIMARY);
                                    });

                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton deleteBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this return item?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            ReturnedItem delEx = row_data.getModelClone();
                                            if(delEx.delete(true)){
                                                d.close();
                                                delEx.reduceItemQuantity();
                                                loadReturnedItemList(null);
                                            }                                            
                                        });
                                    });

                                    deleteBtn.setTooltip(new Tooltip("Delete"));
                                    deleteBtn.getStyleClass().add("btn-danger");
                                    deleteBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    deleteBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn, deleteBtn);

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

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadTransactionList(String conditions) {
        try {
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
                        if (conditions == null || conditions.isEmpty()) {
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME + ">='" + t1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + t2 + "' AND " + HospitalCharge.CHARGEFACILITY + "='Pharmacy' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
                        } else {
                            charges = SQLTable.list(HospitalCharge.class, conditions);
                        }
                        Platform.runLater(() -> {
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
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadTransactionItemList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {

                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t3Tbl, new ArrayList());
                            t3Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<HospitalChargeItem> charges;
                        if (conditions == null || conditions.isEmpty()) {
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.CHARGETIME + ">='" + t1 + "' AND " + HospitalChargeItem.CHARGETIME + "<='" + t2 + "' AND " + HospitalChargeItem.FACILITY + "='Pharmacy' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
                        } else {
                            charges = SQLTable.list(HospitalChargeItem.class, conditions);
                        }
                        Platform.runLater(() -> {
                            FilteredList<HospitalChargeItem> filteredRecords = FXTable.setFilteredList(t3Tbl, charges);
                            HospitalChargeItem.createTableFilter(t3searchF, filteredRecords);
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
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadReturnedItemList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {

                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t4Tbl, new ArrayList());
                            t4Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<ReturnedItem> records;
                        if (conditions == null || conditions.isEmpty()) {
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(ReturnedItem.class, ReturnedItem.RETURNTIME + ">='" + t1 + "' AND " + ReturnedItem.RETURNTIME + "<='" + t2 + "' AND " + ReturnedItem.FACILITY + "='Pharmacy' ORDER BY " + ReturnedItem.RETURNTIME + " DESC");
                        } else {
                            records = SQLTable.list(ReturnedItem.class, conditions);
                        }
                        Platform.runLater(() -> {
                            FilteredList<ReturnedItem> filteredRecords = FXTable.setFilteredList(t4Tbl, records);
                            ReturnedItem.createTableFilter(t4searchF, filteredRecords);
                            t4resLlb.setText("Results : "+filteredRecords.size());
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

    private void loadECartList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {

                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t5Tbl, new ArrayList());
                            t5Tbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<ECart> records;
                        if (conditions == null || conditions.isEmpty()) {
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            records = SQLTable.list(ECart.class, ECart.ECARTTIME + ">='" + t1 + "' AND " + ECart.ECARTTIME + "<='" + t2 + "' AND " + ECart.FROMFACILITY + "='Pharmacy' ORDER BY " + ECart.ECARTTIME + " DESC");
                        } else {
                            records = SQLTable.list(ECart.class, conditions);
                        }
                        Platform.runLater(() -> {
                            FilteredList<ECart> filteredRecords = FXTable.setFilteredList(t5Tbl, records);
                            ECart.createTableFilter(t5searchF, filteredRecords);
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
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    //Filters
    private void loadTransactionFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Transactions");
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
                            loadTransactionList(HospitalCharge.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalCharge.CHARGETIME + "<='" + sqT2 + "' AND " + HospitalCharge.CHARGEFACILITY + "='Pharmacy' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadTransactionList(null);
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

    private void loadSoldItemsFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Items");
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
                            loadTransactionItemList(HospitalChargeItem.CHARGETIME + ">='" + sqT1 + "' AND " + HospitalChargeItem.CHARGETIME + "<='" + sqT2 + "' AND " + HospitalChargeItem.FACILITY + "='Pharmacy' ORDER BY " + HospitalCharge.CHARGETIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadTransactionItemList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<HospitalChargeItem> records = t3Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("chargeditems.xlsx");
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
                                    ExcelManager.export(HospitalChargeItem.class, records, sFile);
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

    private void loadECartFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Items");
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
                            loadECartList(ECart.ECARTTIME + ">='" + sqT1 + "' AND " + ECart.ECARTTIME + "<='" + sqT2 + "' AND " + ECart.FROMFACILITY + "='Pharmacy' ORDER BY " + ECart.ECARTTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadReturnedItemList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<ECart> records = t5Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("ecarts.xlsx");
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
                                    ExcelManager.export(ECart.class, records, sFile);
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

    private void loadReturnFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Items");
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
                            loadReturnedItemList(ReturnedItem.RETURNTIME + ">='" + sqT1 + "' AND " + ReturnedItem.RETURNTIME + "<='" + sqT2 + "' AND " + ReturnedItem.FACILITY + "='Pharmacy' ORDER BY " + ReturnedItem.RETURNTIME + " DESC");
                        } else {
                            warning.setVisible(true);
                        }
                    });

                } catch (Exception er) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                }
            });
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadReturnedItemList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<ReturnedItem> records = t4Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("returns.xlsx");
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
                                    ExcelManager.export(ReturnedItem.class, records, sFile);
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
            FXTable.addCustomTableMenu(t4Tbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadKeyboardShortcuts() {
        try {
            
            KeyCombination K_CTRL_ENTER = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
            KeyCombination K_F9 = new KeyCodeCombination(KeyCode.F9);
            KeyCombination K_F8 = new KeyCodeCombination(KeyCode.F8);
            KeyCombination K_F7 = new KeyCodeCombination(KeyCode.F7);
            KeyCombination K_F6 = new KeyCodeCombination(KeyCode.F6);
            KeyCombination K_F5 = new KeyCodeCombination(KeyCode.F5);
            
            KeyCombination K_F1 = new KeyCodeCombination(KeyCode.F1);
            KeyCombination K_F2 = new KeyCodeCombination(KeyCode.F2);
            KeyCombination K_F3 = new KeyCodeCombination(KeyCode.F3);
            KeyCombination K_F4 = new KeyCodeCombination(KeyCode.F4);

            Care.MAINSCENE.getAccelerators().put(K_CTRL_ENTER, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1CheckoutBtn.fire();
                }
            });
            
            Care.MAINSCENE.getAccelerators().put(K_F9, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1chargeBtn.fire();
                }
            });
            Care.MAINSCENE.getAccelerators().put(K_F8, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1consumeBtn.fire();
                }
            });
            Care.MAINSCENE.getAccelerators().put(K_F7, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1ecartBtn.fire();
                }
            });
            Care.MAINSCENE.getAccelerators().put(K_F6, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1returnBtn.fire();
                }
            });
            
            Care.MAINSCENE.getAccelerators().put(K_F5, ()->{
                if (mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false) {
                    t1holdBtn.fire();
                }
            });
            
            Care.MAINSCENE.getAccelerators().put(K_F1, ()->{
                if(mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false){
                    t1odeF.requestFocus();
                    t1odeF.selectAll();
                }
            });
            
            Care.MAINSCENE.getAccelerators().put(K_F2, ()->{      
                if(mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false){
                    t1keyF.requestFocus();
                    t1keyF.selectAll();                    
                }
            });
            
            
            Care.MAINSCENE.getAccelerators().put(K_F3, ()->{      
                if(mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false){
                    t1sTbl.requestFocus();
                    if(!t1sTbl.getItems().isEmpty()){
                        t1sTbl.getSelectionModel().selectFirst();
                    }                    
                }
            });
            
            Care.MAINSCENE.getAccelerators().put(K_F4, ()->{     
                if(mainTabPane.getSelectionModel().getSelectedItem() == terminalTab && checkingOut == false && chargingPatient == false && chargingInternal == false && chargingECart == false && chargingECart == false && returningCharge == false){
                    t1pTbl.requestFocus();
                    if(!t1pTbl.getItems().isEmpty()){
                        t1pTbl.getSelectionModel().selectFirst();
                    }                    
                }
            });

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    /**
     * Get the value of checkingOut
     *
     * @return the value of checkingOut
     */
    public boolean isCheckingOut() {
        return checkingOut;
    }

    /**
     * Set the value of checkingOut
     *
     * @param checkingOut new value of checkingOut
     */
    public void setCheckingOut(boolean checkingOut) {
        this.checkingOut = checkingOut;
    }

    /**
     * Get the value of chargingPatient
     *
     * @return the value of chargingPatient
     */
    public boolean isChargingPatient() {
        return chargingPatient;
    }

    /**
     * Set the value of chargingPatient
     *
     * @param chargingPatient new value of chargingPatient
     */
    public void setChargingPatient(boolean chargingPatient) {
        this.chargingPatient = chargingPatient;
    }

    /**
     * Get the value of returningCharge
     *
     * @return the value of returningCharge
     */
    public boolean isReturningCharge() {
        return returningCharge;
    }

    /**
     * Set the value of returningCharge
     *
     * @param returningCharge new value of returningCharge
     */
    public void setReturningCharge(boolean returningCharge) {
        this.returningCharge = returningCharge;
    }

    /**
     * Get the value of chargingInternal
     *
     * @return the value of chargingInternal
     */
    public boolean isChargingInternal() {
        return chargingInternal;
    }

    /**
     * Set the value of chargingInternal
     *
     * @param chargingInternal new value of chargingInternal
     */
    public void setChargingInternal(boolean chargingInternal) {
        this.chargingInternal = chargingInternal;
    }

    /**
     * Get the value of chargingECart
     *
     * @return the value of chargingECart
     */
    public boolean isChargingECart() {
        return chargingECart;
    }

    /**
     * Set the value of chargingECart
     *
     * @param chargingECart new value of chargingECart
     */
    public void setChargingECart(boolean chargingECart) {
        this.chargingECart = chargingECart;
    }

}
