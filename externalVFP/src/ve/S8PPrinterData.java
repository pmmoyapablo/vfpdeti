/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve;

import common.*;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import elements.FooterHeader;
/**
 *
 * @author pmoya
 */
public class S8PPrinterData extends  PrinterData implements IPrinterData{
 private String[] listFooterLine;
 
 public String[] getListHeaderLine() {
		return listFooterLine;
	}
	public void setListHeaderLine(String[] pListFooterLine) {
		this.listFooterLine = pListFooterLine;
	}
    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
        FooterHeader fh = new FooterHeader();
            FooterHeader newFH = fh.ListFooterHeaderById(pTicket.getEquipmet().getId())[0];
	   
            String[] arrHL = new String[8];
            
            arrHL[0] = newFH.getP1();
            arrHL[1] = newFH.getP2();
            arrHL[2] = newFH.getP3();
            arrHL[3] = newFH.getP4();
            arrHL[4] = newFH.getP5();
            arrHL[5] = newFH.getP6();
            arrHL[6] = newFH.getP7();
            arrHL[7] = newFH.getP8();

           this.setListHeaderLine(arrHL);

             String trama = "S8P";
        
              int j = 0;
		while (j < this.listFooterLine.length) {
			trama = trama + this.listFooterLine[j] + (char) 0x000A;
			++j;
		}
       
              this._myBytes = trama.getBytes();
    }
    
}
