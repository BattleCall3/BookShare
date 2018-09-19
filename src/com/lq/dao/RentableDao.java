package com.lq.dao;
import java.math.BigDecimal;
import java.util.List;

import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.Rentablestop;
import com.lq.other.PartRentable;
public interface RentableDao {
	public void addRentable(Rentable rentable);
	public boolean moveRentable(int bookid);
	public boolean moveRentabletoSale(int bookid);
	public boolean delRentable(int index);
	public List<Rentable> getRentables(int startlocation, int size);
	public Rentable getOneRentable(int id);
	public List<PartRentable> getPartRentables(int startlocation, int size);
	public boolean updateRentable(int id,String picture);
	public Isbn getBookInfo(String isbn);
	public boolean backRentable(int bookid, String tablename);
	public boolean updateRentableWay(int bookid,int way);
	public int getGeneratorId();
	public boolean updateGeneratorId(int id);
	public List<Rentable> getRentableWayBelowZero(String userid);
	public boolean moveRentabletoStop(int bookid);
	public boolean moveRentableFromStop(int bookid);
	public boolean updateRentableInfo(int bookid, String picPath,BigDecimal rent_price, BigDecimal sale_price, int way);
	public long getRentableLen();
	public boolean updateRentableWayToMinus(int bookid);
	public Rentablestop getOneRentableStop(int bookid);
	public String getNoPicturePath(int bookid);
	public void deleteFormerbyId(int bookid);
}
