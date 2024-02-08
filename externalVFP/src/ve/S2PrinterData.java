package ve;

import common.IPrinterData;
import common.PrinterData;
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
public class S2PrinterData extends  PrinterData implements IPrinterData{
    private double subTotalBases;
    private double sudTotalTax;
    private String dataDummy;
    private double amountPayable;
    private int numberPaymentsMade;
    private int condition;
    

	public double getSubTotalBases() {
		return subTotalBases;
	}
	public void setSubTotalBases(double subTotalBases) {
		this.subTotalBases = subTotalBases;
	}
	public double getSudTotalTax() {
		return sudTotalTax;
	}
	public void setSudTotalTax(double sudTotalTax) {
		this.sudTotalTax = sudTotalTax;
	}
	public String getDataDummy() {
		return dataDummy;
	}
	public void setDataDummy(String dataDummy) {
		this.dataDummy = dataDummy;
	}
	public double getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(double amountPayable) {
		this.amountPayable = amountPayable;
	}
	public int getNumberPaymentsMade() {
		return numberPaymentsMade;
	}
	public void setNumberPaymentsMade(int numberPaymentsMade) {
		this.numberPaymentsMade = numberPaymentsMade;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}

	@Override
	public void loadData(DataLogicSales arg0, TicketInfo arg1,
			PaymentsModel arg2) {
		// TODO Auto-generated method stub
		this.setSubTotalBases(arg1.getSubTotal());
		this.setSudTotalTax(arg1.getTax());
		this.setDataDummy("0000000000000");
                double payPending = arg1.getTotal() - arg1.getTotalPaid();
		this.setAmountPayable(payPending);
		this.setNumberPaymentsMade(arg1.getPayments().size());
		this.setCondition(0);

		String trama = "S2";

		trama = trama + '\u0020';
		String subTotal = String.valueOf(this.getSubTotalBases());
		subTotal = this.StringDoubleFormat(subTotal);
                
                subTotal = this.StringFormatXero(subTotal, 13);

		trama = trama + subTotal + (char) 0x000A + '\u0020';

		String subIva = String.valueOf(this.getSudTotalTax());
		subIva = this.StringDoubleFormat(subIva);
                
                subIva = this.StringFormatXero(subIva, 13);

		trama = trama + subIva + (char) 0x000A + '\u0020';
	        trama = trama + this.getDataDummy();
	     
	        trama = trama + (char)0x000A; 
	     
	        trama = trama + "000000";
	     
	        trama = trama + (char)0x000A + '\u0020';

		String MontTotalPago = String.valueOf(this.getAmountPayable());
		MontTotalPago = this.StringDoubleFormat(MontTotalPago);
                
                MontTotalPago = this.StringFormatXero(MontTotalPago, 13);
		
		trama = trama + MontTotalPago + (char) 0x000A;

		String NumberPagos = String.valueOf(this.getNumberPaymentsMade());
                
                NumberPagos = this.StringFormatXero(NumberPagos, 4);

		trama = trama + NumberPagos + (char) 0x000A;

		trama = trama + String.valueOf(this.getCondition());

		trama = trama + (char) 0x000A;

		this._myBytes = trama.getBytes();
		
	}

}
