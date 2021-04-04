package models;

import com.jfoenix.controls.JFXTextField;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import nse.dcfx.utils.DateTimeKit;

/**
 *
 * @author Duskmourne
 */
public class Admission extends SQLModel<Admission>{
    
    public static final String TABLE_NAME = "admissions";
    public static final String JOIN_KEY = "admission_id";
    
    public static final String PATIENTNAME = "patientname";
    public static final String ADMISSIONNUMBER = "admissionnumber";
    public static final String PREADMISSIONDIAGNOSIS = "preadmissiondiagnosis";
    public static final String PREADMISSIONCOMPLAINS = "preadmissioncomplains";
    public static final String PREADMISSIONCONDITION = "preadmissioncondition";
    public static final String ADMISSIONTIME = "admissiontime";
    public static final String ADMITTEDBY = "admittedby";
    public static final String ADMITTEDBYRELATION = "admittedbyrelation";
    public static final String DISCHARGETIME = "dischargetime";
    public static final String DISCHARGESUMMARY = "dischargesummary";
    public static final String PROVISIONALDIAGNOSIS = "provisionaldiagnosis";
    public static final String FINALDIAGNOSIS = "finaldiagnosis";
    public static final String OTHERDIAGNOSIS = "otherdiagnosis";
    public static final String DISCHARGENOTES = "dischargenotes";
    public static final String DISCHARGEDBY = "dischargedby";
    public static final String ENCODER = "encoder";
    public static final String PHYSICIAN = "physician";
    public static final String ADMISSIONCASE = "admissioncase";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String AGESTRING = "agestring";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String HOSPITALPERSONEL_ID = HospitalPersonel.JOIN_KEY;
    public static final String USER_ID = User.JOIN_KEY;
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    
    
