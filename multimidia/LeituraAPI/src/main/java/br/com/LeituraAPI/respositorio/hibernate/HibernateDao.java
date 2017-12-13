package br.com.LeituraAPI.respositorio.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class HibernateDao<T, PK extends Serializable> implements IDao<T, PK> {

	protected Session session;
	
	@Override
	public void add(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.persist(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(T entity) {
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}		
	}

	@Override
	public void deleteById(PK id) {
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			T entity = getById(id);
			if (entity != null) {
				session.delete(entity);
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<T> getAll() {
		List<T> lista = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			lista = session.createCriteria(getTypeClass()).list(); 
			session.getTransaction().commit();
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
