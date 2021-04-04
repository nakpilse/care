package utils;

import jasper.JasperFrame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ECart;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import nse.dcfx.utils.DateTimeKit;
import org.controlsfx.control.MaskerPane;

/**
 *
 * @author Duskmourne
 */
public class PrintManager {
    
    public static void printECartTransfer(MaskerPane masker,String title,String facility,List<ECart> items,boolean prompt){
        try {
            if (items.size() > 0) {
                List<Map<String, ?>> datasource = new ArrayList();
                
                items.stream().forEach(item->{
                    Map<String, Object> m = new HashMap();
                    m.put("qty", item.getQuantity());
                    m.put("tr_item", item.getDescription());                    
                    datasource.add(m);
                });

                

                JRDataSource fdatasource = new JRBeanCollectionDataSource(datasource);
                
                JasperReport jr = JasperCompileManager.compileReport(Toolkit.getDefaultToolkit().getClass().getResourceAsStream("/jasper/ecarttransfer.jrxml"));
                
                Map<String, Object> datas = new HashMap();
                datas.put("title", title);
                datas.put("facility", facility);
                datas.put("fromfacility", items.get(0).getFromfacility());
                datas.put("tofacility",  items.get(0).getTofacility());
                datas.put("user",  items.get(0).getUser());
                datas.put("ecarttime", DateTimeKit.toProperTimestamp( items.get(0).getEcarttime()));
                datas.put("ecartnumber",  items.get(0).getEcartnumber());
                
                datas.put("requestor",  items.get(0).getRequestedby());
                datas.put("approved",  items.get(0).getApprovedby());
                
                
                JasperPrint jp = JasperFillManager.fillReport(jr, datas, fdatasource);
                
                jp.setName("ECartTransferSlip-"+ items.get(0).getEcartnumber());
                if (prompt) {
                    new JasperFrame().showFrame(new JRViewer(jp));                    
                } else {                    
                    try {
                        if(masker!= null){
                            masker.setVisible(true);
                            Thread.sleep(200);
                        }
                        JasperPrintManager.printReport(jp, false);
                    } catch (InterruptedException | JRException er) {
                        Logger.getLogger(PrintManager.class.getName()).log(Level.SEVERE, null, er);
                    }finally{
                        if(masker!= null){
                            masker.setVisible(false);
                        }
                    }
                }                
            }

        } catch (Exception er) {
            Logger.getLogger(PrintManager.class.getName()).log(Level.SEVERE, null, er);
        }
    }
    
}
