package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import com.lq.entity.Rented;
import com.lq.other.notConfirmPhone;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class RentedDaoImpl implements RentedDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean moveToWorthless(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rented> getRented(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rented u Where u.id in(select b.bookid from BookOwner b where b.userid =?) ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}

	@Override
	public Rented getOneRented(int id) {
		// TODO Auto-generated method stub
		String hql = "FROM Rented u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		return (Rented) query.uniqueResult();
	}

	@Override
	public boolean dealConfirm(int bookid,int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Rented u set u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		//sureornot == 1
		query.setInteger(0, sureornot);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}

	@Override
	public boolean updateRented(int bookid,long begin_time, long end_time,int sureornot) {
		// TODO Auto-generated method stub
		String hql = "update Rented u set u.begin_time=?,u.end_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setLong(0, begin_time);
		query.setLong(1, end_time);
		query.setInteger(2, sureornot);
		query.setInteger(3, bookid);
		return (query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rented> getRentedwithoutConfirm(List<Integer> books) {
		// TODO Auto-generated method stub
		String hql = "FROM Rented u WHERE u.sureornot = 0 and u.id in (:alist) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("alist", books);
		return query.list();
	}

	@Override
	public boolean updateRented(int bookid,long begin_time, long end_time,int sureornot,String picture) {
		// TODO Auto-generated method stub
		String hql = "update Rented u set u.picture=?,u.begin_time=?,u.end_time=?,u.sureornot=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, picture);
		query.setLong(1, begin_time);
		query.setLong(2, end_time);
		query.setInteger(3, sureornot);
		query.setInteger(4, bookid);
		return (query.executeUpdate()>0);
	}

	//根据bookid查找电话号和书名
	@SuppressWarnings("unchecked")
	@Override
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books) {
//		String hql;
//		Query query;
//		hql = "select r.id from Rented r where r.sureornot = 0 and r.id in (:list)";
//		query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setParameterList("list", books);
//		List<Integer> bookss = query.list();
//		hql = "select s.id from Sale s where s.sureornot = 0 and s.id in (:list)";
//		query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setParameterList("list", books);
//		List<Integer> bookt = query.list();
//		bookss.addAll(bookt);
		String hql = "select new notConfirmPhone(r.id, u.phone, i.title) from Rented r, User u, Isbn i "
				+ "where (r.sureornot=0 and r.id in (:booklist)) and u.id in r.origin_openid and i.isbn in r.information order by r.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("booklist", books);
		return query.list();
	}
}