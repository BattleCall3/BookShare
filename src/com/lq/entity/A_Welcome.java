package com.lq.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class A_Welcome {

	@Id
	@GeneratedValue
	private Integer number;
	@Column(length = 128)
	private String text;
	public A_Welcome() {}
	public A_Welcome(Integer number, String text) {
		super();
		this.number = number;
		this.text = text;
	}
	public A_Welcome(String text) {
		super();
		this.text = text;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
