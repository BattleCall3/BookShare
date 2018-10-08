package com.lq.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.filefilter.DelegateFileFilter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.util.Utils;
import com.util.Valuable;

/**
 * 定时任务
 * @author bc
 * @date 2018年9月28日
 */
@Component
public class TimingController {

	private static String access_token = "";
	
	/*
	 * 每隔两小时，获取access_token
	 * 每隔100分钟，获取一次
	 */
//	@Scheduled(cron = "30 45 1/2 * * *")
//	@Scheduled(cron = "0 0/2 * * * *")
	@Scheduled(fixedRate = 100000)
//	@Scheduled(fixedRate = 6000000)
	private void getAccessToken() {
		String dataStr = Utils.doGet(Valuable.getAccessTokenurl());
		if(dataStr != "") {
			JSONObject json = JSONObject.parseObject(dataStr);
			int errcode = json.getIntValue("errcode");
			if(errcode == 0) {
				access_token = json.getString("access_token");
			}else {
				access_token = "";
			}
		}
	}
	public static String getAccess_token() {
		return access_token;
	}
	/*
	 * 数据库备份
	 * 周一和周五，凌晨三点备份一次
	 * 并删除之前第四次备份的
	 */
	private static int mydbNum = 0;
	@Scheduled(cron = "0 0 3 ? * 2,6")
	private void backupDB() {
		String mysqlPath = Valuable.getBackupmysqlpath();
		mydbNum += 1;
		mydbNum /= 4;
		mysqlPath = mysqlPath+"mydb"+mydbNum+".sql";
		File mysqlFile = new File(mysqlPath);
		if(mysqlFile.exists())
			mysqlFile.delete();
		String com = "/user/local/mysql/bin/mysqldump -ubookshare -pecho mydb > "+mysqlPath;
		String[] command = new String[] {"/bin/sh", "-c", com};
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
