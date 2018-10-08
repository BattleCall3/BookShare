package com.lq.dao;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.Former;
import com.lq.entity.Generator;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.Rentablestop;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.other.PartRentable;
@Repository
public class RentableDaoImpl implements RentableDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addRentable(Rentable rentable) {		
	
		sessionFactory.getCurrentSession().save(rentable);
	}
	@Override
	public boolean delRentable(int index) {
		String hql = "delete Rentable u where u.id= ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, index);
		return (query.executeUpdate() > 0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getRentables(int startlocation, int size) {
		String hql = "FROM Rentable order by start_time desc";
		// 鏍规嵁闇�瑕佹樉绀虹殑淇℃伅涓嶅悓  灏�*鏇挎崲鎴愪笉鍚屽睘鎬э紝杩樻槸灏佽鎴愪竴涓被CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(size);
		query.setFirstResult(startlocation);
		return query.list();
	}
	@Override
	public boolean updateRentable(int id, String picture){
		String hql = "update Rentable u set u.picture=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, picture);
		query.setInteger(1, id);
		return (query.executeUpdate()>0);
	}
	/*
	 * 闇�瑕佹妸rentable琛ㄤ腑鎵�鏈変笌rented閲嶅鐨勫瓧娈靛叏閮ㄥ鍒跺埌new Rented涓�
	 */
	@Override
	public boolean moveRentable(int index) {
		// TODO Auto-generated method stub
		String hql = "select new Rented(u.id,u.picture,u.information,u.origin_openid,"
				+ "u.start_time,u.way,u.rent_price,u.sale_price) from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	sessionFactory.getCurrentSession().save((Rented) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, index);
    	return (query.executeUpdate()>0);
	}
	/*
	 * 闇�瑕佹妸rentable琛ㄤ腑鎵�鏈変笌sale閲嶅鐨勫瓧娈靛叏閮ㄥ鍒跺埌new Sale涓�
	 */
	@Override
	public boolean moveRentabletoSale(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Sale(u.id,u.picture,u.information,u.origin_openid,u.start_time,"
				+ "u.way,u.rent_price,u.sale_price) from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Sale) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean backRentable(int bookid,String tablename) {
		// TODO Auto-generated method stub	
		String hql = "select new Rentable(u.id,u.picture,u.information,u.origin_openid,u.start_time,"
				+ "u.way,u.rent_price,u.sale_price) from "+ tablename +" u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Rentable)query.uniqueResult());    
    	hql = "delete "+tablename+" u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
		
	}
	@Override
	public boolean updateRentableWay(int bookid,int way) {
		// TODO Auto-generated method stub
    	String hql = "update Rentable u set u.way=? where u.id = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, way);
		query.setInteger(1, bookid);
		return (query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getPartRentables(int startlocation, int size) {
		String hql = "select new PartRentable(u.id,t.picture,u.way,u.rent_price,u.sale_price,t.title) "
				+ "FROM Rentable u,Isbn t WHERE u.information = t.isbn and u.way>0 order by u.start_time desc";
		// 鏍规嵁闇�瑕佹樉绀虹殑淇℃伅涓嶅悓  灏�*鏇挎崲鎴愪笉鍚屽睘鎬э紝杩樻槸灏佽鎴愪竴涓被CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setMaxResults(size);
		query.setFirstResult(startlocation);
		return query.list();
	}
	@Override
	public Rentable getOneRentable(int id) {
		String hql = "FROM Rentable u Where u.id=? ";
		// 鏍规嵁闇�瑕佹樉绀虹殑淇℃伅涓嶅悓  灏�*鏇挎崲鎴愪笉鍚屽睘鎬э紝杩樻槸灏佽鎴愪竴涓被CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id);
		return (Rentable) query.uniqueResult();
	}
	@Override
	public Isbn getBookInfo(String isbn) {
		// TODO Auto-generated method stub
			String hql = "FROM Isbn u Where u.isbn=? ";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString(0, isbn);
			return (Isbn) query.uniqueResult();
	}
	@Override
	public int getGeneratorId() {
		
		// TODO Auto-generated method stub
		String hql = "SELECT next_value FROM Generator WHERE name =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,"rentable_id");	
		if(query.uniqueResult() ==null ){
			sessionFactory.getCurrentSession().save(new Generator("rentable_id",0));
			return 0;
		}
		return (Integer)query.uniqueResult();
	}
	@Override
	public boolean updateGeneratorId(int id) {
		// TODO Auto-generated method stub
		String hql = "update Generator set next_value=? WHERE name =?";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, id+1);
		query.setString(1,"rentable_id");
		return(query.executeUpdate()>0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Rentable> getRentableWayBelowZero(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rentable WHERE way<0";
		// 鏍规嵁闇�瑕佹樉绀虹殑淇℃伅涓嶅悓  灏�*鏇挎崲鎴愪笉鍚屽睘鎬э紝杩樻槸灏佽鎴愪竴涓被CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@Override
	public boolean moveRentabletoStop(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Rentablestop(u.id,u.information,u.origin_openid,"
				+ "u.start_time)from Rentable u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	sessionFactory.getCurrentSession().save((Rentablestop) query.uniqueResult());
    	hql = "delete Rentable u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean moveRentableFromStop(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select new Rentable(u.id,u.information,u.origin_openid,u.start_time) from Rentablestop u"
				+ " where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		sessionFactory.getCurrentSession().save((Rentable) query.uniqueResult());
		hql = "delete Rentablestop u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, bookid);
    	return (query.executeUpdate()>0);
	}
	@Override
	public boolean updateRentableInfo(int bookid, String picPath,
			BigDecimal rent_price, BigDecimal sale_price, int way) {
		// TODO Auto-generated method stub
		String hql = "update Rentable set picture=?,rent_price=?,sale_price=?,way=? WHERE id =?";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0,picPath);
		query.setBigDecimal(1, rent_price);
		query.setBigDecimal(2, sale_price);
		query.setInteger(3, way);
		query.setInteger(4,bookid);
		return(query.executeUpdate()>0);
	}
	@Override
	public long getRentableLen() {
		// TODO Auto-generated method stub
		String hql = "SELECT COUNT(*) FROM Rentable";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return (long)query.uniqueResult();
	}
	@Override
	public boolean updateRentableWayToMinus(int bookid) {
		// TODO Auto-generated method stub
		String hql = "select b.way from Rentable b WHERE b.id =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
	    String hql2 = "update Rentable u set u.way= ?where u.id = ?";
		Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
		query2.setInteger(0, (Integer)query.uniqueResult()*-1);
		query2.setInteger(1, bookid);
		return (query2.executeUpdate()>0);
	}
	@Override
	public Rentablestop getOneRentableStop(int bookid) {
		// TODO Auto-generated method stub
		String hql = "FROM Rentablestop u Where u.id=? ";
		// 鏍规嵁闇�瑕佹樉绀虹殑淇℃伅涓嶅悓  灏�*鏇挎崲鎴愪笉鍚屽睘鎬э紝杩樻槸灏佽鎴愪竴涓被CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (Rentablestop)query.uniqueResult();
	}
	//根据bookid返回书的图片地址
	@Override
	public String getNoPicturePath(int bookid) {
		String hql = "select r.picture from Rentable r where r.id =?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, bookid);
		return (String) query.uniqueResult();
	}
	@Override
	public void deleteFormerbyId(int bookid) {
		sessionFactory.getCurrentSession().delete(new Former(bookid));
	}
	@Override
	public String getBookName(String isbn) {
		String hql = "select title from Isbn where isbn = ?";
		return (String) sessionFactory.getCurrentSession().createQuery(hql).setString(0, isbn).uniqueResult();
	}
}