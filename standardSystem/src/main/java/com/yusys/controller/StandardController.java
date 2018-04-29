package com.yusys.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.ognl.ObjectMethodAccessor;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yusys.entity.Page;
import com.yusys.entity.Standard;
import com.yusys.service.StandardService;

@Controller
public class StandardController {
	 @InitBinder
	    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	    }
	//	查找全部
	@RequestMapping(value="/list.action",method=RequestMethod.GET)
	public String getAll(Model model){
		System.out.println("22222");
		List<Standard> list = standardService.findAll();
		model.addAttribute("standards", list);
		return "list";
	}
//	根据输入的中文名称或者标准号模糊查询
	@ResponseBody
	@RequestMapping(value="/search.action",method=RequestMethod.GET)
	public void search(@RequestParam("std_zhname")String std_zhname,HttpServletResponse response) throws IOException{
		String stdName= new String(std_zhname.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(stdName);
		List<Standard> standards=standardService.search(stdName);
		JSONArray jsonArray = JSONArray.fromObject(standards);
		System.out.println("list的元素个数为："+standards.size());
		System.out.println(jsonArray);
		response.getWriter().print(jsonArray);
	}
//	分页显示
	@RequestMapping(value="/show.action",method=RequestMethod.GET)
	public String getAllByPage(Model model){
		Integer count=standardService.findCount();
		Page page = new Page();
		page.setCurrentPage(2);
		page.setTotalNum(count);
		List<Standard> list = standardService.findAllPage(page);
		model.addAttribute("standards", list);
		model.addAttribute("page", page);
		return "list";
	}
	
	//回到首页
	@RequestMapping(value="/toFirstPage.action",method=RequestMethod.GET)
	public String goFirstPage(@RequestParam("currentPage") Integer currentPage,Model model){
		Page page= new Page();
		page.setCurrentPage(currentPage);
		Integer count=standardService.findCount();
		page.setTotalNum(count);
		List<Standard> list = standardService.findAllPage(page);
		model.addAttribute("standards", list);
		model.addAttribute("page", page);
		return "list";

	}
//返回上一页
	@RequestMapping(value="/toUpPage.action",method=RequestMethod.GET)
	public String goUpPage(@RequestParam("upPage") Integer upPage,Model model){
		Page page= new Page();
		page.setCurrentPage(upPage);
		Integer count=standardService.findCount();
		page.setTotalNum(count);
		List<Standard> list = standardService.findAllPage(page);
		model.addAttribute("standards", list);
		model.addAttribute("page", page);
		return "list";

	}
//	进入下一页
	@RequestMapping(value="/toNextPage.action",method=RequestMethod.GET)
	public String goNextPage(@RequestParam("nextPage") Integer nextPage,Model model){
		Page page= new Page();
		page.setCurrentPage(nextPage);
		Integer count=standardService.findCount();
		page.setTotalNum(count);
		List<Standard> list = standardService.findAllPage(page);
		model.addAttribute("standards", list);
		model.addAttribute("page", page);
		return "list";

	}
	//进入末页
		@RequestMapping(value="/toLastPage.action",method=RequestMethod.GET)
		public String goLastPage(Model model){
			Integer count=standardService.findCount();
			Page page= new Page();
			page.setTotalNum(count);
			page.setCurrentPage(page.getTotalPage());
			List<Standard> list = standardService.findAllPage(page);
			model.addAttribute("standards", list);
			model.addAttribute("page", page);
			return "list";

		}
	@RequestMapping(value="/toAdd.action",method=RequestMethod.GET)
	public String toAdd(){
		return "addStandard";
	}
	
//	后台校验标准号是否重复
	@RequestMapping(value="/validateStdum.action",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String validateStdum(@RequestParam("v") String val) throws UnsupportedEncodingException{
		List<Standard> list = standardService.findAll();
		String parameVal= new String(val.getBytes("iso-8859-1"),"utf-8");
		for(Standard var:list){
			if(var.getStd_um().equals(val)){
				System.out.println(var.getStd_um());
				return "标准号重复";
		}
			}
		return "1111";
	} 
	
	@RequestMapping(value="/add.action",method=RequestMethod.POST)
	public String add(HttpServletRequest request,@ModelAttribute Standard std,Model model) throws IllegalStateException, IOException{
		if(!std.getFile().isEmpty()){
//			上传文件路径
			String path=request.getServletContext().getRealPath("/images/");
//			上传文件名
			String filename = std.getFile().getOriginalFilename();
			File filepath=new File(path,filename);
//			如果新创建的文件的父目录不存在，则创建一个出来
			if(!filepath.getParentFile().exists()){
				filepath.getParentFile().mkdirs();
			}
//			将上传文件保存到一个目标目录文件中
			std.getFile().transferTo(new File(path+File.separator+filename));
			std.setAckage_path(filename);
		}
		standardService.add(std);
		return "redirect:list.action";
	}
	
/*	@RequestMapping(value="/delete.action",method=RequestMethod.GET)
	public String delete(@Param("id") Integer id){
		Standard std = new Standard();
		std.setId(id);
		standardService.deleteById(std);
		return "redirect:/list.action";
	}*/
	
	@RequestMapping(value="/toUpdate.action",method=RequestMethod.GET)
	public String toModify(Standard std,Model model){
		Standard standard=standardService.findById(std);
		model.addAttribute("standard", standard);
		return "updateStd";
	}
	
	@RequestMapping(value="/update.action",method=RequestMethod.POST)
	public String modify(HttpServletRequest request,@ModelAttribute Standard std,Model model) throws IllegalStateException, IOException{
		if(!std.getFile().isEmpty()){
//			上传文件路径
			String path=request.getServletContext().getRealPath("/images/");
//			上传文件名
			String filename = std.getFile().getOriginalFilename();
			File filepath=new File(path,filename);
//			如果新创建的文件的父目录不存在，则创建一个出来
			if(!filepath.getParentFile().exists()){
				filepath.getParentFile().mkdirs();
			}
//			将上传文件保存到一个目标目录文件中
			std.getFile().transferTo(new File(path+File.separator+filename));
			std.setAckage_path(path+File.separator+filename);
			System.out.println("存储路径："+path+File.separator+filename);
		}
		System.out.println(std.getZhname()+"=="+std.getVersion()+"0000"+std.getId());
		standardService.modify(std);
		return "redirect:/list.action";
	}
	@RequestMapping("/download.action")
	public ResponseEntity<byte[]> download(HttpServletRequest request,@RequestParam("filename") String filename,Model model) throws IOException{
//		下载路径
		String path=request.getServletContext().getRealPath("/images/");
		File file=new File(path+File.separator+filename);
		HttpHeaders headers=new HttpHeaders();
//		下载显示的文件名，解决中文乱码问题
		String downloadFileName = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
//		通知浏览器一attachment打开图片
		headers.setContentDispositionFormData("attachment", downloadFileName);
//		application/octet-steam:二进制流数据(最常见的文件下载)
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		201 HttpStatus.CREATED
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	} 
	
	@RequestMapping(value="/delete.action",method=RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody String ids){
		System.out.println("ids:"+ids);
//		ObjectMapper json= new ObjectMapper();
		JSONArray json = JSONArray.fromObject(ids);
		System.out.println(json.size()+"json的长度");
		if(json.size()>0){
			  for(int i=0;i<json.size();i++){
//			    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象---因为我的数组只是简单的数字，所以不能转成对象，报错不适用
				  Standard std = new Standard();
				  Integer m = Integer.parseInt((json.get(i)).toString());
				  System.out.println(m);
				  std.setId(m);
				  standardService.deleteById(std); 
			  }
			}
	}
	//试验数组
	@RequestMapping(value="/shiyan.action", method=RequestMethod.POST)
	@ResponseBody
	public String requestList(@RequestParam("listParam[]") List<String> param) {
		System.out.println("==================");
	    return "Request successful. Post param : List<String> - " + param.toString();
	}
	@RequestMapping(value="/jsonDemo.action")
	@ResponseBody
	public void getJson(@RequestBody String jon,HttpServletResponse response) throws Exception{
		System.out.println(jon);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(jon));
		response.getWriter().println(mapper.writeValueAsString(jon));
	} 
	
	@Autowired
	 private StandardService standardService;

	public StandardService getStandardService() {
		return standardService;
	}

	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}
	 
	 
}
