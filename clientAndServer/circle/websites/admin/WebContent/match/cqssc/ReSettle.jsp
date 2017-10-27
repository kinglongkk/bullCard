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
        <form:input type="hidden" path="search.resultId" value="${command.result.resultId}"/>
        <jsp:include page="Info.jsp"/>
        <div class="modal-footer">
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right">新的开奖号码：</label>
                <div class="col-xs-8 p-x">
                    <form:input path="result.result" cssClass="form-control"/>
                </div>
            </div>
            <soul:button cssClass="btn btn-filter" text="重新开奖"
                         confirm="您确认使用新的开奖号码进行重新开奖吗?所有本期的注单将重新结算."
                         opType="ajax" dataType="json"
                         target="${root}/match/cqssc/doReSettle.html" post="getCurrentFormData"/>
            <soul:button cssClass="btn btn-filter" target="closePage" opType="function" text="关闭窗口"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>