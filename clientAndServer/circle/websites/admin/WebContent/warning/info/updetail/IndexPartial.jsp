<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningMultipleListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h ">
    <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
        <thead>
            <tr>
                <th class="inline">序号</th>
                <th class="inline">日期时间</th>
                <th class="inline">玩家</th>
                <th class="inline">交易号</th>
                <th class="inline">交易金额</th>
                <th class="inline">账户余额</th>
                <th class="inline">转换后的金币</th>
                <th class="inline">状态</th>
                <th class="inline">备注</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr>
                    <td>${(command.paging.pageNumber-1)*command.paging.pageSize+(status.index+1)}</td>
                    <td>${soulFn:formatDateTz(p.completionTime, DateFormat.DAY_SECOND, timeZone)}</td>
                    <td>${p.username}</td>
                    <td>${p.transactionNo}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.transactionMoney<0}"><span style="color: red">${soulFn:formatCurrency(p.transactionMoney)}</span></c:when>
                            <c:otherwise>${soulFn:formatCurrency(p.transactionMoney)}</c:otherwise>
                        </c:choose>
                   </td>
                    <td>
                        <c:choose>
                            <c:when test="${p.balance<0}"><span style="color: red">${soulFn:formatCurrency(p.balance)}</span></c:when>
                            <c:otherwise>${soulFn:formatCurrency(p.balance)}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${p.gameCurrency}</td>
                    <td>${p.status}</td>
                    <td>${p.remark}</td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="9" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
