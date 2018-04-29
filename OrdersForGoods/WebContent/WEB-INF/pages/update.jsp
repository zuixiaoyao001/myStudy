<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品信息</title>
</head>
<body>
	<form action="update" method="post">
	<table>
	<tr><td>商品编号</td><td><input type="text" name="id" value="${goods.id }"/></td></tr>
	<tr><td>商品名称</td><td><input type="text" name="goodsName" value="${goods.goodsName }"/></td></tr>
	<tr><td>商品价格</td><td><input type="text" name="goodsPrice" value="${goods.goodsPrice }"/></td></tr>
	<tr><td>库存数量</td><td><input type="text" name="goodsCount" value="${goods.goodsCount }"/></td></tr>
	<tr><td>订单状态</td>
	    <td><select name="billStatus">
		    		<option value="0" <c:if test="${goods.billStatus==0 }">selected="selected"</c:if>>未处理</option>	
		    		<option value="1" <c:if test="${goods.billStatus==1 }">selected="selected"</c:if>>处理中</option>
		    		<option value="2" <c:if test="${goods.billStatus==2 }">selected="selected"</c:if>>已处理</option>	
			</select>
		</td></tr>
	<tr><td colspan="2"><input type="submit" value="提交"/><input type="button" onclick="goback(-1)" value="返回"/></td>
	</table>
	</form>
</body>
</html>