    @Dup
    @Col(name = PATIENTNAME)
    private final StringProperty patientname = new SimpleStringProperty("");
    @Dup
    @Col(name = ADMISSIONNUMBER)
    private final StringProperty admissionnumber = new SimpleStringProperty("");
    @Dup
    @Col(name = PREADMISSIONDIAGNOSIS)
    private final StringProperty preadmissiondiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = PREADMISSIONCOMPLAINS)
    private final StringProperty preadmissioncomplains = new SimpleStringProperty("");
    @Dup
    @Col(name = PREADMISSIONCONDITION)
    private final StringProperty preadmissioncondition = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = ADMISSIONTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> admissiontime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = ADMITTEDBY)
    private final StringProperty admittedby = new SimpleStringProperty("");
    @Dup
    @Col(name = ADMITTEDBYRELATION)
    private final StringProperty admittedbyrelation = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = DISCHARGETIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> dischargetime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = DISCHARGESUMMARY)
    private final StringProperty dischargesummary = new SimpleStringProperty("");
    @Dup
    @Col(name = PROVISIONALDIAGNOSIS)
    private final StringProperty provisionaldiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = FINALDIAGNOSIS)
    private final StringProperty finaldiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = OTHERDIAGNOSIS)
    private final StringProperty otherdiagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = DISCHARGENOTES)
    private final StringProperty dischargenotes = new SimpleStringProperty("");
    @Dup
    @Col(name = DISCHARGEDBY)
    private final StringProperty dischargedby = new SimpleStringProperty("");
    @Dup
    @Col(name = ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup
    @Col(name = PHYSICIAN)
    private final StringProperty physician = new SimpleStringProperty("");
    @Dup
    @Col(name = ADMISSIONCASE)
    private final StringProperty admissioncase = new SimpleStringProperty("");
    @Dup
    @Col(name = GENDER)
    private final StringProperty gender = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = AGE,Class = int.class)
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = AGESTRING)
    private final StringProperty agestring = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = HOSPITALPERSONEL_ID,Class = int.class)
    private final IntegerProperty hospitalpersonel_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);
    @Dup(Class = LocalDateTime.class)
    @Col(name = CREATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> created_at = new SimpleObjectProperty<>(null);
    @Dup(Class = LocalDateTime.class)
    @Col(name = UPDATED_AT,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> updated_at = new SimpleObjectProperty<>(null);

    public String getOtherdiagnosis() {
        return otherdiagnosis.get();
    }

    public void setOtherdiagnosis(String value) {
        otherdiagnosis.set(value);
    }

    public StringProperty otherdiagnosisProperty() {
        return otherdiagnosis;
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
    
    

    public String getAgestring() {
        return agestring.get();
    }

    public void setAgestring(String value) {
        agestring.set(value);
    }

    public StringProperty agestringProperty() {
        return agestring;
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
    

    public LocalDateTime getUpdated_at() {
        return updated_at.get();
    }

    public void setUpdated_at(LocalDateTime value) {
        updated_at.set(value);
    }

    public ObjectProperty updated_atProperty() {
        return updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at.get();
    }

    public void setCreated_at(LocalDateTime value) {
        created_at.set(value);
    }

    public ObjectProperty created_atProperty() {
        return created_at;
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
    

    public int getHospitalpersonel_id() {
        return hospitalpersonel_id.get();
    }

    public void setHospitalpersonel_id(int value) {
        hospitalpersonel_id.set(value);
    }

    public IntegerProperty hospitalpersonel_idProperty() {
        return hospitalpersonel_id;
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
    

    public String getAdmissioncase() {
        return admissioncase.get();
    }

    public void setAdmissioncase(String value) {
        admissioncase.set(value);
    }

    public StringProperty admissioncaseProperty() {
        return admissioncase;
    }
    

    public String getPhysician() {
        return physician.get();
    }

    public void setPhysician(String value) {
        physician.set(value);
    }

    public StringProperty physicianProperty() {
        return physician;
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
    

    public String getDischargedby() {
        return dischargedby.get();
    }

    public void setDischargedby(String value) {
        dischargedby.set(value);
    }

    public StringProperty dischargedbyProperty() {
        return dischargedby;
    }
    

    public String getDischargenotes() {
        return dischargenotes.get();
    }

    public void setDischargenotes(String value) {
        dischargenotes.set(value);
    }

    public StringProperty dischargenotesProperty() {
        return dischargenotes;
    }
    

    public String getFinaldiagnosis() {
        return finaldiagnosis.get();
    }

    public void setFinaldiagnosis(String value) {
        finaldiagnosis.set(value);
    }

    public StringProperty finaldiagnosisProperty() {
        return finaldiagnosis;
    }
    

    public String getProvisionaldiagnosis() {
        return provisionaldiagnosis.get();
    }

    public void setProvisionaldiagnosis(String value) {
        provisionaldiagnosis.set(value);
    }

    public StringProperty provisionaldiagnosisProperty() {
        return provisionaldiagnosis;
    }
    

    public String getDischargesummary() {
        return dischargesummary.get();
    }

    public void setDischargesummary(String value) {
        dischargesummary.set(value);
    }

    public StringProperty dischargesummaryProperty() {
        return dischargesummary;
    }
    

    public LocalDateTime getDischargetime() {
        return dischargetime.get();
    }

    public void setDischargetime(LocalDateTime value) {
        dischargetime.set(value);
    }

    public ObjectProperty dischargetimeProperty() {
        return dischargetime;
    }
    

    public String getAdmittedbyrelation() {
        return admittedbyrelation.get();
    }

    public void setAdmittedbyrelation(String value) {
        admittedbyrelation.set(value);
    }

    public StringProperty admittedbyrelationProperty() {
        return admittedbyrelation;
    }
    

    public String getAdmittedby() {
        return admittedby.get();
    }

    public void setAdmittedby(String value) {
        admittedby.set(value);
    }

    public StringProperty admittedbyProperty() {
        return admittedby;
    }
    

    public LocalDateTime getAdmissiontime() {
        return admissiontime.get();
    }

    public void setAdmissiontime(LocalDateTime value) {
        admissiontime.set(value);
    }

    public ObjectProperty admissiontimeProperty() {
        return admissiontime;
    }
    

    public String getPreadmissioncondition() {
        return preadmissioncondition.get();
    }

    public void setPreadmissioncondition(String value) {
        preadmissioncondition.set(value);
    }

    public StringProperty preadmissionconditionProperty() {
        return preadmissioncondition;
    }
    

    public String getPreadmissioncomplains() {
        return preadmissioncomplains.get();
    }

    public void setPreadmissioncomplains(String value) {
        preadmissioncomplains.set(value);
    }

    public StringProperty preadmissioncomplainsProperty() {
        return preadmissioncomplains;
    }
    

    public String getPreadmissiondiagnosis() {
        return preadmissiondiagnosis.get();
    }

    public void setPreadmissiondiagnosis(String value) {
        preadmissiondiagnosis.set(value);
    }

    public StringProperty preadmissiondiagnosisProperty() {
        return preadmissiondiagnosis;
    }
    

    public String getAdmissionnumber() {
        return admissionnumber.get();
    }

    public void setAdmissionnumber(String value) {
        admissionnumber.set(value);
    }

    public StringProperty admissionnumberProperty() {
        return admissionnumber;
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
    
    public static void createTableFilter(JFXTextField field,FilteredList<Admission> filteredRecords ){
        try{
            ObjectProperty<Predicate<Admission>> patientFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Admission>> physicianFilter = new SimpleObjectProperty<>();            
            ObjectProperty<Predicate<Admission>> caseFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Admission>> encoderFilter = new SimpleObjectProperty<>();
            
            caseFilter.bind(Bindings.createObjectBinding(()-> record -> record.getAdmissioncase().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            patientFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatientname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            physicianFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPhysician().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            encoderFilter.bind(Bindings.createObjectBinding(()-> record -> record.getEncoder().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> patientFilter.get().or(physicianFilter.get()).or(caseFilter.get()).or(encoderFilter.get()),patientFilter, physicianFilter,caseFilter,encoderFilter));
        }catch(Exception er){
            Logger.getLogger(Admission.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return admissionnumber.get()+" / "+DateTimeKit.toProperTimestamp(admissiontime.get());
    }
    
    public static void updateAdmissionCounter(){
        try{
            LocalDate date = LocalDate.now();
            
            AdmissionCounter rec = (AdmissionCounter)SQLTable.get(AdmissionCounter.class, AdmissionCounter.RECORDDATE, DateTimeKit.SQL_DATE_FORMATTER.format(date));
            if(rec == null){
                AdmissionCounter prev_rec = (AdmissionCounter)SQLTable.get(AdmissionCounter.class, AdmissionCounter.RECORDDATE, DateTimeKit.SQL_DATE_FORMATTER.format(date.minusDays(1)));
                
                rec = new AdmissionCounter();
                rec.setRecorddate(date);
                rec.setCurrentadmission((prev_rec != null)? prev_rec.getCurrentadmission()+1:1);
                rec.setNewadmission(1);
                rec.setDischarged(0);
                rec.save();
            }else{
                rec.setCurrentadmission(rec.getCurrentadmission()+1);
                rec.setNewadmission(rec.getNewadmission()+1);
                //rec.setDischarged(dcount);
                rec.update();
            }
            
        }catch(Exception er){
            Logger.getLogger(Admission.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void updateDischargedCounter(){
        try{
            LocalDate date = LocalDate.now();
            AdmissionCounter rec = (AdmissionCounter)SQLTable.get(AdmissionCounter.class, AdmissionCounter.RECORDDATE, DateTimeKit.SQL_DATE_FORMATTER.format(date));
            if(rec == null){
                rec = new AdmissionCounter();
                rec.setRecorddate(date);
                rec.setDischarged(1);
                rec.save();
            }else{
                rec.setDischarged(rec.getDischarged()+1);
                rec.update();
            }
            
        }catch(Exception er){
            Logger.getLogger(Admission.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    
}
