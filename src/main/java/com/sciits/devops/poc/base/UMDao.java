
package com.sciits.devops.poc.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

public class UMDao<PK extends Serializable, T> {
	@Autowired
	SessionFactory sessionFactory;
	private final Class<T> persistentClass;

	//========================================================================
	
	@SuppressWarnings("unchecked")
	public UMDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	//========================================================================
	
	protected Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException he) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	//========================================================================
	
	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	//========================================================================
	
	public T saveOrUpdate(T entity) {
		Session session=null;
		try{
			session = getSession();
			session.getTransaction().begin();
			session.saveOrUpdate(entity);
			session.getTransaction().commit();
			session.flush();
			session.close();
		}catch(HibernateException he){
			session.close();
		}
		return entity;
	}

	//========================================================================
	
	public void delete(T entity) {
		getSession().delete(entity);
	}

	//========================================================================
	
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

	//========================================================================
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return createEntityCriteria().list();
	}

	//========================================================================
	
	@SuppressWarnings("unchecked")
	public List<T> getAllBySortingByCreatedDate() {
		return createEntityCriteria().addOrder(Order.desc("createdDate")).list();

	}

	//========================================================================
	public void closeSession(Session session) {
		session.close();
	}
}