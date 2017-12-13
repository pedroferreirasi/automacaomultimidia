package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.LeituraAPI.modelo.Temporada;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;

public class TemporadaDaoImpl extends HibernateDao<Temporada, Integer> {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Temporada> getAllBySeriado(Integer idSeriado) {
		List<Temporada> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Temporada.class);
			criteria.add(Restrictions.eqOrIsNull("seriado", idSeriado));
			criteria.addOrder(Order.desc("temporada"));
			criteria.addOrder(Order.desc("episodio"));
			lista = criteria.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
