<%@ include file="include.base.inc.jsp" %>
<%@ page import="g.web.player.session.SessionManager" %>
<%@ page import="g.web.player.init.WebConfigManager" %>

<c:set var="wsPort" value='<%= WebConfigManager.getConfig().getWsPort() %>' />
