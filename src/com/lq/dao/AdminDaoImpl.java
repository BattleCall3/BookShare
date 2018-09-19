package com.lq.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.A_Welcome;

@Repository
public class AdminDaoImpl implements AdminDao{
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addWelcomeInfo(String text) {
		A_Welcome welcomeInfo = new A_Welcome(text);
		sessionFactory.getCurrentSession().save(welcomeInfo);
	}
	@Override
	public void alterWelcomeInfo(int number, String text) {
		String hql = "update a_welcome set text = ? where number = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, text);
		query.setInteger(1, number);
		query.executeUpdate();
	}
	
}
