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
public class SampleModel extends SQLModel<SampleModel>{
    
    
    
    public static final String TABLE_NAME = "samplemodels";
    public static final String JOIN_KEY = "samplemodel_id";
    
    public static final String DATA1 = "data1";
    @Dup
    @Col(name = DATA1)
    private final StringProperty data1 = new SimpleStringProperty("");

    public String getData1() {
        return data1.get();
    }

    public void setData1(String value) {
        data1.set(value);
    }

    public StringProperty data1Property() {
        return data1;
    }
    
    
}
