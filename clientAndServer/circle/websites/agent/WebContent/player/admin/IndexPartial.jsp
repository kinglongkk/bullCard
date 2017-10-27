<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h">
    <table class="table table-striped table-hover dataTable" id="editable" aria-describedby="editable_info">
        <thead>
        <tr>
            <th>代理商</th>
            <th>账号</th>
            <th>昵称</th>
            <th>注册时间</th>
            <th>
                <gb:select name="search.status" value="${command.search.status}"  prompt="状态" list="${playerStatus}" callback="query"/>
            </th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr>
                <td>${p.agentName}</td>
                <td>${p.username}</td>
                <td>${p.nickname}</td>
                <td>${soulFn:formatDateTz(p.createTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.accountfreeze}">
                            ${views.common["user_status.3"]}
                        </c:when>
                        <c:otherwise>
                            ${views.common["user_status.".concat(p.status)]}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <shiro:hasPermission name="role:player_edit">
                        <a href="/player/playerEdit.html?search.id=${p.id}" nav-target="mainFrame">${views.common['edit']}</a>
                    </shiro:hasPermission>
                    <a href="/player/playerDetail.html?search.id=${p.id}" nav-target="mainFrame">${views.common['detail']}</a>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>