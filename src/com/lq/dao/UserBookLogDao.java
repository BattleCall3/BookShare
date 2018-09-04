package com.lq.dao;
import java.util.List;

import com.lq.entity.TradeLog;
public interface UserBookLogDao {
	
	List<TradeLog> getlogsByuserandbookid(String userid, int bookid);
	public void addlogandformer(TradeLog tradeLog, String origin_openid, int bookid);
	boolean movetoFaillog(int logid);
	List<Integer> getBooksfromFormer(String userid);
}
