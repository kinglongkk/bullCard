<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningMultipleListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h ">
    <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
        <thead>
            <tr>
                <th class="inline">序号</th>
                <th class="inline">注单日期</th>
                <th class="inline">玩家</th>
                <th class="inline">游戏</th>
                <th class="inline">游戏玩法</th>
                <th class="inline">房间名</th>
                <th class="inline">注单号</th>
                <th class="inline">下单金额</th>
                <th class="inline">派彩金额</th>
                <th class="inline">盈亏结果</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr>
                    <td>${(command.paging.pageNumber-1)*command.paging.pageSize+(status.index+1)}</td>
                    <td>${soulFn:formatDateTz(p.betTime, DateFormat.DAY_SECOND, timeZone)}</td>
                    <td>${p.username}</td>
                    <td>${p.gamename}</td>
                    <td>${p.gamemodelname}</td>
                    <td>${p.gameroomname}</td>
                    <td>${p.betNo}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.singleAmount<0}"><span style="color: red">${soulFn:formatCurrency(p.singleAmount)}</span></c:when>
                            <c:otherwise>${soulFn:formatCurrency(p.singleAmount)}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${p.effectiveAmount<0}"><span style="color: red">${soulFn:formatCurrency(p.effectiveAmount)}</span></c:when>
                            <c:otherwise>${soulFn:formatCurrency(p.effectiveAmount)}</c:otherwise>
                        </c:choose>
                   </td>
                    <td>
                     <c:choose>
                        <c:when test="${p.profitAmount<0}"><span style="color: red">${soulFn:formatCurrency(p.profitAmount)}</span></c:when>
                        <c:otherwise>${soulFn:formatCurrency(p.profitAmount)}</c:otherwise>
                     </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="10" class="no-content_wrap">
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
