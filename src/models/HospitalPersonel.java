package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public class HospitalPersonel extends SQLModel<HospitalPersonel>{
    
    public static final String TABLE = "hospitalpersonels";
    public static final String JOIN_KEY = "hospitalpersonel_id";
    
    public static final String NAME = "name";
    public static final String BIRTHDATE = "birthdate";
    public static final String GENDER = "gender";
    public static final String OCCUPATION = "occupation";
    public static final String SPECIALIZATION = "specialization";
    public static final String LICENSENUMBER = "licensenumber";
    public static final String CONSULTATIONFEE = "consultationfee";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String LANDLINE = "landline";
    public static final String ACTIVE = "active";
    
    @Dup
    @Col(name=NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup(Class = LocalDate.class)
    @Col(name=BIRTHDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=GENDER)
    private final StringProperty gender = new SimpleStringProperty("");
    @Dup
    @Col(name=OCCUPATION)
    private final StringProperty occupation = new SimpleStringProperty("");
    @Dup
    @Col(name=SPECIALIZATION)
    private final StringProperty specialization = new SimpleStringProperty("");
    @Dup
    @Col(name=LICENSENUMBER)
    private final StringProperty licensenumber = new SimpleStringProperty("");    
    @Dup(Class = double.class)
    @Col(name=CONSULTATIONFEE,Class = double.class)
    private final DoubleProperty consultationfee = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=EMAIL)
    private final StringProperty email = new SimpleStringProperty("");
    @Dup
    @Col(name=MOBILE)
    private final StringProperty mobile = new SimpleStringProperty("");
    @Dup
    @Col(name=LANDLINE)    
    private final StringProperty landline = new SimpleStringProperty("");
    @Dup(Class = boolean.class)
    @Col(name=ACTIVE,Class = boolean.class)
    private final BooleanProperty active = new SimpleBooleanProperty(true);

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
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
    

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }
    

    public double getConsultationfee() {
        return consultationfee.get();
    }

    public void setConsultationfee(double value) {
        consultationfee.set(value);
    }

    public DoubleProperty consultationfeeProperty() {
        return consultationfee;
    }
    

    public String getLicensenumber() {
        return licensenumber.get();
    }

    public void setLicensenumber(String value) {
        licensenumber.set(value);
    }

    public StringProperty licensenumberProperty() {
        return licensenumber;
    }
    

    public String getSpecialization() {
        return specialization.get();
    }

    public void setSpecialization(String value) {
        specialization.set(value);
    }

    public StringProperty specializationProperty() {
        return specialization;
    }
    

    public String getOccupation() {
        return occupation.get();
    }

    public void setOccupation(String value) {
        occupation.set(value);
    }

    public StringProperty occupationProperty() {
        return occupation;
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
    

    public LocalDate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(LocalDate value) {
        birthdate.set(value);
    }

    public ObjectProperty birthdateProperty() {
        return birthdate;
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
    
    public static void createTableFilter(JFXTextField field,FilteredList<HospitalPersonel> filteredRecords ){
        try{
            ObjectProperty<Predicate<HospitalPersonel>> occupationFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalPersonel>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalPersonel>> specializationFilter = new SimpleObjectProperty<>();
            
            occupationFilter.bind(Bindings.createObjectBinding(()-> record -> record.getOccupation().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getName().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            specializationFilter.bind(Bindings.createObjectBinding(()-> record -> record.getSpecialization().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(specializationFilter.get()).or(occupationFilter.get()),nameFilter, specializationFilter,occupationFilter));
        }catch(Exception er){
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }
    
}
