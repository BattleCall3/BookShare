package com.util;
public class Valuable {
	
	//服务器地址
	//http://115.159.24.14/
	private static final String ip = "https://qrain.club/";
//	private static final String ip = "http://192.168.1.102:8080/";
//	private static final String ip = "http://39.106.146.211:8080/";
//	private static final String ip = "http://111.230.30.179:8080/";
//	private static final String ip = "http://192.168.1.105:8080/";
	
	//用户图片上传保存地址
	private static final String path = "/image/mysqlPicture";
//	private static final String path = "D:/File/bookshare/mysqlPicture";
//	private static final String path = "C:/BookShareTool/bookpicture";
//	private static final String path = "usr/image";
	
	//阿凡达数据图片下载保存地址
	private static final String afandapicturepath = "/image/afandaPicture";
//	private static final String afandapicturepath = "D:/File/bookshare/afandaPicture";
	
	//没有isbn信息的书，存储isbn信息位置
	private static final String isbnnoInfoFile = "/image/afandaPicture/bookIsbnnoInfo.txt";
//	private static final String isbnnoInfoFile = "D:/File/bookshare/afandaPicture/bookIsbnnoInfo.txt";
	
	//数据库备份地址
	private static final String backupMysqlPath = "/image/";
//	private static final String backupMysqlPath = "D:/File/bookshare/backupMysql/";
	
	//小程序的
	private static final String APPID = "wx09392aba647dbe03";
	private static final String SECRET = "650307c831d52a8629fee1a5c0358881";
	
	//请求微信access_token地址
	private static final String access_tokenUrl= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID +"&secret="+ SECRET;

	public static String getAppid() {
		return APPID;
	}
	public static String getSecret() {
		return SECRET;
	}
	public static String getIsbnnoinfofile() {
		return isbnnoInfoFile;
	}
	public static String getIp() {
		return ip;
	}
	public static String getAccessTokenurl() {
		return access_tokenUrl;
	}
	public static String getPath() {
		return path;
	}
	public static String getAfandapicturepath() {
		return afandapicturepath;
	}
	public static String getBackupmysqlpath() {
		return backupMysqlPath;
	}
	
}
