package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.LeituraAPI.modelo.Torrent;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;

public class TorrentDaoImpl extends HibernateDao<Torrent, Integer> {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Torrent> getByIdEZTV(String id) {
		List<Torrent> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(getTypeClass());
			criteria.add(Restrictions.eq("idEZTV", id));
			criteria.addOrder(Order.asc("id"));
			lista = criteria.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
