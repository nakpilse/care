package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
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
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.Expense;
import models.Payment;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.converters.NumberConverter;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import nse.dcfx.utils.NumberKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;
import utils.FXTextEntry;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class AccountingUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    
    @FXML
    private JFXToggleNode paymentsMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode expensesMenu;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab paymentsTab;

    @FXML
    private TableView<Payment> t1Tbl;

    @FXML
    private JFXTextField t1searchF;

    @FXML
    private Label t1resultsLbl;
    
    @FXML
    private Label t1detailsLbl;

    @FXML
    private Tab expencesTab;

    @FXML
    private TableView<Expense> t2Tbl;

    @FXML
    private JFXTextField t2searchF;

    @FXML
    private Label t2resultsLbl;
    
    @FXML
    private Label t2detailsLbl;
    
    @FXML
    private JFXButton t2addBtn;

    @FXML
    void addExpense(ActionEvent event) {
        try{
            ExpenseFormController.showDialog(new Expense(), mainStack, maskerPane, AccountingUXController.this, t2addBtn);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadExpencesMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(expencesTab);
        loadExpenseList(null);
    }

    @FXML
    void loadPaymentsMenu(ActionEvent event) {
        mainTabPane.getSelectionModel().select(paymentsTab);
        loadPaymentList(null);
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
            if(val == 0){
                loadPaymentList(null);
            }else if(val == 1){
                loadExpenseList(null);
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
            
            loadPaymentTable();
            loadExpenseTable();
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            loadPaymentList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadPaymentTableFilters();
            loadExpenseTableFilters();
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
    
    private void loadPaymentTable(){
        try{
            t1Tbl.setEditable(true);
            t1Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t1Tbl, "Timestamp", Payment::paymenttimeProperty, false, 135, 135, 135);
            FXTable.addColumn(t1Tbl, "Patient", Payment::patientProperty, false);
            FXTable.addColumn(t1Tbl, "OR #", Payment::ornumberProperty, false);
            FXTable.addColumn(t1Tbl, "Invoice #", Payment::invoicenumberProperty, false);
            FXTable.addColumn(t1Tbl, "Cashier", Payment::cashierProperty, false);
            FXTable.addColumn(t1Tbl, "Paid By", Payment::paidbyProperty, false);
            FXTable.addColumn(t1Tbl, "Cancelled", Payment::cancelledProperty, false);
            TableColumn cancelCol = FXTable.addColumn(t1Tbl, "Cancel Time", Payment::canceltimeProperty, false, 135, 135, 135);
            FXTable.addColumn(t1Tbl, "Amount", Payment::amountProperty, false,135,135,135);
            TableColumn actCol = FXTable.addColumn(t1Tbl, "Actions", Payment::patientProperty, false, 40, 40, 40);
            
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
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Payment Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
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
    
    private void loadExpenseTable(){
        try{
            t2Tbl.setEditable(true);
            t2Tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t2Tbl, "Timestamp", Expense::expensedateProperty, false, 135, 135, 135);
            FXTable.addColumn(t2Tbl, "Vendor", Expense::vendorProperty, false);
            FXTable.addColumn(t2Tbl, "Invoice #", Expense::invoicenumberProperty, false);
            FXTable.addColumn(t2Tbl, "Voucher", Expense::voucherProperty, false);
            FXTable.addColumn(t2Tbl, "Encoder", Expense::encoderProperty, false);
            FXTable.addCurrencyColumn(t2Tbl, "Net Amount", Expense::netsalesProperty, false,100,100,100);
            TableColumn actCol = FXTable.addColumn(t2Tbl, "Actions", Expense::vendorProperty, false, 112, 112, 112);
            
            FXTable.setDateColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<Expense, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Expense row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(112, 40);
                                    container.setMaxSize(112, 40);
                                    container.setPrefSize(112, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.SEARCH, "14px", evt -> {
                                        FXDialog.showConfirmDialog(mainStack,"Expense Info",ViewForm.create(row_data, 450, 450),FXDialog.PRIMARY);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("View"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton editBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        ExpenseFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, AccountingUXController.this);
                                    });
                                    
                                    editBtn.setTooltip(new Tooltip("Edit"));
                                    editBtn.getStyleClass().add("btn-success");
                                    editBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    editBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "14px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this expense?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            Expense delEx = row_data.getModelClone();
                                            if(delEx.delete(true)){
                                                d.close();
                                                loadExpenseList(null);
                                            }                                            
                                        });
                                    });
                                    
                                    delBtn.setTooltip(new Tooltip("Delete"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    container.setAlignment(Pos.CENTER_LEFT);
                                    container.getChildren().addAll(viewBtn,editBtn,delBtn);

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
                    cashierF.setLabelFloat(true);
                    
                    JFXTextField patientF = new JFXTextField();
                    patientF.setPromptText("Patient");
                    patientF.setMinHeight(28);
                    patientF.setMinWidth(250);
                    patientF.setMaxWidth(250);
                    patientF.setPrefWidth(250);
                    patientF.setLabelFloat(true);

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
                List<Payment> records = t1Tbl.getItems();
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
            
            
            FXTable.addCustomTableMenu(t1Tbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadExpenseTableFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Records");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    List<String> vendors = SQLTable.distinct(Expense.class, Expense.VENDOR);
                    List<String> encoders = SQLTable.distinct(Expense.class, Expense.ENCODER);
                    
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(500);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));
                    
                    JFXTextField vendorF = new JFXTextField();
                    vendorF.setPromptText("Vendor");
                    vendorF.setMinHeight(28);
                    vendorF.setMinWidth(250);
                    vendorF.setMaxWidth(250);
                    vendorF.setPrefWidth(250);
                    vendorF.setLabelFloat(true);
                    
                    JFXTextField encoderF = new JFXTextField();
                    encoderF.setPromptText("Encoder");
                    encoderF.setMinHeight(28);
                    encoderF.setMinWidth(250);
                    encoderF.setMaxWidth(250);
                    encoderF.setPrefWidth(250);
                    encoderF.setLabelFloat(true);

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
                    
                    TextFields.bindAutoCompletion(vendorF, vendors);
                    TextFields.bindAutoCompletion(encoderF, encoders);

                    content.getChildren().addAll(vendorF,encoderF,dfrom, tfrom, dto, tto, warning);

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
                            loadExpenseList(Expense.EXPENSEDATE + ">='" + sqT1 + "' AND " + Expense.EXPENSEDATE + "<='" + sqT2 + "' AND "+Expense.VENDOR+" LIKE '%"+vendorF.getText()+"%' AND "+Expense.ENCODER+" LIKE '%"+encoderF.getText()+"%' ORDER BY " + Expense.EXPENSEDATE + " DESC");
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
                loadExpenseList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Expense> records = t2Tbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("expenses.xlsx");
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
                                    ExcelManager.export(Expense.class, records, sFile);
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
    
    private void loadPaymentList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t1Tbl, new ArrayList());
                            t1Tbl.setPlaceholder(new MaskerPane());
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
                            FilteredList<Payment> filteredRecords = FXTable.setFilteredList(t1Tbl, charges);
                            Payment.createTableFilter(t1searchF, filteredRecords);
                            List<Double> sales = filteredRecords.stream().map(itm -> itm.getAmount()).collect(Collectors.toList());
                            double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            t1detailsLbl.setText("Total Payments : "+NumberKit.toCurrency(total));
                            t1resultsLbl.setText("Results : "+filteredRecords.size());
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
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadExpenseList(String conditions){
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
                        List<Payment> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(Expense.class, Expense.EXPENSEDATE+">='"+t1+"' AND "+Expense.EXPENSEDATE+"<='"+t2+"' ORDER BY "+Expense.EXPENSEDATE+" DESC");                            
                        }else{
                            charges = SQLTable.list(Expense.class, conditions);
                        }     
                        Platform.runLater(()->{
                            FilteredList<Expense> filteredRecords = FXTable.setFilteredList(t2Tbl, charges);
                            Expense.createTableFilter(t2searchF, filteredRecords);
                            List<Double> sales = filteredRecords.stream().map(itm -> itm.getNetsales()).collect(Collectors.toList());
                            double total = sales.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            t2detailsLbl.setText("Total Amount : "+NumberKit.toCurrency(total));
                            t2resultsLbl.setText("Results : "+filteredRecords.size());
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
}
