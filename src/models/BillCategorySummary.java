package models;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Duskmourne
 */
public class BillCategorySummary {

    private final StringProperty category = new SimpleStringProperty("");
    private final DoubleProperty actualcharge = new SimpleDoubleProperty(0);
    private final DoubleProperty vatexcempt = new SimpleDoubleProperty(0);
    private final DoubleProperty scpwddiscount = new SimpleDoubleProperty(0);
    private final DoubleProperty empotdiscount = new SimpleDoubleProperty(0);
    private final DoubleProperty benefitdiscount = new SimpleDoubleProperty(0);
    private final DoubleProperty firstcaserate = new SimpleDoubleProperty(0);
    private final DoubleProperty secondcaserate = new SimpleDoubleProperty(0);
    
    private List<BillStatementItem> items = new ArrayList();

    public BillCategorySummary() {
        category.set("");
    }
    
    public BillCategorySummary(String category) {
        this.category.set(category);
    }
    
    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<BillStatementItem> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<BillStatementItem> items) {
        this.items = items;
    }

    

    public double getSecondcaserate() {
        return secondcaserate.get();
    }

    public void setSecondcaserate(double value) {
        secondcaserate.set(value);
    }

    public DoubleProperty secondcaserateProperty() {
        return secondcaserate;
    }
    

    public double getFirstcaserate() {
        return firstcaserate.get();
    }

    public void setFirstcaserate(double value) {
        firstcaserate.set(value);
    }

    public DoubleProperty firstcaserateProperty() {
        return firstcaserate;
    }
    

    public double getBenefitdiscount() {
        return benefitdiscount.get();
    }

    public void setBenefitdiscount(double value) {
        benefitdiscount.set(value);
    }

    public DoubleProperty benefitdiscountProperty() {
        return benefitdiscount;
    }
    

    public double getEmpotdiscount() {
        return empotdiscount.get();
    }

    public void setEmpotdiscount(double value) {
        empotdiscount.set(value);
    }

    public DoubleProperty empotdiscountProperty() {
        return empotdiscount;
    }
    

    public double getScpwddiscount() {
        return scpwddiscount.get();
    }

    public void setScpwddiscount(double value) {
        scpwddiscount.set(value);
    }

    public DoubleProperty scpwddiscountProperty() {
        return scpwddiscount;
    }
    

    public double getVatexcempt() {
        return vatexcempt.get();
    }

    public void setVatexcempt(double value) {
        vatexcempt.set(value);
    }

    public DoubleProperty vatexcemptProperty() {
        return vatexcempt;
    }
    

    public double getActualcharge() {
        return actualcharge.get();
    }

    public void setActualcharge(double value) {
        actualcharge.set(value);
    }

    public DoubleProperty actualchargeProperty() {
        return actualcharge;
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
    
    public void calculate(List<BillStatementItem> items){
        setItems(items);
        items.stream().forEach(item->{
            actualcharge.set(actualcharge.get()+((item.getNonvatsales() > 0)? item.getAmount()-item.getLessvat():item.getAmount()));
            vatexcempt.set(vatexcempt.get()+(item.getLessvat()));
            scpwddiscount.set(scpwddiscount.get()+item.getScdiscount());
            empotdiscount.set(empotdiscount.get()+(item.getEmpdiscount()+item.getOtdiscount()));
            benefitdiscount.set(benefitdiscount.get()+item.getBenefitamount());
            firstcaserate.set(firstcaserate.get()+item.getFirstcaserateamount());
            secondcaserate.set(secondcaserate.get()+item.getSecondcaserateamount());
        });
    }
    
    public void add(BillStatementItem item){
        if(item != null){
            actualcharge.set(actualcharge.get()+((item.getNonvatsales() > 0)? item.getAmount()-item.getLessvat():item.getAmount()));
            vatexcempt.set(vatexcempt.get()+(item.getLessvat()));
            scpwddiscount.set(scpwddiscount.get()+item.getScdiscount());
            empotdiscount.set(empotdiscount.get()+(item.getEmpdiscount()+item.getOtdiscount()));
            benefitdiscount.set(benefitdiscount.get()+item.getBenefitamount());
            firstcaserate.set(firstcaserate.get()+item.getFirstcaserateamount());
            secondcaserate.set(secondcaserate.get()+item.getSecondcaserateamount());
            items.add(item);
        }
    }
    
}
