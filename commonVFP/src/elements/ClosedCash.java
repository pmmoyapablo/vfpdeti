package elements;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import elements.ResultQueries;
import java.util.Calendar;
import util.HibernateUtils;

@Table
@Entity
public class ClosedCash implements Serializable {
	@Id
	@Column
	private String money;
	@Column
	private String host;
	@Column
	private int hostsequence;
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date  datestart;
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	//@Type(type = "TIMESTAMP")
	private Date  dateend;
	@Column
        @Id
	private int equipment_id;
	
	public ClosedCash() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public ClosedCash(String money, String host, int hostsequence,
			Date datestart, Date dateend, int equipment_id) {
		super();
		this.money = money;
		this.host = host;
		this.hostsequence = hostsequence;
		this.datestart = datestart;
		this.dateend = dateend;
		this.equipment_id = equipment_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getHostsequence() {
		return hostsequence;
	}

	public void setHostsequence(int hostsequence) {
		this.hostsequence = hostsequence;
	}

	public Date getDatestart() {
		return datestart;
	}

	public void setDatestart(Date datestart) {
		this.datestart = datestart;
	}

	public Date getDateend() {
		return dateend;
	}

	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}

	public int getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(int equipment_id) {
		this.equipment_id = equipment_id;
	}
	
	public ResultQueries.Count[] countClosedCash(int hsq) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
			SQLQuery query = session.createSQLQuery("SELECT COUNT(*) as cantidadClosedCas FROM CLOSEDCASH WHERE HOSTSEQUENCE > '" + hsq + "' ");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.Count> res = new ArrayList<ResultQueries.Count>();
			for (Object[] obj : elist){
				//BigInteger n = (BigInteger) obj;
				res.add(new ResultQueries.Count((Integer)obj[0]));
			}
			return res.toArray(new ResultQueries.Count[0]);
		}finally {
			session.close();
		}
		
	}
	public ClosedCash getClosedCashBySequence(int hsq, int idEquip ) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery("SELECT * FROM closedcash WHERE hostsequence = " + hsq + " and equipment_id = " + idEquip);
            query.addEntity(ClosedCash.class);
            List<ClosedCash> elist = query.list();
			session.getTransaction().commit();
                       ClosedCash[] listCloseCash = elist.toArray(new ClosedCash[elist.size()]);
                        if(listCloseCash != null)
                        {
                           if(listCloseCash.length > 0)
                               return listCloseCash[0];
                           else
                               return null;
                        }else
			return null;
		}finally {
			session.close();
		}
		
	}
        
        public int getHostSequenceActual(int idEquip ) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery("SELECT * FROM closedcash WHERE  equipment_id = " + idEquip + " AND DATEEND IS NULL");
            query.addEntity(ClosedCash.class);
            List<ClosedCash> elist = query.list();
			session.getTransaction().commit();
                       ClosedCash[] listCloseCash = elist.toArray(new ClosedCash[elist.size()]);
                        if(listCloseCash != null)
                        {
                           if(listCloseCash.length > 0)
                               return listCloseCash[0].hostsequence;
                           else
                               return 1;
                        }else
			return 1;
		}finally {
			session.close();
		}
		
	}
	
	public ResultQueries.DataendMoney[] DateMoneyClosedCash(int hsq) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
			SQLQuery query = session.createSQLQuery("SELECT  DATEEND as dataend, MONEY as money FROM CLOSEDCASH WHERE HOSTSEQUENCE = " + hsq + " ");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();
     
			List<ResultQueries.DataendMoney> res = new ArrayList<ResultQueries.DataendMoney>();
			for (Object[] obj : elist){
				res.add(new ResultQueries.DataendMoney((Date)obj[0], (String)obj[1]));
			}
			return res.toArray(new ResultQueries.DataendMoney[0]);
		}finally {
			session.close();
		}
		
	}
	public ResultQueries.MaxDate[] maxDate(int hsq) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
			SQLQuery query = session.createSQLQuery("SELECT  max(DATEEND) as dataend FROM CLOSEDCASH WHERE HOSTSEQUENCE > '" + hsq + "' ");
			List<Date> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.MaxDate> res = new ArrayList<ResultQueries.MaxDate>();
			for (Date obj : elist){
				res.add(new ResultQueries.MaxDate((Date)obj));
			}
			return res.toArray(new ResultQueries.MaxDate[0]);
		}finally {
			session.close();
		}
		
	}
	public void InsertClosedCash(String mon, String hos, int hostseq, Date datesta, Date dateend, int equip_id ) {

		Session session = HibernateUtils.SESSION_FACTORY.openSession();
		try {
			session.beginTransaction();
			ClosedCash  cc = new ClosedCash();
			cc.setMoney(mon);
			cc.setHost(hos);
			cc.setHostsequence(hostseq);
			cc.setDatestart(datesta);
			cc.setDateend(dateend);
			cc.setEquipment_id(equip_id);
			session.save(cc);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public ResultQueries.MoneyClosedCash[] MoneyClosedCash(String mon) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
            session.beginTransaction();
			SQLQuery query = session.createSQLQuery("SELECT MONEY as money FROM CLOSEDCASH WHERE DATEEND IS NULL AND MONEY =  '" + mon + "' ");
			List<Object[]> elist = query.list();
			session.getTransaction().commit();

			List<ResultQueries.MoneyClosedCash> res = new ArrayList<ResultQueries.MoneyClosedCash>();
			for (Object[] obj : elist){
				res.add(new ResultQueries.MoneyClosedCash((String)obj[0]));
			}
			return res.toArray(new ResultQueries.MoneyClosedCash[0]);
		}finally {
			session.close();
		}
		
	}
	public void UpdateClosedCash(Date dataend, String mon, String host) throws HibernateException{
		Session session = HibernateUtils.SESSION_FACTORY.openSession();               
		try {
                    Calendar Cale = Calendar.getInstance();
                     Cale.setTime(dataend); 
                  String y = String.valueOf(Cale.get(Calendar.YEAR));
                  int m = Cale.get(Calendar.MONTH) + 1;
                  String mes = String.valueOf(m);
                  String d = String.valueOf(Cale.get(Calendar.DAY_OF_MONTH));
                  String h = String.valueOf(Cale.get(Calendar.HOUR));
                  String min = String.valueOf(Cale.get(Calendar.MINUTE));
                  String s = String.valueOf(Cale.get(Calendar.SECOND));

			while (mes.length() < 2) {
				mes = "0" + mes;
			}
			while (y.length() < 2) {
				y = "0" + y;
			}
			while (d.length() < 2) {
				d = "0" + d;
			}
			while (h.length() < 2) {
				h = "0" + h;
			}
			while (min.length() < 2) {
				min = "0" + min;
			}
			while (s.length() < 2) {
				s = "0" + s;
			}
                        
                        String formatDate = y + "-" + mes + "-" + d + " " + h + ":" + min + ":" + s;
                        
                        session.beginTransaction();
			SQLQuery query = session.createSQLQuery("UPDATE CLOSEDCASH SET DATEEND =  '" + formatDate + "' WHERE HOST =  '" + host + "' AND MONEY =  '" + mon + "' ");
			query.executeUpdate();
			session.getTransaction().commit();

		}finally {
			session.close();
		}
		
	}
        
        public void DeleteClosecash(ClosedCash pClsCash) {
		Session session = HibernateUtils.SESSION_FACTORY.openSession();
                try {
		session.beginTransaction();// beginning of the transaction
		session.delete(pClsCash);
		session.getTransaction().commit();// end of transaction
		session.close();// end of session
                }finally {
			session.close();
		}

	}
	
}
