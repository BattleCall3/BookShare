package com.lq.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_worthless")
public class Worthless {

	//序号：当书被报废时，所在记录从上表移到此表，序号依然沿用。
	@Id
	private int id;
	//书名：从表一复制
	@Column(length = 32)
	private String name;
	//书的图片:从表一复制
	@Column(length = 64)
	private String picture;
	//书的信息:从表一复制
	@Column(length = 32)
	private String information;
	//报废时间:书被报废时，读取服务器时间
	@Column(length = 32)
	private long worthless_time;
	//原主人（的手机号）:从表一复制
	@Column(length = 32)
	private String origin_tel;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public long getWorthless_time() {
		return worthless_time;
	}

	public void setWorthless_time(long worthless_time) {
		this.worthless_time = worthless_time;
	}

	public String getOrigin_tel() {
		return origin_tel;
	}

	public void setOrigin_tel(String origin_tel) {
		this.origin_tel = origin_tel;
	}
	
}