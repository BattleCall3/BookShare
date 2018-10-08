package com.lq.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.lq.entity.FormId;
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
	
	@Override
	public Map<String, String> welcomeInfo(int title, int text) {
		Map<String, String> welcomeMap = new HashMap<String, String>();
		String hql = "select text from A_Welcome where number = ?";
		Session session = sessionFactory.openSession();
		String resultTitle = (String) session.createQuery(hql).setInteger(0, title).uniqueResult();
		String resultText = (String) session.createQuery(hql).setInteger(0, text).uniqueResult();
		session.flush();//执不执行都可以
		session.clear();
		session.close();
		welcomeMap.put("title", resultTitle);
		welcomeMap.put("text", resultText);
		return welcomeMap;
	}

	@Override
	public void saveFormid(FormId formid) {
		Session session = sessionFactory.openSession();
		session.save(formid);
		String sql = "delete from t_formid where userid = ? and timestampdiff(DAY, date, current_timestamp) > 6";
		session.createSQLQuery(sql).setString(0, formid.getUserid()).executeUpdate();
		session.clear();
		session.close();
	}

	@Override
	public String getFormid(String userid) {
		String hql = "select formid from FormId where userid = ? and timestampdiff(DAY, date, current_timestamp) <7 order by id asc";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setString(0, userid);
		query.setMaxResults(1);
		String formid = (String) query.uniqueResult();
		hql = "delete from FormId where userid = ? and formid = ?";
		query = session.createQuery(hql);
		query.setString(0, userid);
		query.setString(1, formid);
		query.executeUpdate();
		session.clear();
		session.close();
		return formid;
	}

	@Override
	public String getUserPhone(String userid) {
		String hql = "select phone from User where id = ?";
		return (String) sessionFactory.getCurrentSession().createQuery(hql).setString(0, userid).uniqueResult();
	}

}
