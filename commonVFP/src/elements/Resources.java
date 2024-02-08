package elements;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtils;
@Table
@Entity
public class Resources implements Serializable {
	@Column
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private int resType;
	@Lob @Column
	SerialBlob content;
	public Resources() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Resources(int id, String name, int resType, SerialBlob content) {
		super();
		this.id = id;
		this.name = name;
		this.resType = resType;
		this.content = content;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResType() {
		return resType;
	}
	public void setResType(int resType) {
		this.resType = resType;
	}
	public SerialBlob getContent() {
		return content;
	}
	public void setContent(SerialBlob content) {
		this.content = content;
	}
	
	public Integer listMaxId() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {			
			Integer maxId = this.ListResources().length;
                        
			return maxId;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			session.close();// end of session
		}

	}

	public void InsertResources(int idR, String nam, int rest, SerialBlob blob) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			Resources re = new Resources();
			re.setId(idR);
			re.setName(nam);
			re.setResType(rest);
			re.setContent(blob);
			session.save(re);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public Resources[] ListResources() {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			List<Resources> re = session.createCriteria(Resources.class).list();
			return re.toArray(new Resources[re.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();// end of session
		}

	}
        
        public Resources[] ResourceByName(String name) throws HibernateException {
			// consultando con condicion
			Session session = HibernateUtils.SESSION_FACTORY.openSession();
			try {
				List<Resources> res = (List<Resources>) session
						.createCriteria(Resources.class)
						.add(Restrictions.eq("name", name)).list();
                                if(res.size() > 0)
                                {
                                    for(Resources r : res)
                                    { 
                                       this.setId(r.getId());
                                       this.setName(r.getName());
                                       this.setContent(r.getContent());
                                    }
                                }else
                                {
                                       this.setId(0);
                                       this.setName(null);
                                       this.setContent(null);
                                }
				return res.toArray(new Resources[res.size()]);
			} finally {
				session.close();
			}
	
		}
    	public void UpdateResource(Resources re) {
    		Session session = HibernateUtils.SESSION_FACTORY.openSession();
    		try {
    			session.beginTransaction();
    			session.update(re);
    			session.getTransaction().commit();
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			session.close();
    		}
    	}
		
}
