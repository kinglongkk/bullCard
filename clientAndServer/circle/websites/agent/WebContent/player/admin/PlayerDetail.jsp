<%--@elvariable id="command" type="g.model.master.player.vo.VUserPlayerVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/player/playerView.html?search.id=${command.result.id}&ajax=true" method="post">
    <a href="/player/playerView.html?search.id=${command.result.id}&random=<%=Math.random()%>"
       nav-target="mainFrame" name="returnView" style="display: none"></a>
    <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
    <div class="table-responsive" id="tab-1">
    <table class="table  dataTable">
        <tbody>
        <tr>
            <td class="bg-tbcolor">${views.column['player.belongProxy']}:${command.result.agentName}</td>
            <td class="bg-tbcolor">${views.column['player.playerAccount']}:${command.result.username}</td>
            <td class="bg-tbcolor">${views.column['player.nickName']}:${command.result.nickname}</td>
        </tr>
        <tr>
            <td class="bg-tbcolor">${views.common['status']}:${dicts.player.player_status[command.result.playerStatus]}</td>
            <td class="bg-tbcolor">${views.common['createTime']}:${soulFn:formatDateTz(command.result.createTime, DateFormat.DAY_SECOND,timeZone)}</td>
            <td class="bg-tbcolor">${views.column['player.lastLoginTime']}:${soulFn:formatDateTz(command.result.lastLoginTime, DateFormat.DAY_SECOND,timeZone)}</td>
        </tr>

        </tbody>
    </table>
    </div></div>
</form:form>
<%--<soul:import res="site/player/player"/>--%>
