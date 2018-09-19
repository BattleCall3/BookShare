package com.lq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.dao.AdminDao;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminDao aDao;
	@Override
	public void addWelcomeInfo(String text) {
		aDao.addWelcomeInfo(text);
	}
	@Override
	public void alterWelcomeInfo(int number, String text) {
		aDao.alterWelcomeInfo(number, text);
	}

}
