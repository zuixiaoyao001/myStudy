<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品区域订单查询首页</title>
</head>
<body>
	<form action="getByDistric" method="post">
		请选择区域<select name="distric">
				<option value="1">朝阳</option>
				<option value="2">海淀</option>
				<option value="3">丰台</option>
				<option value="4">西城</option>
				<option value="5">昌平</option>
			   </select>
	<input type="submit" value="提交"/>
	</form>
</body>
</html>