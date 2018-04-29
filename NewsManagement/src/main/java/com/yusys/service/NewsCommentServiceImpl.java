package com.yusys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yusys.dao.NewsCommentMapper;
import com.yusys.entity.NewsComment;
import com.yusys.entity.NewsDetail;

@Service
public class NewsCommentServiceImpl implements NewsCommentService {
	@Autowired
	NewsCommentMapper newsCommentMapper;
	
	/* (non-Javadoc)
	 * @see com.yusys.service.NewsCommentService#getAllByNewsId(com.yusys.entity.NewsDetail)
	 */
	@Override
	public List<NewsComment> getAllByNewsId(NewsDetail newsDetail){
		return newsCommentMapper.getAllCommentByNewsDetailId(newsDetail);
		
	}
	
	public void addComment(NewsComment newsComment){
		newsCommentMapper.addComment(newsComment);
	}
}
