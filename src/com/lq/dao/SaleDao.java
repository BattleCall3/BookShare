package com.lq.dao;

import java.util.List;

import com.lq.entity.Sale;
import com.lq.other.notConfirmPhone;

public interface SaleDao {

	boolean updateSale(int bookid, long start_time, int sureornot);
	List<Sale> getSalewithoutConfirm(List<Integer> books);
	Sale getOneSale(int bookid);
	boolean dealConfirm(int bookid, int sureornot);
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books);
}
