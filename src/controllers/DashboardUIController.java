package controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import models.Admission;
import models.AdmissionCounter;
import models.Consultation;
import models.ERConsultation;
import models.HospitalCharge;
import nse.dcfx.controls.FXTable;
import nse.dcfx.controls.UIController;
import nse.dcfx.mysql.SQLTable;
import nse.dcfx.utils.DateTimeKit;
import org.controlsfx.control.MaskerPane;

/**
 * FXML Controller class
 *
 * @author Duskmourne
 */
public class DashboardUIController implements Initializable,UIController {
    
    private StackPane mainStack;
    private MaskerPane maskerPane;
    
    
    @FXML
    private Label totalPatientLb;

    @FXML
    private Label admittedLb;


    @FXML
    private Label opdLb;


    @FXML
    private Label erLb;

    @FXML
    private LineChart<String, Number> activityChart;

    @FXML
    private TableView<?> acitivityTbl;

    @FXML
    private TableView<?> transactionTbl;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void reloadReferences(int val) {
        
    }

    @Override
    public void setMainStack(StackPane stackpane) {
        this.mainStack = stackpane;
    }

    @Override
    public StackPane getMainStack() {
        return mainStack;
    }

    @Override
    public void loadCustomizations() {
        try{
            
            LocalDate from = LocalDate.now().minusDays(10);
            LocalDate to = LocalDate.now();
            loadChart(from,to);
            
            loadTransactionTable();
            loadTransactions(from,to);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }

    @Override
    public void loadResources() {
        
    }

    @Override
    public void setUIController(UIController controller) {
        
    }

    @Override
    public void setMaskerPane(MaskerPane masker) {
        this.maskerPane = masker;
    }

    @Override
    public MaskerPane getMaskerPane() {
        return maskerPane;
    }

    @Override
    public void loadUIFixes() {
        
    }
    
    public void loadChart(LocalDate from,LocalDate to){
        try{
            List<LocalDate> dates = DateTimeKit.getLocalDatesList( from, to);
            //Timestamp t1 = Timestamp.valueOf(LocalDateTime.of(from, LocalTime.of(0, 0, 0)));
            //Timestamp t2 = Timestamp.valueOf(LocalDateTime.of(to, LocalTime.of(23, 59, 59)));
            //<Admission> adms = SQLTable.list(Admission.class,Admission.ADMISSIONTIME+">='"+t1+"' AND ("+Admission.DISCHARGETIME+" IS NULL OR ("+Admission.DISCHARGETIME+">='"+t1+"' AND "+Admission.DISCHARGETIME+"<='"+t2+"'))");
            //Admissions
            Map<String,Integer> admissions = new LinkedHashMap();
            Map<String,Integer> dis = new LinkedHashMap();
            Map<String,Integer> newadm = new LinkedHashMap();
            LocalDate today = LocalDate.now();
            for(LocalDate date:dates){
                AdmissionCounter adc = (AdmissionCounter)SQLTable.get(AdmissionCounter.class, AdmissionCounter.RECORDDATE, DateTimeKit.SQL_DATE_FORMATTER.format(date));
                admissions.put(DateTimeKit.toProperDate(date), (adc == null)? 0:adc.getCurrentadmission());
                newadm.put(DateTimeKit.toProperDate(date), (adc == null)? 0:adc.getNewadmission());
                dis .put(DateTimeKit.toProperDate(date), (adc == null)? 0:adc.getDischarged());
                if(date.isEqual(today)){
                    totalPatientLb.setText(String.valueOf((adc == null)? 0:adc.getCurrentadmission()));
                    admittedLb.setText(String.valueOf((adc == null)? 0:adc.getDischarged()));
                }
            }
            
            //OPD
            Map<String,Integer> opds = new LinkedHashMap();
            for(LocalDate date:dates){
                Timestamp t1 = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0, 0)));
                Timestamp t2 = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
                List<Consultation> recs = SQLTable.list(Consultation.class,Consultation.CONSULTATIONTIME+">='"+t1+"' AND "+Consultation.CONSULTATIONTIME+"<='"+t2+"'"); 
                opds.put(DateTimeKit.toProperDate(date), recs.size());
                if(date.isEqual(today)){
                    opdLb.setText(String.valueOf(recs.size()));
                }
            }
            
