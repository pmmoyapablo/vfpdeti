/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ve;

import common.*;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
/**
 *
 * @author pmoya
 */
public class SVPrinterData extends  PrinterData implements IPrinterData{

    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
             String trama = "SV" + (char) 0x000A;
             String iniSerial = pTicket.getEquipmet().getMachine_register().substring(0,3);
	     trama = trama + iniSerial + (char) 0x000A;
             trama = trama + "VE" + (char) 0x000A;
       
            this._myBytes = trama.getBytes();
    }
    
}
