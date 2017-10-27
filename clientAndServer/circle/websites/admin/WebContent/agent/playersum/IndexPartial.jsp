<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>日期</th>
                <th>代理商</th>
                <th>玩家总数</th>
                <th>活跃玩家</th>
                <th>新增玩家</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.date, DateFormat.DAY, timeZone)}</td>
                    <td>${p.username}</td>
                    <td class="text_r">${soulFn:formatInteger(p.playerSummery)}</td>
                    <td class="text_r">${soulFn:formatInteger(p.activePlayerQty)}</td>
                    <td class="text_r">${soulFn:formatInteger(p.newPlayerQty)}</td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+6}" class="no-content_wrap">
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
