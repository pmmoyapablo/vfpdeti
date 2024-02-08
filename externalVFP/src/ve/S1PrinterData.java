package ve;

import common.*;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import elements.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author pmoya
 */
public class S1PrinterData extends  PrinterData implements IPrinterData{

private int cashierNumber;
private double totalDailySales;
private int lastInvoiceNumber;
private int quantityOfInvoicesToday;
private int numberNonFiscalDocuments;
private int quantityNonFiscalDocuments;
private int dailyClosureCounter;
private int auditReportsCounter;
private String RIF;
private String registeredMachineNumber;
private Date currentPrinterDateTime; 
private int lastNCNumber;
private int quantityOfNCToday;
private int lastDebtNoteNumber;
private int quantityDebtNoteToday;


	@Override
	public void loadData(DataLogicSales pData, TicketInfo pTicket,
			PaymentsModel pCashModel) {
		try {
			int ca = 0;
                        if(pTicket.getUser() != null)
                        ca = pTicket.getUser().getId_Cashier();
		        this.setCashierNumber(ca);
			this.setTotalDailySales(pData.getSalesTotal());
			this.setLastInvoiceNumber(pData.getNextTicketIndex() - 1);
			this.setQuantityOfInvoicesToday(pData.getSales());
			this.setNumberNonFiscalDocuments(0);
			this.setQuantityNonFiscalDocuments(0);
			this.setDailyClosureCounter(pCashModel.getCountReport() - 1);
			this.setAuditReportsCounter(1);
			this.setRIF(pTicket.printIdFiscal());
			this.setRegisteredMachineNumber(pTicket.printNumberRegister());
			this.setCurrentPrinterDateTime(pTicket.getDate());
			this.setLastNCNumber(pData.getNextTicketRefundIndex() - 1);
			this.setQuantityOfNCsToday(0);
			this.setLastDebtNoteNumber(0);
			this.setQuantityDebtNoteToday(0);

			String trama = "S1";

			String cajero = String.valueOf(this.cashierNumber);

			while (cajero.length() < 2) {
				cajero = "0" + cajero;
			}

			trama = trama + cajero + (char) 0x000A;

			String ventas = String.valueOf(this.totalDailySales);
			ventas = ventas.replace(".", "");

			while (ventas.length() < 17) {
				ventas = "0" + ventas;
			}

			trama = trama + ventas + (char) 0x000A;
			if (this.lastInvoiceNumber == -1) {
				this.lastInvoiceNumber = 0;
			}
			String id_lastFactura = String.valueOf(this.lastInvoiceNumber);

			while (id_lastFactura.length() < 8) {
				id_lastFactura = "0" + id_lastFactura;
			}

			trama = trama + id_lastFactura + (char) 0x000A;

			if (this.quantityOfInvoicesToday == -1) {
				this.quantityOfInvoicesToday = 0;
			}
			String cantFactday = String.valueOf(this.quantityOfInvoicesToday);

			while (cantFactday.length() < 5) {
				cantFactday = "0" + cantFactday;
			}

			trama = trama + cantFactday + (char) 0x000A;

			if (this.lastDebtNoteNumber == -1) {
				this.lastDebtNoteNumber = 0;
			}
			String id_lastND = String.valueOf(this.lastDebtNoteNumber);

			while (id_lastND.length() < 8) {
				id_lastND = "0" + id_lastND;
			}

			trama = trama + id_lastND + (char) 0x000A;

			if (this.quantityDebtNoteToday == -1) {
				this.quantityDebtNoteToday = 0;
			}
			String cantND = String.valueOf(this.quantityDebtNoteToday);

			while (cantND.length() < 5) {
				cantND = "0" + cantND;
			}

			trama = trama + cantND + (char) 0x000A;

			if (this.lastNCNumber == -1) {
				this.lastNCNumber = 0;
			}
			String id_lastNC = String.valueOf(this.lastNCNumber);

			while (id_lastNC.length() < 8) {
				id_lastNC = "0" + id_lastNC;
			}

			trama = trama + id_lastNC + (char) 0x000A;

			if (this.quantityOfNCToday == -1) {
				this.quantityOfNCToday = 0;
			}
			String cantNC = String.valueOf(this.quantityOfNCToday);

			while (cantNC.length() < 5) {
				cantNC = "0" + cantNC;
			}

			trama = trama + cantNC + (char) 0x000A;

			String lastDNF = String.valueOf(this.numberNonFiscalDocuments);

			while (lastDNF.length() < 8) {
				lastDNF = "0" + lastDNF;
			}

			trama = trama + lastDNF + (char) 0x000A;

			String cantDNFdia = String.valueOf(this.quantityNonFiscalDocuments);

			while (cantDNFdia.length() < 5) {
				cantDNFdia = "0" + cantDNFdia;
			}

			trama = trama + cantDNFdia + (char) 0x000A;

			if (this.dailyClosureCounter == -1) {
				this.dailyClosureCounter = 0;
			}
			String counterZdiario = String.valueOf(this.dailyClosureCounter);

			while (counterZdiario.length() < 4) {
				counterZdiario = "0" + counterZdiario;
			}

			trama = trama + counterZdiario + (char) 0x000A;

			String counterReporAudi = String.valueOf(this.auditReportsCounter);

			while (counterReporAudi.length() < 4) {
				counterReporAudi = "0" + counterReporAudi;
			}
			trama = trama + counterReporAudi + (char) 0x000A;

			trama = trama + this.RIF + (char) 0x000A;
			trama = trama + this.registeredMachineNumber + (char) 0x000A;

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

	private void setCashierNumber(int cashierNumber) {
		this.cashierNumber = cashierNumber;
	}

	private void setLastDebtNoteNumber(int lastDebtNoteNumber) {
		this.lastDebtNoteNumber = lastDebtNoteNumber;
	}

	private void setQuantityDebtNoteToday(int quantityDebtNoteToday) {
		this.quantityDebtNoteToday = quantityDebtNoteToday;
	}

	private void setTotalDailySales(double totalDailySales) {
		this.totalDailySales = totalDailySales;
	}

	private void setLastInvoiceNumber(int lastInvoiceNumber) {
		this.lastInvoiceNumber = lastInvoiceNumber;
	}

	private void setQuantityOfInvoicesToday(int quantityOfInvoicesToday) {
		this.quantityOfInvoicesToday = quantityOfInvoicesToday;
	}

	private void setNumberNonFiscalDocuments(int numberNonFiscalDocuments) {
		this.numberNonFiscalDocuments = numberNonFiscalDocuments;
	}

	private void setQuantityNonFiscalDocuments(int quantityNonFiscalDocuments) {
		this.quantityNonFiscalDocuments = quantityNonFiscalDocuments;
	}

	private void setDailyClosureCounter(int dailyClosureCounter) {
		this.dailyClosureCounter = dailyClosureCounter;
	}

	private void setAuditReportsCounter(int auditReportsCounter) {
		this.auditReportsCounter = auditReportsCounter;
	}

	private void setRIF(String RIF) {
		this.RIF = RIF;
	}

	private void setRegisteredMachineNumber(String registeredMachineNumber) {
		this.registeredMachineNumber = registeredMachineNumber;
	}

	private void setCurrentPrinterDateTime(Date currentPrinterDateTime) {
		this.currentPrinterDateTime = currentPrinterDateTime;
	}

	private void setLastNCNumber(int lNCNumber) {
		this.lastNCNumber = lNCNumber;
	}

	private void setQuantityOfNCsToday(int qNCsToday) {
		this.quantityOfNCToday = qNCsToday;
	}

}