package models;

import java.time.LocalDateTime;
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
public class RoomRecord extends SQLModel<RoomRecord>{
    
    
    public static final String TABLE_NAME = "roomrecords";
    public static final String JOIN_KEY = "roomrecord_id";
    
    public static final String ROOM = "room";
    public static final String BED = "bed";
    public static final String RATE = "rate";
    public static final String BASIS = "basis";
    public static final String TIMESTART = "timestart";
    public static final String TIMEEND = "timeend";
    public static final String ENCODER = "encoder";
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    
    
    @Dup
    @Col(name=ROOM)
    private final StringProperty room = new SimpleStringProperty("");
    @Dup
    @Col(name=BED)
    private final StringProperty bed = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=RATE,Class = double.class)
    private final DoubleProperty rate = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=BASIS,Class = double.class)
    private final DoubleProperty basis = new SimpleDoubleProperty(0);
    @Dup(Class = LocalDateTime.class)
    @Col(name=TIMESTART,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> timestart = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name=TIMEEND,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> timeend = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name=ADMISSION_ID,Class = int.class)
    private final IntegerProperty admission_id = new SimpleIntegerProperty(0);

    public int getAdmission_id() {
        return admission_id.get();
    }

    public void setAdmission_id(int value) {
        admission_id.set(value);
    }

    public IntegerProperty admission_idProperty() {
        return admission_id;
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
    
    public LocalDateTime getTimeend() {
        return timeend.get();
    }

    public void setTimeend(LocalDateTime value) {
        timeend.set(value);
    }

    public ObjectProperty timeendProperty() {
        return timeend;
    }
    

    public LocalDateTime getTimestart() {
        return timestart.get();
    }

    public void setTimestart(LocalDateTime value) {
        timestart.set(value);
    }

    public ObjectProperty timestartProperty() {
        return timestart;
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
    

    public double getRate() {
        return rate.get();
    }

    public void setRate(double value) {
        rate.set(value);
    }

    public DoubleProperty rateProperty() {
        return rate;
    }
    

    public String getBed() {
        return bed.get();
    }

    public void setBed(String value) {
        bed.set(value);
    }

    public StringProperty bedProperty() {
        return bed;
    }
    

    public String getRoom() {
        return room.get();
    }

    public void setRoom(String value) {
        room.set(value);
    }

    public StringProperty roomProperty() {
        return room;
    }
    
    
    
}
