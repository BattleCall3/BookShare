package com.lq.dao;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.User;
import com.lq.other.notConfirmPhone;
@Repository
public class UserDaoImpl implements UserDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public boolean delUser(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getALLUser() {
		// TODO Auto-generated method stub
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);		
		return query.list();
	}

	@Override
	public boolean updateUserPhone(String userid, String phone) {
		// TODO Auto-generated method stub
		String hql = "update User u set u.phone=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, phone);
		query.setString(1, userid);
		return (query.executeUpdate()>0);
	}

	@Override
	public boolean updateUserInfo(String userid, String phone, String grade,
			String sex) {
		// TODO Auto-generated method stub
		String hql = "update User u set u.grade=?,u.phone=?,u.sex=?where u.id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, grade);
		query.setString(1, phone);
		query.setString(2, sex);
		query.setString(3, userid);
		return (query.executeUpdate()>0);
	}

	@Override
	public User getOneUserInfo(String userid) {
		// TODO Auto-generated method stub
		String hql = "FROM User u Where u.id=? ";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return (User) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<notConfirmPhone> getMyRentPhone(List<Integer> books) {
		String hql = "select new notConfirmPhone(r.id, u.phone) from Rentable r, User u where "
				+ "r.id in :booklist and (r.way=1 or r.way=3) and u.id in r.origin_openid";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameterList("booklist", books);
		return query.list();
	}
}
