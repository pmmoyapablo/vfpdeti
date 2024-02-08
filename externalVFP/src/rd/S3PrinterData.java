package rd;

import common.IPrinterData;
import common.PrinterData;
import contable.DataLogicSales;
import contable.PaymentsModel;
import contable.TicketInfo;
import elements.*;

public class S3PrinterData extends PrinterData implements IPrinterData {
	private int typeTax1;
	private double tax1;
	private int typeTax2;
	private double tax2;
	private int typeTax3;
	private double tax3;
	private int typeTax4;
	private double tax4;
	private int typeTax5;
	private double tax5;
	private int[] systemFlags0to63;

	public int getTypeTax1() {
		return typeTax1;
	}

	public void setTypeTax1(int typeTax1) {
		this.typeTax1 = typeTax1;
	}

	public double getTax1() {
		return tax1;
	}

	public void setTax1(double tax1) {
		this.tax1 = tax1;
	}

	public int getTypeTax2() {
		return typeTax2;
	}

	public void setTypeTax2(int typeTax2) {
		this.typeTax2 = typeTax2;
	}

	public double getTax2() {
		return tax2;
	}

	public void setTax2(double tax2) {
		this.tax2 = tax2;
	}

	public int getTypeTax3() {
		return typeTax3;
	}

	public void setTypeTax3(int typeTax3) {
		this.typeTax3 = typeTax3;
	}

	public double getTax3() {
		return tax3;
	}

	public void setTax3(double tax3) {
		this.tax3 = tax3;
	}

	public int getTypeTax4() {
		return typeTax4;
	}

	public void setTypeTax4(int typeTax4) {
		this.typeTax4 = typeTax4;
	}

	public double getTax4() {
		return tax4;
	}

	public void setTax4(double tax4) {
		this.tax4 = tax4;
	}

	public int getTypeTax5() {
		return typeTax5;
	}

	public void setTypeTax5(int typeTax5) {
		this.typeTax5 = typeTax5;
	}

	public double getTax5() {
		return tax5;
	}

	public void setTax5(double tax5) {
		this.tax5 = tax5;
	}

	public int[] getSystemFlags0to63() {
		return systemFlags0to63;
	}

	public void setSystemFlags0to63(int[] systemFlags0to63) {
		this.systemFlags0to63 = systemFlags0to63;
	}

	@Override
	public void loadData(DataLogicSales arg0, TicketInfo arg1,
			PaymentsModel arg2) {
		int[] valor = new int[64];
		int[] myIntArray = { 0 };
                Taxes tax = new Taxes();
                Taxes[] listTaxes = tax.listTaxes(arg1.getEquipmet().getId());
                if(listTaxes != null)
                {
                    if(listTaxes.length > 0)
                    {
                        this.setTypeTax1(listTaxes[1].getRateCascade());
                        this.setTax1(listTaxes[1].getRate());
                        this.setTypeTax2(listTaxes[2].getRateCascade());
                        this.setTax2(listTaxes[2].getRate());
                        this.setTypeTax3(listTaxes[3].getRateCascade());
                        this.setTax3(listTaxes[3].getRate());
                        this.setTypeTax4(listTaxes[4].getRateCascade());
                        this.setTax4(listTaxes[4].getRate());
                        this.setTypeTax5(listTaxes[5].getRateCascade());
                        this.setTax5(listTaxes[5].getRate());
                    }
                }
                Flags flags = new Flags();
                Flags[] flagses = flags.listFlags(arg1.getEquipmet().getId());
                myIntArray = new int[flagses.length];
                int x = 0;
                while(x < myIntArray.length)
                {
                    myIntArray[x] = flagses[x].getValue();
                    ++x;
                }
                
		this.setSystemFlags0to63(myIntArray);
                
		String trama = "S3";

		trama = trama + String.valueOf(this.getTypeTax1());

		String valorTax = StringDoubleFormat(String.valueOf(this.getTax1()));
                valorTax = StringFormatXero(valorTax, 4);

		trama = trama + valorTax + (char) 0x000A;

		trama = trama + String.valueOf(this.getTypeTax2());

	        valorTax = StringDoubleFormat(String.valueOf(this.getTax2()));
                valorTax = StringFormatXero(valorTax, 4);

		trama = trama + valorTax + (char) 0x000A;

		trama = trama + String.valueOf(this.getTypeTax3());

	        valorTax = StringDoubleFormat(String.valueOf(this.getTax3()));
                valorTax = StringFormatXero(valorTax, 4);

		trama = trama + valorTax + (char) 0x000A;

		trama = trama + String.valueOf(this.getTypeTax4());

		valorTax = StringDoubleFormat(String.valueOf(this.getTax4()));
                valorTax = StringFormatXero(valorTax, 4);

		trama = trama + valorTax + (char) 0x000A;

		trama = trama + String.valueOf(this.getTypeTax5());

		valorTax = StringDoubleFormat(String.valueOf(this.getTax5()));
                valorTax = StringFormatXero(valorTax, 4);

		trama = trama + valorTax + (char) 0x000A;

		int y = 0;
		while (y < this.getSystemFlags0to63().length) {
			String flg = "";
			if (y < this.getSystemFlags0to63().length) {
				flg = String.valueOf(this.getSystemFlags0to63()[y]);
				flg = StringFormatXero(flg, 2);
			} else {
				flg = "00";
			}

			trama = trama + flg;

			++y;
		}

		trama = trama + (char) 0x000A;

		this._myBytes = trama.getBytes();

	}

}
