package com.lq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lq.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService aService;
	/*
	 * 添加要显示的欢迎信息
	 * 修改要显示的信息
	 * 1--title
	 * 2--text
	 */
	@RequestMapping("/addWelcomeInfo")
	public void addWelcomeInfo(String text) {
		aService.addWelcomeInfo(text);
	}
	@RequestMapping("/alterWelcomeInfo")
	public void alterWelcomeInfo(int number, String text) {
		aService.alterWelcomeInfo(number, text);
	}
	
}
