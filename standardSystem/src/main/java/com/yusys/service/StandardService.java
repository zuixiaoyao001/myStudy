package com.yusys.service;

import java.util.List;

import com.yusys.entity.Page;
import com.yusys.entity.Standard;

public interface StandardService {

	public abstract List<Standard> findAll();
	
	public List<Standard> findAllPage(Page page);
	
	public Integer findCount();
	
	public void add(Standard std);
	
	public void deleteById(Standard std);
	
	public void modify(Standard std);
	
	public Standard findById(Standard std);

	public abstract List<Standard> search(String std_zhname);
	

}