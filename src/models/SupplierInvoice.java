package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import nse.dcfx.mysql.SQLModel;

/**
 *
 * @author Duskmourne
 */
public class SupplierInvoice extends SQLModel<SupplierInvoice>{
    
    public static final String TABLE_NAME = "supplierinvoices";
    public static final String JOIN_KEY = "supplierinvoice_id";
    
    public static final String INVOICENUMBER = "invoicenumber";
    public static final String INVOICEDATE = "invoicedate";
    public static final String RECIEVEDATE = "recievedate";
    public static final String SUPPLIERNAME = "suppliername";
    public static final String SALESREPRESENTATIVE = "salesrepresentative";
    public static final String RECIEVINGPERSONNEL = "recievingpersonnel";
    public static final String GROSSAMOUNT = "grossamount";
    public static final String VATABLEAMOUNT = "vatableamount";
    public static final String INPUTVAT = "inputvat";
    public static final String NONVATABLEAMOUNT = "nonvatableamount";
    public static final String DISCOUNTAMOUNT = "discountamount";
    public static final String DISCOUNTREMARKS = "discountremarks";
    public static final String NETAMOUNT = "netamount";
    public static final String REMARKS = "remarks";
    public static final String PAIDAMOUNT = "paidamount";
    public static final String SUPPLIER_ID = Supplier.JOIN_KEY;
    
    private final StringProperty invoicenumber = new SimpleStringProperty("");
    private final ObjectProperty<LocalDate> invoicedate = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDate> recievedate = new SimpleObjectProperty<>(null);
    private final StringProperty suppliername = new SimpleStringProperty("");
    private final StringProperty salesrepresentative = new SimpleStringProperty("");
    private final StringProperty recievingpersonnel = new SimpleStringProperty("");
    private final DoubleProperty grossamount = new SimpleDoubleProperty(0);
    private final DoubleProperty vatableamount = new SimpleDoubleProperty(0);
    private final DoubleProperty inputvat = new SimpleDoubleProperty(0);
    private final DoubleProperty nonvatableamount = new SimpleDoubleProperty(0);
    private final DoubleProperty discountamount = new SimpleDoubleProperty(0);
    private final StringProperty discountremarks = new SimpleStringProperty("");
    private final DoubleProperty netamount = new SimpleDoubleProperty(0);
    private final StringProperty remarks = new SimpleStringProperty("");
    private final IntegerProperty supplier_id = new SimpleIntegerProperty(0);
    private final DoubleProperty paidamount = new SimpleDoubleProperty(0);

    public double getPaidamount() {
        return paidamount.get();
    }

    public void setPaidamount(double value) {
        paidamount.set(value);
    }

    public DoubleProperty paidamountProperty() {
        return paidamount;
    }
    
    
    public int getSupplier_id() {
        return supplier_id.get();
    }

    public void setSupplier_id(int value) {
        supplier_id.set(value);
    }

    public IntegerProperty supplier_idProperty() {
        return supplier_id;
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

    public double getNetamount() {
        return netamount.get();
    }

    public void setNetamount(double value) {
        netamount.set(value);
    }

    public DoubleProperty netamountProperty() {
        return netamount;
    }

    public String getDiscountremarks() {
        return discountremarks.get();
    }

    public void setDiscountremarks(String value) {
        discountremarks.set(value);
    }

    public StringProperty discountremarksProperty() {
        return discountremarks;
    }

    public double getDiscountamount() {
        return discountamount.get();
    }

    public void setDiscountamount(double value) {
        discountamount.set(value);
    }

    public DoubleProperty discountamountProperty() {
        return discountamount;
    }

    public double getNonvatableamount() {
        return nonvatableamount.get();
    }

    public void setNonvatableamount(double value) {
        nonvatableamount.set(value);
    }

    public DoubleProperty nonvatableamountProperty() {
        return nonvatableamount;
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

    public double getVatableamount() {
        return vatableamount.get();
    }

    public void setVatableamount(double value) {
        vatableamount.set(value);
    }

    public DoubleProperty vatableamountProperty() {
        return vatableamount;
    }

    public double getGrossamount() {
        return grossamount.get();
    }

    public void setGrossamount(double value) {
        grossamount.set(value);
    }

    public DoubleProperty grossamountProperty() {
        return grossamount;
    }

    public String getRecievingpersonnel() {
        return recievingpersonnel.get();
    }

    public void setRecievingpersonnel(String value) {
        recievingpersonnel.set(value);
    }

    public StringProperty recievingpersonnelProperty() {
        return recievingpersonnel;
    }

    public String getSalesrepresentative() {
        return salesrepresentative.get();
    }

    public void setSalesrepresentative(String value) {
        salesrepresentative.set(value);
    }

    public StringProperty salesrepresentativeProperty() {
        return salesrepresentative;
    }

    public String getSuppliername() {
        return suppliername.get();
    }

    public void setSuppliername(String value) {
        suppliername.set(value);
    }

    public StringProperty suppliernameProperty() {
        return suppliername;
    }

    public LocalDate getRecievedate() {
        return recievedate.get();
    }

    public void setRecievedate(LocalDate value) {
        recievedate.set(value);
    }

    public ObjectProperty recievedateProperty() {
        return recievedate;
    }

    public LocalDate getInvoicedate() {
        return invoicedate.get();
    }

    public void setInvoicedate(LocalDate value) {
        invoicedate.set(value);
    }

    public ObjectProperty invoicedateProperty() {
        return invoicedate;
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
}
