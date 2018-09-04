package com.lq.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_rentablestop")
public class Rentablestop {

	//序号：作为表的主键，结构是：原主人手机号+原主人首次提交信息的年月日时分秒，如1871122334420171231120000
	@Id
	private int id;
	@Column(length = 32)
	private String information;
	//原主人（的手机号）:从书的原主人微信账户中读取
	@Column(length = 32)
	private String origin_openid;
	//上线时间:原主人将此书提交到平台上的时间
	private long start_time;
	//交易方式:即书的原主人希望这本书被租出还是卖出（亦或两者均可），由书的原主人提交信息时输入
	public Rentablestop(){}
	public Rentablestop(int id,String information,String origin_openid,long start_time){
		this.id = id;
		this.information = information;
		this.origin_openid = origin_openid;
		this.start_time = start_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getOrigin_openid() {
		return origin_openid;
	}
	public void setOrigin_openid(String origin_openid) {
		this.origin_openid = origin_openid;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	
	
}