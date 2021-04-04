package models;

import javafx.beans.property.IntegerProperty;
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
public class CityMunicipality extends SQLModel<CityMunicipality>{
    
    public static final String TABLE_NAME = "citymunicipalities";
    public static final String JOIN_KEY = "citymunicipality_id";
    
    public static final String NAME = "name";
    public static final String STATEPROVINCE_ID = StateProvince.JOIN_KEY;
    
    @Dup
    @Col(name = NAME)
    private final StringProperty name = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = STATEPROVINCE_ID,Class = int.class)
    private final IntegerProperty stateprovince_id = new SimpleIntegerProperty(0);

    public int getStateprovince_id() {
        return stateprovince_id.get();
    }

    public void setStateprovince_id(int value) {
        stateprovince_id.set(value);
    }

    public IntegerProperty stateprovince_idProperty() {
        return stateprovince_id;
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
