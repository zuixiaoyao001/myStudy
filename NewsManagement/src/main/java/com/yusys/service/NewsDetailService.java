package com.yusys.service;

import java.util.List;

import com.yusys.entity.NewsDetail;

public interface NewsDetailService {

	public abstract List<NewsDetail> getAll();

	public abstract List<NewsDetail> getNewsDetailByTitle(NewsDetail newsDetail);
	
	public void delete(NewsDetail newsDetail);

}