<%--@elvariable id="command" type="g.model.report.vo.ReportCqsscListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered">
        <thead>
        <tr>
            <th width="80px">#</th>
            <th>日期</th>
            <th>场次</th>
            <th>总投注额</th>
            <th>总有效投注额</th>
            <th>总输赢</th>
            <th>总返点</th>
            <th width="70px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${soulFn:formatDateTz(p.beginTime, DateFormat.DAY,timeZone)}</td>
                <td>${p.totalNumber}</td>
                <td>${p.singleAmount}</td>
                <td>${p.effectiveAmount}</td>
                <td>${p.profitAmount}</td>
                <td>${p.rakebackAmount}</td>
                <td>
                    <div class="joy-list-row-operations">
                        <a href="/report/agentReport.html?search.startTime=${soulFn:formatDateTz(p.beginTime, DateFormat.DAY,timeZone)}" nav-target="mainFrame">
                            查看
                        </a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <td>总计</td>
        <td></td>
        <td>${soulFn:formatNumber(command.totalNumber)}</td>
        <td>${soulFn:formatCurrency(command.totalSingleAmount)}</td>
        <td>${soulFn:formatCurrency(command.totalEffectiveAmount)}</td>
        <td>${soulFn:formatCurrency(command.totalProfitAmount)}</td>
        <td>${soulFn:formatCurrency(command.totalRakebackAmount)}</td>
        <td></td>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
