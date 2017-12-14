package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;

import br.com.LeituraAPI.modelo.Temporada;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;

public class TemporadaDaoImpl extends HibernateDao<Temporada, Integer> {

	public List<Temporada> getAllBySeriado(Integer idSeriado) {
		List<Temporada> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Temporada> criteria = builder.createQuery(Temporada.class);
			
			Root<Temporada> root = criteria.from(Temporada.class);
			  
			criteria.where(builder.equal(root.get("seriado"), idSeriado));
			
			lista = session.createQuery(criteria).getResultList();
			
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
