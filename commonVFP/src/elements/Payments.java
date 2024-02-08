package elements;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import contable.PaymentsModel.SalesLine;
import contable.PaymentsModel.PaymentsLine;
import util.HibernateUtils;

@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Payments implements Serializable {
	@Column
	@Id
	private String id;
//	@JoinColumn(name = "receipt_id")
//	@ManyToOne
        @Column
	private String receipt;
	@Column
	private int payment;
	@Column
	private double total;
	@Column
	private String transId;
	@Lob @Column
	SerialBlob returnMsg;
	public Payments() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Payments(String id, String receipt, int payment, double total,
			String transId, SerialBlob returnMsg) {
		super();
		this.id = id;
		this.receipt = receipt;
		this.payment = payment;
		this.total = total;
		this.transId = transId;
		this.returnMsg = returnMsg;
	}

		
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getReceipt() {
		return receipt;
	}


	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}


	public int getPayment() {
		return payment;
	}


	public void setPayment(int payment) {
		this.payment = payment;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public String getTransId() {
		return transId;
	}


	public void setTransId(String transId) {
		this.transId = transId;
	}


	public SerialBlob getReturnMsg() {
		return returnMsg;
	}


	public void setReturnMsg(SerialBlob returnMsg) {
		this.returnMsg = returnMsg;
	}


	public void AddPayment(String idP, String rec, int paym, double total ,String t_id,SerialBlob retMsg) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Payments pm = new Payments();
			pm.setId(idP);
			pm.setReceipt(rec);
			pm.setPayment(paym);
			pm.setTotal(total);
			pm.setTransId(t_id);
			pm.setReturnMsg(retMsg);
			session.save(pm);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public void UpdatePayments(Payments paym) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(paym);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public Payments[] ListPayments() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Payments> pay = session.createCriteria(Payments.class).list();
			return pay.toArray(new Payments[pay.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	public void DeletePayments(Payments paym) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		session.beginTransaction();// beginning of the transaction
		session.delete(paym);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session

	}
	public ResultQueries.TotalPayment[] SumPayments(String mon) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"Select p.payment as cantidad, SUM(p.total) as total " 
        			+ "FROM Payments p "
        			+ "INNER JOIN Receipts r "
        			+ "ON r.id = p.receipt "
        			+ "WHERE r.money = '" + mon + "'"
            		+ "GROUP BY p.payment");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.TotalPayment> result = new ArrayList<ResultQueries.TotalPayment>();
			for (Object[] obj : elist) {
				//BigInteger n = (BigInteger) obj[0];
				result.add(new ResultQueries.TotalPayment((Integer)obj[0], (Double)obj[1]));
			}
			
			return result.toArray(new ResultQueries.TotalPayment[0]);
		} finally {
			//session.close();
		}

	}
	public ResultQueries.TicketLinesCountSumPrice[] ticketlincountsumprice(String mon , int equipId) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"Select COUNT(DISTINCT t.id_ticket) as countTicket, SUM(tl.UNITS * tl.PRICE) as sumunitprice, SUM(tl.UNITS * tl.PRICE * (1 + ta.RATE)) as sumtotal  " 
        			+ "FROM RECEIPTS r, TICKETS t, TICKETLINES tl, TAXES ta "
        			+ "where r.id = t.id_ticket "
        			+ "and t.id_ticket = tl.ticket "
        			+ "and tl.taxid = ta.id "
                                + "and ta.equipment_id = " + equipId + " "
        			+ "and r.money = '" + mon + "'");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.TicketLinesCountSumPrice> result = new ArrayList<ResultQueries.TicketLinesCountSumPrice>();
			for (Object[] obj : elist) {
				//BigInteger n = (BigInteger) obj[0];
                                if( obj[1] == null || obj[2] == null )
                                { 
                                  obj[1] = 0.00; 
                                  obj[2] = 0.00;
                                }
				result.add(new ResultQueries.TicketLinesCountSumPrice((Integer)obj[0], (Double)obj[1], (Double)obj[2]));
			}
			
			return result.toArray(new ResultQueries.TicketLinesCountSumPrice[0]);
		} finally {
			session.close();
		}

	}	
	public ResultQueries.TicketLinesNameSumPrice[] ticketlinnamesumprice(String mon, int equipId) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"Select ta.name as name, SUM(tl.UNITS * tl.PRICE) as sumunitprice, SUM(tl.UNITS * tl.PRICE * (1 + ta.RATE)) as sumtotal  " 
        			+ "FROM RECEIPTS r, TICKETS t, TICKETLINES tl, TAXES ta "
        			+ "where r.id = t.id_ticket "
        			+ "and t.id_ticket = tl.ticket "
        			+ "and tl.taxid = ta.id "
                                + "and ta.equipment_id = " + equipId + " "
        			+ "and r.money = '" + mon + "'"
                                + " GROUP BY  ta.name"   //TAXES.ID, 
                    );
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.TicketLinesNameSumPrice> result = new ArrayList<ResultQueries.TicketLinesNameSumPrice>();
			for (Object[] obj : elist) {
                         //Object[] obj = new Object[3];
     
//                         obj[0] = "TasaX"; //new SalesLine("Tasa X" , 0.00, 0.00);
//                         obj[1] = 0.00; //new SalesLine("Tasa X" , 0.00, 0.00);
//                         obj[2] = 0.00; //new SalesLine("Tasa X" , 0.00, 0.00);
                         
				result.add(new ResultQueries.TicketLinesNameSumPrice((String)obj[0], (Double)obj[1], (Double)obj[2]));
			}
			return result.toArray(new ResultQueries.TicketLinesNameSumPrice[0]);
		} finally {
			session.close();
		}

	}	
	public ResultQueries.PaymentbyReceipt[] PaymentbyReceipt(String rec) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.getCurrentSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"SELECT PAYMENT as payment, TOTAL as tot, TRANSID as tid FROM PAYMENTS WHERE RECEIPT = '" + rec + "'");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.PaymentbyReceipt> result = new ArrayList<ResultQueries.PaymentbyReceipt>();
			for (Object[] obj : elist) {
				result.add(new ResultQueries.PaymentbyReceipt((Integer)obj[0], (Double)obj[1], (String)obj[2]));
			}
			return result.toArray(new ResultQueries.PaymentbyReceipt[0]);
		} finally {
			//session.close();
		}

	}	
	 
}
