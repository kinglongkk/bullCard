<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="modal-body">
    <h5 class="al-center">重庆时时彩</h5>
    <div class="clearfix bg-gray">
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">期号：</label>
            <div class="col-xs-8 p-x">${command.result.code}</div>
        </div>
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">开始时间：</label>
            <div class="col-xs-8 p-x">${soulFn:formatDateTz(command.result.beginTime,DateFormat.DAY_SECOND,timeZone)}</div>
        </div>
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">结束时间：</label>
            <div class="col-xs-8 p-x">${soulFn:formatDateTz(command.result.endTime,DateFormat.DAY_SECOND,timeZone)}</div>
        </div>
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">开奖号码：</label>
            <div class="col-xs-8 p-x">${command.result.result}</div>
        </div>

        <c:forEach items="${settleStateMap}" var="p">
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right">
                    <c:choose>
                        <c:when test="${p.settleStatus=='10'}">未结算:</c:when>
                        <c:when test="${p.settleStatus=='20'}">已结算:</c:when>
                    </c:choose>
                </label>
                <div class="col-xs-8 p-x">总计[${p.count}]注</div>
            </div>
        </c:forEach>
        <c:if test="${empty settleStateMap}">
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right">注单信息：</label>
                <div class="col-xs-8 p-x">总计[0]注</div>
            </div>
        </c:if>
    </div>
</div>

