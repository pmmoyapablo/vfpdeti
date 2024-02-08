/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve;

import common.DeviceException;
import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author pmoya
 */
public class ReportData extends PrinterData implements IPrinterData{
    
private int numberOfLastZReport;
private Date ZReportDate;
private Date lastInvoiceDate; //(includes the hour)
//Facturas
private double freeSalesTax;
private double generalRate1Sale;
private double generalRate1Tax;
private double reducedRate2Sale;
private double reducedRate2Tax;
private double additionalRate3Sal;
private double additionalRate3Tax;
//Nota de Creditos
private double freeTaxDevolution;
private double generalRateDevolution;
private double generalRateTaxDevolution;
private double reducedRateDevolution;
private double reducedRateTaxDevolution;
private double additionalRateDevolution;
private double additionalRateTaxDevolution;
// Notas de Debito
private double freeTaxDebit; 
private double generalRateDebit;
private double generalRateTaxDebit;
private double reducedRateDebit;
private double reducedRateTaxDebit;
private double additionalRateDebit;
private double additionalRateTaxDebit;

private int numberOfLastInvoice;
private int numberOfLastCreditNote;
private int numberOfLastDebitNote;
private int numberOfLastNonFiscal;   

    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
//        try
//        { 
//            if(mode == 'X')
//            {
              this.setNumberOfLastZReport(pCashModel.getCountReport());
              this.setZReportDate(pCashModel.getDateStart());
              this.setLastInvoiceDate(pData.getDateEnd());
              this.setFreeSalesTax(pCashModel.getTotal());
              this.setGeneralRate1Sale(0.0);
              this.setGeneralRate1Tax(0.0);
              this.setReducedRate2Sale(0.0);
              this.setReducedRate2Tax(0.0);
              this.setAdditionalRate3Sal(0.0);
              this.setAdditionalRate3Tax(0.0);
              //Notas de Creditos
              this.setFreeTaxDevolution(0.0);
              this.setGeneralRateDevolution(0.0);
              this.setGeneralRateTaxDevolution(0.0);
              this.setReducedRateDevolution(0.0);
              this.setReducedRateTaxDevolution(0.0);
              this.setAdditionalRateDevolution(0.0);
              this.setAdditionalRateTaxDevolution(0.0);
              //Notas de Debitos
              this.setFreeTaxDebitNote(0.0);
              this.setGeneralRateDebitNote(0.0);
              this.setGeneralRateTaxDebitNote(0.0);
              this.setReducedRateDebitNote(0.0);
              this.setReducedRateTaxDebitNote(0.0);
              this.setAdditionalRateDebitNote(0.0);
              this.setAdditionalRateTaxDebitNote(0.0);
              //Ultimos Documentos y Cantidades
              this.setNumberOfLastCreditNote(pData.getNewTicket_Id(2) - 1);
              this.setNumberOfLastDebitNote(pData.getNewTicket_Id(3) - 1);
              this.setNumberOfLastInvoice(pData.getNewTicket_Id(1) - 1);
              this.setNumberOfLastNonFiscal(pData.getNewTicket_Id(7) - 1);
//            }
              
             String trama = this.StringFormatXero(String.valueOf(this.getNumberOfLastZReport()), 4);
              
             trama = trama + (char) 0x000A;
             
             Calendar Cale = Calendar.getInstance();
                Cale.setTime(this.getZReportDate());
                String y = String.valueOf(Cale.get(Calendar.YEAR));
                int m = Cale.get(Calendar.MONTH) + 1;
                String d = String.valueOf(Cale.get(Calendar.DAY_OF_MONTH));
                y = this.StringFormatXero(y,2);
                String mes = this.StringFormatXero(String.valueOf(m),2);
                d = this.StringFormatXero(d,2);
                
                trama = trama + d + mes + y.substring(2) + (char) 0x000A;            
                trama = trama + StringFormatXero(String.valueOf(this.getNumberOfLastInvoice()), 8)  + (char) 0x000A;
                
                 Cale.setTime(new Date());  //this.getLastInvoiceDate()
                  y = this.StringFormatXero(String.valueOf(Cale.get(Calendar.YEAR)),2);
                  m = Cale.get(Calendar.MONTH) + 1;
                  mes = this.StringFormatXero(String.valueOf(m),2);
                  d = this.StringFormatXero(String.valueOf(Cale.get(Calendar.DAY_OF_MONTH)),2);
                  String h = this.StringFormatXero(String.valueOf(Cale.get(Calendar.HOUR)),2);
                  String min = this.StringFormatXero(String.valueOf(Cale.get(Calendar.MINUTE)),2);
                
                 trama = trama + d + mes + y.substring(2) + (char) 0x000A;     
                 trama = trama + h + min + (char) 0x000A;
                 
