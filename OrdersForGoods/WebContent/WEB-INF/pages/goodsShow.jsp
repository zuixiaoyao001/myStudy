<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<table align="center" border="1">
<thead align="center">区域商品查询结果列表</thead>
<tbody>
	<tr>
	<td>商品编号</td>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>库存数量</td>
	<td>订单状态</td>
	<td>操作</td>
	</tr>
	<c:forEach items="${list }" var="goods">
	<tr>
	<td>${goods.id }</td>
	<td>${goods.goodsName }</td>
	<td>${goods.goodsPrice }</td>
	<td>${goods.goodsCount }</td>
	<td>
		<c:if test="${goods.billStatus==0 }">
			<c:out value="未处理"/>
		</c:if>
		<c:if test="${goods.billStatus==1 }">
			<c:out value="处理中"/>
		</c:if>
		<c:if test="${goods.billStatus==2 }">
			<c:out value="已处理"/>
		</c:if>
	</td>
	<td><a href="toupdate?id=${goods.id }">修改</a><a href="delete?id=${goods.id }">删除</a></td>
	</tr>
	</c:forEach>
	<tr>
	<td colspan="6"><a href="toadd">增加商品信息</a></td>
	</tr>
</tbody>
		</table>
		
</body>
</html>