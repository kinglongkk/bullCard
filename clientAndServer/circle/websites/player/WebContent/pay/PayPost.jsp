<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>充值接口-提交信息</title>
</head>
<body onload="pay.submit()">
<form method="post" name="pay" id="pay" action="${payUrl}">
    <c:forEach items="${payParamMap}" var="item">
        <input name='${item.key}' type='hidden' value= "${item.value}"/>
    </c:forEach>
</form>
</body>
</html>