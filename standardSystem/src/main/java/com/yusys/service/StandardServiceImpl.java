package com.yusys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yusys.dao.StandardMapper;
import com.yusys.entity.Page;
import com.yusys.entity.Standard;

@Service
public class StandardServiceImpl implements StandardService {
	
	/* (non-Javadoc)
	 * @see com.yusys.service.StandardService#findAll()
	 */
	@Override
	public List<Standard> findAll(){
		return standardMapper.findAll();
	}
	
	public List<Standard> findAllPage(Page page){
		return standardMapper.findAllPage(page);
	}
	
	@Override
	public void add(Standard std) {
		// TODO Auto-generated method stub
		standardMapper.add(std);
	}

	@Override
	public void deleteById(Standard std) {
		// TODO Auto-generated method stub
		standardMapper.deleteById(std);
	}

	@Override
	public void modify(Standard std) {
		// TODO Auto-generated method stub
		standardMapper.modify(std);
	}
	
	@Autowired
	private StandardMapper standardMapper;

	public StandardMapper getStandardMapper() {
		return standardMapper;
	}

	public void setStandardMapper(StandardMapper standardMapper) {
		this.standardMapper = standardMapper;
	}

	@Override
	public Standard findById(Standard std) {
		// TODO Auto-generated method stub
		return standardMapper.findById(std);
	}

	@Override
	public Integer findCount() {
		// TODO Auto-generated method stub
		return standardMapper.findCount();
	}
	@Override
	public List<Standard> search(String std_zhname){
		return standardMapper.search(std_zhname);
	}

}
