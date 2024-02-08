//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2008 Openbravo, S.L.
//    http://sourceforge.net/projects/openbravopos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package contable;

import java.util.*;

import javax.swing.table.AbstractTableModel;

import data.Formats;
import elements.ClosedCash;
import elements.Payments;
import elements.Receipts;
import elements.ResultQueries;
import common.DeviceException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import util.Utilities;

/**
 *
 * @author adrianromero
 */
public class PaymentsModel {

	private String m_sHost;
	private Date m_dDateStart;
	private Date m_dDateEnd;
        private String m_IndexMoneyCash;

	private Integer m_CountReport;
	private Integer m_iPayments;
	private Double m_dPaymentsTotal;
	private java.util.List<PaymentsLine> m_lpayments;

	private final static String[] PAYMENTHEADERS = { "Label.Payment",
			"label.totalcash" };

	private Integer m_iSales;
	private Double m_dSalesSubtotal;
	private Double m_dSalesTotal;

	private String m_aSales;
	private Double m_aSalesSubtotal;
	private Double m_aSalesTotal;
	private java.util.List<SalesLine> m_lsales;

	private final static String[] SALEHEADERS = { "label.taxcash",
			"label.subtotalcash", "label.totalcash" };
        static NumberFormat formatter = new DecimalFormat("#0.00"); 

	private PaymentsModel() {
	}

	public static PaymentsModel emptyInstance() {

		PaymentsModel p = new PaymentsModel();

		p.m_iPayments = new Integer(0);
		p.m_CountReport = new Integer(0);
		p.m_dPaymentsTotal = new Double(0.0);
		p.m_lpayments = new ArrayList<PaymentsLine>();
                p.m_IndexMoneyCash = null;

		p.m_iSales = new Integer(0);
		p.m_dSalesSubtotal = new Double(0.0);
		p.m_dSalesTotal = new Double(0.0);
		p.m_aSales = new String();
		p.m_aSalesSubtotal = new Double(0.0);
		p.m_aSalesTotal = new Double(0.0);
		p.m_lsales = new ArrayList<SalesLine>();

		return p;
	}

	public static PaymentsModel loadInstance() throws DeviceException {

		PaymentsModel p = new PaymentsModel();

		ClosedCash c = new ClosedCash();
		// se le asigna como paramatro hostsequence en 1
		ResultQueries.Count rqc[] = c.countClosedCash(0);
		// Contador
		int idReport = rqc[0].getCantidadClosedCas();

		// Propiedades globales
		p.m_sHost = c.getHost();
		p.m_dDateStart = c.getDatestart();
                p.m_IndexMoneyCash = c.getMoney();
		p.m_dDateEnd = null;
		p.m_CountReport = idReport;

		ResultQueries.MaxDate[] mdate = c.maxDate(0);
		Date fechaCierre = mdate[0].getMaxdate();

		if (fechaCierre != null) {
			p.m_dDateEnd = (Date) fechaCierre;
		}

		// Pagos
		Receipts r = new Receipts();
		ResultQueries.ResultadoConsulta[] rec = r.listReceipts(c.getMoney());
		Object[] valtickets = { rec[0].getCantidad(), rec[0].getTotal() };
		if (valtickets == null) {
			p.m_iPayments = new Integer(0);
			p.m_dPaymentsTotal = new Double(0.0);
		} else {
			p.m_iPayments = (Integer) valtickets[0];
			p.m_dPaymentsTotal = (Double) valtickets[1];
		}

		Payments pa = new Payments();
		ResultQueries.TotalPayment[] pay = pa.SumPayments(c.getMoney());
		List l = Arrays.asList(pay);
		l.add(pay[0].getPago());
		l.add(pay[0].getTotalpago());

		if (l == null) {
			p.m_lpayments = new ArrayList();
		} else {
			p.m_lpayments = l;
		}

		// Ventas
		ResultQueries.TicketLinesCountSumPrice[] tlcsm = pa
				.ticketlincountsumprice(c.getMoney(), 0);
		Object[] recsales = { tlcsm[0].getCountTicket(),
				tlcsm[0].getSumunitprice(), tlcsm[0].getSumtotal() };
		if (recsales == null) {
			p.m_iSales = new Integer(0);
			p.m_dSalesSubtotal = new Double(0.0);
			p.m_dSalesTotal = new Double(0.0);
		} else {
			p.m_iSales = (Integer) recsales[0];
			p.m_dSalesSubtotal = (Double) recsales[1];
			p.m_dSalesTotal = (Double) recsales[2];
		}
		ResultQueries.TicketLinesNameSumPrice[] tlnsp = pa
				.ticketlinnamesumprice(c.getMoney(),0);
		List le = Arrays.asList(tlnsp);
		le.add(tlnsp[0].getName());
		le.add(tlnsp[0].getSumunitprice());
		le.add(tlnsp[0].getSumtotal());
		List<SalesLine> asales = le;
		if (asales == null) {
			p.m_lsales = new ArrayList<SalesLine>();
		} else {
			p.m_lsales = asales;
		}

		return p;
	}

