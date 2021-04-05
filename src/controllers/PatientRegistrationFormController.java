package controllers;

import alpha.Care;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import models.CityMunicipality;
import models.ContactPerson;
import models.Patient;
import models.StateProvince;
import nse.dcfx.controls.FXDialog;
import nse.dcfx.controls.FXDialogTask;
import nse.dcfx.controls.FXField;
import nse.dcfx.controls.FXTask;
import nse.dcfx.controls.FormController;
import nse.dcfx.controls.UIController;
import nse.dcfx.models.GlobalOption;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.similarity.StringMatcher;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.StringKit;
import nse.dcfx.validators.RegexPatterns;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class PatientRegistrationFormController implements Initializable, FormController<Patient> {

    private static UIController UI_CONTROLLER = null;
    private static JFXDialog dialog = null;
    private static StackPane stackPane = null;
    private Patient record = null;

    @FXML
    private JFXTabPane mainTab;

    @FXML
    private Tab infoTab;

    @FXML
    private JFXTextField lastnameF;

    @FXML
    private JFXTextField firstnameF;

    @FXML
    private JFXTextField middlenameF;

    @FXML
    private JFXDatePicker bdateF;

    @FXML
    private JFXRadioButton maleF;

    @FXML
    private JFXRadioButton femaleF;

    @FXML
    private JFXComboBox<String> civilF;

    @FXML
    private JFXButton bdateBtn;

    @FXML
    private Tab addressTab;

    @FXML
    private JFXComboBox<String> provinceF;

    @FXML
    private JFXComboBox<String> municipalityF;

    @FXML
    private JFXTextField addressF;

    @FXML
    private JFXTextField mobileF;

    @FXML
    private JFXTextField landlineF;

    @FXML
    private JFXTextField emailF;

    @FXML
    private Tab contactsTab;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton prevBtn;

    @FXML
    private JFXButton nextBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXToggleNode infoMenu;

    @FXML
    private JFXToggleNode addressMenu;

    @FXML
    private JFXToggleNode contactsMenu;

    @FXML
    private ToggleGroup genderGroup;
    
    @FXML
    private ToggleGroup menuGroup;

    @FXML
    private JFXTextField cpname1F;

    @FXML
    private JFXTextField cpnum1F;

    @FXML
    private JFXTextField cprel1F;

    @FXML
    private JFXTextField cpname2F;

    @FXML
    private JFXTextField cpnum2F;

    @FXML
    private JFXTextField cprel2F;

    @FXML
    private JFXTextField cpname3F;

    @FXML
    private JFXTextField cpnum3F;

    @FXML
    private JFXTextField cprel3F;

    private final FXTask saveTask = new FXTask() {
        @Override
        protected void load() {
            try{
                Platform.runLater(()->{
                    closeBtn.setDisable(true);
                    saveBtn.setDisable(true);
                });       
                record.setCode(StringKit.timeCode(LocalDateTime.now()));
                if(!cpname1F.getText().isEmpty()){
                    ContactPerson cp1 = new ContactPerson();
                    cp1.setName(cpname1F.getText());
                    cp1.setMobile(cpnum1F.getText());
                    cp1.setRelation(cprel1F.getText());
                    cp1.save();
                }
                
                if(!cpname2F.getText().isEmpty()){
                    ContactPerson cp1 = new ContactPerson();
                    cp1.setName(cpname2F.getText());
                    cp1.setMobile(cpnum2F.getText());
                    cp1.setRelation(cprel2F.getText());
                    cp1.save();
                }
                if(!cpname3F.getText().isEmpty()){
                    ContactPerson cp1 = new ContactPerson();
                    cp1.setName(cpname3F.getText());
                    cp1.setMobile(cpnum3F.getText());
                    cp1.setRelation(cprel3F.getText());
                    cp1.save();
                }
                if(record.save(true) > 0){
                    Platform.runLater(()->{
                        dialog.close();
                        FXDialog.showMessageDialog(stackPane, "Successfull", record.getFullname()+" has been registered!", FXDialog.SUCCESS);
                        postAction();                        
                    });
                }else{
                    Platform.runLater(()->{
                        FXDialog.showMessageDialog(stackPane, "Failure", "There has been a problem on server communication!", FXDialog.DANGER);                      
                    });                    
                }
            }catch(Exception er){
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            }finally{
                Platform.runLater(()->{
                    saveBtn.setDisable(false);
                    closeBtn.setDisable(false);
                });
            }
        }
    };
    
    

    @FXML
    void formClose(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void formSave(ActionEvent event) {
        System.out.println(record.getDebugInfo());
        try {
            boolean ok = false;
            if (mainTab.getSelectionModel().getSelectedItem() == infoTab) {
                ok = infoValidated();
            } else if (mainTab.getSelectionModel().getSelectedItem() == addressTab) {
                ok = addressValidated();
            } else if (mainTab.getSelectionModel().getSelectedItem() == contactsTab) {
                ok = contactValidated();
            }
            if (ok) {
                List<Patient> sims = SQLTable.list(Patient.class, Patient.LASTNAME + " LIKE '%" + record.getLastname() + "%'");
                List<Patient> similars = new ArrayList();
                sims.stream().forEach(pat -> {
                    if (StringMatcher.similarityCount(pat.getFullname().toLowerCase(), record.getFullname().toLowerCase()) >= 92.5) {
                        similars.add(pat.getModelClone());
                    }
                });

                if (similars.size() > 0) {
                    VBox content = new VBox();
                    HBox l1 = new HBox();
                    l1.setPrefHeight(30);
                    HBox l2 = new HBox();
                    l2.setPrefHeight(30);
                    Label n1 = new Label("Name ");
                    Label n2 = new Label("Birth ");
                    TextField t1 = new TextField(record.getFullname());
                    TextField t2 = new TextField(DateTimeKit.toProperDate(record.getBirthdate()));
                    
                    n1.setPrefWidth(80);
                    n1.getStyleClass().add("anton-font");
                    t1.setEditable(false);
                    t1.setFocusTraversable(false);
                    t1.setDisable(true);
                    HBox.setHgrow(t1, Priority.ALWAYS);
                    
                    n2.setPrefWidth(80);
                    n2.getStyleClass().add("anton-font");
                    t2.setEditable(false);
                    t2.setFocusTraversable(false);
                    t2.setDisable(true);
                    HBox.setHgrow(t2, Priority.ALWAYS);
                    
                    l1.getChildren().setAll(n1,t1);
                    l2.getChildren().setAll(n2,t2);
                    
                    Label a = new Label("Similar Patients");
                    a.setPadding(new Insets(20,20,20,20));
                    a.setAlignment(Pos.CENTER);
                    a.setTextAlignment(TextAlignment.CENTER);
                    a.getStyleClass().add("anton-font");
                    a.setTextFill(Color.valueOf("#ff4444"));
                    a.setMaxWidth(500);
                    HBox.setHgrow(a, Priority.ALWAYS);
                    
                    content.getChildren().setAll(l1,l2,a);
                    
                    similars.stream().forEach(pat->{
                        content.getChildren().add(new Label("• "+pat.getFullname()+((pat.getBirthdate() != null)? "\t\t"+DateTimeKit.toProperDate(pat.getBirthdate()):"")));
                    });
                    
                    JFXButton confirmBtn = new JFXButton("Confirm & Register");
                    confirmBtn.getStyleClass().add("btn-success");
                    
                    JFXDialog promptD = FXDialog.showConfirmDialog(stackPane, "Patient Registration Confirmation", content, FXDialog.WARNING,confirmBtn);
                    confirmBtn.setOnAction(evt->{
                        promptD.close();
                        Care.process(saveTask);
                    });
                } else {
                    Care.process(saveTask);
                }
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @FXML
    void loadAddress(ActionEvent event) {
        mainTab.getSelectionModel().select(addressTab);
    }

    @FXML
    void loadContacts(ActionEvent event) {
        mainTab.getSelectionModel().select(contactsTab);
    }

    @FXML
    void loadInfo(ActionEvent event) {
        mainTab.getSelectionModel().select(infoTab);
    }

    @FXML
    void loadNext(ActionEvent event) {
        if (mainTab.getSelectionModel().getSelectedItem() == infoTab) {
            if (infoValidated()) {
                mainTab.getSelectionModel().selectNext();
                addressMenu.setSelected(true);
                addressMenu.setDisable(false);
            }
        } else if (mainTab.getSelectionModel().getSelectedItem() == addressTab) {
            if (addressValidated()) {
                mainTab.getSelectionModel().selectNext();
                contactsMenu.setSelected(true);
                contactsMenu.setDisable(false);
            }
        }

    }

    @FXML
    void loadPrevious(ActionEvent event) {
        if (mainTab.getSelectionModel().getSelectedItem() == contactsTab) {
            if (contactValidated()) {
                mainTab.getSelectionModel().selectPrevious();                
                addressMenu.setSelected(true);
            }
        } else if (mainTab.getSelectionModel().getSelectedItem() == addressTab) {
            if (addressValidated()) {
                mainTab.getSelectionModel().selectPrevious();                
                infoMenu.setSelected(true);
            }
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
        try {
            if (UI_CONTROLLER instanceof PatientRecordsUIController) {
                UI_CONTROLLER.reloadReferences(0);
            }
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void setUIController(UIController controller) {
        UI_CONTROLLER = controller;
    }

    @Override
    public void setFormObject(Patient obj) {
        this.record = obj;
    }

    @Override
    public Patient getFormObject() {
        return record;
    }

    @Override
    public boolean isFieldInputsValid() {
        return true;
    }

    @Override
    public void loadBindings() {
        try {
            lastnameF.textProperty().bindBidirectional(record.lastnameProperty());
            firstnameF.textProperty().bindBidirectional(record.firstnameProperty());
            middlenameF.textProperty().bindBidirectional(record.middlenameProperty());
            bdateF.valueProperty().bindBidirectional(record.birthdateProperty());
            civilF.valueProperty().bindBidirectional(record.civilstatusProperty());
            addressF.textProperty().bindBidirectional(record.addressProperty());
            provinceF.valueProperty().bindBidirectional(record.stateprovinceProperty());
            municipalityF.valueProperty().bindBidirectional(record.citymunicipalityProperty());

            mobileF.textProperty().bindBidirectional(record.mobileProperty());
            landlineF.textProperty().bindBidirectional(record.landlineProperty());
            emailF.textProperty().bindBidirectional(record.emailProperty());
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadCustomizations() {
        try {
            provinceF.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && !newVal.isEmpty()) {
                    Platform.runLater(() -> {
                        StateProvince state = (StateProvince) SQLTable.get(StateProvince.class, StateProvince.NAME, newVal);
                        if (state != null) {
                            List<String> cities = SQLTable.distinct(CityMunicipality.class, CityMunicipality.NAME, CityMunicipality.STATEPROVINCE_ID + "=" + state.getId() + " ORDER BY " + CityMunicipality.NAME + " ASC");
                            municipalityF.getItems().setAll(cities);
                            Map<String, String> gOptions = GlobalOption.getMap(GlobalOption.GENERAL_CATEGORY);
                            if (!gOptions.get(Care.FACILITYCITYMUNICIPALITY).isEmpty() && cities.contains(gOptions.get(Care.FACILITYCITYMUNICIPALITY))) {
                                municipalityF.getSelectionModel().select(gOptions.get(Care.FACILITYCITYMUNICIPALITY));
                            } else {
                                municipalityF.getSelectionModel().selectFirst();
                            }
                        }
                        if (mainTab.getSelectionModel().getSelectedItem() == addressTab) {
                            record.setStateprovince(newVal);
                        }
                    });
                }
            });

            municipalityF.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && !newVal.isEmpty()) {
                    Platform.runLater(() -> {
                        if (mainTab.getSelectionModel().getSelectedItem() == addressTab) {
                            record.setCitymunicipality(newVal);
                        }
                    });
                }
            });

            mainTab.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && newVal == infoTab) {
                    prevBtn.setDisable(true);
                } else {
                    prevBtn.setDisable(false);
                }

                if (newVal != null && newVal == contactsTab) {
                    nextBtn.setDisable(true);
                } else {
                    nextBtn.setDisable(false);
                }
            });

            maleF.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    record.setGender("Male");
                } else {
                    record.setGender("Female");
                }
            });

            bdateBtn.setOnAction(evt -> {
                Platform.runLater(() -> {
                    bdateF.show();
                });
            });
            prevBtn.setDisable(true);
            mainTab.getSelectionModel().select(infoTab);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadValidators() {
        try {
            //Info
            FXField.addRequiredValidator(lastnameF);
            FXField.addRequiredValidator(firstnameF);
            FXField.setNameCharactersOnly(lastnameF);
            FXField.setNameCharactersOnly(firstnameF);
            FXField.setNameCharactersOnly(middlenameF);
            //FXField.addRequiredValidator(bdateF);
            FXField.addRequiredValidator(civilF);

            //Address
            FXField.addRequiredValidator(provinceF);
            FXField.addRequiredValidator(municipalityF);

            FXField.setCommonCharactersOnly(addressF);
            FXField.addRegexValidator(mobileF, RegexPatterns.MOBILE_PH_REGEX, "Invalid Number Ex.\"09123456789\"");
            FXField.addContactValidator(landlineF);
            FXField.addEmailValidator(emailF);

            //Contact
            FXField.setCommonCharactersOnly(cpname1F);
            FXField.addRegexValidator(cpnum1F, RegexPatterns.MOBILE_PH_REGEX, "Invalid Number Ex.\"09123456789\"");
            FXField.setCommonCharactersOnly(cprel1F);

            FXField.setCommonCharactersOnly(cpname2F);
            FXField.addRegexValidator(cpnum2F, RegexPatterns.MOBILE_PH_REGEX, "Invalid Number Ex.\"09123456789\"");
            FXField.setCommonCharactersOnly(cprel2F);

            FXField.setCommonCharactersOnly(cpname2F);
            FXField.addRegexValidator(cpnum2F, RegexPatterns.MOBILE_PH_REGEX, "Invalid Number Ex.\"09123456789\"");
            FXField.setCommonCharactersOnly(cprel2F);

            FXField.addTrimOnFocusLost(lastnameF, firstnameF, middlenameF, addressF, mobileF, landlineF, emailF, cpname1F, cpname2F, cpname3F, cprel1F, cprel2F, cprel3F);
            FXField.addFocusValidationListener(lastnameF, firstnameF, civilF, mobileF, landlineF, emailF, cpnum1F, cpnum2F, cpnum3F);
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        try {
            Platform.runLater(() -> {
                List<String> provinces = SQLTable.distinct(StateProvince.class, StateProvince.NAME);
                provinceF.getItems().setAll(provinces);
                Map<String, String> gOptions = GlobalOption.getMap(GlobalOption.GENERAL_CATEGORY);
                if (!gOptions.get(Care.FACILITYSTATEPROVINCE).isEmpty() && provinces.contains(gOptions.get(Care.FACILITYSTATEPROVINCE))) {
                    provinceF.getSelectionModel().select(gOptions.get(Care.FACILITYSTATEPROVINCE));
                } else {
                    provinceF.getSelectionModel().selectFirst();
                }

                civilF.getItems().setAll("Single", "Married", "Widow", "Widower", "Separated");
                civilF.getSelectionModel().selectFirst();

                
                if (record.getGender().equalsIgnoreCase("Male")) {
                    maleF.setSelected(true);
                } else {
                    femaleF.setSelected(true);
                }
            });
        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadUIFixes() {
        try {

        } catch (Exception er) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    public static FXDialogTask showDialog(StackPane stackpane, MaskerPane masker, UIController ui_controller, Node... nodes) {
        if (dialog == null || !dialog.isVisible()) {
            try {
                dialog = new JFXDialog();
                FXMLLoader LOADER = new FXMLLoader(FormController.class.getResource("/views/PatientRegistrationForm.fxml"));
                FXDialogTask task = new FXDialogTask(dialog, new Patient(), ui_controller, 800, 620);
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

    public boolean infoValidated() {
        boolean a = lastnameF.validate() && firstnameF.validate() && middlenameF.validate() && bdateF.validate() && civilF.validate();
        System.out.println("infoValidated : "+a);
        return a;
    }

    public boolean addressValidated() {
        boolean a = provinceF.validate() && municipalityF.validate() && mobileF.validate() && landlineF.validate();
        System.out.println("addressValidated : "+a);
        return a;
    }

    public boolean contactValidated() {
        boolean a = cpnum1F.validate() && cpnum2F.validate() && cpnum3F.validate();
        System.out.println("contactValidated : "+a);
        return a;
    }
}
