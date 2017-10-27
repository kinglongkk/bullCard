<%--@elvariable id="command" type="g.model.agent.vo.VAgentManageVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/userAgent/agent/detail.html?search.id=${command.result.id}&ajax=true" method="post">
    <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
    <div class="table-responsive" id="tab-1">
    <table class="table dataTable">
        <tbody>

            <tr class="tab-title">
                <th class="bg-tbcolor">${views.column['VAgentManage.agentName']}：</th>
                <td><a href="javascript:void(0)">${command.result.username}</a></td>
                <th class="bg-tbcolor">${views.column['VAgentManage.realName']}：</th>
                <td>${command.result.realName}</td>
                <th class="bg-tbcolor">${views.column['VAgentManage.sex']}：</th>
                <td>
                    ${dicts.common.sex[command.result.sex]}
                </td>
                <th class="bg-tbcolor">${views.column['VAgentManage.playerNum']}：</th>
                <td>
                    <a href="/player/playerList.html?search.ownerId=${command.result.id}&search.userType=30" nav-target="mainFrame">
                        ${command.result.playerNum}
                    </a>
                </td>
            </tr>
            <tr class="tab-title">
                <th class="bg-tbcolor">${views.column['VAgentManage.mobilePhone']}：</th>
                <td>${soulFn:overlayTel(command.result.mobilePhone)}</td>
                <th class="bg-tbcolor">${views.column['VAgentManage.mail']}：</th>
                <td>${soulFn:overlayEmaill(command.result.mail)}</td>
                <th class="bg-tbcolor">${views.column['VAgentManage.status']}：</th>
                <td>${dicts.player.player_status[command.result.status]}</td>
                <th class="bg-tbcolor">${views.column['VAgentManage.skype']}：</th>
                <td>${soulFn:overlayName(command.result.skype)}</td>
            </tr>
            <tr class="tab-title">
                <th class="bg-tbcolor">${views.column['VAgentManage.createTime']}：</th>
                <td>${soulFn:formatDateTz(command.result.createTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <th class="bg-tbcolor">${views.column['VAgentManage.lastLoginTime']}：</th>
                <td><fmt:formatDate value="${command.result.lastLoginTime}" pattern="${DateFormat.DAY_SECOND}"></fmt:formatDate></td>
            </tr>

        </tbody>
    </table>
    <br>
    </div>
    </div>
</form:form>
