package com.lq.service;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.Rentablestop;
import com.lq.other.PartRentable;
import com.lq.dao.RentableDao;
@Service
public class RentableServiceImpl implements RentableService{

	@Autowired
	private RentableDao rentableDao;
	@Override	public void addRentable(Rentable rentable) {
		// TODO Auto-generated method stub
		rentableDao.addRentable(rentable);
	}
	@Override
	public boolean delRentable(int index) {
		// TODO Auto-generated method stub
		return rentableDao.delRentable(index);
	}
	@Override
	public boolean updateRentable(int id,String picture) {
		// TODO Auto-generated method stub
		return rentableDao.updateRentable(id,picture);
	}
	@Override
	public List<Rentable> getRentables(int startlocation, int size) {
		// TODO Auto-generated method stub
		return rentableDao.getRentables(startlocation, size);
	}
	@Override
	public List<PartRentable> getPartRentables(int startlocation, int size) {
		// TODO Auto-generated method stub
		return rentableDao.getPartRentables(startlocation, size);
	}
	@Override
	public Rentable getOneRentable(int id) {
		// TODO Auto-generated method stub
		return rentableDao.getOneRentable(id);
	}
	@Override
	public boolean moveRentable(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentable(bookid);
	}
	@Override
	public boolean moveRentabletoSale(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentabletoSale(bookid);
	}
	@Override
	public boolean backRentable(int bookid,String tablename) {
		// TODO Auto-generated method stub
		return rentableDao.backRentable(bookid,tablename);
	}
	
	@Override
	public Isbn getBookInfo(String isbn) {
		// TODO Auto-generated method stub
		return rentableDao.getBookInfo(isbn);
	}
	@Override
	public boolean updateRentableWay(int bookid,int way) {
		// TODO Auto-generated method stub
		return rentableDao.updateRentableWay(bookid,way);
	}
	@Override
	public int getGeneratorId() {
		// TODO Auto-generated method stub
		return rentableDao.getGeneratorId();
	}
	@Override
	public boolean updateGeneratorId(int id) {
		// TODO Auto-generated method stub
		return rentableDao.updateGeneratorId(id);
	}
	@Override
	public List<Rentable> getRentableWayBelowZero(String userid) {
		// TODO Auto-generated method stub
		return rentableDao.getRentableWayBelowZero(userid);
	}
	@Override
	public boolean moveRentabletoStop(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentabletoStop(bookid);
	}
	@Override
	public boolean moveRentableFromStop(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.moveRentableFromStop(bookid);
	}
	@Override
	public boolean updateRentableInfo(int bookid, String picPath,
			BigDecimal rent_price, BigDecimal sale_price, int way) {
		// TODO Auto-generated method stub
		return rentableDao.updateRentableInfo(bookid,picPath,rent_price,sale_price,way);
	}
	@Override
	public long getRentableLen() {
		// TODO Auto-generated method stub
		return rentableDao.getRentableLen();
	}
	@Override
	public boolean updateRentableWayToMinus(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.updateRentableWayToMinus(bookid);
	}
	@Override
	public Rentablestop getOneRentableStop(int bookid) {
		// TODO Auto-generated method stub
		return rentableDao.getOneRentableStop(bookid);
	}
	@Override
	public String getNoPicturePath(int bookid) {
		return rentableDao.getNoPicturePath(bookid);
	}
	@Override
	public void deleteFormerbyId(int bookid) {
		rentableDao.deleteFormerbyId(bookid);
	}
}
