package elements;


import java.io.Serializable;
import java.util.List;
import util.HibernateUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Table
@Entity
public class Flags implements Serializable {
	// Atributos Globales
	@Column
	@Id
	private int id;
	@Column
	private int value_int;
	@Column
	private String description;
        @Column
        @Id
	private int equipment_id;

    public Flags() {
    }


	// Constructore s
	public Flags(int pId, int pValor, String pDescription, int equip_id) {
		this.id = pId;
		this.value_int = pValor;
        this.description = pDescription;
        this.equipment_id = equip_id;
	}

//	public Flags(AppView m_App) {
//		this.m_dlsyt = (DataLogicSystem) m_App.getBean("forms.DataLogicSystem");
//	}
	// Metodo

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value_int;
	}

	public void setValue(int value) {
		this.value_int = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
        
        public int getEquipoId() {
		return equipment_id;
	}

	public void setEquipoId(int pValue) {
		this.equipment_id = pValue;
	}

	public void Edit_Flags(Flags fl) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(fl);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
        
        public void Insert_Flags(Flags fl) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.save(fl);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
        
        public Flags[] AllFlags()
        {
        Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Flags> flagsList = session.createCriteria(Flags.class)
                                 .list();
			return flagsList.toArray(new Flags[flagsList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}
        }

	public Flags[] listFlags(int idEquipmet) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Flags> flagsList = session.createCriteria(Flags.class)
                                 .add(Restrictions.eq("equipment_id", idEquipmet))
                                 .list();
			return flagsList.toArray(new Flags[flagsList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
        
        public Flags getFlags(int idFlag ,int idEquipmet) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Flags> flagsList = session.createCriteria(Flags.class)
                                .add(Restrictions.eq("id", idFlag))
                                .add(Restrictions.eq("equipment_id", idEquipmet))
                                .list();
			Flags[] flagses = flagsList.toArray(new Flags[flagsList.size()]);
                        if(flagses != null)
                        {
                           if(flagses.length > 0)
                               return flagses[0];
                           else
                               return null;
                        }else
                               return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}

	
}
