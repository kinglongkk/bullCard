<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<input type="hidden" name="sysUser.id" value="${command.result.id}"/>
<div class="form-group clearfix line-hi34 m-b-xs">
    <label class="col-xs-3 al-right">${views.account['MasterManage.edit.ConfirmDisabled.masterUsername']} : </label>
    <div class="col-xs-8 p-x">
        ${command.result.username}
        <span class="${command.result.isonline>0?'co-green':'co-gray'}">&nbsp;${command.result.isonline>0?views.account['MasterManage.edit.ConfirmDisabled.online']:views.account['MasterManage.edit.ConfirmDisabled.outline']}</span>
    </div>
</div>
<div class="form-group clearfix line-hi34 m-b-xs">
    <label class="col-xs-3  al-right">${views.account['MasterManage.edit.ConfirmDisabled.createTime']} : </label>
    <div class="col-xs-8 p-x">${soulFn:formatDateTz(command.result.createTime, DateFormat.DAY_SECOND, timeZone)}</div>
</div>
<div class="gray-chunk clearfix">
    <dd class="pull-left p-xs text-oflow-20">
        <b>${views.account['MasterManage.edit.ConfirmDisabled.playerCount']}:</b>
        <span class="m-l-xs">${empty masterCalInfo.result.playerNum?0:masterCalInfo.result.playerNum}${views.account['MasterManage.edit.ConfirmDisabled.number']}</span>
    </dd>
</div>