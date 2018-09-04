package com.lq.other;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_partrentable")
public class PartRentable{
	@Id
	private int id;
	@Column(length = 32)
	private String picture;
	private int way;
	private BigDecimal rent_price;
	private BigDecimal sale_price;
	@Column(length = 32)
	private String title;
	public PartRentable(){}
	public PartRentable(int id,String picture,int way,BigDecimal rent_price,BigDecimal sale_price,String title){
			this.id = id;
			this.picture = picture;
			this.way = way;
			this.rent_price =  rent_price;
			this.sale_price = sale_price;
			this.title = title;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
