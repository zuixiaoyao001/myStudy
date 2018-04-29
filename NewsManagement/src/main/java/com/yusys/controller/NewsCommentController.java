package com.yusys.controller;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yusys.entity.NewsComment;
import com.yusys.entity.NewsDetail;
import com.yusys.service.NewsCommentService;

@Controller
public class NewsCommentController {
	@Autowired
	NewsCommentService newsCommentService;
	@RequestMapping(value="/getAllCommentByNewsId",method=RequestMethod.GET)
	public String getAllCommentByNewsId(@Param("id") int id,Model model){
		NewsDetail newsDetail = new NewsDetail();
		newsDetail.setId(id);
		List<NewsComment> list= newsCommentService.getAllByNewsId(newsDetail);
		model.addAttribute("list", list);
		return "newsComment";
	}
	@RequestMapping(value="/toAddComment",method=RequestMethod.GET)
	public String toAddComment(@Param("id") String id,Model model){
		model.addAttribute("id",id);
		return "addComment";
	}
	@RequestMapping(value="/addComment",method=RequestMethod.POST)
	public String addComment(@ModelAttribute NewsComment newsComment){
		Date date = new Date();
		newsComment.setCreatedate(date);
		newsCommentService.addComment(newsComment);
		return "redirect:getAllCommentByNewsId?id="+newsComment.getNewsDetail().getId();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public NewsCommentService getNewsCommentService() {
		return newsCommentService;
	}

	public void setNewsCommentService(NewsCommentService newsCommentService) {
		this.newsCommentService = newsCommentService;
	}
	
}
