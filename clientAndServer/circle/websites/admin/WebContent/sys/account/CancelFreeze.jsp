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
<form:form method="post" action="${root}/accountManage/saveCancelFreeze.html">
    <input type="hidden" name="sysUser.id" value="${command.result.id}"/>
    <input type="hidden" name="search.id" value="${command.result.id}"/>
    <div id="validateRule" style="display: none">${validate}</div>
    <div class="modal-body">
        <div class="form-group clearfix line-hi34 m-b-xs">
            <label class="col-xs-3 al-right">${views.account['MasterManage.edit.CancelFreeze.username']} : </label>
            <div class="col-xs-8 p-x">
                ${command.result.username}
                <span class="${command.result.isonline>0?'co-green':'co-gray'}">&nbsp;${command.result.isonline>0?views.account['MasterManage.edit.CancelFreeze.online']:views.account['MasterManage.edit.CancelFreeze.outline']}</span>
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-sm">
            <label class="col-xs-3 al-right ">
                <span class="co-red m-r-sm">*</span>${views.account['MasterManage.edit.CancelFreeze.freezeTime']} :
            </label>
            <div class="col-xs-8 p-x">
                <c:choose>
                    <c:when test="${command.result.timeInterval<=3}">
                        ${views.account['MasterManage.edit.CancelFreeze.treeHours']}
                    </c:when>
                    <c:when test="${command.result.timeInterval<=6}">
                        ${views.account['MasterManage.edit.CancelFreeze.sixHours']}
                    </c:when>
                    <c:when test="${command.result.timeInterval<=12}">
                       ${views.account['MasterManage.edit.CancelFreeze.twelveHours']}
                    </c:when>
                    <c:when test="${command.result.timeInterval<=24}">
                        ${views.account['MasterManage.edit.CancelFreeze.twentyFourHours']}
                    </c:when>
                    <c:when test="${command.result.timeInterval<=72}">
                       ${views.account['MasterManage.edit.CancelFreeze.seventyTwoHours']}
                    </c:when>
                    <c:otherwise>
                        ${views.account['MasterManage.edit.CancelFreeze.forever']}
                    </c:otherwise>
                </c:choose>
                <span class="m-l co-grayc2">${views.account['MasterManage.edit.CancelFreeze.deadline']}：${soulFn:formatDateTz(command.result.freezeEndTime, DateFormat.DAY_SECOND, timeZone)}</span>
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-sm">
            <label class="col-xs-3 al-right ">
                <span class="co-red m-r-sm">*</span>${views.account['MasterManage.edit.CancelFreeze.freezeReason']}</label>
                <div class="col-xs-8 p-x">
                    <div class="gray-chunk clearfix">${command.result.freezeContent}</div>
                    <span class="co-grayc2">${views.account['MasterManage.edit.CancelFreeze.operator']}：${command.result.freezeUsername}&nbsp;&nbsp;${soulFn:formatDateTz(command.result.freezeStartTime, DateFormat.DAY_SECOND, timeZone)}</span>
                </div>
        </div>
    </div>
    <div class="modal-footer">
        <soul:button precall="validateForm" cssClass="btn btn-filter" text="确认" opType="function" target="cancelFreezeAccount"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/account/CancelFreezeAccount"/>
</html>