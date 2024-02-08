/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import contable.*;

/**
 *
 * @author pmoya
 */
public interface IPrinterData {
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel);
    public byte[] getDataLoader() throws DeviceException;
    public void setMode(char pMode);
    public void setNumberZ(int pNumberZ);
}
