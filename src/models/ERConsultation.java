package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
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
public class ERConsultation extends SQLModel<ERConsultation>{
    
    public static final String TABLE_NAME = "erconsultations";
    public static final String JOIN_KEY = "erconsultation_id";
    
    public static final String RECORDNUMBER = "recordnumber";
    public static final String PATIENTNAME = "patientname";
    public static final String FACILITY = "facility";
    public static final String CONSULTATIONTIME = "consultationtime";
    public static final String PHYSICIAN = "physician";
    public static final String COMPLAINS = "complains";
    public static final String INITIALSTATS = "initialstats";
    public static final String DIAGNOSIS = "diagnosis";
    public static final String CASECODE = "casecode";
    public static final String TRANSFERFROM = "transferfrom";
    public static final String TRANSFERFROMTIME = "transferfromtime";
    public static final String TRANSFERFROMNOTE = "transferfromnote";
    public static final String TRANSFERTO = "transferto";
    public static final String TRANSFERTOTIME = "transfertotime";
    public static final String TRANSFERTONOTE = "transfertonote";
    public static final String ADMITTEDBY = "admittedby";
    public static final String REMARKS = "remarks";
    public static final String FOLLOWUPDATE = "followupdate";
    public static final String ENCODER = "encoder";
    public static final String ORNUMBER = "ornumber";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    public static final String HOSPITALPERSONEL_ID = HospitalPersonel.JOIN_KEY;
    public static final String USER_ID = User.JOIN_KEY;
    
    
    @Dup
    @Col(name = RECORDNUMBER)
    private final StringProperty recordnumber = new SimpleStringProperty("");
    @Dup
    @Col(name = PATIENTNAME)
    private final StringProperty patientname = new SimpleStringProperty("");
    @Dup
    @Col(name = FACILITY)
    private final StringProperty facility = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = CONSULTATIONTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> consultationtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = PHYSICIAN)
    private final StringProperty physician = new SimpleStringProperty("");
    @Dup
    @Col(name = COMPLAINS)
    private final StringProperty complains = new SimpleStringProperty("");
    @Dup
    @Col(name = INITIALSTATS)
    private final StringProperty initialstats = new SimpleStringProperty("");
    @Dup
    @Col(name = DIAGNOSIS)
    private final StringProperty diagnosis = new SimpleStringProperty("");
    @Dup
    @Col(name = CASECODE)
    private final StringProperty casecode = new SimpleStringProperty("");@Dup
    @Col(name = TRANSFERFROM)
    private final StringProperty transferfrom = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = TRANSFERFROMTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> transferfromtime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = TRANSFERFROMNOTE)
    private final StringProperty transferfromnote = new SimpleStringProperty("");
    @Dup
    @Col(name = TRANSFERTO)
    private final StringProperty transferto = new SimpleStringProperty("");
    @Dup(Class = LocalDateTime.class)
    @Col(name = TRANSFERTOTIME,Class = LocalDateTime.class)
    private final ObjectProperty<LocalDateTime> transfertotime = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = TRANSFERTONOTE)
    private final StringProperty transfertonote = new SimpleStringProperty("");
    @Dup
    @Col(name = ADMITTEDBY)
    private final StringProperty admittedby = new SimpleStringProperty("");
    @Dup
    @Col(name = REMARKS)
    private final StringProperty remarks = new SimpleStringProperty("");
    @Dup(Class = LocalDate.class)
    @Col(name = FOLLOWUPDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> followupdate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name = ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");
    @Dup
    @Col(name = ORNUMBER)
    private final StringProperty ornumber = new SimpleStringProperty("");    
    @Dup(Class = int.class)
    @Col(name = PATIENT_ID,Class = int.class)
    private final IntegerProperty patient_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = HOSPITALPERSONEL_ID,Class = int.class)
    private final IntegerProperty hospitalpersonel_id = new SimpleIntegerProperty(0);
    @Dup(Class = int.class)
    @Col(name = USER_ID,Class = int.class)
    private final IntegerProperty user_id = new SimpleIntegerProperty(0);

    public String getAdmittedby() {
        return admittedby.get();
    }

    public void setAdmittedby(String value) {
        admittedby.set(value);
    }

    public StringProperty admittedbyProperty() {
        return admittedby;
    }
    

    public String getTransfertonote() {
        return transfertonote.get();
    }

    public void setTransfertonote(String value) {
        transfertonote.set(value);
    }

    public StringProperty transfertonoteProperty() {
        return transfertonote;
    }
    

    public LocalDateTime getTransfertotime() {
        return transfertotime.get();
    }

    public void setTransfertotime(LocalDateTime value) {
        transfertotime.set(value);
    }

    public ObjectProperty transfertotimeProperty() {
        return transfertotime;
    }
    

    public String getTransferto() {
        return transferto.get();
    }

    public void setTransferto(String value) {
        transferto.set(value);
    }

    public StringProperty transfertoProperty() {
        return transferto;
    }
    

    public String getTransferfromnote() {
        return transferfromnote.get();
    }

    public void setTransferfromnote(String value) {
        transferfromnote.set(value);
    }

    public StringProperty transferfromnoteProperty() {
        return transferfromnote;
    }
    

    public LocalDateTime getTransferfromtime() {
        return transferfromtime.get();
    }

    public void setTransferfromtime(LocalDateTime value) {
        transferfromtime.set(value);
    }

    public ObjectProperty transferfromtimeProperty() {
        return transferfromtime;
    }
    

    public String getTransferfrom() {
        return transferfrom.get();
    }

    public void setTransferfrom(String value) {
        transferfrom.set(value);
    }

    public StringProperty transferfromProperty() {
        return transferfrom;
    }
    

    public String getInitialstats() {
        return initialstats.get();
    }

    public void setInitialstats(String value) {
        initialstats.set(value);
    }

    public StringProperty initialstatsProperty() {
        return initialstats;
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
    

    public String getOrnumber() {
        return ornumber.get();
    }

    public void setOrnumber(String value) {
        ornumber.set(value);
    }

    public StringProperty ornumberProperty() {
        return ornumber;
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
    

    public LocalDate getFollowupdate() {
        return followupdate.get();
    }

    public void setFollowupdate(LocalDate value) {
        followupdate.set(value);
    }

    public ObjectProperty followupdateProperty() {
        return followupdate;
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
    

    public String getCasecode() {
        return casecode.get();
    }

    public void setCasecode(String value) {
        casecode.set(value);
    }

    public StringProperty casecodeProperty() {
        return casecode;
    }
    

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public void setDiagnosis(String value) {
        diagnosis.set(value);
    }

    public StringProperty diagnosisProperty() {
        return diagnosis;
    }
    

    public String getComplains() {
        return complains.get();
    }

    public void setComplains(String value) {
        complains.set(value);
    }

    public StringProperty complainsProperty() {
        return complains;
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
    

    public LocalDateTime getConsultationtime() {
        return consultationtime.get();
    }

    public void setConsultationtime(LocalDateTime value) {
        consultationtime.set(value);
    }

    public ObjectProperty consultationtimeProperty() {
        return consultationtime;
    }
    

    public String getFacility() {
        return facility.get();
    }

    public void setFacility(String value) {
        facility.set(value);
    }

    public StringProperty facilityProperty() {
        return facility;
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
    

    public String getRecordnumber() {
        return recordnumber.get();
    }

    public void setRecordnumber(String value) {
        recordnumber.set(value);
    }

    public StringProperty recordnumberProperty() {
        return recordnumber;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<ERConsultation> filteredRecords ){
        try{
            ObjectProperty<Predicate<ERConsultation>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ERConsultation>> physicianFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ERConsultation>> userFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<ERConsultation>> orFilter = new SimpleObjectProperty<>();
            
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPatientname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            physicianFilter.bind(Bindings.createObjectBinding(()-> record -> record.getPhysician().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            orFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOrnumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            userFilter.bind(Bindings.createObjectBinding(()-> record -> record.getEncoder().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(physicianFilter.get()).or(userFilter.get()).or(orFilter.get()),nameFilter, physicianFilter,userFilter,orFilter));
        }catch(Exception er){
            Logger.getLogger(ERConsultation.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
