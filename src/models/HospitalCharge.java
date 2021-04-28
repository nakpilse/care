package models;

import com.jfoenix.controls.JFXTextField;
import jasper.JasperFrame;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.NumberKit;
import org.controlsfx.control.MaskerPane;

/**
 *
 * @author Duskmourne
 */
public class HospitalCharge extends SQLModel<HospitalCharge>{
    
    public static final String TABLE_NAME = "hospitalcharges";
    public static final String JOIN_KEY = "hospitalcharge_id";
    
    public static final String CHARGENUMBER = "chargenumber";
    public static final String CHARGETIME = "chargetime";
    public static final String CHARGETO = "chargeto";
    public static final String CHARGENOTES = "chargenotes";
    public static final String INVOICENUMBER = "invoicenumber";
    public static final String BILLNUMBER = "billnumber";
    public static final String ORNUMBER = "ornumber";
    public static final String DESCRIPTION = "description";
    public static final String CHARGEFACILITY = "chargefacility";
    public static final String CHARGETYPE = "chargetype";
    public static final String TOTALGROSS = "totalgross";
    public static final String VATSALES = "vatsales";
    public static final String NONVATSALES = "nonvatsales";
    public static final String ZERORATEDSALES = "zeroratedsales";
    public static final String INPUTVAT = "inputvat";
    public static final String LESSVAT = "lessvat";
    public static final String SCDISCOUNT = "scdiscount";
    public static final String SCID = "scid";
    public static final String PWDDISCOUNT = "pwddiscount";
    public static final String PWDID = "pwdid";
    public static final String CARETO = "careto";
    public static final String EMPDISCOUNT = "empdiscount";
    public static final String EMPID = "empid";
    public static final String OTDISCOUNT = "otdiscount";
    public static final String OTDISCOUNTREMARKS = "otdiscountremarks";
    public static final String NETSALES = "netsales";
    public static final String PAIDAMOUNT = "paidamount";
    public static final String PAYMENTTYPE = "paymenttype";
    public static final String PAYMENTREFERRENCE = "paymentreferrence";
    public static final String PAYMENTTIME = "paymenttime";
    public static final String CASHIER = "cashier";
    public static final String USER = "user";
    public static final String CANCELLED = "cancelled";
    public static final String CANCELTIME = "canceltime";
    public static final String CANCELREMARKS = "cancelremarks";
    public static final String VOIDED = "voided";
    public static final String VOIDTIME = "voidtime";
    public static final String VOIDREMARKS = "voidremarks";
    public static final String CASHIERID = "cashierid";
    public static final String REMARKS = "remarks";
    public static final String NOTES = "notes";
    public static final String RECORDTABLE = "recordtable";
    public static final String RECORDTABLEID = "recordtableid";
    public static final String USER_ID = User.JOIN_KEY;
    public static final String PATIENT_ID = Patient.JOIN_KEY;    
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    public static final String PAYMENT_ID = Payment.JOIN_KEY;
    public static final String OPT0 = "opt0";// Physician
    public static final String OPT1 = "opt1";
    public static final String OPT2 = "opt2";
    public static final String OPT3 = "opt3";
    public static final String OPT4 = "opt4";
    public static final String OPT5 = "opt5";
    public static final String OPT6 = "opt6";
    public static final String OPT7 = "opt7";
    public static final String OPT8 = "opt8";
    public static final String OPT9 = "opt9";
    
    public static double taxPercentage = 12;
    public static double SC_Discount_Percentage = 20;
    public static double PWD_Discount_Percentage = 20;
    
