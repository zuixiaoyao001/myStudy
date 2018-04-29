package com.yusys.dao;

import java.util.List;

import com.yusys.entity.NewsDetail;

public interface NewsDetailMapper {
	
	public List<NewsDetail> getAll();
	
	public List<NewsDetail> getNewsDetailByTitle(NewsDetail newsDetail);
	
	public void deleteNewsDetail(NewsDetail newsDetail);
}
