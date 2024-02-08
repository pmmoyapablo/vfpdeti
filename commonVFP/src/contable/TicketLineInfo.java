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

import java.io.*;
//import com.openbravo.pos.util.StringUtils;

import data.Formats;
import common.DeviceException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author adrianromero
 */
public class TicketLineInfo implements  Serializable {

    private static final long serialVersionUID = 6608012948284450199L;
    NumberFormat formatter = new DecimalFormat("###,##0.00"); 
    private String m_sTicket;
    private int m_iLine;
    private double multiply;
    private double price;
    private TaxInfo tax;
    private Properties attributes;
    private String productid;
    private int productId;
    private String attsetinstid;
    public String m_SimbolCurrency;    
    public double rechargeAmount;
    public double DiscountAmount;
    public String productCode;
    public String productNameHigher;
    public String comments;
    ArrayList<String> commentsLista = new ArrayList<String>();
    public String commentsString = "";
    private double base = 0.0;
    private boolean correction = false;
    private boolean adjustmentCorrected = false;
    private String nombreAdjustmentCorrected = "";
    private double amountAdjustmentCorrected = 0.0;
    private boolean adjustment = false;
    private boolean anulado = false;
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

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public boolean isAdjustment() {
        return adjustment;
    }

    public void setAdjustment(boolean adjustment) {
        this.adjustment = adjustment;
    }
    
    public void setAdjustmentCorrected(String nombre, double amount){
        this.setAmountAdjustmentCorrected(amount);
        this.setNombreAdjustmentCorrected(nombre);
        this.setAdjustmentCorrected(true);
    }

    public String getNombreAdjustmentCorrected() {
        return nombreAdjustmentCorrected;
    }

    public void setNombreAdjustmentCorrected(String nombreAdjustmentCorrected) {
        this.nombreAdjustmentCorrected = nombreAdjustmentCorrected;
    }

    public String printAmountAdjustmentCorrected(){
        return formatter.format(amountAdjustmentCorrected);
    }
    
    public double getAmountAdjustmentCorrected() {
        return amountAdjustmentCorrected;
    }

    public void setAmountAdjustmentCorrected(double amountAdjustmentCorrected) {
        this.amountAdjustmentCorrected = amountAdjustmentCorrected;
    }    

    public boolean isAdjustmentCorrected() {
        return adjustmentCorrected;
    }

    public void setAdjustmentCorrected(boolean adjustmentCorrected) {
        this.adjustmentCorrected = adjustmentCorrected;
    }

    public boolean isCorrection() {
        return correction;
    }

