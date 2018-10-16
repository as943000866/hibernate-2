package com.lmg.enties;

import java.sql.Blob;
import java.util.Date;

public class New {
	private int id;
	private String title;
	private String author;
	private Date date;
	//派生属性
	private String desc;
	
	//大文本
	private String content;
	
	private Blob image;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public New(String title, String author, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
	}
	
	
	public New(String title, String author, Date date, String desc) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
		this.desc = desc;
	}
	
	
	public New(String title, String author, Date date, String desc, String content, Blob image) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
		this.desc = desc;
		this.content = content;
		this.image = image;
	}
	public New() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "New [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", desc=" + desc + "]";
	}
	
	public String getDesc() {
		return desc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	
}
