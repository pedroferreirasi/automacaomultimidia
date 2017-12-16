package br.com.LeituraAPI.repositorio.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;

import br.com.LeituraAPI.modelo.Seriado;
import br.com.LeituraAPI.respositorio.hibernate.HibernateDao;
import br.com.LeituraAPI.respositorio.hibernate.HibernateSessionFactory;;

public class SeriadoDaoImpl extends HibernateDao<Seriado, Integer> {

	@Override
	public List<Seriado> getAll() {
		List<Seriado> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
		    CriteriaQuery<Seriado> criteria = builder.createQuery(Seriado.class);
		    Root<Seriado> root = criteria.from(Seriado.class);
		    criteria.where(builder.equal(root.get("ativo"), true));
		    lista = session.createQuery(criteria).getResultList();
			
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}			
		return lista;
	}
}
