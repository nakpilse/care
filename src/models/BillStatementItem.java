package models;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class BillStatementItem extends SQLModel<BillStatementItem>{
    
    public static final String TABLE_NAME = "billstatementitems";
    public static final String JOIN_KEY = "billstatementitem_id";
    
    public static final String PATIENT = "patient";
    public static final String DESCRIPTION = "description";
    public static final String FACILITY = "facility";
    public static final String ITEMTYPE = "itemtype";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String AMOUNT = "amount";
    public static final String HOSPITALSHARE = "hospitalshare";
    public static final String PFSHARE = "pfshare";    
    public static final String PNFAMOUNT = "pnfamount";
    public static final String FIRSTCASERATEAMOUNT = "firstcaserateamount";
    public static final String SECONDCASERATEAMOUNT = "secondcaserateamount";
    public static final String BENEFITAMOUNT = "benefitamount";
    public static final String BENEFITSOURCE = "benefitsource";
    public static final String VATSALES = "vatsales";
    public static final String NONVATSALES = "nonvatsales";
    public static final String ZERORATEDSALES = "zeroratedsales";
    public static final String INPUTVAT = "inputvat";
    public static final String LESSVAT = "lessvat";
    public static final String SCDISCOUNT = "scdiscount";
    public static final String PWDDISCOUNT = "pwddiscount";
    public static final String EMPDISCOUNT = "empdiscount";
    public static final String OTDISCOUNT = "otdiscount";
    public static final String OTDISCOUNTREMARKS = "otdiscountremarks";
    public static final String NETSALES = "netsales";
    public static final String ADDEDBY = "addedby";
    public static final String CHARGENUMBER = "chargenumber";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String HOSPITALCHARGEITEM_ID = HospitalChargeItem.JOIN_KEY;
    public static final String HOSPITALCHARGE_ID = HospitalCharge.JOIN_KEY;
    public static final String BILLSTATEMENT_ID = BillStatement.JOIN_KEY;
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    
    @Dup
    @Col(name=PATIENT)
    private final StringProperty patient = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup
    @Col(name=FACILITY)
    private final StringProperty facility = new SimpleStringProperty("");
    @Dup
    @Col(name=ITEMTYPE)
    private final StringProperty itemtype = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    @Dup(Class = double.class)
    @Col(name=PRICE,Class = double.class)
    private final DoubleProperty price = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=AMOUNT,Class = double.class)
    private final DoubleProperty amount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=HOSPITALSHARE,Class = double.class)
    private final DoubleProperty hospitalshare = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=PFSHARE,Class = double.class)
    private final DoubleProperty pfshare = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=PNFAMOUNT,Class = double.class)
    private final DoubleProperty pnfamount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=FIRSTCASERATEAMOUNT,Class = double.class)
    private final DoubleProperty firstcaserateamount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=SECONDCASERATEAMOUNT,Class = double.class)
    private final DoubleProperty secondcaserateamount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=BENEFITAMOUNT,Class = double.class)
    private final DoubleProperty benefitamount = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=BENEFITSOURCE)
    private final StringProperty benefitsource = new SimpleStringProperty("");
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
    @Dup(Class = double.class)
    @Col(name=PWDDISCOUNT,Class = double.class)
    private final DoubleProperty pwddiscount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=EMPDISCOUNT,Class = double.class)
    private final DoubleProperty empdiscount = new SimpleDoubleProperty(0);
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
    @Col(name=ADDEDBY)
    private final StringProperty addedby = new SimpleStringProperty("");
    @Dup
    @Col(name=CHARGENUMBER)
    private final StringProperty chargenumber = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=HOSPITALCHARGEITEM_ID,Class = int.class)
    private final IntegerProperty hospitalchargeitem_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=HOSPITALCHARGE_ID,Class = int.class)
    private final IntegerProperty hospitalcharge_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=BILLSTATEMENT_ID,Class = int.class)
    private final IntegerProperty billstatement_id = new SimpleIntegerProperty(0);
    @Dup(Class = LocalDateTime.class)
    @Col(name=CREATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> created_at = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name=UPDATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> updated_at = new SimpleObjectProperty<>(null);

    public int getHospitalcharge_id() {
        return hospitalcharge_id.get();
    }

    public void setHospitalcharge_id(int value) {
        hospitalcharge_id.set(value);
    }

    public IntegerProperty hospitalcharge_idProperty() {
        return hospitalcharge_id;
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
    
    
    

    public String getBenefitsource() {
        return benefitsource.get();
    }

    public void setBenefitsource(String value) {
        benefitsource.set(value);
    }

    public StringProperty benefitsourceProperty() {
        return benefitsource;
    }
    

    public double getBenefitamount() {
        return benefitamount.get();
    }

    public void setBenefitamount(double value) {
        benefitamount.set(value);
    }

    public DoubleProperty benefitamountProperty() {
        return benefitamount;
    }
    

    public double getSecondcaserateamount() {
        return secondcaserateamount.get();
    }

    public void setSecondcaserateamount(double value) {
        secondcaserateamount.set(value);
    }

    public DoubleProperty secondcaserateamountProperty() {
        return secondcaserateamount;
    }
    
    

    public double getFirstcaserateamount() {
        return firstcaserateamount.get();
    }

    public void setFirstcaserateamount(double value) {
        firstcaserateamount.set(value);
    }

    public DoubleProperty firstcaserateamountProperty() {
        return firstcaserateamount;
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
    

    public int getBillstatement_id() {
        return billstatement_id.get();
    }

    public void setBillstatement_id(int value) {
        billstatement_id.set(value);
    }

    public IntegerProperty billstatement_idProperty() {
        return billstatement_id;
    }
    

    public int getHospitalchargeitem_id() {
        return hospitalchargeitem_id.get();
    }

    public void setHospitalchargeitem_id(int value) {
        hospitalchargeitem_id.set(value);
    }

    public IntegerProperty hospitalchargeitem_idProperty() {
        return hospitalchargeitem_id;
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
    

    public String getAddedby() {
        return addedby.get();
    }

    public void setAddedby(String value) {
        addedby.set(value);
    }

    public StringProperty addedbyProperty() {
        return addedby;
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
    

    public double getEmpdiscount() {
        return empdiscount.get();
    }

    public void setEmpdiscount(double value) {
        empdiscount.set(value);
    }

    public DoubleProperty empdiscountProperty() {
        return empdiscount;
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
    

    public double getPnfamount() {
        return pnfamount.get();
    }

    public void setPnfamount(double value) {
        pnfamount.set(value);
    }

    public DoubleProperty pnfamountProperty() {
        return pnfamount;
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
    

    public double getHospitalshare() {
        return hospitalshare.get();
    }

    public void setHospitalshare(double value) {
        hospitalshare.set(value);
    }

    public DoubleProperty hospitalshareProperty() {
        return hospitalshare;
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
    

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public DoubleProperty priceProperty() {
        return price;
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
    

    public String getItemtype() {
        return itemtype.get();
    }

    public void setItemtype(String value) {
        itemtype.set(value);
    }

    public StringProperty itemtypeProperty() {
        return itemtype;
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
    

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
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
    
    public void calculateGross(){
        try{            
            double c = this.getQuantity()*this.getPrice();
            this.setAmount(c);            
        }catch(Exception er){
            Logger.getLogger(BillStatementItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void calculateNet(){
        try{
            double net = (this.getVatsales()+this.getNonvatsales()+this.getZeroratedsales()+this.getInputvat())-(this.getScdiscount()+this.getPwddiscount()+this.getOtdiscount()+this.getEmpdiscount());            
            this.setNetsales((net<0)? 0:net);            
        }catch(Exception er){
            Logger.getLogger(BillStatementItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
