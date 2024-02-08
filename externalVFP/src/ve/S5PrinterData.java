package ve;

import common.DeviceException;
import common.IPrinterData;
import common.PrinterData;
import data.Formats;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;

public class S5PrinterData extends  PrinterData implements IPrinterData {
    private String RIF;
    private String serial;
    private int numberMemoryAudit;
    private double capacityTotalMemoryAudit;
    private double disponyCapacityMemoryAudit;
    private int numberDocumentRegisters;
    
	public String getRIF() {
		return RIF;
	}
	public void setRIF(String rIF) {
		RIF = rIF;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public int getNumberMemoryAudit() {
		return numberMemoryAudit;
	}
	public void setNumberMemoryAudit(int numberMemoryAudit) {
		this.numberMemoryAudit = numberMemoryAudit;
	}
	public double getCapacityTotalMemoryAudit() {
		return capacityTotalMemoryAudit;
	}
	public void setCapacityTotalMemoryAudit(double capacityTotalMemoryAudit) {
		this.capacityTotalMemoryAudit = capacityTotalMemoryAudit;
	}
	public double getDisponyCapacityMemoryAudit() {
		return disponyCapacityMemoryAudit;
	}
	public void setDisponyCapacityMemoryAudit(double disponyCapacityMemoryAudit) {
		this.disponyCapacityMemoryAudit = disponyCapacityMemoryAudit;
	}
	public int getNumberDocumentRegisters() {
		return numberDocumentRegisters;
	}
	public void setNumberDocumentRegisters(int numberDocumentRegisters) {
		this.numberDocumentRegisters = numberDocumentRegisters;
	}
	@Override
	public void loadData(DataLogicSales arg0, TicketInfo pTicket,
			PaymentsModel arg2) {
		
		this.setRIF(pTicket.printIdFiscal());
		this.setSerial(pTicket.printNumberRegister());
		this.setNumberMemoryAudit(0);
		this.setCapacityTotalMemoryAudit(0);
		this.setDisponyCapacityMemoryAudit(0);
		this.setNumberDocumentRegisters(0);
		
	       String trama = "S5";
	        
	        trama = trama + getRIF();      
	        trama = trama + (char)0x000A;
	        trama = trama + getSerial(); 
	        trama = trama + (char)0x000A;
	        
	        String numeroMemAud = String.valueOf(getNumberMemoryAudit());
	        
	        while(numeroMemAud.length() < 4)
	        {
	            numeroMemAud = "0" + numeroMemAud;
	        }
	        
	        trama = trama + numeroMemAud;
	        
	        trama = trama + (char)0x000A;
	        
	         String capacidad = String.valueOf(getCapacityTotalMemoryAudit());
                 capacidad = capacidad.replace(".", "");
	        
	        while(capacidad.length() < 4)
	        {
	           capacidad = "0" + capacidad;
	        }
	        
	         capacidad =  capacidad.substring(0, 4);
	        
	        trama = trama + capacidad;
	        
	        trama = trama + (char)0x000A;
	        
	         String disponibilidad = String.valueOf(getDisponyCapacityMemoryAudit());
                 disponibilidad = disponibilidad.replace(".", "");
	        
	        while(disponibilidad.length() < 4)
	        {
	           disponibilidad = "0" + disponibilidad;
	        }
	        
	         disponibilidad =  disponibilidad.substring(0, 4);
	        
	        trama = trama + disponibilidad;
	        
	        trama = trama + (char)0x000A;
	        
	         String nroDocs = String.valueOf(getNumberDocumentRegisters());
	        
	        while(nroDocs.length() < 6)
	        {
	          nroDocs = "0" + nroDocs;
	        }
	        
	        trama = trama + nroDocs;
	        
	               
	        this._myBytes = trama.getBytes();

	}

}
