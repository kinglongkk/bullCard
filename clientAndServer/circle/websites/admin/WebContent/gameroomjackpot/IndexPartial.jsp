<%--@elvariable id="command" type="g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>房间名称</th>
                <th>玩法名称</th>
                <th>房间名称</th>
                <th>奖池总额</th>
                <th>奖池最高积累金额</th>
                <th>奖池最低下限金额</th>
                <th>当局最高可输金额</th>
                <th>当局设定收缴值</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>${p.gameName}</td>
                    <td>${p.gameModelName}</td>
                    <td>${p.gameRoomName}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.jackpotSum)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.maxJackpotLimit)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.minJackpotLimit)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.maxLimitGameLose)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.maxJackpotAmatch)}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/gameRoomJackpot/edit.html?id=${p.id}" nav-target="mainFrame" class="co-blue">编辑</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+10}" class="no-content_wrap">
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
<!--//endregion your codes 1-->
