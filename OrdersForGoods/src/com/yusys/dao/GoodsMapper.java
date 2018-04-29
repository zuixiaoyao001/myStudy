package com.yusys.dao;

import java.util.List;

import com.yusys.entity.Goods;
public interface GoodsMapper {
	
	public  List<Goods> getGoodsByDistric(int distric);
	
	public Goods getById(int id);
	
	public List<Goods> findAll();
	
	public void modify(Goods goods);
	
	public void add(Goods goods);
	
	public void delete(int id);
}
