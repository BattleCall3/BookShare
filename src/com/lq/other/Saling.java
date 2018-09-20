package com.lq.other;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_saling")
public class Saling {

	@Id
	private int id;
	@Column(length = 64)
	private String picture;
	@Column(length = 32)
	private String information;
	private long start_time;
	private int way;
	@Column(length = 32)
	private BigDecimal rent_price;
	@Column(length = 32)
	private BigDecimal sale_price;
	@Column(length = 32)
	private String phone;
	public Saling() {}
	public Saling(int id, String picture, String information, long start_time, int way, BigDecimal rent_price,
			BigDecimal sale_price, String phone) {
		super();
		this.id = id;
		this.picture = picture;
		this.information = information;
		this.start_time = start_time;
		this.way = way;
		this.rent_price = rent_price;
		this.sale_price = sale_price;
		this.phone = phone;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
