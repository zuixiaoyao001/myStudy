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
	//	����ȫ��
	@RequestMapping(value="/list.action",method=RequestMethod.GET)
	public String getAll(Model model){
		System.out.println("22222");
		List<Standard> list = standardService.findAll();
		model.addAttribute("standards", list);
		return "list";
	}
//	����������������ƻ��߱�׼��ģ����ѯ
	@ResponseBody
	@RequestMapping(value="/search.action",method=RequestMethod.GET)
	public void search(@RequestParam("std_zhname")String std_zhname,HttpServletResponse response) throws IOException{
		String stdName= new String(std_zhname.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(stdName);
		List<Standard> standards=standardService.search(stdName);
		JSONArray jsonArray = JSONArray.fromObject(standards);
		System.out.println("list��Ԫ�ظ���Ϊ��"+standards.size());
		System.out.println(jsonArray);
		response.getWriter().print(jsonArray);
	}
//	��ҳ��ʾ
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
	
	//�ص���ҳ
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
//������һҳ
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
//	������һҳ
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
	//����ĩҳ
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
	
//	��̨У���׼���Ƿ��ظ�
	@RequestMapping(value="/validateStdum.action",method=RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
	@ResponseBody
	public String validateStdum(@RequestParam("v") String val) throws UnsupportedEncodingException{
		List<Standard> list = standardService.findAll();
		String parameVal= new String(val.getBytes("iso-8859-1"),"utf-8");
		for(Standard var:list){
			if(var.getStd_um().equals(val)){
				System.out.println(var.getStd_um());
				return "��׼���ظ�";
		}
			}
		return "1111";
	} 
	
	@RequestMapping(value="/add.action",method=RequestMethod.POST)
	public String add(HttpServletRequest request,@ModelAttribute Standard std,Model model) throws IllegalStateException, IOException{
		if(!std.getFile().isEmpty()){
//			�ϴ��ļ�·��
			String path=request.getServletContext().getRealPath("/images/");
//			�ϴ��ļ���
			String filename = std.getFile().getOriginalFilename();
			File filepath=new File(path,filename);
//			����´������ļ��ĸ�Ŀ¼�����ڣ��򴴽�һ������
			if(!filepath.getParentFile().exists()){
				filepath.getParentFile().mkdirs();
			}
//			���ϴ��ļ����浽һ��Ŀ��Ŀ¼�ļ���
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
//			�ϴ��ļ�·��
			String path=request.getServletContext().getRealPath("/images/");
//			�ϴ��ļ���
			String filename = std.getFile().getOriginalFilename();
			File filepath=new File(path,filename);
//			����´������ļ��ĸ�Ŀ¼�����ڣ��򴴽�һ������
			if(!filepath.getParentFile().exists()){
				filepath.getParentFile().mkdirs();
			}
//			���ϴ��ļ����浽һ��Ŀ��Ŀ¼�ļ���
			std.getFile().transferTo(new File(path+File.separator+filename));
			std.setAckage_path(path+File.separator+filename);
			System.out.println("�洢·����"+path+File.separator+filename);
		}
		System.out.println(std.getZhname()+"=="+std.getVersion()+"0000"+std.getId());
		standardService.modify(std);
		return "redirect:/list.action";
	}
	@RequestMapping("/download.action")
	public ResponseEntity<byte[]> download(HttpServletRequest request,@RequestParam("filename") String filename,Model model) throws IOException{
//		����·��
		String path=request.getServletContext().getRealPath("/images/");
		File file=new File(path+File.separator+filename);
		HttpHeaders headers=new HttpHeaders();
//		������ʾ���ļ��������������������
		String downloadFileName = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
//		֪ͨ�����һattachment��ͼƬ
		headers.setContentDispositionFormData("attachment", downloadFileName);
//		application/octet-steam:������������(������ļ�����)
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
		System.out.println(json.size()+"json�ĳ���");
		if(json.size()>0){
			  for(int i=0;i<json.size();i++){
//			    JSONObject job = json.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����---��Ϊ�ҵ�����ֻ�Ǽ򵥵����֣����Բ���ת�ɶ��󣬱�������
				  Standard std = new Standard();
				  Integer m = Integer.parseInt((json.get(i)).toString());
				  System.out.println(m);
				  std.setId(m);
				  standardService.deleteById(std); 
			  }
			}
	}
	//��������
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
