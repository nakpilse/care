package models;

import com.jfoenix.controls.JFXTextField;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import nse.dcfx.annotations.Col;
import nse.dcfx.annotations.Dup;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.mysql.SQLTable;

/**
 *
 * @author Duskmourne
 */
public class Item extends SQLModel<Item>{
    
    public static final String TABLE_NAME = "items";
    public static final String JOIN_KEY = "item_id";
    
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String GENERICNAME = "genericname";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";
    public static final String QUANTITY = "quantity";
    public static final String REORDERQUANTITY = "reorderquantity";
    public static final String UNITMEASURE = "unitmeasure";
    public static final String FORM = "form";
    public static final String STRENGTH = "strength";
    public static final String COST = "cost";
    public static final String PRICE = "price";
    public static final String VATABLE = "vatable";
    public static final String PNF = "pnf";
    public static final String AVAILABLE = "available";
    public static final String ARCHIVE = "archive";
    public static final String STOCKED = "stocked";
    public static final String SUPPLY = "supply";
    public static final String OPT1 = "opt1";
    public static final String OPT2 = "opt2";
    
    @Dup
    @Col(name = CODE)
    private final StringProperty code = new SimpleStringProperty("");    
    @Dup
    @Col(name = DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup
    @Col(name = GENERICNAME)
    private final StringProperty genericname = new SimpleStringProperty("");
    @Dup
    @Col(name = CATEGORY)
    private final StringProperty category = new SimpleStringProperty("");
    @Dup
    @Col(name = TYPE)
    private final StringProperty type = new SimpleStringProperty("");
    @Dup(Class = int.class)
    @Col(name = QUANTITY,Class = int.class)
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);    
    @Dup(Class = int.class)
    @Col(name = REORDERQUANTITY,Class = int.class)
    private final IntegerProperty reorderquantity = new SimpleIntegerProperty(0);
    @Dup
    @Col(name = UNITMEASURE)
    private final StringProperty unitmeasure = new SimpleStringProperty("");
    @Dup
    @Col(name = FORM)
    private final StringProperty form = new SimpleStringProperty("");
    @Dup
    @Col(name = STRENGTH)
    private final StringProperty strength = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name = COST,Class = double.class)
    private final DoubleProperty cost = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name = PRICE,Class = double.class)
    private final DoubleProperty price = new SimpleDoubleProperty(0);
    @Dup(Class = boolean.class)
    @Col(name = VATABLE,Class = boolean.class)
    private final BooleanProperty vatable = new SimpleBooleanProperty(true);
    @Dup(Class = boolean.class)
    @Col(name = PNF,Class = boolean.class)
    private final BooleanProperty pnf = new SimpleBooleanProperty(false);
    @Dup(Class = boolean.class)
    @Col(name = AVAILABLE,Class = boolean.class)
    private final BooleanProperty available = new SimpleBooleanProperty(true);
    @Dup(Class = boolean.class)
    @Col(name = ARCHIVE,Class = boolean.class)
    private final BooleanProperty archive = new SimpleBooleanProperty(false);
    @Dup(Class = boolean.class)
    @Col(name = STOCKED,Class = boolean.class)
    private final BooleanProperty stocked = new SimpleBooleanProperty(true);
    @Dup(Class = boolean.class)
    @Col(name = SUPPLY,Class = boolean.class)
    private final BooleanProperty supply = new SimpleBooleanProperty(false);
    @Dup
    @Col(name = OPT1)
    private final StringProperty opt1 = new SimpleStringProperty("");
    @Dup
    @Col(name = OPT2)
    private final StringProperty opt2 = new SimpleStringProperty("");

    public String getForm() {
        return form.get();
    }

    public void setForm(String value) {
        form.set(value);
    }

    public StringProperty formProperty() {
        return form;
    }
    

    public String getOpt2() {
        return opt2.get();
    }

    public void setOpt2(String value) {
        opt2.set(value);
    }

    public StringProperty opt2Property() {
        return opt2;
    }
    

    public String getOpt1() {
        return opt1.get();
    }

    public void setOpt1(String value) {
        opt1.set(value);
    }

    public StringProperty opt1Property() {
        return opt1;
    }
    

    public boolean isSupply() {
        return supply.get();
    }

    public void setSupply(boolean value) {
        supply.set(value);
    }

    public BooleanProperty supplyProperty() {
        return supply;
    }
    

    public boolean isStocked() {
        return stocked.get();
    }

    public void setStocked(boolean value) {
        stocked.set(value);
    }

    public BooleanProperty stockedProperty() {
        return stocked;
    }
    

    public boolean isArchive() {
        return archive.get();
    }

    public void setArchive(boolean value) {
        archive.set(value);
    }

    public BooleanProperty archiveProperty() {
        return archive;
    }
    

    public boolean isAvailable() {
        return available.get();
    }

    public void setAvailable(boolean value) {
        available.set(value);
    }

    public BooleanProperty availableProperty() {
        return available;
    }
    

    public boolean isPnf() {
        return pnf.get();
    }

    public void setPnf(boolean value) {
        pnf.set(value);
    }

    public BooleanProperty pnfProperty() {
        return pnf;
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
    

    public double getCost() {
        return cost.get();
    }

    public void setCost(double value) {
        cost.set(value);
    }

    public DoubleProperty costProperty() {
        return cost;
    }
    
    public String getStrength() {
        return strength.get();
    }

    public void setStrength(String value) {
        strength.set(value);
    }

    public StringProperty strengthProperty() {
        return strength;
    }
    

    public String getUnitmeasure() {
        return unitmeasure.get();
    }

    public void setUnitmeasure(String value) {
        unitmeasure.set(value);
    }

    public StringProperty unitmeasureProperty() {
        return unitmeasure;
    }
    

    public int getReorderquantity() {
        return reorderquantity.get();
    }

    public void setReorderquantity(int value) {
        reorderquantity.set(value);
    }

    public IntegerProperty reorderquantityProperty() {
        return reorderquantity;
    }
    

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
    

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
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
    

    public String getGenericname() {
        return genericname.get();
    }

    public void setGenericname(String value) {
        genericname.set(value);
    }

    public StringProperty genericnameProperty() {
        return genericname;
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
    

    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    public StringProperty codeProperty() {
        return code;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<Item> filteredRecords ){
        try{
            ObjectProperty<Predicate<Item>> codeFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Item>> nameFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Item>> desFilter = new SimpleObjectProperty<>();
            
            codeFilter.bind(Bindings.createObjectBinding(()-> record -> record.getCode().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            nameFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            desFilter.bind(Bindings.createObjectBinding(()-> record -> record.getGenericname().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> nameFilter.get().or(desFilter.get()).or(codeFilter.get()),nameFilter, desFilter,codeFilter));
        }catch(Exception er){
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public DeliveryItem newDelivery(){
        DeliveryItem dlitem = new DeliveryItem();
        dlitem.setItem_id(this.getId());
        dlitem.setItemcost(this.getCost());
        dlitem.setItemselling(this.getPrice());
        dlitem.setDescription(this.getDescription());
        dlitem.setType(DeliveryItem.DELIVERY_RECIEVED);
        return dlitem;
    }
    
    public static boolean recieveDeliver(int id,int qty,double cost,double selling){
        try{            
            return SQLTable.execute("UPDATE "+Item.TABLE_NAME+" SET "+Item.QUANTITY+"=("+Item.QUANTITY+"+"+qty+"),"+Item.COST+"="+cost+","+Item.PRICE+"="+selling+" WHERE "+Item.ID+"="+id);
        }catch(Exception er){            
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, er);
            return false;
        }
    }
    
    public HospitalChargeItem purchase(int qty){
        HospitalChargeItem itm = new HospitalChargeItem();
        itm.setDescription(this.getDescription());
        itm.setQuantity(qty);
        itm.setCost(this.getCost());
        itm.setSelling(this.getPrice());
        itm.setPnf(this.isPnf());
        itm.setVatable(this.isVatable());
        itm.setService(false);
        itm.setItemtype((this.isSupply())? "Supply":"Pharmacy");
        itm.setItemcode(this.getCode());
        itm.setItemtable(this.getTable());
        itm.setItemtableid(this.getId());
        itm.calculateNet();
        return itm;
    }
}
