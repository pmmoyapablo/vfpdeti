package elements;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtils;

@Table(name = "Receipts")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Receipts implements Serializable {
	@Id
	@Column(name = "id")
	private String id;
	@Column
	private String money;
	@Basic(optional = false)
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date datenew;
	@Lob @Column
	private SerialBlob attributes;
	public Receipts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Receipts(String id, String money, Date datenew, SerialBlob attributes) {
		super();
		this.id = id;
		this.money = money;
		this.datenew = datenew;
		this.attributes = attributes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Date getDatenew() {
		return datenew;
	}
	public void setDatenew(Date datenew) {
		this.datenew = datenew;
	}
	public SerialBlob getAttributes() {
		return attributes;
	}
	public void setAttributes(SerialBlob attributes) {
		this.attributes = attributes;
	}
	public void AddReceipts(String idR, String mon, Date daten, SerialBlob attrib) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Receipts re = new Receipts();
			re.setId(idR);
			re.setMoney(mon);
			re.setDatenew(daten);
			re.setAttributes(attrib);
			session.save(re);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public Receipts[] ReceiptsList() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Receipts> re = session.createCriteria(Receipts.class).list();
			return re.toArray(new Receipts[re.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	public void DeleteReceipts(Receipts rec) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		session.beginTransaction();// beginning of the transaction
		session.delete(rec);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session

	}
//	public Receipts[] ReceiptsListJoin(String mon) {
//		Session session = HibernateUtils.SESSION_FACTORY.openSession();
//		try {
//			List<Receipts> re = session.createCriteria(Receipts.class)
//				    .createAlias("Receipts", "r")
//				    .createAlias("Payments", "p")
//				    .setProjection(Projections.projectionList()
//				    .add(Projections.rowCount())
//				    .add(Projections.sum("p.total")))
//				    .add(Restrictions.eq("r.money", mon))
//				    .add(Restrictions.eqProperty("r.id", "p.receipt_id"))
//					.list();
//			return re.toArray(new Receipts[re.size()]);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			session.close();// end of session
//		}
//
//	}

	public ResultQueries.ResultadoConsulta[] listReceipts(String mon) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession(); //HibernateUtils.SESSION_FACTORY.getCurrentSession();
		try {
			session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
            			"SELECT COUNT(*) as cantidad, SUM(p.total) as total "
            			+ "FROM Payments p "
            			+ "INNER JOIN Receipts r "
            			+ "ON r.id = p.receipt "
            			+ "WHERE r.money = '" + mon + "'");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.ResultadoConsulta> resultado = new ArrayList<ResultQueries.ResultadoConsulta>();
			for (Object[] obj : elist) {
                            if(obj[1] == null)
                                obj[1] = 0.00;
				//BigInteger n = (BigInteger) obj[0];
				resultado.add(new ResultQueries.ResultadoConsulta((Integer)obj[0], (Double) obj[1]));
			}		
			return resultado.toArray(new ResultQueries.ResultadoConsulta[0]);
		}
               catch(org.hibernate.TransactionException te)
                        {
                            String mnj = te.getMessage();
                            return null;
                        }
                finally {
			//session.close();
		}

	}	
	
	
}
