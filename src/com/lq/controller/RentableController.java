package com.lq.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lq.entity.BookOwner;
import com.lq.entity.BookSort;
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.entity.User;
import com.lq.service.IsbnService;
import com.lq.service.RentableService;
import com.lq.service.UserService;
import com.util.Valuable;
/* author 	lmr
 * time		2017/11/29 19:34
 * function	增加了书籍的注册
 * */
@Controller
@RequestMapping("/rentable")
public class RentableController{
		@Autowired
		private RentableService rentableService;
		@Autowired
		private UserService userService;
		@Autowired
		private IsbnService isbnService;
		@RequestMapping("/bookapplication")
	    public void bookapplication(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
			request.setCharacterEncoding("utf-8");
			/* 2017/12/18
			 * 随同书信息一起上传的用户ID,检查该用户是否已经注册
			 * 若已经注册过，更新该用户的手机号码
			 * 若没有注册过，注册该用户，补充该用户的手机号
			 * */
			String userid = request.getParameter("userid");
			String origin_tel = request.getParameter("origin_tel");
			if(userService.getOneUserInfo(userid)==null){
				User user = new User();
				user.setId(userid);
				user.setPhone(origin_tel);
				userService.addUser(user);
			}
			else {
				userService.updateUserPhone(userid, origin_tel);
			}
			/*2017/12/18
			 * 开始注册图书信息
			 * */
			boolean rentbtn = Boolean.parseBoolean(request.getParameter("rentbtn"));
			boolean sellbtn = Boolean.parseBoolean(request.getParameter("sellbtn"));
			String  onlycode= request.getParameter("onlycode");
			/* 图片远程地址处理	过滤图片请求,不拦截图片URL
			 * similar-> "http://api.jisuapi.com/isbn/upload/20161010/174050_28792.jpg"
			 * */
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName 	  = "OpenId"+onlycode +filetype;//onlycode 作为图片名字一部分
			//String ip         = "http://115.159.24.14/";
			//String ip = "http://192.168.3.140:8082/";
			String path       = Valuable.getIp()+"image/";//就有一个问题图片的路径怎么对应上URL
			String picPath    = path+picName;
			String information= request.getParameter("isbn");
			String rent_price = request.getParameter("rent_price");
			String sale_price = request.getParameter("sale_price");	
			long   start_time = System.currentTimeMillis();
			/* 读取交易方式
			 * 只可出租 way = 1 
			 * 只可卖     way = 2
			 * 可租可卖 way = 3
			 * */
			int way = 0;
			if(sellbtn && rentbtn)	way = 3;
			else if(sellbtn)		way = 2;
			else if(rentbtn)	    way = 1;
			else 					way = 0;
			/* addRentableandOwner
			 * */
			Rentable rentable = new Rentable();
			rentable.setPicture(picPath);
			rentable.setInformation(information);
			rentable.setOrigin_openid(userid);
			rentable.setRent_price(new BigDecimal(rent_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setSale_price(new BigDecimal(sale_price).setScale(1, BigDecimal.ROUND_HALF_UP));
			rentable.setStart_time(start_time);
			rentable.setWay(way);
			int id = rentableService.getGeneratorId();
//			System.out.println(id);
			rentableService.updateGeneratorId(id);
			rentable.setId(id);
			rentableService.addRentable(rentable);
			/*2017/12/18
			 * 图书信息注册完成
			 * */
			/*2017/12/18
			 * 书——用户关系表，即所有者表的注册，采用复合主码
			 * 是否能取到书序号
			 * */
			userService.addBookOwner(new BookOwner(rentable.getId(),userid,0));
			/* 向数据库isbn表查询是否有存在该书的信息
			 * */
			response.setContentType("application/json");
			String data = "{\"result\":\"exist\",\"bookid\":"+id+"}";	
			if(isbnService.getOneIsbninfor(information)==null){	
				data = "{\"isbn\":\""+information+"\",\"bookid\":"+id+"}";	
			}			  
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		@RequestMapping("/saveisbn")
		@ResponseBody
	    public int saveisbn(Isbn isbninfo){
			isbnService.addIsbnInfor(isbninfo);
			/*
			 * author bc
			 * 2018.9.20
			 * 增加方法：将阿凡达api图片下载到服务器上
			 * url:https://api.avatardata.cn/BookInfo/Img?file=b5cbcc228e8d4f51b239a6662b879e3d.jpg
			 * 截取后图片名称：b5cbcc228e8d4f51b239a6662b879e3d.jpg
			 */
			String pictureUrl = isbninfo.getPicture();
			String picturePath = Valuable.getAfandapicturepath();
			String pictureName = pictureUrl.split("=")[1];
			if(pictureUrl.split(":")[0].equals("http"))
				pictureUrl = pictureUrl.replaceAll("http", "https");
			try {
				URL url = new URL(pictureUrl);
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(10000);
				File file = new File(picturePath);
				if(!file.exists())
					file.mkdir();
				InputStream is = conn.getInputStream();
				byte[] b = new byte[1024];
				int len;
				OutputStream os = new FileOutputStream(picturePath+"/"+pictureName);
				while((len=is.read(b))!=-1)
					os.write(b, 0, len);
				os.close();
				is.close();
 			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 200;
		}
		/* author 	lmr
		 * time		2017/12/22
		 * function 用户撤回上传的仍在自己手上的图书，删除持有者关系记录，删除书表记录
		 * */
		@RequestMapping("/cancel")
	    public void cacelregister(int bookid,String userid,HttpServletRequest request,HttpServletResponse response) {
			String data = "{\"result\":\"book does't exist\"}";
			System.out.println(bookid+" : "+userid);
			if(rentableService.getOneRentable(bookid)!=null){
				userService.delBookOwner(bookid);
				rentableService.delRentable(bookid);
				String rentable = JSON.toJSONString(userService.getAllRentable(userid));
				response.setContentType("application/json");
				data = "{\"rentable\":"+ rentable +"}";	

			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		//修改自定义书籍信息
		@RequestMapping("/alternobook")
		public void alternoBookinfo(String isbn, String title, String publisher, String author, HttpServletRequest request, HttpServletResponse response){
			isbnService.alternoBookinfo(isbn, title, publisher, author);
		}
		/*
		 * bc
		 * 增加图书分类
		 */
		@RequestMapping(value = "classifyTextbook", method = RequestMethod.POST)
		@ResponseBody
		public Integer classifyTextbook(@RequestBody(required = false) JSONObject jsonObject) {
			int bookid = jsonObject.getInteger("bookid");
			String isbn = jsonObject.getString("isbn");
			JSONArray arr = jsonObject.getJSONArray("sort");
			BookSort booksort = new BookSort(bookid, isbn, arr.getInteger(0), arr.getInteger(1), arr.getInteger(2));
			isbnService.addBookSort(booksort);
			return 200;
		}
}