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

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import data.*;
import common.DeviceException;
import static contable.TicketLineInfo.round;
import elements.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import util.StringUtils;
import util.Utilities;

/**
 *
 * @author adrianromero
 */
public class TicketInfo {

    private static final long serialVersionUID = 2765650092387265178L;

    public static final int RECEIPT_NORMAL = 0;
    public static final int RECEIPT_REFUND = 1;
    public static final int RECEIPT_PAYMENT = 3;
    
    public static final int STATUS_CLOSED = 1;
    public static final int STATUS_VOID = 0;

    private static DateFormat m_hourformat = new SimpleDateFormat("hh:mm");
    private static DateFormat m_dateformat = new SimpleDateFormat("dd-MM-yyyy");
       
    NumberFormat formatter = new DecimalFormat("###,##0.00");
    NumberFormat formatterBase = new DecimalFormat("###,##0.000"); 
    private String m_sId;
    private int tickettype;
    private int m_iTicketId;
    private int m_status;
    private java.util.Date m_dDate;
    private Properties attributes;
    private Cashier m_User;
    private FooterHeader m_FootHeads;
    private Equipments m_Equipmet;
    private String m_sActiveCash = "";
    private double m_sActivoCash = 0.0;
    private List<TicketLineInfo> m_aLines;
    private List<PaymentInfo> payments;
    private List<TicketTaxInfo> taxes;
    private List<String> m_linesNotFiscal;
    private CustomerInfo m_Customer = null;
    public boolean isAjustableGlobal = false;
    private boolean isComision10 = false;
    private double basesSuma = 0.00;
    private double presentSubtotal = 0.00;
     private double sumOfProducts = 0.00;
    private String m_SimbolCurrency;
    private String m_NameType;
    private boolean result = false;
    public ArrayList<String> subtotalList = new ArrayList<String>();
    private int productID = 0;
    public ArrayList<String> rechargeAmount = new ArrayList<String>();
    public ArrayList<String> DiscountAmount = new ArrayList<String>();
    public ArrayList<String> typeAdjustment = new ArrayList<String>();
    private ArrayList<Double> taxesValuesList = new ArrayList<Double>();
    private ArrayList<String> taxNames = new ArrayList<String>();
    private ArrayList<String> nameAdjustmentCorrected = new ArrayList<String>();    
    public ArrayList<Double> amountAdjustmentCorrected = new ArrayList<Double>(); 
    private String printChange = "";    
    private double change = 0.0;
    private boolean subtotalVisor = false;
    private String presentSubtotalVisor = "";
    private String barcode = "";
    private String type = "";
    private String position = "";

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setFootBarcode(String barcode, String type, String position){
        this.barcode = barcode;
        this.type = type;
        this.position = position;
    }
    
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTypeBarcode() {
        return type;
    }

    public void setTypeBarcode(String type) {
        this.type = type;
    }

    public String getPresentSubtotalVisor() {
        this.setSubtotalVisor(false);
        return presentSubtotalVisor;
    }

    public void setPresentSubtotalVisor(Double presentSubtotalVisor) {        
        this.presentSubtotalVisor = formatter.format(presentSubtotalVisor);
    }

    public boolean isSubtotalVisor() {
        return subtotalVisor;
    }

    public void setSubtotalVisor(boolean subtotalVisor) {
        this.subtotalVisor = subtotalVisor;
    }

    public String printChange() {
        printChange = formatter.format(getChange());
        return printChange;
    }

    public void setPrintChange(String printChange) {
        this.printChange = printChange;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    } 

    public void setSubtotalAdjustmentCorrected(){
        String amountCorrected = "";
        this.setNameAdjustmentCorrected(this.getTypeAdjustment().get(this.getTypeAdjustment().size()-1));
        if(!"".equals(this.getRechargeAmount().get(this.getRechargeAmount().size()-1))){
            amountCorrected = this.getRechargeAmount().get(this.getTypeAdjustment().size()-1);
        }
        else if(!"".equals(this.getDiscountAmount().get(this.getDiscountAmount().size()-1))){
            amountCorrected = this.getDiscountAmount().get(this.getTypeAdjustment().size()-1);
        }        
        this.setAmountAdjustmentCorrected(-1*toDouble(amountCorrected));
    }
    
