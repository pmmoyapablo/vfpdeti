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
public class S6PrinterData extends  PrinterData implements IPrinterData{
    
        private String Bit_Facturacion;
        private String Bit_Slip;
        private String Bit_Validacion;
 

        private void setBitFacturacion(String Bit_Facturacion)
        {
            this.Bit_Facturacion = Bit_Facturacion;
        }

        private void setBitSlip(String Bit_Slip)
        {
            this.Bit_Slip = Bit_Slip;
        }

        private void setBitValidacion(String Bit_Validacion)
        {
            this.Bit_Validacion = Bit_Validacion;
        }
 
    @Override
    public void loadData(DataLogicSales pData, TicketInfo pTicket, PaymentsModel pCashModel) {
           String trama = "S6";
           
           this.setBitFacturacion("0");
           this.setBitSlip("0");
           this.setBitValidacion("0");

           trama = trama + this.Bit_Facturacion + this.Bit_Slip + this.Bit_Validacion;
                
          this._myBytes = trama.getBytes();
    }
    
}
