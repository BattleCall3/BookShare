package com.lq.entity;
import java.io.Serializable;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "t_bookowner")
public class BookOwner implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32)
	private int bookid;
	@Id
	@Column(length = 32)
	private String userid;
	@Id
	private int logid;
	public BookOwner(){}
	public BookOwner(int bookid,String userid,int logid){
		super();
		this.bookid = bookid;
		this.userid = userid;
		this.logid  = logid;
	}

	public int getLogid() {
		return logid;
	}
	public void setLogid(int logid) {
		this.logid = logid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
