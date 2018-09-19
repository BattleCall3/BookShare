package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.entity.TradeLog;
import com.lq.other.notConfirmPhone;
@Repository
public class UserBookDaoImpl implements UserBookDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addBookOwner(BookOwner bookowner) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(bookowner);
	}

	@Override
	public boolean resumeBookOwner(String lastuserid,int bookid) {
		// TODO Auto-generated method stub

		String hql   = "update BookOwner u set u.userid= ?,u.logid = 0 where u.bookid=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, lastuserid);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0); 
	}
	
	@Override
	public boolean updateBookOwner(String userid, int bookid,int logid) {
		// TODO Auto-generated method stub
		String hql = "update BookOwner u set u.userid=?,u.logid=?where u.bookid=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		query.setInteger(1, logid);
		query.setInteger(2, bookid);
		return (query.executeUpdate()>0);
	}

	@Override
	public BookOwner getBookOwner(int bookid) {
		// TODO Auto-generated method stub
		String hql = "FROM BookOwner u Where u.bookid=? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (BookOwner) query.uniqueResult();
	}
	
	@Override
	public boolean delBookOwner(int bookid) {
		// TODO Auto-generated method stub
		String hql = "delete BookOwner u where u.bookid = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (query.executeUpdate() > 0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Sale> getAllSale(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Sale u WHERE u.sureornot = 1 and u.id in (SELECT b.bookid FROM BookOwner b WHERE b.userid= ? and b.logid > 0) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rented> getAllRented(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rented u WHERE u.sureornot = 1 and u.id in (SELECT b.bookid FROM BookOwner b WHERE b.userid= ?) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getAllRentable(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rentable u WHERE u.id in (SELECT b.bookid FROM BookOwner b "
				+ "WHERE b.userid= ? and b.logid =0) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getAllOutDate(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rentable u WHERE u.id in (SELECT b.bookid FROM BookOwner b "
				+ "WHERE b.userid =? and b.logid <>0) order by u.start_time desc";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	@Override
	public TradeLog getLogByid(int logid) {
		// TODO Auto-generated method stub
		String hql = "FROM TradeLog u Where u.id=? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, logid);
		return (TradeLog) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getBooksfromBookOwner(String userid) {
		// TODO Auto-generated method stub
		String hql = "SELECT u.bookid FROM BookOwner u Where u.userid=?";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}

	//根据书的isbn查找书名，借用notconfirmphone表结构，返回bookid和书名
	@SuppressWarnings("unchecked")
	public List<notConfirmPhone> getBookTitle(String userid) {
		List<notConfirmPhone> resultTitle = null;
		List<notConfirmPhone> temp = null;
		String hql = null;
		Query query = null;
		hql = "select new notConfirmPhone(s.id, i.title) from Sale s, Isbn i "
				+ "where (s.sureornot=1 and s.id in (select b.bookid from BookOwner b where b.userid=?)) and i.isbn in s.information order by s.start_time desc";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		resultTitle = query.list();
		hql = "select new notConfirmPhone(r.id, i.title) from Rented r, Isbn i "
				+ "where (r.sureornot=1 and r.id in (select b.bookid from BookOwner b where b.userid=?)) and i.isbn in r.information order by r.start_time desc";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		temp = query.list();
		resultTitle.addAll(temp);
		hql = "select new notConfirmPhone(r.id, i.title) from Rentable r, Isbn i "
				+ "where r.id in (select b.bookid from BookOwner b where b.userid=? and b.logid<>0) and i.isbn in r.information order by r.start_time desc";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		temp = query.list();
		resultTitle.addAll(temp);
		hql = "select new notConfirmPhone(r.id, i.title) from Rentable r, Isbn i "
				+ "where r.id in (select b.bookid from BookOwner b where b.userid=? and b.logid=0) and i.isbn in r.information  order by r.start_time desc";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		temp = query.list();
		resultTitle.addAll(temp);
		return resultTitle;
	}
	//返回被别人预定购买的书
	@SuppressWarnings("unchecked")
	@Override
	public List<Sale> getSaling(String userid) {
		String hql = "from sale where origin_openid = ? and sureornot = 0";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	
}
