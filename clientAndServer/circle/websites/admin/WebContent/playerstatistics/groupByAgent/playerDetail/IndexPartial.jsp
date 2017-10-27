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
                <th>玩家</th>
                <th>游戏</th>
                <th>游戏玩法</th>
                <th>房间</th>
                <th>注单号</th>
                <th>注单时间</th>
                <th>下单金额</th>
                <th>派彩金额</th>
                <th>盈亏结果</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.betDate, DateFormat.DAY, timeZone)}</td>
                    <td>${p.username}</td>
                    <td>${p.gameName}</td>
                    <td>${p.gameModelName}</td>
                    <td>${p.roomName}</td>
                    <td>${p.betNo}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.betTime, DateFormat.SECOND, timeZone)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.effectiveAmount)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.profitAmount)}</td>
                    <c:choose>
                        <c:when test="${p.winAmount > 0}">
                            <td class="table_color_green text_r">${soulFn:formatCurrency(p.winAmount)}</td>
                        </c:when>
                        <c:otherwise>
                            <td class="table_color_red text_r">${soulFn:formatCurrency(p.winAmount)}</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            <td>本页总计</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="text_r">${soulFn:formatCurrency(command.totalEffectiveAmount)}</td>
            <td class="text_r">${soulFn:formatCurrency(command.totalProfitAmount)}</td>
            <c:choose>
                <c:when test="${command.totalWinAmount > 0}">
                    <td class="table_color_green text_r">${soulFn:formatCurrency(command.totalWinAmount)}</td>
                </c:when>
                <c:otherwise>
                    <td class="table_color_red text_r">${soulFn:formatCurrency(command.totalWinAmount)}</td>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
