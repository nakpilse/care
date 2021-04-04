package models;

import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.models.User;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class LaboratoryTest extends SQLModel<LaboratoryTest>{
    
    public static final String TABLE_NAME = "laboratorytests";
    public static final String JOIN_KEY = "laboratorytest_id";
    
    public static final String LABTESTNUMBER = "labtestnumber";
    public static final String PATIENT = "patient";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String AGESTRING = "agestring";
    public static final String TESTNAME = "testname";
    public static final String TESTCATEGORY = "testcategory";
    public static final String TESTGROUP = "testgroup";
    public static final String TESTTIME = "testtime";
    public static final String ORNUMBER = "ornumber";
    public static final String PHYSICIAN = "physician";
    public static final String MEDICALTECHNOLOGIST = "medicaltechnologist";
    public static final String MEDICALTECHNOLOGISTLIC = "medicaltechnologistlic";
    public static final String PATHOLOGIST = "pathologist";
    public static final String PATHOLOGISTLIC = "pathologistlic";
    public static final String FINDINGS = "findings";
    public static final String REMARKS = "remarks";
    public static final String ENCODER = "encoder";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String ADMISSION_ID = Admission.JOIN_KEY;
    public static final String USER_ID = User.JOIN_KEY;
    public static final String HOSPITALSERVICE_ID = HospitalService.JOIN_KEY;
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    
    
    @Dup
    @Col(name = LABTESTNUMBER)
    private final StringProperty labtestnumber = new SimpleStringProperty("");
    @Dup
    @Col(name = PATIENT)
    private final StringProperty patient = new SimpleStringProperty("");
    @Dup
    @Col(name = GENDER)
    private final StringProperty gender = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = AGE,Class = int.class)
    private final IntegerProperty age = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = AGESTRING)
    private final StringProperty agestring = new SimpleStringProperty("");
    @Dup
    @Col(name = TESTNAME)
    private final StringProperty testname = new SimpleStringProperty("");
    @Dup
    @Col(name = TESTCATEGORY)
    private final StringProperty testcategory = new SimpleStringProperty("");
    @Dup
    @Col(name = TESTGROUP)
    private final StringProperty testgroup = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = TESTTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> testtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");
    @Dup
    @Col(name = PHYSICIAN)
    private final StringProperty physician = new SimpleStringProperty("");
    @Dup
    @Col(name = MEDICALTECHNOLOGIST)
    private final StringProperty medicaltechnologist = new SimpleStringProperty("");    
    @Dup
    @Col(name = MEDICALTECHNOLOGISTLIC)
    private final StringProperty medicaltechnologistlic = new SimpleStringProperty("");
    @Dup
    @Col(name = PATHOLOGIST)
    private final StringProperty pathologist = new SimpleStringProperty("");
    @Dup
    @Col(name = PATHOLOGISTLIC)
    private final StringProperty pathologistlic = new SimpleStringProperty("");
    @Dup
    @Col(name = FINDINGS)
    private final StringProperty findings = new SimpleStringProperty("");
    @Dup
    @Col(name = REMARKS)
    private final StringProperty remarks = new SimpleStringProperty("");
    @Dup
    @Col(name = ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = ADMISSION_ID,Class = int.class)
    private final IntegerProperty admission_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0); 
    @Dup(Class = int.class)
    @Col(name = HOSPITALSERVICE_ID,Class = int.class)
    private final IntegerProperty hospitalservice_id = new SimpleIntegerProperty(0);

    
    //Non-Table Column
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean value) {
        selected.set(value);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public int getHospitalservice_id() {
        return hospitalservice_id.get();
    }

    public void setHospitalservice_id(int value) {
        hospitalservice_id.set(value);
    }

    public IntegerProperty hospitalservice_idProperty() {
        return hospitalservice_id;
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
    
    public LocalDateTime getTesttime() {
        return testtime.get();
    }

    public void setTesttime(LocalDateTime value) {
        testtime.set(value);
    }

    public ObjectProperty testtimeProperty() {
        return testtime;
    }
    
    

    public String getPathologistlic() {
        return pathologistlic.get();
    }

    public void setPathologistlic(String value) {
        pathologistlic.set(value);
    }

    public StringProperty pathologistlicProperty() {
        return pathologistlic;
    }
    

    public String getMedicaltechnologistlic() {
        return medicaltechnologistlic.get();
    }

    public void setMedicaltechnologistlic(String value) {
        medicaltechnologistlic.set(value);
    }

    public StringProperty medicaltechnologistlicProperty() {
        return medicaltechnologistlic;
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
    

    public int getAdmission_id() {
        return admission_id.get();
    }

    public void setAdmission_id(int value) {
        admission_id.set(value);
    }

    public IntegerProperty admission_idProperty() {
        return admission_id;
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
    

    public String getRemarks() {
        return remarks.get();
    }

    public void setRemarks(String value) {
        remarks.set(value);
    }

    public StringProperty remarksProperty() {
        return remarks;
    }
    

    public String getFindings() {
        return findings.get();
    }

    public void setFindings(String value) {
        findings.set(value);
    }

    public StringProperty findingsProperty() {
        return findings;
    }
    

    public String getPathologist() {
        return pathologist.get();
    }

    public void setPathologist(String value) {
        pathologist.set(value);
    }

    public StringProperty pathologistProperty() {
        return pathologist;
    }
    

    public String getMedicaltechnologist() {
        return medicaltechnologist.get();
    }

    public void setMedicaltechnologist(String value) {
        medicaltechnologist.set(value);
    }

    public StringProperty medicaltechnologistProperty() {
        return medicaltechnologist;
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
    

    public String getOrnumber() {
        return ornumber.get();
    }

    public void setOrnumber(String value) {
        ornumber.set(value);
    }

    public StringProperty ornumberProperty() {
        return ornumber;
    }
    

    public String getTestgroup() {
        return testgroup.get();
    }

    public void setTestgroup(String value) {
        testgroup.set(value);
    }

    public StringProperty testgroupProperty() {
        return testgroup;
    }
    

    public String getTestcategory() {
        return testcategory.get();
    }

    public void setTestcategory(String value) {
        testcategory.set(value);
    }

    public StringProperty testcategoryProperty() {
        return testcategory;
    }
    

    public String getTestname() {
        return testname.get();
    }

    public void setTestname(String value) {
        testname.set(value);
    }

    public StringProperty testnameProperty() {
        return testname;
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
    

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }
    

    public String getPatient() {
        return patient.get();
    }

    public void setPatient(String value) {
        patient.set(value);
    }

    public StringProperty patientProperty() {
        return patient;
    }
    

    public String getLabtestnumber() {
        return labtestnumber.get();
    }

    public void setLabtestnumber(String value) {
        labtestnumber.set(value);
    }

    public StringProperty labtestnumberProperty() {
        return labtestnumber;
    }
    
    
    
}
