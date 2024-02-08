/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rd;

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
               String iniSerial = "UNW";
            if (pTicket.getEquipmet().getModel().equals("SRP-350II") || pTicket.getEquipmet().getModel().equals("SRP-350"))
            { iniSerial = "Z1B"; }
            else if (pTicket.getEquipmet().getModel().equals("TALLY-1125"))
            { iniSerial = "Z6A"; }
            else if (pTicket.getEquipmet().getModel().equals("DT-230"))
            { iniSerial = "Z6B"; } 
	     trama = trama + iniSerial + (char) 0x000A;
             trama = trama + "DO" + (char) 0x000A;
       
            this._myBytes = trama.getBytes();
    }
    
}