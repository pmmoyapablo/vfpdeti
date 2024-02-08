/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contable;

import java.util.Date;
import data.Formats;

/**
 *
 * @author pmoya
 */
public class CustomerInfo {
    
    private String socialreason;
    private String taxcustomerid;
    private String notes;
    private boolean visible;
    private String id;
    private Date curdate;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String phone2;
    private String fax;
    private String address;
    private String address2;
    private String postal;
    private String city;
    private String region;
    private String country;
    private String fiscalDate;
    private String fiscalInvoice;

    public String getFiscalInvoice() {
        return fiscalInvoice;
    }

    public void setFiscalInvoice(String fiscalInvoice) {
        if(fiscalInvoice.length() > 15){
        this.fiscalInvoice = "#FAC: " + fiscalInvoice.substring(0, 15);
    }
        else{
            this.fiscalInvoice = "#FAC: " + fiscalInvoice;
        }
    }

    public String getFiscalDate() {
        return fiscalDate;
    }

    public void setFiscalDate(String fiscalDate) {
        this.fiscalDate = "FECHA FAC: " + fiscalDate;
    }

    public String getFiscalSerial() {
        return fiscalSerial;
    }

    public void setFiscalSerial(String fiscalSerial) {
        this.fiscalSerial = "#CONTROL/SERIAL IF: " + fiscalSerial;
    }
    private String fiscalSerial;
    
    /** Creates a new instance of UserInfoBasic */
    public CustomerInfo() {
    } 
  
    public String getTaxCustomerID() {
        return taxcustomerid;
    }
    
    public void setTaxCustomerID(String taxcustomerid) {
        this.taxcustomerid = taxcustomerid;
    }
    
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getSocialreason()
    {
        return  this.socialreason;
    }
    
    public void setSocialreason(String value)
    {
       this.socialreason = "RAZON SOCIAL: " + value;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        this.id = "RIF/C.I.: " + pId;
    }

    public Date getCurdate() {
        return curdate;
    }

    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
