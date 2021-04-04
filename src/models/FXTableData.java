package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Duskmourne
 */
public class FXTableData {

    private final StringProperty label = new SimpleStringProperty("");
    private final StringProperty value = new SimpleStringProperty("");
    private final ObjectProperty<Class> objectclass = new SimpleObjectProperty<>(null);

    public Class getObjectclass() {
        return objectclass.get();
    }

    public void setObjectclass(Class value) {
        objectclass.set(value);
    }

    public ObjectProperty objectclassProperty() {
        return objectclass;
    }
    

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public StringProperty valueProperty() {
        return value;
    }
    

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String value) {
        label.set(value);
    }

    public StringProperty labelProperty() {
        return label;
    }
    
    
    
}
