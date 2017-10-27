<%--@elvariable id="result" type="g.model.master.player.po.Remark"--%>
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

        <form:input type="hidden" path="result.id" value="${result.id}"/>
        <form:input type="hidden" path="result.entityUserId" value="${result.entityUserId}"/>
        <form:input type="hidden" path="result.model" value="${result.model}"/>
        <form:input type="hidden" path="result.entityId" value="${result.entityId}"/>
        <form:input type="hidden" path="result.remarkType" value="${result.remarkType}" />
        <div class="modal-body">
            <%--<div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">${views.role['player.view.remark.remarkType']}：</label>
                <div class="input-group m-b col-xs-9">

                    <input class="form-control" value="${dicts.common.remark_type[command.result.remarkType]}" disabled/>
                </div>
            </div>--%>
            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">${views.playerAdmin['player.view.remark.remarkTitle']}：</label>
                <div class="input-group m-b col-xs-9">
                    <form:input class="form-control" path="result.remarkTitle" value="${result.remarkTitle}" maxlength="100"/>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">${views.playerAdmin['player.view.remark.remarkContent']}：</label>
                <div class="input-group m-b col-xs-9">
                    <form:textarea class="form-control" path="result.remarkContent" value="${result.remarkContent}" maxlength="200"></form:textarea>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="${views.common['save']}" opType="ajax" dataType="json" target="${root}/playerRemark/persist.html" post="getCurrentFormData"/>
            <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>