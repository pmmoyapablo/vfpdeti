package elements;

import java.util.Date;

import javax.persistence.Column;

import contable.PaymentsModel.PaymentsLine;
import contable.PaymentsModel.SalesLine;

public class ResultQueries {
	public static class Count{
		private int cantidadClosedCas;
		public Count() {
			super();
		}
		public Count(int cantidadClosedCas) {
			super();
			this.cantidadClosedCas = cantidadClosedCas;
		}
		public int getCantidadClosedCas() {
			return cantidadClosedCas;
		}
		public void setCantidadClosedCas(int cantidadClosedCas) {
			this.cantidadClosedCas = cantidadClosedCas;
		}
	}
	public static class MaxDate{
		private Date maxdate;
		public MaxDate() {
			super();
		}
		public MaxDate(Date maxdate) {
			super();
			this.maxdate = maxdate;
		}
		public Date getMaxdate() {
			return maxdate;
		}
		public void setMaxdate(Date maxdate) {
			this.maxdate = maxdate;
		}	
	}
	public static class DataendMoney{
		private Date dataend;
		private String money;
		public DataendMoney() {
			super();
		}
		public DataendMoney(Date dataend, String money) {
			super();
			this.dataend = dataend;
			this.money = money;
		}
		public Date getDataend() {
			return dataend;
		}
		public void setDataend(Date dataend) {
			this.dataend = dataend;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}	
	}
	public static class TotalPayment{
		private Integer pago;
		private Double totalpago;
		public TotalPayment() {
			super();
		}
		public TotalPayment(Integer pago, Double totalpago) {
			super();
			this.pago = pago;
			this.totalpago = totalpago;
		}
		public Integer getPago() {
			return pago;
		}
		public void setPago(Integer pago) {
			this.pago = pago;
		}
		public Double getTotalpago() {
			return totalpago;
		}
		public void setTotalpago(Double totalpago) {
			this.totalpago = totalpago;
		}
	}
	public static class TicketLinesCountSumPrice{
		private int countTicket;
		private double sumunitprice;
		private double sumtotal;
		public TicketLinesCountSumPrice() {
			super();
		}
		public TicketLinesCountSumPrice(int countTicket, double sumunitprice,
				double sumtotal) {
			super();
			this.countTicket = countTicket;
			this.sumunitprice = sumunitprice;
			this.sumtotal = sumtotal;
		}
		public int getCountTicket() {
			return countTicket;
		}
		public void setCountTicket(int countTicket) {
			this.countTicket = countTicket;
		}
		public double getSumunitprice() {
			return sumunitprice;
		}
		public void setSumunitprice(double sumunitprice) {
			this.sumunitprice = sumunitprice;
		}
		public double getSumtotal() {
			return sumtotal;
		}
		public void setSumtotal(double sumtotal) {
			this.sumtotal = sumtotal;
		}	
	}
	public static class TicketLinesNameSumPrice{
		private String name;
		private Double sumunitprice;
		private Double sumtotal;
		public TicketLinesNameSumPrice() {
			super();
		}

		public TicketLinesNameSumPrice(String name, Double sumunitprice,
				Double sumtotal) {
			super();
			this.name = name;
			this.sumunitprice = sumunitprice;
			this.sumtotal = sumtotal;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Double getSumunitprice() {
			return sumunitprice;
		}
		public void setSumunitprice(Double sumunitprice) {
			this.sumunitprice = sumunitprice;
		}
		public Double getSumtotal() {
			return sumtotal;
		}
		public void setSumtotal(Double sumtotal) {
			this.sumtotal = sumtotal;
		}
	}
	public static class ResultadoConsulta{
		private int cantidad;
		private double total;
		public ResultadoConsulta() {
			super();
		}
		public ResultadoConsulta(int cantidad, double total) {
			super();
			this.cantidad = cantidad;
			this.total = total;
		}
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
	}
	public static class Loadticket{
		private String idticket;
		private int tipoticket;
		private int ticketid;
		private Date datenew;
		private String money;
		private String attribut;
		public Loadticket(String idticket, int tipoticket, int ticketid,
				Date datenew, String money, String attribut) {
			super();
			this.idticket = idticket;
			this.tipoticket = tipoticket;
			this.ticketid = ticketid;
			this.datenew = datenew;
			this.money = money;
			this.attribut = attribut;
		}
		public Loadticket() {
			super();
		}
		public String getIdticket() {
			return idticket;
		}
		public void setIdticket(String idticket) {
			this.idticket = idticket;
		}
		public int getTipoticket() {
			return tipoticket;
		}
		public void setTipoticket(int tipoticket) {
			this.tipoticket = tipoticket;
		}
		public int getTicketid() {
			return ticketid;
		}
		public void setTicketid(int ticketid) {
			this.ticketid = ticketid;
		}
		public Date getDatenew() {
			return datenew;
		}
		public void setDatenew(Date datenew) {
			this.datenew = datenew;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getAttribut() {
			return attribut;
		}
		public void setAttribut(String attribut) {
			this.attribut = attribut;
		}
	}
	public static class MoneyClosedCash{
		private String money;

