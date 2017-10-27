<%--common js base--%>
<%@ include file="include.base.inc.jsp" %>
<%@ page import="g.web.common.SessionManagerCommon" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var curTheme = '${curTheme}';
    var root = '${root}';
    var resComRoot = '${resComRoot}';
    var resRoot = '${resRoot}';
    var fileRoot = '${fileRoot}';
    var imgRoot = '${imgRoot}';
    var random = '${random}';
    var utcOffSet = <%=SessionManagerCommon.getTimeZone().getRawOffset()/60/1000%>;
    var language = '${language.replace('_','-')}';
</script>


