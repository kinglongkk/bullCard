<%--@elvariable id="command" type="g.model.match.vo.VBetListListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@page import="g.model.cache.CacheBase" %>
<%@page import="g.model.common.Const" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered dataTable">
        <thead>
        <tr>
            <th>${views.common['number']}</th>
            <th>${views.column['VBetList.betNo']}</th>
            <th>${views.column['VBetList.username']}</th>
            <th>${views.column['VBetList.betTime']}</th>
            <th>${views.column['VBetList.betType']}</th>
            <th>${views.column['VBetList.iorField']}</th>
            <th>${views.column['VBetList.singleAmount']}</th>
            <th>${views.column['VBetList.profitAmount']}</th>
            <th>${views.column['VBetList.effectiveAmount']}</th>
            <th>${views.column['VBetList.status']}</th>
            <th>${views.column['VBetList.settleStatus']}</th>
            <th>${views.column['VBetList.beginTime']}</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="league" value="<%=CacheBase.getLeagueI18n(Const.Default_Locale)%>"/>
        <c:set var="team" value="<%=CacheBase.getTeamI18n(Const.Default_Locale)%>"/>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr>
                <td>${(command.paging.pageNumber-1)*command.paging.pageSize+status.index+1}</td>
                <td>${p.betNo}</td>
                <td>${p.username}</td>
                <td>${soulFn:formatDateTz(p.betTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:trans("g.model.bet.ssc.enums.BetTypeEnum",p.betType)}</td>
                <td>${p.desc}</td>
                <td>${soulFn:formatCurrency(p.singleAmount)}</td>
                <td>${empty p.profitAmount?'--':p.profitAmount}</td>
                <td>${empty p.effectiveAmount?'--':p.effectiveAmount}</td>
                <td><c:choose>
                    <c:when test="${p.status=='10'}">未确认</c:when>
                    <c:when test="${p.status=='20'}">已确认</c:when>
                    <c:when test="${p.status=='30'}">取消</c:when>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${p.settleStatus=='10'}">未结算</c:when>
                        <c:when test="${p.settleStatus=='20'}">已结算</c:when>
                    </c:choose>
                </td>
                <td>${soulFn:formatDateTz(p.beginTime, DateFormat.DAY_SECOND,timeZone)}</td>
            </tr>
        </c:forEach>
        <td>${views.common['total']}</td>
        <td colspan="5"></td>
        <td>${soulFn:formatCurrency(command.totalSingleAmount)}</td>
        <td>${soulFn:formatCurrency(command.totalEffectiveAmount)}</td>
        <td>${soulFn:formatCurrency(command.totalSendcach)}</td>
        <td colspan="4"></td>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
