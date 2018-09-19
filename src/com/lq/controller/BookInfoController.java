package com.lq.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lq.entity.Isbn;
import com.lq.entity.Rentable;
import com.lq.other.PartRentable;
import com.lq.service.IsbnService;
import com.lq.service.RentableService;


/*
 * author 	lmr
 * time		2017/12/11 0:46
 * function	向前台提供书籍信息
 *  修改 author bc
 *  将ofindex与ofsearch合为一个方法
 * */
@Controller
@RequestMapping("/bookinfo")
public class BookInfoController{
		@Autowired
		private RentableService rentableService;
		@Autowired
		private IsbnService isbnService;
		
		@RequestMapping(value = "/ofindex", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> bookput(@RequestBody(required = false) JSONObject json) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<PartRentable> rentableList = new ArrayList<PartRentable>();
			//下面几种转换都可以
			// 1.
			JSONArray arr = json.getJSONArray("keyword");
			int size = json.getInteger("size");
			int startlocation = json.getInteger("startlocation");
			// 2.
//			JSONObject jsonObject = JSON.parseObject(json.toJSONString());
//			JSONArray arr = jsonObject.getJSONArray("keyword");
			// 3.
//			String arrStr = jsonObject.getString("keyword");
//			JSONArray arr = JSONArray.parseArray(arrStr);
//			int size = jsonObject.getInteger("size");
//			int startlocation = jsonObject.getInteger("startlocation");
			int arrSize = arr.size();
			resultMap.put("len", "");
			if(arrSize > 0) {
				List<String> isbns = isbnService.searchIsbn0(arr.getString(0));
				if(isbns.size()>0) {
					List<String> tempIsbns = isbns;
					for(int i=1; i<arrSize; i++) {
						isbns = isbnService.searchInIsbn(arr.getString(i), isbns);
						if(isbns.size()>0)
							tempIsbns = isbns;
						else {
							isbns = tempIsbns;
							break;
						}
					}
					rentableList = isbnService.searchBookbyIsbn(startlocation, size*2, isbns);
					resultMap.replace("len", isbnService.bookNumberbyIsbn(isbns));
				}
				/*	返回所有按关键字搜索的书
				 * List<PartRentable> tempList = null;
				for(int i=0; i<arrSize; i++) {
					tempList = ofsearch(arr.getString(i));
					if(tempList!=null) {
						rentableList.addAll(tempList);
					}
				}*/
			}else {
				rentableList = rentableService.getPartRentables(startlocation, size);
				resultMap.replace("len", rentableService.getRentableLen());
			}
			resultMap.put("result", rentableList);
			return resultMap;
		}
		@RequestMapping("/ofdetail")
	    public void bookdetail(int bookid,HttpServletRequest request,HttpServletResponse response) {
	    	response.setContentType("application/json");
	    	Rentable rentable = rentableService.getOneRentable(bookid);
	    	Isbn isbn =rentableService.getBookInfo(rentable.getInformation());
			String data = "{\"rentable\":"+ JSON.toJSONString(rentable) +",\"detail\":"+ JSON.toJSONString(isbn)+"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		/*根据  关键字/书名/作者/出版社		搜索
		 * */
//		@RequestMapping("/ofsearch")
	    public List<PartRentable> ofsearch(String keyword) {
	    	if(keyword != ""){
	    		List<PartRentable> rentables = isbnService.getSearchInCore(keyword);
	    		return rentables;
	    	}	
			return null;
		}
		/*
		 * 未完成的方法
		 * */
		@RequestMapping("/ofcore")
	    public void ofcore(String keyword,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
	    	System.out.println(keyword);	    	
	    	//String keyword1="";
	    	//String keyword2="";
	    	//String[] keywords = keyword.split(" ");
	    	List<String> isbns = isbnService.getSearchIsbn(keyword);
	    	for(String string: isbns){
	    		System.out.println(string);
	    	}
			List<PartRentable> rentables = isbnService.getSearchInCore(keyword);
			//rentables = isbnService.getSearchInfoByTwokey(keyword1,keyword2);
			System.out.println(rentables.size());
	    	response.setContentType("application/json");
			String data = "{\"result\":"+ JSON.toJSONString(rentables) +"}";	
			try{
				PrintWriter out = response.getWriter();
				out.write(data);
				out.close();
			}catch(IOException e){
				e.printStackTrace();				
			}
		}
		//返回没有图片书的图片
		/*@RequestMapping("/nopicture")
		public void noPicture(int bookid, int num, HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("application/json");
			String nopicturepath = rentableService.getNoPicturePath(bookid);
			String data = "{\"personalPicture\":"+ JSON.toJSONString(nopicturepath) +",\"num\":"+num+"}";
			PrintWriter writer = response.getWriter();
			writer.write(data);
			writer.close();
		}*/
		@RequestMapping("/nopicture")
		public void noPicture(int bookid, HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("application/json");
			String data = "{\"personalPicture\":"+ JSON.toJSONString(rentableService.getNoPicturePath(bookid)) +"}";
			PrintWriter writer = response.getWriter();
			writer.write(data);
			writer.close();
		}
//		 * 根据标签查找书，可以搜索
		@RequestMapping("/byClassify")
		@ResponseBody
		public Map<String, Object> byClassify(@RequestBody JSONObject json) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			int startlocation = json.getInteger("startlocation");
			int size = json.getInteger("size");
			JSONArray arrSort = json.getJSONArray("sort");
			JSONArray arrKeyword = json.getJSONArray("keyword");
			int keywordSize = arrKeyword.size();
			List<String> isbns = isbnService.getIsbnbySort(arrSort.getInteger(0), arrSort.getInteger(1), arrSort.getInteger(2));
			resultMap.put("result", "");
			resultMap.put("len", 0);
			if(isbns.size() > 0) {
				if(keywordSize>0) {
					List<String> tempIsbns = isbns;
					for(int i=0; i<keywordSize; i++) {
						isbns = isbnService.searchInIsbn(arrKeyword.getString(i), isbns);
						if(isbns.size()>0)
							tempIsbns = isbns;
						else {
							isbns = tempIsbns;
							break;
						}
					}
				}
				resultMap.replace("result", isbnService.searchBookbyIsbn(startlocation, size, isbns));
				resultMap.replace("len", isbnService.bookNumberbyIsbn(isbns));
			}
			return resultMap;
		}
}	