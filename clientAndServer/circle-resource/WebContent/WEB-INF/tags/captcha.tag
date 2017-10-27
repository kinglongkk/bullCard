<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="url" type="java.lang.String" required="true" description="Image Source URL - HTML Optional Attribute<"%>
<%@attribute name="alt" type="java.lang.String" required="false" description="规定图像的替代文本"%>
<%@attribute name="height" type="java.lang.Double" required="false" description="定义图像的高度"%>
<%@attribute name="width" type="java.lang.Double" required="false" description="设置图像的宽度"%>
<%@attribute name="cssStyle" type="java.lang.String" required="false" description="Equivalent to style - HTML Optional Attribute<"%>
<%@attribute name="clickToReload" type="java.lang.Boolean" required="false" description="点击是否重新加载"%>

<c:set var="random" value="<%= System.currentTimeMillis() %>"/>
<c:set var="alt" value="${alt == null ? '验证码' : alt}"/>

<c:choose>
    <c:when test="${not empty clickToReload && clickToReload }">
        <img src="${url}" class="${cssStyle}" alt="${alt}" reloadable height="${height}" width="${width}"/>
    </c:when>
    <c:otherwise>
        <img src="${url}" class="${cssStyle}" alt="${alt}" height="${height}" width="${width}"/>
    </c:otherwise>
</c:choose>