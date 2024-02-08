package elements;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtils;
@Table
@Entity
public class Tickets implements Serializable {
	@Column
	@Id
	private String id_ticket;
	@Column
	private int ticketType;
	@Column
	private int ticketId;
	@Column
	private String person;
	@Column
	private String customer;
	@Column
	private int status;
	
	public Tickets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tickets(String id_ticket, int ticketType, int ticketId,
			String person, String customer, int status) {
		super();
		this.id_ticket = id_ticket;
		this.ticketType = ticketType;
		this.ticketId = ticketId;
		this.person = person;
		this.customer = customer;
		this.status = status;
	}
	public String getId_ticket() {
		return id_ticket;
	}

	public void setId_ticket(String id_ticket) {
		this.id_ticket = id_ticket;
	}

	public int getTicketType() {
		return ticketType;
	}
	public void setTicketType(int ticketType) {
		this.ticketType = ticketType;
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public void InsertTickets(String idt, int ticketTyp, int tickid, String per, String cust, int stat ) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Tickets  ti = new Tickets();
			ti.setId_ticket(idt);
			ti.setTicketType(ticketTyp);
			ti.setTicketId(tickid);
			ti.setPerson(per);
			ti.setCustomer(cust);
			ti.setStatus(stat);
			session.save(ti);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateTickets(Tickets tick) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(tick);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public Tickets[] ListTickets(int typeTick) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Tickets> ti = session.createCriteria(Tickets.class)
                                .add(Restrictions.eq("ticketType", typeTick))
                                .list();
			return ti.toArray(new Tickets[ti.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
        
        public Tickets LastTicket() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Tickets> ti = session.createCriteria(Tickets.class)
                                .list();
			return ti.toArray(new Tickets[ti.size()])[0];
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	public void DeleteTicket(Tickets tick) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		session.beginTransaction();// beginning of the transaction
		session.delete(tick);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session

	}
	
	public ResultQueries.Loadticket[] LoadTickets(int tickettype, int ticketid) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.getCurrentSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"SELECT T.ID_ticket as idticket, T.TICKETTYPE as tipoticket, T.TICKETID as ticketid, "
            		+ "R.DATENEW as datenew, R.MONEY as money, R.ATTRIBUTES as attribut "
            		+ "FROM RECEIPTS R JOIN TICKETS T "
            		+ "ON R.ID = T.ID_ticket "
            		+" WHERE T.TICKETTYPE = '" + tickettype + "' AND T.TICKETID = '" + ticketid + "' ");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.Loadticket> result = new ArrayList<ResultQueries.Loadticket>();
			for (Object[] obj : elist) {
//				BigInteger n = (BigInteger) obj[1];
//				BigInteger m = (BigInteger) obj[2];
				result.add(new ResultQueries.Loadticket((String)obj[0],(Integer)obj[1], (Integer)obj[2],(Date)obj[3],(String)obj[4],(String)obj[5]));
			}
			return result.toArray(new ResultQueries.Loadticket[0]);
		} finally {
			//session.close();
		}

	}
	
}
