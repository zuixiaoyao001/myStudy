<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新闻评论详细页面</title>
<!-- <style type="text/css">
	
	#ncl{font-size: 30px;
			text-align: center;
	}
</style> -->
</head>
<body>

	<table align="center" border="1">
			<tr >
			<td align="center" colspan="2">评论列表<input type="submit" value="返回新闻列表" onclick="history.go(-1)"/></td></tr>
		
			<tr>
				<td align="center">评论编号</td>
				<td align="center">评论内容</td>
				<td align="center">评论人</td>
				<td align="center">评论时间</td>
			</tr>
			<c:forEach items="${list }" var="newsComment">
			<tr>
				<td align="center">${newsComment.id }</td>
				<td align="center">${newsComment.content }</td>
				<td align="center">${newsComment.author }</td>
				<td align="center"><fmt:formatDate value="${newsComment.createdate }" pattern="yyyy年MM月dd日 "/> </td>
			</tr>
			</c:forEach>
		
	</table>

</body>
</html>