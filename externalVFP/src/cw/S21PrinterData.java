package cw;

import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;

public class S21PrinterData extends  PrinterData implements IPrinterData {
    private double subTotalSalesRate1;
    private double sudTotalTaxRate1;
    private String futureUse;
    private int articlesQuantity;
    private double totalPayment;
    private int paymentsAmount;
    private int typeDocument;
    
        public void setTypeDocument(int type)
        {
            this.typeDocument = type;
        }
        
        public int getTypeDocument()
        {
            return this.typeDocument;
        }
   
	public double getSubTotalSalesRate1() {
		return subTotalSalesRate1;
	}
	public void setSubTotalSalesRate1(double subTotalSalesRate1) {
		this.subTotalSalesRate1 = subTotalSalesRate1;
	}
	public double getSudTotalTaxRate1() {
		return sudTotalTaxRate1;
	}
	public void setSudTotalTaxRate1(double sudTotalTaxRate1) {
		this.sudTotalTaxRate1 = sudTotalTaxRate1;
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
	@Override
	public void loadData(DataLogicSales arg0, TicketInfo arg1,
			PaymentsModel arg2) {
                        this.setSubTotalSalesRate1(arg1.getSubTotal());
                        this.setSudTotalTaxRate1(arg1.getTax());
                        this.setFutureUse("0000000000000");
                        this.setArticlesQuantity((int)arg1.getArticlesCount());
                        this.setTotalPayment(arg1.getTotal());;
                        this.setPaymentsAmount(arg1.getPayments().size());
                        this.setTypeDocument(arg1.getTicketType());

			String trama = "S2";

		trama = trama + '\u0020';
		String subTotal = String.valueOf(this.getSubTotalSalesRate1());
		subTotal = this.StringDoubleFormat(subTotal);
                
                subTotal = this.StringFormatXero(subTotal, 13);
		trama = trama + subTotal + (char) 0x000A + '\u0020';

		String subIva = String.valueOf(this.getSudTotalTaxRate1());
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
