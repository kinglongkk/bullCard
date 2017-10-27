<%--@elvariable id="command" type="g.model.agent.vo.VSubAccountListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h">
    <table class="table table-striped table-hover dataTable" aria-describedby="editable_info">
        <thead>
        <tr role="row" class="bg-gray">
            <th><input type="checkbox" class="i-checks"></th>
            <th>${views.subAccount['list.ID']}</th>
            <th>${views.subAccount['list.username']}</th>
            <th>${views.subAccount['list.nickname']}</th>
            <th>${views.subAccount['list.role']}</th>
            <th>${views.subAccount['list.createTime']}</th>
            <th class="inline">
                <select class="btn-group chosen-select-no-single" name="search.status" callback="query">
                    <option value="">全部</option>
                    <c:forEach items="${command.userStatus}" var="status">
                        <option value="${status}" ${command.search.status eq status ? 'selected':''}>
                            ${dicts.player.user_status[status]}
                        </option>
                    </c:forEach>
                </select>
            </th>
            <th>${views.common['operate']}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="re">
            <tr class="gradeA odd tab-detail">
                <td class="_frist_td"><input type="checkbox" class="i-checks" value="${re.id}" data-builtIn="${re.builtIn}" data-using="${re.status eq '1'}"></td>
                <td>${re.id}</td>
                <td>${re.username}</td>
                <td>${re.nickname}</td>
                <td>
                    <c:forEach items="${re.roleNames}" var="name" varStatus="status">
                        ${ empty views.subAccount["role.".concat(name)] ? name:views.subAccount["role.".concat(name)]}<c:if test="${not status.last}">、</c:if>
                    </c:forEach>
                </td>
                <td>${soulFn:formatDateTz(re.createTime,DateFormat.DAY_SECOND,timeZone)}</td>
                <td>
                    <c:choose>
                        <c:when test="${re.status eq '1'}">
                            <span class="label label-success">${views.subAccount['status.normal']}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-danger">${views.subAccount['status.disable']}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <soul:button permission="subaccount:edit" target="${root}/subAccount/edit.html?id=${re.id}" tag="a" title="${views.subAccount['list.edit']}" text="" callback="query" opType="dialog">
                        ${views.common['edit']}
                    </soul:button>

                    <span class="dividing-line m-r-xs m-l-xs">|</span>

                    <a href="/subAccount/logList.html?search.moduleType=1&search.operator=${re.username}&hasReturn=return" nav-second="mainFrame">${views.subAccount['list.log']}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>
