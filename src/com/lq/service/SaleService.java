package com.lq.service;
import java.util.List;

import com.lq.entity.Sale;
import com.lq.other.notConfirmPhone;
public interface SaleService {
	public boolean dealCancel(int bookid);
	public boolean moveToWorthless(int bookid);
	public List<Sale> getSale(String userid);
	public Sale getOneSale(int bookid);
	public boolean dealConfirm(int bookid, int sureornot);
	public boolean updateSale(int bookid,long start_time,int sureornot);
	public List<Sale> getSalewithoutConfirm(List<Integer> books);
	public List<notConfirmPhone> getNotConfirmPhone(List<Integer> books);
}
