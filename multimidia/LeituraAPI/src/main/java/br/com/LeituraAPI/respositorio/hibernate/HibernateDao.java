package br.com.LeituraAPI.respositorio.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class HibernateDao<T, PK extends Serializable> implements IDao<T, PK> {

	protected Session session;
	protected Transaction transacao = null;

	@Override
	public void add(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			transacao = session.beginTransaction();
			session.persist(entity);

			if (transacao.isActive()) {
				transacao.commit();
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	@Override
	public void update(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			transacao = session.beginTransaction();
			session.update(entity);
			if (transacao.isActive()) {
				transacao.commit();
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			transacao = session.beginTransaction();
			session.delete(entity);
			if (transacao.isActive()) {
				transacao.commit();
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(PK id) {
		try {
			session = HibernateSessionFactory.getSession();
			transacao = session.beginTransaction();
			T entity = getById(id);
			if (entity != null) {
				session.delete(entity);
			}
			if (transacao.isActive()) {
				transacao.commit();
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public List<T> getAll() {
		List<T> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			transacao = session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(getTypeClass());
			criteria.from(getTypeClass());
			lista = session.createQuery(criteria).getResultList();

			if (transacao.isActive()) {
				transacao.commit();
			}
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public T getById(PK id) {
		return HibernateSessionFactory.getSession().get(getTypeClass(), id);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getTypeClass() {
		Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return clazz;
	}

}
