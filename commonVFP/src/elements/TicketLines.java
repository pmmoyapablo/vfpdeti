package elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import util.HibernateUtils;

@Table
@Entity
public class TicketLines implements Serializable {
        @Column
	@Id
	private String id_Tl;
	@Column	
	private String ticket;
	@Column        
	private int line;
	@Column
	private String product;
	@Column
	private String attributesetinstance_id;
	@Column
	private double units;
	@Column
	private double price;
	@Column
	private String taxid;
	@Column
	private String attributes;
	
	
	public TicketLines() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TicketLines(String pId, String ticket, int line, String product,
			String attributesetinstance_id, double units, double price,
			String taxid, String attributes) {
		super();
                this.id_Tl = pId;
		this.ticket = ticket;
		this.line = line;
		this.product = product;
		this.attributesetinstance_id = attributesetinstance_id;
		this.units = units;
		this.price = price;
		this.taxid = taxid;
		this.attributes = attributes;
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
        public String getId() {
		return id_Tl;
	}
	public void setId(String pId) {
		this.id_Tl = pId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getAttributesetinstance_id() {
		return attributesetinstance_id;
	}
	public void setAttributesetinstance_id(String attributesetinstance_id) {
		this.attributesetinstance_id = attributesetinstance_id;
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
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public void InsertTicketLines(String pId, String tick, int lin, String prod, String atribId, double uni, double pric, String tid, String atrib) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			TicketLines  tl = new TicketLines();
                        tl.setId(pId);
			tl.setTicket(tick);
			tl.setLine(lin);
			tl.setProduct(prod);
			tl.setAttributesetinstance_id(atribId);
			tl.setUnits(uni);
			tl.setPrice(pric);
			tl.setTaxid(tid);
			tl.setAttributes(atrib);
			session.save(tl);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateTicketLines(TicketLines tickL) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(tickL);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public TicketLines[] ListTicketLines() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<TicketLines> tl = session.createCriteria(TicketLines.class).list();
			return tl.toArray(new TicketLines[tl.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	public void DeleteTicketLines(TicketLines tickLine) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		session.beginTransaction();// beginning of the transaction
		session.delete(tickLine);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session

	}
	public ResultQueries.TicketLinesTaxeInfo[] TicketLinesTaxeInfo(String ticket) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.getCurrentSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            		"SELECT L.TICKET as ticket, L.LINE as line, L.PRODUCT as prod, L.ATTRIBUTESETINSTANCE_ID attsetinsta_id, "
            		+ "L.UNITS as units, L.PRICE as price, T.ID as id, T.nombre as name, T.CATEGORia as category, T.CUSTCATEGORY as custcat, "
            		+ "T.PARENTID as parentid, T.RATE as rate, T.RATECASCADE as ratecascade, T.RATEORDER as rateorder, L.ATTRIBUTES as attrib "
            		+ "FROM TICKETLINES L, TAXES T "
            		+ "WHERE L.TAXID = T.ID AND L.TICKET = '" + ticket + "' "
            		+ "ORDER BY L.LINE ");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.TicketLinesTaxeInfo> result = new ArrayList<ResultQueries.TicketLinesTaxeInfo>();
			for (Object[] obj : elist) {
//				BigInteger n = (BigInteger) obj[1];
//				BigInteger m = (BigInteger) obj[2];
				result.add(new ResultQueries.TicketLinesTaxeInfo((String)obj[0],(Integer)obj[1], (String)obj[2],(String)obj[3],(Double)obj[4]
																,(Double)obj[5],(String)obj[6],(String)obj[7],(String)obj[8],(String)obj[9],
																(String)obj[10],(Double)obj[11],(Integer)obj[12],(Integer)obj[13],
																(String)obj[14]));
			}
			return result.toArray(new ResultQueries.TicketLinesTaxeInfo[0]);
		} finally {
			//session.close();
		}

	}
		
}
