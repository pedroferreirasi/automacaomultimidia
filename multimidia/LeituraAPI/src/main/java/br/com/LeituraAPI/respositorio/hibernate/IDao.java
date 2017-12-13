package br.com.LeituraAPI.respositorio.hibernate;

import java.io.Serializable;
import java.util.List;

public interface IDao<T, PK extends Serializable> {
	void add(T entity);
	void update(T entity);
	void delete(T entity);
	void deleteById(PK id);
	List<T> getAll();
	T getById(PK id);

}
