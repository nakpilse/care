package models;

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class ContactPerson extends SQLModel<ContactPerson>{
    
    public static final String TABLE_NAME = "contactpersons";
    public static final String JOIN_KEY = "contactperson_id";
    
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String LANDLINE = "landline";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String CITYMUNICIPALITY = "citymunicipality";
    public static final String STATEPROVINCE = "stateprovince";
    public static final String RELATION = "relation";
    public static final String PATIENT_ID = Patient.JOIN_KEY;
    
    public static final List<String> OPTIONS = Arrays.asList(new String[]{"Mother","Father","Spouse","Cousin","Auntie","Uncle","Guardian"});
    
    @Dup
    @Col(name = NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup
    @Col(name = MOBILE)
    private final StringProperty mobile = new SimpleStringProperty("");
    @Dup
    @Col(name = LANDLINE)
    private final StringProperty landline = new SimpleStringProperty("");
    @Dup
    @Col(name = EMAIL)
    private final StringProperty email = new SimpleStringProperty("");
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
    @Col(name = RELATION)
    private final StringProperty relation = new SimpleStringProperty("");

    

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
    

    public String getRelation() {
        return relation.get();
    }

    public void setRelation(String value) {
        relation.set(value);
    }

    public StringProperty relationProperty() {
        return relation;
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
    

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    
    
    
}
