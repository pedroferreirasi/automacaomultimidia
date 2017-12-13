package br.com.LeituraAPI.respositorio.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static String FILE_CFG_HIBERNATE = "hibernate.cfg.xml";
	private static SessionFactory sessionFactory;
	
	private HibernateSessionFactory() { }
	
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure(FILE_CFG_HIBERNATE).buildSessionFactory();
			return sessionFactory;
		} else {
			return sessionFactory;
		}
	}
	
	public static Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
}
