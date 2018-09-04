package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.dao.UserBookDao;
import com.lq.dao.UserBookLogDao;
import com.lq.dao.UserDao;
import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.Sale;
import com.lq.entity.TradeLog;
import com.lq.entity.User;
import com.lq.other.notConfirmPhone;
@Service
public class UserServiceImpl implements UserService{
	@Autowired 
	private UserDao userDao;
	@Autowired 
	private UserBookDao userbookDao;
	@Autowired 
	private UserBookLogDao userbooklogDao;
	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userDao.addUser(user);
	}

	@Override
	public boolean delUser(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getALLUser() {
		// TODO Auto-generated method stub
		return userDao.getALLUser();
	}

	@Override
	public boolean updateUserPhone(String userid, String phone) {
		// TODO Auto-generated method stub
		return userDao.updateUserPhone(userid,phone);
	}

	@Override
	public boolean updateUserInfo(String userid, String phone, String grade,
			String sex) {
		// TODO Auto-generated method stub
		return userDao.updateUserInfo(userid, phone, grade, sex);
	}

	@Override
	public User getOneUserInfo(String userid) {
		// TODO Auto-generated method stub
		return userDao.getOneUserInfo(userid);
		//如果找不到此用户，则返回null
	}

	@Override
	public void addBookOwner(BookOwner bookowner) {
		// TODO Auto-generated method stub
		userbookDao.addBookOwner(bookowner);
	}

	@Override
	public boolean resumeBookOwner(int bookid,String lastuserid) {
		// TODO Auto-generated method stub
		return userbookDao.resumeBookOwner(lastuserid,bookid);
	}
	@Override
	public boolean updateBookOwner(String userid, int bookid,int logid) {
		// TODO Auto-generated method stub
		return userbookDao.updateBookOwner(userid, bookid,logid);
	}

	@Override
	public BookOwner getBookOwner(int bookid) {
		// TODO Auto-generated method stub
		return userbookDao.getBookOwner(bookid);
	}
	

	@Override
	public void addlogandformer( TradeLog tradeLog,String origin_openid,int bookid) {
		// TODO Auto-generated method stub
		userbooklogDao.addlogandformer(tradeLog, origin_openid, bookid);
	}
	
	@Override
	public List<Sale> getAllSale(String userid) {
		// TODO Auto-generated method stub
		return userbookDao.getAllSale(userid);
	}

	@Override
	public List<Rented> getAllRented(String userid) {
		// TODO Auto-generated method stub
		return userbookDao.getAllRented(userid);
	}

	@Override
	public List<Rentable> getAllOutDate(String userid) {
		// TODO Auto-generated method stub
		return userbookDao.getAllOutDate(userid);
	}

	@Override
	public List<Rentable> getAllRentable(String userid) {
		// TODO Auto-generated method stub
		return userbookDao.getAllRentable(userid);
	}

	@Override
	public List<TradeLog> getlogsByuserandbookid(String userid, int bookid) {
		// TODO Auto-generated method stub
		return userbooklogDao.getlogsByuserandbookid(userid,bookid);
	}

	@Override
	public boolean delBookOwner(int bookid) {
		// TODO Auto-generated method stub
		return userbookDao.delBookOwner(bookid);
	}

	@Override
	public List<Integer> getBooksfromBookOwner(String userid) {
		// TODO Auto-generated method stub
		return userbookDao.getBooksfromBookOwner(userid);
	}

	@Override
	public TradeLog getLogByid(int logid) {
		// TODO Auto-generated method stub
		return userbookDao.getLogByid(logid);
	}

	@Override
	public boolean movetoFaillog(int logid) {
		// TODO Auto-generated method stub
		return userbooklogDao.movetoFaillog(logid);
	}

	@Override
	public List<Integer> getBooksfromFormer(String userid) {
		// TODO Auto-generated method stub
		return userbooklogDao.getBooksfromFormer(userid);
	}

	@Override
	public List<notConfirmPhone> getMyRentPhone(List<Integer> books) {
		return userDao.getMyRentPhone(books);
	}

	@Override
	public List<notConfirmPhone> getBookTitle(String userid) {
		return userbookDao.getBookTitle(userid);
	}
	
}
