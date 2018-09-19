package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lq.entity.BookOwner;
import com.lq.entity.Rentable;
import com.lq.entity.Rented;
import com.lq.entity.TradeLog;
import com.lq.entity.User;
import com.lq.service.RentableService;
import com.lq.service.RentedService;
import com.lq.service.SaleService;
import com.lq.service.UserService;
import com.util.Valuable;
@Controller
@RequestMapping("/bookdeal")
/*author lmr
 * time   2017/12/19
 * function 用户买书或者租书时，书在表间的移动
 * 书的移动从可租可借调到已租或已借表 
 * 书当前持有者关系变更
 * * 若是租书period在设定的区间内，建议1-180天
 * 若是买书period恒为0，表示永久
 * */
public class BookDealController{
		private static final int Rent = 1;
		@Autowired
		private RentableService rentableService;
		@Autowired
		private RentedService rentedService;
		@Autowired
		private SaleService saleService;
		@Autowired
		private UserService userService;
			
		/*测试表移动回去的时候，主键是否还是原来的id
		 * 实验结果是否，会取用自动生成的id
		 * */
		@RequestMapping("/test")
	    public void testtrade(HttpServletRequest request,ServletResponse response) {
			Rentable rentable = new Rentable();
			int id = rentableService.getGeneratorId();
			rentableService.updateGeneratorId(id);
			rentable.setId(id);
			rentableService.addRentable(rentable);
		}
		/* author 	lmr
		 * time		2017/12/20 18:58
		 * function 发生交易表的移动和日志的生成
		 * 用户买/租用图书 
		 * */
		@RequestMapping("/trade")
	    public void maketrade(int bookid,String userid,boolean rorbtn,int period,HttpServletRequest request,ServletResponse response) {
			
			int sureornot = 0;
			int now_way = 0;
			long begin_time = System.currentTimeMillis();
			long end_time = System.currentTimeMillis()+period*24*60*60*1000;
			Rentable rentable = rentableService.getOneRentable(bookid);
			BigDecimal monney = null;
			String data = "{\"result\":\"badrequest\"}";	
			if(rentable!=null){
				int way = rentable.getWay();
				data = "{\"result\":\"dealwayillegal\"}";	
				if((rorbtn&&(way==1||way==3))||((!rorbtn)&&(way==2||way==3))){
					if(rorbtn){
						now_way = 1;
						rentableService.moveRentable(bookid);
						rentedService.updateRented(bookid,begin_time,end_time,sureornot);
						monney = rentable.getRent_price().multiply(new BigDecimal(period));
					}
					else {
						now_way = 2;
						period = 1024;
						rentableService.moveRentabletoSale(bookid);
						saleService.updateSale(bookid,begin_time,sureornot);
						monney = rentable.getSale_price();
					}
					/*退回去的书没有主人
					 * */
					BookOwner bookOwner = userService.getBookOwner(bookid);
					if(bookOwner!=null){
						TradeLog tradeLog  = new TradeLog(begin_time,period,now_way,bookOwner.getUserid(),userid,monney); 
						userService.addlogandformer(tradeLog,rentable.getOrigin_openid(),bookid);
						userService.updateBookOwner(userid,bookid,tradeLog.getId());
						data = "{\"result\":\"dealsuccess\",\"phone\":"+userService.getOneUserInfo(rentable.getOrigin_openid()).getPhone()+"}";	
					}
				}
			}
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}		
		/* author 	lmr
		 * time		2017/12/20 18:58
		 * function 尚未确认的交易信息显示界面
		 * 用户买/租用图书， sure or not = 0
		 * */
		@RequestMapping("/viewnotconfirm")
	    public void dealnotconfirm(String userid,HttpServletRequest request,HttpServletResponse response) {
			
			response.setContentType("application/json");
			/*获得了用户持有书的序号,下一步去Sale or Rented 表中找sureornot = 0
			 * 提供书的信息包括书名和书的图片
			 * */
			String renteds = null;
			String sales = null;
			String rentedphones = null;
			String salephones = null;
			List<Integer> books = userService.getBooksfromBookOwner(userid);
			if(books.size()!=0){
				renteds = JSON.toJSONString(rentedService.getRentedwithoutConfirm(books));
				sales = JSON.toJSONString(saleService.getSalewithoutConfirm(books));
				rentedphones = JSON.toJSONString(rentedService.getNotConfirmPhone(books));
				salephones = JSON.toJSONString(saleService.getNotConfirmPhone(books));
			}
			String data = "{\"renteds\":"+ renteds +",\"sales\":"+sales+",\"phones\":"+ rentedphones +",\"salephones\":"+salephones+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/20 18:58
		 * function confirm交易确认发生
		 * */
		@RequestMapping("/confirm")
	    public void dealconfirm(int bookid,HttpServletRequest request,ServletResponse response) {
			String data = "{\"result\":\"confirm\"}";	
			response.setContentType("application/json");
			int sureornot = 1;
			rentedService.dealConfirm(bookid,sureornot);
			saleService.dealConfirm(bookid,sureornot);
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/20 18:58
		 * function cancel 交易取消
		 * */
		@RequestMapping("/cancel")
	    public void dealcancel(int bookid,HttpServletRequest request,ServletResponse response) {
			
			String tablename = " Sale";
			TradeLog log = userService.getLogByid(userService.getBookOwner(bookid).getLogid());
			String data = "{\"result\":\"cacelfail logs doesn't exist\"}";	
			if(log !=null){
				userService.movetoFaillog(log.getId());
				userService.resumeBookOwner(bookid,log.getManA());
				if(log.getWay() == Rent)
					tablename  = " Rented";
				rentableService.backRentable(bookid,tablename);
				//删除former表记录
				rentableService.deleteFormerbyId(bookid);
				//rentableService.updateRentableId(bookid);
				data = "{\"result\":\"cacel success\",\"log\":"+JSON.toJSONString(log)+"}";	
			}		
			
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/* author 	lmr
		 * time		2017/12/20 18:58
		 * function 将租期到的图书归还，每天定时触发
		 * */
		@RequestMapping("/rentdue")
	    public void rentdue(HttpServletRequest request,HttpServletResponse response) {
			System.out.println("触发了");
			int status = 0;
			long now_time = System.currentTimeMillis();
			long end_time = 0;
			List<User> users = userService.getALLUser();
			/*不改变书的所有者,只是把书移动回去
			 * status变为1,说明有书到期
			 * */
			if(users!=null){
				for(User user : users){
					List<Rented> renteds =  rentedService.getRented(user.getId());
					System.out.println(user.getId());
					System.out.println(renteds.size());
					if(renteds!=null){
						for(Rented rented : renteds){
							System.out.println(rented.getId()+":"+rented.getEnd_time());
							end_time = rented.getEnd_time();
							if(now_time>end_time){
								rentableService.backRentable(rented.getId(), "Rented");
								status = 1;
							}
						}
					}
				}
			}
			String data = "{\"status\":"+status+"}";
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 租用者在租期内续借书籍
		 * */
		@RequestMapping("/reRentintime")
	    public void addRentTwo(String userid,int bookid,int period,String onlycode,HttpServletRequest request,HttpServletResponse response) {
			/*调用支付接口，生成交易日志log，添加former，更新bookowner的日志序号，跳转更新图片
			 * 先调用支付接口，再调用续借业务
			 * */
			System.out.println("进来了");
			System.out.println(bookid);
			int sureornot = 1;
			int now_way = 1;
			Rented rented = rentedService.getOneRented(bookid);
			long begin_time = System.currentTimeMillis();
			long end_time = rented.getEnd_time()+period*24*60*60*1000;			
			BigDecimal monney = rented.getRent_price().multiply(new BigDecimal(period));
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName 	  = "OpenId" +onlycode + filetype;
			//String ip = "http://192.168.3.140:8082/";
			//String ip         = "http://115.159.24.14/";
			String path       = Valuable.getIp()+"image/";//就有一个问题图片的路径怎么对应上URL
			String picture    = path+picName;
			rentedService.updateRented(bookid,begin_time,end_time,sureornot,picture);
			TradeLog tradeLog  = new TradeLog(begin_time,period,now_way,userid,userid,monney); 
			userService.addlogandformer(tradeLog,userid,bookid);
			userService.updateBookOwner(userid,bookid,tradeLog.getId());
			rented.setEnd_time(end_time);
			rented.setPicture(picture);	
 			String data = "{\"rented\":"+JSON.toJSONString(rented)+",\"log\":"+tradeLog.getId()+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 租用者超过租期续借书籍
		 * */
		@RequestMapping("/reRent")
	    public String addRent(String userid,int bookid,int period,HttpServletRequest request,HttpServletResponse response) {
			/*调用支付接口，生成交易日志log，添加former，更新bookowner的日志序号，跳转更新图片
			 * 先调用支付接口，再调用续借业务
			 * */
			int sureornot = 1;
			int now_way = 1;
			long begin_time = System.currentTimeMillis();
			long end_time = System.currentTimeMillis()+period*24*60*60*1000;
			Rentable rentable = rentableService.getOneRentable(bookid);
			BigDecimal monney = rentable.getRent_price().multiply(new BigDecimal(period));
			rentableService.moveRentable(bookid);
			rentedService.updateRented(bookid,begin_time,end_time,sureornot);
			TradeLog tradeLog  = new TradeLog(begin_time,period,now_way,userid,userid,monney); 
			userService.addlogandformer(tradeLog,userid,bookid);
			userService.updateBookOwner(userid,bookid,tradeLog.getId());
			String data = "{\"result\":\"success\",\"log\":"+tradeLog+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
			return "/bookdeal/updatepic";
		}
		/*
		 * author 	lmr
		 * time		2017/12/23
		 * function 租用者租用结束后更新图片
		 * */
		@RequestMapping("/updatepic")
	    public void updatepic(int bookid,HttpServletRequest request,HttpServletResponse response) {
			System.out.println("进来了");
			System.out.println(bookid);
			String onlycode = request.getParameter("onlycode");
			String filetype   = ".jpg";//判断上传图片格式需要做
			String picName 	  = "OpenId" +onlycode + filetype;
			//String ip         = "http://115.159.24.14/";
			//String ip = "http://192.168.3.140:8082/";
			String path       = Valuable.getIp()+"image/";//就有一个问题图片的路径怎么对应上URL
			String picture    = path+picName;
			rentableService.updateRentable(bookid, picture);
			String data = "{\"rentable\":"+JSON.toJSONString(rentableService.getOneRentable(bookid))+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}	
}	