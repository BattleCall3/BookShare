package com.lq.dao;
import java.util.List;
import java.util.Map;

import com.lq.entity.User;
import com.lq.other.notConfirmPhone;
public interface UserDao {
	public void addUser(User user);
	public boolean delUser(String userid);
	public List<User> getALLUser();
	public boolean updateUserPhone(String userid,String phone);
	public boolean updateUserInfo(String userid,String phone,String grade,String sex);
	public User getOneUserInfo(String userid);
	public List<notConfirmPhone> getMyRentPhone(List<Integer> books);
	public Map<String, String> welcomeInfo(int title, int text);
}

