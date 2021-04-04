package controllers;

import alpha.Care;
import alpha.ViewForm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import models.DeliveryItem;
import models.ECart;
import models.Item;
import models.ReturnedItem;
import models.Supplier;
import nse.dcfx.controls.FXButtonsBuilderFactory;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.models.SystemLog;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.FileKit;
import org.controlsfx.control.MaskerPane;
import utils.ExcelManager;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class InventoryUIController implements Initializable, UIController {

    private StackPane mainStack;
    private MaskerPane maskerPane;

    @FXML
    private JFXToggleNode pharmacyMenu;

    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXToggleNode suppliesMenu;

    @FXML
    private JFXToggleNode suppliersMenu;

    @FXML
    private JFXToggleNode deliveriesMenu;

    @FXML
    private JFXToggleNode ecartMenu;
    
    @FXML
    private JFXToggleNode returnsMenu;

    @FXML
    private JFXTabPane mainTabPane;

    @FXML
    private Tab pharmacyTab;

    @FXML
    private TableView<Item> itemsTbl;

    @FXML
    private JFXTextField itemssearchF;

    @FXML
    private Label itemresultsLbl;

    @FXML
    private JFXButton addItemBtn;

    @FXML
    private Tab suppliesTab;

    @FXML
    private TableView<Item> suppliesTbl;

    @FXML
    private JFXTextField suppliessearchF;

    @FXML
    private Label suppliesresultLbl;

    @FXML
    private JFXButton addsuppliesBtn;

    @FXML
    private Tab suppliersTab;

    @FXML
    private TableView<Supplier> suppliersTbl;

    @FXML
    private JFXTextField suppliersearchF;

    @FXML
    private Label supplierresultLbl;

    @FXML
    private JFXButton addsupplierBtn;

    @FXML
    private Tab deliveriesTab;

    @FXML
    private TableView<DeliveryItem> deliveriesTbl;

    @FXML
    private JFXTextField deliverysearchF;

    @FXML
    private Label deliveryresultLbl;

    @FXML
    private Tab ecartsTab;

    @FXML
    private TableView<ECart> ecartsTbl;

    @FXML
    private JFXTextField ecartsearchF;

    @FXML
    private Label ecartsresultLbl;
    
    @FXML
    private Tab returnsTab;

    @FXML
    private TableView<ReturnedItem> returnsTbl;

    @FXML
    private JFXTextField returnsearchF;

    @FXML
    private Label returnsresultLbl;

    @FXML
    void addItem(ActionEvent event) {
        Item item = new Item();
        item.setSupply(false);
        NewItemFormController.showDialog(item, mainStack, maskerPane, InventoryUIController.this, addItemBtn);
    }

    @FXML
    void addSupplier(ActionEvent event) {
        NewSupplierFormController.showDialog(mainStack, maskerPane, InventoryUIController.this, addsupplierBtn);
    }

    @FXML
    void addSupply(ActionEvent event) {
        Item item = new Item();
        item.setSupply(true);
        NewItemFormController.showDialog(item, mainStack, maskerPane, InventoryUIController.this, addsuppliesBtn);
    }

    @FXML
    void loadDeliveries(ActionEvent event) {
        mainTabPane.getSelectionModel().select(deliveriesTab);
        loadDeliveryList(null);
    }

    @FXML
    void loadPharmacy(ActionEvent event) {
        mainTabPane.getSelectionModel().select(pharmacyTab);
        loadPharmacyItemList(null);
    }

    @FXML
    void loadSuppliers(ActionEvent event) {
        mainTabPane.getSelectionModel().select(suppliersTab);
        loadSuppliersList(null);
    }

    @FXML
    void loadSupplies(ActionEvent event) {
        mainTabPane.getSelectionModel().select(suppliesTab);
        loadSupplyItemList(null);
    }

    @FXML
    void loadECarts(ActionEvent event) {
        mainTabPane.getSelectionModel().select(ecartsTab);
        loadECartList(null);
    }    
    
    @FXML
    void loadReturns(ActionEvent event) {
         mainTabPane.getSelectionModel().select(returnsTab);
         loadReturnedItemList(null);
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
                    loadPharmacyItemList(null);
                    break;
                case 1:
                    loadSupplyItemList(null);
                    break;
                case 2:
                    loadSuppliersList(null);
                    break;
                case 3:
                    loadDeliveryList(null);
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
        return mainStack;
    }

    @Override
    public void loadCustomizations() {
        try {
            //Pharmacy
            loadPharmacyTableCustomizations();
            //Supplies
            loadSupplyTableCustomizations();
            //Suppliers
            loadSupplierTableCustomizations();
            //Delivery
            loadDeliveryTableCustomizations();
            //ECart
            loadECartTableCustomizations();
            //Returns
            loadReturnsTableCustomizations();

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

    @Override
    public void setMaskerPane(MaskerPane masker) {
        this.maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }

    //TableCustomizations
    private void loadPharmacyTableCustomizations() {
        try {
            itemsTbl.setEditable(false);
            itemsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(itemsTbl);
            Map<String, String> opts = GlobalOption.getMap(GlobalOption.ITEM_CATEGORY);

            FXTable.addColumn(itemsTbl, "ID", Item::idProperty, false, 80, 80, 80);
            FXTable.addColumn(itemsTbl, "Code", Item::codeProperty, false, 80, 150, 120);
            TableColumn pharDescCol = FXTable.addColumn(itemsTbl, "Description", Item::descriptionProperty, false, 150, 1000, 300);
            TableColumn pharUnitsCol = FXTable.addColumn(itemsTbl, "STR/Units/Form", Item::descriptionProperty, false, 80, 180, 90);
            TableColumn pharShelfCol = FXTable.addColumn(itemsTbl, opts.get("ITEM_OPT1") + "/" + opts.get("ITEM_OPT2"), Item::descriptionProperty, false, 80, 180, 125);
            TableColumn pharQtyCol = FXTable.addColumn(itemsTbl, "Qty", Item::quantityProperty, false, 65, 125, 80);
            TableColumn pharPriceCol = FXTable.addColumn(itemsTbl, "Pricing", Item::descriptionProperty, false, 80, 180, 90);
            TableColumn pharPnfCol = FXTable.addColumn(itemsTbl, "PNF", Item::pnfProperty, false, 65, 65, 65);
            TableColumn pharVATCol = FXTable.addColumn(itemsTbl, "VATable", Item::vatableProperty, false, 65, 65, 65);
            TableColumn pharAvailCol = FXTable.addColumn(itemsTbl, "Available", Item::availableProperty, true, 65, 65, 65);
            TableColumn pharActCol = FXTable.addColumn(itemsTbl, "Actions", Item::descriptionProperty, false, 158, 158, 158);
            pharDescCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(200, 55);
                                    container.setMaxSize(32767, 55);
                                    container.setPrefSize(310, 55);

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
            pharUnitsCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label str = new Label();
                                    str.textProperty().bind(row_data.strengthProperty());
                                    str.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    str.setTooltip(new Tooltip("Strength"));
                                    str.setTextAlignment(TextAlignment.RIGHT);
                                    str.setAlignment(Pos.CENTER_RIGHT);

                                    Label measure = new Label();
                                    measure.textProperty().bind(row_data.unitmeasureProperty());
                                    measure.setTooltip(new Tooltip("Unit Measure"));
                                    measure.setTextAlignment(TextAlignment.RIGHT);
                                    measure.setAlignment(Pos.CENTER_RIGHT);
                                    
                                    Label form = new Label();
                                    form.textProperty().bind(row_data.formProperty());
                                    form.setTooltip(new Tooltip("Form"));
                                    form.setTextAlignment(TextAlignment.RIGHT);
                                    form.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(str, measure,form);

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
            pharShelfCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label str = new Label();
                                    str.textProperty().bind(row_data.opt1Property());
                                    str.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    str.setTooltip(new Tooltip(opts.get("ITEM_OPT1")));
                                    str.setTextAlignment(TextAlignment.RIGHT);
                                    str.setAlignment(Pos.CENTER_RIGHT);

                                    Label measure = new Label();
                                    measure.textProperty().bind(row_data.opt2Property());
                                    measure.setTooltip(new Tooltip(opts.get("ITEM_OPT2")));
                                    measure.setTextAlignment(TextAlignment.RIGHT);
                                    measure.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(str, measure);

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
            FXTable.setIntegerFormatColumn(pharQtyCol);
            pharPriceCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label sell = new Label();
                                    sell.textProperty().bind(row_data.priceProperty().asString("%,.2f"));
                                    sell.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    sell.setTooltip(new Tooltip("Selling Price"));
                                    sell.setTextAlignment(TextAlignment.RIGHT);
                                    sell.setAlignment(Pos.CENTER_RIGHT);

                                    Label cost = new Label();
                                    cost.textProperty().bind(row_data.costProperty().asString("%,.2f"));
                                    cost.setTooltip(new Tooltip("Unit Cost"));
                                    cost.setTextAlignment(TextAlignment.RIGHT);
                                    cost.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(sell, cost);

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
            pharPnfCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.setFocusTraversable(false);
                                    comp.selectedProperty().bindBidirectional(row_data.pnfProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.PNF);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });
            pharVATCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.setFocusTraversable(false);
                                    comp.selectedProperty().bindBidirectional(row_data.vatableProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.VATABLE);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });

            pharAvailCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.setFocusTraversable(false);
                                    comp.selectedProperty().bindBidirectional(row_data.availableProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.AVAILABLE);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });
            pharActCol.setCellFactory(column -> {
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
                                    container.setMinSize(158, 55);
                                    container.setMaxSize(158, 55);
                                    container.setPrefSize(158, 55);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Item Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View Item"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ItemFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, InventoryUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Item"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton dryBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRUCK, "16px", evt -> {
                                        DeliveryItem dlItem = row_data.newDelivery();
                                        DeliveryFormController.showDialog(dlItem, mainStack, maskerPane, InventoryUIController.this, 0);
                                    });

                                    dryBtn.setTooltip(new Tooltip("Add Item Delivery"));
                                    dryBtn.getStyleClass().add("btn-warning");
                                    dryBtn.setStyle("-jfx-button-type : FLAT;");
                                    dryBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this item?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            row_data.setArchive(true);
                                            row_data.update(true, Item.ARCHIVE);
                                            d.close();
                                            loadPharmacyItemList(null);
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Archive Item"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(viewBtn, edtBtn, dryBtn, delBtn);

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
            
            //Search
            itemssearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!itemssearchF.getText().isEmpty()) {
                        loadPharmacyItemList("("+Item.DESCRIPTION+" LIKE '%"+itemssearchF.getText()+"%' OR "+Item.GENERICNAME+" LIKE '%"+itemssearchF.getText()+"%' OR "+Item.CATEGORY+" LIKE '%"+itemssearchF.getText()+"%' OR "+Item.TYPE+" LIKE '%"+itemssearchF.getText()+"%' OR "+Item.FORM+" LIKE '%"+itemssearchF.getText()+"%') AND "+Item.SUPPLY+"='0' ORDER BY "+Item.DESCRIPTION+" ASC");
                    }
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSupplyTableCustomizations() {
        try {
            suppliesTbl.setEditable(false);
            suppliesTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(suppliesTbl);
            Map<String, String> opts = GlobalOption.getMap(GlobalOption.ITEM_CATEGORY);

            FXTable.addColumn(suppliesTbl, "ID", Item::idProperty, false, 80, 80, 80);
            FXTable.addColumn(suppliesTbl, "Code", Item::codeProperty, false, 80, 150, 120);
            TableColumn pharDescCol = FXTable.addColumn(suppliesTbl, "Description", Item::descriptionProperty, false, 150, 1000, 300);
            TableColumn pharUnitsCol = FXTable.addColumn(suppliesTbl, "STR/Units/Form", Item::descriptionProperty, false, 80, 180, 90);
            TableColumn pharShelfCol = FXTable.addColumn(suppliesTbl, opts.get("ITEM_OPT1") + "/" + opts.get("ITEM_OPT2"), Item::descriptionProperty, false, 80, 180, 125);
            TableColumn pharQtyCol = FXTable.addColumn(suppliesTbl, "Qty", Item::quantityProperty, false, 65, 125, 80);
            TableColumn pharPriceCol = FXTable.addColumn(suppliesTbl, "Pricing", Item::descriptionProperty, false, 80, 180, 90);
            TableColumn pharPnfCol = FXTable.addColumn(suppliesTbl, "PNF", Item::pnfProperty, false, 65, 65, 65);
            TableColumn pharVATCol = FXTable.addColumn(suppliesTbl, "VATable", Item::vatableProperty, false, 65, 65, 65);
            TableColumn pharAvailCol = FXTable.addColumn(suppliesTbl, "Available", Item::availableProperty, true, 65, 65, 65);
            TableColumn pharActCol = FXTable.addColumn(suppliesTbl, "Actions", Item::descriptionProperty, false, 158, 158, 158);
            pharDescCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(200, 55);
                                    container.setMaxSize(32767, 55);
                                    container.setPrefSize(310, 55);

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
            pharUnitsCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label str = new Label();
                                    str.textProperty().bind(row_data.strengthProperty());
                                    str.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    str.setTooltip(new Tooltip("Strength"));
                                    str.setTextAlignment(TextAlignment.RIGHT);
                                    str.setAlignment(Pos.CENTER_RIGHT);

                                    Label measure = new Label();
                                    measure.textProperty().bind(row_data.unitmeasureProperty());
                                    measure.setTooltip(new Tooltip("Unit Measure"));
                                    measure.setTextAlignment(TextAlignment.RIGHT);
                                    measure.setAlignment(Pos.CENTER_RIGHT);
                                    
                                    Label form = new Label();
                                    form.textProperty().bind(row_data.formProperty());
                                    form.setTooltip(new Tooltip("Form"));
                                    form.setTextAlignment(TextAlignment.RIGHT);
                                    form.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(str, measure,form);

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
            pharShelfCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label str = new Label();
                                    str.textProperty().bind(row_data.opt1Property());
                                    str.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    str.setTooltip(new Tooltip(opts.get("ITEM_OPT1")));
                                    str.setTextAlignment(TextAlignment.RIGHT);
                                    str.setAlignment(Pos.CENTER_RIGHT);

                                    Label measure = new Label();
                                    measure.textProperty().bind(row_data.opt2Property());
                                    measure.setTooltip(new Tooltip(opts.get("ITEM_OPT2")));
                                    measure.setTextAlignment(TextAlignment.RIGHT);
                                    measure.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(str, measure);

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
            FXTable.setIntegerFormatColumn(pharQtyCol);
            pharPriceCol.setCellFactory(column -> {
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
                                    VBox container = new VBox();
                                    container.setMinSize(80, 55);
                                    container.setMaxSize(180, 55);
                                    container.setPrefSize(90, 55);

                                    Label sell = new Label();
                                    sell.textProperty().bind(row_data.priceProperty().asString("%,.2f"));
                                    sell.setStyle("-fx-font-weight:bold;-fx-font-size:14px");
                                    sell.setTooltip(new Tooltip("Selling Price"));
                                    sell.setTextAlignment(TextAlignment.RIGHT);
                                    sell.setAlignment(Pos.CENTER_RIGHT);

                                    Label cost = new Label();
                                    cost.textProperty().bind(row_data.costProperty().asString("%,.2f"));
                                    cost.setTooltip(new Tooltip("Unit Cost"));
                                    cost.setTextAlignment(TextAlignment.RIGHT);
                                    cost.setAlignment(Pos.CENTER_RIGHT);

                                    container.setAlignment(Pos.CENTER_RIGHT);
                                    container.getChildren().addAll(sell, cost);

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
            pharPnfCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.selectedProperty().bindBidirectional(row_data.pnfProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.PNF);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });
            pharVATCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.selectedProperty().bindBidirectional(row_data.vatableProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.VATABLE);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });

            pharAvailCol.setCellFactory(column -> {
                return new TableCell<Item, Boolean>() {
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Item row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    JFXCheckBox comp = new JFXCheckBox();
                                    comp.selectedProperty().bindBidirectional(row_data.availableProperty());
                                    comp.setOnAction(evt -> {
                                        row_data.update(true, Item.AVAILABLE);
                                    });
                                    container.getChildren().setAll(comp);
                                    container.setAlignment(Pos.CENTER);
                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                            }
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                };
            });
            pharActCol.setCellFactory(column -> {
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
                                    container.setMinSize(158, 55);
                                    container.setMaxSize(158, 55);
                                    container.setPrefSize(158, 55);
                                    container.setSpacing(4);

                                    JFXButton viewBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Item Information", vf, FXDialog.PRIMARY, null);
                                    });
                                    viewBtn.setTooltip(new Tooltip("View Item"));
                                    viewBtn.getStyleClass().add("btn-control");
                                    viewBtn.setStyle("-jfx-button-type : FLAT;");
                                    viewBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        ItemFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, InventoryUIController.this);
                                    });
                                    edtBtn.setTooltip(new Tooltip("Edit Item"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton dryBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRUCK, "16px", evt -> {
                                        DeliveryItem dlItem = row_data.newDelivery();
                                        DeliveryFormController.showDialog(dlItem, mainStack, maskerPane, InventoryUIController.this, 1);
                                    });
                                    dryBtn.setTooltip(new Tooltip("Add Item Delivery"));
                                    dryBtn.getStyleClass().add("btn-warning");
                                    dryBtn.setStyle("-jfx-button-type : FLAT;");
                                    dryBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this item?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            row_data.setArchive(true);
                                            row_data.update(true, Item.ARCHIVE);
                                            d.close();
                                            loadSupplyItemList(null);
                                        });
                                    });
                                    delBtn.setTooltip(new Tooltip("Archive Item"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(viewBtn, edtBtn, dryBtn, delBtn);

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

            
            //Search
            suppliessearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!suppliessearchF.getText().isEmpty()) {
                        loadSupplyItemList(Item.DESCRIPTION+" LIKE '%"+suppliessearchF.getText()+"%' AND "+Item.SUPPLY+"='1' ORDER BY "+Item.DESCRIPTION+" ASC");
                    }
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSupplierTableCustomizations() {
        try {
            suppliersTbl.setEditable(false);
            suppliersTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(suppliersTbl);
            FXTable.addColumn(suppliersTbl, "Code", Supplier::codeProperty, false, 65, 150, 85);
            FXTable.addColumn(suppliersTbl, "Name", Supplier::nameProperty, false);
            FXTable.addColumn(suppliersTbl, "Contact Person", Supplier::contactpersonProperty, false);
            FXTable.addColumn(suppliersTbl, "Contact #", Supplier::contactnumberProperty, false);
            FXTable.addColumn(suppliersTbl, "Email", Supplier::contactemailProperty, false);
            TableColumn termsCol = FXTable.addColumn(suppliersTbl, "Terms", Supplier::paymenttermsProperty, false, 80, 80, 80);
            TableColumn actCol = FXTable.addColumn(suppliersTbl, "Actions", Supplier::nameProperty, false, 86, 86, 86);
            termsCol.setCellFactory(column -> {
                return new TableCell<Supplier, Integer>() {
                    @Override
                    protected void updateItem(Integer value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Supplier row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    this.textProperty().unbind();
                                    this.textProperty().bind(row_data.paymenttermsProperty().asString("%d Day/s"));
                                    setStyle("-fx-alightment : CENTER_RIGHT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });

            actCol.setCellFactory(column -> {
                return new TableCell<Supplier, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                Supplier row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMaxWidth(86);
                                    container.setMaxWidth(86);
                                    container.setPrefWidth(86);
                                    container.setSpacing(4);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EDIT, "16px", evt -> {
                                        SupplierFormController.showDialog(row_data.getModelClone(), mainStack, maskerPane, InventoryUIController.this);
                                    });

                                    edtBtn.setTooltip(new Tooltip("Edit Supplier"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TRASH, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to delete this supplier?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            row_data.setArchive(true);
                                            row_data.update(true, Supplier.ARCHIVE);
                                            d.close();
                                            loadSuppliersList(null);
                                        });
                                    });

                                    delBtn.setTooltip(new Tooltip("Archive Item"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(edtBtn, delBtn);

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
            
            //Search
            suppliersearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!suppliersearchF.getText().isEmpty()) {
                        loadSuppliersList(Supplier.NAME+" LIKE '%"+suppliersearchF.getText()+"%' ORDER BY "+Supplier.NAME+" ASC");
                    }
                }
            });

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadDeliveryTableCustomizations() {
        try {
            deliveriesTbl.setEditable(false);
            deliveriesTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(deliveriesTbl);
            TableColumn dateCol = FXTable.addColumn(deliveriesTbl, "Date", DeliveryItem::deliverydateProperty, false, 85, 85, 85);
            FXTable.addColumn(deliveriesTbl, "Item", DeliveryItem::descriptionProperty, false);
            TableColumn unitCol = FXTable.addColumn(deliveriesTbl, "Unit", DeliveryItem::unitsProperty, false, 80, 80, 80);
            FXTable.addColumn(deliveriesTbl, "Type", DeliveryItem::typeProperty, false, 80, 80, 80);
            TableColumn qtyCol = FXTable.addColumn(deliveriesTbl, "Inv Qty", DeliveryItem::itemqtyProperty, false, 80, 80, 80);
            FXTable.addColumn(deliveriesTbl, "Supplier", DeliveryItem::supplierProperty, false);
            FXTable.addColumn(deliveriesTbl, "Reciever", DeliveryItem::recievedbyProperty, false);
            TableColumn cancelCol = FXTable.addColumn(deliveriesTbl, "Canceled", DeliveryItem::canceldateProperty, false, 150, 150, 150);
            TableColumn actCol = FXTable.addColumn(deliveriesTbl, "Actions", DeliveryItem::descriptionProperty, false, 122, 122, 122);
            FXTable.setDateColumn(dateCol);
            FXTable.setIntegerFormatColumn(qtyCol);
            FXTable.setTimestampColumn(cancelCol);

            unitCol.setCellFactory(column -> {
                return new TableCell<DeliveryItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                DeliveryItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    setText(row_data.getQuantity() + " " + row_data.getUnits());
                                    //setGraphic(null);                                    
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });

            actCol.setCellFactory(column -> {
                return new TableCell<DeliveryItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                DeliveryItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMaxWidth(122);
                                    container.setMaxWidth(122);
                                    container.setPrefWidth(122);
                                    container.setSpacing(4);

                                    JFXButton vwBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Delivery Information", vf, FXDialog.PRIMARY, null);
                                    });

                                    vwBtn.setTooltip(new Tooltip("View Delivery"));
                                    vwBtn.getStyleClass().add("btn-control");
                                    vwBtn.setStyle("-jfx-button-type : FLAT;");
                                    vwBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton edtBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.REPLY, "16px", evt -> {
                                        DeliveryItem i = row_data.getModelClone();
                                        i.setType(DeliveryItem.DELIVERY_RETURNED);
                                        i.setItemqty(0);
                                        DeliveryFormController.showDialog(i, mainStack, maskerPane, InventoryUIController.this, 3);
                                    });

                                    edtBtn.setTooltip(new Tooltip("Return Delivery"));
                                    edtBtn.getStyleClass().add("btn-primary");
                                    edtBtn.setStyle("-jfx-button-type : FLAT;");
                                    edtBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Cancel");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to cancel this delivery?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            try {
                                                DeliveryItem i = row_data.getModelClone();
                                                Item t = (Item) SQLTable.get(Item.class, i.getItem_id());
                                                String stat;
                                                if (i.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RECIEVED)) {
                                                    stat = "Canceled delivery decieved " + i.getItemqty();
                                                    t.setQuantity(t.getQuantity() - i.getItemqty());
                                                } else {
                                                    stat = "Canceled delivery returned " + i.getItemqty();
                                                    t.setQuantity(t.getQuantity() + i.getItemqty());
                                                }
                                                i.setCanceldate(LocalDateTime.now());
                                                i.setCanceledby(Care.getUser().getName());
                                                if (i.update(true, DeliveryItem.CANCELDATE, DeliveryItem.CANCELEDBY)) {
                                                    t.update(true, Item.QUANTITY);
                                                    SystemLog log = new SystemLog();
                                                    log.setLogtable(Item.TABLE_NAME);
                                                    log.setLogtableid(t.getId());
                                                    log.setDescription(t.toJSON().toJSONString());
                                                    log.setLogtype(SystemLog.UPDATE);
                                                    log.setUser(SystemLog.getCurrentUser());
                                                    log.setUser_id(SystemLog.getCurrentUserID());
                                                    log.setRemarks(stat);
                                                    log.save();
                                                    loadDeliveryList(null);
                                                    d.close();
                                                } else {
                                                    d.close();
                                                    FXDialog.showMessageDialog(mainStack, "Server connection error", "Failed to cancel delivery please try again!", FXDialog.DANGER);
                                                }

                                            } catch (Exception er) {
                                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                            }
                                        });
                                    });

                                    delBtn.setTooltip(new Tooltip("Cancel Delivery"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    edtBtn.setDisable(row_data.getType().equalsIgnoreCase(DeliveryItem.DELIVERY_RETURNED) || row_data.getCanceldate() != null);
                                    delBtn.setDisable(row_data.getCanceldate() != null);

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(vwBtn, edtBtn, delBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });

            //Search
            deliverysearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!deliverysearchF.getText().isEmpty()) {
                        loadDeliveryList(DeliveryItem.DESCRIPTION+" LIKE '%"+deliverysearchF.getText()+"%' ORDER BY "+DeliveryItem.DESCRIPTION+" ASC");
                    }
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadECartTableCustomizations() {
        try {
            ecartsTbl.setEditable(false);
            ecartsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(ecartsTbl);
            TableColumn timeCol = FXTable.addColumn(ecartsTbl, "Timestamp", ECart::ecarttimeProperty, false, 150, 150, 150);
            FXTable.addColumn(ecartsTbl, "ECart #", ECart::ecartnumberProperty, false, 110, 110, 110);
            FXTable.addColumn(ecartsTbl, "Item", ECart::descriptionProperty, false);
            FXTable.addColumn(ecartsTbl, "Quantity", ECart::quantityProperty, false,65,65,65);
            FXTable.addColumn(ecartsTbl, "RequestedBy", ECart::requestedbyProperty, false);
            FXTable.addColumn(ecartsTbl, "ApprovedBy", ECart::approvedbyProperty, false);
            FXTable.addColumn(ecartsTbl, "Encoder", ECart::userProperty, false);
            TableColumn actCol = FXTable.addColumn(ecartsTbl, "Actions", ECart::ecartnumberProperty, false, 82, 82, 82);
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<ECart, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                ECart row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMaxWidth(122);
                                    container.setMaxWidth(122);
                                    container.setPrefWidth(122);
                                    container.setSpacing(4);

                                    JFXButton vwBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "ECart Info", vf, FXDialog.PRIMARY, null);
                                    });

                                    vwBtn.setTooltip(new Tooltip("View ECart"));
                                    vwBtn.getStyleClass().add("btn-control");
                                    vwBtn.setStyle("-jfx-button-type : FLAT;");
                                    vwBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to deleted this ecart?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            try {
                                                
                                            } catch (Exception er) {
                                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                            }
                                        });
                                    });

                                    delBtn.setTooltip(new Tooltip("Delete ECart"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(vwBtn, delBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });
            
            //Search
            ecartsearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!ecartsearchF.getText().isEmpty()) {
                        loadECartList(ECart.DESCRIPTION+" LIKE '%"+ecartsearchF.getText()+"%' ORDER BY "+ECart.DESCRIPTION+" ASC");
                    }
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadReturnsTableCustomizations() {
        try {
            returnsTbl.setEditable(false);
            returnsTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(returnsTbl);
            TableColumn timeCol = FXTable.addColumn(returnsTbl, "Timestamp", ReturnedItem::returntimeProperty, false, 150, 150, 150);
            FXTable.addColumn(returnsTbl, "Item", ReturnedItem::descriptionProperty, false);
            FXTable.addColumn(returnsTbl, "Quantity", ReturnedItem::quantityProperty, false,65,65,65);
            FXTable.addColumn(returnsTbl, "Charge #", ReturnedItem::chargenumberProperty, false);
            FXTable.addColumn(returnsTbl, "ChargeTo", ReturnedItem::chargetoProperty, false);
            FXTable.addColumn(returnsTbl, "ReturnedBy", ReturnedItem::returnedbyProperty, false);
            FXTable.addColumn(returnsTbl, "RecievedBy", ReturnedItem::recievedbyProperty, false);
            FXTable.addColumn(returnsTbl, "Encoder", ReturnedItem::userProperty, false);
            TableColumn actCol = FXTable.addColumn(returnsTbl, "Actions", ReturnedItem::descriptionProperty, false, 82, 82, 82);
            FXTable.setTimestampColumn(timeCol);
            
            actCol.setCellFactory(column -> {
                return new TableCell<ReturnedItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty || value == null || value.isEmpty()) {
                            setGraphic(null);
                            setStyle("");
                            setText("");
                        } else {
                            try {
                                ReturnedItem row_data = getTableView().getItems().get(getIndex());
                                if (row_data != null) {
                                    HBox container = new HBox();
                                    container.setMaxWidth(122);
                                    container.setMaxWidth(122);
                                    container.setPrefWidth(122);
                                    container.setSpacing(4);

                                    JFXButton vwBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.EYE, "16px", evt -> {
                                        AnchorPane vf = ViewForm.create(row_data.getModelClone(), 600, 445);
                                        JFXDialog d = FXDialog.showMessageDialog(mainStack, "Return Item Info", vf, FXDialog.PRIMARY, null);
                                    });

                                    vwBtn.setTooltip(new Tooltip("View Return Info"));
                                    vwBtn.getStyleClass().add("btn-control");
                                    vwBtn.setStyle("-jfx-button-type : FLAT;");
                                    vwBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    JFXButton delBtn = FXButtonsBuilderFactory.createButton("", 32, 32, "cell-btn", FontAwesomeIcon.TIMES_CIRCLE, "16px", evt -> {
                                        JFXButton b = new JFXButton("Yes, Delete");
                                        b.getStyleClass().add("btn-danger");
                                        JFXDialog d = FXDialog.showConfirmDialog(mainStack, "Confirm", new Label("Do you want to deleted this return info?"), FXDialog.DANGER, b);
                                        b.setOnAction(bevt -> {
                                            try {
                                                
                                            } catch (Exception er) {
                                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                                            }
                                        });
                                    });

                                    delBtn.setTooltip(new Tooltip("Delete ECart"));
                                    delBtn.getStyleClass().add("btn-danger");
                                    delBtn.setStyle("-jfx-button-type : FLAT;");
                                    delBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                                    

                                    container.setAlignment(Pos.CENTER);
                                    container.getChildren().addAll(vwBtn, delBtn);

                                    setGraphic(container);
                                    setStyle("-fx-alightment : CENTER_LEFT;");
                                } else {
                                    setGraphic(null);
                                    setStyle("");
                                    setText("");
                                }
                            } catch (Exception er) {
                                setGraphic(null);
                                setStyle("");
                                setText("");
                            }
                        }
                    }
                };
            });
            
            //Search
            returnsearchF.setOnKeyReleased(evt -> {
                if (evt.getCode() == KeyCode.ENTER) {
                    if (!returnsearchF.getText().isEmpty()) {
                        loadReturnedItemList(ReturnedItem.DESCRIPTION+" LIKE '%"+returnsearchF.getText()+"%' ORDER BY "+ReturnedItem.DESCRIPTION+" ASC");
                    }
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    //TablePopulations
    private void loadPharmacyItemList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(itemsTbl, new ArrayList());
                            itemsTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<Item> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(Item.class, Item.ARCHIVE + "='0' AND " + Item.SUPPLY + "='0' ORDER BY " + Item.ID + " DESC");
                        } else {
                            records = SQLTable.list(Item.class, conditions);
                        }
                        /*
                        FilteredList<Item> filteredRecords = FXTable.setFilteredList(itemsTbl, records);
                        Item.createTableFilter(itemssearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends Item> c) -> {
                            itemresultsLbl.setText("Result : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(itemsTbl, records);
                            itemresultsLbl.setText("Result : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            itemsTbl.setPlaceholder(null);
                        });                        
                    }
                }
            };
            Care.process(task);
            
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSupplyItemList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(suppliesTbl, new ArrayList());
                            suppliesTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<Item> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(Item.class, Item.ARCHIVE + "='0' AND " + Item.SUPPLY + "='1' ORDER BY " + Item.ID + " DESC");
                        } else {
                            records = SQLTable.list(Item.class, conditions);
                        }
                        /*
                        FilteredList<Item> filteredRecords = FXTable.setFilteredList(suppliesTbl, records);
                        Item.createTableFilter(suppliessearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends Item> c) -> {
                            suppliesresultLbl.setText("Results : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(suppliesTbl, records);
                            suppliesresultLbl.setText("Results : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            suppliesTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSuppliersList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(suppliersTbl, new ArrayList());
                            suppliersTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<Supplier> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(Supplier.class, Supplier.ARCHIVE + "='0' ORDER BY " + Supplier.NAME + " ASC");
                        } else {
                            records = SQLTable.list(Supplier.class, conditions);
                        }
                        /*
                        FilteredList<Supplier> filteredRecords = FXTable.setFilteredList(suppliersTbl, records);
                        Supplier.createTableFilter(suppliersearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends Supplier> c) -> {
                            supplierresultLbl.setText("Results : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(suppliersTbl, records);
                            supplierresultLbl.setText("Results : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            suppliersTbl.setPlaceholder(null);
                        });
                    }
                }
            };
            Care.process(task);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadDeliveryList(String conditions) {
        try {
            FXTask task = new FXTask() {
                @Override
                protected void load() {
                    try {                        
                        Platform.runLater(() -> {
                            FXTable.setFilteredList(deliveriesTbl, new ArrayList());
                            deliveriesTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<DeliveryItem> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(DeliveryItem.class, DeliveryItem.ID + "<>'0' ORDER BY " + DeliveryItem.DELIVERYDATE + " DESC");
                        } else {
                            records = SQLTable.list(DeliveryItem.class, conditions);
                        }
                        /*
                        FilteredList<DeliveryItem> filteredRecords = FXTable.setFilteredList(deliveriesTbl, records);
                        DeliveryItem.createTableFilter(deliverysearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends DeliveryItem> c) -> {
                            deliveryresultLbl.setText("Results : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(deliveriesTbl, records);
                            deliveryresultLbl.setText("Results : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            deliveriesTbl.setPlaceholder(null);
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
                            FXTable.setFilteredList(ecartsTbl, new ArrayList());
                            ecartsTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<ECart> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(ECart.class, ECart.ID + "<>'0' ORDER BY " + ECart.ECARTTIME + " DESC");
                        } else {
                            records = SQLTable.list(ECart.class, conditions);
                        }
                        /*
                        FilteredList<ECart> filteredRecords = FXTable.setFilteredList(ecartsTbl, records);
                        ECart.createTableFilter(ecartsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends ECart> c) -> {
                            ecartsresultLbl.setText("Results : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(ecartsTbl, records);
                            ecartsresultLbl.setText("Results : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            ecartsTbl.setPlaceholder(null);
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
                            FXTable.setFilteredList(returnsTbl, new ArrayList());
                            returnsTbl.setPlaceholder(new MaskerPane());
                        });
                        Thread.sleep(400);
                        List<ReturnedItem> records;
                        if (conditions == null || conditions.isEmpty()) {
                            records = SQLTable.list(ReturnedItem.class, ReturnedItem.ID + "<>'0' ORDER BY " + ReturnedItem.RETURNTIME + " DESC");
                        } else {
                            records = SQLTable.list(ReturnedItem.class, conditions);
                        }
                        /*
                        FilteredList<ReturnedItem> filteredRecords = FXTable.setFilteredList(returnsTbl, records);
                        ReturnedItem.createTableFilter(returnsearchF, filteredRecords);
                        filteredRecords.addListener((ListChangeListener.Change<? extends ReturnedItem> c) -> {
                            returnsresultLbl.setText("Results : " + filteredRecords.size());
                        });
                        */
                        Platform.runLater(() -> {
                            FXTable.setList(returnsTbl, records);
                            returnsresultLbl.setText("Results : " + records.size());
                        });
                    } catch (Exception er) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
                    } finally {
                        Platform.runLater(() -> {
                            returnsTbl.setPlaceholder(null);
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
    private void loadPharmacyItemFilters() {
        try {
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadPharmacyItemList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Item> items = itemsTbl.getItems();
                if(items.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("items.xlsx");
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
                                    ExcelManager.export(Item.class, items, sFile);
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
            FXTable.addCustomTableMenu(itemsTbl, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSupplyItemFilters() {
        try {            
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadSupplyItemList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Item> items = suppliesTbl.getItems();
                if(items.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("supplies.xlsx");
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
                                    ExcelManager.export(Item.class, items, sFile);
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
            FXTable.addCustomTableMenu(suppliesTbl, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadSupplierFilters() {
        try {
            GlyphIcon clearIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.REFRESH).size("13px").build();
            Label clearLb = new Label("Clear Filters");
            clearLb.setCursor(Cursor.HAND);
            clearLb.setGraphic(clearIcon);
            clearLb.setOnMouseClicked(evt -> {
                loadSuppliersList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<Supplier> records = suppliersTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("suppliers.xlsx");
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
                                    ExcelManager.export(Supplier.class, records, sFile);
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
            FXTable.addCustomTableMenu(suppliersTbl, clearIcon, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    private void loadDeliveryFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Items");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(600);
                    content.setAlignment(Pos.CENTER);
                    content.setSpacing(35);
                    content.setPadding(new Insets(35, 25, 25, 25));

                    JFXDatePicker dfrom = new JFXDatePicker();
                    dfrom.setPromptText("Date From");
                    dfrom.setMinHeight(28);
                    dfrom.setMinWidth(250);
                    dfrom.setMaxWidth(250);
                    dfrom.setPrefWidth(250);

                    JFXDatePicker dto = new JFXDatePicker();
                    dto.setPromptText("Date To");
                    dto.setMinHeight(28);
                    dto.setMinWidth(250);
                    dto.setMaxWidth(250);
                    dto.setPrefWidth(250);

                    Label warning = new Label("Invalid Date Range!");
                    warning.setTextFill(Color.RED);
                    warning.setMinHeight(28);
                    warning.setMinWidth(250);
                    warning.setMaxWidth(250);
                    warning.setPrefWidth(250);
                    warning.setVisible(false);

                    content.getChildren().addAll(dfrom, dto, warning);

                    JFXButton filter = new JFXButton("Filter");
                    filter.getStyleClass().add("btn-info");

                    JFXDialog dialog = FXDialog.showConfirmDialog(mainStack, "Filter by Date", content, FXDialog.DANGER, filter);
                    filter.setOnAction(filterEvt -> {
                        LocalDate d1 = dfrom.getValue();
                        LocalDate d2 = dto.getValue();

                        d1 = (d1 == null) ? LocalDate.now() : d1;
                        d2 = (d2 == null) ? LocalDate.now() : d2;

                        if (d1.isBefore(d2)) {
                            dialog.close();
                            java.sql.Date sqT1 = java.sql.Date.valueOf(d1);
                            java.sql.Date sqT2 = java.sql.Date.valueOf(d2);
                            loadDeliveryList(DeliveryItem.DELIVERYDATE + ">='" + sqT1 + "' AND " + DeliveryItem.DELIVERYDATE + "<='" + sqT2 + "' ORDER BY " + DeliveryItem.DELIVERYDATE + " DESC");
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
                loadDeliveryList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<DeliveryItem> records = deliveriesTbl.getItems();
                if(records.size() > 0){
                    FileChooser fileChooser = new FileChooser();
            
                    fileChooser.setInitialFileName("deliveryitems.xlsx");
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
                                    ExcelManager.export(DeliveryItem.class, records, sFile);
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
            FXTable.addCustomTableMenu(deliveriesTbl, filterLb, clearLb, exportLb);
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
                    content.setMaxHeight(600);
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
                    filter.setOnAction(filterEvt -> {
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
                            loadECartList(ECart.ECARTTIME + ">='" + sqT1 + "' AND " + ECart.ECARTTIME + "<='" + sqT2 + "' ORDER BY " + ECart.ECARTTIME + " DESC");
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
                loadECartList(null);
            });
            GlyphIcon saveIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILE_EXCEL_ALT).size("13px").build();
            Label exportLb = new Label("Save to Excel");
            exportLb.setCursor(Cursor.HAND);
            exportLb.setGraphic(saveIcon);
            exportLb.setOnMouseClicked(evt -> {
                List<ECart> records = ecartsTbl.getItems();
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
            FXTable.addCustomTableMenu(ecartsTbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    private void loadReturnsFilters() {
        try {
            GlyphIcon filterIcon = GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.FILTER).size("13px").build();
            Label filterLb = new Label("Filter Items");
            filterLb.setCursor(Cursor.HAND);
            filterLb.setGraphic(filterIcon);
            filterLb.setOnMouseClicked(evt -> {
                try {
                    VBox content = new VBox();
                    content.setMaxWidth(500);
                    content.setMaxHeight(600);
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
                    filter.setOnAction(filterEvt -> {
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
                            loadReturnedItemList(ReturnedItem.RETURNTIME + ">='" + sqT1 + "' AND " + ReturnedItem.RETURNTIME + "<='" + sqT2 + "' ORDER BY " + ReturnedItem.RETURNTIME + " DESC");
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
                List<ReturnedItem> records = returnsTbl.getItems();
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
            FXTable.addCustomTableMenu(returnsTbl, filterLb, clearLb, exportLb);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        loadPharmacyItemFilters();
        loadSupplyItemFilters();
        loadSupplierFilters();
        loadDeliveryFilters();
        loadECartFilters();
        loadReturnsFilters();
        pharmacyMenu.fire();
    }
}
