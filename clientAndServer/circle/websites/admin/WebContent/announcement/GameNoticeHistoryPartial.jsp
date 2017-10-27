<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>公告标题</th>
                <th>有效期</th>
                <th>选择游戏</th>
                <th>通知方式</th>
                <th>发布时间</th>
                <th>是否重复</th>
                <th>重复间隔</th>
                <th>重复单位</th>
                <th>操作人</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="s">
                <tr class="tab-detail">
                    <td>
                        <a href="/vGameAnnouncement/gameNoticeDetail.html?search.id=${s.id}"
                           nav-target="mainFrame">
                            ${fn:substring(s.title,0,30)}
                            <c:if test="${fn:length(s.title)>30}">
                                ...
                            </c:if>
                        </a>
                    </td>
                    <td class="text_c">
                        ${soulFn:formatDateTz(s.validityStartTime, DateFormat.DAY_SECOND,timeZone)}
                        &nbsp;-&nbsp;
                        ${soulFn:formatDateTz(s.validityEndTime, DateFormat.DAY_SECOND,timeZone)}
                    </td>
                    <td>
                        ${s.gameName}
                    </td>
                    <td>
                        <c:if test="${s.announcementType eq 'popup'}">弹窗</c:if>
                        <c:if test="${s.announcementType eq 'banner'}">跑马灯</c:if>
                    </td>
                    <td class="co-grayc2 text_c">
                        ${soulFn:formatDateTz(s.publishTime, DateFormat.DAY_SECOND,timeZone)}
                    </td>
                    <td>
                        <c:if test="${s.repeat}">是</c:if>
                        <c:if test="${!s.repeat}">否</c:if>
                    </td>
                    <c:if test="${s.repeat}">
                        <td class="text_r">
                            ${s.repeatTime}
                        </td>
                        <td>
                            <c:if test="${s.repeatUnit eq '10'}">天</c:if>
                            <c:if test="${s.repeatUnit eq '20'}">时</c:if>
                            <c:if test="${s.repeatUnit eq '30'}">分</c:if>
                            <c:if test="${s.repeatUnit eq '40'}">秒</c:if>
                        </td>
                    </c:if>
                    <c:if test="${!s.repeat}">
                        <td class="text_r">
                            -
                        </td>
                        <td class="text_r">
                            -
                        </td>
                    </c:if>
                    <td>
                        ${s.publishUserName}
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>