    public List<String> printNameAdjustmentCorrected(){
        return this.getNameAdjustmentCorrected();
    }
    
    public String printAmountAdjustmentCorrected(int ite){      
        return formatter.format(this.getAmountAdjustmentCorrected().get(ite));
    }
    
    public void setSubtotalAdjustmentCorrected(String name, Double amount){
        this.setNameAdjustmentCorrected(name);
        this.setAmountAdjustmentCorrected(amount);
    }  
    
    public ArrayList<String> getNameAdjustmentCorrected() {
        return nameAdjustmentCorrected;
    }
    
    public void setNameAdjustmentCorrected(String nameAdjustmentCorrected) {
        
        this.nameAdjustmentCorrected.add(nameAdjustmentCorrected);
    }

    public ArrayList<Double> getAmountAdjustmentCorrected() {
        return amountAdjustmentCorrected;
    }

    public void setAmountAdjustmentCorrected(Double amountAdjustmentCorrected) {
        this.amountAdjustmentCorrected.add(amountAdjustmentCorrected);
    }       

    /** Creates new TicketModel */
    public TicketInfo(String pLocalCurrency) {
        m_sId = UUID.randomUUID().toString();
        tickettype = RECEIPT_NORMAL;
        m_iTicketId = 0; // incrementamos
        m_status = TicketInfo.STATUS_CLOSED;
        m_dDate = new Date();
        attributes = new Properties();
        m_User = null;
        m_Customer = null;
        m_sActiveCash = null;
        m_aLines = new ArrayList<TicketLineInfo>(); // vacio de lineas
        payments = new ArrayList<PaymentInfo>();
        taxes = new ArrayList<TicketTaxInfo>();
        m_linesNotFiscal = null;
        m_NameType = "TICKET";
        m_SimbolCurrency = pLocalCurrency;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        // esto es solo para serializar tickets que no estan en la bolsa de tickets pendientes
        out.writeObject(m_sId);
        out.writeInt(tickettype);
        out.writeInt(m_iTicketId);
        out.writeObject(m_Customer);
        out.writeObject(m_dDate);
        out.writeObject(attributes);
        out.writeObject(m_aLines);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // esto es solo para serializar tickets que no estan en la bolsa de tickets pendientes
        m_sId = (String) in.readObject();
        tickettype = in.readInt();
        m_iTicketId = in.readInt();
        m_Customer = (CustomerInfo) in.readObject();
        m_dDate = (Date) in.readObject();
        attributes = (Properties) in.readObject();
        m_aLines = (List<TicketLineInfo>) in.readObject();
        m_User = null;
        m_sActiveCash = null;

        payments = new ArrayList<PaymentInfo>();
        taxes = null;
    }

