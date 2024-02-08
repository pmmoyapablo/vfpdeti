package elements;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtils;

@Table
@Entity
public class Countries implements Serializable {
		@Column
		@Id	
		private int id;
		@Column
		private String description;
		public Countries() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public void addCountries(int id, String desc ) {
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				session.beginTransaction();
				Countries co = new Countries();
				co.setId(id);
				co.setDescription(desc);
				session.save(co);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
                public void updateCountries(Countries pNewCountr) {
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				session.beginTransaction();
				session.update(pNewCountr);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
		public Countries getContryByName(String pName) throws HibernateException{
			Session session = HibernateUtils.SESSION_FACTORY.openSession();               
			try {
				List<Countries> co = (List<Countries>) session
						.createCriteria(Countries.class)
						.add(Restrictions.eq("description", pName)).list();
                                
                                if(co.size() > 0)
				return co.toArray(new Countries[co.size()])[0];
                                else
                                    return null;
			} finally {
				session.close();
			}
			
		}
		public Countries[] listCountries() {
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				List<Countries> countriesList = session.createCriteria(Countries.class).list();
				return countriesList.toArray(new Countries[countriesList.size()]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				session.close();// end of session
			}
		}

	
		public Countries[] CountryById(int id) throws HibernateException {
			// consultando con condicion
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				List<Countries> co = (List<Countries>) session
						.createCriteria(Countries.class)
						.add(Restrictions.eq("id", id)).list();
				return co.toArray(new Countries[co.size()]);
			} finally {
				session.close();
			}
	
		}
		
}
