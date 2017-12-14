package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;

import br.com.LeituraAPI.modelo.Torrent;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;

public class TorrentDaoImpl extends HibernateDao<Torrent, Integer> {

	public List<Torrent> getByIdEZTV(String id) {
		List<Torrent> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Torrent> criteria = builder.createQuery(Torrent.class); 			
			Root<Torrent> entidade = criteria.from(Torrent.class);
			
			criteria.where(builder.equal(entidade.get("idEZTV"), id));
			lista = session.createQuery(criteria).getResultList();
			
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
