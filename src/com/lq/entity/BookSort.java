package com.lq.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_booksort")
public class BookSort implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int bookid;
	@Id
	@Column(length = 16)
	private String isbn;
	@Column
	private int label1;
	@Column
	private int label2;
	@Column
	private int label3;
	public BookSort() {	}
	public BookSort(int bookid, String isbn, int label1, int label2, int label3) {
		super();
		this.bookid = bookid;
		this.isbn = isbn;
		this.label1 = label1;
		this.label2 = label2;
		this.label3 = label3;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getLabel1() {
		return label1;
	}
	public void setLabel1(int label1) {
		this.label1 = label1;
	}
	public int getLabel2() {
		return label2;
	}
	public void setLabel2(int label2) {
		this.label2 = label2;
	}
	public int getLabel3() {
		return label3;
	}
	public void setLabel3(int label3) {
		this.label3 = label3;
	}
	
}
