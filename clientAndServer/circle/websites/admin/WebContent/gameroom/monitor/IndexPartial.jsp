<%--@elvariable id="command" type="g.model.gameroom.vo.GameRoomListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>房间名称</th>
                <th>所属游戏</th>
                <th>游戏玩法</th>
                <th>状态</th>
                <th>最小押注额</th>
                <th>最大押注额</th>
                <th>当前玩家数</th>
                <th>当前AI数</th>
                <th>本日累计输赢</th>
                <th>基本配置</th>
                <th>调控</th>
                <th>调控停止</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>${p.name}</td>
                    <td>${p.gamename}</td>
                    <td>${p.modelname}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.status == 10}">启用</c:when>
                            <c:when test="${p.status == 20}">禁用</c:when>
                            <c:when test="${p.status == 30}">维护</c:when>
                        </c:choose>
                   </td>
                    <td class="text_r">
                        <fmt:formatNumber value="${fn:split(p.betChip,',' )[0]}" pattern= "#,#00.0#" />
                    </td>
                    <td class="text_r">
                        <c:choose>
                            <c:when test="${fn:endsWith(p.betChip,',' )}">
                                ${soulFn:formatCurrency(fn:split(fn:substring(p.betChip,0 ,fn:length(p.betChip)-1 ),',' )[fn:length(fn:split(fn:substring(p.betChip,0 ,fn:length(p.betChip)-1 ),',' ))-1] * p.betTimes)}
                            </c:when>
                            <c:otherwise>
                                ${soulFn:formatCurrency(fn:split(p.betChip,',' )[fn:length(fn:split(p.betChip,',' ))-1] * p.betTimes)}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="currentplayer text_r" data="${p.id}" style="color:red;font-weight: bolder">加载中...</td>
                    <td class="robot text_r" data="${p.id}" style="color:red;font-weight: bolder" >加载中...</td>
                    <td class="totalWin text_r" data="${p.id}"  style="color:red;font-weight: bolder" >加载中...</td>
                    <td class="text_c">
                        <a href="/playerAiRatioControl/editAdd.html?roomId=${p.id}" nav-target="mainFrame" class="co-blue">
                            ${views.common['detail']}
                        </a>
                    </td>
                    <td class="text_c">
                        <a href="/playerAiControl/ailist.html?roomId=${p.id}" nav-target="mainFrame" class="co-blue">
                            ${views.common['detail']}
                        </a>
                    </td>
                    <td class="text_c">
                        <soul:button target="${root}/playerAiControl/stopAi.html?roomId=${p.id}" text=" 取消调控" opType="ajax" dataType="json" confirm="您确定要停止调控吗？" callback="query" />
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="13" class="no-content_wrap">
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
<soul:import res="site/gameroom/vgameroom"></soul:import>
<!--//endregion your codes 1-->
