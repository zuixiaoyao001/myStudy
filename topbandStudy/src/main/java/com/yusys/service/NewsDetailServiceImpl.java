package com.yusys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yusys.dao.NewsCommentMapper;
import com.yusys.dao.NewsDetailMapper;
import com.yusys.entity.NewsDetail;
@Service
public class NewsDetailServiceImpl implements NewsDetailService {
	@Autowired
	NewsDetailMapper newsDetailMpper;
	@Autowired
	NewsCommentMapper newsCommentMapper;
	
	/* (non-Javadoc)
	 * @see com.yusys.service.NewsDetailService#getAll()
	 */
	@Override
	public List<NewsDetail> getAll(){
		return newsDetailMpper.getAll();
	}
	
	/* (non-Javadoc)
	 * @see com.yusys.service.NewsDetailService#getNewsDetailByTitle()
	 */
	@Override
	public List<NewsDetail> getNewsDetailByTitle(NewsDetail newsDetail){
		return newsDetailMpper.getNewsDetailByTitle(newsDetail);
	}
	
	public void delete(NewsDetail newsDetail){
		
		newsCommentMapper.deleteComment(newsDetail);
		
		newsDetailMpper.deleteNewsDetail(newsDetail);
		
	}
}