    public void readValues(String[] dr) throws DeviceException {
        m_sId = dr[0];
        tickettype = Integer.valueOf(dr[1]);
        m_iTicketId = Integer.valueOf(dr[2]);
        Calendar calLocal = Calendar.getInstance(); //Format YYYY-mm-dd hh:ii
        calLocal.set(Integer.valueOf(dr[3].substring(0,4)), Integer.valueOf(dr[3].substring(6,8)), Integer.valueOf(dr[3].substring(10,12)), Integer.valueOf(dr[3].substring(14,16)),  Integer.valueOf(dr[3].substring(18,20)));
        m_dDate = calLocal.getTime();
        m_sActiveCash = dr[4];
        try {
            byte[] img = dr[5].getBytes(); 
            if (img != null) {
                attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        } catch (IOException e) {
        }
     //   m_User = new UserInfo(dr.getString(7), dr.getString(8));
    //    m_Customer = new CustomerInfoExt(dr.getString(9));
        m_aLines = new ArrayList<TicketLineInfo>();

        payments = new ArrayList<PaymentInfo>();
        taxes = null;
    }

    public TicketInfo copyTicket() {
        TicketInfo t = new TicketInfo(this.m_SimbolCurrency);

        t.tickettype = tickettype;
        t.m_iTicketId = m_iTicketId;
        t.m_dDate = m_dDate;
        t.m_status = m_status;
        t.m_sActiveCash = m_sActiveCash;
        t.attributes = (Properties) attributes.clone();
        t.m_User = m_User;
        t.m_Customer = m_Customer;

        t.m_aLines = new ArrayList<TicketLineInfo>();
        for (TicketLineInfo l : m_aLines) {
            t.m_aLines.add(l.copyTicketLine());
        }
        t.refreshLines();

        t.payments = new LinkedList<PaymentInfo>();
        for (PaymentInfo p : payments) {
            t.payments.add(p.copyPayment());
        }

        // taxes are not copied, must be calculated again.

        return t;
    }

    public String getId() {
        return m_sId;
    }

    public int getTicketType() {
        return tickettype;
    }
    
    public void setStatus(int pStatus)
    {
        m_status = pStatus;
    }
    
    public int getStatus()
    {
       return  m_status;
    }

    public void setTicketType(int tickettype) {
        this.tickettype = tickettype;
    }

    public int getTicketId() {
        return m_iTicketId;
    }

    public void setTicketId(int iTicketId) {
        m_iTicketId = iTicketId;
    }
    
    public void setNotFiscalLines(List<String> pValues)
    {
        m_linesNotFiscal = pValues;
    }
    
    public void setNameType(String pValue)
    {
            this.m_NameType = pValue;
    }
    
    public String getNameType()
    {
        return this.m_NameType;
    }

    public String getName(Object info) {

        StringBuilder name = new StringBuilder();

        if (getCustomer() != null) {
            name.append(m_Customer.toString());
            name.append(" - ");
        }

        if (info == null) {
            if (m_iTicketId == 0) {
                name.append("(" + m_hourformat.format(m_dDate) + " " + Long.toString(m_dDate.getTime() % 1000) + ")");
            } else {
                name.append(Integer.toString(m_iTicketId));
            }
        } else {
            name.append(info.toString());
        }
        
        return name.toString();
    }

    public String getName() {
        return getName(null);
    }

    public java.util.Date getDate() {
        return m_dDate;
    }
    
    public void setDate(java.util.Date dDate) {
        m_dDate = dDate;
    }
    
    public DateFormat getHour() {
        return m_hourformat;
    }
    
    public void setHour(DateFormat dHour) {
        m_hourformat = dHour;
    }

    public CustomerInfo getCustomer() {
       return m_Customer;
    }

    public void setCustomer(CustomerInfo value) {
        m_Customer = value;
    }
    
    public String getTransactionID(){
        return (getPayments().size()>0)
            ? ( getPayments().get(getPayments().size()-1) ).getTransactionID()
            : StringUtils.getCardNumber(); //random transaction ID
    }
    
    public void setUser(Cashier pValue) {
        m_User = pValue;
    }
    
     public Cashier getUser() {
        
          return  m_User;
    }
    
    public void setEquipmet(Equipments pValue) {
        
        m_Equipmet = pValue;
    }
    
    
    public Equipments getEquipmet() {
        
         return m_Equipmet;
    }
    
    public void setFooterHeader(FooterHeader pValue) {
        
        m_FootHeads = pValue;
    }
     
  /*  
    public String getReturnMessage(){
        return ( (getPayments().get(getPayments().size()-1)) instanceof PaymentInfoMagcard )
            ? ((PaymentInfoMagcard)(getPayments().get(getPayments().size()-1))).getReturnMessage()
            : LocalRes.getIntString("button.ok");
    }
*/
    public void setActiveCash(String value) {
        m_sActiveCash = value;
    }

    public String getActiveCash() {
        return m_sActiveCash;
    }
    
    public void setActivoCash(Double value) {
        m_sActivoCash = value;
    }

    public Double getActivoCash() {
        return m_sActivoCash;
    }

    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }

    public Properties getProperties() {
        return attributes;
    }

    public TicketLineInfo getLine(int index) {
        return m_aLines.get(index);
    }

    public void addLine(TicketLineInfo oLine) {
        oLine.m_SimbolCurrency = this.m_SimbolCurrency;
        oLine.setTicket(m_sId, m_aLines.size());
        m_aLines.add(oLine);
    }

    public void insertLine(int index, TicketLineInfo oLine) {
        m_aLines.add(index, oLine);
        refreshLines();
    }

    public void setLine(int index, TicketLineInfo oLine) {
        oLine.setTicket(m_sId, index);
        m_aLines.set(index, oLine);
    }

