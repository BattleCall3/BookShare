package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Utils {

	/**
	 * post 带参数请求url，参数为json格式
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, String param) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(param, "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		String resultStr = "";
		try {
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				resultStr = EntityUtils.toString(response.getEntity());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
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
		return resultStr;
	}
	/**
	 * get不带参数请求
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(8000).setSocketTimeout(20000).build();
		httpGet.setConfig(config);
		CloseableHttpResponse response = null;
		String result = "";
		try {
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
				result = EntityUtils.toString(response.getEntity());
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
	/*
	 * author bc
	 * 2018.9.20
	 * 增加方法：将阿凡达api图片下载到服务器上
	 * url:https://api.avatardata.cn/BookInfo/Img?file=b5cbcc228e8d4f51b239a6662b879e3d.jpg
	 * 截取后图片名称：b5cbcc228e8d4f51b239a6662b879e3d.jpg
	 */
	public static void downPicture(String pictureUrl) {
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
	
}
