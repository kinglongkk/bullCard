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
<form:form method="post">
    <div id="validateRule" style="display: none">${validate}</div>
    <input type="hidden" name="result.id" value="${command.result.id}"/>
    <input type="hidden" name="search.id" value="${command.result.id}"/>
    <input type="hidden" name="isExistPassword" value=""/>

    <div class="modal-body">
        <div name="editor">
            <div class="form-group clearfix line-hi34 m-b-xs">
                <label class="col-xs-3 al-right">${views.account['MasterManage.edit.CancelFreeze.username']} : </label>
                <div class="col-xs-8 p-x">
                        ${command.result.username}
                    <span class="${command.result.isonline>0?'co-green':'co-gray'}">&nbsp;${command.result.isonline>0?views.account['MasterManage.edit.CancelFreeze.online']:views.account['MasterManage.edit.CancelFreeze.outline']}</span>
                </div>
            </div>
            <div class="form-group clearfix line-hi34 m-b-sm">
                <label class="col-xs-3 al-right "><span class="co-red m-r-sm">*</span>${views.account['MasterManage.edit.CancelFreeze.freezeTime']} : </label>
                <div class="col-xs-8 p-x">
                    <gb:select name="freezeTime" cssClass="btn-group chosen-select-no-single ${command.result.isAllowOperate?'':'disabled'}" value="${empty command.result.timeInterval?'-99':command.result.timeInterval}" notUseDefaultPrompt="true" list="${freezeTime}" listKey="key" listValue="${dicts.common.freeze_time[key]}"/>
                </div>
            </div>
            <div class="form-group clearfix line-hi34 m-b-sm">
                <label class="col-xs-3 al-right"><span class="co-red m-r-sm">*</span>${views.account['MasterManage.edit.CancelFreeze.freezeReason']} : </label>
                <div class="col-xs-8 p-x">
                    <textarea name="sysUser.freezeContent" class="form-control ${command.result.isAllowOperate?'':'disabled'}"></textarea>
                    <c:if test="${!command.result.isAllowOperate}">
                        <span class="co-grayc2">${views.account['MasterManage.edit.CancelFreeze.operator']}：${command.result.freezeUsername}&nbsp;&nbsp;${soulFn:formatDateTz(command.result.freezeTime, DateFormat.DAY_SECOND, timeZone)}</span>
                    </c:if>
                    <span class="co-grayc2">${views.account['MasterManage.edit.PreviewFreeze.systemAnnouncementMaster']}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer addFoot ${command.result.isAllowOperate?'':'hide'}">
        <soul:button precall="validateForm" cssClass="btn btn-outline btn-filter" text="${views.account['MasterManage.edit.FreezeAccount.submitAndPreview']}" opType="function" target="previewFreezeAccount"/>
        <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-filter" opType="function"/>
    </div>
    <div class="modal-footer preFoot" style="display: none">
        <soul:button cssClass="btn btn-outline btn-filter" text="${views.account['MasterManage.edit.FreezeAccount.backModify']}" opType="function" target="cancelPreview"/>
        <soul:button precall="validateForm" target="submit" text="${views.account['MasterManage.edit.FreezeAccount.submit']}" cssClass="btn btn-filter" opType="function"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/account/FreezeAccount"/>
</html>
