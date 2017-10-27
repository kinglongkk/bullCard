<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningWinCountListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>序号</th>
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
                <td class="text_r">${(command.paging.pageNumber-1)*command.paging.pageSize+(status.index+1)}</td>
                <td class="text_c">${soulFn:formatDateTz(p.datetime, DateFormat.DAY, timeZone)}</td>
                <td class="text_c">${p.agentname}</td>
                <td class="text_c">${p.playername}</td>
                <td class="text_c">${p.gamename}</td>
                <td class="text_c">${p.gamemodelname}</td>
                <td class="text_c">${soulFn:formatCurrency(p.profitamount)}</td>
                <td>
                    <div class="joy-list-row-operations text_c">
                        <a href="/jackpotReport/jackpot.html?sysuserid=${p.sysuserid}&gameid=${p.gameid}&gamemodelid=${p.gamemodelid}&startTimeStr=${startTimeStr}&endTimeStr=${endTimeStr}" nav-target="mainFrame">${views.common['detail']}</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
