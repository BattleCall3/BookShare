package com.lq.entity;
import javax.persistence.*;
@Entity
@Table(name = "t_user")
public class User {

	//用户的wx账号：这是主键，充分利用了OPENID不可重复的特点
	@Id
	@Column(length = 32)
	private String id;
	
	@Column(length = 32)
	private String phone;
	
	@Column(length = 32)
	private String grade;
	
	@Column(length = 32)
	private String sex;
	
	public User(){}
	public User(String id){
		this.id = id;
	}
	public User(String id,String phone,String grade,String sex){
		this.id = id;
		this.phone = phone;
		this.grade = grade;
		this.sex = sex;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}