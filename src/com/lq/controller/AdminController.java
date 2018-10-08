package com.lq.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lq.entity.Isbn;
import com.lq.service.AdminService;
import com.lq.service.IsbnService;
import com.util.Utils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService aService;
	@Autowired
	private IsbnService isbnService;
	
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
	//没有书籍信息的，尝试请求官网将isbn表数据信息补全
	@RequestMapping("/completebookinfo")
	public void completeBookinfo(String isbn, HttpServletResponse response) {
		String afandaUrl = "https://api.avatardata.cn/BookInfo/FindByIsbn?key=9bb781070f8d453f979300897dffb279&isbn=" + isbn;
		JSONObject json = JSONObject.parseObject(Utils.doGet(afandaUrl));
		int error_code = json.getIntValue("error_code");
		String data = "";
		if(error_code == 0) {
			JSONObject result = json.getJSONObject("result");
			String title = result.getString("title");
			String subtitle = result.getString("subtitle");
			JSONObject images = result.getJSONObject("images");
			String picture = images.getString("small");
			Utils.downPicture(picture);
			JSONArray authors = result.getJSONArray("author");
			String author = "";
			int authorSize = authors.size();
			if(authorSize > 0) {
				author = authors.getString(0);
				for(int i=1; i<authorSize-1; i++) {
					author += ",";
					author += authors.getString(i);
				}
				author += authors.getString(authorSize-1);
			}
			String summary = result.getString("summary");
			String publisher = result.getString("publisher");
			String pubdate = result.getString("pubdate");
			String pages = result.getString("pages");
			String price = result.getString("price");
			String binding = result.getString("binding");
			String isbn10 = result.getString("Isbn10");
			JSONArray tags = result.getJSONArray("tags");
			String keyword = "";
			int tagsSize = tags.size();
			if(tagsSize > 0) {
				keyword += tags.getJSONObject(0).getString("name");
				for(int i=1; i<tagsSize-1; i++) {
					keyword += ",";
					keyword += tags.getJSONObject(i).getString("name");
				}
				keyword += tags.getJSONObject(tagsSize-1).getString("name");
			}
			Isbn newIsbn = new Isbn(isbn, title, subtitle, picture, author, 
					summary, publisher, pubdate, pages, price, binding, isbn10, keyword);
			isbnService.addIsbnInfor(newIsbn);
			data = "{\"code\":"+200+"}";
		}else if(error_code == 1) {
			data = "{\"code\":"+404+"}";
		}else if(error_code == 10014 || error_code == 10015) {
			data = "{\"code\":"+500+"}";
		}
		try{
			PrintWriter out = response.getWriter();
			out.write(data);
			out.close();
		}catch(IOException e){
			e.printStackTrace();				
		}
	}
	
}
