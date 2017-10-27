<%@page contentType="text/html;charset=UTF-8" %>
<meta charset="utf-8" />
<meta name="keywords" content="keywords"/>
<meta name="description" content="description"/>
<meta name="author" content="author" />
<meta name="Copyright" content="author" />
<meta name="viewport" content="width=device-width"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<link rel="shortcut icon" href="${resRoot}/images/favicon.ico"/>
<link rel="apple-touch-icon" href="${resRoot}/images/touchicon.png"/>
<%
    String url = request.getRequestURI();
    if (url.indexOf("front/app/member") == -1){
%>
<link rel="stylesheet" type="text/css" href="${resRoot}/themes/${curTheme}/common.css"/>
<%
    }
%>