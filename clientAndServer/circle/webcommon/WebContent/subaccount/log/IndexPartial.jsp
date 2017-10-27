<%--@elvariable id="command" type="org.soul.model.sys.vo.SysAuditLogListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <table class="table table-striped table-hover dataTable m-b-none" aria-describedby="editable_info">
        <thead>
        <tr role="row" class="bg-gray">
            <th>${views.report['log.title.no']}</th>
            <th>
                <gb:select name="search.operatorUserType" list="${roleKeys}" callback="query" prompt="${views.report['log.query.username']}" value="${command.search.operatorUserType}" cssClass="chosen-select-no-single" listKey="key" listValue="value" />
            <th>${views.log['log.title.oprtime']}</th>
            <th>${views.log['log.query.ip']}</th>
            <th>${views.log['log.title.client']}</th>
            <th class="inline">
                <gb:select name="search.operateType" value="${command.search.operateType}" cssClass="btn-group chosen-select-no-single" prompt="${views.log['log.title.allopr']}" list="${opType}" callback="query"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cmd" items="${command.result}" varStatus="vs">
        <tr>
            <td>${vs.count + ((command.paging.pageNumber - 1) * command.paging.pageSize)}</td>
            <td>${cmd.operator}</td>
            <td>${soulFn:formatDateTz(cmd.operateTime, DateFormat.DAY_SECOND, timeZone)}</td>
            <td>${soulFn:formatIp(cmd.operateIp)}<br>${gbFn:getIpRegion(cmd.operateIpDictCode)}</td>
            <td>${views.log['log.label.os']}${cmd.clientOs}&nbsp;&nbsp;${views.log['log.label.browser']}${cmd.clientBrowser}</td>
            <td>${soulFn:formatLogDesc(cmd)}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
