package com.sciits.devops.poc.base;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SessionBase {

	public SessionBase() {
	}

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException he) {
			session = sessionFactory.openSession();
		}
		return session;
	}

}
