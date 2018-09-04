package com.lq.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.dao.RentedDao;
import com.lq.entity.Rented;
import com.lq.other.notConfirmPhone;
@Service
public class RentedServiceImpl implements RentedService{

	@Autowired
	private RentedDao rentedDao;
	@Override
	public boolean moveToWorthless(int index) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Rented> getRented(String userid) {
		// TODO Auto-generated method stub
		return rentedDao.getRented(userid);
	}
	@Override
	public Rented getOneRented(int id) {
		// TODO Auto-generated method stub
		return rentedDao.getOneRented(id);
	}
	@Override
	public boolean dealConfirm( int bookid,int sureornot) {
		// TODO Auto-generated method stub
		return rentedDao.dealConfirm(bookid,sureornot);
	}
	@Override
	public boolean updateRented(int bookid,long begin_time, long end_time,int sureornot) {
		// TODO Auto-generated method stub
		return rentedDao.updateRented(bookid,begin_time,end_time,sureornot);
	}
	@Override
	public List<Rented> getRentedwithoutConfirm(List<Integer> books) {
		// TODO Auto-generated method stub
		return rentedDao.getRentedwithoutConfirm(books);
	}
	@Override
	public boolean updateRented(int bookid,long begin_time, long end_time,int sureornot, String picture) {
		// TODO Auto-generated method stub
		return rentedDao.updateRented(bookid,begin_time,end_time,sureornot,picture);
	}
	@Override
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books) {
		return rentedDao.getNotConfirmPhone(books);
	}
}
