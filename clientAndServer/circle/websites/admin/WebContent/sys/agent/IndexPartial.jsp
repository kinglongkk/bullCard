<%@ page import="g.model.agent.po.VAgentManage" %>
<%--@elvariable id="command" type="g.model.agent.vo.VAgentManageListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<c:set var="poType" value="<%= VAgentManage.class %>"></c:set>
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
        <tr role="row" class="bg-gray">
            <th class="user_checkbox">
                <label>
                    <input type="checkbox" class="i-checks">
                </label>
            </th>
            <th>用户名</th>
            <th>代理商名称</th>
            <th>姓名</th>
            <th>用户类型</th>
            <th class="inline">
                <gb:select name="search.status" value="${command.search.status}" cssClass="btn-group chosen-select-no-single" prompt="${views.common['all']}" list="${status}" callback="query"/>
            </th>
            <th>最后登陆时间</th>
            <soul:orderColumn poType="${poType}" property="playerNum" column="${views.column['VAgentManage.playerNum']}"/>
            <th>最后活跃时间</th>
            <th>上次登录IP</th>
            <th>更新时间</th>
            <th>更新者</th>
            <th style="width:100px;" >${views.common['operate']}</th>
        </tr>
        <tr class="bd-none hide">
            <th colspan="${fn:length(command.fields)+3}">
                <div class="select-records">
                    <i class="fa fa-exclamation-circle"></i>
                    ${views.common['player.cancelSelectAll.prefix']}&nbsp;
                    <span id="page_selected_total_record"></span>
                    ${views.common['player.cancelSelectAll.middlefix']}
                    <soul:button target="cancelSelectAll" opType="function" text="${views.common['player.cancelSelectAll']}"/>
                    ${views.common['player.cancelSelectAll.suffix']}
                </div>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <c:choose>
                <c:when test="${p.status eq '1'}">
                    <c:set var="color" value="label-success"></c:set>
                </c:when>
                <c:when test="${p.status eq '2'}">
                    <c:set var="color" value="label-danger"></c:set>
                </c:when>
                <c:when test="${p.status eq '3'}">
                    <c:set var="color" value="label-info"></c:set>
                </c:when>
                <c:when test="${p.status eq '4'}">
                    <c:set var="color" value="label-orange"></c:set>
                </c:when>
            </c:choose>
            <tr class="tab-detail">
                <th><input type="checkbox" value="${p.id}" ></th>
                <td>
                    <a href="/vAgentManage/detail/detail.html?search.id=${p.id}" nav-target="mainFrame" class="co-blue">
                        ${p.username}
                    </a>
                </td>
                <td>${p.nickname}</td>
                <td>${p.realName}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.userType eq '20'}">代理</c:when>
                        <c:when test="${p.userType eq '21'}">代理子账号</c:when>
                    </c:choose>
                </td>
                <td>
                    <span class="label ${color}">
                        ${dicts.player.user_status[p.status]}
                    </span>
                </td>
                <td class="text_c"><fmt:formatDate value="${p.lastLoginTime}" type="date" pattern="${DateFormat.DAY_SECOND}"/></td>
                <td class="text_r">
                    <a href="/player/playerList.html?search.ownerId=${p.id}&search.userType=30" nav-second='mainFrame'>
                        ${p.playerNum}
                    </a>
                </td>
                <td class="text_c"><fmt:formatDate value="${p.lastActiveTime}" type="date" pattern="${DateFormat.DAY_SECOND}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${not empty p.lastLoginIp}">
                            ${soulFn:formatIp(p.lastLoginIp)}
                        </c:when>
                    </c:choose>
                </td>
                <td class="text_c"><fmt:formatDate value="${p.updateTime}" type="date" pattern="${DateFormat.DAY_SECOND}"/></td>
                <td>${p.updateUser}</td>
                <td class="text_c">
                    <a href="/vAgentManage/edit.html?id=${p.id}" nav-target="mainFrame" class="">
                        ${views.common['edit']}
                    </a>
                    <span class="dividing-line m-r-xs m-l-xs">|</span>
                    <a href="/vAgentManage/detail/detail.html?search.id=${p.id}" nav-target="mainFrame" class="co-blue">
                        ${views.common['detail']}
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${fn:length(command.result)<1}">
            <tr>
                <td colspan="${fn:length(command.fields)+13}" class="no-content_wrap">
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