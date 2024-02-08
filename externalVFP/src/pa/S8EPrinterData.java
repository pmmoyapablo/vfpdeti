package pa;

import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import elements.FooterHeader;

public class S8EPrinterData extends  PrinterData implements IPrinterData {
	private String[] listHeaderLine;
	
	public String[] getListHeaderLine() {
		return listHeaderLine;
	}
	public void setListHeaderLine(String[] pListHeaderLine) {
		this.listHeaderLine = pListHeaderLine;
	}

	@Override
	public void loadData(DataLogicSales arg0, TicketInfo arg1,
			PaymentsModel arg2) {
            FooterHeader fh = new FooterHeader();
            FooterHeader newFH = fh.ListFooterHeaderById(arg1.getEquipmet().getId())[0];
	   
            String[] arrHL = new String[8];
            
            arrHL[0] = newFH.getE1();
            arrHL[1] = newFH.getE2();
            arrHL[2] = newFH.getE3();
            arrHL[3] = newFH.getE4();
            arrHL[4] = newFH.getE5();
            arrHL[5] = newFH.getE6();
            arrHL[6] = newFH.getE7();
            arrHL[7] = newFH.getE8();

           this.setListHeaderLine(arrHL);

             String trama = "S8E";
        
              int j = 0;
		while (j < this.listHeaderLine.length) {
			trama = trama + this.listHeaderLine[j] + (char) 0x000A;
			++j;
		}
       
              this._myBytes = trama.getBytes();

	}

}
