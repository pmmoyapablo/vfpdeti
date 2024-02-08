package cw;

import common.DeviceException;
import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import java.util.Calendar;
import java.util.Date;

public class S1PrinterData extends  PrinterData implements IPrinterData {
	private int cashierNumber;
	private double subTotalSalesFinalClient;
	private double subTotalSalesCreditFiscal;
	private double subTotalNCFinalClient;
	private double subTotalNCCreditFiscal;
	private int lastNumberFiscalTran;
	private int quantityOfTransactionsToday;
	private int numberLastNonFiscalDocuments;
	private int quantityNonFiscalDocuments;
	private int CountfiscalMemoryReports;
	private int countDailyClZ;
	private String RNC;
        private String serial;
        private Date currentPrinterDateTime; 
	
	public void setSerial(String pSerial) {
		this.serial = pSerial;
	}
        
        private void setCurrentPrinterDateTime(Date currentPrinterDateTime) {
		this.currentPrinterDateTime = currentPrinterDateTime;
	}
	
	
	public int getCashierNumber() {
		return cashierNumber;
	}
	public void setCashierNumber(int cashierNumber) {
		this.cashierNumber = cashierNumber;
	}
	public double getSubTotalSalesFinalClient() {
		return subTotalSalesFinalClient;
	}
	public void setSubTotalSalesFinalClient(double subTotalSalesFinalClient) {
		this.subTotalSalesFinalClient = subTotalSalesFinalClient;
	}
	public double getSubTotalSalesCreditFiscal() {
		return subTotalSalesCreditFiscal;
	}
	public void setSubTotalSalesCreditFiscal(double subTotalSalesCreditFiscal) {
		this.subTotalSalesCreditFiscal = subTotalSalesCreditFiscal;
	}
	public double getSubTotalNCFinalClient() {
		return subTotalNCFinalClient;
	}
	public void setSubTotalNCFinalClient(double subTotalNCFinalClient) {
		this.subTotalNCFinalClient = subTotalNCFinalClient;
	}
	public double getSubTotalNCCreditFiscal() {
		return subTotalNCCreditFiscal;
	}
	public void setSubTotalNCCreditFiscal(double subTotalNCCreditFiscal) {
		this.subTotalNCCreditFiscal = subTotalNCCreditFiscal;
	}
	public int getLastNumberFiscalTran() {
		return lastNumberFiscalTran;
	}
	public void setLastNumberFiscalTran(int lastNumberFiscalTran) {
		this.lastNumberFiscalTran = lastNumberFiscalTran;
	}
	public int getQuantityOfTransactionsToday() {
		return quantityOfTransactionsToday;
	}
	public void setQuantityOfTransactionsToday(int quantityOfTransactionsToday) {
		this.quantityOfTransactionsToday = quantityOfTransactionsToday;
	}
	public int getNumberLastNonFiscalDocuments() {
		return numberLastNonFiscalDocuments;
	}
	public void setNumberLastNonFiscalDocuments(int numberLastNonFiscalDocuments) {
		this.numberLastNonFiscalDocuments = numberLastNonFiscalDocuments;
	}
	public int getQuantityNonFiscalDocuments() {
		return quantityNonFiscalDocuments;
	}
	public void setQuantityNonFiscalDocuments(int quantityNonFiscalDocuments) {
		this.quantityNonFiscalDocuments = quantityNonFiscalDocuments;
	}
	public int getCountfiscalMemoryReports() {
		return CountfiscalMemoryReports;
	}
	public void setCountfiscalMemoryReports(int countfiscalMemoryReports) {
		CountfiscalMemoryReports = countfiscalMemoryReports;
	}
	public int getCountDailyClZ() {
		return countDailyClZ;
	}
	public void setCountDailyClZ(int countDailyClZ) {
		this.countDailyClZ = countDailyClZ;
	}
	public String getRNC() {
		return RNC;
	}
	public void setRNC(String rNC) {
		RNC = rNC;
	}


