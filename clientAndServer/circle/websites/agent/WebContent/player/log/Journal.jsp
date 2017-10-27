<%@ page import="org.soul.model.sys.po.SysAuditLog" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="org.soul.model.sys.vo.SysAuditLogListVo"--%>
<c:set var="poType" value="<%= SysAuditLog.class %>"></c:set>
<!--日志-->
<form name="journalForm" action="${root}/playerLog/view/journal.html?search.entityId=${command.search.entityId}">
    <div class="search-list-container">
        <%@include file="JournalPartial.jsp"%>
    </div>
</form>
<script type="text/javascript">
    curl(["site/player/log/Journal"], function (Journal) {
        page.journal = new Journal();
    });
</script>
