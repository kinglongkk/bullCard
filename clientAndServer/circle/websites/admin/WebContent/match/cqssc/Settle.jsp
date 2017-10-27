<%--@elvariable id="command" type="g.model.match.vo.VMatchResultVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>
    <form:form method="post">
        <form:input type="hidden" path="search.id" value="${command.result.id}"/>
        <jsp:include page="Info.jsp"/>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="开奖" opType="ajax" dataType="json" target="${root}/match/cqssc/doSettle.html" post="getCurrentFormData"/>
            <soul:button cssClass="btn btn-filter" target="closePage" opType="function" text="关闭窗口"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>