    public void removeLine(int index) {
        m_aLines.remove(index);
        refreshLines();
    }

    private void refreshLines() {
        for (int i = 0; i < m_aLines.size(); i++) {
            getLine(i).setTicket(m_sId, i);
        }
    }

    public int getLinesCount() {
        return m_aLines.size();
    }
    
    public double getArticlesCount() {
        double dArticles = 0.00;
        TicketLineInfo oLine;

        for (Iterator<TicketLineInfo> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();
            dArticles += oLine.getMultiply();
        }

        return dArticles;
    }
	
	public void setSubTotal(double sTot){
	
	this.basesSuma = sTot;
    
    }	
	

    public double getSubTotal() {
        double sum = 0.00;
        if(!isAjustableGlobal ){
        for (TicketLineInfo line : m_aLines) {        
            sum += line.getSubValue() + line.getRechargeAmount() + line.getDiscountAmount();
        }
		}
        //else
//		{
//		   sum = basesSuma;
//		}
        return sum;
    }    
    
    public void setPresentSubTotal(double sTot){
	
	this.presentSubtotal = sTot;
    
    }		

        
    public double getFinalSubtotal() {
        double sum = 0.00;
        for (TicketLineInfo line : m_aLines) {             
                sum += line.getProductBaseAmount();             
        }
        return sum;
    }
    
    public double getFinalIva() {
        return getTaxesValuesList().get(0) +
               getTaxesValuesList().get(1) +
               getTaxesValuesList().get(2);        
    }
    
    public String printFinalIva(){
        return "Bs " + formatter.format(getFinalIva());
    }
    
    public String printFinalSubtotal(){
        return "Bs " + formatter.format(getFinalSubtotal());
    }
    
    public double getPresentSubTotal(int presentLineId) {
        double sum = 0.00;
        int itea = 0;
        for (TicketLineInfo line : m_aLines) { 
            if(line.getProductId() <= presentLineId){            
                sum += line.getSubValue() + line.getRechargeAmount() + line.getDiscountAmount(); 
                if(line.isAdjustmentCorrected()){
                    sum -= line.getRechargeAmount() + line.getDiscountAmount(); 
                }
                if(getAmountAdjustmentCorrected().size() > itea){
                if(getAmountAdjustmentCorrected().get(itea) != 0.0){
                    sum += getAmountAdjustmentCorrected().get(itea);
                }
                }
               itea++; 
            }             
        }
      int ite = 0;
//      System.out.println("getPresentSubtotal(): Subtotal size: " + getSubtotalList().size()+ ". Recharge Size: " + getRechargeAmount().size() + ". Discount Size: " + getDiscountAmount().size());
      while((this.getSubtotalList().size()-1) >= ite){
          String recharge = "";
          String discount = "";
          if(this.getRechargeAmount().size() == this.getSubtotalList().size()){
            recharge = this.getRechargeAmount().get(ite);            
          }
          if(this.getDiscountAmount().size() == this.getSubtotalList().size()){
            discount = this.getDiscountAmount().get(ite); 
          }
        recharge = recharge.replace(".", "");
        discount = discount.replace("Bs ","");
        recharge = recharge.replace("Bs ","");
        discount = discount.replace(".", "");
        recharge = recharge.replace(",", ".");
        discount = discount.replace(",", ".");
        if((recharge.equals("")) || recharge.isEmpty()){
            recharge = "0.0";
        }
        if((discount.equals("")) || discount.isEmpty()){
            discount = "0.0";
        }   
        sum += Double.valueOf(recharge) + Double.valueOf(discount);
        ite++;
      }           
//            System.out.println(sum + ": Subtotal size: " + getSubtotalList().size()+ ". Recharge Size: " + getRechargeAmount().size() + ". Discount Size: " + getDiscountAmount().size());

        return sum;
    }    
    
    public void setSubtotalCalled(boolean result){
        this.result = result;
    }
    
    public boolean getSubtotalCalled(){
        return result;
    }
	
    public boolean isSubtotalCalled(){
        return getSubtotalCalled();
    }

    public String getM_sId() {
        return m_sId;
    }
    public void addSubtotalLine(String subtotalLine){
        subtotalList.add(subtotalLine);
    }
    
