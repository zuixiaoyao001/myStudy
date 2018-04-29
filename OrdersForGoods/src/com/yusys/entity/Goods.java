package com.yusys.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

public class Goods {
	private int id;
	private String goodsName;
	private int billStatus;
	private int goodsDistric;
	private double goodsPrice;
	private int goodsCount;
	private Date creationTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public int getGoodsDistric() {
		return goodsDistric;
	}
	public void setGoodsDistric(int goodsDistric) {
		this.goodsDistric = goodsDistric;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	
}
