<%--@elvariable id="command" type="g.model.admin.gamebull100room.vo.VRoomBull100ListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>所属游戏</th>
                <th>所属模式</th>
                <th>房间名称</th>
                <th>状态</th>
                <th>玩家进房下限</th>
                <th>最大容纳玩家数</th>
                <th>上庄最低额度</th>
                <th>庄家下庄金额</th>
                <th>庄家提示金额</th>
                <th>最大投注次数</th>
                <th>筹码</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>${p.gameName}</td>
                    <td>${p.gameModelName}</td>
                    <td>${p.name}</td>
                    <td>
                        <span class="label label-success">${p.status eq '10' ? views.common['enable'] : ''}</span>
                        <span class="label label-danger">${p.status eq '20' ? views.common['forbidden'] : ''}</span>
                        <span class="label label-warning">${p.status eq '30' ? views.common['safeguard'] : ''}</span>
                    </td>
                    <td class="text_r">${soulFn:formatCurrency(p.minLimitPlayerBlance)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.maxLimitPlayerNumber)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.dealerBlance)}</td>
                    <td class="text_r">${p.dealerBlanceQuit}%</td>
                    <td class="text_r">${p.dealerBlanceTip}</td>
                    <td class="text_r">${p.betTimes}</td>
                    <td>${p.betChip}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/gameBull100Room/configEdit.html?id=${p.id}" text="配置" opType="dialog" callback="query" />
                            <soul:button target="${root}/gameBull100Room/view.html?id=${p.id}" text="查看" opType="dialog" />
                            <soul:button target="${root}/gameBull100Room/edit.html?id=${p.id}" text="编辑" opType="dialog" callback="query" />
                            <soul:button target="${root}/gameBull100Room/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
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
<!--//endregion your codes 1-->