		public MoneyClosedCash() {
			super();
		}
		public MoneyClosedCash(String money) {
			super();
			this.money = money;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
	}
	public static class PaymentbyReceipt{
		private int payment;
		private double tot;
		private String tid;
		public PaymentbyReceipt(int payment, double tot, String tid) {
			super();
			this.payment = payment;
			this.tot = tot;
			this.tid = tid;
		}
		public PaymentbyReceipt() {
			super();
		}
		public int getPayment() {
			return payment;
		}
		public void setPayment(int payment) {
			this.payment = payment;
		}
		public double getTot() {
			return tot;
		}
		public void setTot(double tot) {
			this.tot = tot;
		}
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
	}
	public static class TicketLinesTaxeInfo{
		private String ticket;
		private int line;
		private String prod;
		private String attsetinsta_id;
		private double units;
		private double price;
		private String id;
		private String name;
		private String category;
		private String custcat;
		private String parentid;
		private double rate;
		private int ratecascade;
		private int rateorder;
		private String attrib;
		public TicketLinesTaxeInfo() {
			super();
		}
		public TicketLinesTaxeInfo(String ticket, int line, String prod,
				String attsetinsta_id, double units, double price, String id,
				String name, String category, String custcat, String parentid,
				double rate, int ratecascade, int rateorder, String attrib) {
			super();
			this.ticket = ticket;
			this.line = line;
			this.prod = prod;
			this.attsetinsta_id = attsetinsta_id;
			this.units = units;
			this.price = price;
			this.id = id;
			this.name = name;
			this.category = category;
			this.custcat = custcat;
			this.parentid = parentid;
			this.rate = rate;
			this.ratecascade = ratecascade;
			this.rateorder = rateorder;
			this.attrib = attrib;
		}
		public String getTicket() {
			return ticket;
		}
		public void setTicket(String ticket) {
			this.ticket = ticket;
		}
		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}
		public String getProd() {
			return prod;
		}
		public void setProd(String prod) {
			this.prod = prod;
		}
		public String getAttsetinsta_id() {
			return attsetinsta_id;
		}
		public void setAttsetinsta_id(String attsetinsta_id) {
			this.attsetinsta_id = attsetinsta_id;
		}
		public double getUnits() {
			return units;
		}
		public void setUnits(double units) {
			this.units = units;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getCustcat() {
			return custcat;
		}
		public void setCustcat(String custcat) {
			this.custcat = custcat;
		}
		public String getParentid() {
			return parentid;
		}
		public void setParentid(String parentid) {
			this.parentid = parentid;
		}
		public double getRate() {
			return rate;
		}
		public void setRate(double rate) {
			this.rate = rate;
		}
		public int getRatecascade() {
			return ratecascade;
		}
		public void setRatecascade(int ratecascade) {
			this.ratecascade = ratecascade;
		}
		public int getRateorder() {
			return rateorder;
		}
		public void setRateorder(int rateorder) {
			this.rateorder = rateorder;
		}
		public String getAttrib() {
			return attrib;
		}
		public void setAttrib(String attrib) {
			this.attrib = attrib;
		}
	}
}
