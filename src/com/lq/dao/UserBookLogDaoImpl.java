package com.lq.dao;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.lq.entity.FailTradeLog;
import com.lq.entity.Former;
import com.lq.entity.TradeLog;
@Repository
public class UserBookLogDaoImpl implements UserBookLogDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeLog> getlogsByuserandbookid(String userid, int bookid) {
		// TODO Auto-generated method stub
		String hql = "FROM TradeLog u Where u.id in(select t.logid from Former t where t.userid=? and t.bookid=?) ORDER BY u.id DESC";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		query.setInteger(1, bookid);
		return (List<TradeLog>) query.list();
	}	
	
	@Override
	public void addlogandformer(TradeLog tradeLog,String origin_openid,int bookid) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(tradeLog);
		Former former = new Former(bookid,origin_openid,tradeLog.getId());
		sessionFactory.getCurrentSession().save(former);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getBooksfromFormer(String userid) {
		// TODO Auto-generated method stub
		String hql = "SELECT DISTINCT bookid FROM Former  Where userid=?";
		// 根据需要显示的信息不同  将*替换成不同属性，还是封装成一个类CommonInfo
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		return query.list();
	}
	
	@Override
	public boolean movetoFaillog(int logid) {
		// TODO Auto-generated method stub
		String hql = "select new FailTradeLog(u.id,u.deal_time,u.period,u.way,u.manA,u.manB,u.money) from TradeLog u where u.id=?";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, logid);
    	//有一些问题，字段不是全部匹配上的，
    	sessionFactory.getCurrentSession().save((FailTradeLog)query.uniqueResult());
    	hql = "delete TradeLog u where u.id = ?";
    	query = sessionFactory.getCurrentSession().createQuery(hql);
    	query.setInteger(0, logid);
    	return (query.executeUpdate()>0);
	}
}
