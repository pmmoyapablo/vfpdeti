package elements;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtils;
@Table
@Entity
public class MeanPayment implements Serializable {
	
	@Column
	@Id
	private int id_mean;
	@Column
	private String Description;
	@Column
	private double Amount;
	@Column
        @Id
	private int equipment_id;
	public MeanPayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public MeanPayment(int id_mean, String description, double amount,
			int equipment_id) {
		super();
		this.id_mean = id_mean;
		Description = description;
		Amount = amount;
		this.equipment_id = equipment_id;
	}


	public int getId_mean() {
		return id_mean;
	}


	public void setId_mean(int id_mean) {
		this.id_mean = id_mean;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public double getAmount() {
		return Amount;
	}


	public void setAmount(double amount) {
		Amount = amount;
	}


	public int getEquipment_id() {
		return equipment_id;
	}


	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}


	public void Edit_Value(MeanPayment mean) throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(mean);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
  
	public void InsertMeanPayment(int id_m, String desc, double amo, int equip_id) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			MeanPayment mp = new MeanPayment();
			mp.setId_mean(id_m);
			mp.setDescription(desc);
			mp.setAmount(amo);
			mp.setEquipment_id(equip_id);
			session.save(mp);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public MeanPayment[] mediaList(int equipId) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<MeanPayment> meanList = session
					.createCriteria(MeanPayment.class)
					.add(Restrictions.eq("equipment_id", equipId)).list();
			return meanList.toArray(new MeanPayment[meanList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
        
        public MeanPayment meanEntity(int pId, int equipId) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<MeanPayment> meanList = session
					.createCriteria(MeanPayment.class)
                                        .add(Restrictions.eq("id_mean", pId))
					.add(Restrictions.eq("equipment_id", equipId)).list();
                      
                        if(meanList.size() > 0)
			    return meanList.toArray(new MeanPayment[meanList.size()])[0];
                        else
                            return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
	
	public MeanPayment[] ListMeanPayment() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<MeanPayment> mp = session.createCriteria(MeanPayment.class).list();
			return mp.toArray(new MeanPayment[mp.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}


}
