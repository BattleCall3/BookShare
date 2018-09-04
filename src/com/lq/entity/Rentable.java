package com.lq.entity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.TableGenerator;
@Entity
@Table(name = "t_rentable")
public class Rentable {

	//序号：作为表的主键，结构是：原主人手机号+原主人首次提交信息的年月日时分秒，如1871122334420171231120000
	@Id
	//@GeneratedValue(strategy=GenerationType.TABLE,generator="tableGenerator")
	//@TableGenerator(name="tableGenerator",initialValue=0,allocationSize=1)
	private int id;
	@Column(length = 64)
	private String picture;
	//书的信息:由书的原主人提交信息时拍摄书的条码上传或从下表复制
	@Column(length = 32)
	private String information;
	//原主人（的手机号）:从书的原主人微信账户中读取
	@Column(length = 32)
	private String origin_openid;
	//上线时间:原主人将此书提交到平台上的时间
	private long start_time;
	//交易方式:即书的原主人希望这本书被租出还是卖出（亦或两者均可），由书的原主人提交信息时输入
	private int way;
	//租价:由书的原主人提交信息时输入，若只想卖，则此项设为空。单位是固定的。即“元/天”，采用Decimal数据类型存储
	@Column(length = 32)
	private BigDecimal rent_price;
	//售价:由书的原主人提交信息时输入，若只想租，则此项设为空。单位是固定的。即“元”，采用Decimal数据类型存储。
	@Column(length = 32)
	private BigDecimal sale_price;
	public Rentable(){}
	public Rentable(int id, String picture) {
		this.id = id;
		this.picture = picture;
	}
	public Rentable(int id,String information,String origin_openid,long start_time){
		this.id = id;
		this.information = information;
		this.origin_openid = origin_openid;
		this.start_time = start_time;
	}
	public Rentable(int id,String picture,String information,String origin_openid,long start_time,int way,BigDecimal rent_price,BigDecimal sale_price){
		this.id = id;
		this.picture = picture;
		this.information = information;
		this.origin_openid = origin_openid;
		this.start_time = start_time;
		this.way = way;
		this.rent_price = rent_price;
		this.sale_price = sale_price;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public BigDecimal getRent_price() {
		return rent_price;
	}

	public void setRent_price(BigDecimal rent_price) {
		this.rent_price = rent_price;
	}

	public BigDecimal getSale_price() {
		return sale_price;
	}

	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}	
}