    public void setCorrection(boolean correction) {
        this.correction = correction;
    }
    
//HASTA AQUI
    /** Creates new TicketLineInfo */
    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax, Properties props) {
        init(productid, null, dMultiply, dPrice, tax, props);        
    }

    public TicketLineInfo(String productid,  double dMultiply, double dPrice, TaxInfo tax) {
        init(productid, null, dMultiply, dPrice, tax, new Properties());
    }

    public TicketLineInfo(String productid,  String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {
        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(productid, null,dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo(String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {

        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(null, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo() {
        init(null, null, 0.00, 0.00, null, new Properties());
    }

    public TicketLineInfo(ProductInfoExt product, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {

        String pid;

        if (product == null) {
            pid = null;
        } else {
            pid = product.getID();
            attributes.setProperty("product.name", product.getName());
            attributes.setProperty("product.com", product.isCom() ? "true" : "false");
            if (product.getAttributeSetID() != null) {
                attributes.setProperty("product.attsetid", product.getAttributeSetID());
            }
            attributes.setProperty("product.taxcategoryid", product.getTaxCategoryID());
            if (product.getCategoryID() != null) {
                attributes.setProperty("product.categoryid", product.getCategoryID());
            }
        }
        init(pid, null, dMultiply, dPrice, tax, attributes);
    }

    public TicketLineInfo(ProductInfoExt oProduct, double dPrice, TaxInfo tax, Properties attributes) {
        this(oProduct, 1.00, dPrice, tax, attributes);
    }

    public TicketLineInfo(TicketLineInfo line) {
        init(line.productid, line.attsetinstid, line.multiply, line.price, line.tax, (Properties) line.attributes.clone());
    }

    private void init(String productid, String attsetinstid, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {

        this.productid = productid;
        this.attsetinstid = attsetinstid;
        multiply = dMultiply;
        price = dPrice;
        this.tax = tax;
        this.attributes = attributes;
        m_sTicket = null;
        m_iLine = -1;
    }

   public void setTicket(String ticket, int line) {
        m_sTicket = ticket;
        m_iLine = line;
    }

    public void writeValues(String[] dp) throws DeviceException {
//        dp.setString(1, m_sTicket);
//        dp.setInt(2, new Integer(m_iLine));
//        dp.setString(3, productid);
//        dp.setString(4, attsetinstid);
//
//        dp.setDouble(5, new Double(multiply));
//        dp.setDouble(6, new Double(price));
//
//        dp.setString(7, tax.getId());
//        try {
//            ByteArrayOutputStream o = new ByteArrayOutputStream();
//            attributes.storeToXML(o, "", "UTF-8");
//            dp.setBytes(8, o.toByteArray());
//        } catch (IOException e) {
//            dp.setBytes(8, null);
//        }
    }

    public void readValues(String[] dr) throws DeviceException {
//        m_sTicket = dr.getString(1);
//        m_iLine = dr.getInt(2).intValue();
//        productid = dr.getString(3);
//        attsetinstid = dr.getString(4);
//
//        multiply = dr.getDouble(5);
//        price = dr.getDouble(6);
//
//        tax = new TaxInfo(dr.getString(7), dr.getString(8), dr.getString(9), dr.getString(10), dr.getString(11), dr.getDouble(12), dr.getBoolean(13), dr.getInt(14));
//     //  tax = new TaxInfo(dr.getString(7), dr.getString(8), dr.getString(9), dr.getString(10), dr.getString(11), 0.0, false, 0);
//        attributes = new Properties();
//        try {
//            byte[] img = dr.getBytes(15);
//             //byte[] img = null;
//            if (img != null) {
//                attributes.loadFromXML(new ByteArrayInputStream(img));
//            }
//        } catch (IOException e) {
//        }
    }

    public TicketLineInfo copyTicketLine() {
        TicketLineInfo l = new TicketLineInfo();
        // l.m_sTicket = null;
        // l.m_iLine = -1;
        l.productid = productid;
        l.attsetinstid = attsetinstid;
        l.multiply = multiply;
        l.price = price;
        l.tax = tax;
        l.comments = comments;
        l.attributes = (Properties) attributes.clone();
        return l;
    }
    
        public int getProductId(){
        return productId;
    }
        
    public void setProductId(int productid){
        productId = productid;
    }

    public int getTicketLine() {
        return m_iLine;
    }
    
    public void setProductID(String pValue) {
        productid = pValue;
    }

    public String getProductID() {
        return productid;
    }
    
    public void setProductNameHigher(String pValue) {
        if(pValue.length() > 24){
            pValue = pValue.substring(0, 42);
        }
        productNameHigher = pValue;
    }
    
    public String getProductNameHigher() {
            return productNameHigher;
    }
    
    public void setProductCode(String pValue) {
        productCode = pValue;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return attributes.getProperty("product.name");
    }

    public String getProductAttSetId() {
        return attributes.getProperty("product.attsetid");
    }

    public String getProductAttSetInstDesc() {        
         return attributes.getProperty("product.attsetdesc", "");       
    }

    public void setProductAttSetInstDesc(String value) {
        if (value == null) {
            attributes.remove(value);
        } else {
            attributes.setProperty("product.attsetdesc", value);
        }
    }

    public String getProductAttSetInstId() {
        return attsetinstid;
    }

    public void setProductAttSetInstId(String value) {
        attsetinstid = value;
    }
    
    public void setRechargeAmount(double value){
        rechargeAmount = value;
    }
    public double getRechargeAmount(){
        return rechargeAmount;
    }
    
    public String printAmount(){
        return (formatter.format(round(rechargeAmount + DiscountAmount,2)));
    }
    
    public String printAmountAdustmentCorrected(){
        return (formatter.format(round(this.getAmountAdjustmentCorrected(),2)));
    }
    
    public String printNameAdustmentCorrected(){
        return this.getNombreAdjustmentCorrected();
    }
    
    public String printRechargeAmount(){
        return m_SimbolCurrency + " " + (formatter.format(round(getRechargeAmount(),2)));
    }
    
    public void setDiscountAmount(double value){
        DiscountAmount = value;
    }
    
    public double getDiscountAmount(){
        return DiscountAmount;
    }
    
    public String printDiscountAmount(){
        return m_SimbolCurrency + "-" + (formatter.format(round(getDiscountAmount(),2)));
    }
    
    public String getPropertyAjuste() {

 	   if(attributes.getProperty("A1") != null)
         {return attributes.getProperty("A1");}
 		else if(attributes.getProperty("B1") != null)
         {return attributes.getProperty("B1");}
 		else
 		{ return "";}
     }

    public boolean isProductCom() {
        return "true".equals(attributes.getProperty("product.com"));
    }
    
     public boolean isProductLower() {
        if (this.getProductName().length() < 24){
            return true;
        }            
        else
            return false;
    }
     
    public boolean isProductHigher() {
        if (getProductName().length() > 24){
            productNameHigher = this.getProductName().substring(42);
            setProductNameHigher(productNameHigher);
            return true;
        }            
        else{
            return false;}
    }   
    
    public boolean isProductCode(){
        if(getProductCode() != null){
            return true;
        }
        else{
           return false; 
        }        
    }

    public String getProductTaxCategoryID() {
        return (attributes.getProperty("product.taxcategoryid"));
    }

    public String getProductCategoryID() {
        return (attributes.getProperty("product.categoryid"));
    }

    public double getMultiply() {
        return multiply;
    }

    public void setMultiply(double dValue) {
        multiply = dValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double dValue) {     
         price = dValue;      
    }

    public double getPriceTax() {  
    	
        return price * (1.00 + getTaxRate());   
    	
    }

    public void setPriceTax(double dValue) {
    	
         price = dValue / (1.00 + getTaxRate());      
    }
    

    public TaxInfo getTaxInfo() {
        return tax;
    }

    public void setTaxInfo(TaxInfo value) {
        tax = value;
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

    public double getTaxRate() {
        return tax == null ? 0.00 : tax.getRate();
    }

    public double getSubValue() {
    	 if(tax.isCascade())
         {return (double) (price * multiply);}
         else
         {
          return (double) (price * multiply);
         }
    }

    public double getTax() {
    	if(tax.isCascade())
        {return (price * multiply * getTaxRate())/(1.00+getTaxRate());}
        else
        {return price * multiply * getTaxRate();}
    }
	

    public double getValue() {	    
        return (double) (price * multiply);	
    }

    public String printName() {
        return this.productid;
        //return StringUtils.encodeXML(attributes.getProperty("product.name"));
    }
    
    public String printNameHigher() {
        return getProductNameHigher();
        //return StringUtils.encodeXML(attributes.getProperty("product.name"));
    }
    
    public String printProductCode() {        
        return this.getProductCode();
        //return StringUtils.encodeXML(attributes.getProperty("product.name"));
    }

    public String printMultiply() {
        return  (String.valueOf(getMultiply())).replace('.',',');
    }

    public String printPrice() {
        return m_SimbolCurrency + " " + formatter.format(round(getPrice(),2));//.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }

    public String printPriceTax() {
        return m_SimbolCurrency + " " + (formatter.format(round(getPriceTax(),2)));//.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }

    public String printTax() {
        return m_SimbolCurrency + " " + (formatter.format(round(getTax(),2)));
    }

    public String printTaxRate() {
        return Formats.PERCENT.formatValue(getTaxRate());
    }

    public String printSubValue() {
        return m_SimbolCurrency + " " + (formatter.format(round(getSubValue(),2)));//.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }
    
   public List<String> printCommentsLines(){
        return this.getCommentsLines();//takes the comments of the first product
    }    
   
    public ArrayList<String> getCommentsLines(){
        return commentsLista;
    }              

    public void setCommentsLines(ArrayList<String> pValue) {
       commentsLista = pValue;           
    }
    
       public String printCommentsString(){
        return this.getCommentsString();//takes the comments of the first product
    }    
   
    public String getCommentsString(){
        return commentsString;
    }              

    public void setCommentsString(String pValue) {
       commentsString = pValue;           
    }

    public String printValue() {
        return m_SimbolCurrency + " " + (formatter.format(round(getValue(),2))); //.replaceAll('\u20AC' + "", m_SimbolCurrency);
    }
    
    public void setBaseAmount(Double baseAmount){
        base = baseAmount;
    }
    
    public double getProductBaseAmount(){
        return base;
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
    /*public String formatThousands (String value){
        int length = 0;
        int coma = value.indexOf(',');
        length = value.length();
        int i = length;
        String charsLeft = "";
        String sum = "";
        while (i <= length){
            value = value.substring(0, value.indexOf(coma - 3));
            charsLeft = value.substring(value.indexOf(coma - 3), length);
            sum +=  value.substring(coma - 6, value.indexOf(coma - 3)) + "." + charsLeft;
            sum = value;
        }            
        return value;
    }*/
}