    private List<HospitalChargeItem> items = new ArrayList();
    @Dup(Class = boolean.class)
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    
    @Dup
    @Col(name=CHARGENUMBER)
    private final StringProperty chargenumber = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=CHARGETIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> chargetime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=CHARGETO)
    private final StringProperty chargeto = new SimpleStringProperty("");
    @Dup
    @Col(name=CHARGENOTES)
    private final StringProperty chargenotes = new SimpleStringProperty("");
    @Dup
    @Col(name=INVOICENUMBER)
    private final StringProperty invoicenumber = new SimpleStringProperty("");
    @Dup
    @Col(name=BILLNUMBER)
    private final StringProperty billnumber = new SimpleStringProperty("");
    @Dup
    @Col(name=ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup
    @Col(name=CHARGEFACILITY)
    private final StringProperty chargefacility = new SimpleStringProperty("");
    @Dup
    @Col(name=CHARGETYPE)
    private final StringProperty chargetype = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=TOTALGROSS,Class = double.class)
    private final DoubleProperty totalgross = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=VATSALES,Class = double.class)
    private final DoubleProperty vatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=NONVATSALES,Class = double.class)
    private final DoubleProperty nonvatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=ZERORATEDSALES,Class = double.class)
    private final DoubleProperty zeroratedsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=INPUTVAT,Class = double.class)
    private final DoubleProperty inputvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=LESSVAT,Class = double.class)
    private final DoubleProperty lessvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=SCDISCOUNT,Class = double.class)
    private final DoubleProperty scdiscount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=SCID)
    private final StringProperty scid = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=PWDDISCOUNT,Class = double.class)
    private final DoubleProperty pwddiscount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=PWDID)
    private final StringProperty pwdid = new SimpleStringProperty("");
    @Dup
    @Col(name=CARETO)
    private final StringProperty careto = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=EMPDISCOUNT,Class = double.class)
    private final DoubleProperty empdiscount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=EMPID)
    private final StringProperty empid = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=OTDISCOUNT,Class = double.class)
    private final DoubleProperty otdiscount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=OTDISCOUNTREMARKS)
    private final StringProperty otdiscountremarks = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=NETSALES,Class = double.class)
    private final DoubleProperty netsales = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=PAYMENTREFERRENCE)
    private final StringProperty paymentreferrence = new SimpleStringProperty("");
    @Dup
    @Col(name=PAYMENTTYPE)
    private final StringProperty paymenttype = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=PAYMENTTIME,Class = LocalDateTime.class)    
    private final ObjectProperty<LocalDateTime> paymenttime = new SimpleObjectProperty<>(null);
    @Dup(Class = double.class)
    @Col(name=PAIDAMOUNT,Class = double.class)
    private final DoubleProperty paidamount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=CASHIER)
    private final StringProperty cashier = new SimpleStringProperty("");
    @Dup
    @Col(name=USER)
    private final StringProperty user = new SimpleStringProperty("");
    @Dup
    @Col(name=CANCELLED)
    private final StringProperty cancelled = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=CANCELTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> canceltime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=CANCELREMARKS)
    private final StringProperty cancelremarks = new SimpleStringProperty("");
    @Dup
    @Col(name=VOIDED)
    private final StringProperty voided = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=VOIDTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> voidtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=VOIDREMARKS)
    private final StringProperty voidremarks = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=CASHIERID,Class = int.class)
    private final IntegerProperty cashierid = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=REMARKS)
    private final StringProperty remarks = new SimpleStringProperty("");
    @Dup
    @Col(name=NOTES)
    private final StringProperty notes = new SimpleStringProperty("");
    @Dup
    @Col(name=RECORDTABLE)
    private final StringProperty recordtable = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=RECORDTABLEID,Class = int.class)
    private final IntegerProperty recordtableid = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=ADMISSION_ID,Class = int.class)
    private final IntegerProperty admission_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=PAYMENT_ID,Class = int.class)
    private final IntegerProperty payment_id = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=OPT0)
    private final StringProperty opt0 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT1)
    private final StringProperty opt1 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT2)
    private final StringProperty opt2 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT3)
    private final StringProperty opt3 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT4)
    private final StringProperty opt4 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT5)
    private final StringProperty opt5 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT6)
    private final StringProperty opt6 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT7)
    private final StringProperty opt7 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT8)
    private final StringProperty opt8 = new SimpleStringProperty("");
    @Dup
    @Col(name=OPT9)
    private final StringProperty opt9 = new SimpleStringProperty("");

    public int getPayment_id() {
        return payment_id.get();
    }

    public void setPayment_id(int value) {
        payment_id.set(value);
    }

    public IntegerProperty payment_idProperty() {
        return payment_id;
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
    

    public int getRecordtableid() {
        return recordtableid.get();
    }

    public void setRecordtableid(int value) {
        recordtableid.set(value);
    }

    public IntegerProperty recordtableidProperty() {
        return recordtableid;
    }
    

    public String getRecordtable() {
        return recordtable.get();
    }

    public void setRecordtable(String value) {
        recordtable.set(value);
    }

    public StringProperty recordtableProperty() {
        return recordtable;
    }
    
    
    
    public String getOpt9() {
        return opt9.get();
    }

    public void setOpt9(String value) {
        opt9.set(value);
    }

    public StringProperty opt9Property() {
        return opt9;
    }
    

    public String getOpt8() {
        return opt8.get();
    }

    public void setOpt8(String value) {
        opt8.set(value);
    }

    public StringProperty opt8Property() {
        return opt8;
    }
    

    public String getOpt7() {
        return opt7.get();
    }

    public void setOpt7(String value) {
        opt7.set(value);
    }

    public StringProperty opt7Property() {
        return opt7;
    }
    

    public String getOpt6() {
        return opt6.get();
    }

    public void setOpt6(String value) {
        opt6.set(value);
    }

    public StringProperty opt6Property() {
        return opt6;
    }
    

    public String getOpt5() {
        return opt5.get();
    }

    public void setOpt5(String value) {
        opt5.set(value);
    }

    public StringProperty opt5Property() {
        return opt5;
    }
    

    public String getOpt4() {
        return opt4.get();
    }

    public void setOpt4(String value) {
        opt4.set(value);
    }

    public StringProperty opt4Property() {
        return opt4;
    }
    

    public String getOpt3() {
        return opt3.get();
    }

    public void setOpt3(String value) {
        opt3.set(value);
    }

    public StringProperty opt3Property() {
        return opt3;
    }
    
    

    public String getOpt2() {
        return opt2.get();
    }

    public void setOpt2(String value) {
        opt2.set(value);
    }

    public StringProperty opt2Property() {
        return opt2;
    }
    

    public String getOpt1() {
        return opt1.get();
    }

    public void setOpt1(String value) {
        opt1.set(value);
    }

    public StringProperty opt1Property() {
        return opt1;
    }
    

    public String getOpt0() {
        return opt0.get();
    }

    public void setOpt0(String value) {
        opt0.set(value);
    }

    public StringProperty opt0Property() {
        return opt0;
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
    

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String value) {
        notes.set(value);
    }

    public StringProperty notesProperty() {
        return notes;
    }
    

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String value) {
        remarks.set(value);
    }

    public StringProperty remarksProperty() {
        return remarks;
    }
    

    public int getCashierid() {
        return cashierid.get();
    }

    public void setCashierid(int value) {
        cashierid.set(value);
    }

    public IntegerProperty cashieridProperty() {
        return cashierid;
    }
    

    public String getVoidremarks() {
        return voidremarks.get();
    }

    public void setVoidremarks(String value) {
        voidremarks.set(value);
    }

    public StringProperty voidremarksProperty() {
        return voidremarks;
    }
    

    public LocalDateTime getVoidtime() {
        return voidtime.get();
    }

    public void setVoidtime(LocalDateTime value) {
        voidtime.set(value);
    }

    public ObjectProperty voidtimeProperty() {
        return voidtime;
    }
    

    public String getVoided() {
        return voided.get();
    }

    public void setVoided(String value) {
        voided.set(value);
    }

    public StringProperty voidedProperty() {
        return voided;
    }
    

    public String getCancelremarks() {
        return cancelremarks.get();
    }

    public void setCancelremarks(String value) {
        cancelremarks.set(value);
    }

    public StringProperty cancelremarksProperty() {
        return cancelremarks;
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
    
    public LocalDateTime getPaymenttime() {
        return paymenttime.get();
    }

    public void setPaymenttime(LocalDateTime value) {
        paymenttime.set(value);
    }

    public ObjectProperty paymenttimeProperty() {
        return paymenttime;
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
    
    public String getPaymentreferrence() {
        return paymentreferrence.get();
    }

    public void setPaymentreferrence(String value) {
        paymentreferrence.set(value);
    }

    public StringProperty paymentreferrenceProperty() {
        return paymentreferrence;
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
    

    public String getOtdiscountremarks() {
        return otdiscountremarks.get();
    }

    public void setOtdiscountremarks(String value) {
        otdiscountremarks.set(value);
    }

    public StringProperty otdiscountremarksProperty() {
        return otdiscountremarks;
    }
    

    public double getOtdiscount() {
        return otdiscount.get();
    }

    public void setOtdiscount(double value) {
        otdiscount.set(value);
    }

    public DoubleProperty otdiscountProperty() {
        return otdiscount;
    }
    

    public String getEmpid() {
        return empid.get();
    }

    public void setEmpid(String value) {
        empid.set(value);
    }

    public StringProperty empidProperty() {
        return empid;
    }
    

    public double getEmpdiscount() {
        return empdiscount.get();
    }

    public void setEmpdiscount(double value) {
        empdiscount.set(value);
    }

    public DoubleProperty empdiscountProperty() {
        return empdiscount;
    }
    

    public String getCareto() {
        return careto.get();
    }

    public void setCareto(String value) {
        careto.set(value);
    }

    public StringProperty caretoProperty() {
        return careto;
    }
    

    public String getPwdid() {
        return pwdid.get();
    }

    public void setPwdid(String value) {
        pwdid.set(value);
    }

    public StringProperty pwdidProperty() {
        return pwdid;
    }
    

    public double getPwddiscount() {
        return pwddiscount.get();
    }

    public void setPwddiscount(double value) {
        pwddiscount.set(value);
    }

    public DoubleProperty pwddiscountProperty() {
        return pwddiscount;
    }
    

    public String getScid() {
        return scid.get();
    }

    public void setScid(String value) {
        scid.set(value);
    }

    public StringProperty scidProperty() {
        return scid;
    }
    

    public double getScdiscount() {
        return scdiscount.get();
    }

    public void setScdiscount(double value) {
        scdiscount.set(value);
    }

    public DoubleProperty scdiscountProperty() {
        return scdiscount;
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
    
    
    public String getChargetype() {
        return chargetype.get();
    }

    public void setChargetype(String value) {
        chargetype.set(value);
    }

    public StringProperty chargetypeProperty() {
        return chargetype;
    }
    
    
    public String getChargefacility() {
        return chargefacility.get();
    }

    public void setChargefacility(String value) {
        chargefacility.set(value);
    }

    public StringProperty chargefacilityProperty() {
        return chargefacility;
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
    
    public String getOrnumber() {
        return ornumber.get();
    }

    public void setOrnumber(String value) {
        ornumber.set(value);
    }

    public StringProperty ornumberProperty() {
        return ornumber;
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
    

    public String getInvoicenumber() {
        return invoicenumber.get();
    }

    public void setInvoicenumber(String value) {
        invoicenumber.set(value);
    }

    public StringProperty invoicenumberProperty() {
        return invoicenumber;
    }
    

    public String getChargenotes() {
        return chargenotes.get();
    }

    public void setChargenotes(String value) {
        chargenotes.set(value);
    }

    public StringProperty chargenotesProperty() {
        return chargenotes;
    }
    
    

    public String getChargeto() {
        return chargeto.get();
    }

    public void setChargeto(String value) {
        chargeto.set(value);
    }

    public StringProperty chargetoProperty() {
        return chargeto;
    }
    

    public LocalDateTime getChargetime() {
        return chargetime.get();
    }

    public void setChargetime(LocalDateTime value) {
        chargetime.set(value);
    }

    public ObjectProperty chargetimeProperty() {
        return chargetime;
    }
    
    public String getChargenumber() {
        return chargenumber.get();
    }

    public void setChargenumber(String value) {
        chargenumber.set(value);
    }

    public StringProperty chargenumberProperty() {
        return chargenumber;
    }
     
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
    
    
    /**
     * Get the value of taxPercentage
     *
     * @return the value of taxPercentage
     */
    public static double getTaxPercentage() {
        return HospitalCharge.taxPercentage;
    }

    /**
     * Set the value of taxPercentage
     *
     * @param taxPercentage new value of taxPercentage
     */
    public static void setTaxPercentage(double taxPercentage) {
        HospitalCharge.taxPercentage = taxPercentage;
    }
    
    
    public void calculateTotal(){
        try{
            double net = (this.getVatsales()+this.getNonvatsales()+this.getZeroratedsales()+this.getInputvat())-(this.getScdiscount()+this.getPwddiscount()+this.getEmpdiscount()+this.getOtdiscount());
            this.setNetsales(NumberKit.round(net,3));
        }catch(Exception er){
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void calculateTotal(List<HospitalChargeItem> items){
        try{
            double total = 0;
            double vsales = 0;
            double nvsales = 0;
            double zrsales = 0;
            double ivat = 0;
            double lvat = 0;
            
            double sc_amt = 0;
            double pwd_amt = 0;
            double emp_amt = 0;
            double ot_amt = 0;
            
            for(HospitalChargeItem item:items){
                total+= item.getTotalselling();
                vsales+= item.getVatsales();
                nvsales+= item.getNonvatsales();
                zrsales+= item.getZeroratedsales();
                ivat+= item.getInputvat();
                lvat+= item.getLessvat();
                
                sc_amt+= item.getScdiscount();
                pwd_amt+= item.getPwddiscount();
                emp_amt+= item.getEmpdiscount();
                ot_amt+= item.getOtdiscount();                
            }
            
            this.setTotalgross(NumberKit.round(total,3));
            this.setVatsales(NumberKit.round(vsales,3));
            this.setNonvatsales(NumberKit.round(nvsales,3));
            this.setZeroratedsales(NumberKit.round(zrsales,3));
            this.setInputvat(NumberKit.round(ivat,3));
            this.setLessvat(NumberKit.round(lvat,3));
            this.setScdiscount(NumberKit.round(sc_amt,3));
            this.setPwddiscount(NumberKit.round(pwd_amt,3));
            this.setEmpdiscount(NumberKit.round(emp_amt,3));
            this.setOtdiscount(NumberKit.round(ot_amt,3));
            
            double net = (this.getVatsales()+this.getNonvatsales()+this.getZeroratedsales()+this.getInputvat())-(this.getScdiscount()+this.getPwddiscount()+this.getEmpdiscount()+this.getOtdiscount());
            this.setNetsales(NumberKit.round(net,3));
        }catch(Exception er){
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    
    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<HospitalChargeItem> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<HospitalChargeItem> items) {
        this.items = items;
    }
    
    public void printChargeSlip(MaskerPane masker,String title,String facility,boolean prompt){
        try {
            if (this.getItems().size() > 0) {
                List<Map<String, ?>> datasource = new ArrayList();
                
                this.getItems().stream().forEach(item->{
                    Map<String, Object> m = new HashMap();
                    m.put("qty", item.getQuantity());
                    m.put("tr_item", item.getDescription()+((item.getQuantity() > 1)? " @ " + item.getSelling():""));
                    m.put("amt", item.getTotalselling());
                    datasource.add(m);
                });

                

                JRDataSource fdatasource = new JRBeanCollectionDataSource(datasource);
                
                JasperReport jr = JasperCompileManager.compileReport(Toolkit.getDefaultToolkit().getClass().getResourceAsStream("/jasper/chargeslip.jrxml"));
                
                Map<String, Object> datas = new HashMap();
                datas.put("title", title);
                datas.put("facility", facility);
                datas.put("chargeto", this.getChargeto());
                datas.put("user", this.getUser());
                datas.put("chargetime", DateTimeKit.toProperTimestamp(this.getChargetime()));
                datas.put("chargenumber", this.getChargenumber());
                datas.put("total", this.getTotalgross());
                datas.put("physician", this.getOpt0());
                
                datas.put("requestor", this.getChargenotes());
                datas.put("approved", this.getRemarks());
                
                
                JasperPrint jp = JasperFillManager.fillReport(jr, datas, fdatasource);
                
                jp.setName("ChargeSlip-"+this.getChargenumber());
                if (prompt) {
                    new JasperFrame().showFrame(new JRViewer(jp));                    
                } else {                    
                    try {
                        if(masker!= null){
                            masker.setVisible(true);
                            Thread.sleep(200);
                        }
                        JasperPrintManager.printReport(jp, false);
                    } catch (InterruptedException | JRException er) {
                        Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
                    }finally{
                        if(masker!= null){
                            masker.setVisible(false);
                        }
                    }
                }                
            }

        } catch (Exception er) {
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public static void createTableFilter(JFXTextField field,FilteredList<HospitalCharge> filteredRecords ){
        try{
            ObjectProperty<Predicate<HospitalCharge>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> invFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> orFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalCharge>> cashierFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargeto().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            invFilter.bind(Bindings.createObjectBinding(()-> record -> record.getInvoicenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            orFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOrnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            cashierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCashier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(codeFilter.get()).or(invFilter.get()).or(orFilter.get()).or(userFilter.get()).or(cashierFilter.get()),nameFilter, desFilter,codeFilter,invFilter,orFilter,userFilter,cashierFilter));
        }catch(Exception er){
            Logger.getLogger(HospitalCharge.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return chargenumber.get()+" - "+chargefacility.get()+ " - "+NumberKit.toCurrency(netsales.get());
    }
    
    
    
    
    
}
