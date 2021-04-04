package models;

import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.mysql.SQLTable;

/**
 *
 * @author Duskmourne
 */
public class Room extends SQLModel<Room>{
    
    
    public static final String TABLE_NAME = "rooms";
    public static final String JOIN_KEY = "room_id";
    
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String FLOOR = "floor";
    public static final String COLORVALUE = "colorvalue";
    public static final String AVAILABLE = "available";
    
    
    @Dup
    @Col(name=NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup
    @Col(name=TYPE)
    private final StringProperty type = new SimpleStringProperty("");
    @Dup
    @Col(name=FLOOR)
    private final StringProperty floor = new SimpleStringProperty("");
    @Dup
    @Col(name=COLORVALUE)
    private final StringProperty colorvalue = new SimpleStringProperty("");
    @Dup(Class = boolean.class)
    @Col(name=AVAILABLE,Class = boolean.class)
    private final BooleanProperty available = new SimpleBooleanProperty(true);

    public String getColorvalue() {
        return colorvalue.get();
    }

    public void setColorvalue(String value) {
        colorvalue.set(value);
    }

    public StringProperty colorvalueProperty() {
        return colorvalue;
    }
    
    

    public boolean isAvailable() {
        return available.get();
    }

    public void setAvailable(boolean value) {
        available.set(value);
    }

    public BooleanProperty availableProperty() {
        return available;
    }
    

    public String getFloor() {
        return floor.get();
    }

    public void setFloor(String value) {
        floor.set(value);
    }

    public StringProperty floorProperty() {
        return floor;
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
    

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    
    public List<Bed> getBeds(){
        List<Bed> beds = new ArrayList();
        try{
            beds = SQLTable.list(Bed.class, Bed.ROOM_ID+"='"+this.getId()+"'");
            return beds;
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            return beds;
        }
    }
    
    public List<Patient> getOccupants(){
        List<Patient> patients = new ArrayList();
        try{
            patients = SQLTable.list(Patient.class, Patient.ROOM+"='"+this.getName()+"'");
            return patients;
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
            return patients;
        }
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<Room> filteredRecords ){
        try{
            ObjectProperty<Predicate<Room>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Room>> typeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Room>> floorFilter = new SimpleObjectProperty<>();
            
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getName().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            typeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getType().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            floorFilter.bind(Bindings.createObjectBinding(()-> record -> record.getFloor().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(typeFilter.get()).or(floorFilter.get()),nameFilter, typeFilter,floorFilter));
        }catch(Exception er){
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return name.get();
    }
    
    
}
