package com.lq.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_formid")
public class FormId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(length = 32)
	private String userid;
	@Column(length = 50)
	private String formid;
	@Column
	private Timestamp date;
	public FormId() {}
	public FormId(Integer id, String userid, String formid, Timestamp date) {
		super();
		this.id = id;
		this.userid = userid;
		this.formid = formid;
		this.date = date;
	}
	public FormId(String userid, String formid, Timestamp date) {
		super();
		this.userid = userid;
		this.formid = formid;
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
