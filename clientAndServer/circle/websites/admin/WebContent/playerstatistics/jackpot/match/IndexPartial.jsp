<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningWinCountListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>序号</th>
                <th>房间名称</th>
                <th>开局时间</th>
                <th>局号</th>
                <th>变量奖池金额</th>
                <th>奖池溢出金额</th>
                <th>手续费金额</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr class="tab-detail">
                <td class="text_r">${(command.paging.pageNumber-1)*command.paging.pageSize+(status.index+1)}</td>
                <td class="text_c">${p.gameroomname}</td>
                <td class="text_c">${soulFn:formatDateTz(p.beginTime, DateFormat.DAY_SECOND, timeZone)}</td>
                <td class="text_c">${p.id}</td>
                <td class="text_c">${soulFn:formatCurrency(p.jackpot)}</td>
                <td class="text_c">${soulFn:formatCurrency(p.jackpotOverflow)}</td>
                <td class="text_c">${soulFn:formatCurrency(p.pumpingamount)}</td>
                <td>
                    <div class="joy-list-row-operations text_c">
                        <a href="/jackpotReport/jackpotBet.html?matchId=${p.id}" nav-target="mainFrame">${views.common['detail']}</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
