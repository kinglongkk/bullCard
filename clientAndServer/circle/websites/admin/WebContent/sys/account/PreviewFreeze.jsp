<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div name="preview">
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
            ${dicts.common.freeze_time[sub.freezeTime]}
            <span class="m-l co-grayc2">${views.account['MasterManage.edit.PreviewFreeze.dealline']}：
                <c:if test="${sub.freezeTime eq '-99'}">
                    ${views.account['MasterManage.edit.CancelFreeze.forever']}
                </c:if>
                <c:if test="${!(sub.freezeTime eq '-99')}">
                    ${soulFn:formatDateTz(sub.freezeEndTime, DateFormat.DAY_SECOND, timeZone)}
                </c:if>
            </span>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-sm">
        <label class="col-xs-3 al-right "><span class="co-red m-r-sm">*</span>${views.account['MasterManage.edit.CancelFreeze.freezeReason']} : </label>
        <div class="col-xs-8 p-x">
            <textarea class="form-control" disabled>${sub.sysUser.freezeContent}</textarea>
            <span class="co-grayc2">${views.account['MasterManage.edit.PreviewFreeze.systemAnnouncementMaster']}</span>
        </div>
    </div>
</div>