                 trama = trama + this.StringFormatXero(String.valueOf( this.getNumberOfLastCreditNote()), 8) + (char) 0x000A; 
                 trama = trama + this.StringFormatXero(String.valueOf( this.getNumberOfLastDebitNote()), 8) + (char) 0x000A; 
                 trama = trama + this.StringFormatXero(String.valueOf( this.getNumberOfLastNonFiscal()), 8) + (char) 0x000A; 
                 
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getFreeSalesTax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Sal())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Tax())), 10) + (char) 0x000A;
                 
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getFreeSalesTax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Sal())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Tax())), 10) + (char) 0x000A;
                 
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getFreeSalesTax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getGeneralRate1Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Sale())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getReducedRate2Tax())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Sal())), 10) + (char) 0x000A;
                 trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.getAdditionalRate3Tax())), 10) + (char) 0x000A;
	       
                 this._myBytes = trama.getBytes();

//        } catch (DeviceException ex) {
//                this._myBytes = null;
//        }
        
    }

    /**
      * Retorna el número del ultimo reporte Z efectuado.
      */
    public int getNumberOfLastZReport() {
        return numberOfLastZReport;
    }

    private void setNumberOfLastZReport(int numberOfLastZReport) {
        this.numberOfLastZReport = numberOfLastZReport;
    }
     /**
      * Retorna la fecha  del ultimo reporte Z efectuado.
      */
    public Date getZReportDate() {
        return ZReportDate;
    }

    private void setZReportDate(Date ZReportDate) {
        this.ZReportDate = ZReportDate;
    }
     /**
      * Retorna el número de la ultima factura emitida.
      */
    public int getNumberOfLastInvoice() {
        return numberOfLastInvoice;
    }

    private void setNumberOfLastInvoice(int numberOfLastInvoice) {
        this.numberOfLastInvoice = numberOfLastInvoice;
    }
    /**
      * Retorna la fecha y la hora de la ultima factura emitida.
      */
    public Date getLastInvoiceDate() {
        return lastInvoiceDate;
    }

    private void setLastInvoiceDate(Date lastInvoiceDate) {
        this.lastInvoiceDate = lastInvoiceDate;
    }
    /**
      * Retorna el monto total Exento almacenado.
      */
    public double getFreeSalesTax() {
        return freeSalesTax;
    }

    private void setFreeSalesTax(double freeSalesTax) {
        this.freeSalesTax = freeSalesTax;
    }
     /**
      * Retorna el monto total de Base Imponible General almacenado.
      */
    public double getGeneralRate1Sale() {
        return generalRate1Sale;
    }

   private void setGeneralRate1Sale(double generalRate1Sale) {
        this.generalRate1Sale = generalRate1Sale;
    }
     /**
      * Retorna el monto total de IVA General ó Tasa(1) almacenado.
      */
    public double getGeneralRate1Tax() {
        return generalRate1Tax;
    }

   private void setGeneralRate1Tax(double generalRate1Tax) {
        this.generalRate1Tax = generalRate1Tax;
    }
     /**
      * Retorna el monto total de Base Imponible Reducida almacenado.
      */
    public double getReducedRate2Sale() {
        return reducedRate2Sale;
    }

    private void setReducedRate2Sale(double reducedRate2Sale) {
        this.reducedRate2Sale = reducedRate2Sale;
    }
     /**
      * Retorna el monto total de IVA Reducido ó Tasa(2) almacenado.
      */
    public double getReducedRate2Tax() {
        return reducedRate2Tax;
    }

    private void setReducedRate2Tax(double reducedRate2Tax) {
        this.reducedRate2Tax = reducedRate2Tax;
    }
    /**
      * Retorna el monto total de Base Imponible Adicional almacenado.
      */
    public double getAdditionalRate3Sal() {
        return additionalRate3Sal;
    }

    private void setAdditionalRate3Sal(double additionalRate3Sal) {
        this.additionalRate3Sal = additionalRate3Sal;
    }
    /**
      * Retorna el monto total de IVA Adicional ó Tasa(3) almacenado.
      */
    public double getAdditionalRate3Tax() {
        return additionalRate3Tax;
    }

    private void setAdditionalRate3Tax(double additionalRate3Tax) {
        this.additionalRate3Tax = additionalRate3Tax;
    }
    /**
      * Retorna el monto total Exento en Devolución almacenado.
      */
    public double getFreeTaxDevolution() {
        return freeTaxDevolution;
    }

    private void setFreeTaxDevolution(double freeTaxDevolution) {
        this.freeTaxDevolution = freeTaxDevolution;
    }
    /**
      * Retorna el monto total de Base Imponible General en Devolución almacenado.
      */
    public double getGeneralRateDevolution() {
        return generalRateDevolution;
    }

   private void setGeneralRateDevolution(double generalRateDevolution) {
        this.generalRateDevolution = generalRateDevolution;
    }
    /**
      * Retorna el monto total de IVA General ó Tasa(1) en Devolución almacenado.
      */
    public double getGeneralRateTaxDevolution() {
        return generalRateTaxDevolution;
    }

   private void setGeneralRateTaxDevolution(double generalRateTaxDevolution) {
        this.generalRateTaxDevolution = generalRateTaxDevolution;
    }
    /**
      * Retorna el monto total de Base Imponible Reducida en Devolución almacenado.
      */
    public double getReducedRateDevolution() {
        return reducedRateDevolution;
    }

   private void setReducedRateDevolution(double reducedRateDevolution) {
        this.reducedRateDevolution = reducedRateDevolution;
    }
    /**
      * Retorna el monto total de IVA Reducido ó Tasa(2) en Devolución almacenado.
      */
    public double getReducedRateTaxDevolution() {
        return reducedRateTaxDevolution;
    }

    private void setReducedRateTaxDevolution(double reducedRateTaxDevolution) {
        this.reducedRateTaxDevolution = reducedRateTaxDevolution;
    }
    /**
      * Retorna el monto total de Base Imponible Adicional en Devolución almacenado.
      */
    public double getAdditionalRateDevolution() {
        return additionalRateDevolution;
    }

   private void setAdditionalRateDevolution(double additionalRateDevolution) {
        this.additionalRateDevolution = additionalRateDevolution;
    }
     /**
      * Retorna el monto total de IVA Adicional ó Tasa(3) en Devolución almacenado.
      */
    public double getAdditionalRateTaxDevolution() {
        return additionalRateTaxDevolution;
    }

   private void setAdditionalRateTaxDevolution(double additionalRateTaxDevolution) {
        this.additionalRateTaxDevolution = additionalRateTaxDevolution;
    }
     /**
      * Retorna el número de la última Nota de Credito ó Devolución.
      */
    public int getNumberOfLastCreditNote() {
        return numberOfLastCreditNote;
    }

   private void setNumberOfLastCreditNote(int numberOfLastCreditNote) {
        this.numberOfLastCreditNote = numberOfLastCreditNote;
    }
    /**
      * Retorna el número de la última Nota de Débito ó Devolución.
      */
    public int getNumberOfLastDebitNote() {
        return numberOfLastDebitNote;
    }

   private void setNumberOfLastDebitNote(int numberOfLastDebitNote) {
        this.numberOfLastDebitNote = numberOfLastDebitNote;
    }
   /**
      * Retorna el número de la última Nota de Débito ó Devolución.
      */
    public int getNumberOfLastNonFiscal() {
        return numberOfLastNonFiscal;
    }

   private void setNumberOfLastNonFiscal(int numberOfLastNonFiscal) {
        this.numberOfLastNonFiscal = numberOfLastNonFiscal;
    }
    /**
      * Retorna el monto total Exento en Nota de debito almacenado.
      */
    public double getFreeTaxDebitNote() {
        return freeTaxDebit;
    }

    private void setFreeTaxDebitNote(double freeTaxDebit) {
        this.freeTaxDebit = freeTaxDebit;
    }
    /**
      * Retorna el monto total de Base Imponible General en Nota de debito almacenado.
      */
    public double getGeneralRateDebitNote() {
        return generalRateDebit;
    }

   private void setGeneralRateDebitNote(double generalRateDebit) {
        this.generalRateDebit = generalRateDebit;
    }
    /**
      * Retorna el monto total de IVA General ó Tasa(1) en Nota de debito almacenado.
      */
    public double getGeneralRateTaxDebitNote() {
        return generalRateTaxDebit;
    }

   private void setGeneralRateTaxDebitNote(double generalRateTaxDebit) {
        this.generalRateTaxDebit = generalRateTaxDebit;
    }
    /**
      * Retorna el monto total de Base Imponible Reducida en Nota de debito almacenado.
      */
    public double getReducedRateDebitNote() {
        return reducedRateDebit;
    }

   private void setReducedRateDebitNote(double reducedRateDebit) {
        this.reducedRateDebit = reducedRateDebit;
    }
    /**
      * Retorna el monto total de IVA Reducido ó Tasa(2) en Nota de debito almacenado.
      */
    public double getReducedRateTaxDebitNote() {
        return reducedRateTaxDebit;
    }

    private void setReducedRateTaxDebitNote(double reducedRateTaxDebit) {
        this.reducedRateTaxDebit= reducedRateTaxDebit;
    }
    /**
      * Retorna el monto total de Base Imponible Adicional en Nota de debito almacenado.
      */
    public double getAdditionalRateDebitNote() {
        return additionalRateDebit;
    }

   private void setAdditionalRateDebitNote(double additionalRateDebit) {
        this.additionalRateDebit = additionalRateDebit;
    }
     /**
      * Retorna el monto total de IVA Adicional ó Tasa(3) en Nota de debito almacenado.
      */
    public double getAdditionalRateTaxDebitNote() {
        return additionalRateTaxDebit;
    }

   private void setAdditionalRateTaxDebitNote(double additionalRateTaxDebit) {
        this.additionalRateTaxDebit = additionalRateTaxDebit;
    }
}
