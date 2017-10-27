<%--@elvariable id="command" type="g.model.gameroom.vo.PlayerAiControlListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>房间名</th>
                <th>批次标志</th>
                <th>运行状态</th>
                <th>AI数目</th>
                <th>调控方式</th>
                <th>调控时间</th>
                <th>调控时长</th>
                <th>携带最少金币数</th>
                <th>携带最大金币数</th>
                <th>进入最小时间间隔</th>
                <th>进入最大时间间隔</th>
                <th>离开最小时间间隔</th>
                <th>离开最大时间间隔</th>
                <th>休息最小局数</th>
                <th>休息最大局数</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>${p.roomName}</td>
                    <td>${p.batchId}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.status == 10}">生效</c:when>
                            <c:when test="${p.status == 20}">已失效</c:when>
                            <c:when test="${p.status == 30}">未生效</c:when>
                        </c:choose>
                    </td>
                    <td>${p.aiQty}</td>
                    <td>
                        <c:if test="${p.controlMode == 1}">进入</c:if>
                        <c:if test="${p.controlMode == 2}">退出</c:if>
                    </td>
                    <td>${p.beginControlTime}</td>
                    <td>${p.controlCycle}</td>
                    <td>${p.bringGoldMin}</td>
                    <td>${p.bringGoldMax}</td>
                    <td>${p.intervalMinTime}</td>
                    <td>${p.intervalMaxTime}</td>
                    <td>${p.leaveMinTime}</td>
                    <td>${p.leaveMaxTime}</td>
                    <td>${p.restMinGames}</td>
                    <td>${p.restMaxGames}</td>
                    <td>
                        <div class="joy-list-row-operations">
                            <soul:button target="${root}/playerAiControl/edit.html?id=${p.id}&roomId=${roomId}" text="编辑" opType="dialog" callback="query"/>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="17" class="no-content_wrap">
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
