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
public class Payment extends SQLModel<Payment>{
    
    public static final String TABLE_NAME = "payments";
    public static final String JOIN_KEY = "payment_id";
    
    public static final String PATIENT = "patient";
    public static final String PAYMENTTYPE = "paymenttype";
    public static final String AMOUNT = "amount";
    public static final String PAIDBY = "paidby";
    public static final String PAYMENTTIME = "paymenttime";
    public static final String PAYMENTREFERRENCE = "paymentreferrence";
    public static final String DESCRIPTION = "description";
    public static final String ORNUMBER = "ornumber";
    public static final String INVOICENUMBER = "invoicenumber";
    public static final String CHEQUENUMBER = "chequenumber";
    public static final String CHEQUENDATE = "chequedate";
    public static final String CHEQUEBANK = "chequebank";
    public static final String CASHIER = "cashier";
    public static final String ENCODER = "encoder";
    public static final String CANCELLED = "cancelled";
    public static final String CANCELTIME = "canceltime";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String BILLSTATEMENT_ID = BillStatement.JOIN_KEY;
    public static final String CREATED_AT = "created_at";
    
    @Dup
    @Col(name = PATIENT)
    private final StringProperty patient = new SimpleStringProperty("");
    @Dup
    @Col(name = PAYMENTTYPE)
    private final StringProperty paymenttype = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name = AMOUNT,Class = double.class)
    private final DoubleProperty amount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name = PAIDBY)
    private final StringProperty paidby = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = PAYMENTTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> paymenttime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = PAYMENTREFERRENCE)
    private final StringProperty paymentreferrence = new SimpleStringProperty("");
    @Dup
    @Col(name = DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup
    @Col(name = ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");
    @Dup
    @Col(name = INVOICENUMBER)
    private final StringProperty invoicenumber = new SimpleStringProperty("");
    @Dup
    @Col(name = CHEQUENUMBER)
    private final StringProperty chequenumber = new SimpleStringProperty("");
    @Dup(Class = LocalDate.class)
    @Col(name = CHEQUENDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> chequedate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = CHEQUEBANK)
    private final StringProperty chequebank = new SimpleStringProperty("");
    @Dup
    @Col(name = CASHIER)
    private final StringProperty cashier = new SimpleStringProperty("");
    @Dup
    @Col(name = ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup
    @Col(name = CANCELLED)
    private final StringProperty cancelled = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CANCELTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> canceltime = new SimpleObjectProperty<>(null);
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = BILLSTATEMENT_ID,Class = int.class)
    private final IntegerProperty billstatement_id = new SimpleIntegerProperty(0);

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
    

    public int getBillstatement_id() {
        return billstatement_id.get();
    }

    public void setBillstatement_id(int value) {
        billstatement_id.set(value);
    }

    public IntegerProperty billstatement_idProperty() {
        return billstatement_id;
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
    
    

    public String getEncoder() {
        return encoder.get();
    }

    public void setEncoder(String value) {
        encoder.set(value);
    }

    public StringProperty encoderProperty() {
        return encoder;
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
    

    public String getChequebank() {
        return chequebank.get();
    }

    public void setChequebank(String value) {
        chequebank.set(value);
    }

    public StringProperty chequebankProperty() {
        return chequebank;
    }
    

    public LocalDate getChequedate() {
        return chequedate.get();
    }

    public void setChequedate(LocalDate value) {
        chequedate.set(value);
    }

    public ObjectProperty chequedateProperty() {
        return chequedate;
    }
    

    public String getChequenumber() {
        return chequenumber.get();
    }

    public void setChequenumber(String value) {
        chequenumber.set(value);
    }

    public StringProperty chequenumberProperty() {
        return chequenumber;
    }
    

    public String getInvoicenumber() {
        return invoicenumber.get();
    }

    public void setInvoicenumber(String value) {
        invoicenumber.set(value);
    }

    public StringProperty invoicenumberProperty() {
        return invoicenumber;
    }
    

    public String getOrnumber() {
        return ornumber.get();
    }

    public void setOrnumber(String value) {
        ornumber.set(value);
    }

    public StringProperty ornumberProperty() {
        return ornumber;
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
    

    public String getPaymentreferrence() {
        return paymentreferrence.get();
    }

    public void setPaymentreferrence(String value) {
        paymentreferrence.set(value);
    }

    public StringProperty paymentreferrenceProperty() {
        return paymentreferrence;
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
    

    public String getPaidby() {
        return paidby.get();
    }

    public void setPaidby(String value) {
        paidby.set(value);
    }

    public StringProperty paidbyProperty() {
        return paidby;
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
    

    public String getPaymenttype() {
        return paymenttype.get();
    }

    public void setPaymenttype(String value) {
        paymenttype.set(value);
    }

    public StringProperty paymenttypeProperty() {
        return paymenttype;
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
    
    
    public static void createTableFilter(JFXTextField field,FilteredList<Payment> filteredRecords ){
        try{
            ObjectProperty<Predicate<Payment>> patientFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> paidbyFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> invFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> orFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Payment>> cashierFilter = new SimpleObjectProperty<>();
            
            patientFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatient().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            paidbyFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPaidby().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            invFilter.bind(Bindings.createObjectBinding(()-> record -> record.getInvoicenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            orFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOrnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getEncoder().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            cashierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCashier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> paidbyFilter.get().or(desFilter.get()).or(patientFilter.get()).or(invFilter.get()).or(orFilter.get()).or(userFilter.get()).or(cashierFilter.get()),paidbyFilter, desFilter,patientFilter,invFilter,orFilter,userFilter,cashierFilter));
        }catch(Exception er){
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
