package com.yusys.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NewsComment {
	
private int id;
private NewsDetail newsDetail;
private String content;
private String author;
private Date createdate;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public NewsDetail getNewsDetail() {
	return newsDetail;
}
public void setNewsDetail(NewsDetail newsDetail) {
	this.newsDetail = newsDetail;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public Date getCreatedate() {
	return createdate;
}
@DateTimeFormat
public void setCreatedate(Date createdate) {
	this.createdate = createdate;
}


}
