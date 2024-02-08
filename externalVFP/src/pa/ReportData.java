/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pa;

import cw.*;
import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;

/**
 *
 * @author pmoya
 */
public class ReportData extends PrinterData implements IPrinterData{

    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
