package com.yusys.service;

import java.util.List;

import com.yusys.entity.Goods;

public interface GoodsService {

	public abstract Goods getById(int id);
	
	public abstract List<Goods> findAll();
	
	public List<Goods> getByDistric(int distric);
	
	public void modify(Goods goods);
	
	public void add(Goods goods);
	
	public void delete(int id);
}