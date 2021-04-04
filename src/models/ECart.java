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

/**
 *
 * @author Duskmourne
 */
public class ECart extends SQLModel<ECart>{
    
    public static final String TABLE_NAME = "ecarts";
    public static final String JOIN_KEY = "ecart_id";
    
    public static final String ECARTNUMBER = "ecartnumber";
    public static final String ECARTTIME = "ecarttime";
    public static final String FROMFACILITY = "fromfacility";
    public static final String TOFACILITY = "tofacility";
    public static final String REQUESTEDBY = "requestedby";
    public static final String APPROVEDBY = "approvedby";
    public static final String DESCRIPTION = "description";
    public static final String QUANTITY = "quantity";
    public static final String ITEMTABLE = "itemtable";
    public static final String ITEMTABLEID = "itemtableid";
    public static final String USER = "user";
    public static final String USER_ID = User.JOIN_KEY;
    
    
    @Dup
    @Col(name=ECARTNUMBER)
    private final StringProperty ecartnumber = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name=ECARTTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> ecarttime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=FROMFACILITY)
    private final StringProperty fromfacility = new SimpleStringProperty("");
    @Dup
    @Col(name=TOFACILITY)
    private final StringProperty tofacility = new SimpleStringProperty("");
    @Dup
    @Col(name=REQUESTEDBY)
    private final StringProperty requestedby = new SimpleStringProperty("");
    @Dup
    @Col(name=APPROVEDBY)
    private final StringProperty approvedby = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=ITEMTABLE)
    private final StringProperty itemtable = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=ITEMTABLEID,Class = int.class)
    private final IntegerProperty itemtableid = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);
    @Dup
    @Col(name=USER)
    private final StringProperty user = new SimpleStringProperty("");

    public String getUser() {
        return user.get();
    }

    public void setUser(String value) {
        user.set(value);
    }

    public StringProperty userProperty() {
        return user;
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
    

    public String getApprovedby() {
        return approvedby.get();
    }

    public void setApprovedby(String value) {
        approvedby.set(value);
    }

    public StringProperty approvedbyProperty() {
        return approvedby;
    }
    
    

    public String getRequestedby() {
        return requestedby.get();
    }

    public void setRequestedby(String value) {
        requestedby.set(value);
    }

    public StringProperty requestedbyProperty() {
        return requestedby;
    }
    

    public String getTofacility() {
        return tofacility.get();
    }

    public void setTofacility(String value) {
        tofacility.set(value);
    }

    public StringProperty tofacilityProperty() {
        return tofacility;
    }
    

    public String getFromfacility() {
        return fromfacility.get();
    }

    public void setFromfacility(String value) {
        fromfacility.set(value);
    }

    public StringProperty fromfacilityProperty() {
        return fromfacility;
    }
    

    public LocalDateTime getEcarttime() {
        return ecarttime.get();
    }

    public void setEcarttime(LocalDateTime value) {
        ecarttime.set(value);
    }

    public ObjectProperty ecarttimeProperty() {
        return ecarttime;
    }
    

    public String getEcartnumber() {
        return ecartnumber.get();
    }

    public void setEcartnumber(String value) {
        ecartnumber.set(value);
    }

    public StringProperty ecartnumberProperty() {
        return ecartnumber;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<ECart> filteredRecords ){
        try{
            ObjectProperty<Predicate<ECart>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ECart>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ECart>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ECart>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ECart>> reqestorFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getEcartnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getTofacility().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            reqestorFilter.bind(Bindings.createObjectBinding(()-> record -> record.getRequestedby().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getUser().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(codeFilter.get()).or(userFilter.get()).or(reqestorFilter.get()),nameFilter, desFilter,codeFilter,userFilter,reqestorFilter));
        }catch(Exception er){
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, er);
        }
    }
}
