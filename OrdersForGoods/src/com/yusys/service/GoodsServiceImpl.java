package com.yusys.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yusys.dao.GoodsMapper;
import com.yusys.entity.Goods;
@Service
@Transactional(propagation=Propagation.SUPPORTS,isolation=Isolation.READ_COMMITTED,readOnly=true)
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsMapper goodsMapper;
	
	/* (non-Javadoc)
	 * @see com.yusys.service.GoodsService#getById()
	 */
	@Override
	public Goods getById(int id){
		return goodsMapper.getById(id);
	}

	@Override
	public List<Goods> findAll() {
		// TODO Auto-generated method stub
		return goodsMapper.findAll();
	}
	
	public List<Goods> getByDistric(int distric){
		List<Goods> list = goodsMapper.getGoodsByDistric(distric);
		return list;
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public void modify(Goods goods){
		goodsMapper.modify(goods);
	}
	
	public void add(Goods goods){
		//Date date= new Date();
		//goods.setCreationTime(date);
		goodsMapper.add(goods);
	}
	public void delete(int id){
		goodsMapper.delete(id);
	}
}

