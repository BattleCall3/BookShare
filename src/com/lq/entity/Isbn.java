package com.lq.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "t_isbn")
public class Isbn {

	//序号：作为表的主键，书的isbn号
	@Id
	@Column(length = 32)
	private String isbn;
	
	//书名
	@Column(length = 128)
	private String title;
	
	//副标题
	@Column(length = 128)
	private String subtitle;	
		
	//书的图片
	@Column(length = 120)
	private String picture;
	
	//书的作者
	@Column(length = 128)
	private String author;
	
	//书的摘要
	@Column(length = 256)
	private String summary;

	//出版社
	@Column(length = 64)
	private String publisher;
	
	/*//出版地
	@Column(length = 32)
	private String pubplace;*/
	
	//出版时间
	@Column(length = 32)
	private String pubdate;
	
	//页数
	@Column(length = 32)
	private String page;
	
	//价格
	@Column(length = 32)
	private String price;
	
	//装帧方式
	@Column(length = 32)
	private String binding;
	
	//ISBN 10位
	@Column(length = 32)
	private String isbn10;

	//主题词
	@Column(length = 255)
	private String keyword;
	
	/*//版次
	@Column(length = 32)
	private String edition;*/
	
	/*//印次
	@Column(length = 32)
	private String impression;*/
	
	/*//正文语种
	@Column(length = 32)
	private String language;*/
	
	/*//开本
	@Column(length = 32)
	private String format;*/
	
	/*//中图法分类
	@Column(length = 32)
	private String category;*/

	
	public Isbn() {	}
	
	public Isbn(String isbn, String title, String subtitle, String picture, String author, String summary,
			String publisher, String pubdate, String page, String price, String binding, String isbn10,
			String keyword) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.subtitle = subtitle;
		this.picture = picture;
		this.author = author;
		this.summary = summary;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.page = page;
		this.price = price;
		this.binding = binding;
		this.isbn10 = isbn10;
		this.keyword = keyword;
	}
	

	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}