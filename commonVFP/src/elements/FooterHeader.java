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
public class FooterHeader implements Serializable {
	@Column
	@Id
	private int equipment_id;
	@Column
	private String e1;
	@Column
	private String e2;
	@Column
	private String e3;
	@Column
	private String e4;
	@Column
	private String e5;
	@Column
	private String e6;
	@Column
	private String e7;
	@Column
	private String e8;
	@Column
	private String p1;
	@Column
	private String p2;
	@Column
	private String p3;
	@Column
	private String p4;
	@Column
	private String p5;
	@Column
	private String p6;
	@Column
	private String p7;
	@Column
	private String p8;
	
	public FooterHeader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}

	public String getE1() {
		return e1;
	}

	public void setE1(String e1) {
		this.e1 = e1;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public String getE3() {
		return e3;
	}

	public void setE3(String e3) {
		this.e3 = e3;
	}

	public String getE4() {
		return e4;
	}

	public void setE4(String e4) {
		this.e4 = e4;
	}

	public String getE5() {
		return e5;
	}

	public void setE5(String e5) {
		this.e5 = e5;
	}

	public String getE6() {
		return e6;
	}

	public void setE6(String e6) {
		this.e6 = e6;
	}

	public String getE7() {
		return e7;
	}

	public void setE7(String e7) {
		this.e7 = e7;
	}

	public String getE8() {
		return e8;
	}

	public void setE8(String e8) {
		this.e8 = e8;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public String getP6() {
		return p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public String getP8() {
		return p8;
	}

	public void setP8(String p8) {
		this.p8 = p8;
	}
	
	public FooterHeader[] ListFooterHeaderById(int equip_id)
			throws HibernateException {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<FooterHeader> hf = (List<FooterHeader>) session
					.createCriteria(FooterHeader.class)
					.add(Restrictions.eq("equipment_id", equip_id)).list();
			return hf.toArray(new FooterHeader[hf.size()]);
		} finally {
			session.close();
		}

	}
	
	public void setHeader_Footer(FooterHeader hf) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(hf);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}