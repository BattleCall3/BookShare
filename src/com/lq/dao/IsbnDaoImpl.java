package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import com.lq.entity.BookSort;
import com.lq.entity.Isbn;
import com.lq.other.PartRentable;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
@Repository
public class IsbnDaoImpl implements IsbnDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void addIsbn(Isbn isbninfo) {
		sessionFactory.getCurrentSession().save(isbninfo);
	}
	@Override
	public Isbn getOneIsbninfor(String isbn) {
		String hql = "FROM Isbn u Where u.isbn=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, isbn);
		return (Isbn) query.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInfo(String keyword) {
		// TODO Auto-generated method stub 
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information in( select isbn "
				+ "from Isbn where  title like '%"+keyword+"%' or subtitle like '%"+keyword+"%' or author like '%"+keyword+"%' or publisher like '%"+keyword+"%' or keyword like '%"+keyword+"%' )";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInCore(String keyword) {
		// TODO Auto-generated method stub
		String hql= "select new PartRentable(u.id,t.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information in( "
				+ "select isbn from Isbn where title like :key or subtitle like :key or author like :key or publisher like :key or keyword like :key)"
				+ "order by length(t.title),t.author,t.pubdate,u.way";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("key", "%"+keyword+"%");
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchInfoByTwokey(String keyword,String keyword2){
		// TODO Auto-generated method stub
		String hql= "select new PartRentable(u.id,t.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information in( "
				+ "select isbn from Isbn where (title like :key and author like :key2) or (author like :key  and publisher like :key2) or (keyword like :key and  like :key2) "
				+ "order by t.title,t.author,t.pubdate,u.way";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("key", "%"+keyword+"%");
		query.setParameter("key2", "%"+keyword2+"%");
		return query.list();
	}
	
	/*
	 * 以下是未完成的扩展检索方法
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSearchIsbn(String keyword) {
		// TODO Auto-generated method stub
		String hql = "select isbn from Isbn where title like :key or subtitle like :key or author like :key or publisher like :key or keyword like :key "
				+ "order by length(title) ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("key", "%"+keyword+"%");
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchOrderByKeys(List<String> isbns) {
		// TODO Auto-generated method stub
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = t.isbn and u.information = (:keys)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("keys", isbns);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> getSearchOrderByKey(String isbnstr) {
		// TODO Auto-generated method stub
		String hql= "select new PartRentable(u.id,u.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u,Isbn t where u.information = (:keys)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("keys", isbnstr);
		return query.list();
	}
	//修改图书名称
	@Override
	public boolean alternoBookinfo(String isbn, String title, String publisher, String author, String price) {
		String hql = "update Isbn i set i.title=?, i.publisher=?, i.author=?, i.price=? where i.isbn=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, title);
		query.setString(1, publisher);
		query.setString(2, author);
		query.setString(3, price);
		query.setString(4, price);
		return (query.executeUpdate()>0);
	}
	@Override
	public void addBookSort(BookSort booksort) {
		sessionFactory.getCurrentSession().save(booksort);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchIsbn0(String keyword) {
		String hql = "select isbn from Isbn where title like :key or subtitle like :key or author like :key or publisher like :key or keyword like :key";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("key", "%"+keyword+"%");
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchInIsbn(String keyword, List<String> isbns) {
		String hql = "select isbn from Isbn where isbn in (:isbns) and "
				+ "title like :key or subtitle like :key or author like :key or publisher like :key or keyword like :key";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("isbns", isbns);
		query.setParameter("key", "%"+keyword+"%");
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PartRentable> searchBookbyIsbn(int startlocation, int size, List<String> isbns) {
		String hql = "select new PartRentable(u.id,t.picture,u.way,u.rent_price,u.sale_price,t.title) from Rentable u, Isbn t "
				+ "where u.information = t.isbn and t.isbn in (:isbns) ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("isbns", isbns);
		query.setFirstResult(startlocation);
		query.setMaxResults(size);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getIsbnbySort(int label1, int label2, int label3) {
		String hql = "select isbn from BookSort where label1=? and label2=? and label3=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger(0, label1);
		query.setInteger(1, label2);
		query.setInteger(2, label3);
		return query.list();
	}
	@Override
	public long bookNumberbyIsbn(List<String> isbns) {
		String hql = "select count(*) from Rentable where information in (:isbns)";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("isbns", isbns);
		return (long) query.uniqueResult();
	}
}