	// Sobrecarga 2
	public static PaymentsModel loadInstance(int idZ, int idEquip) throws DeviceException {

		PaymentsModel p = new PaymentsModel();

		p.m_CountReport = idZ;
		ClosedCash c = new ClosedCash();
		ClosedCash c0 = c.getClosedCashBySequence(idZ, idEquip);
                
                if(c0 == null)
                {
                    //Creo una Caja Nueva
                    c.InsertClosedCash(Utilities.StringFormatXero(String.valueOf(idZ) , 4) + "-" + String.valueOf(idEquip), Utilities.getLocalHostName(), idZ, new Date(), null, idEquip);
                    c = c.getClosedCashBySequence(idZ, idEquip);
                }else
                { // Asigno
                   c = c0;
                }
		// Propiedades globales
		p.m_sHost = c.getHost();
		p.m_dDateStart = c.getDatestart();
                p.m_IndexMoneyCash = c.getMoney();
		p.m_dDateEnd = c.getDateend();

	        String moneyCach = c.getMoney();		

		// Pagos
		Receipts r = new Receipts();
		ResultQueries.ResultadoConsulta[] rec = r.listReceipts(moneyCach);
		Object[] valtickets = { rec[0].getCantidad(), rec[0].getTotal() };

		if (valtickets == null) {
			p.m_iPayments = new Integer(0);
			p.m_dPaymentsTotal = new Double(0.0);
		} else {
			p.m_iPayments = (Integer) valtickets[0];
			p.m_dPaymentsTotal = (Double) valtickets[1];
		}
		Payments pa = new Payments();
		ResultQueries.TotalPayment[] pay = pa.SumPayments(moneyCach);
		List<PaymentsLine> l = new ArrayList();
                if(pay.length > 0)
                {
                    int index = 0;
                    while(index < pay.length)
                    {
                     PaymentsLine pl = new PaymentsLine();
                     pl.readValues(String.valueOf(pay[index].getPago()), pay[index].getTotalpago());
		     l.add(pl);
                     ++index;
                    }
                }

	        p.m_lpayments = l;

		// Ventas
		ResultQueries.TicketLinesCountSumPrice[] tlcsm = pa.ticketlincountsumprice(moneyCach, idEquip);
		Object[] recsales = { tlcsm[0].getCountTicket(),
				tlcsm[0].getSumunitprice(), tlcsm[0].getSumtotal() };
		if (recsales == null) {
			p.m_iSales = new Integer(0);
			p.m_dSalesSubtotal = new Double(0.0);
			p.m_dSalesTotal = new Double(0.0);
		} else {
			p.m_iSales = (Integer) recsales[0];
			p.m_dSalesSubtotal = (Double) recsales[1];
			p.m_dSalesTotal = (Double) recsales[2];
		}
            
		ResultQueries.TicketLinesNameSumPrice[] tlnsp = pa.ticketlinnamesumprice(moneyCach, idEquip);
		//List le = Arrays.asList(tlnsp);
//		le.add(tlnsp[0].getName());
//		le.add(tlnsp[0].getSumunitprice());
//		le.add(tlnsp[0].getSumtotal());
		List<SalesLine> asales = new ArrayList<SalesLine>(); //le;
		if (tlnsp == null) {
			p.m_lsales = asales;
		} else { 
                    for(ResultQueries.TicketLinesNameSumPrice obj : tlnsp)
                    {
                        SalesLine sl = new SalesLine(obj.getName(), obj.getSumunitprice(), obj.getSumtotal());
                        asales.add(sl);
                    }
                   
			p.m_lsales = asales;
		}

		return p;
	}

	public int getCountReport() {
		return m_CountReport.intValue();
	}

	public int getPayments() {
		return m_iPayments.intValue();
	}

	public double getTotal() {
		return m_dPaymentsTotal.doubleValue();
	}

	public String getHost() {
		return m_sHost;
	}
        
        public String getMoney() {
		return m_IndexMoneyCash;
	}

