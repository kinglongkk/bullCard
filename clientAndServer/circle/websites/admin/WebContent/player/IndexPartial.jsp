<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
        <tr role="row" class="bg-gray">
            <th>代理商</th>
            <th>玩家用户名</th>
            <th>玩家昵称</th>
            <th>注册日期</th>
            <th>累计充值额</th>
            <th>累计提现额</th>
            <th>当前冻结额</th>
            <th>当前余额</th>
            <th>最近登录时间</th>
            <th>最近登录IP</th>
            <th>
                <gb:select name="search.status" value="${command.search.status}"  prompt="状态" list="${playerStatus}" callback="query"/>
            </th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr class="tab-detail">
                <td>${p.agentName}</td>
                <td>${p.username}</td>
                <td>${p.nickname}</td>
                <td class="text_c">${soulFn:formatDateTz(p.createTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td class="text_r">${soulFn:formatCurrency(p.rechargeTotal)}</td>
                <td class="text_r">${soulFn:formatCurrency(p.withdrawTotal)}</td>
                <td class="text_r">${soulFn:formatCurrency(p.freezingFundsBalance)}</td>
                <td class="text_r">${soulFn:formatCurrency(p.walletBalance)}</td>
                <td class="text_c">${soulFn:formatDateTz(p.lastLoginTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:formatIp(p.lastLoginIp)}</td>
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
                <td class="text_c">
                    <shiro:hasPermission name="role:player_edit">
                        <a href="/player/playerEdit.html?search.id=${p.id}" nav-target="mainFrame">${views.common['edit']}</a>
                    </shiro:hasPermission>
                    <a href="/player/playerDetail.html?search.id=${p.id}" nav-target="mainFrame">${views.common['detail']}</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${fn:length(command.result)<1}">
            <tr>
                <td colspan="${fn:length(command.fields)+12}" class="no-content_wrap">
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