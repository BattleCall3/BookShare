package com.lq.service;
import java.util.List;

import com.lq.entity.Rented;
import com.lq.other.notConfirmPhone;
public interface RentedService {
	public boolean moveToWorthless(int index);
	public List<Rented> getRented(String userid);
	public Rented getOneRented(int id);
	public boolean dealConfirm(int bookid, int sureornot);
	public boolean updateRented(int bookid,long begin_time,long end_time,int sureornot);
	public List<Rented> getRentedwithoutConfirm(List<Integer> books);
	public boolean updateRented(int bookid,long begin_time,long end_time,int sureornot,String picture);
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books);
}
