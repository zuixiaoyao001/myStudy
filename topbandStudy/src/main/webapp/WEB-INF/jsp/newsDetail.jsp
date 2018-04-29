<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新闻列表展示页面</title>
</head>
<body>
<div align="center">
<form action="searchByTitle.do" method="post">
新闻标题：<input type="text" name="title" value=""/><input type="submit"/>
</form>
</div>
	<table align="center">
		<thead><h1 align="center">新闻列表</h1></thead>
		<tbody>
			<tr>
				<td align="center">新闻编号</td>
				<td align="center">新闻标题</td>
				<td align="center">新闻摘要</td>
				<td align="center">作者</td>
				<td align="center">创建时间</td>
				<td align="center">操作</td>
			</tr>
			<c:forEach items="${list }" var="newsDetail">
			<tr>
				<td align="center">${newsDetail.id }</td>
				<td align="center">${newsDetail.title }</td>
				<td align="center">${newsDetail.summary }</td>
				<td align="center">${newsDetail.author }</td>
				<td align="center"><fmt:formatDate value="${newsDetail.createdate }" pattern="yyyy年MM月dd日 "/> </td>
				<td align="center"><a href="getAllCommentByNewsId?id=${newsDetail.id }">查看评论</a><a href="toAddComment?id=${newsDetail.id }">评论</a><a href="deleteNewsDetail?id=${newsDetail.id }">删除</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>