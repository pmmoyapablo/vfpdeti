package pa;
import common.DeviceException;
import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import elements.MeanPayment;
import java.util.List;

public class S4PrinterData extends  PrinterData implements IPrinterData {
	private double[] listCumulaMountsMeansPayments;
	
	public double[] getListCumulaMountsMeansPayments() {
		return listCumulaMountsMeansPayments;
	}
	public void setListCumulaMountsMeansPayments(
			double[] listCumulaMountsMeansPayments) {
		this.listCumulaMountsMeansPayments = listCumulaMountsMeansPayments;
	}

	@Override
	public void loadData(DataLogicSales arg0, TicketInfo arg1,
			PaymentsModel arg2) {
	    double[] arr= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	        MeanPayment mp = new MeanPayment();
            MeanPayment[] listMeansPay = mp.mediaList(arg1.getEquipmet().getId());
            
             try { //Refrescando las cuentas de la caja
             arg2 = PaymentsModel.loadInstance(arg2.getCountReport(), arg1.getEquipmet().getId());
            } catch (DeviceException ex) {
                 //ex.printStackTrace();
            }
            List<PaymentsModel.PaymentsLine> listPaysAcum = arg2.getPaymentLines();    
            
            if(listMeansPay != null)
            {
                if(listMeansPay.length > 0)
                { 
                    arr = new double[listMeansPay.length];
                  int y = 0;
                  while(y < arr.length)
                  {
                      for(PaymentsModel.PaymentsLine pl : listPaysAcum)
                      {         
                               if(Integer.valueOf(pl.getType()) == listMeansPay[y].getId_mean())
                               {
                                    arr[y] = pl.getValue();
                               }
                      }
                      ++y;
                  }
                }
            
            }
            
		this.setListCumulaMountsMeansPayments(arr);
                String trama = "S4";
        
        int j = 0;
        while(j < this.listCumulaMountsMeansPayments.length)
        {
             String pagoTotal = String.valueOf(this.listCumulaMountsMeansPayments[j]);
             pagoTotal = StringDoubleFormat(pagoTotal);
             pagoTotal = StringFormatXero(pagoTotal, 10);

                 trama = trama + pagoTotal;
                 trama = trama + (char)0x000A; 
              ++j;
        }
        
        this._myBytes = trama.getBytes();

	}
}
