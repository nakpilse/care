package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class StateProvince extends SQLModel<StateProvince>{
    
    public static final String TABLE_NAME = "stateprovinces";
    public static final String JOIN_KEY = "stateprovince_id";
    
    public static final String NAME = "name";
    
    @Dup
    @Col(name = NAME)
    private final StringProperty name = new SimpleStringProperty("");

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
