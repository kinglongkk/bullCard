<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h">
    <table class="table m-b-none">
        <thead>
        <tr role="row" class="bg-gray">
            <th>${views.column['sysNotice.type']}</th>
            <th>${views.column['sysNotice.title']}</th>
            <th>${views.column['sysNotice.publishTime']}</th>
            <th>${views.column['sysNotice.operatorPerson']}</th>
            <th>${views.column['sysNotice.publishStatus']}</th>
            <th>${views.column['sysNotice.status']}</th>
            <th>${views.column['sysNotice.operator']}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="s">
            <tr>
                <td>
                    <span>
                        ${views.column["sysNotice.".concat(s.announcementType)]}
                    </span>
                </td>
                <td class="al-left">
                    <a href="/agentAnnouncement/agentNoticeDetail.html?search.id=${s.id}"
                       nav-target="mainFrame">${fn:substring(s.title,0,30)}<c:if test="${fn:length(s.title)>30}">...</c:if></a>
                </td>

                <td class="co-grayc2">
                    <c:if test="${s.publishStatus>0}">
                        ${views.column['sysNotice.estimate']}${soulFn:formatDateTz(s.publishTime, DateFormat.DAY_SECOND,timeZone)}${views.column['sysNotice.publish']}
                    </c:if>
                    <c:if test="${s.publishStatus<=0}">
                        ${soulFn:formatDateTz(s.publishTime, DateFormat.DAY_SECOND,timeZone)}
                    </c:if>
                </td>
                <td>
                        ${s.publishUserName}
                </td>
                <td>
                    ${s.publishStatus>0?views.column['sysNotice.waitpublish']:views.column['sysNotice.publishfinish']}
                </td>
                <td>
                        ${views.column[s.status]}
                </td>
                <td data-id="${s.id}">
                    <soul:button target="updateAnnouncementStatus" dataVal="enable" text="${views.column['enable']}" opType="function" confirm="${views.column['sysNotice.enableConfirmcancel']}？"
                                 cssClass="m-l-xs"/>
                    <soul:button target="updateAnnouncementStatus" dataVal="disable" text="${views.column['disable']}" opType="function" confirm="${views.column['sysNotice.disableConfirmcancel']}？"
                                 cssClass="m-l-xs"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>