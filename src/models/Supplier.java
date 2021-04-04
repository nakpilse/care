package models;

import com.jfoenix.controls.JFXTextField;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class Supplier extends SQLModel<Supplier> {
    public static final String TABLE_NAME = "suppliers";
    public static final String JOIN_KEY = "supplier_id";
    
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String TIN = "tin";
    public static final String ADDRESS = "address";
    public static final String CONTACTPERSON = "contactperson";
    public static final String CONTACTNUMBER = "contactnumber";
    public static final String CONTACTEMAIL = "contactemail";
    public static final String PAYMENTTERMS = "paymentterms";
    public static final String ARCHIVE = "archive";
    
    @Dup
    @Col(name=CODE)
    private final StringProperty code = new SimpleStringProperty("");
    @Dup
    @Col(name=NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup
    @Col(name=TIN)
    private final StringProperty tin = new SimpleStringProperty("");
    @Dup
    @Col(name=ADDRESS)
    private final StringProperty address = new SimpleStringProperty("");
    @Dup
    @Col(name=CONTACTPERSON)
    private final StringProperty contactperson = new SimpleStringProperty("");
    @Dup
    @Col(name=CONTACTNUMBER)
    private final StringProperty contactnumber = new SimpleStringProperty("");
    @Dup
    @Col(name=CONTACTEMAIL)
    private final StringProperty contactemail = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=PAYMENTTERMS,Class = int.class)
    private final IntegerProperty paymentterms = new SimpleIntegerProperty(30);
    @Dup(Class = boolean.class)
    @Col(name=ARCHIVE,Class = boolean.class)
    private final BooleanProperty archive = new SimpleBooleanProperty(false);

    public boolean isArchive() {
        return archive.get();
    }

    public void setArchive(boolean value) {
        archive.set(value);
    }

    public BooleanProperty archiveProperty() {
        return archive;
    }
    

    public int getPaymentterms() {
        return paymentterms.get();
    }

    public void setPaymentterms(int value) {
        paymentterms.set(value);
    }

    public IntegerProperty paymenttermsProperty() {
        return paymentterms;
    }
    
    
    public String getContactemail() {
        return contactemail.get();
    }

    public void setContactemail(String value) {
        contactemail.set(value);
    }

    public StringProperty contactemailProperty() {
        return contactemail;
    }

    public String getContactnumber() {
        return contactnumber.get();
    }

    public void setContactnumber(String value) {
        contactnumber.set(value);
    }

    public StringProperty contactnumberProperty() {
        return contactnumber;
    }

    public String getContactperson() {
        return contactperson.get();
    }

    public void setContactperson(String value) {
        contactperson.set(value);
    }

    public StringProperty contactpersonProperty() {
        return contactperson;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getTin() {
        return tin.get();
    }

    public void setTin(String value) {
        tin.set(value);
    }

    public StringProperty tinProperty() {
        return tin;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
        return code;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<Supplier> filteredRecords ){
        try{
            ObjectProperty<Predicate<Supplier>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Supplier>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Supplier>> personFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCode().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getName().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            personFilter.bind(Bindings.createObjectBinding(()-> record -> record.getContactperson().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(personFilter.get()).or(codeFilter.get()),nameFilter, personFilter,codeFilter));
        }catch(Exception er){
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
    
}
