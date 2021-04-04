package utils;

import com.smartxls.RangeStyle;
import com.smartxls.WorkBook;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nse.dcfx.annotations.Col;
import nse.dcfx.mysql.SQL;
import nse.dcfx.mysql.SQLModel;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.ColorKit;

/**
 *
 * @author Duskmourne
 */
public class ExcelManager {
    
     public static final String[] C = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ",
        "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ",
        "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ",
        "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ",
        "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ",
        "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ",
        "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ",
        "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ",
        "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV", "IW", "IX", "IY", "IZ",
        "JA", "JB", "JC", "JD", "JE", "JF", "JG", "JH", "JI", "JJ", "JK", "JL", "JM", "JN", "JO", "JP", "JQ", "JR", "JS", "JT", "JU", "JV", "JW", "JX", "JY", "JZ",
        "KA", "KB", "KC", "KD", "KE", "KF", "KG", "KH", "KI", "KJ", "KK", "KL", "KM", "KN", "KO", "KP", "KQ", "KR", "KS", "KT", "KU", "KV", "KW", "KX", "KY", "KZ",
        "LA", "LB", "LC", "LD", "LE", "LF", "LG", "LH", "LI", "LJ", "LK", "LL", "LM", "LN", "LO", "LP", "LQ", "LR", "LS", "LT", "LU", "LV", "LW", "LX", "LY", "LZ",
        "MA", "MB", "MC", "MD", "ME", "MF", "MG", "MH", "MI", "MJ", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ",
        "NA", "NB", "NC", "ND", "NE", "NF", "NG", "NH", "NI", "NJ", "NK", "NL", "NM", "NN", "NO", "NP", "NQ", "NR", "NS", "NT", "NU", "NV", "NW", "NX", "NY", "NZ",
        "OA", "OB", "OC", "OD", "OE", "OF", "OG", "OH", "OI", "OJ", "OK", "OL", "OM", "ON", "OO", "OP", "OQ", "OR", "OS", "OT", "OU", "OV", "OW", "OX", "OY", "OZ",
        "PA", "PB", "PC", "PD", "PE", "PF", "PG", "PH", "PI", "PJ", "PK", "PL", "PM", "PN", "PO", "PP", "PQ", "PR", "PS", "PT", "PU", "PV", "PW", "PX", "PY", "PZ"};
     
    public static Color HEAD_BG = new Color(225, 225, 225);

    public static final String EXCEL_ACCOUNTING_FORMAT = "_-* #,##0.00_-;[Red]-* #,##0.00_-;_-* \"-\"??_-;_-@_-";
    public static final String EXCEL_PROPERDATE_FORMAT = "[$-3409]mmm dd, yyyy;@";
    public static final String EXCEL_PROPERTIME_FORMAT = "hh:mm AM/PM";
    public static final String EXCEL_PROPERDATETIME_FORMAT = "MMM dd, yyyy HH:mm AM/PM";
    
    public static final SimpleDateFormat SQLTIMESTAMP_EXCEL_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static final SimpleDateFormat SQLDATE_EXCEL_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat SQLTIME_EXCEL_FORMAT = new SimpleDateFormat("HH:mm");
    
    public static final String DEFAULT_FONT = "Cambria";
    public static final int COLUMM_LABEL_SIZE = 11*20;
    public static final int DEFAULT_SIZE = 8*20;
    
    
    
    public static final <T>List<T> export(Class<? extends SQLModel> model,String condition,File target){
        List<T> records = new ArrayList();
        OutputStream OutStream = null;
        WorkBook template = null;
        try{
            template = new WorkBook();           
            SQLModel obj = model.newInstance();
            final List<Field> fields = obj.getColumnFields();       
            
                        
            template.setNumSheets(1);             
            template.setSheetName(0, obj.getTable());
            template.setSheet(0);
            
            //Column Names
            for(int i=0;i<fields.size();i++){
                template.setText(0, i, fields.get(i).getName());
            }
            
            records = SQLTable.list(model, condition);
            
            int ROW = 1;
            for(int i = 0;i< records.size();i++){
                SQLModel objData = (SQLModel)records.get(i);
                for(int col = 0;col < fields.size();col++){
                    Field field = fields.get(col);
                    Col c = field.getAnnotation(Col.class);
                    Object val = SQL.columnValue(objData, field);
                    if(c.Class() == String.class){
                        template.setText(ROW,col, (String)val);                
                    }else if(c.Class() == int.class){
                        template.setNumber(ROW,col, (int)val);
                    }else if(c.Class() == double.class){
                        template.setNumber(ROW,col, (double)val);
                    }else if(c.Class() == float.class){
                        template.setNumber(ROW,col, (float)val);
                    }else if(c.Class() == long.class){
                        template.setNumber(ROW,col, (long)val);
                    }else if(c.Class() == boolean.class){
                        template.setEntry(ROW,col, val.toString());
                    }else if(c.Class() == java.sql.Date.class){
                        template.setEntry(ROW,col,asDate((java.sql.Date)val));
                    }else if(c.Class() == java.sql.Time.class){
                        template.setEntry(ROW,col,asTime((java.sql.Time)val));
                    }else if(c.Class() == java.sql.Timestamp.class){
                        template.setEntry(ROW,col,asDateTime((java.sql.Timestamp)val));
                    }else if(c.Class() == java.time.LocalDate.class){
                        template.setEntry(ROW,col, ((val == null)? null:asDate(java.sql.Date.valueOf((java.time.LocalDate)val))));
                    }else if(c.Class() == java.time.LocalTime.class){
                        template.setEntry(ROW,col, ((val == null)? null:asTime(java.sql.Time.valueOf((java.time.LocalTime)val))));
                    }else if(c.Class() == java.time.LocalDateTime.class){
                       template.setEntry(ROW,col,((val == null)? null:asDateTime(java.sql.Timestamp.valueOf((java.time.LocalDateTime)val))));
                    }else if(c.Class() == javafx.scene.paint.Color.class){
                        template.setText(ROW,col, ((val == null)? "''":"'"+ColorKit.toRGBCode((javafx.scene.paint.Color)val)+"'"));
                    }
                }
                ROW++;
            }
            /*
            //Sheet Protection
            for (int i = 0; i < template.getNumSheets(); i++) {
                template.setSheetProtected(i, WorkBook.sheetProtectionAllowUseAutoFilter | WorkBook.sheetProtectionAllowDefault | WorkBook.sheetProtectionAllowSort | WorkBook.sheetProtectionAllowFormatCells | WorkBook.sheetProtectionAllowFormatRows | WorkBook.sheetProtectionAllowFormatColumns, "dwr2rufd7ezj");
            }
            */
            
            for(int i = 1;i < template.getLastCol();i++){
                template.setColWidthAutoSize(i, true);
            }
            
            OutStream = new FileOutputStream(target);
            template.writeXLSX(OutStream);      
            
            return records;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return records;
        } finally {
            try {
                if (OutStream != null) {
                    OutStream.flush();
                    OutStream.close();
                }
                if (template != null) {
                    template.dispose();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
                return records;
            }
            System.gc();
        }
    }
    
    public static final <T>List<T> export(Class<? extends SQLModel> model,List<T> records,File target){
        OutputStream OutStream = null;
        WorkBook template = null;
        try{
            template = new WorkBook();           
            SQLModel obj = model.newInstance();
            final List<Field> fields = obj.getColumnFields();            
                        
            template.setNumSheets(1);             
            template.setSheetName(0, obj.getTable());
            template.setSheet(0);
            
            //Column Names
            for(int i=0;i<fields.size();i++){
                template.setText(0, i, fields.get(i).getName());
            }
            
            RangeStyle stringStyle = template.getRangeStyle();
            stringStyle.setCustomFormat("@");
            
            int ROW = 1;
            for(int i = 0;i< records.size();i++){
                SQLModel objData = (SQLModel)records.get(i);
                for(int col = 0;col < fields.size();col++){
                    Field field = fields.get(col);
                    Col c = field.getAnnotation(Col.class);
                    Object val = SQL.columnValue(objData, field);
                    if(c.Class() == String.class){
                        template.setRangeStyle(stringStyle, ROW, col, ROW, col);
                        template.setText(ROW,col, (String)val);                
                    }else if(c.Class() == int.class){
                        template.setNumber(ROW,col, (int)val);
                    }else if(c.Class() == double.class){
                        template.setNumber(ROW,col, (double)val);
                    }else if(c.Class() == float.class){
                        template.setNumber(ROW,col, (float)val);
                    }else if(c.Class() == long.class){
                        template.setNumber(ROW,col, (long)val);
                    }else if(c.Class() == boolean.class){
                        template.setEntry(ROW,col, val.toString());
                    }else if(c.Class() == java.sql.Date.class){
                        template.setEntry(ROW,col,asDate((java.sql.Date)val));
                    }else if(c.Class() == java.sql.Time.class){
                        template.setEntry(ROW,col,asTime((java.sql.Time)val));
                    }else if(c.Class() == java.sql.Timestamp.class){
                        template.setEntry(ROW,col,asDateTime((java.sql.Timestamp)val));
                    }else if(c.Class() == java.time.LocalDate.class){
                        template.setEntry(ROW,col, ((val == null)? null:asDate(java.sql.Date.valueOf((java.time.LocalDate)val))));
                    }else if(c.Class() == java.time.LocalTime.class){
                        template.setEntry(ROW,col, ((val == null)? null:asTime(java.sql.Time.valueOf((java.time.LocalTime)val))));
                    }else if(c.Class() == java.time.LocalDateTime.class){
                       template.setEntry(ROW,col,((val == null)? null:asDateTime(java.sql.Timestamp.valueOf((java.time.LocalDateTime)val))));
                    }else if(c.Class() == javafx.scene.paint.Color.class){
                        template.setText(ROW,col, ((val == null)? "''":"'"+ColorKit.toRGBCode((javafx.scene.paint.Color)val)+"'"));
                    }
                }
                ROW++;
            }
            
            /*
            //Sheet Protection
            for (int i = 0; i < template.getNumSheets(); i++) {
                template.setSheetProtected(i, WorkBook.sheetProtectionAllowUseAutoFilter | WorkBook.sheetProtectionAllowDefault | WorkBook.sheetProtectionAllowSort | WorkBook.sheetProtectionAllowFormatCells | WorkBook.sheetProtectionAllowFormatRows | WorkBook.sheetProtectionAllowFormatColumns, "dwr2rufd7ezj");
            }
            */
            
            for(int i = 1;i < template.getLastCol();i++){
                template.setColWidthAutoSize(i, true);
            }
            
            OutStream = new FileOutputStream(target);
            template.writeXLSX(OutStream);      
            
            return records;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return records;
        } finally {
            try {
                if (OutStream != null) {
                    OutStream.flush();
                    OutStream.close();
                }
                if (template != null) {
                    template.dispose();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
                return records;
            }
            System.gc();
        }
    }
    
    public static final <T>List<T> export(Class<? extends SQLModel> model,List<Field> fields,List<T> records,File target){
        OutputStream OutStream = null;
        WorkBook template = null;
        try{
            template = new WorkBook();           
            SQLModel obj = model.newInstance();
            //final List<Field> fields = obj.getFillableColumnFields();            
                        
            template.setNumSheets(1);             
            template.setSheetName(0, obj.getTable());
            template.setSheet(0);
            
            //Column Names
            for(int i=0;i<fields.size();i++){
                template.setText(0, i, fields.get(i).getName());
            }
                        
            int ROW = 1;
            for(int i = 0;i< records.size();i++){
                SQLModel objData = (SQLModel)records.get(i);
                for(int col = 0;col < fields.size();col++){
                    Field field = fields.get(col);
                    Col c = field.getAnnotation(Col.class);
                    Object val = SQL.columnValue(objData, field);
                    if(c.Class() == String.class){
                        template.setText(ROW,col, (String)val);                
                    }else if(c.Class() == int.class){
                        template.setNumber(ROW,col, (int)val);
                    }else if(c.Class() == double.class){
                        template.setNumber(ROW,col, (double)val);
                    }else if(c.Class() == float.class){
                        template.setNumber(ROW,col, (float)val);
                    }else if(c.Class() == long.class){
                        template.setNumber(ROW,col, (long)val);
                    }else if(c.Class() == boolean.class){
                        template.setEntry(ROW,col, val.toString());
                    }else if(c.Class() == java.sql.Date.class){
                        template.setEntry(ROW,col,asDate((java.sql.Date)val));
                    }else if(c.Class() == java.sql.Time.class){
                        template.setEntry(ROW,col,asTime((java.sql.Time)val));
                    }else if(c.Class() == java.sql.Timestamp.class){
                        template.setEntry(ROW,col,asDateTime((java.sql.Timestamp)val));
                    }else if(c.Class() == java.time.LocalDate.class){
                        template.setEntry(ROW,col, ((val == null)? null:asDate(java.sql.Date.valueOf((java.time.LocalDate)val))));
                    }else if(c.Class() == java.time.LocalTime.class){
                        template.setEntry(ROW,col, ((val == null)? null:asTime(java.sql.Time.valueOf((java.time.LocalTime)val))));
                    }else if(c.Class() == java.time.LocalDateTime.class){
                       template.setEntry(ROW,col,((val == null)? null:asDateTime(java.sql.Timestamp.valueOf((java.time.LocalDateTime)val))));
                    }else if(c.Class() == javafx.scene.paint.Color.class){
                        template.setText(ROW,col, ((val == null)? "''":"'"+ColorKit.toRGBCode((javafx.scene.paint.Color)val)+"'"));
                    }
                }
                ROW++;
            }
            
            /*
            //Sheet Protection
            for (int i = 0; i < template.getNumSheets(); i++) {
                template.setSheetProtected(i, WorkBook.sheetProtectionAllowUseAutoFilter | WorkBook.sheetProtectionAllowDefault | WorkBook.sheetProtectionAllowSort | WorkBook.sheetProtectionAllowFormatCells | WorkBook.sheetProtectionAllowFormatRows | WorkBook.sheetProtectionAllowFormatColumns, "dwr2rufd7ezj");
            }
            */
            
            for(int i = 1;i < template.getLastCol();i++){
                template.setColWidthAutoSize(i, true);
            }
            
            OutStream = new FileOutputStream(target);
            template.writeXLSX(OutStream);      
            
            return records;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return records;
        } finally {
            try {
                if (OutStream != null) {
                    OutStream.flush();
                    OutStream.close();
                }
                if (template != null) {
                    template.dispose();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, ex);
                return records;
            }
            System.gc();
        }
    }
    
    public static void createGridStyle(WorkBook template,int header_row,int data_row,int ini_column){
        try{            
            int LAST_COL = template.getLastCol();
            int LAST_ROW = template.getLastRow();
            
            RangeStyle STYLER = template.getRangeStyle(header_row,ini_column, header_row,  LAST_COL);
            //HEADER STYLER
            STYLER.setFontName(DEFAULT_FONT);
            STYLER.setFontBold(true);
            STYLER.setFontSize(13*20);
            STYLER.setHorizontalAlignment(RangeStyle.HorizontalAlignmentCenter);
            STYLER.setWordWrap(false);
            STYLER.setVerticalAlignment(RangeStyle.VerticalAlignmentCenter);
            STYLER.setTopBorder(RangeStyle.BorderThin);
            STYLER.setBottomBorder(RangeStyle.BorderThin);
            STYLER.setLeftBorder(RangeStyle.BorderThin);
            STYLER.setRightBorder(RangeStyle.BorderThin);
            STYLER.setVerticalInsideBorder(RangeStyle.BorderHair);
            template.setRangeStyle(STYLER,header_row,ini_column, header_row,  LAST_COL);
            if(LAST_ROW >= data_row){
                STYLER = template.getRangeStyle(data_row,ini_column, LAST_ROW,  LAST_COL);
                STYLER.setFontName(DEFAULT_FONT);
                STYLER.setFontBold(false);
                STYLER.setFontSize(11*20);
                STYLER.setWordWrap(false);
                STYLER.setTopBorder(RangeStyle.BorderThin);
                STYLER.setBottomBorder(RangeStyle.BorderThin);
                STYLER.setLeftBorder(RangeStyle.BorderThin);
                STYLER.setRightBorder(RangeStyle.BorderThin);
                STYLER.setVerticalInsideBorder(RangeStyle.BorderHair);
                STYLER.setHorizontalInsideBorder(RangeStyle.BorderHair);
                template.setRangeStyle(STYLER,data_row,ini_column, LAST_ROW,  LAST_COL);
            }
            
            
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void accountingStyle(WorkBook template,int col,int row,int col_to,int row_to){
        try{
            RangeStyle STYLER = template.getRangeStyle(row,col, row_to,  col_to);
            STYLER.setCustomFormat(EXCEL_ACCOUNTING_FORMAT);
            template.setRangeStyle(STYLER,row,col, row_to,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void summingRow(WorkBook template,int col,int row,int col_to,int row_from,int row_to){
        try{
            for(int i = col;i <= col_to;i++){
                
                 template.setFormula(row, i, "SUM($"+C[i]+(row_from+1)+":$"+C[i]+(row_to+1)+")");
            }
           
            RangeStyle STYLER = template.getRangeStyle(row,col, row,  col_to);
            STYLER.setCustomFormat(EXCEL_ACCOUNTING_FORMAT);
            STYLER.setFontBold(true);
            template.setRangeStyle(STYLER,row,col, row,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void properDateStyle(WorkBook template,int col,int row,int col_to,int row_to){
        try{
            RangeStyle STYLER = template.getRangeStyle(row,col, row_to,  col_to);
            STYLER.setCustomFormat(EXCEL_PROPERDATE_FORMAT);
            template.setRangeStyle(STYLER,row,col, row_to,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void properTimeStyle(WorkBook template,int col,int row,int col_to,int row_to){
        try{
            RangeStyle STYLER = template.getRangeStyle(row,col, row_to,  col_to);
            STYLER.setCustomFormat(EXCEL_PROPERTIME_FORMAT);
            template.setRangeStyle(STYLER,row,col, row_to,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void properDateTimeStyle(WorkBook template,int col,int row,int col_to,int row_to){
        try{
            RangeStyle STYLER = template.getRangeStyle(row,col, row_to,  col_to);
            STYLER.setCustomFormat(EXCEL_PROPERDATETIME_FORMAT);
            template.setRangeStyle(STYLER,row,col, row_to,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static void lockCell(WorkBook template,int col,int row,int col_to,int row_to,boolean b){
        try{
            RangeStyle STYLER = template.getRangeStyle(row,col, row_to,  col_to);
            STYLER.setLocked(b);
            template.setRangeStyle(STYLER,row,col, row_to,  col_to);
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public static String asDate(java.sql.Date date){
        try{
            String s = SQLDATE_EXCEL_FORMAT.format(date);
            return s;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
    
    public static String asTime(java.sql.Time time){
        try{
            String s = SQLTIME_EXCEL_FORMAT.format(time);
            return  s;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
    
    public static String asDateTime(java.sql.Timestamp time){
        try{
            String s = SQLTIMESTAMP_EXCEL_FORMAT.format(time);
            return s;
        }catch(Exception er){
            Logger.getLogger(ExcelManager.class.getName()).log(Level.SEVERE, null, er);
            return null;
        }
    }
}
