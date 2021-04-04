package models;

import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
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
public class Expense extends SQLModel<Expense>{
    
    public static final String TABLE_NAME = "expenses";
    public static final String JOIN_KEY = "expense_id";
    
    public static final String EXPENSEDATE = "expensedate";
    public static final String VOUCHER = "voucher";
    public static final String INVOICENUMBER ="invoicenumber";
    public static final String VENDOR = "vendor";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    public static final String TOTALSALES = "totalsales";
    public static final String VATSALES = "vatsales";
    public static final String NONVATSALES = "nonvatsales";
    public static final String INPUTVAT = "inputvat";
    public static final String EWT = "ewt";
    public static final String LESSVAT = "lessvat";
    public static final String DISCOUNT = "discount";
    public static final String NETSALES = "netsales";
    public static final String ENCODER = "encoder";
    
    
    @Dup(Class = LocalDate.class)
    @Col(name=EXPENSEDATE,Class = LocalDate.class)
    private final ObjectProperty<LocalDate> expensedate = new SimpleObjectProperty<>(null);
    @Dup
    @Col(name=VOUCHER)
    private final StringProperty voucher = new SimpleStringProperty("");
    @Dup
    @Col(name=INVOICENUMBER)
    private final StringProperty invoicenumber = new SimpleStringProperty("");
    @Dup
    @Col(name=VENDOR)
    private final StringProperty vendor = new SimpleStringProperty("");
    @Dup
    @Col(name=CATEGORY)
    private final StringProperty category = new SimpleStringProperty("");
    @Dup
    @Col(name=DESCRIPTION)
    private final StringProperty description = new SimpleStringProperty("");
    @Dup(Class = double.class)
    @Col(name=TOTALSALES,Class = double.class)
    private final DoubleProperty totalsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=VATSALES,Class = double.class)
    private final DoubleProperty vatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=NONVATSALES,Class = double.class)
    private final DoubleProperty nonvatsales = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=INPUTVAT,Class = double.class)
    private final DoubleProperty inputvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=EWT,Class = double.class)
    private final DoubleProperty ewt = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=LESSVAT,Class = double.class)
    private final DoubleProperty lessvat = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=DISCOUNT,Class = double.class)
    private final DoubleProperty discount = new SimpleDoubleProperty(0);
    @Dup(Class = double.class)
    @Col(name=NETSALES,Class = double.class)
    private final DoubleProperty netsales = new SimpleDoubleProperty(0);
    @Dup
    @Col(name=ENCODER)
    private final StringProperty encoder = new SimpleStringProperty("");

    public String getEncoder() {
        return encoder.get();
    }

    public void setEncoder(String value) {
        encoder.set(value);
    }

    public StringProperty encoderProperty() {
        return encoder;
    }
    

    public LocalDate getExpensedate() {
        return expensedate.get();
    }

    public void setExpensedate(LocalDate value) {
        expensedate.set(value);
    }

    public ObjectProperty expensedateProperty() {
        return expensedate;
    }
    

    public double getNetsales() {
        return netsales.get();
    }

    public void setNetsales(double value) {
        netsales.set(value);
    }

    public DoubleProperty netsalesProperty() {
        return netsales;
    }
    

    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(double value) {
        discount.set(value);
    }

    public DoubleProperty discountProperty() {
        return discount;
    }
    

    public double getLessvat() {
        return lessvat.get();
    }

    public void setLessvat(double value) {
        lessvat.set(value);
    }

    public DoubleProperty lessvatProperty() {
        return lessvat;
    }
    

    public double getEwt() {
        return ewt.get();
    }

    public void setEwt(double value) {
        ewt.set(value);
    }

    public DoubleProperty ewtProperty() {
        return ewt;
    }
    

    public double getInputvat() {
        return inputvat.get();
    }

    public void setInputvat(double value) {
        inputvat.set(value);
    }

    public DoubleProperty inputvatProperty() {
        return inputvat;
    }
    

    public double getNonvatsales() {
        return nonvatsales.get();
    }

    public void setNonvatsales(double value) {
        nonvatsales.set(value);
    }

    public DoubleProperty nonvatsalesProperty() {
        return nonvatsales;
    }
    

    public double getVatsales() {
        return vatsales.get();
    }

    public void setVatsales(double value) {
        vatsales.set(value);
    }

    public DoubleProperty vatsalesProperty() {
        return vatsales;
    }
    

    public double getTotalsales() {
        return totalsales.get();
    }

    public void setTotalsales(double value) {
        totalsales.set(value);
    }

    public DoubleProperty totalsalesProperty() {
        return totalsales;
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
    

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String value) {
        category.set(value);
    }

    public StringProperty categoryProperty() {
        return category;
    }
    

    public String getVendor() {
        return vendor.get();
    }

    public void setVendor(String value) {
        vendor.set(value);
    }

    public StringProperty vendorProperty() {
        return vendor;
    }
    

    public String getInvoicenumber() {
        return invoicenumber.get();
    }

    public void setInvoicenumber(String value) {
        invoicenumber.set(value);
    }

    public StringProperty invoicenumberProperty() {
        return invoicenumber;
    }
    

    public String getVoucher() {
        return voucher.get();
    }

    public void setVoucher(String value) {
        voucher.set(value);
    }

    public StringProperty voucherProperty() {
        return voucher;
    }
    
    public static void createTableFilter(JFXTextField field,FilteredList<Expense> filteredRecords ){
        try{
            ObjectProperty<Predicate<Expense>> voucherFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Expense>> invoiceFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Expense>> vendorFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Expense>> descFilter = new SimpleObjectProperty<>();
            ObjectProperty<Predicate<Expense>> encoderFilter = new SimpleObjectProperty<>();
            
            voucherFilter.bind(Bindings.createObjectBinding(()-> record -> record.getVoucher().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            invoiceFilter.bind(Bindings.createObjectBinding(()-> record -> record.getInvoicenumber().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            vendorFilter.bind(Bindings.createObjectBinding(()-> record -> record.getVendor().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            descFilter.bind(Bindings.createObjectBinding(()-> record -> record.getDescription().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            encoderFilter.bind(Bindings.createObjectBinding(()-> record -> record.getEncoder().toLowerCase().contains(field.getText().toLowerCase()),field.textProperty()));
            filteredRecords.predicateProperty().bind(Bindings.createObjectBinding(() -> invoiceFilter.get().or(vendorFilter.get()).or(voucherFilter.get()).or(descFilter.get()).or(encoderFilter.get()),invoiceFilter, vendorFilter,voucherFilter,descFilter,encoderFilter));
        }catch(Exception er){
            Logger.getLogger(Expense.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
