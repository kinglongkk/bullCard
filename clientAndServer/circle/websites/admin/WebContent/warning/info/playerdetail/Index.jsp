<%@ page import="org.soul.model.sys.po.SysAuditLog" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="org.soul.model.sys.vo.SysAuditLogListVo"--%>
<c:set var="poType" value="<%= SysAuditLog.class %>"></c:set>
<!--日志-->
<form name="playerdetailForm" action="${root}/vWarningPlayerDetail/betDetailInfo.html?sysUserId=${sysUserId}&date=${date}">
    <div class="search-list-container">
        <%@include file="IndexPartial.jsp"%>
    </div>
</form>
<script type="text/javascript">
    curl(["site/warning/playerdetail"], function (playerdetail) {
        page.playerdetail = new playerdetail();
    });
</script>
