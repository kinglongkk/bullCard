<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningMultipleListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>序号</th>
                <th>用户名</th>
                <th>昵称</th>
                <%--<th>注册日期</th>--%>
                <th>所属代理</th>
                <th>日期</th>
                <%--<th>最后监控时间</th>--%>
                <th>累计赢得金额</th>
                <th>累计投注金额</th>
                <th>获得倍数</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${(command.paging.pageNumber-1)*command.paging.pageSize+(status.index+1)}</td>
                    <td>${p.username}</td>
                    <td>${p.nickname}</td>
                    <%--<td class="text_c">${soulFn:formatDateTz(p.registerDate, DateFormat.DAY_SECOND, timeZone)}</td>--%>
                    <td>${p.agentUsername}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.date, DateFormat.DAY, timeZone)}</td>
                    <%--<td class="text_c">${soulFn:formatDateTz(p.createTime, DateFormat.DAY_SECOND, timeZone)}</td>--%>
                    <td class="text_r">${soulFn:formatCurrency(p.winAmount)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.betAmount)}</td>
                    <td class="text_r">${p.multiple}</td>
                    <td>
                        <c:if test="${p.status == '1'}"><div style="border: solid 0px black;background-color: yellow;height: 20px;"></div></c:if>
                        <c:if test="${p.status == '2'}"><div style="border: solid 0px black;background-color: red;height: 20px;"></div></c:if>
                    </td>

                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/playerWarningMultiple/warninfo.html?id=${p.id}&playerId=${p.playerId}" nav-target="mainFrame">${views.common['detail']}</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="11" class="no-content_wrap">
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
