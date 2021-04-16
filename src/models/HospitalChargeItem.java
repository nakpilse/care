package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDateTime;
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
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.NumberKit;

/**
 *
 * @author Duskmourne
 */
public class HospitalChargeItem extends SQLModel<HospitalChargeItem>{
    
    public static final String TABLE_NAME = "hospitalchargeitems";
    public static final String JOIN_KEY = "hospitalchargeitem_id";
    
    public static final String CHARGENUMBER = "chargenumber";
    public static final String CHARGETIME = "chargetime";
    public static final String CHARGETO = "chargeto";
    public static final String FACILITY = "facility";
    public static final String ITEMCODE = "itemcode";
    public static final String ITEMTYPE = "itemtype";
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String RETURNED = "returned";
    public static final String COST = "cost";
    public static final String SELLING = "selling";
    public static final String TOTALCOST = "totalcost";
    public static final String TOTALSELLING = "totalselling";
    public static final String TOTALRETURN = "totalreturn";
    public static final String HOSPITALSHARE = "hospitalshare";
    public static final String PFCODE = "pfcode";
    public static final String PFSHARE = "pfshare";
    public static final String PHYSICIAN = "physician";
    public static final String OTSHARE = "otshare";
    public static final String OTSHAREDESCRIPTION = "otsharedescription";
    public static final String SERVICE = "service";
    public static final String PNF = "pnf";
    public static final String VATABLE = "vatable";
    public static final String PNFAMOUNT = "pnfamount";
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
    public static final String PAYMENTTIME = "paymenttime";
    public static final String PAYMENTTYPE = "paymenttype";
    public static final String PAYMENTREFERRENCE = "paymentreferrence";
    public static final String ENCODER = "encoder";
    public static final String CANCELLED = "cancelled";
    public static final String CANCELTIME = "canceltime";
    public static final String CANCELREMARKS = "cancelremarks";
    public static final String VOIDED = "voided";
    public static final String VOIDTIME = "voidtime";
    public static final String VOIDREMARKS = "voidremarks";
    public static final String CASHIER = "cashier";
    public static final String CASHIERID = "cashierid";
    public static final String USER = "user";
    public static final String ITEMTABLE = "itemtable";
    public static final String ITEMTABLEID = "itemtableid";
    public static final String RECORDTABLE = "recordtable";
    public static final String RECORDTABLEID = "recordtableid";
    public static final String USER_ID = User.JOIN_KEY;
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String HOSPITALCHARGE_ID = HospitalCharge.JOIN_KEY;    
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    public static final String OPT0 = "opt0";
    public static final String OPT1 = "opt1";
    public static final String OPT2 = "opt2";
    public static final String OPT3 = "opt3";
    public static final String OPT4 = "opt4";
    public static final String OPT5 = "opt5";
    public static final String OPT6 = "opt6";
    public static final String OPT7 = "opt7";
    public static final String OPT8 = "opt8";
    public static final String OPT9 = "opt9";
    
    
    public HospitalChargeItem(){
        try{
            scdiscount.addListener((obs,oldVal,newVal)->{
                this.setScpwdvalue(this.getScdiscount()+this.getPwddiscount());
            });
            pwddiscount.addListener((obs,oldVal,newVal)->{
                this.setScpwdvalue(this.getScdiscount()+this.getPwddiscount());
            });
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    
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
    @Col(name=FACILITY)
    private final StringProperty facility = new SimpleStringProperty("");
    @Dup
    @Col(name=ITEMCODE)
    private final StringProperty itemcode = new SimpleStringProperty("");
    @Dup
    @Col(name=ITEMTYPE)
    private final StringProperty itemtype = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=RETURNED,Class = int.class)
    private final IntegerProperty returned = new SimpleIntegerProperty(0);
    @Dup(Class = double.class)
    @Col(name=COST,Class = double.class)
    private final DoubleProperty cost = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=SELLING,Class = double.class)
    private final DoubleProperty selling = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=TOTALCOST,Class = double.class)
    private final DoubleProperty totalcost = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=TOTALSELLING,Class = double.class)
    private final DoubleProperty totalselling = new SimpleDoubleProperty(0);    
    @Dup(Class = double.class)
    @Col(name=TOTALRETURN,Class = double.class)
    private final DoubleProperty totalreturn = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=HOSPITALSHARE,Class = double.class)
    private final DoubleProperty hospitalshare = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=PFCODE)
    private final StringProperty pfcode = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=PFSHARE,Class = double.class)
    private final DoubleProperty pfshare = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=PHYSICIAN)
    private final StringProperty physician = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=OTSHARE,Class = double.class)
    private final DoubleProperty otshare = new SimpleDoubleProperty(0);    
    @Dup
    @Col(name=OTSHAREDESCRIPTION)
    private final StringProperty otsharedescription = new SimpleStringProperty("");
    @Dup(Class = boolean.class)
    @Col(name=SERVICE,Class = boolean.class)
    private final BooleanProperty service = new SimpleBooleanProperty(false);
    @Dup(Class = boolean.class)
    @Col(name=PNF,Class = boolean.class)
    private final BooleanProperty pnf = new SimpleBooleanProperty(false);
    @Dup(Class = boolean.class)
    @Col(name=VATABLE,Class = boolean.class)
    private final BooleanProperty vatable = new SimpleBooleanProperty(true);
    @Dup(Class = double.class)
    @Col(name=PNFAMOUNT,Class = double.class)
    private final DoubleProperty pnfamount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name= VATSALES,Class = double.class)
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
    @Dup(Class = double.class)
    @Col(name=PAIDAMOUNT,Class = double.class)
    private final DoubleProperty paidamount = new SimpleDoubleProperty(0);
    @Dup(Class = LocalDateTime.class)
    @Col(name=PAYMENTTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> paymenttime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=PAYMENTTYPE)
    private final StringProperty paymenttype = new SimpleStringProperty("");
    @Dup
    @Col(name=PAYMENTREFERRENCE)
    private final StringProperty paymentreferrence = new SimpleStringProperty("");
    @Dup
    @Col(name=ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
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
    @Dup
    @Col(name=CASHIER)
    private final StringProperty cashier = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=CASHIERID,Class = int.class)
    private final IntegerProperty cashierid = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=USER)
    private final StringProperty user = new SimpleStringProperty("");
    @Dup
    @Col(name=ITEMTABLE)
    private final StringProperty itemtable = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=ITEMTABLEID,Class = int.class)
    private final IntegerProperty itemtableid = new SimpleIntegerProperty(0);
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
    @Col(name=HOSPITALCHARGE_ID,Class = int.class)
    private final IntegerProperty hospitalcharge_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=ADMISSION_ID,Class = int.class)
    private final IntegerProperty admission_id = new SimpleIntegerProperty(0);
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
    
    
    private final IntegerProperty tmpcount = new SimpleIntegerProperty(0);
    private final DoubleProperty scpwdvalue = new SimpleDoubleProperty(0);

    public double getScpwdvalue() {
        return scpwdvalue.get();
    }

    public void setScpwdvalue(double value) {
        scpwdvalue.set(value);
    }

    public DoubleProperty scpwdvalueProperty() {
        return scpwdvalue;
    }
    

    public int getTmpcount() {
        return tmpcount.get();
    }

    public void setTmpcount(int value) {
        tmpcount.set(value);
    }

    public IntegerProperty tmpcountProperty() {
        return tmpcount;
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
    

    public boolean isService() {
        return service.get();
    }

    public void setService(boolean value) {
        service.set(value);
    }

    public BooleanProperty serviceProperty() {
        return service;
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
    

    public double getHospitalshare() {
        return hospitalshare.get();
    }

    public void setHospitalshare(double value) {
        hospitalshare.set(value);
    }

    public DoubleProperty hospitalshareProperty() {
        return hospitalshare;
    }
    
    public double getTotalreturn() {
        return totalreturn.get();
    }

    public void setTotalreturn(double value) {
        totalreturn.set(value);
    }

    public DoubleProperty totalreturnProperty() {
        return totalreturn;
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
    

    public int getHospitalcharge_id() {
        return hospitalcharge_id.get();
    }

    public void setHospitalcharge_id(int value) {
        hospitalcharge_id.set(value);
    }

    public IntegerProperty hospitalcharge_idProperty() {
        return hospitalcharge_id;
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
    

    public int getItemtableid() {
        return itemtableid.get();
    }

    public void setItemtableid(int value) {
        itemtableid.set(value);
    }

    public IntegerProperty itemtableidProperty() {
        return itemtableid;
    }
    

    public String getItemtable() {
        return itemtable.get();
    }

    public void setItemtable(String value) {
        itemtable.set(value);
    }

    public StringProperty itemtableProperty() {
        return itemtable;
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
    

    public int getCashierid() {
        return cashierid.get();
    }

    public void setCashierid(int value) {
        cashierid.set(value);
    }

    public IntegerProperty cashieridProperty() {
        return cashierid;
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
    
    

    public String getEncoder() {
        return encoder.get();
    }

    public void setEncoder(String value) {
        encoder.set(value);
    }

    public StringProperty encoderProperty() {
        return encoder;
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
    

    public String getPaymenttype() {
        return paymenttype.get();
    }

    public void setPaymenttype(String value) {
        paymenttype.set(value);
    }

    public StringProperty paymenttypeProperty() {
        return paymenttype;
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
    
    public String getScid() {
        return scid.get();
    }

    public void setScid(String value) {
        scid.set(value);
    }

    public StringProperty scidProperty() {
        return scid;
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
    
    

    public double getScdiscount() {
        return scdiscount.get();
    }

    public void setScdiscount(double value) {
        scdiscount.set(value);
    }

    public DoubleProperty scdiscountProperty() {
        return scdiscount;
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
    

    public double getPnfamount() {
        return pnfamount.get();
    }

    public void setPnfamount(double value) {
        pnfamount.set(value);
    }

    public DoubleProperty pnfamountProperty() {
        return pnfamount;
    }
    

    public boolean isVatable() {
        return vatable.get();
    }

    public void setVatable(boolean value) {
        vatable.set(value);
    }

    public BooleanProperty vatableProperty() {
        return vatable;
    }
    

    public boolean isPnf() {
        return pnf.get();
    }

    public void setPnf(boolean value) {
        pnf.set(value);
    }

    public BooleanProperty pnfProperty() {
        return pnf;
    }
    
    

    public String getOtsharedescription() {
        return otsharedescription.get();
    }

    public void setOtsharedescription(String value) {
        otsharedescription.set(value);
    }

    public StringProperty otsharedescriptionProperty() {
        return otsharedescription;
    }
    
    

    public double getOtshare() {
        return otshare.get();
    }

    public void setOtshare(double value) {
        otshare.set(value);
    }

    public DoubleProperty otshareProperty() {
        return otshare;
    }
    

    public String getPhysician() {
        return physician.get();
    }

    public void setPhysician(String value) {
        physician.set(value);
    }

    public StringProperty physicianProperty() {
        return physician;
    }
    

    public double getPfshare() {
        return pfshare.get();
    }

    public void setPfshare(double value) {
        pfshare.set(value);
    }

    public DoubleProperty pfshareProperty() {
        return pfshare;
    }
    

    public String getPfcode() {
        return pfcode.get();
    }

    public void setPfcode(String value) {
        pfcode.set(value);
    }

    public StringProperty pfcodeProperty() {
        return pfcode;
    }
    
    public double getTotalselling() {
        return totalselling.get();
    }

    public void setTotalselling(double value) {
        totalselling.set(value);
    }

    public DoubleProperty totalsellingProperty() {
        return totalselling;
    }
    

    public double getTotalcost() {
        return totalcost.get();
    }

    public void setTotalcost(double value) {
        totalcost.set(value);
    }

    public DoubleProperty totalcostProperty() {
        return totalcost;
    }
    

    public double getSelling() {
        return selling.get();
    }

    public void setSelling(double value) {
        selling.set(value);
    }

    public DoubleProperty sellingProperty() {
        return selling;
    }
    

    public double getCost() {
        return cost.get();
    }

    public void setCost(double value) {
        cost.set(value);
    }

    public DoubleProperty costProperty() {
        return cost;
    }
    

    public int getReturned() {
        return returned.get();
    }

    public void setReturned(int value) {
        returned.set(value);
    }

    public IntegerProperty returnedProperty() {
        return returned;
    }
    

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
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
    

    public String getItemtype() {
        return itemtype.get();
    }

    public void setItemtype(String value) {
        itemtype.set(value);
    }

    public StringProperty itemtypeProperty() {
        return itemtype;
    }
    

    public String getItemcode() {
        return itemcode.get();
    }

    public void setItemcode(String value) {
        itemcode.set(value);
    }

    public StringProperty itemcodeProperty() {
        return itemcode;
    }
    

    public String getFacility() {
        return facility.get();
    }

    public void setFacility(String value) {
        facility.set(value);
    }

    public StringProperty facilityProperty() {
        return facility;
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
    
    public ECart asECartItem(){
        ECart ecart = new ECart();
        ecart.setDescription(this.getDescription());
        ecart.setQuantity(this.getQuantity());
        ecart.setItemtable(this.getItemtable());
        ecart.setItemtableid(this.getItemtableid());
        return ecart;
    }
    
    public ReturnedItem asReturnedItem(){
        ReturnedItem ritem = new ReturnedItem();
        ritem.setChargenumber(this.getChargenumber());
        ritem.setChargeto(this.getChargeto());
        ritem.setFacility(this.getFacility());
        ritem.setDescription(this.getDescription());
        ritem.setQuantity(this.getQuantity());
        ritem.setItemtable(this.getItemtable());
        ritem.setItemtableid(this.getItemtableid());
        return ritem;
    }
    
    public void calculateGross(){
        try{
            
            double c = this.getQuantity()*this.getCost();
            double s = this.getQuantity()*this.getSelling();
            double r = this.getReturned()*this.getSelling();
            this.setTotalcost(c);
            this.setTotalselling(s);
            this.setTotalreturn(r);
            
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void calculateNet(){
        calculateNet(true,0,0,0,0); 
    }
    
    public void calculateNet(boolean vat){
        calculateNet(vat,0,0,0,0); 
    }
    
    public void calculateNet(boolean vat,double sc,double pwd,double emp,double ot){
        try{
            double vat_div = 1 + (HospitalCharge.getTaxPercentage()/100);
            calculateGross();     
            
            double vsales = 0;
            double nvsales = 0;
            double zrsales = 0;
            double vatamt = 0;
            double lvatamt = 0;
            double net = 0;
            
            double sc_amt = 0;
            double pwd_amt = 0;
            double emp_amt = 0;
            double ot_amt = 0;
            
            if(!this.isService()){
                if(this.isVatable()){                    
                    if(vat){
                        //Vatable Item
                        if(sc > 0 || pwd > 0){
                            //Vatable Item to non-vat due to SC/PWD
                            vsales = 0;
                            nvsales = this.getTotalselling()/vat_div;
                            zrsales = 0;
                            vatamt = 0;
                            lvatamt = this.getTotalselling()-nvsales;   

                            sc_amt = nvsales * (sc/100);
                            pwd_amt = nvsales * (pwd/100);
                            emp_amt = (nvsales-(sc_amt+pwd_amt)) * (emp/100);
                            ot_amt = (nvsales-(sc_amt+pwd_amt+emp_amt)) * (ot/100);

                            this.setScdiscount(NumberKit.round(sc_amt, 3));
                            this.setPwddiscount(NumberKit.round(pwd_amt, 3));
                            this.setOtdiscount(NumberKit.round(ot_amt, 3));
                            this.setEmpdiscount(NumberKit.round(emp_amt, 3));
                        }else{
                            vsales = this.getTotalselling()/vat_div;
                            nvsales = 0;
                            zrsales = 0;
                            vatamt = this.getTotalselling()-vsales;
                            lvatamt = 0;   


                            emp_amt = this.getTotalselling() * (emp/100);
                            ot_amt = (this.getTotalselling()-emp_amt) * (ot/100);

                            this.setScdiscount(0);
                            this.setPwddiscount(0);
                            this.setOtdiscount(NumberKit.round(ot_amt, 3));
                            this.setEmpdiscount(NumberKit.round(emp_amt, 3));
                        }
                    }else{                        
                        vsales = 0;
                        nvsales = this.getTotalselling()/vat_div;
                        zrsales = 0;
                        vatamt = 0;
                        lvatamt = this.getTotalselling()-nvsales;   

                        sc_amt = nvsales * (sc/100);
                        pwd_amt = nvsales * (pwd/100);
                        emp_amt = (nvsales-(sc_amt+pwd_amt)) * (emp/100);
                        ot_amt = (nvsales-(sc_amt+pwd_amt+emp_amt)) * (ot/100);

                        this.setScdiscount(NumberKit.round(sc_amt, 3));
                        this.setPwddiscount(NumberKit.round(pwd_amt, 3));
                        this.setOtdiscount(NumberKit.round(ot_amt, 3));
                        this.setEmpdiscount(NumberKit.round(emp_amt, 3));                        
                    }                    
                }else{
                    vsales = 0;
                    nvsales = this.getTotalselling();
                    zrsales = 0;
                    vatamt = 0;
                    lvatamt = 0;//(this.getTotalselling()*vat_div)-this.getTotalselling();

                    sc_amt = nvsales * (sc/100);
                    pwd_amt = nvsales * (pwd/100);
                    emp_amt = (nvsales-(sc_amt+pwd_amt)) * (emp/100);
                    ot_amt = (nvsales-(sc_amt+pwd_amt+emp_amt)) * (ot/100);

                    this.setScdiscount(NumberKit.round(sc_amt, 3));
                    this.setPwddiscount(NumberKit.round(pwd_amt, 3));
                    this.setOtdiscount(NumberKit.round(ot_amt, 3));
                    this.setEmpdiscount(NumberKit.round(emp_amt, 3));
                }     
            }else{
                vsales = 0;
                nvsales = this.getTotalselling();
                zrsales = 0;
                vatamt = 0;
                lvatamt = 0;

                sc_amt = nvsales * (sc/100);
                pwd_amt = nvsales * (pwd/100);
                emp_amt = (nvsales-(sc_amt+pwd_amt)) * (emp/100);
                ot_amt = (nvsales-(sc_amt+pwd_amt+emp_amt)) * (ot/100);

                this.setScdiscount(NumberKit.round(sc_amt, 3));
                this.setPwddiscount(NumberKit.round(pwd_amt, 3));
                this.setOtdiscount(NumberKit.round(ot_amt, 3));
                this.setEmpdiscount(NumberKit.round(emp_amt, 3));
            }
                   
                
                
            this.setVatsales(NumberKit.round(vsales, 3));
            this.setNonvatsales(NumberKit.round(nvsales, 3));
            this.setZeroratedsales(NumberKit.round(zrsales, 3));
            this.setInputvat(NumberKit.round(vatamt, 3));
            this.setLessvat(NumberKit.round(lvatamt, 3));
            
            net = (vsales+nvsales+zrsales+vatamt)-(this.getScdiscount()+this.getPwddiscount()+this.getOtdiscount()+this.getEmpdiscount());            
            this.setNetsales((net<0)? 0:NumberKit.round(net, 3));            
            
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public final boolean deductItemQuantity(){
        try{            
            return SQLTable.execute("UPDATE "+this.getItemtable()+" SET "+QUANTITY+"=("+QUANTITY+"-"+this.getQuantity()+") WHERE "+ID+"="+this.getItemtableid());
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
            return false;
        }
    }
    
    public final boolean addItemQuantity(){
        try{            
            return SQLTable.execute("UPDATE "+this.getItemtable()+" SET "+QUANTITY+"=("+QUANTITY+"+"+this.getQuantity()+") WHERE "+ID+"="+this.getItemtableid());
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
            return false;
        }
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<HospitalChargeItem> filteredRecords ){
        try{
            ObjectProperty<Predicate<HospitalChargeItem>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalChargeItem>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalChargeItem>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalChargeItem>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalChargeItem>> cashierFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargeto().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            cashierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCashier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(codeFilter.get()).or(userFilter.get()).or(cashierFilter.get()),nameFilter, desFilter,codeFilter,userFilter,cashierFilter));
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
    public BillStatementItem toBillStatementItem(){
        BillStatementItem b = new BillStatementItem();
        b.setPatient(this.getChargeto());
        b.setDescription(this.getDescription());
        b.setFacility(this.getFacility());
        b.setItemtype(this.getItemtype());
        b.setQuantity(this.getQuantity());
        b.setPrice(this.getSelling());
        b.setAmount(this.getTotalselling());
        b.setHospitalshare(this.getHospitalshare());
        b.setPfshare(this.getPfshare());
        b.setPnfamount(this.getPnfamount());
        b.setVatsales(this.getVatsales());
        b.setNonvatsales(this.getNonvatsales());
        b.setZeroratedsales(this.getZeroratedsales());
        b.setInputvat(this.getInputvat());
        b.setLessvat(this.getLessvat());
        b.setScdiscount(this.getScdiscount());
        b.setPwddiscount(this.getPwddiscount());
        b.setEmpdiscount(this.getEmpdiscount());
        b.setOtdiscount(this.getOtdiscount());
        b.setOtdiscountremarks(this.getOtdiscountremarks());
        b.setNetsales(this.getNetsales());
        
        b.setChargenumber(this.getChargenumber());
        b.setHospitalchargeitem_id(this.getId());
        b.setHospitalcharge_id(this.getHospitalcharge_id());
        
        
        return b;
    }
}
