package elements;


import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import util.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
@Table
@Entity
public class Cashier implements Serializable {
		@Column
		@Id	
	    private int id_Cashier = -1;
		@Column
		private String name;
		@Column
		private String pass;
		@Column
                @Id
		private int equipment_id;

    public Cashier() {
    }
	    
	    public Cashier(int idEq)
	    {
	       this.id_Cashier = 0;
	       this.name = null;
	       this.pass = null;
	       this.equipment_id = idEq;
	    }
	    public int getId_Cashier() {
			return id_Cashier;
		}
		public void setId_Cashier(int id_Cashier) {
			this.id_Cashier = id_Cashier;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public int getId_equipment() {
			return equipment_id;
		}
		public void setId_equipment(int id_equipment) {
			this.equipment_id = id_equipment;
		}
	    
	    //Metodo
		public void Schedule_Cashier(Cashier cashier) throws HibernateException {
			// edicion
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(cashier);
				session.getTransaction().commit();
	
			} finally {
				session.close();
			}
		}

		public Cashier Start_Cashier(int equip_id, String passw) throws HibernateException {
			// consultando  con condicion
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			Cashier cashier = null;
			try {
				List<Cashier> cash = (List<Cashier>) session.createCriteria(Cashier.class)
										.add(Restrictions.eq("pass", passw))
										.add(Restrictions.eq("equipment_id", equip_id)).list(); 
				if(cash.size() > 0)
                                 cashier = cash.get(0);
                                
                                return cashier;
                               
			} finally {
				session.close();
			}
	
		}
	    
	    public void Finalize_Cashier(int equipment_id) throws HibernateException
	    {
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			Cashier cashier = null;
			try {
				cashier = (Cashier) session.load(Cashier.class, equipment_id);
				cashier.setId_Cashier(-1);
				cashier.setName(null);
			} finally {
				session.close();
			}
	       
	    }

}
