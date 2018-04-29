package com.yusys.controlller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yusys.entity.Goods;
import com.yusys.service.GoodsService;

@Controller
public class GoodsDaoController {
	@Resource
	private GoodsService goodsService;
	
	@RequestMapping("/")
	public String welcome(){
		return "../index";
	}
	@RequestMapping("/getByDistric")
	public String getByDistrict(@RequestParam("distric") int distric, Model model){
	 List<Goods> list=goodsService.getByDistric(distric);
	 model.addAttribute("list", list);
		return "goodsShow";
	}
	
	@RequestMapping(value="/toupdate",method=RequestMethod.GET)
	public String toupdate(@RequestParam(value="id") int id,Model model){
		Goods goods=goodsService.getById(id);
		model.addAttribute("goods", goods);
		return "update";
	}
	@RequestMapping("/list")
	public String getAll(Model model){
		List<Goods> list = goodsService.findAll();
		model.addAttribute("list", list);
		return "goodsShow";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@ModelAttribute Goods goods){
		Date creationTime = new Date();
		goods.setCreationTime(creationTime);
		goodsService.modify(goods);
		return "redirect:/";
		
	}
	@RequestMapping("/toadd")
	public String toAdd(){
		
		return "add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@ModelAttribute Goods goods){
		Date creationTime = new Date();
		goods.setCreationTime(creationTime);
		goodsService.add(goods);
		return "redirect:/list";
	}
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(int id){
		goodsService.delete(id);
		return "redirect:/list";
	}
}
