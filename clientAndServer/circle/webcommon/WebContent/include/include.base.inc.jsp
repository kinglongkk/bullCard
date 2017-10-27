<%--@elvariable id="DateFormat" type="org.soul.web.locale.DateFormat"--%>
<%--@elvariable id="dateQPicker" type="org.soul.web.locale.DateQuickPicker"--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="org.soul.web.init.BaseConfigManager" %>
<%@page import="org.soul.web.locale.DateQuickPicker" %>
<%@page import="g.web.common.SessionManagerCommon" %>
<%@page import="java.text.MessageFormat" %>
<%@page trimDirectiveWhitespaces="true" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@taglib uri="http://soul/tags" prefix="soul" %>
<%@taglib uri="http://soul/fnTag" prefix="soulFn"  %>
<%@taglib uri="http://gb/fnTag" prefix="gbFn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="gb" %>

<c:set var="random" value="<%= System.currentTimeMillis() %>"/>
<c:set var="language" value="<%= SessionManagerCommon.getLocale().toString() %>"/>
<c:set var="DateFormat" value="<%= new org.soul.web.locale.DateFormat() %>"/>
<c:set var="timeZone" value="<%= SessionManagerCommon.getTimeZone() %>"/>
<c:set var="dateQPicker" value="<%=DateQuickPicker.getInstance() %>"/>
<c:set var="curTheme" value="default"/>

<c:set var="domain" value="<%= request.getServerName() %>"/>

<c:set var="root" value='<%= MessageFormat.format(BaseConfigManager.getConfigration().getRoot(),request.getServerName()) %>' />
<c:set var="resComRoot" value='<%= MessageFormat.format(BaseConfigManager.getConfigration().getResComRoot(),request.getServerName()) %>' />
<c:set var="resRoot" value='<%= MessageFormat.format(BaseConfigManager.getConfigration().getResRoot(),request.getServerName()) %>' />
<c:set var="fileRoot" value='<%= MessageFormat.format(BaseConfigManager.getConfigration().getFileRoot(),request.getServerName()) %>' />
<c:set var="imgRoot" value='<%= MessageFormat.format(BaseConfigManager.getConfigration().getImgRoot(),request.getServerName()) %>' />

