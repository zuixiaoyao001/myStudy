package com.yusys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yusys.entity.NewsDetail;
import com.yusys.service.NewsDetailService;
@Controller
public class NewsDetailController {
	@Autowired
	NewsDetailService newsDetailService;
	
	@RequestMapping(value="/newsDetailList",method=RequestMethod.GET)
	public String getAllDetail(Model model){
		List<NewsDetail> list=newsDetailService.getAll();
		model.addAttribute("list", list);
		return "newsDetail";
	}
	@RequestMapping(value="/searchByTitle.do",method=RequestMethod.POST)
	public String getDetailByTitle(NewsDetail newsDetail,Model model){
		List<NewsDetail> list=newsDetailService.getNewsDetailByTitle(newsDetail);
		model.addAttribute("list",list);
		return "newsDetail";
	}
	@RequestMapping(value="/deleteNewsDetail",method=RequestMethod.GET)
	public String deleteNewsDetail(@ModelAttribute NewsDetail newsDetail){
		newsDetailService.delete(newsDetail);
		return "redirect:/newsDetailList";
		
	}
}
