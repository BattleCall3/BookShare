package com.lq.controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
/*			response.setContentType("application/json");
			String data = "{\"result\":\"exist\",\"bookid\":"+id+"}";	
			if(isbnService.getOneIsbninfor(information)==null){	
				data = "{\"isbn\":\""+information+"\",\"bookid\":"+id+"}";	
			}			  
*/			
			/*
			 * 2018.9.21
			 * bc：后端请求阿凡达数据，给前端返回是否请求到图书信息
			 * 	正常--200
			 *	没有找到书--404
			 * 	阿凡达数据官方接口维护/停用--500
			 */
			if(isbnService.getOneIsbninfor(information)==null) {
				String dataStr = afandaBook(information);
				if(dataStr != "") {
					JSONObject json = JSONObject.parseObject(dataStr);
					int error_code = json.getIntValue("error_code");
					if(error_code == 0) {
						String data = "{\"code\":"+200+", \"bookid\":"+id+"}";
						writeCode(response, data);
						JSONObject result = json.getJSONObject("result");
						String title = result.getString("title");
						String subtitle = result.getString("subtitle");
						JSONObject images = result.getJSONObject("images");
						String picture = images.getString("small");
						downPicture(picture);
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
						Isbn newIsbn = new Isbn(information, title, subtitle, picture, author, 
								summary, publisher, pubdate, pages, price, binding, isbn10, keyword);
						isbnService.addIsbnInfor(newIsbn);
					}else if(error_code == 1) {
						String data = "{\"code\":"+404+", \"bookid\":"+id+"}";
						writeCode(response, data);
						saveNoInfoIsbn(information);
					}else if(error_code == 10014 || error_code == 10015) {
						String data = "{\"code\":"+500+", \"bookid\":"+id+"}";
						writeCode(response, data);
						saveNoInfoIsbn(information);
					}
				}else {
					String data = "{\"code\":"+404+", \"bookid\":"+id+"}";
					writeCode(response, data);
					saveNoInfoIsbn(information);
					
				}
			}else if(isbnService.getOneIsbninfor(information).getTitle() == "自定义书籍"){
				String data = "{\"code\":"+404+", \"bookid\":"+id+"}";
				writeCode(response, data);
				saveNoInfoIsbn(information);
			}
		}
		//写isbn到文件
		public void writeIsbntoTxt(String isbn) {
			String filePath = Valuable.getIsbnnoinfofile();
			File file = new File(filePath);
			if(!file.isFile())
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			try {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				bw.write("time:"+df.format(date)+"\r\n");
				bw.write("isbn:"+isbn+"\r\n");
				bw.flush();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//请求阿凡达数据
		public String afandaBook(String isbn) {
			String afandaUrl = "https://api.avatardata.cn/BookInfo/FindByIsbn?key=9bb781070f8d453f979300897dffb279&isbn=" + isbn;
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(afandaUrl);
			RequestConfig config = RequestConfig.custom().setConnectTimeout(8000).setSocketTimeout(20000).build();
			httpGet.setConfig(config);
			CloseableHttpResponse response = null;
			String result = "";
			try {
				response = httpClient.execute(httpGet);
				if(response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(response != null)
					try {
						response.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		public void writeCode(HttpServletResponse response, String data) {
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author bc
		 * 2018.9.20
		 * 增加方法：将阿凡达api图片下载到服务器上
		 * url:https://api.avatardata.cn/BookInfo/Img?file=b5cbcc228e8d4f51b239a6662b879e3d.jpg
		 * 截取后图片名称：b5cbcc228e8d4f51b239a6662b879e3d.jpg
		 */
		public void downPicture(String pictureUrl) {
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
		}
		//存储阿凡达没有数据的图书，信息自定义。
		public void saveNoInfoIsbn(String isbn) {
			String title = "自定义书籍";
			String subtitle = "未知";
			String picture = "";
			String author = "未知";
			String summary = "未知";
			String publisher = "未知";
			String pubdate = "未知";
			String page = "未知";
			String price = "未知";
			String binding = "未知";
			String isbn10 = "未知";
			String keyword = "未知";
			Isbn newIsbn = new Isbn(isbn, title, subtitle, picture, author, 
					summary, publisher, pubdate, page, price, binding, isbn10, keyword);
			isbnService.addIsbnInfor(newIsbn);
		}
		@RequestMapping("/saveisbn")
	    public void saveisbn(Isbn isbninfo){
			isbnService.addIsbnInfor(isbninfo);
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
		public void alternoBookinfo(String isbn, String title, String publisher, String author, String price, HttpServletRequest request, HttpServletResponse response){
			isbnService.alternoBookinfo(isbn, title, publisher, author, price);
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