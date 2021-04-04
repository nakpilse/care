/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Nsoft
 */
public class AdmissionCounter extends SQLModel<AdmissionCounter>{
    public static final String TABLE_NAME = "admissioncounters";
    public static final String JOIN_KEY = "admissioncounter_id";
    
    public static final String RECORDDATE = "recorddate";
    public static final String CURRENTADMISSION = "currentadmission";
    public static final String NEWADMISSION = "newadmission";
    public static final String DISCHARGED = "discharged";
    
    @Dup(Class = LocalDate.class)
    @Col(name = RECORDDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> recorddate = new SimpleObjectProperty<>();
    @Dup(Class = int.class)
    @Col(name = CURRENTADMISSION,Class = int.class)
    private final IntegerProperty currentadmission = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = NEWADMISSION,Class = int.class)
    private final IntegerProperty newadmission = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = DISCHARGED,Class = int.class)
    private final IntegerProperty discharged = new SimpleIntegerProperty(0);

    public int getDischarged() {
        return discharged.get();
    }

    public void setDischarged(int value) {
        discharged.set(value);
    }

    public IntegerProperty dischargedProperty() {
        return discharged;
    }
    

    public int getNewadmission() {
        return newadmission.get();
    }

    public void setNewadmission(int value) {
        newadmission.set(value);
    }

    public IntegerProperty newadmissionProperty() {
        return newadmission;
    }
    

    public int getCurrentadmission() {
        return currentadmission.get();
    }

    public void setCurrentadmission(int value) {
        currentadmission.set(value);
    }

    public IntegerProperty currentadmissionProperty() {
        return currentadmission;
    }
    

    public LocalDate getRecorddate() {
        return recorddate.get();
    }

    public void setRecorddate(LocalDate value) {
        recorddate.set(value);
    }

    public ObjectProperty recorddateProperty() {
        return recorddate;
    }
    
    
}
