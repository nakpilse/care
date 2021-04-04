package utils;

import com.jfoenix.controls.JFXDatePicker;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 *
 * @author Duskmourne
 */
public class FXDateEntry extends HBox{

    private final StringProperty label = new SimpleStringProperty("");
    
    private final Label labelComponent = new Label();    
    private final JFXDatePicker textfieldComponent = new JFXDatePicker();
    private final Tooltip tooltip = new Tooltip();

    public FXDateEntry() {
        initialize();
    }
    
    public FXDateEntry(String lb) {
        initialize();
        label.set(lb);
    }
    
    

    /**
     * Get the value of textfieldComponent
     *
     * @return the value of textfieldComponent
     */
    public JFXDatePicker getTextfieldComponent() {
        return textfieldComponent;
    }

    /**
     * Get the value of labelComponent
     *
     * @return the value of labelComponent
     */
    public Label getLabelComponent() {
        return labelComponent;
    }
    
    private void initialize(){
        try{
            textfieldComponent.setMinSize(200, 28);
            textfieldComponent.setMaxSize(200, 28);
            textfieldComponent.setPrefSize(200, 28);
            
            labelComponent.setMinSize(150, 28);
            labelComponent.setMaxSize(150, 28);
            labelComponent.setPrefSize(150, 28);
            
            labelComponent.textProperty().bind(label);
            tooltip.textProperty().bind(label);
            
            labelComponent.setTooltip(tooltip);
            
            this.setMinSize(360, 30);
            this.setMaxSize(360, 30);
            this.setPrefSize(360, 30);
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER_LEFT);
            
            this.getChildren().addAll(labelComponent,textfieldComponent);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
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
