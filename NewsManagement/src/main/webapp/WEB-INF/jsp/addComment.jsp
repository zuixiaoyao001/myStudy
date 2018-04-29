<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>增加评论页面</title>
<style type="text/css">
	
</style>
</head>
<body>
	<form action="addComment" method="post">
<input type="hidden" name="newsDetail.id" value="${id }"/>
	<table align="center">

		<thead align="center"><h3 align="center">增加评论</h3></thead>
		<tbody>
			<tr><td align="center">评论内容(*)</td><td><textarea rows="5" cols="5" name="content" value=""></textarea></td></tr>
			<tr><td align="center">评论人</td><td><input type="text" name="author" value=""/></td></tr>
		</tbody>
		<tfoot align="center">
		<td colspan="2"><input type="submit" value="提交"/><input type="button" value="goback" onclick="history.back()"/><td>
		</tfoot>
	</table>
</form>
</body>
</html>