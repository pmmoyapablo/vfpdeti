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
public class Equipments implements Serializable {
	@Column
	@Id
	private int id;
	@Column
	private String brand;
	@Column
	private String model;
	@Column
	private String machine_register;
	@Column
	private String machine_serie;
	@Column
	private String version_firmware;
	@Column
	private double papel_quantity;
	@Column
	private String id_fiscal;
        @Column
        private int status; //Default 3
	 @Column(name="CONTRY_ID")
	private int countryId;
        
	public Equipments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMachine_register() {
	    return machine_register;
	}
	public void setMachine_register(String machine_register) {
		this.machine_register = machine_register;
	}
	public String getMachine_serie() {
		return machine_serie;
	}
	public void setMachine_serie(String machine_serie) {
		this.machine_serie = machine_serie;
	}
	public String getVersion_firmware() {
		return version_firmware;
	}
	public void setVersion_firmware(String version_firmware) {
		this.version_firmware = version_firmware;
	}
	public double getPapel_quantity() {
		return papel_quantity;
	}
	public void setPapel_quantity(double papel_quantity) {
		this.papel_quantity = papel_quantity;
	}
	public String getId_fiscal() {
		return id_fiscal;         
	}
	public void setId_fiscal(String id_fiscal) {
		this.id_fiscal = id_fiscal;
	}
	
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
        
        public void setStatus(int pStatus)
        {
           this.status = pStatus;
        }
        
        public int getStatus()
        {
            return this.status;
        }
	public void InsertEquipment( int id, String br, String mod, String m_register, 
			 					String m_serie, String ver_firmw, double p_quantity,
			 					String id_fisc, int c_id){
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Equipments eq = new Equipments();
			eq.setId(id);
			eq.setBrand(br);
			eq.setModel(mod);
			eq.setMachine_register(m_register);
			eq.setMachine_serie(m_serie);
			eq.setVersion_firmware(ver_firmw);
			eq.setPapel_quantity(p_quantity);
			eq.setId_fiscal(id_fisc);
			eq.setCountryId(c_id);
                        eq.setStatus(3);
			session.save(eq);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public void UpdateEquipments(Equipments eq) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(eq);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Equipments[] ListEquipmentsById(int country_id)
			throws HibernateException {
		// consultando con condicion
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Equipments> eq = (List<Equipments>) session
					.createCriteria(Equipments.class)
					.add(Restrictions.eq("countryId", country_id)).list();

			return eq.toArray(new Equipments[eq.size()]);
		} finally {
			session.close();
		}

	}
	public Equipments[] ListEquipments() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Equipments> eq = session.createCriteria(Equipments.class).list();
			return eq.toArray(new Equipments[eq.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
}
