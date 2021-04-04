package models;

import com.jfoenix.controls.JFXTextField;
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
public class CashAdvance extends SQLModel<CashAdvance> {
        
    public static final String TABLE_NAME = "cashadvances";
    public static final String JOIN_KEY = "cashadvance_id";
    
    public static final String ACKNOWLEDGEMENTNUM = "acknowledgementnum";
    public static final String PATIENT = "patient";
    public static final String PAYMENTTIME = "paymenttime";
    public static final String AMOUNT = "amount";
    public static final String CASHIER = "cashier";
    public static final String CANCELLED = "cancelled";
    public static final String CANCELTIME = "canceltime";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String CREATED_AT = "created_at";
    
    @Dup
    @Col(name=ACKNOWLEDGEMENTNUM)
    private final StringProperty acknowledgementnum = new SimpleStringProperty("");
    @Dup
    @Col(name=PATIENT)
    private final StringProperty patient = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=PAYMENTTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> paymenttime = new SimpleObjectProperty<>(null);
    @Dup(Class = double.class)
    @Col(name=AMOUNT,Class = double.class)
    private final DoubleProperty amount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=CASHIER)
    private final StringProperty cashier = new SimpleStringProperty("");@Dup
    @Col(name = CANCELLED)
    private final StringProperty cancelled = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CANCELTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> canceltime = new SimpleObjectProperty<>(null);
    @Dup(Class = int.class)
    @Col(name=PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = LocalDateTime.class)
    @Col(name=CREATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> created_at = new SimpleObjectProperty<>(null);

    public LocalDateTime getCanceltime() {
        return canceltime.get();
    }

    public void setCanceltime(LocalDateTime value) {
        canceltime.set(value);
    }

    public ObjectProperty canceltimeProperty() {
        return canceltime;
    }
    

    public String getCancelled() {
        return cancelled.get();
    }

    public void setCancelled(String value) {
        cancelled.set(value);
    }

    public StringProperty cancelledProperty() {
        return cancelled;
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
    

    public int getPatient_id() {
        return patient_id.get();
    }

    public void setPatient_id(int value) {
        patient_id.set(value);
    }

    public IntegerProperty patient_idProperty() {
        return patient_id;
    }
    

    public String getCashier() {
        return cashier.get();
    }

    public void setCashier(String value) {
        cashier.set(value);
    }

    public StringProperty cashierProperty() {
        return cashier;
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
    

    public LocalDateTime getPaymenttime() {
        return paymenttime.get();
    }

    public void setPaymenttime(LocalDateTime value) {
        paymenttime.set(value);
    }

    public ObjectProperty paymenttimeProperty() {
        return paymenttime;
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
    

    public String getAcknowledgementnum() {
        return acknowledgementnum.get();
    }

    public void setAcknowledgementnum(String value) {
        acknowledgementnum.set(value);
    }

    public StringProperty acknowledgementnumProperty() {
        return acknowledgementnum;
    }
    
    
    public static void createTableFilter(JFXTextField field,FilteredList<CashAdvance> filteredRecords ){
        try{
            ObjectProperty<Predicate<CashAdvance>> patientFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<CashAdvance>> ackFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<CashAdvance>> cashierFilter = new SimpleObjectProperty<>();
            
            patientFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatient().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            ackFilter.bind(Bindings.createObjectBinding(()-> record -> record.getAcknowledgementnum().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            cashierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCashier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> ackFilter.get().or(patientFilter.get()).or(cashierFilter.get()),ackFilter,patientFilter,cashierFilter));
        }catch(Exception er){
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
