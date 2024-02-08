/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pa;


import common.*;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
/**
 *
 * @author pmoya
 */
public class S7PrinterData extends  PrinterData implements IPrinterData{
        private String MICR;    

        private void setMICR(String pMICR)
        {
            this.MICR = pMICR;
        }

    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
        String trama = "S7";
           
         this.setMICR("???????????????????????????????????????");

          trama = trama + this.MICR;
                
          this._myBytes = trama.getBytes();
    }
    
}
