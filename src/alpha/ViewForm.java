package alpha;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.FXTableData;
import nse.dcfx.annotations.Col;
import nse.dcfx.controls.FXTable;
import nse.dcfx.mysql.SQL;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.utils.DateTimeKit;
import nse.dcfx.utils.ReflectionKit;

/**
 *
 * @author Duskmourne
 */
public class ViewForm {
    
    public static AnchorPane create(SQLModel model,int width,int height){
        try{
            AnchorPane anc = new AnchorPane();
            anc.setPrefSize(width, height);
            anc.setMaxSize(width, height);
            
            TableView<FXTableData> tbl = new TableView();
            TableColumn lbCol = FXTable.addColumn(tbl, "Label", FXTableData::labelProperty, false, 125, 125, 125);
            TableColumn vaCol = FXTable.addColumn(tbl, "Value", FXTableData::valueProperty, false);
            lbCol.setCellFactory(tc -> {
                TableCell<FXTableData, String> cell = new TableCell<>();
                Text text = new Text();
                text.wrappingWidthProperty().bind(lbCol.widthProperty().subtract(10));
                text.textProperty().bind(cell.itemProperty());
                cell.setGraphic(text);
                text.getStyleClass().add("text");
                text.getStyleClass().add("anton-font");
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                return cell ;
            });
            vaCol.setCellFactory(tc -> {
                TableCell<FXTableData, String> cell = new TableCell<>();
                Text text = new Text();
                text.wrappingWidthProperty().bind(vaCol.widthProperty().subtract(8));
                text.textProperty().bind(cell.itemProperty());
                cell.setGraphic(text);
                text.getStyleClass().add("text");
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                return cell ;
            });
            tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //tbl.setDisable(true);
            
            anc.getChildren().setAll(tbl);
            AnchorPane.setTopAnchor(tbl, 0.0);            
            AnchorPane.setRightAnchor(tbl, 0.0);            
            AnchorPane.setBottomAnchor(tbl, 0.0);            
            AnchorPane.setLeftAnchor(tbl, 0.0);
            
            List<FXTableData> datas = new ArrayList();
            
            List<Field> fields = SQL.getAllFields(new ArrayList<>(), model.getClass());
            for(Field field:fields){
                Col c = field.getDeclaredAnnotation(Col.class);        
                if(c instanceof Col){
                    String mname = field.getName().substring(0,1).toUpperCase()+ field.getName().substring(1,  field.getName().length());        
                    Method get = ReflectionKit.getMethod(model.getClass(), mname);
                    
                    Object val = get.invoke(model);     
                    FXTableData data = new FXTableData();
                    data.setLabel(mname);
                    data.setObjectclass(c.Class());
                    if (c.Class() == int.class) {
                        data.setValue(String.valueOf(val));
                    } else if (c.Class() == String.class) {
                        data.setValue((String)val);
                    } else if (c.Class() == double.class) {
                        data.setValue(String.valueOf(val));
                    } else if (c.Class() == float.class) {
                        data.setValue(String.valueOf(val));
                    } else if (c.Class() == long.class) {
                       data.setValue(String.valueOf(val));
                    } else if (c.Class() == boolean.class) {
                        boolean b = (boolean)val;
                        data.setValue((b)? "Yes":"No");
                    } else if (c.Class() == java.sql.Date.class) {
                        java.sql.Date d = (java.sql.Date)val;
                        data.setValue(DateTimeKit.toProperDate(d));
                    } else if (c.Class() == java.sql.Time.class) {                        
                        java.sql.Time t = (java.sql.Time)val;
                        data.setValue(DateTimeKit.toProperTime(t));
                    } else if (c.Class() == java.sql.Timestamp.class) {                                      
                        java.sql.Timestamp t = (java.sql.Timestamp)val;
                        data.setValue(DateTimeKit.toProperDate(t));
                    } else if (c.Class() == java.time.LocalDate.class) {                                                  
                        java.time.LocalDate t = (java.time.LocalDate)val;
                        data.setValue(DateTimeKit.toProperDate(t));
                    } else if (c.Class() == java.time.LocalTime.class) {                                           
                        java.time.LocalTime t = (java.time.LocalTime)val;
                        data.setValue(DateTimeKit.toProperTime(t));                        
                    } else if (c.Class() == java.time.LocalDateTime.class) {                                     
                        java.time.LocalDateTime t = (java.time.LocalDateTime)val;
                        data.setValue(DateTimeKit.toProperTimestamp(t));                        
                    } else if (c.Class() == javafx.scene.paint.Color.class) {
                         javafx.scene.paint.Color t = (javafx.scene.paint.Color)val;
                         data.setValue(t.toString());
                    } 
                    datas.add(data);
                }
            }
            FXTable.setList(tbl, datas);
            tbl.getStyleClass().add("fxc-table");
            
            return anc;
        }catch(Exception er){
            Logger.getLogger(ViewForm.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
    
}
