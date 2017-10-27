<%--@elvariable id="command" type="g.model.report.vo.ReportCqsscListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered">
        <thead>
        <tr>
            <th width="80px">#</th>
            <th>期号</th>
            <th>开始时间</th>
            <th>投注额</th>
            <th>有效投注额</th>
            <th>输赢</th>
            <th>返点</th>
            <th width="70px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${p.matchCode}</td>
                <td>${soulFn:formatDateTz(p.beginTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${p.singleAmount}</td>
                <td>${p.effectiveAmount}</td>
                <td>${p.profitAmount}</td>
                <td>${p.rakebackAmount}</td>
                <td>
                    <div class="joy-list-row-operations">
                        <soul:button target="${root}/report/view.html?id=${p.id}" text="查看" opType="dialog" />
                    </div>
                </td>
            </tr>
        </c:forEach>
        <td>总计</td>
        <td colspan="2"></td>
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