	public Date getDateStart() {
		return m_dDateStart;
	}

	public void setDateEnd(Date dValue) {
		m_dDateEnd = dValue;
	}

	public Date getDateEnd() {
		return m_dDateEnd;
	}

	public String printHost() {
		return m_sHost;
	}

	public String printDateStart() {
		return Formats.TIMESTAMP.formatValue(m_dDateStart);
	}

	public String printDateEnd() {
		return Formats.TIMESTAMP.formatValue(m_dDateEnd);
	}

	public String printPayments() {
		return Formats.INT.formatValue(m_iPayments);
	}

	public String printPaymentsTotal() {
		return Formats.CURRENCY.formatValue(m_dPaymentsTotal);
	}

	public List<PaymentsLine> getPaymentLines() {
		return m_lpayments;
	}

	public int getSales() {
		return m_iSales.intValue();
	}

	public String printSales() {
		return Formats.INT.formatValue(m_iSales);
	}

	public String printCountReport() {
		return Utilities.StringFormatXero(Formats.STRING.formatValue(String.valueOf(m_CountReport)),4);
	}

	public String printSalesSubtotal() {
		return Formats.CURRENCY.formatValue(m_dSalesSubtotal);
	}

	public String printSalesTotal() {
		return Formats.CURRENCY.formatValue(m_dSalesTotal);
	}

	public List<SalesLine> getSaleLines() {
		return m_lsales;
	}       

	public AbstractTableModel getPaymentsModel() {
		return new AbstractTableModel() {
			public String getColumnName(int column) {
				return String.valueOf(PAYMENTHEADERS[column]);
			}

			public int getRowCount() {
				return m_lpayments.size();
			}

			public int getColumnCount() {
				return PAYMENTHEADERS.length;
			}

			public Object getValueAt(int row, int column) {
				PaymentsLine l = m_lpayments.get(row);
				switch (column) {
				case 0:
					return l.getType();
				case 1:
					return l.getValue();
				default:
					return null;
				}
			}
		};
	}

	public static class SalesLine {

		private String m_SalesTaxes;
		private Double m_SalesSubtotal;
		private Double m_SalesTotal;
                
                public SalesLine(String taxes, Double subTot, Double Total)
                {
                  m_SalesTaxes = taxes;
                  m_SalesSubtotal = subTot;
                  m_SalesTotal = Total;
                }

		public void readValues() throws DeviceException {
			// m_SalesTaxes = dr.getString(1);
			// m_SalesSubtotal = dr.getDouble(2);
			// m_SalesTotal = dr.getDouble(3);
		}

		public String printTax() {
			return formatter.format(m_SalesTaxes);
		}

		public String printSubtotal() {
			return formatter.format(round(m_SalesSubtotal,2));
		}

		public String printTotal() {
			return formatter.format(round(m_SalesTotal,2));
		}

		public String printTaxtotal() {
			return formatter.format(round((m_SalesTotal - m_SalesSubtotal),2));
		}

		public String getTax() {
			return m_SalesTaxes;
		}

		public Double getSubtotal() {
			return m_SalesSubtotal;
		}

		public Double getTotal() {
			return m_SalesTotal;
		}
	}

	public AbstractTableModel getSalesModel() {
		return new AbstractTableModel() {
			public String getColumnName(int column) {
				return String.valueOf(SALEHEADERS[column]);
			}

			public int getRowCount() {
				return m_lsales.size();
			}

			public int getColumnCount() {
				return SALEHEADERS.length;
			}

			public Object getValueAt(int row, int column) {
				SalesLine l = m_lsales.get(row);
				switch (column) {
				case 0:
					return l.getTax();
				case 1:
					return l.getSubtotal();
				case 2:
					return l.getTotal();
				default:
					return null;
				}
			}
		};
	}

	public static class PaymentsLine {

		private String m_PaymentType;
		private Double m_PaymentValue;

		public void readValues(String pago, Double monto) throws DeviceException {
			 m_PaymentType = pago;
			 m_PaymentValue = monto;
		}

		public String printType() {
			return String.valueOf("Payment Mean " + m_PaymentType);
		}

		public String getType() {
			return m_PaymentType;
		}

		public String printValue() {
			return formatter.format(m_PaymentValue);
		}

		public Double getValue() {
			return m_PaymentValue;
		}
	}
                    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
//        NumberFormat formatter = new DecimalFormat("#0.00");     
//        return Double.valueOf(formatter.format((double) tmp / factor));
        return (double) tmp / factor;        
    }
}