package models;

import alpha.Care;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;

/**
 *
 * @author Duskmourne
 */
public class Patient extends SQLModel<Patient>{
    
    public static final String TABLE_NAME = "patients";
    public static final String JOIN_KEY = "patient_id";
    
    public static final String CODE = "code";
    public static final String FIRSTNAME = "firstname";
    public static final String MIDDLENAME = "middlename";
    public static final String LASTNAME = "lastname";
    public static final String ADDRESS = "address";
    public static final String CITYMUNICIPALITY = "citymunicipality";
    public static final String STATEPROVINCE = "stateprovince";
    public static final String BIRTHDATE = "birthdate";
    public static final String GENDER = "gender";
    public static final String CIVILSTATUS = "civilstatus";
    public static final String RELIGION = "religion";
    public static final String MOBILE = "mobile";
    public static final String LANDLINE = "landline";
    public static final String EMAIL = "email";
    
    public static final String RECENTADMISSION = "recentadmission";
    public static final String RECENTCONSULTATION = "recentconsultation";
    public static final String RECENTDISCHARGE = "recentdischarge";
    public static final String RECENTERCASE = "recentercase";
    public static final String ADMITTED = "admitted";
    public static final String ROOM = "room";
    public static final String BED = "bed";
    public static final String EMPLOYEE = "employee";
    public static final String SENIORID = "seniorid";
    public static final String PWDID = "pwdid";
    public static final String PHILHEALTHID = "philhealthid";
    public static final String SSSID = "sssid";
    
    public Patient(){
        birthdate.addListener((obs,oldVal,newVal)->{
            if(newVal != null){
                DateTimeKit.computeAge(newVal, DateTimeKit.CURRENT_DATE, null);
            }else{
                age.set(0);
            }
        });
        firstname.addListener(listener ->{
            this.setFullname(lastname.getValue()+", "+firstname.getValue()+((middlename.getValue().isEmpty())? "":" "+middlename.getValue()));
        });
        middlename.addListener(listener ->{
            this.setFullname(lastname.getValue()+", "+firstname.getValue()+((middlename.getValue().isEmpty())? "":" "+middlename.getValue()));
        });
        lastname.addListener(listener ->{
            this.setFullname(lastname.getValue()+", "+firstname.getValue()+((middlename.getValue().isEmpty())? "":" "+middlename.getValue()));
        });
    }
        
    @Dup
    @Col(name = CODE)
    private final StringProperty code = new SimpleStringProperty("");
    @Dup
    @Col(name = FIRSTNAME)
    private final StringProperty firstname = new SimpleStringProperty("");
    @Dup
    @Col(name = MIDDLENAME)
    private final StringProperty middlename = new SimpleStringProperty("");
    @Dup
    @Col(name = LASTNAME)
    private final StringProperty lastname = new SimpleStringProperty("");
    @Dup
    @Col(name = GENDER)
    private final StringProperty gender = new SimpleStringProperty("Male");
    @Dup(Class = LocalDate.class)
    @Col(name = BIRTHDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = ADDRESS)
    private final StringProperty address = new SimpleStringProperty("");
    @Dup
    @Col(name = CITYMUNICIPALITY)
    private final StringProperty citymunicipality = new SimpleStringProperty("");
    @Dup
    @Col(name = STATEPROVINCE)
    private final StringProperty stateprovince = new SimpleStringProperty("");
    @Dup
    @Col(name = CIVILSTATUS)
    private final StringProperty civilstatus = new SimpleStringProperty("Single");
    @Dup
    @Col(name = RELIGION)
    private final StringProperty religion = new SimpleStringProperty("");
    @Dup
    @Col(name = MOBILE)
    private final StringProperty mobile = new SimpleStringProperty("");
    @Dup
    @Col(name = LANDLINE)
    private final StringProperty landline = new SimpleStringProperty("");
    @Dup
    @Col(name = EMAIL)
    private final StringProperty email = new SimpleStringProperty("");    
    @Dup(Class = LocalDateTime.class)
    @Col(name = RECENTADMISSION,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> recentadmission = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = RECENTCONSULTATION,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> recentconsultation = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = RECENTDISCHARGE,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> recentdischarge = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = RECENTERCASE,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> recentercase = new SimpleObjectProperty(null);
    @Dup
    @Col(name = ROOM)
    private final StringProperty room = new SimpleStringProperty("");
    @Dup
    @Col(name = BED)
    private final StringProperty bed = new SimpleStringProperty("");
    @Dup(Class = boolean.class)
    @Col(name = ADMITTED,Class = boolean.class)
    private final BooleanProperty admitted = new SimpleBooleanProperty(false);
    @Dup(Class = boolean.class)
    @Col(name = EMPLOYEE,Class = boolean.class)
    private final BooleanProperty employee = new SimpleBooleanProperty(false);
    @Dup
    @Col(name = SENIORID)
    private final StringProperty seniorid = new SimpleStringProperty("");
    @Dup
    @Col(name = PWDID)
    private final StringProperty pwdid = new SimpleStringProperty("");
    
    //Non Table Column
    @Dup
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    @Dup
    private final StringProperty fullname = new SimpleStringProperty("");

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
    
    


    public String getPwdid() {
        return pwdid.get();
    }

    public void setPwdid(String value) {
        pwdid.set(value);
    }

    public StringProperty pwdidProperty() {
        return pwdid;
    }
    
    public String getSeniorid() {
        return seniorid.get();
    }

    public void setSeniorid(String value) {
        seniorid.set(value);
    }

    public StringProperty senioridProperty() {
        return seniorid;
    }
    

    public boolean isEmployee() {
        return employee.get();
    }

    public void setEmployee(boolean value) {
        employee.set(value);
    }

    public BooleanProperty employeeProperty() {
        return employee;
    }
    

    public boolean isAdmitted() {
        return admitted.get();
    }

    public void setAdmitted(boolean value) {
        admitted.set(value);
    }

    public BooleanProperty admittedProperty() {
        return admitted;
    }
    

    public LocalDateTime getRecentdischarge() {
        return recentdischarge.get();
    }

    public void setRecentdischarge(LocalDateTime value) {
        recentdischarge.set(value);
    }

    public ObjectProperty recentdischargeProperty() {
        return recentdischarge;
    }
    

    public LocalDateTime getRecentercase() {
        return recentercase.get();
    }

    public void setRecentercase(LocalDateTime value) {
        recentercase.set(value);
    }

    public ObjectProperty recentercaseProperty() {
        return recentercase;
    }
    

    public LocalDateTime getRecentconsultation() {
        return recentconsultation.get();
    }

    public void setRecentconsultation(LocalDateTime value) {
        recentconsultation.set(value);
    }

    public ObjectProperty recentconsultationProperty() {
        return recentconsultation;
    }
    

    public LocalDateTime getRecentadmission() {
        return recentadmission.get();
    }

    public void setRecentadmission(LocalDateTime value) {
        recentadmission.set(value);
    }

    public ObjectProperty recentadmissionProperty() {
        return recentadmission;
    }
    
    
    
    public String getFullname() {
        return fullname.get();
    }

    public void setFullname(String value) {
        fullname.set(value);
    }

    public StringProperty fullnameProperty() {
        return fullname;
    }
    
    
    public int getAge() {
        return age.get();
    }

    public void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }
    

