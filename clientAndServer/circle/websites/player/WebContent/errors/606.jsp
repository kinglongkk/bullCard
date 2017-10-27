<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>强制踢出</title>
</head>
<body>
<h3>${requestScope.errorMessage.title}</h3>
<div>${requestScope.errorMessage.message}</div>
<div><a href="#" onclick="javascript:window.top.location.href='${loginUrl}';">重新登录</a></div>
</body>
</html>