package com.yusys.service;

import java.util.List;

import com.yusys.entity.NewsComment;
import com.yusys.entity.NewsDetail;

public interface NewsCommentService {

	public abstract List<NewsComment> getAllByNewsId(NewsDetail newsDetail);
	public void addComment(NewsComment newsComment);

}