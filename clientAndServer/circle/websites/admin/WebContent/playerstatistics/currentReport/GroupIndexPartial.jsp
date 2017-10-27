<%--@elvariable id="command" type="g.model.bet.vo.VBetDetailListVo"--%>
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
                <th>玩家</th>
                <th>游戏</th>
                <th>游戏玩法</th>
                <th>盈亏</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.settleTime, DateFormat.DAY, timeZone)}</td>
                    <td>${p.ownerUsername}</td>
                    <td>${p.username}</td>
                    <td>${p.gameName}</td>
                    <td>${p.gameModelName}</td>
                    <c:choose>
                        <c:when test="${p.profitAmount > 0}">
                            <td class="table_color_green text_r">${soulFn:formatCurrency(p.profitAmount)}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="table_color_red text_r">${soulFn:formatCurrency(p.profitAmount)}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/currentReport/detail.html?search.ownerId=${p.ownerId}&search.sysUserId=${p.sysUserId}&search.gameId=${p.gameId}&search.gameModelId=${p.gameModelId}" nav-target="mainFrame">
                                查看
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <td>本页总计</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <c:choose>
                <c:when test="${command.totalProfitAmount > 0}">
                    <td class="table_color_green text_r">${soulFn:formatCurrency(command.totalProfitAmount)}</td>
                </c:when>
                <c:otherwise>
                    <td class="table_color_red text_r">${soulFn:formatCurrency(command.totalProfitAmount)}</td>
                </c:otherwise>
            </c:choose>
            <td></td>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
