package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.Sale;
import com.lq.other.notConfirmPhone;
@Repository
public class SaleDaoImpl implements SaleDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean updateSale(int bookid, long start_time, int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Sale u set u.start_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, start_time);
		query.setInteger(1, sureornot);
		query.setInteger(2, bookid);
		return (query.executeUpdate()>0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sale> getSalewithoutConfirm(List<Integer> books) {
		// TODO Auto-generated method stub
		String hql = "FROM Sale u WHERE u.sureornot = 0 and u.id in (:alist) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("alist", books);
		return query.list();
	}

	@Override
	public Sale getOneSale(int bookid) {
		// TODO Auto-generated method stub
		String hql = "FROM Sale u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (Sale) query.uniqueResult();
	}

	@Override
	public boolean dealConfirm(int bookid, int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Sale u set u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		//sureornot == 1
		query.setInteger(0, sureornot);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}
	//根据bookid查找手机号和书名
	@SuppressWarnings("unchecked")
	@Override
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books) {
		String hql = "select new notConfirmPhone(s.id, u.phone, i.title) from Sale s, User u, Isbn i "
				+ "where (s.sureornot=0 and s.id in (:booklist)) and u.id in s.origin_openid and i.isbn in s.information order by s.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("booklist", books);
		return query.list();
	}
}