    public ArrayList<String> getSubtotalList(){
        return subtotalList;
    }
	
    public List<String> printSubtotalList(){
        return getSubtotalList();
    }

    public double getTax() {

        double sum = 0.00;
        if (hasTaxesCalculated()) {
            for (TicketTaxInfo tax : taxes) { 
                sum += tax.getTax(); // Taxes are already rounded...                          
              
            }
        } else {
            for (TicketLineInfo line : m_aLines) {       
                sum += line.getTax();                                  
            }
        }
        return sum;
    }

    public double getTotal() {        
        return this.getFinalSubtotal() + this.getFinalIva();     
    }

    public double getTotalPaid() {

        double sum = 0.00;
        for (PaymentInfo p : payments) {
            if (!"debtpaid".equals(p.getName())) {
                sum += p.getTotal();
            }
        }
        return sum;
    }

    public List<TicketLineInfo> getLines() {
        return m_aLines;
    }

    public void setLines(List<TicketLineInfo> l) {
        m_aLines = l;
    }

    public List<PaymentInfo> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInfo> l) {
        payments = l;
    }

    public void resetPayments() {
        payments = new ArrayList<PaymentInfo>();
    }

    public List<TicketTaxInfo> getTaxes() {
        return taxes;
    }

    public boolean hasTaxesCalculated() {
        return taxes != null;
    }

    public void setTaxes(List<TicketTaxInfo> l) {
        taxes = l;
    }
    
     public void addTaxeInfo(TicketTaxInfo ticketTax) {
        taxes.add(ticketTax);
     }

    public void resetTaxes() {
        taxes = null;
    }

    public TicketTaxInfo getTaxLine(TaxInfo tax) {

        for (TicketTaxInfo taxline : taxes) {
            if (tax.getId().equals(taxline.getTaxInfo().getId())) {
                return taxline;
            }
        }

        return new TicketTaxInfo(tax);
    }
	
public int getProductID(){
    return productID;
}


public double toDouble(String value){
    value = value.replace(".", "");
    value = value.replace("Bs ","");
    value = value.replace(",", ".");
    if((value.equals("")) || value.isEmpty()){
        value = "0.0";
    }
    return Double.valueOf(value);
}

public double getSumOfProducts(){
    sumOfProducts = 0.0;
    for(TicketLineInfo line : this.getLines()){
        sumOfProducts += line.getSubValue() + line.getDiscountAmount() + line.getRechargeAmount();
    }   
    return (sumOfProducts);
}

