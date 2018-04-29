<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=basePath%>/resources/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=basePath%>/resources/js/json2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
 		$("#search").click(function(){
			var std_zhname=$("#std_zhname").val();
			alert(std_zhname);
			$.ajax({
			url:"search.action?std_zhname="+std_zhname,
			type:"get",
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			//data:std_zhname,
			processData: true,
			asysc:true,
			success:function(data){
				var list = JSON.stringify(data);
				
				alert(list);
				alert("大小"+list.size());
				alert(list.standards);
				alert("success!")
				//window.location.reload();
			}
			});
		}) 
		$("#toAdd").click(function(){
			window.location.href="toAdd.action";
		})
		/* 复选框选定部分进行删除 */
		$("#delete").click(function(){
			var array = [];
			$("input[name='singlecheckbox']:checked").each(function(){
				array.push($(this).val());
				alert($(this).val())
			})
			alert(JSON.stringify(array));
			if(confirm("确认要删除吗？")){
				$.ajax({
					url:"delete.action",
					dataType:"json",
					traditional:true,
					contentType:"application/json",
					type:"POST",
					async:false,
					data:JSON.stringify(array),
					success:function(data){
						alert("ok");
						window.location.reload();
					}
				}) 
			}
			
		})
		/* 试验json */	
		$("#button0").click(function(){
			$.ajax({
				url:"jsonDemo.action",
				dataType:"json",
				type:"post",
				contentType:"application/json",
				data:JSON.stringify({id:1,name:"zhangxueliang",age:76,major:"军事"}),
				success:function(data){
					$("#bjson").html(data)
				}
			})
		})
		
	});
</script>
</head>
<body>
<div id="div1">
	<table>
		<thead align="center">标准列表</thead>
		<tbody>
			<tr>
				<td colspan="7" align="right"><input type="text" id="std_zhname" name="std_zhname"/>
				<input type="button" id="search" name="search" value="提交索引"/>
				<input type="button" value="新增标准" id="toAdd" onclick="toAdd()"/>
				<input type="button" id="delete" value="删除标准"/>
				</td>
			</tr>
			<tr>
				<td><input name="all" id="all" type="checkbox" /></td>
				<td>标准号</td>
				<td>中文名称</td>
				<td>版本</td>
				<td>发布日期</td>
				<td>实施日期</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${standards }" var="standard">
			<tr>
				<td><input type="checkbox" name="singlecheckbox" value="${standard.id }"/></td>
				<td>${standard.std_um }</td>
				<td>${standard.zhname }</td>
				<td>${standard.version }</td>
				<td><fmt:formatDate value="${standard.release_date }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${standard.impl_date }" pattern="yyyy-MM-dd"/></td>
				<td><a href="download.action?filename=${standard.ackage_path }">下载</a><a href="toUpdate.action?id=${standard.id }">修改</a></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="7" align="right">
				<a href="toFirstPage.action?currentPage=1">首页</a>|<a href="toUpPage.action?upPage=${page.currentPage-1 }">上一页</a>|
				<a href="toNextPage.action?nextPage=${page.currentPage+1 }">下一页</a>|<a href="toLastPage.action">末页</a>&ensp;&ensp;第${page.currentPage } 页/共 ${page.totalPage }页
				</td>
			</tr>
		</tbody>
	</table>
	</div>
	<input type="button" id="button0" value="json"/><div id="bjson"></div>
</body>
</html>