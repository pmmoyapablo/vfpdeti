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

import common.DeviceException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import data.Formats;

/**
 *
 * @author adrianromero
 */
public class TaxInfo implements Serializable{
    
    private static final long serialVersionUID = -2705212098856473043L;
    private String id;
    private String name;
    private String taxcategoryid;
    private String taxcustcategoryid;
    private String parentid;
    
    private double rate;
    private boolean cascade;
    private Integer order;

    
    /** Creates new TaxInfo */
    public TaxInfo(String id, String name, String taxcategoryid, String taxcustcategoryid, String parentid, double rate, boolean cascade, Integer order) {
        this.id = id;
        this.name = name;
        this.taxcategoryid = taxcategoryid;
        this.taxcustcategoryid = taxcustcategoryid;
        this.parentid = parentid;
        
        this.rate = rate;
        this.cascade = cascade;
        this.order = order;
    }
    
     public TaxInfo(String id, String pName ,double rate, int modo)
     {
          this.id = id;
          this.rate = rate;
          this.name = pName;
          
          if(modo == 1)
                 this.cascade = false;
          else
                 this.cascade = true;
     }
    
    public Object getKey() {
        return id;
    }
    
    public void setID(String value) {
        id = value;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String value) {
        name = value;
    }

    public String getTaxCategoryID() {
        return taxcategoryid;
    }
    
    public void setTaxCategoryID(String value) {
        taxcategoryid = value;
    }

    public String getTaxCustCategoryID() {
        return taxcustcategoryid;
    }
    
    public void setTaxCustCategoryID(String value) {
        taxcustcategoryid = value;
    }    

    public String getParentID() {
        return parentid;
    }
    
    public void setParentID(String value) {
        parentid = value;
    }
    
    public double getRate() {
        return rate;
    }
    
    public String printRate() {
        return Formats.PERCENT.formatValue(rate);
    }
    
    public void setRate(double value) {
        rate = value;
    }

    public boolean isCascade() {
        return cascade;
    }
    
    public void setCascade(boolean value) {
        cascade = value;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public Integer getApplicationOrder() {
        return order == null ? Integer.MAX_VALUE : order.intValue();
    }    
    
    public void setOrder(Integer value) {
        order = value;
    }
    
    public TaxInfo[] listarTasas(int Equipo_id)
    {
        TaxInfo[] listTasas = null;
        
        // try {
             
            List lista = null; //this.m_dlsyt.listTasas(Equipo_id);
             listTasas  = new TaxInfo[lista.size()];
           
             int j = 0;
            for(Object obj  : lista)
             { 
                 listTasas[j] = (TaxInfo) obj;
                 
                 ++j;
             }
                
//             } catch (DeviceException ex) 
//             { Logger.getLogger(TaxInfo.class.getName()).log(Level.SEVERE, null, ex);}
        
        return listTasas;
    }
    
    public void Editar_Tasa(String idTasa, double newrate, int modo, int equipo_id)
    {
      //  try {
                Object[] data = new Object[4];
		data[0] = newrate;
                data[1] = modo;
                data[2] = idTasa;
                data[3] = equipo_id;
                //this.m_dlsyt.execChangeTasas(data);
                this.id = idTasa;
		this.rate = newrate;
                if(modo == 1)
                { this.cascade = false;}
                else
                {this.cascade = true;}
//            }catch (DeviceException ex) 
//            { Logger.getLogger(TaxInfo.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    @Override
    public String toString(){
        return name;
    }
}
