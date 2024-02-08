package elements;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;

import util.HibernateUtils;

@Table
@Entity
public class Taxlines implements Serializable {
	@Column
	@Id
	private String id;
	@Column
	private String receipt;
	@Column
	private String taxId;
	@Column
	private double base;
	@Column
	private double amount;
	public Taxlines() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Taxlines(String id, String receipt, String taxId, double base,
			double amount) {
		super();
		this.id = id;
		this.receipt = receipt;
		this.taxId = taxId;
		this.base = base;
		this.amount = amount;
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
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public double getBase() {
		return base;
	}
	public void setBase(double base) {
		this.base = base;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void InsertTickets(String idT, String rece, String taxid, double bas, double amou) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Taxlines  taxL = new Taxlines();
			taxL.setId(idT);
			taxL.setReceipt(rece);
			taxL.setTaxId(taxid);
			taxL.setBase(bas);
			taxL.setAmount(amou);
			session.save(taxL);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void UpdateTaxlines(Taxlines taxLine) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(taxLine);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public Taxlines[] ListTaxlines() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Taxlines> taxL = session.createCriteria(Taxlines.class).list();
			return taxL.toArray(new Taxlines[taxL.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	public void DeleteTaxLines(Taxlines txlines) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		session.beginTransaction();// beginning of the transaction
		session.delete(txlines);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session

	}

}
