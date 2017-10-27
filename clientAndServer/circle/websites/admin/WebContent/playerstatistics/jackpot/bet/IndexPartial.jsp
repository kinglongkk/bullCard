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
                <th>玩家</th>
                <th>游戏</th>
                <th>游戏玩法</th>
                <th>房间</th>
                <th>单号</th>
                <th>下单金额</th>
                <th>盈亏结果</th>
                <th>抽水金额</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr class="tab-detail">
                <td class="text_r">${status.index+1}</td>
                <td class="text_c">${soulFn:formatDateTz(p.betTime, DateFormat.DAY, timeZone)}</td>
                <td class="text_c">${p.username}</td>
                <td class="text_c">${p.gamename}</td>
                <td class="text_c">${p.gamemodelname}</td>
                <td class="text_c">${p.gameroomname}</td>
                <td class="text_c">${p.betNo}</td>
                <td class="text_c">${p.effectiveAmount}</td>
                <td class="text_c">${p.profitAmount}</td>
                <td class="text_c">${p.pumpingamount}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
