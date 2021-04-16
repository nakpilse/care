/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Nsoft
 */
public class PharmacyOrder extends SQLModel<PharmacyOrder>{
    
    public static final String TABLE_NAME = "pharmacyorders";
    public static final String JOIN_KEY = "pharmacyorder_id";
    
    
    public static final String ORDERTIME = "ordertime";
    public static final String ORDERFACILITY = "orderfacility";
    public static final String ORDEREDBY = "orderedby";
    public static final String PATIENTNAME = "patientname";
    public static final String ENCODER = "encoder";
    public static final String DONE = "done";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    public static final String ERCONSULTATION_ID = ERConsultation.JOIN_KEY;
    public static final String USER_ID = User.JOIN_KEY;
    public static final String HOSPITALCHARGE_ID = HospitalCharge.JOIN_KEY;
    
    
    @Dup(Class = LocalDateTime.class)
    @Col(name=ORDERTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> ordertime = new SimpleObjectProperty<>(null);    
    @Dup
    @Col(name=ORDERFACILITY)
    private final StringProperty orderfacility = new SimpleStringProperty("");    
    @Dup
    @Col(name=ORDEREDBY)
    private final StringProperty orderedby = new SimpleStringProperty("");    
    @Dup
    @Col(name=PATIENTNAME)
    private final StringProperty patientname = new SimpleStringProperty("");    
    @Dup
    @Col(name=ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup(Class = boolean.class)
    @Col(name=DONE,Class = boolean.class)
    private final BooleanProperty done = new SimpleBooleanProperty(false);
    @Dup(Class = int.class)
    @Col(name=PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name=HOSPITALCHARGE_ID,Class = int.class)
    private final IntegerProperty hospitalcharge_id = new SimpleIntegerProperty(0);

    
    //Non-Table Variables
    private final ListProperty<PharmacyOrderItem> items = new SimpleListProperty<>();

    public ObservableList getItems() {
        return items.get();
    }

    public void setItems(ObservableList value) {
        items.set(value);
    }

    public ListProperty itemsProperty() {
        return items;
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
    
    

    public int getUser_id() {
        return user_id.get();
    }

    public void setUser_id(int value) {
        user_id.set(value);
    }

    public IntegerProperty user_idProperty() {
        return user_id;
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
    

    public boolean isDone() {
        return done.get();
    }

    public void setDone(boolean value) {
        done.set(value);
    }

    public BooleanProperty doneProperty() {
        return done;
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
    
    
    public String getPatientname() {
        return patientname.get();
    }

    public void setPatientname(String value) {
        patientname.set(value);
    }

    public StringProperty patientnameProperty() {
        return patientname;
    }
    

    public String getOrderedby() {
        return orderedby.get();
    }

    public void setOrderedby(String value) {
        orderedby.set(value);
    }

    public StringProperty orderedbyProperty() {
        return orderedby;
    }
    

    public String getOrderfacility() {
        return orderfacility.get();
    }

    public void setOrderfacility(String value) {
        orderfacility.set(value);
    }

    public StringProperty orderfacilityProperty() {
        return orderfacility;
    }
    

    public LocalDateTime getOrdertime() {
        return ordertime.get();
    }

    public void setOrdertime(LocalDateTime value) {
        ordertime.set(value);
    }

    public ObjectProperty ordertimeProperty() {
        return ordertime;
    }
    
    
    
}
