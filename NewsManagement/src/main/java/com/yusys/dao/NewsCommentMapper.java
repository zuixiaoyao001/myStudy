package com.yusys.dao;

import java.util.List;

import com.yusys.entity.NewsComment;
import com.yusys.entity.NewsDetail;

public interface NewsCommentMapper {
	
	public List<NewsComment> getAllCommentByNewsDetailId(NewsDetail newsDetail);
	
	public void addComment(NewsComment newsComment);
	
	public void deleteComment(NewsDetail newsDetail);
		
}
