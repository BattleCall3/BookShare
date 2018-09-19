package com.util;
public class Valuable {
	
	//服务器地址
	//http://115.159.24.14/
//	private static final String ip = "https://qrain.club/";
//	private static final String ip = "http://39.106.146.211:8080/";
//	private static final String ip = "http://111.230.30.179:8080/";
//	private static final String ip = "http://192.168.1.105:8080/";
	private static final String ip = "http://192.168.1.102:8080/";
	
	//用户图片上传保存地址
	private static final String path = "D:/File/bookshare/mysqlPicture";
//	private static final String path = "C:/BookShareTool/bookpicture";
//	private static final String path = "usr/image";
	
	//阿凡达数据图片下载保存地址
	private static final String afandapicturepath = "D:/File/bookshare/afandaPicture";
	
//	private static final String accessToken = "13_rWdxCoAN4xxatY3PN5fjpSlyxnC7H7HKmxgL1HZikMpoPABefDojupxmFXwjC6lMW-YqoJOxlxvcCZON9YWmCEe3krAEC8Qw_lvNsyinYgEI_g80MKXwvDCpy8asG79VhTpq2cAbI7KrbD2yODCiAAATIH";
	public static String getIp() {
		return ip;
	}
	public static String getPath() {
		return path;
	}
	public static String getAfandapicturepath() {
		return afandapicturepath;
	}
}
