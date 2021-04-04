package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class PatientBenefit extends SQLModel<PatientBenefit>{
        
    public static final String TABLE_NAME = "patientbenefits";
    public static final String JOIN_KEY = "patientbenefit_id";
    
    public static final String BILLNUMBER = "billnumber";
    public static final String PATIENT = "patient";
    public static final String BENEFITSOURCE = "benefitsource";
    public static final String BENEFITCODE = "benefitcode";
    public static final String DESCRIPTION = "description";
    public static final String AMOUNT = "amount";
    public static final String COLLECTIONREFERRENCE = "collectionreferrence";
    public static final String COLLECTIONDATE = "collectiondate";    
    public static final String BILLSTATEMENT_ID = BillStatement.JOIN_KEY;
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String CREATEDBY = "createdby";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    
    @Dup
    @Col(name = BILLNUMBER)
    private final StringProperty billnumber = new SimpleStringProperty("");
    @Dup
    @Col(name = PATIENT)
    private final StringProperty patient = new SimpleStringProperty("");
    @Dup
    @Col(name = BENEFITSOURCE)
    private final StringProperty benefitsource = new SimpleStringProperty("");
    @Dup
    @Col(name = BENEFITCODE)
    private final StringProperty benefitcode = new SimpleStringProperty("");
    @Dup
    @Col(name = DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name = AMOUNT,Class = double.class)
    private final DoubleProperty amount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name = COLLECTIONREFERRENCE)
    private final StringProperty collectionreferrence = new SimpleStringProperty("");
    @Dup(Class = LocalDate.class)
    @Col(name = COLLECTIONDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> collectiondate = new SimpleObjectProperty<>(null);
    @Dup(Class = int.class)
    @Col(name = BILLSTATEMENT_ID,Class = int.class)
    private final IntegerProperty billstatement_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = CREATEDBY)
    private final StringProperty createdby = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CREATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> created_at = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = UPDATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> updated_at = new SimpleObjectProperty<>(null);

    public LocalDateTime getUpdated_at() {
        return updated_at.get();
    }

    public void setUpdated_at(LocalDateTime value) {
        updated_at.set(value);
    }

    public ObjectProperty updated_atProperty() {
        return updated_at;
    }
    

    public LocalDateTime getCreated_at() {
        return created_at.get();
    }

    public void setCreated_at(LocalDateTime value) {
        created_at.set(value);
    }

    public ObjectProperty created_atProperty() {
        return created_at;
    }
    

    public String getCreatedby() {
        return createdby.get();
    }

    public void setCreatedby(String value) {
        createdby.set(value);
    }

    public StringProperty createdbyProperty() {
        return createdby;
    }
    

    public int getPatient_id() {
        return patient_id.get();
    }

    public void setPatient_id(int value) {
        patient_id.set(value);
    }

    public IntegerProperty patient_idProperty() {
        return patient_id;
    }
    

    public int getBillstatement_id() {
        return billstatement_id.get();
    }

    public void setBillstatement_id(int value) {
        billstatement_id.set(value);
    }

    public IntegerProperty billstatement_idProperty() {
        return billstatement_id;
    }
    
    

    public LocalDate getCollectiondate() {
        return collectiondate.get();
    }

    public void setCollectiondate(LocalDate value) {
        collectiondate.set(value);
    }

    public ObjectProperty collectiondateProperty() {
        return collectiondate;
    }
    

    public String getCollectionreferrence() {
        return collectionreferrence.get();
    }

    public void setCollectionreferrence(String value) {
        collectionreferrence.set(value);
    }

    public StringProperty collectionreferrenceProperty() {
        return collectionreferrence;
    }
    
    

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double value) {
        amount.set(value);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
    

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
    

    public String getBenefitcode() {
        return benefitcode.get();
    }

    public void setBenefitcode(String value) {
        benefitcode.set(value);
    }

    public StringProperty benefitcodeProperty() {
        return benefitcode;
    }
    

    public String getBenefitsource() {
        return benefitsource.get();
    }

    public void setBenefitsource(String value) {
        benefitsource.set(value);
    }

    public StringProperty benefitsourceProperty() {
        return benefitsource;
    }
    

    public String getPatient() {
        return patient.get();
    }

    public void setPatient(String value) {
        patient.set(value);
    }

    public StringProperty patientProperty() {
        return patient;
    }
    

    public String getBillnumber() {
        return billnumber.get();
    }

    public void setBillnumber(String value) {
        billnumber.set(value);
    }

    public StringProperty billnumberProperty() {
        return billnumber;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<PatientBenefit> filteredRecords ){
        try{
            ObjectProperty<Predicate<PatientBenefit>> billFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<PatientBenefit>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<PatientBenefit>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<PatientBenefit>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<PatientBenefit>> sourceFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<PatientBenefit>> createdbyFilter = new SimpleObjectProperty<>();
            
            billFilter.bind(Bindings.createObjectBinding(()-> record -> record.getBillnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatient().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getBenefitcode().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            sourceFilter.bind(Bindings.createObjectBinding(()-> record -> record.getBenefitsource().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            createdbyFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCreatedby().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(billFilter.get()).or(codeFilter.get()).or(sourceFilter.get()).or(createdbyFilter.get()),nameFilter, desFilter,billFilter,codeFilter,sourceFilter,createdbyFilter));
        }catch(Exception er){
            Logger.getLogger(PatientBenefit.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
