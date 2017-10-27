<%--@elvariable id="command" type="g.model.account.vo.VAccountManageVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form method="post" action="">
    <input type="hidden" name="search.id" value="${command.search.id}"/>
    <div id="validateRule" style="display: none">${validateRule}</div>
    <div class="modal-body">
        <div name="editor">
            <h3 class="co-orange al-center">${views.account['MasterManage.edit.DisabledAccount.confirmDisabled']}</h3>
            <dd class="m-sm">${views.account['MasterManage.edit.DisabledAccount.accountDisabledAndUnderAccountAlsoDisabled']}</dd>
            <dd class="m-sm">${views.account['MasterManage.edit.DisabledAccount.topagentAndAgentNotLogin']}</dd>
            <dd class="m-sm">${views.account['MasterManage.edit.DisabledAccount.playerNotLogin']}</dd>
        </div>
    </div>
    <div class="modal-footer oneFoot">
        <soul:button target="sureStop" precall="validateForm" title="${views.common['OK']}" text="${views.common['OK']}" cssClass="btn btn-filter" opType="function"/>
    </div>
    <div class="modal-footer secFoot" style="display: none">
        <soul:button target="toConfirm"  text="" opType="function"  cssClass="btn btn-outline btn-filter">${views.common['OK']}</soul:button>
        <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-filter" opType="function"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/account/DisabledAccount"/>
</html>