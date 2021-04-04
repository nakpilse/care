package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class Bed extends SQLModel<Bed>{
    
    public static final String TABLE_NAME = "beds";
    public static final String JOIN_KEY = "bed_id";
    
    public static final String NAME = "name";
    public static final String BASIS = "basis";    
    public static final String RATE = "rate";
    public static final String AVAILABLE = "available";
    public static final String ROOM_ID = Room.JOIN_KEY;
    
    
    @Dup
    @Col(name=NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=BASIS,Class = double.class)
    private final DoubleProperty basis = new SimpleDoubleProperty(12);
    @Dup(Class = double.class)
    @Col(name=RATE,Class = double.class)
    private final DoubleProperty rate = new SimpleDoubleProperty(0);
    @Dup(Class = boolean.class)
    @Col(name=AVAILABLE,Class = boolean.class)
    private final BooleanProperty available = new SimpleBooleanProperty(true);
    @Dup(Class = int.class)
    @Col(name=ROOM_ID,Class = int.class)
    private final IntegerProperty room_id = new SimpleIntegerProperty(0);

    public double getRate() {
        return rate.get();
    }

    public void setRate(double value) {
        rate.set(value);
    }

    public DoubleProperty rateProperty() {
        return rate;
    }
    

    public int getRoom_id() {
        return room_id.get();
    }

    public void setRoom_id(int value) {
        room_id.set(value);
    }

    public IntegerProperty room_idProperty() {
        return room_id;
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
    

    public double getBasis() {
        return basis.get();
    }

    public void setBasis(double value) {
        basis.set(value);
    }

    public DoubleProperty basisProperty() {
        return basis;
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
    
    
    @Override
    public String toString() {
        return name.get();
    }
    
}
