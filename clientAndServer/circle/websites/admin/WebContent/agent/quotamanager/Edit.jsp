<%--@elvariable id="command" type="g.model.admin.agent.quotamanager.vo.VAgentQuotaVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->

<html lang="zh-CN">
<head>
    <title>充值</title>
    <%@ include file="/include/include.head.jsp" %>
    <!--//region your codes 2-->

    <!--//endregion your codes 2-->
</head>

<body>

<form:form id="editForm" action="${root}/vAgentQuota/edit.html" method="post">
    <form:hidden path="result.id" />
    <form:hidden path="result.agentId" value="${command.result.agentId}" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>

    <!--//region your codes 3-->
    <div class="modal-body">

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                充值金额
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.amount" />
            </div>
        </div>

    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-default" text="保存" opType="ajax" dataType="json" target="${root}/vAgentQuota/persist.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
    </div>

    <!--//endregion your codes 3-->

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="edit"/>
<!--//endregion your codes 4-->
</html>