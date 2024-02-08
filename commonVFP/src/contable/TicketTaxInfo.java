//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package contable;

import static contable.PaymentsModel.formatter;
import data.Formats;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class TicketTaxInfo {
    
    private TaxInfo tax;
    NumberFormat formatter = new DecimalFormat("#0.00"); 
    
    private double subtotal;
    private double taxtotal;
            
    /** Creates a new instance of TicketTaxInfo */
    public TicketTaxInfo(TaxInfo tax) {
        this.tax = tax;
        
        subtotal = 0.00;
        taxtotal = 0.00;
    }
    
    public TaxInfo getTaxInfo() {
        return tax;
    }
    
    public void add(double dValue) {
        subtotal += dValue;
        taxtotal = subtotal * tax.getRate();
    }
    
    public double getSubTotal() {    
        return subtotal;
    }
	
	public void setSubTotal(double newBaseImponible) {
		  this.subtotal = newBaseImponible;      
    }
    
    public double getTax() {       
        return taxtotal;
    }
	
	public void setTax(double iva) {       
        this.taxtotal = iva;
    }
    
    public double getTotal() {         
        return subtotal + taxtotal;
    }
    
    public String printSubTotal() {
        return (formatter.format(round((getSubTotal()),2))).replace('.', ',');
    }
    public String printTax() {
        return (formatter.format(round((getTax()),2))).replace('.', ',');
    }    
    public String printTotal() {
        return (formatter.format(round((getTotal()),2))).replace('.', ',');
    }    
            public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
//        NumberFormat formatter = new DecimalFormat("#0.00");     
//        return Double.valueOf(formatter.format((double) tmp / factor));
        return (double) tmp / factor;
    }
}
