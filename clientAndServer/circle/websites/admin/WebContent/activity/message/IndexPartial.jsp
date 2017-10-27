<%--@elvariable id="command" type="g.model.activitymessage.vo.ActivityMessageListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>活动类型</th>
                <th>是否展示</th>
                <th>排序</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>更新人</th>
                <th>更新时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr>
                    <td class="text_r">${status.index+1}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.startTime, DateFormat.DAY,timeZone)}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.endTime, DateFormat.DAY,timeZone)}</td>
                    <td>
                        <c:forEach items="${activityTypeEnum}" var="activityClassifyKeyName">
                            <c:choose>
                                <c:when test="${ p.activityClassifyKey eq activityClassifyKeyName.code}">
                                ${activityClassifyKeyName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${p.isDisplay eq false}">否</c:when>
                            <c:when test="${p.isDisplay eq true}">是</c:when>
                        </c:choose>
                    </td>
                    <td class="text_r">${p.orderNum}</td>
                    <td>${p.createUserName}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.createTime, DateFormat.DAY,timeZone)}</td>
                    <td>${p.updateUserName}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.updateTime, DateFormat.DAY,timeZone)}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/activityMessage/view.html?id=${p.id}" nav-target="mainFrame">查看</a>
                            <a href="/activityMessage/edit.html?id=${p.id}" nav-target="mainFrame">编辑</a>
                            <soul:button target="${root}/activityMessage/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+11}" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->