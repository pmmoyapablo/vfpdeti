package cw;

import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;

public class S5PrinterData extends  PrinterData implements IPrinterData {
    private String RNC;
    private String serial;
    private int EJnumber;
    private int EJcapacity;
    private int freeSpaceEJ;
    private int quantityDocuments;
    
	public String getRNC() {
		return RNC;
	}

	public void setRNC(String rNC) {
		RNC = rNC;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public int getEJnumber() {
		return EJnumber;
	}

	public void setEJnumber(int eJnumber) {
		EJnumber = eJnumber;
	}

	public int getEJcapacity() {
		return EJcapacity;
	}

	public void setEJcapacity(int eJcapacity) {
		EJcapacity = eJcapacity;
	}

	public int getFreeSpaceEJ() {
		return freeSpaceEJ;
	}

	public void setFreeSpaceEJ(int freeSpaceEJ) {
		this.freeSpaceEJ = freeSpaceEJ;
	}

	public int getQuantityDocuments() {
		return quantityDocuments;
	}

	public void setQuantityDocuments(int quantityDocuments) {
		this.quantityDocuments = quantityDocuments;
	}

	@Override
	public void loadData(DataLogicSales pData, TicketInfo pTicket,
			PaymentsModel pCashModel) {
		this.setRNC(pTicket.getEquipmet().getId_fiscal());
		this.setSerial(pTicket.getEquipmet().getMachine_register());
		this.setEJnumber(0);
		this.setEJcapacity(0);
		this.setFreeSpaceEJ(0);
		this.setQuantityDocuments(0);
		
	       String trama = "S5";
	        
	        trama = trama + getRNC();      
	        trama = trama + (char)0x000A;
	        trama = trama + getSerial(); 
	        trama = trama + (char)0x000A;
	        
	        String ejnumber = String.valueOf(getEJnumber());
	        
	        while(ejnumber.length() < 4)
	        {
	        	ejnumber = "0" + ejnumber;
	        }
	        
	        trama = trama + ejnumber;
	        
	        trama = trama + (char)0x000A;
	        
	         String capacidad = String.valueOf(getEJcapacity());
                 capacidad = capacidad.replace(".", "");
	        
	        while(capacidad.length() < 4)
	        {
	           capacidad = "0" + capacidad;
	        }
	        
	         capacidad =  capacidad.substring(0, 4);
	        
	        trama = trama + capacidad;
	        
	        trama = trama + (char)0x000A;
	        
	         String disponibilidad = String.valueOf(getFreeSpaceEJ());
                 disponibilidad = disponibilidad.replace(".", "");
	        
	        while(disponibilidad.length() < 4)
	        {
	           disponibilidad = "0" + disponibilidad;
	        }
	        
	         disponibilidad =  disponibilidad.substring(0, 4);
	        
	        trama = trama + disponibilidad;
	        
	        trama = trama + (char)0x000A;
	        
	         String nroDocs = String.valueOf(getQuantityDocuments());
	        
	        while(nroDocs.length() < 6)
	        {
	          nroDocs = "0" + nroDocs;
	        }
	        
	        trama = trama + nroDocs;
	        
	               
	        this._myBytes = trama.getBytes();
		
	}

}
