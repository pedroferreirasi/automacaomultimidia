package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.LeituraAPI.modelo.Seriado;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;;

public class SeriadoDaoImpl extends HibernateDao<Seriado, Integer> {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Seriado> getAll() {
		List<Seriado> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seriado.class);
			criteria.add(Restrictions.eqOrIsNull("ativo", true));
			criteria.addOrder(Order.asc("id")).list();
			lista = criteria.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
