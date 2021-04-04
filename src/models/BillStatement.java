package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class BillStatement extends SQLModel<BillStatement>{    
    
    public static final String TABLE_NAME = "billstatements";
    public static final String JOIN_KEY = "billstatement_id";
    
    public static final String BILLNUMBER = "billnumber"; 
    public static final String BILLTIME = "billtime";
    public static final String HOSPITALIZATIONPLAN = "hospitalizationplan";
    public static final String PATIENTNAME = "patientname";
    public static final String AGE = "age";
    public static final String AGESTRING = "agestring";
    public static final String GENDER = "gender";
    public static final String FINALDIAGNOSIS = "finaldiagnosis";
    public static final String OTHERDIAGNOSIS = "otherdiagnosis";
    public static final String CHARGEDTO = "chargedto";
    public static final String INVOICENUMBER = "invoicenumber";
    public static final String ORNUMBER = "ornumber";
    public static final String TOTALGROSS = "totalgross";
    public static final String VATSALES = "vatsales";
    public static final String NONVATSALES = "nonvatsales";
    public static final String ZERORATEDSALES = "zeroratedsales";
    public static final String INPUTVAT = "inputvat";
    public static final String LESSVAT = "lessvat";
    public static final String DISCOUNTS = "discounts";
    public static final String NETSALES = "netsales";
    public static final String PAIDAMOUNT = "paidamount";
    public static final String CASHIER = "cashier";
    public static final String USER = "user";
    public static final String CANCELLED = "cancelled";
    public static final String CANCELTIME = "canceltime";
    public static final String FINALIZED = "finalized";
    public static final String FINALIZEDTIME = "finalizedtime";
    public static final String FIRSTCASERATE = "firstcaserate";
    public static final String SECONDCASERATE = "secondcaserate";
    public static final String ADMISSIONTIME = "admissiontime";
    public static final String DISCHARGEDTIME = "dischargedtime";
    public static final String USER_ID = User.JOIN_KEY;
    public static final String PATIENT_ID = Patient.JOIN_KEY;    
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    public static final String CREATEDBY = "createdby";
    public static final String PHILHEALTHSTAFF = "philhealthstaff";
    public static final String CASHIERSTAFF = "cashierstaff";
    public static final String REVIEWEDBY = "reviewedby";
    public static final String NOTEDBY = "notedby";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    
    
    @Dup
    @Col(name = BILLNUMBER)
    private final StringProperty billnumber = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = BILLTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> billtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = HOSPITALIZATIONPLAN)
    private final StringProperty hospitalizationplan = new SimpleStringProperty("");
    @Dup
    @Col(name = PATIENTNAME)
    private final StringProperty patientname = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = AGE,Class = int.class)
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = AGESTRING)
    private final StringProperty agestring = new SimpleStringProperty("");
    @Dup
    @Col(name = GENDER)
    private final StringProperty gender = new SimpleStringProperty("");
    @Dup
    @Col(name = FINALDIAGNOSIS)
    private final StringProperty finaldiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = OTHERDIAGNOSIS)
    private final StringProperty otherdiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = CHARGEDTO)
    private final StringProperty chargedto = new SimpleStringProperty("");
    @Dup
    @Col(name = INVOICENUMBER)
    private final StringProperty invoicenumber = new SimpleStringProperty("");
    @Dup
    @Col(name = ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name = TOTALGROSS,Class = double.class)
    private final DoubleProperty totalgross = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = VATSALES,Class = double.class)
    private final DoubleProperty vatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = NONVATSALES,Class = double.class)
    private final DoubleProperty nonvatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = ZERORATEDSALES,Class = double.class)
    private final DoubleProperty zeroratedsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = INPUTVAT,Class = double.class)
    private final DoubleProperty inputvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = LESSVAT,Class = double.class)
    private final DoubleProperty lessvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = DISCOUNTS,Class = double.class)
    private final DoubleProperty discounts = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = NETSALES,Class = double.class)
    private final DoubleProperty netsales = new SimpleDoubleProperty(0);    
    @Dup(Class = double.class)
    @Col(name = PAIDAMOUNT,Class = double.class)
    private final DoubleProperty paidamount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name = CASHIER)
    private final StringProperty cashier = new SimpleStringProperty("");
    @Dup
    @Col(name = USER)
    private final StringProperty user = new SimpleStringProperty("");
    @Dup
    @Col(name = CANCELLED)
    private final StringProperty cancelled = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CANCELTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> canceltime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = FINALIZED)
    private final StringProperty finalized = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = FINALIZEDTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> finalizedtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = FIRSTCASERATE)
    private final StringProperty firstcaserate = new SimpleStringProperty("");
    @Dup
    @Col(name = SECONDCASERATE)
    private final StringProperty secondcaserate = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = ADMISSIONTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> admissiontime = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = DISCHARGEDTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> dischargedtime = new SimpleObjectProperty<>(null);
    @Dup(Class = int.class)
    @Col(name = USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = ADMISSION_ID,Class = int.class)
    private final IntegerProperty admission_id = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = CREATEDBY)
    private final StringProperty createdby = new SimpleStringProperty("");
    @Dup
    @Col(name = PHILHEALTHSTAFF)
    private final StringProperty philhealthstaff = new SimpleStringProperty("");
    @Dup
    @Col(name = CASHIERSTAFF)
    private final StringProperty cashierstaff = new SimpleStringProperty("");
    @Dup
    @Col(name = REVIEWEDBY)
    private final StringProperty reviewedby = new SimpleStringProperty("");
    @Dup
    @Col(name = NOTEDBY)
    private final StringProperty notedby = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CREATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> created_at = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = UPDATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> updated_at = new SimpleObjectProperty<>(null);

    
    private List<BillStatementItem> items = new ArrayList();

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<BillStatementItem> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<BillStatementItem> items) {
        this.items = items;
    }

    
    public String getNotedby() {
        return notedby.get();
    }

    public void setNotedby(String value) {
        notedby.set(value);
    }

    public StringProperty notedbyProperty() {
        return notedby;
    }
    

    public String getReviewedby() {
        return reviewedby.get();
    }

    public void setReviewedby(String value) {
        reviewedby.set(value);
    }

    public StringProperty reviewedbyProperty() {
        return reviewedby;
    }
    

    public String getCashierstaff() {
        return cashierstaff.get();
    }

    public void setCashierstaff(String value) {
        cashierstaff.set(value);
    }

    public StringProperty cashierstaffProperty() {
        return cashierstaff;
    }
    

    public String getPhilhealthstaff() {
        return philhealthstaff.get();
    }

    public void setPhilhealthstaff(String value) {
        philhealthstaff.set(value);
    }

    public StringProperty philhealthstaffProperty() {
        return philhealthstaff;
    }
    

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
    

    public int getAdmission_id() {
        return admission_id.get();
    }

    public void setAdmission_id(int value) {
        admission_id.set(value);
    }

    public IntegerProperty admission_idProperty() {
        return admission_id;
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
    

    public int getUser_id() {
        return user_id.get();
    }

    public void setUser_id(int value) {
        user_id.set(value);
    }

    public IntegerProperty user_idProperty() {
        return user_id;
    }
    

    public LocalDateTime getDischargedtime() {
        return dischargedtime.get();
    }

    public void setDischargedtime(LocalDateTime value) {
        dischargedtime.set(value);
    }

    public ObjectProperty dischargedtimeProperty() {
        return dischargedtime;
    }
    

    public LocalDateTime getAdmissiontime() {
        return admissiontime.get();
    }

    public void setAdmissiontime(LocalDateTime value) {
        admissiontime.set(value);
    }

    public ObjectProperty admissiontimeProperty() {
        return admissiontime;
    }
    
    

    public String getSecondcaserate() {
        return secondcaserate.get();
    }

    public void setSecondcaserate(String value) {
        secondcaserate.set(value);
    }

    public StringProperty secondcaserateProperty() {
        return secondcaserate;
    }
    

    public String getFirstcaserate() {
        return firstcaserate.get();
    }

    public void setFirstcaserate(String value) {
        firstcaserate.set(value);
    }

    public StringProperty firstcaserateProperty() {
        return firstcaserate;
    }
    

    public LocalDateTime getFinalizedtime() {
        return finalizedtime.get();
    }

    public void setFinalizedtime(LocalDateTime value) {
        finalizedtime.set(value);
    }

    public ObjectProperty finalizedtimeProperty() {
        return finalizedtime;
    }
    

    public String getFinalized() {
        return finalized.get();
    }

    public void setFinalized(String value) {
        finalized.set(value);
    }

    public StringProperty finalizedProperty() {
        return finalized;
    }
    

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
    
    

    public String getUser() {
        return user.get();
    }

    public void setUser(String value) {
        user.set(value);
    }

    public StringProperty userProperty() {
        return user;
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
    

    public double getPaidamount() {
        return paidamount.get();
    }

    public void setPaidamount(double value) {
        paidamount.set(value);
    }

    public DoubleProperty paidamountProperty() {
        return paidamount;
    }
    

    public double getNetsales() {
        return netsales.get();
    }

    public void setNetsales(double value) {
        netsales.set(value);
    }

    public DoubleProperty netsalesProperty() {
        return netsales;
    }
    

    public double getDiscounts() {
        return discounts.get();
    }

    public void setDiscounts(double value) {
        discounts.set(value);
    }

    public DoubleProperty discountsProperty() {
        return discounts;
    }
    

    public double getLessvat() {
        return lessvat.get();
    }

    public void setLessvat(double value) {
        lessvat.set(value);
    }

    public DoubleProperty lessvatProperty() {
        return lessvat;
    }
    

    public double getInputvat() {
        return inputvat.get();
    }

    public void setInputvat(double value) {
        inputvat.set(value);
    }

    public DoubleProperty inputvatProperty() {
        return inputvat;
    }
    

    public double getZeroratedsales() {
        return zeroratedsales.get();
    }

    public void setZeroratedsales(double value) {
        zeroratedsales.set(value);
    }

    public DoubleProperty zeroratedsalesProperty() {
        return zeroratedsales;
    }
    
    
    public double getNonvatsales() {
        return nonvatsales.get();
    }

    public void setNonvatsales(double value) {
        nonvatsales.set(value);
    }

    public DoubleProperty nonvatsalesProperty() {
        return nonvatsales;
    }
    
    

    public double getVatsales() {
        return vatsales.get();
    }

    public void setVatsales(double value) {
        vatsales.set(value);
    }

    public DoubleProperty vatsalesProperty() {
        return vatsales;
    }
    

    public double getTotalgross() {
        return totalgross.get();
    }

    public void setTotalgross(double value) {
        totalgross.set(value);
    }

    public DoubleProperty totalgrossProperty() {
        return totalgross;
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
    

    public String getInvoicenumber() {
        return invoicenumber.get();
    }

    public void setInvoicenumber(String value) {
        invoicenumber.set(value);
    }

    public StringProperty invoicenumberProperty() {
        return invoicenumber;
    }
    

    public String getChargedto() {
        return chargedto.get();
    }

    public void setChargedto(String value) {
        chargedto.set(value);
    }

    public StringProperty chargedtoProperty() {
        return chargedto;
    }
    

    public String getOtherdiagnosis() {
        return otherdiagnosis.get();
    }

    public void setOtherdiagnosis(String value) {
        otherdiagnosis.set(value);
    }

    public StringProperty otherdiagnosisProperty() {
        return otherdiagnosis;
    }
    

    public String getFinaldiagnosis() {
        return finaldiagnosis.get();
    }

    public void setFinaldiagnosis(String value) {
        finaldiagnosis.set(value);
    }

    public StringProperty finaldiagnosisProperty() {
        return finaldiagnosis;
    }
    

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }
    

    public String getAgestring() {
        return agestring.get();
    }

    public void setAgestring(String value) {
        agestring.set(value);
    }

    public StringProperty agestringProperty() {
        return agestring;
    }
    

    public int getAge() {
        return age.get();
    }

    public void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    

    public String getHospitalizationplan() {
        return hospitalizationplan.get();
    }

    public void setHospitalizationplan(String value) {
        hospitalizationplan.set(value);
    }

    public StringProperty hospitalizationplanProperty() {
        return hospitalizationplan;
    }
    
    public String getPatientname() {
        return patientname.get();
    }

    public void setPatientname(String value) {
        patientname.set(value);
    }

    public StringProperty patientnameProperty() {
        return patientname;
    }
    

    public LocalDateTime getBilltime() {
        return billtime.get();
    }

    public void setBilltime(LocalDateTime value) {
        billtime.set(value);
    }

    public ObjectProperty billtimeProperty() {
        return billtime;
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
    
    public void calculateNet(){
        this.setNetsales((vatsales.get()+nonvatsales.get()+zeroratedsales.get()+inputvat.get())-discounts.get());
    }
    
    public void calculateNet(List<BillStatementItem> items){
        try{
            double total = 0;
            double vsales = 0;
            double nvsales = 0;
            double zrsales = 0;
            double ivat = 0;
            double lvat = 0;
            
            double adiscounts = 0;
            
            for(BillStatementItem item:items){
                total+= item.getAmount();
                vsales+= item.getVatsales();
                nvsales+= item.getNonvatsales();
                zrsales+= item.getZeroratedsales();
                ivat+= item.getInputvat();
                lvat+= item.getLessvat();
                
                adiscounts+= item.getScdiscount()+item.getPwddiscount()+item.getEmpdiscount()+item.getOtdiscount();         
            }
            
            this.setTotalgross(total);
            this.setVatsales(vsales);
            this.setNonvatsales(nvsales);
            this.setZeroratedsales(zrsales);
            this.setInputvat(ivat);
            this.setLessvat(lvat);
            this.setDiscounts(adiscounts);
            
            double net = (this.getVatsales()+this.getNonvatsales()+this.getZeroratedsales()+this.getInputvat())-this.getDiscounts();
            this.setNetsales(net);
        }catch(Exception er){
            Logger.getLogger(BillStatement.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public static void createTableFilter(JFXTextField field,FilteredList<BillStatement> filteredRecords ){
        try{
            ObjectProperty<Predicate<BillStatement>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> hospitalizationFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> invFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> orFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<BillStatement>> cashierFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getBillnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatientname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            hospitalizationFilter.bind(Bindings.createObjectBinding(()-> record -> record.getHospitalizationplan().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            invFilter.bind(Bindings.createObjectBinding(()-> record -> record.getInvoicenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            orFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOrnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            cashierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCashier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(hospitalizationFilter.get()).or(codeFilter.get()).or(invFilter.get()).or(orFilter.get()).or(userFilter.get()).or(cashierFilter.get()),nameFilter, hospitalizationFilter,codeFilter,invFilter,orFilter,userFilter,cashierFilter));
        }catch(Exception er){
            Logger.getLogger(BillStatement.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
