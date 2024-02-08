//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2008-2009 Openbravo, S.L.
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

public  class PaymentInfo {
    
    NumberFormat formatter = new DecimalFormat("###,##0.00");  
    private double m_dPaid;
    private double m_dTotal;
    private String name;
    private int m_id;
    
    /** Creates a new instance of PaymentInfoCash */
    public PaymentInfo(String Name,double dTotal, double dPaid, int id) {
        m_dTotal = dTotal;
        m_dPaid = dPaid;
        name = Name;
        m_id = id;
    }
    
    public PaymentInfo copyPayment(){
        return new PaymentInfo(name,m_dTotal, m_dPaid, m_id);
    }
    
    public int getPaid_Id()
    {
       return m_id;
    }
    
    public void setName(String pName) {
        this.name = pName;
    } 
    
    public String getName() {
        return name;
    }   
    public double getTotal() {
        return m_dTotal;
    }   
    
    public double getPaid() {
        return m_dPaid;
    }
    public String getTransactionID(){
        return "no ID";
    }
    
    public void setPaid(Double newPayment){
        m_dPaid = newPayment;
    }
    
    public String printPaid() {      
        
        return (formatter.format(new Double(m_dPaid)).replaceAll('\u20AC' + "", "BsF"));
    }   
    public String printChange() {
        return (formatter.format(new Double(m_dPaid - m_dTotal)).replaceAll('\u20AC' + "", "BsF"));
    }
    
    public String printTotal() {
        return (formatter.format(new Double(getTotal())).replaceAll('\u20AC' + "", "BsF"));
    }
}
