package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import models.HospitalPersonel;
import models.Item;
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
public class HospitalPersonelsUIController implements Initializable,UIController {

    private StackPane mainStack;
    private MaskerPane maskerPane;
    
    @FXML
    private JFXTextField searchRecordF;

    @FXML
    private TableView<HospitalPersonel> recordTbl;

    @FXML
    private Label recordcountLbl;

    @FXML
    private JFXButton addBtn;

    @FXML
    void addRecord(ActionEvent event) {
        HospitalPersonelFormController.showDialog(new HospitalPersonel(),mainStack, maskerPane, this);
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
                loadHospitalPersonelList(null);
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
            recordTbl.setEditable(false);
            recordTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(recordTbl);

            FXTable.addColumn(recordTbl, "ID", HospitalPersonel::idProperty, false, 65, 65, 65);
            FXTable.addColumn(recordTbl, "Name", HospitalPersonel::nameProperty, false);
            FXTable.addColumn(recordTbl, "Occupation", HospitalPersonel::occupationProperty, false);
            FXTable.addColumn(recordTbl, "Specialization", HospitalPersonel::specializationProperty, false);
            FXTable.addColumn(recordTbl, "Email", HospitalPersonel::emailProperty, false);
            FXTable.addColumn(recordTbl, "Mobile #", HospitalPersonel::mobileProperty, false);            
            TableColumn actCol = FXTable.addColumn(recordTbl, " ", HospitalPersonel::nameProperty, false, 86, 86, 86);
            actCol.setCellFactory(column -> {
                return new TableCell<HospitalPersonel, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                HospitalPersonel row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMinSize(86, 32);
                                    container.setMaxSize(86, 32);
                                    container.setPrefSize(86, 32);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Personel Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View Info"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        HospitalPersonelFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, HospitalPersonelsUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Info"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER);
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

    @Override
    public void loadResources() {
        try{
            loadHospitalPersonelList(null);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try{
            
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

    public void loadHospitalPersonelList(String conditions){
        try{
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(recordTbl, new ArrayList());
                            recordTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(200);
                        List<HospitalPersonel> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(HospitalPersonel.class, HospitalPersonel.ID + "<>'0' ORDER BY " + HospitalPersonel.NAME + " ASC");
                        } else {
                            records = SQLTable.list(HospitalPersonel.class, conditions);
                        }
                        Platform.runLater(()->{
                            FilteredList<HospitalPersonel> filteredRecords = FXTable.setFilteredList(recordTbl, records);
                            HospitalPersonel.createTableFilter(searchRecordF, filteredRecords);
                            filteredRecords.addListener((ListChangeListener.Change<? extends HospitalPersonel> c) -> {
                                recordcountLbl.setText("Result : " + filteredRecords.size());
                            });
                            recordcountLbl.setText("Result : " + filteredRecords.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            recordTbl.setPlaceholder(null);
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
