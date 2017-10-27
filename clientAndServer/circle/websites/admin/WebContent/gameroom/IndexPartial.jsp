<%--@elvariable id="command" type="g.model.gameroom.vo.GameRoomListVo"--%>
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
                <th>${views.common['status']}</th>
                <th>房间描述</th>
                <th>最大容纳玩家数</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>
                        <c:forEach items="${gameListVo.result}" var="game">
                            <c:choose>
                                <c:when test="${p.gameId eq game.id}">
                                    ${game.name}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${gameModelListVo.result}" var="gameModel">
                            <c:choose>
                                <c:when test="${p.gameModelId eq gameModel.id}">
                                    ${gameModel.name}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>${p.name}</td>
                    <td>
                        <span class="label label-success">${p.status eq '10' ? views.common['enable'] : ''}</span>
                        <span class="label label-danger">${p.status eq '20' ? views.common['forbidden'] : ''}</span>
                        <span class="label label-warning">${p.status eq '30' ? views.common['safeguard'] : ''}</span>
                    </td>
                    <td>${p.description}</td>
                    <td class="text_r">${p.maxLimitPlayerNumber}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/gameRoom/configEdit.html?id=${p.id}" text="配置" opType="dialog" callback="query" />
                            <soul:button target="${root}/gameRoom/view.html?id=${p.id}" text="查看" opType="dialog" />
                            <soul:button target="${root}/gameRoom/edit.html?id=${p.id}" text="编辑" opType="dialog" callback="query" />
                            <soul:button target="${root}/gameRoom/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