            //ER
            Map<String,Integer> ers = new LinkedHashMap();
            for(LocalDate date:dates){
                Timestamp t1 = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(0, 0, 0)));
                Timestamp t2 = Timestamp.valueOf(LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
                List<ERConsultation> recs = SQLTable.list(ERConsultation.class,ERConsultation.CONSULTATIONTIME+">='"+t1+"' AND "+ERConsultation.CONSULTATIONTIME+"<='"+t2+"'"); 
                ers.put(DateTimeKit.toProperDate(date), recs.size());
                if(date.isEqual(today)){
                    erLb.setText(String.valueOf(recs.size()));
                }
            }
            
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            XYChart.Series series3 = new XYChart.Series();
            XYChart.Series series4 = new XYChart.Series();
            XYChart.Series series5 = new XYChart.Series();
            
            series1.setName("Total Admitted");
            series2.setName("New Admission");
            series3.setName("Discharged");
            series4.setName("OPD");
            series5.setName("ER");
            
            dates.stream().forEach(date->{
                String sdate = DateTimeKit.toProperDate(date);
                series1.getData().add(new XYChart.Data(sdate, admissions.get(sdate)));
                series2.getData().add(new XYChart.Data(sdate, newadm.get(sdate)));
                series3.getData().add(new XYChart.Data(sdate, dis.get(sdate)));
                series4.getData().add(new XYChart.Data(sdate, opds.get(sdate)));
                series5.getData().add(new XYChart.Data(sdate, ers.get(sdate)));
            });
            //Setting the data to Line chart
            activityChart.getData().addAll(series1, series2, series3,series4,series5);
            activityChart.getXAxis().setTickLabelRotation(360-45);
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadCounters(){
        
    }
    
    public void loadActivities(){
        
    }
    
    public void loadTransactions(LocalDate from,LocalDate to){
        try{
            Timestamp t1 = Timestamp.valueOf(LocalDateTime.of(from, LocalTime.of(0, 0, 0)));
            Timestamp t2 = Timestamp.valueOf(LocalDateTime.of(to, LocalTime.of(23, 59, 59)));
            
            List<HospitalCharge> recs = SQLTable.list(HospitalCharge.class, HospitalCharge.CHARGETIME+">='"+t1+"' AND "+HospitalCharge.CHARGETIME+"<='"+t2+"' ORDER BY "+HospitalCharge.CHARGETIME+" DESC");
        }catch(Exception er){
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, er);
        }
    }
    
    public void loadTransactionTable(){
        try{
            transactionTbl.setEditable(false);
            transactionTbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            FXTable.disableColumnReorder(transactionTbl);

            TableColumn timeCol = FXTable.addColumn(transactionTbl, "Timestamp", HospitalCharge::chargetimeProperty, false, 135, 135, 135);
            FXTable.addColumn(transactionTbl, "Charge #", HospitalCharge::chargenumberProperty, false, 80, 150, 120);
            FXTable.addColumn(transactionTbl, "Facility", HospitalCharge::chargefacilityProperty, false, 80, 150, 120);
            FXTable.addColumn(transactionTbl, "Patient", HospitalCharge::chargetoProperty, false);
            FXTable.addColumn(transactionTbl, "Net Amount", HospitalCharge::chargetoProperty, false,100,100,100);            
            TableColumn ptimeCol = FXTable.addColumn(transactionTbl, "Payment Time", HospitalCharge::paymenttimeProperty, false,135,135,135);
                        
            FXTable.setTimestampColumn(timeCol);
            FXTable.setTimestampColumn(ptimeCol);
        }catch(Exception er){
            
        }
    }
    
}
