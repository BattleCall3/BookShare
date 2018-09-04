package com.lq.other;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_notConfirmPhone")
public class notConfirmPhone {
	@Id
	private int bookid;
	@Column(length = 32)
	private String originphone;
	//留着扩展用
	@Column
	private int num1;
	@Column(length = 32)
	private String str1;
	@Column(length = 64)
	private String str2;
	public notConfirmPhone() {}
	public notConfirmPhone(int bookid, String originphone) {
		super();
		this.bookid = bookid;
		this.originphone = originphone;
	}
	public notConfirmPhone(int bookid, String originphone, String title) {
		super();
		this.bookid = bookid;
		this.originphone = originphone;
		this.str2 = title;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getOriginphone() {
		return originphone;
	}
	public void setOriginphone(String originphone) {
		this.originphone = originphone;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	public String getStr2() {
		return str2;
	}
	public void setStr2(String str2) {
		this.str2 = str2;
	}
}
