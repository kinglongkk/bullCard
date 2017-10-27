<%@ page import="org.soul.model.sys.po.SysAuditLog" %>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<c:set var="poType" value="<%= SysAuditLog.class %>"></c:set>
<div class="table-responsive table-min-h ">
    <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
        <thead>
            <tr>
                <soul:orderColumn poType="${poType}" property="id" column="${views.column['SysAuditLog.operateTime']}"/>
                <th class="inline">
                    <select callback="searchQuery" class="btn-group chosen-select-no-single" name="search.operatorUserType">
                        <option value="">${views.column['SysAuditLog.roles']}</option>
                        <c:forEach var="i" items="${userType}">
                            <c:if test="${i.key=='20'||i.key=='21'}">
                                <option value="${i.key}" ${command.search.operatorUserType==i.key?'selected':''}>${dicts.common.user_type[i.key]}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </th>
                <th class="inline">
                    <gb:select callback="searchQuery" name="search.operateType" cssClass="btn-group chosen-select-no-single" prompt="${views.common['allOperate']}" list="${opType}" listKey="key" listValue="${dicts.log.op_type[key]}" value="${command.search.operateType}"/>
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="l" varStatus="vs">
                <tr class="tab-title" ${(vs.index % 2)==0 ? 'class="gradeA odd"':' class="gradeA even"'}>
                    <td>${soulFn:formatDateTz(l.operateTime, DateFormat.DAY_SECOND, timeZone)}</td>
                    <td>${dicts.common.user_type[l.operatorUserType]}${l.operatorUserType!=24?'_'.concat(l.operator) : ''}</td>
                    <td>${soulFn:formatLogDesc(l)}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>