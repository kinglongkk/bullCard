<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form method="post">
    <jsp:include page="Info.jsp"/>
    <div class="modal-footer">
        <input type="hidden" name="search.id" value="${command.result.id}"/>
        <soul:button cssClass="btn btn-filter" target="cancel" opType="function" text="取消彩期" confirm="您确认取消吗?"/>
        <soul:button cssClass="btn btn-filter" target="closePage" opType="function" text="关闭窗口"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/match/cqssc/Edit"/>
