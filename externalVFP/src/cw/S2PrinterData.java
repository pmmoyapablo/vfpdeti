/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw;

import common.*;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;

/**
 *
 * @author pmoya
 */
public class S2PrinterData extends  PrinterData implements IPrinterData{
    private double subTotalSales;
    private double sudTotalTax;
    private String futureUse;
    private int articlesQuantity;
    private double totalPayment;
    private int paymentsAmount;
    private int typeDocument;

	public double getSubTotalSales() {
		return subTotalSales;
	}
	public void setSubTotalSales(double subTotalSales) {
		this.subTotalSales = subTotalSales;
	}
	public double getSudTotalTax() {
		return sudTotalTax;
	}
	public void setSudTotalTax(double sudTotalTax) {
		this.sudTotalTax = sudTotalTax;
	}
	public String getFutureUse() {
		return futureUse;
	}
	public void setFutureUse(String futureUse) {
		this.futureUse = futureUse;
	}
	public int getArticlesQuantity() {
		return articlesQuantity;
	}
	public void setArticlesQuantity(int articlesQuantity) {
		this.articlesQuantity = articlesQuantity;
	}
	public double getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}
	public int getPaymentsAmount() {
		return paymentsAmount;
	}
	public void setPaymentsAmount(int paymentsAmount) {
		this.paymentsAmount = paymentsAmount;
	}
        
        public void setTypeDocument(int type)
        {
            this.typeDocument = type;
        }
        
        public int getTypeDocument()
        {
            return this.typeDocument;
        }
	
	public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
                this.setSubTotalSales(pTicket.getSubTotal());
                this.setSudTotalTax(pTicket.getTax());
		this.setFutureUse("0000000000000");
		this.setArticlesQuantity((int)pTicket.getArticlesCount());
		double amount = pTicket.getTotal() - pTicket.getTotalPaid();
		this.setTotalPayment(amount);
		this.setPaymentsAmount(pTicket.getPayments().size());
                this.setTypeDocument(pTicket.getTicketType());

		String trama = "S2";

		trama = trama + '\u0020';
		String subTotal = String.valueOf(this.getSubTotalSales());
		subTotal = this.StringDoubleFormat(subTotal);
                
                subTotal = this.StringFormatXero(subTotal, 13);
		trama = trama + subTotal + (char) 0x000A + '\u0020';

		String subIva = String.valueOf(this.getSudTotalTax());
		subIva = this.StringDoubleFormat(subIva);
                
                subIva = this.StringFormatXero(subIva, 13);

		trama = trama + subIva + (char) 0x000A + '\u0020';

		String futuse = this.getFutureUse();

		trama = trama + futuse + (char) 0x000A + '\u0020';

		String cantArt = String.valueOf(this.getArticlesQuantity());
		cantArt = this.StringDoubleFormat(cantArt);
                
                cantArt = this.StringFormatXero(cantArt, 6);

		trama = trama + cantArt + (char) 0x000A;

		String totalpagar = String.valueOf(this.getTotalPayment());
                 totalpagar = this.StringDoubleFormat(totalpagar);
                 
                totalpagar = this.StringFormatXero(totalpagar,13);
		trama = trama + totalpagar + (char) 0x000A;
		
		String cantpagos = String.valueOf(this.getPaymentsAmount());
		
                cantpagos = this.StringFormatXero(cantpagos, 4);

		trama = trama + cantpagos + (char) 0x000A;
                
                trama = trama + String.valueOf(this.getTypeDocument());

		trama = trama + (char) 0x000A;

		this._myBytes = trama.getBytes();
		
    }
    
}
