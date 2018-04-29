package com.yusys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yusys.entity.Page;
import com.yusys.entity.Standard;

public interface StandardMapper {

	public List<Standard> findAll();
	
	public Integer findCount();
	
	public List<Standard> search(@Param("std_zhname") String std_zhname);
	
	public List<Standard> findAllPage(Page page);
	
	public Standard findById(Standard std);
	
	public void add(Standard std);
	
	public void deleteById(Standard std);
	
	public void modify(Standard std);
	
}
