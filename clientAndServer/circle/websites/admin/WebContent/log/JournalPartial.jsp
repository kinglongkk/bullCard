<%--@elvariable id="command" type="org.soul.model.sys.vo.SysAuditLogListVo"--%>
<%@ page import="org.soul.model.sys.po.SysAuditLog" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<c:set var="poType" value="<%= SysAuditLog.class %>"></c:set>
<div class="scope_wrap clearfix">
    <div class="col-sm-6 form-inline al-left">
        ${views.role['Player.detail.log.timeRange']}
        <div class="input-group date">
            <gb:dateRange useRange="true" format="${DateFormat.DAY}" callback="query" startName="search.operatorBegin"
                          endName="search.operatorEnd" startDate="${command.search.operatorBegin}"
                          endDate="${command.search.operatorEnd}"/>
        </div>
    </div>
    <a class="pull-right"
       href="/report/log/logList.html?search.moduleType=1&search.entityUserId=${command.search.entityUserId}"
       nav-target="mainFrame">${views.role['Player.detail.log.playerLog']}</a>
</div>
<div class="dataTables_wrapper" role="grid">
    <div class="table-responsive table-min-h">
        <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
            <div class="table-responsive" id="tab-1">
                <table class="table table-striped table-bordered table-hover dataTable" name="journalTable">
                    <thead>
                    <tr>
                        <th>${views.playerAdmin['log.title.no']}</th>
                        <th>${views.playerAdmin['log.title.oprtime']}</th>
                        <th>${views.playerAdmin['log.query.ip']}</th>
                        <th>${views.playerAdmin['log.title.client']}</th>
                        <th class="inline">
                            <gb:select callback="journal.query" name="search.operateType"
                                       cssClass="btn-group chosen-select-no-single"
                                       prompt="${views.playerAdmin['allOperate']}" list="${opType}"
                                       listKey="key"
                                       listValue="${dicts.log.op_type[key]}" value="${command.search.operateType}"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${command.result}" var="l" varStatus="vs">
                        <tr ${(vs.index % 2)==0 ? 'class="gradeA odd"':' class="gradeA even"'}>
                            <td>${vs.index+1}</td>
                            <td>${soulFn:formatDateTz(l.operateTime, DateFormat.DAY_SECOND, timeZone)}</td>
                            <td>${soulFn:formatIp(l.operateIp)}<br>${gbFn:getIpRegion(l.operateIpDictCode)}</td>
                            <td>${views.column['log.label.os']}${l.clientOs}&nbsp;&nbsp;${views.column['log.label.browser']}${l.clientBrowser}</td>
                            <td>${soulFn:formatLogDesc(l)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<soul:pagination/>