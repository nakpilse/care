package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import models.ERConsultation;
import models.HospitalCharge;
import models.HospitalChargeItem;
import models.HospitalPersonel;
import models.Patient;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import utils.ExcelManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class ERUXController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    
    @FXML
    private JFXTextField t1searchF;

    @FXML
    private TableView<ERConsultation> t1table;

    @FXML
    private Label t1resultsLbl;

    @FXML
    private JFXButton t1searchBtn;

    @FXML
    private JFXButton t1addPatientBtn;

    @FXML
    private JFXButton t1addConsultationBtn;

    @FXML
    void addConsultation(ActionEvent event) {
        try{
            VBox content = new VBox();
            content.setMaxWidth(500);
            content.setMaxHeight(500);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(35);
            content.setPadding(new Insets(35,25,25,25));

            List<Patient> patients = SQLTable.list(Patient.class);
            List<HospitalPersonel> physicians = SQLTable.list(HospitalPersonel.class,HospitalPersonel.OCCUPATION+"='Physician' ORDER BY "+HospitalPersonel.NAME+" ASC");

            List<String> sphysicians = physicians.stream().map(obj->obj.getName()).collect(Collectors.toList());

            List<String> chargesuggestions = new ArrayList();
            patients.stream().forEach(rec->{
                chargesuggestions.add(rec.getFullname());
            });


            JFXTextField patientNameF = new JFXTextField();
            patientNameF.setPromptText("Patient Name");    
            patientNameF.setMinHeight(28);
            patientNameF.setMinWidth(250);
            patientNameF.setMaxWidth(250);
            patientNameF.setPrefWidth(250);   
            patientNameF.setLabelFloat(true);
            
            JFXTextField physicianF = new JFXTextField();
            physicianF.setPromptText("Physician");    
            physicianF.setMinHeight(28);
            physicianF.setMinWidth(250);
            physicianF.setMaxWidth(250);
            physicianF.setPrefWidth(250);   
            physicianF.setLabelFloat(true);
            
            

            TextFields.bindAutoCompletion(patientNameF, chargesuggestions);            
            TextFields.bindAutoCompletion(physicianF, sphysicians);

            FXField.addRequiredValidator(patientNameF);
            FXField.addRequiredValidator(physicianF);
            FXField.addFocusValidationListener(patientNameF,physicianF);

            content.getChildren().addAll(patientNameF,physicianF);

            JFXButton createBtn = new JFXButton("Create");                                    
            createBtn.getStyleClass().add("btn-danger");

            JFXDialog dialog = FXDialog.showConfirmDialog(mainStack,"Create ER Record",content,FXDialog.DANGER,createBtn);

            createBtn.setOnAction(crtEvt->{
                if(patientNameF.validate()){
                    Patient selPatient = null;
                    for(Patient p:patients){
                        if(p.getFullname().equals(patientNameF.getText())){
                            selPatient = p;
                            break;
                        }
                    }
                    if(selPatient != null){
                        int rec_num = SQLTable.getMaxValue(ERConsultation.class, ERConsultation.RECORDNUMBER);
                        ERConsultation rec = new ERConsultation();
                        rec.setRecordnumber((rec_num > 0)? String.valueOf(rec_num+1):"");
                        rec.setPatient_id(selPatient.getId());
                        rec.setPatientname(selPatient.getFullname());
                        rec.setPhysician(physicianF.getText());
                        dialog.close();
                        ERConsultationFormController.showDialog(rec, mainStack, maskerPane, this);
                    }
                }
            });
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void addPatient(ActionEvent event) {
        try{
            PatientRegistrationFormController.showDialog(mainStack, maskerPane, ERUXController.this,t1addPatientBtn);
        }catch(Exception er){
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void searchPatientMasterRecords(ActionEvent event) {
        try{
            if(t1searchF.getText() != null){
                String key = t1searchF.getText();
                String con = ERConsultation.PATIENTNAME+" LIKE '%"+key+"%' ORDER BY "+ERConsultation.CONSULTATIONTIME+" DESC";
                loadERConsultationList(con);
            }
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
            if(val == 0){
                loadERConsultationList(null);
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
        return this.mainStack;
    }

    @Override
    public void loadCustomizations() {
        try{
            t1table.setEditable(true);
            t1table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn timeCol = FXTable.addColumn(t1table, "Timestamp", ERConsultation::consultationtimeProperty, false, 150, 150, 150);
            FXTable.addColumn(t1table, "Patient Name", ERConsultation::patientnameProperty, false);
            FXTable.addColumn(t1table, "Physician", ERConsultation::physicianProperty, false);
            FXTable.addColumn(t1table, "Case", ERConsultation::casecodeProperty, false);
            FXTable.addColumn(t1table, "Encoder", ERConsultation::encoderProperty, false);
            FXTable.addColumn(t1table, "OR #", ERConsultation::ornumberProperty, false);
            TableColumn actCol = FXTable.addColumn(t1table, " ", ERConsultation::patientnameProperty, false, 44, 44, 44);
            
            actCol.setCellFactory(column -> {
                return new TableCell<ERConsultation, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                ERConsultation row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(76, 40);
                                    container.setMaxSize(76, 40);
                                    container.setPrefSize(76, 40);
                                    container.setSpacing(4);
                                    
                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "14px", evt -> {
                                        ERConsultationFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, ERUXController.this);
                                    });
                                    
                                    viewBtn.setTooltip(new Tooltip("Edit"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                    
                                    JFXButton printBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.PRINT, "14px", evt -> {
                                        FXTask task = new FXTask() {
                                            @Override
                                            protected void load() {
                                                try {
                                                    Platform.runLater(()->{
                                                        maskerPane.setVisible(true);
                                                    });
                                                    HospitalCharge charge = (HospitalCharge)SQLTable.get(HospitalCharge.class, HospitalCharge.RECORDTABLEID+"="+String.valueOf(row_data.getId())+" AND "+HospitalCharge.RECORDTABLE+"='"+ERConsultation.TABLE_NAME+"'");
                                                    if(charge!= null){
                                                        List<HospitalChargeItem> charge_items = SQLTable.list(HospitalChargeItem.class, HospitalChargeItem.HOSPITALCHARGE_ID+"='"+charge.getId()+"'");
                                                        charge.setItems(charge_items);
                                                        if(charge_items.size()>0){
                                                            Map<String,String> opts = GlobalOption.getMap("General");
                                                            charge.printChargeSlip(maskerPane, opts.get("FACILITYNAME"), "ER", false);
                                                        }
                                                    }
                                                } catch (Exception er) {
                                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                                } finally {
                                                    Platform.runLater(()->{
                                                        maskerPane.setVisible(false);
                                                    });
                                                }
                                            }
                                        };
                                        Care.process(task);
                                    });
                                    
                                    printBtn.setTooltip(new Tooltip("Print"));
                                    printBtn.getStyleClass().add("btn-success");
                                    printBtn.setStyle("-jfx-button-type : FLAT;-fx-padding:0;");
                                    printBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
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
            
            FXTable.setTimestampColumn(timeCol);
            
            t1searchF.setOnKeyReleased(evt ->{
                if(evt.getCode() == KeyCode.ENTER){
                    if(!t1searchF.getText().isEmpty()){
                        try{
                            t1searchBtn.fire();
                        }catch(Exception er){
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                        }
                    }
                }else if(evt.getCode() == KeyCode.ESCAPE){                    
                    try{
                        t1searchF.setText("");
                        loadERConsultationList(null);
                    }catch(Exception er){
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    }
                }
            });
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try{
            loadERConsultationList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            loadERRecordFilters();
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
    
    
    private void loadERConsultationList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {
                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(t1table, new ArrayList());
                            t1table.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(300);
                        List<ERConsultation> charges;
                        if( conditions == null || conditions.isEmpty()){
                            java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
                            java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
                            charges = SQLTable.list(ERConsultation.class, ERConsultation.CONSULTATIONTIME+">='"+t1+"' AND "+ERConsultation.CONSULTATIONTIME+"<='"+t2+"' ORDER BY "+ERConsultation.CONSULTATIONTIME+" DESC");                            
                        }else{
                            charges = SQLTable.list(ERConsultation.class,conditions);
                        }      
                        Platform.runLater(() -> {
                            FXTable.setList(t1table, charges);
                        });                        
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            t1table.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadERRecordFilters() {
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
                            loadERConsultationList(ERConsultation.CONSULTATIONTIME + ">='" + sqT1 + "' AND " + ERConsultation.CONSULTATIONTIME + "<='" + sqT2 + "' ORDER BY " + ERConsultation.CONSULTATIONTIME + " DESC");
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
                loadERConsultationList(null);
            });
            
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<ERConsultation> records = t1table.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("erconsultations.xlsx");
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
                                    ExcelManager.export(ERConsultation.class, records, sFile);
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
            
            
            FXTable.addCustomTableMenu(t1table, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
}
