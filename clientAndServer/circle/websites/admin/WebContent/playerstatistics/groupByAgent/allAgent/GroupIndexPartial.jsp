<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
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
                    <td class="text_c">${soulFn:formatDateTz(p.betDate, DateFormat.DAY, timeZone)}</td>
                    <td>${p.ownerUsername}</td>
                    <td>${p.gameName}</td>
                    <td>${p.gameModelName}</td>
                    <c:choose>
                        <c:when test="${p.winAmount > 0}">
                            <td class="table_color_green text_r">${soulFn:formatCurrency(p.winAmount)}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="table_color_red text_r">${soulFn:formatCurrency(p.winAmount)}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/playerDataStatistics/ownerPlayerList.html?search.startTime=${soulFn:formatDateTz(p.betDate, DateFormat.DAY, timeZone)}&search.ownerUsername=${p.ownerUsername}&search.gameId=${p.gameId}&search.gameModelId=${p.gameModelId}" nav-target="mainFrame">
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
            <c:choose>
                <c:when test="${command.totalWinAmount > 0}">
                    <td class="table_color_green text_r">${soulFn:formatCurrency(command.totalWinAmount)}</td>
                </c:when>
                <c:otherwise>
                    <td class="table_color_red text_r">${soulFn:formatCurrency(command.totalWinAmount)}</td>
                </c:otherwise>
            </c:choose>
            <td></td>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
