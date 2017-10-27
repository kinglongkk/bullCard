<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="table-responsive table-min-h ">
    <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
        <thead>
        <tr>
            <th class="inline">#</th>
            <th class="inline">游戏类型</th>
            <th class="inline">游戏名称</th>
            <th class="inline">游戏logo</th>
            <th class="inline">游戏返回链接</th>
            <th class="inline">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${agentGameList.result}" var="agent" varStatus="status">
            <tr class="tab-title">
                <td>
                    ${status.index+1}
                </td>
                <td>${agent.firstType}</td>
                <td>${agent.name}</td>
                <c:forEach items="${command.result}" var="game" varStatus="status">
                    <c:choose>
                        <c:when test="${game.gameId eq agent.id}">
                            <td>
                                <img src="${soulFn:getImagePathWithDefault(domain, game.gameLogo, resRoot.concat('/images/myaccount.jpg'))}" class="logo-size-h100">
                                <soul:button target="${root}/userAgentGame/toUploadLogoPortrait.html?result.id=${game.id}" text="点击修改Logo" title="${views.setting['myAccount.editHeadPortrait']}" opType="dialog" callback="query" />
                            </td>
                            <td>
                                ${game.gameLink}
                            </td>
                            <td>
                                <div class="joy-list-row-operations">
                                    <soul:button target="${root}/userAgentGame/edit.html?id=${game.id}" text="编辑" opType="dialog" callback="query" />
                                    <soul:button target="${root}/userAgentGame/delete.html?id=${game.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                                </div>
                            </td>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </tr>
        </c:forEach>
        <c:if test="${fn:length(command.result)<1}">
            <tr>
                <td colspan="${fn:length(command.fields)+6}" class="no-content_wrap">
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