    public String getStateprovince() {
        return stateprovince.get();
    }

    public void setStateprovince(String value) {
        stateprovince.set(value);
    }

    public StringProperty stateprovinceProperty() {
        return stateprovince;
    }
    

    public String getCitymunicipality() {
        return citymunicipality.get();
    }

    public void setCitymunicipality(String value) {
        citymunicipality.set(value);
    }

    public StringProperty citymunicipalityProperty() {
        return citymunicipality;
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
    

    public LocalDate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(LocalDate value) {
        birthdate.set(value);
    }

    public ObjectProperty birthdateProperty() {
        return birthdate;
    }
    

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    

    public String getLandline() {
        return landline.get();
    }

    public void setLandline(String value) {
        landline.set(value);
    }

    public StringProperty landlineProperty() {
        return landline;
    }
    

    public String getMobile() {
        return mobile.get();
    }

    public void setMobile(String value) {
        mobile.set(value);
    }

    public StringProperty mobileProperty() {
        return mobile;
    }
    

    public String getReligion() {
        return religion.get();
    }

    public void setReligion(String value) {
        religion.set(value);
    }

    public StringProperty religionProperty() {
        return religion;
    }
    

    public String getCivilstatus() {
        return civilstatus.get();
    }

    public void setCivilstatus(String value) {
        civilstatus.set(value);
    }

    public StringProperty civilstatusProperty() {
        return civilstatus;
    }
    

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }
    

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String value) {
        lastname.set(value);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }
    

    public String getMiddlename() {
        return middlename.get();
    }

    public void setMiddlename(String value) {
        middlename.set(value);
    }

    public StringProperty middlenameProperty() {
        return middlename;
    }
    

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String value) {
        firstname.set(value);
    }

    public StringProperty firstnameProperty() {
        return firstname;
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

    @Override
    public String toString() {
        return fullname.get();
    }
    
    public Admission getLatestAdmission(){        
        try{            
            Admission record = (Admission)SQLTable.getLatestRecordOf(Admission.class, Admission.PATIENT_ID, String.valueOf(this.getId()));
            return record;
        }catch(Exception er){
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<Patient> filteredRecords ){
        try{
            ObjectProperty<Predicate<Patient>> snameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Patient>> fnameFilter = new SimpleObjectProperty<>();            
            ObjectProperty<Predicate<Patient>> mnameFilter = new SimpleObjectProperty<>();
            
            mnameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getMiddlename().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            snameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getLastname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            fnameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getFirstname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> snameFilter.get().or(fnameFilter.get()).or(mnameFilter.get()),snameFilter, fnameFilter,mnameFilter));
        }catch(Exception er){
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void copy(Patient obj){
        try{
            this.setId(obj.getId());
            this.setCode(obj.getCode());
            this.setFirstname(obj.getFirstname());
            this.setMiddlename(obj.getMiddlename());
            this.setLastname(obj.getLastname());
            this.setFullname(obj.getFullname());
            this.setGender(obj.getGender());
            this.setBirthdate(obj.getBirthdate());
            this.setAddress(obj.getAddress());
            this.setCitymunicipality(obj.getCitymunicipality());
            this.setStateprovince(obj.getStateprovince());
            this.setCivilstatus(obj.getCivilstatus());
            this.setReligion(obj.getReligion());
            this.setMobile(obj.getMobile());
            this.setLandline(obj.getLandline());
            this.setRecentadmission(obj.getRecentadmission());
            this.setRecentconsultation(obj.getRecentconsultation());
            this.setRecentdischarge(obj.getRecentdischarge());
            this.setRoom(obj.getRoom());
            this.setBed(obj.getBed());
            this.setAdmitted(obj.isAdmitted());
            this.setEmployee(obj.isEmployee());
            this.setSeniorid(obj.getSeniorid());
            this.setPwdid(obj.getPwdid());
            
        }catch(Exception er){
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void main(String args[]){
        
    }
}
