<%--@elvariable id="command" type="g.model.admin.agent.currency.vo.AgentCurrencyExchangeVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<html lang="zh-CN">
<head>
    <title>编辑</title>
    <%@ include file="/include/include.head.jsp" %>

</head>

<body>

<form:form id="editForm" action="${root}/agentCurrencyExchange/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>

    <div class="modal-body">

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                货币编码
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.code" cssClass="form-control m-b" value="${command.result.code}" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                货币名称
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.name" cssClass="form-control m-b" value="${command.result.name}" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                货币汇率(1:X)
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.exchange" cssClass="form-control m-b" placeholder="请输入X值" value="${command.result.exchange}" />
            </div>
        </div>

    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/agentCurrencyExchange/persist.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <c:choose>
            <c:when test="${command.result.id eq null or command.result.id eq ''}">
                <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
            </c:when>
            <c:otherwise>
                <soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/agentCurrencyExchange/delete.html?id=${command.result.id}" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />
            </c:otherwise>
        </c:choose>
    </div>
</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>

<soul:import type="edit"/>

</html>