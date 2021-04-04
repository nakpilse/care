package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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

/**
 *
 * @author Duskmourne
 */
public class ReturnedItem extends SQLModel<ReturnedItem>{
    
    public static final String TABLE = "returneditems";
    public static final String JOIN_KEY = "returneditem_id";
    
    public static final String CHARGENUMBER = "chargenumber";
    public static final String CHARGETO = "chargeto";
    public static final String INVOICE = "invoice";
    public static final String BILLNUMBER = "billnumber";
    public static final String ORNUMBER = "ornumber";
    public static final String RETURNTIME = "returntime";
    public static final String FACILITY = "facility";
    public static final String RETURNEDBY = "returnedby";
    public static final String RECIEVEDBY = "recievedby";
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String REMARKS = "remarks";
    public static final String ITEMTABLE = "itemtable";
    public static final String ITEMTABLEID = "itemtableid";
    public static final String USER = "user";
    public static final String USER_ID = User.JOIN_KEY;
    
    
    @Dup
    @Col(name=CHARGENUMBER)
    private final StringProperty chargenumber = new SimpleStringProperty("");
    @Dup
    @Col(name=CHARGETO)
    private final StringProperty chargeto = new SimpleStringProperty("");
    @Dup
    @Col(name=INVOICE)
    private final StringProperty invoice = new SimpleStringProperty("");
    @Dup
    @Col(name=BILLNUMBER)
    private final StringProperty billnumber = new SimpleStringProperty("");
    @Dup
    @Col(name=ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=RETURNTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> returntime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=FACILITY)
    private final StringProperty facility = new SimpleStringProperty("");
    @Dup
    @Col(name=RETURNEDBY)
    private final StringProperty returnedby = new SimpleStringProperty("");
    @Dup
    @Col(name=RECIEVEDBY)
    private final StringProperty recievedby = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=REMARKS)
    private final StringProperty remarks = new SimpleStringProperty("");
    @Dup
    @Col(name=ITEMTABLE)
    private final StringProperty itemtable = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=ITEMTABLEID,Class = int.class)
    private final IntegerProperty itemtableid = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=USER)
    private final StringProperty user = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);

    public int getUser_id() {
        return user_id.get();
    }

    public void setUser_id(int value) {
        user_id.set(value);
    }

    public IntegerProperty user_idProperty() {
        return user_id;
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
    

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String value) {
        remarks.set(value);
    }

    public StringProperty remarksProperty() {
        return remarks;
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
    

    public String getRecievedby() {
        return recievedby.get();
    }

    public void setRecievedby(String value) {
        recievedby.set(value);
    }

    public StringProperty recievedbyProperty() {
        return recievedby;
    }
    

    public String getReturnedby() {
        return returnedby.get();
    }

    public void setReturnedby(String value) {
        returnedby.set(value);
    }

    public StringProperty returnedbyProperty() {
        return returnedby;
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
    

    public LocalDateTime getReturntime() {
        return returntime.get();
    }

    public void setReturntime(LocalDateTime value) {
        returntime.set(value);
    }

    public ObjectProperty returntimeProperty() {
        return returntime;
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
    

    public String getInvoice() {
        return invoice.get();
    }

    public void setInvoice(String value) {
        invoice.set(value);
    }

    public StringProperty invoiceProperty() {
        return invoice;
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
    
    
    

    public String getChargenumber() {
        return chargenumber.get();
    }

    public void setChargenumber(String value) {
        chargenumber.set(value);
    }

    public StringProperty chargenumberProperty() {
        return chargenumber;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<ReturnedItem> filteredRecords ){
        try{
            ObjectProperty<Predicate<ReturnedItem>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> returnedbyFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> recievedbyFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> invoiceFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> orFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ReturnedItem>> billFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getChargeto().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            returnedbyFilter.bind(Bindings.createObjectBinding(()-> record -> record.getReturnedby().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            
            recievedbyFilter.bind(Bindings.createObjectBinding(()-> record -> record.getRecievedby().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            invoiceFilter.bind(Bindings.createObjectBinding(()-> record -> record.getInvoice().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            orFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOrnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            billFilter.bind(Bindings.createObjectBinding(()-> record -> record.getBillnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(codeFilter.get()).or(userFilter.get()).or(returnedbyFilter.get()).or(recievedbyFilter.get()).or(invoiceFilter.get()).or(orFilter.get()).or(billFilter.get())
                    ,nameFilter, desFilter,codeFilter,userFilter,returnedbyFilter,recievedbyFilter,invoiceFilter,orFilter,billFilter));
        }catch(Exception er){
            Logger.getLogger(ReturnedItem.class.getName()).log(Level.SEVERE, null, er);
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
    
    public final boolean reduceItemQuantity(){
        try{            
            return SQLTable.execute("UPDATE "+this.getItemtable()+" SET "+QUANTITY+"=("+QUANTITY+"-"+this.getQuantity()+") WHERE "+ID+"="+this.getItemtableid());
        }catch(Exception er){
            Logger.getLogger(HospitalChargeItem.class.getName()).log(Level.SEVERE, null, er);
            return false;
        }
    }
}
