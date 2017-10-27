<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="table-responsive table-min-h ">
    <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
        <thead>
        <tr>
            <th class="inline">#</th>
            <th class="inline">时间</th>
            <th class="inline">额度</th>
            <th class="inline">类型</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr class="tab-title">
                <td>${status.index+1}</td>
                <td>${soulFn:formatDateTz(p.date, DateFormat.DAY,timeZone)}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.quota > 0}" >
                            +${soulFn:formatCurrency(p.quota)}
                        </c:when>
                        <c:otherwise>
                            ${soulFn:formatCurrency(p.quota)}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${p.type}</td>
            </tr>
        </c:forEach>
        <c:if test="${fn:length(command.result)<1}">
            <tr>
                <td colspan="${fn:length(command.fields)+4}" class="no-content_wrap">
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