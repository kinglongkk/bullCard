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
        <div id="validateRule" style="display: none">${command.validateRule}</div>

        <form:input type="hidden" path="result.id" value="${command.result.id}"/>
        <form:input type="hidden" path="result.resultId" value="${command.result.resultId}"/>
        <div class="modal-body">
            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">结果：</label>
                <div class="input-group m-b col-xs-9">
                    <form:input path="result.result" cssClass="form-control"/>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="${views.common['save']}" opType="ajax" dataType="json" target="${root}/match/cqssc/update.html" post="getCurrentFormData"/>
            <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>