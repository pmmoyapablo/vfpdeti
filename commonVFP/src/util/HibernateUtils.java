package util;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import elements.*;

public class HibernateUtils {
	private static final AnnotationConfiguration config = new AnnotationConfiguration()
			.addAnnotatedClass(Flags.class)
                        .addAnnotatedClass(Cashier.class)
                        .addAnnotatedClass(ClosedCash.class)
                        .addAnnotatedClass(TicketLines.class)
                        .addAnnotatedClass(Tickets.class)
                        .addAnnotatedClass(Receipts.class)
                        .addAnnotatedClass(Payments.class)
                        .addAnnotatedClass(MeanPayment.class)
                        .addAnnotatedClass(FooterHeader.class) 
                        .addAnnotatedClass(Equipments.class)
                        .addAnnotatedClass(Taxes.class)
                        .addAnnotatedClass(Taxlines.class)
                        .addAnnotatedClass(Countries.class)
                        .addAnnotatedClass(Resources.class)
                        .configure("hibernate.cfg.xml");

        public static final SessionFactory SESSION_FACTORY = config.buildSessionFactory();
        public void contextDestroyed() {
            HibernateUtils.SESSION_FACTORY.close(); // Free all resources
            try {
                  DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException e) {}
            
        }
/*
	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
      
   */
}
