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
<script type="text/javascript">
	$(document).ready(function(){
		$("#stdum").blur(function(){
			var value = $(this).val();
			alert(value);
			$.ajax({
				url:"validateStdum.action?v="+value,
				data:value,
				type:"get",
				dataType:"text",
				 scriptCharset: 'utf-8'  , 
				success:function(data){
					if(data&&data=="标准号重复biaozhunhaochongfu"){
						alert(data);
					}
				}
			})
		})
		
	});
</script>
</head>
<body>
<form action="add.action" method="post" enctype="multipart/form-data">
	<table>
				<tr><td colspan="2">新增标准信息</td></tr>	
				<tr><td>标准号</td><td><input type="text" id="stdum" name="std_um" value=""/></td></tr>
				<tr><td>中文名称</td><td><input type="text" name="zhname" value=""/></td></tr>
				<tr><td>版本</td><td><input type="text" name="version" value=""/></td></tr>
				<tr><td>关键词</td><td><input type="text" name="keys" value=""/></td></tr>
				<tr><td>发布日期</td><td><input type="text" name="release_date" value=""/></td></tr>
				<tr><td>实施日期</td><td><input type="text" name="impl_date" value=""/></td></tr>
				<tr><td>附件</td><td><input type="file" name="file" value=""/></td></tr>
				<tr><td colspan="2"><input type="submit" value="保存"/>
				<input type="button" value="取消" onclick="history.go(-1)"/></td></tr>	
	</table>
	
	</form>
</body>
</html>