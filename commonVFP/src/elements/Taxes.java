package elements;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import util.HibernateUtils;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Table
@Entity
public class Taxes implements Serializable {
	@Column
	@Id
	private String id;
	@Column
	private String name;
	@Column
	private String categoria;
	@Column
	private String custCategory;
	@Column
	private String parentId;
	@Column
	private double rate;
	@Column
	private int rateCascade;
	@Column
	private int rateOrder;
	@Column
        @Id
	private int equipment_id;
	public Taxes() {
		super();
	}
	public Taxes(String id, String nombre, String categoria,
			String custCategory, String parentId, double rate, int rateCascade,
			int rateOrder, int equipment_id) {
		super();
		this.id = id;
		this.name = nombre;
		this.categoria = categoria;
		this.custCategory = custCategory;
		this.parentId = parentId;
		this.rate = rate;
		this.rateCascade = rateCascade;
		this.rateOrder = rateOrder;
		this.equipment_id = equipment_id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return name;
	}
	public void setNombre(String nombre) {
		this.name = nombre;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getCustCategory() {
		return custCategory;
	}
	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getRateCascade() {
		return rateCascade;
	}
	public void setRateCascade(int rateCascade) {
		this.rateCascade = rateCascade;
	}
	public int getRateOrder() {
		return rateOrder;
	}
	public void setRateOrder(int rateOrder) {
		this.rateOrder = rateOrder;
	}
	public int getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	
	public void insertar(String id, String nom, String cat, String custCat,
			String pId, double rat, int rateC, int rateO, int equip_id) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Taxes tx = new Taxes();
			tx.setId(id);
			tx.setNombre(nom);
			tx.setCategoria(cat);
			tx.setCustCategory(custCat);
			tx.setParentId(pId);
			tx.setRate(rat);
			tx.setRateCascade(rateC);
			tx.setRateOrder(rateO);
			tx.setEquipment_id(equip_id);
			session.save(tx);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Taxes[] listTaxes(int equip_id) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Taxes> taxesList = session.createCriteria(Taxes.class)
                                .add(Restrictions.eq("equipment_id", equip_id)).list();
			return taxesList.toArray(new Taxes[taxesList.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}
	}

	public void EditTaxes(Taxes tx) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.update(tx);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Taxes getTaxeById(String idTax, int equip_id) throws HibernateException {
		// consultando con condicion
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Taxes> tx = (List<Taxes>) session.createCriteria(Taxes.class)
                                        .add(Restrictions.eq("id", String.valueOf(idTax)))
					.add(Restrictions.eq("equipment_id", equip_id)).list();
			Taxes[] taxs = tx.toArray(new Taxes[tx.size()]);
                        
                        if(taxs != null)
                            if(taxs.length > 0)
                            return taxs[0];
                                    else
                                return null;
                        else
                            return null;
		} finally {
			session.close();
		}

	}
        
        public Taxes[] getTaxesByIdEquipment( int equip_id) throws HibernateException {
		// consultando con condicion
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Taxes> tx = (List<Taxes>) session.createCriteria(Taxes.class)
					.add(Restrictions.eq("equipment_id", equip_id)).list();
			Taxes[] taxs = tx.toArray(new Taxes[tx.size()]);
                        
                        if(taxs != null)
                            if(taxs.length > 0)
                            return taxs;
                                    else
                                return null;
                        else
                            return null;
		} finally {
			session.close();
		}

	}
        
        public Taxes[] getTaxesAll() throws HibernateException {
		// consultando con condicion
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Taxes> tx = (List<Taxes>) session.createCriteria(Taxes.class).list();
			Taxes[] taxs = tx.toArray(new Taxes[tx.size()]);
                        
                        if(taxs != null)
                            if(taxs.length > 0)
                            return taxs;
                                    else
                                return null;
                        else
                            return null;
		} finally {
			session.close();
		}

	}
	
	
}