	@Override
	public void loadData(DataLogicSales pData, TicketInfo pTicket,
			PaymentsModel pCashModel) {
              try {
                  int ca = 0;
            if(pTicket.getUser() != null)
                ca = pTicket.getUser().getId_Cashier();
		this.setCashierNumber(ca);
		this.setCountDailyClZ(pCashModel.getCountReport() - 1);
		this.setCountfiscalMemoryReports(1);
		this.setLastNumberFiscalTran(pData.getNextTicketIndex());
		this.setNumberLastNonFiscalDocuments(pData.getNewTicket_Id(9) - 1);
		this.setQuantityNonFiscalDocuments(0);
		this.setQuantityOfTransactionsToday(0);
		this.setSerial(pTicket.printNumberRegister());
                this.setRNC(pTicket.printIdFiscal());
		this.setSubTotalNCCreditFiscal(0.0);
		this.setSubTotalNCFinalClient(0.0);
		this.setSubTotalSalesCreditFiscal(0.0);
		this.setSubTotalSalesFinalClient(0.0);
                this.setCurrentPrinterDateTime(pTicket.getDate());
		
		String trama = "S1";

		String cashier = String.valueOf(this.cashierNumber);
		trama = trama +  this.StringFormatXero(cashier, 2) + (char) 0x000A;
                
                trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.subTotalSalesFinalClient)), 17) + (char) 0x000A;
                trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.subTotalSalesCreditFiscal)), 17) + (char) 0x000A;
                trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.subTotalNCFinalClient)), 17) + (char) 0x000A;
                trama = trama + this.StringFormatXero(this.StringDoubleFormat(String.valueOf(this.subTotalNCCreditFiscal)), 17) + (char) 0x000A;
		
                trama = trama + this.StringFormatXero(String.valueOf( this.lastNumberFiscalTran), 8) + (char) 0x000A;
                trama = trama + this.StringFormatXero(String.valueOf( this.quantityOfTransactionsToday), 5) + (char) 0x000A;
                
                trama = trama + this.StringFormatXero(String.valueOf( this.numberLastNonFiscalDocuments), 8) + (char) 0x000A;
                trama = trama + this.StringFormatXero(String.valueOf( this.quantityNonFiscalDocuments), 5) + (char) 0x000A;
                
                trama = trama + this.StringFormatXero(String.valueOf( this.CountfiscalMemoryReports), 4) + (char) 0x000A;
                trama = trama + this.StringFormatXero(String.valueOf( this.countDailyClZ), 4) + (char) 0x000A;
		
                trama = trama + this.StringFormatXero(this.RNC, 11) + (char) 0x000A;
                trama = trama + this.StringFormatXero(this.serial, 6) + (char) 0x000A;   
                
                Calendar Cale = Calendar.getInstance();
                Cale.setTime(this.currentPrinterDateTime);
                String y = String.valueOf(Cale.get(Calendar.YEAR));
                int m = Cale.get(Calendar.MONTH);
                String d = String.valueOf(Cale.get(Calendar.DAY_OF_MONTH));
                String h = String.valueOf(Cale.get(Calendar.HOUR));
                String min = String.valueOf(Cale.get(Calendar.MINUTE));
                String s = String.valueOf(Cale.get(Calendar.SECOND));
                ++m;
                String mes = String.valueOf(m);

                while (mes.length() < 2) {
                        mes = "0" + mes;
                }
                while (y.length() < 2) {
                        y = "0" + y;
                }
                while (d.length() < 2) {
                        d = "0" + d;
                }
                while (h.length() < 2) {
                        h = "0" + h;
                }
                while (min.length() < 2) {
                        min = "0" + min;
                }
                while (s.length() < 2) {
                        s = "0" + s;
                }
                trama = trama + h + min + s + (char) 0x000A;
                trama = trama + d + mes + y.substring(2) + (char) 0x000A;
		
		this._myBytes = trama.getBytes();
                
                  } catch (DeviceException ex) {
			this._myBytes = null;
		}
	}

}
