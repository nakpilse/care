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
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class DeliveryItem extends SQLModel<DeliveryItem>{
    
    public static final String TABLE_NAME = "deliveryitems";
    public static final String JOIN_KEY = "deliveryitem_id";
    
    public static final String SUPPLIER = "supplier";
    public static final String REFERRENCE = "referrence";
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String UNITS = "units";
    public static final String COST = "cost";
    public static final String AMOUNT = "amount";
    public static final String ITEMQTY = "itemqty";
    public static final String ITEMCOST = "itemcost";
    public static final String ITEMSELLING = "itemselling";
    public static final String EXPIRATION = "expiration";
    public static final String DELIVERYDATE = "deliverydate";    
    public static final String DELIVEREDBY = "deliveredby";    
    public static final String RECIEVEDBY = "recievedby";    
    public static final String RETURNDATE = "returndate";    
    public static final String RETURNEDBY = "returnedby";
    public static final String RETURNEDTO = "returnedto";    
    public static final String CANCELDATE = "canceldate";
    public static final String CANCELEDBY = "canceledby";
    public static final String TYPE = "type";
    public static final String USER = "user";
    public static final String REMARKS = "remarks";
    public static final String ITEM_ID = Item.JOIN_KEY;
    public static final String SUPPLIER_ID = Supplier.JOIN_KEY;
    public static final String USER_ID = User.JOIN_KEY;
    
    //Status
    public static final String DELIVERY_RECIEVED = "Recieved";
    public static final String DELIVERY_RETURNED = "Returned";
    
    @Dup
    @Col(name=SUPPLIER)
    private final StringProperty supplier = new SimpleStringProperty("");
    @Dup
    @Col(name=REFERRENCE)
    private final StringProperty referrence = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=UNITS)
    private final StringProperty units = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=COST,Class = double.class)
    private final DoubleProperty cost = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=AMOUNT,Class = double.class)
    private final DoubleProperty amount = new SimpleDoubleProperty(0);
    @Dup(Class = int.class)
    @Col(name=ITEMQTY,Class = int.class)
    private final IntegerProperty itemqty = new SimpleIntegerProperty(0);
    @Dup(Class = double.class)
    @Col(name=ITEMCOST,Class = double.class)
    private final DoubleProperty itemcost = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=ITEMSELLING,Class = double.class)
    private final DoubleProperty itemselling = new SimpleDoubleProperty(0);
    @Dup(Class = LocalDate.class)
    @Col(name=EXPIRATION,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> expiration = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDate.class)
    @Col(name=DELIVERYDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> deliverydate = new SimpleObjectProperty<>(LocalDate.now());
    @Dup
    @Col(name=DELIVEREDBY)
    private final StringProperty deliveredby = new SimpleStringProperty("");
    @Dup
    @Col(name=RECIEVEDBY)
    private final StringProperty recievedby = new SimpleStringProperty("");
    @Dup(Class = LocalDate.class)
    @Col(name=RETURNDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> returndate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=RETURNEDBY)
    private final StringProperty returnedby = new SimpleStringProperty("");
    @Dup
    @Col(name=RETURNEDTO)
    private final StringProperty returnedto = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=CANCELDATE,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> canceldate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=CANCELEDBY)
    private final StringProperty canceledby = new SimpleStringProperty("");
    @Dup
    @Col(name=TYPE)
    private final StringProperty type = new SimpleStringProperty("");
    @Dup
    @Col(name=USER)
    private final StringProperty user = new SimpleStringProperty("");
    @Dup
    @Col(name=REMARKS)
    private final StringProperty remarks = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=ITEM_ID,Class = int.class)
    private final IntegerProperty item_id = new SimpleIntegerProperty(0);    
    @Dup(Class = int.class)
    @Col(name=SUPPLIER_ID,Class = int.class)
    private final IntegerProperty supplier_id = new SimpleIntegerProperty(0);    
    @Dup(Class = int.class)
    @Col(name=USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);

    public String getCanceledby() {
        return canceledby.get();
    }

    public void setCanceledby(String value) {
        canceledby.set(value);
    }

    public StringProperty canceledbyProperty() {
        return canceledby;
    }
    

    public LocalDateTime getCanceldate() {
        return canceldate.get();
    }

    public void setCanceldate(LocalDateTime value) {
        canceldate.set(value);
    }

    public ObjectProperty canceldateProperty() {
        return canceldate;
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
    

    public String getReturnedto() {
        return returnedto.get();
    }

    public void setReturnedto(String value) {
        returnedto.set(value);
    }

    public StringProperty returnedtoProperty() {
        return returnedto;
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
    

    public String getRecievedby() {
        return recievedby.get();
    }

    public void setRecievedby(String value) {
        recievedby.set(value);
    }

    public StringProperty recievedbyProperty() {
        return recievedby;
    }
    

    public String getDeliveredby() {
        return deliveredby.get();
    }

    public void setDeliveredby(String value) {
        deliveredby.set(value);
    }

    public StringProperty deliveredbyProperty() {
        return deliveredby;
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
    
    

    public int getUser_id() {
        return user_id.get();
    }

    public void setUser_id(int value) {
        user_id.set(value);
    }

    public IntegerProperty user_idProperty() {
        return user_id;
    }
    

    public int getSupplier_id() {
        return supplier_id.get();
    }

    public void setSupplier_id(int value) {
        supplier_id.set(value);
    }

    public IntegerProperty supplier_idProperty() {
        return supplier_id;
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
    

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }
    

    public LocalDate getReturndate() {
        return returndate.get();
    }

    public void setReturndate(LocalDate value) {
        returndate.set(value);
    }

    public ObjectProperty returndateProperty() {
        return returndate;
    }
    
    
    public LocalDate getExpiration() {
        return expiration.get();
    }

    public void setExpiration(LocalDate value) {
        expiration.set(value);
    }

    public ObjectProperty expirationProperty() {
        return expiration;
    }
    
    
    public LocalDate getDeliverydate() {
        return deliverydate.get();
    }

    public void setDeliverydate(LocalDate value) {
        deliverydate.set(value);
    }

    public ObjectProperty deliverydateProperty() {
        return deliverydate;
    }
        
    public int getItem_id() {
        return item_id.get();
    }

    public void setItem_id(int value) {
        item_id.set(value);
    }

    public IntegerProperty item_idProperty() {
        return item_id;
    }
    
    public double getItemselling() {
        return itemselling.get();
    }

    public void setItemselling(double value) {
        itemselling.set(value);
    }

    public DoubleProperty itemsellingProperty() {
        return itemselling;
    }
    
    public double getItemcost() {
        return itemcost.get();
    }

    public void setItemcost(double value) {
        itemcost.set(value);
    }

    public DoubleProperty itemcostProperty() {
        return itemcost;
    }
    public int getItemqty() {
        return itemqty.get();
    }

    public void setItemqty(int value) {
        itemqty.set(value);
    }

    public IntegerProperty itemqtyProperty() {
        return itemqty;
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
    
    public double getCost() {
        return cost.get();
    }

    public void setCost(double value) {
        cost.set(value);
    }

    public DoubleProperty costProperty() {
        return cost;
    }
    
    public String getUnits() {
        return units.get();
    }

    public void setUnits(String value) {
        units.set(value);
    }

    public StringProperty unitsProperty() {
        return units;
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
    
    public String getReferrence() {
        return referrence.get();
    }

    public void setReferrence(String value) {
        referrence.set(value);
    }

    public StringProperty referrenceProperty() {
        return referrence;
    }
    
    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String value) {
        supplier.set(value);
    }

    public StringProperty supplierProperty() {
        return supplier;
    }
    
    
    public static void createTableFilter(JFXTextField field,FilteredList<DeliveryItem> filteredRecords ){
        try{
            ObjectProperty<Predicate<DeliveryItem>> supplierFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<DeliveryItem>> refFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<DeliveryItem>> userFilter = new SimpleObjectProperty<>();            
            ObjectProperty<Predicate<DeliveryItem>> descFilter = new SimpleObjectProperty<>();
            
            supplierFilter.bind(Bindings.createObjectBinding(()-> record -> record.getSupplier().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            refFilter.bind(Bindings.createObjectBinding(()-> record -> record.getReferrence().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            descFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> refFilter.get().or(userFilter.get()).or(supplierFilter.get()).or(descFilter.get()),refFilter, userFilter,supplierFilter,descFilter));
        }catch(Exception er){
            Logger.getLogger(DeliveryItem.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
