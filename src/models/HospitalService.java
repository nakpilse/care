package models;

import com.jfoenix.controls.JFXTextField;
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
public class HospitalService extends SQLModel<HospitalService>{
    
    public static final String TABLE_NAME = "hospitalservices";
    public static final String JOIN_KEY = "hospitalservice_id";
    
    public static final String DESCRIPTION = "description";
    public static final String FACILITY = "facility";
    public static final String CATEGORY = "category";
    public static final String GRP = "grp";
    public static final String PRICE = "price";
    public static final String VATABLE = "vatable";
    public static final String PRINTTEMPLATE = "printtemplate";
    
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup
    @Col(name=FACILITY)
    private final StringProperty facility = new SimpleStringProperty("");
    @Dup
    @Col(name=CATEGORY)
    private final StringProperty category = new SimpleStringProperty("");
    @Dup
    @Col(name=GRP)
    private final StringProperty grp = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=PRICE,Class = double.class)
    private final DoubleProperty price = new SimpleDoubleProperty(0);
    @Dup(Class = boolean.class)
    @Col(name=VATABLE,Class = boolean.class)
    private final BooleanProperty vatable = new SimpleBooleanProperty(false);
    @Dup
    @Col(name=PRINTTEMPLATE)
    private final StringProperty printtemplate = new SimpleStringProperty("");
    
    //Non Table Column Variable
    @Dup
    private final StringProperty patienttemp = new SimpleStringProperty("");

    public String getPatienttemp() {
        return patienttemp.get();
    }

    public void setPatienttemp(String value) {
        patienttemp.set(value);
    }

    public StringProperty patienttempProperty() {
        return patienttemp;
    }
    
    

    public String getPrinttemplate() {
        return printtemplate.get();
    }

    public void setPrinttemplate(String value) {
        printtemplate.set(value);
    }

    public StringProperty printtemplateProperty() {
        return printtemplate;
    }

    public boolean isVatable() {
        return vatable.get();
    }

    public void setVatable(boolean value) {
        vatable.set(value);
    }

    public BooleanProperty vatableProperty() {
        return vatable;
    }
    

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public DoubleProperty priceProperty() {
        return price;
    }
    

    public String getGrp() {
        return grp.get();
    }

    public void setGrp(String value) {
        grp.set(value);
    }

    public StringProperty grpProperty() {
        return grp;
    }
    

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String value) {
        category.set(value);
    }

    public StringProperty categoryProperty() {
        return category;
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

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<HospitalService> filteredRecords ){
        try{
            ObjectProperty<Predicate<HospitalService>> desFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalService>> categoryFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalService>> facilityFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<HospitalService>> grpFilter = new SimpleObjectProperty<>();
            
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            facilityFilter.bind(Bindings.createObjectBinding(()-> record -> record.getFacility().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            categoryFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCategory().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            grpFilter.bind(Bindings.createObjectBinding(()-> record -> record.getGrp().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> desFilter.get().or(categoryFilter.get()).or(facilityFilter.get()).or(grpFilter.get()), desFilter,categoryFilter,facilityFilter,grpFilter));
        }catch(Exception er){
            Logger.getLogger(HospitalService.class.getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public String toString() {
        return description.get();
    }
    
    
    
}