public void sumProductID(){
    productID += 1;
}

    public TicketTaxInfo[] getTaxLines() {

        Map<String, TicketTaxInfo> m = new HashMap<String, TicketTaxInfo>();

        TicketLineInfo oLine;
        for (Iterator<TicketLineInfo> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();

            TicketTaxInfo t = m.get(oLine.getTaxInfo().getId());
            if (t == null) {
                t = new TicketTaxInfo(oLine.getTaxInfo());
                m.put(t.getTaxInfo().getId(), t);
            }
            t.add(oLine.getSubValue());
        }

        // return dSuma;       
        Collection<TicketTaxInfo> avalues = m.values();
        return avalues.toArray(new TicketTaxInfo[avalues.size()]);
    }
    
    public List<Double> getTaxesValuesList(){
        taxesValuesList.add(getTotalBaseAmountForTax(1)*.12);
        taxesValuesList.add(getTotalBaseAmountForTax(2)*.08);
        taxesValuesList.add(getTotalBaseAmountForTax(3)*.22);
        return taxesValuesList;
    }
    
    public double getTotalValueForTax(int tax){
        return getTaxesValuesList().get(tax);
    }
    
    public List<String> printTotalValuesTaxes(){
        ArrayList<String> printTaxesValue = new ArrayList<String>();
        printTaxesValue.add("");
        printTaxesValue.add("Bs " + formatter.format(getTaxesValuesList().get(0)));
        printTaxesValue.add("Bs " + formatter.format(getTaxesValuesList().get(1)));
        printTaxesValue.add("Bs " + formatter.format(getTaxesValuesList().get(2)));        
        return printTaxesValue;
    }
    
    public List<String> printTaxName(){
        ArrayList<String> printTaxesName = new ArrayList<String>();
        printTaxesName.add("");
        printTaxesName.add("IVA G (12.00%)");
        printTaxesName.add("IVA R (8.00%)");
        printTaxesName.add("IVA A (22.00%)");        
        return printTaxesName;
    }
    
    public List<String> printBaseName(){
        ArrayList<String> printBasesName = new ArrayList<String>();
        printBasesName.add("EXENTO");
        printBasesName.add("BI G (12.00%)");
        printBasesName.add("BI R (8.00%)");
        printBasesName.add("BI A (22.00%)");
        return printBasesName;
    }
    
    public String printBaseAmountForTax(int tax){
        String BaseAmountForTax = formatter.format(getTotalBaseAmountForTax(tax));
        BaseAmountForTax = "Bs " + BaseAmountForTax; 
        return BaseAmountForTax;
    }
    
    public double getTotalBaseAmountForTax(int value){
        double base = 0.0;
        switch(value){
            case 0: //Exento 0% 
                for(TicketLineInfo line : m_aLines){
                    if("0".equals(line.getTaxInfo().getId())){
                        base += line.getProductBaseAmount();
                    }
                }
                break;
            case 1: //General 12% 
                for(TicketLineInfo line : m_aLines){
                    if("1".equals(line.getTaxInfo().getId()))
                        base += line.getProductBaseAmount();
                }
                break;
            case 2: //Reducido 8% 
                for(TicketLineInfo line : m_aLines){
                    if("2".equals(line.getTaxInfo().getId()))
                        base += line.getProductBaseAmount();
                }
                break;
            case 3: //Adicional 22% 
                for(TicketLineInfo line : m_aLines){
                    if("3".equals(line.getTaxInfo().getId()))
                        base += line.getProductBaseAmount();
                }
                break;
        }
        return base;
    }
        
    public void setComision(boolean valor)
    {
       this.isComision10 = valor;
    }
    
    public boolean getComision()
    {  	 
          return this.isComision10;
    }

    public String printId() {
        if (m_iTicketId > 0) {
            // valid ticket id
            return Utilities.StringFormatXero(Formats.INT.formatValue(new Integer(m_iTicketId)), 8);
        } else {
            return "";
        }
    }
    
    public String printHour() {
        return m_hourformat.format(m_dDate);
    }

    public String printDate() {
        return m_dateformat.format(m_dDate);
    }

    public String printUser() {
        return m_User == null ? "" : m_User.getName();
    }
    
    public String printCustomerId() {
        return m_Customer == null ? "" : m_Customer.getId();
    }
    
    public String printCustomerDoc() {
        return m_Customer == null ? "" : m_Customer.getTaxCustomerID();
    }
    
    public String printCustomerDocAffected() {
        return m_Customer == null ? "" : m_Customer.getNotes();
    }

    public List<String> printCustomer() {
        if(m_Customer != null)
        { 
             List<String> linesClient = new ArrayList<String>();
             if(m_Customer.getFiscalInvoice() != null)
             linesClient.add(m_Customer.getFiscalInvoice());
             if(m_Customer.getFiscalDate() != null)
             linesClient.add(m_Customer.getFiscalDate());
             if(m_Customer.getFiscalSerial()!= null)
             linesClient.add(m_Customer.getFiscalSerial());
             if(m_Customer.getId() != null)
             linesClient.add(m_Customer.getId());
             if(m_Customer.getSocialreason() != null)
             linesClient.add(m_Customer.getSocialreason());
             if(m_Customer.getFirstname() != null)
             linesClient.add(m_Customer.getFirstname());
             if(m_Customer.getAddress() != null)
             linesClient.add(m_Customer.getAddress());
             if(m_Customer.getPhone2() != null)
             linesClient.add(m_Customer.getPhone2());
             if(m_Customer.getCity() != null)
             linesClient.add(m_Customer.getCity());             
             if(m_Customer.getPhone() != null)
             linesClient.add(m_Customer.getPhone());
             if(m_Customer.getFax() != null)
             linesClient.add(m_Customer.getFax());
             if(m_Customer.getNotes() != null)
             linesClient.add(m_Customer.getNotes());
             if(m_Customer.getEmail() != null)
             linesClient.add(m_Customer.getEmail());
             if(m_Customer.getAddress2() != null)
             linesClient.add(m_Customer.getAddress2());
             if(m_Customer.getRegion() != null)
             linesClient.add(m_Customer.getRegion()); 
             if(m_Customer.getPostal() != null)
             linesClient.add(m_Customer.getPostal());
            return linesClient;
        }
        else
           return null;
    }
    
     public List<String> printPieticket()
     {
       if(m_FootHeads != null)
        { 
             List<String> linesFooter = new ArrayList<String>();
             linesFooter.add(m_FootHeads.getP1());    
             linesFooter.add(m_FootHeads.getP2());  
             linesFooter.add(m_FootHeads.getP3());    
             linesFooter.add(m_FootHeads.getP4());
             linesFooter.add(m_FootHeads.getP5());    
             linesFooter.add(m_FootHeads.getP6());
             linesFooter.add(m_FootHeads.getP7());    
             linesFooter.add(m_FootHeads.getP8());
             
            return linesFooter;
        }
        else
           return null;
     }
     
     public List<String> printEncabticket()
     {
      if(m_FootHeads != null)
        { 
             List<String> linesHeader = new ArrayList<String>();
             
             linesHeader.add(m_FootHeads.getE1());    
             linesHeader.add(m_FootHeads.getE2());  
             linesHeader.add(m_FootHeads.getE3());    
             linesHeader.add(m_FootHeads.getE4()); 
             linesHeader.add(m_FootHeads.getE5());    
             linesHeader.add(m_FootHeads.getE6());  
             linesHeader.add(m_FootHeads.getE7());    
             linesHeader.add(m_FootHeads.getE8());   
             
            return linesHeader;
        }
        else
           return null;
     }
     
     public List<String> printLinesNoFiscal()
     {
      if(m_linesNotFiscal != null)
           return m_linesNotFiscal;
        else
           return null;
     }     
     
     public String printNumberRegister()
     {
       if(m_Equipmet != null)
           return m_Equipmet.getMachine_register();
        else
           return "";
     }
     
     public String printIdFiscal()
     {
       if(m_Equipmet != null)
           return m_Equipmet.getId_fiscal();
        else
           return "";
     }


    public String printArticlesCount() {
        return Formats.DOUBLE.formatValue(new Double(getArticlesCount()));
    }

    public String printSubTotal() {
        return m_SimbolCurrency + " " + formatter.format(round(getSubTotal(),2)); //.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }

    public String printTax() {
        return m_SimbolCurrency + " " + formatter.format(round(getTax(),2)); //.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }
    
    

    public String printTotal() {
        return m_SimbolCurrency + " " + formatter.format(getTotal());//.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }

    public String printTotalPaid() {
        return m_SimbolCurrency + " " + (formatter.format(round(getTotalPaid(),2))); //.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }
        public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
        }
        
        //Para emitir recargos/descuentos en procentaje/monto en subtotales:
        
    public void addTypeAdjustment(String value){
        typeAdjustment.add(value);
    }
    
    public List<String> getTypeAdjustment(){
        return typeAdjustment;
    }
    
    public List<String> printTypeAdjustment(){
        return getTypeAdjustment();
    } 
            
    public void addRechargeAmount(String value){
        rechargeAmount.add(value);
    }
    
    public List<String> getRechargeAmount(){
        return rechargeAmount;
    }
    
    public List<String> printRechargeAmount(){
//        int ite = 0;
//        ArrayList<String> newList = new ArrayList<String>();
//        while(this.getRechargeAmount().size() >= ite){
//        newList.set(ite,"Bs " + formatter.format(round(this.getRechargeAmount().get(ite),2)));
//        ite++;
//    }
//        return newList;
//        
        return getRechargeAmount();
    }
    
    public void addDiscountAmount(String value){
        DiscountAmount.add(value);
    }
    
    public List<String> getDiscountAmount(){
        return DiscountAmount;
    }
    
    public List<String> printDiscountAmount(){
//        int ite = 0;
//        ArrayList<String> newList = new ArrayList<String>();
//        while(this.getRechargeAmount().size() >= ite){
//        newList.set(ite,"Bs " + formatter.format(round(this.getDiscountAmount().get(ite),2)));
//        ite++;
//    }       
//        return newList;
        return this.getDiscountAmount();
    }
    
    
}
