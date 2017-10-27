<%--@elvariable id="command" type="g.model.gameroom.vo.PlayerAiControlVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->

<html lang="zh-CN">
<head>
    <title>编辑</title>
    <%@ include file="/include/include.head.jsp" %>
    <!--//region your codes 2-->

    <!--//endregion your codes 2-->
</head>

<body>

<form:form id="editForm" action="${root}/playerAiControl/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <c:if test="${empty command.result.id }">
        <input type="hidden" name="result.roomId" value="${param.roomId}">
    </c:if>
    <c:if test="${not empty command.result.id }">
        <input type="hidden" name="result.roomId" value="${command.result.roomId}">
    </c:if>
    <input type="hidden" name="result.status" value="${command.result.status}">
    <input type="hidden" name="result.batchId" value="${command.result.batchId}">
    <input type="hidden" name="result.roomName" value="${command.result.roomName}">
    <input type="hidden" name="result.createUser" value="${command.result.createUser}">
    <input type="hidden" name="result.createTime" value="${command.result.createTime}">
    <input type="hidden" name="result.updateUser" value="${command.result.updateUser}">
    <input type="hidden" name="result.updateTime" value="${command.result.updateTime}">
    <input type="hidden" name="result.updateTime" value="${command.result.updateTime}">
    <input type="hidden" name="result.gameModel" value="${command.result.gameModel}">
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            AI数目*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.aiQty" value="${command.result.aiQty}">人
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            调控时间*:
        </label>
        <div class="col-xs-8 p-x">
            <gb:dateRange value="${command.result.beginControlTime}" style="width:300px;" format="${DateFormat.DAY_SECOND}" useTime="true" name="result.beginControlTime"/>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            调控时长*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.controlCycle" value="${command.result.controlCycle}">分钟
            <label>* 调控的周期时间长度，到期自动停止</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            调控方式*:
        </label>
        <div class="col-xs-8 p-x">
            <select name="result.controlMode" style="height: 38px;">
                <option value="1" <c:if test="${command.result.controlMode == 1}">selected</c:if> >进入</option>
                <option value="2" <c:if test="${command.result.controlMode == 2}">selected</c:if> >退出</option>
            </select>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            携带金币数*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.bringGoldMin" value="${command.result.bringGoldMin}">---<input type="text" name="result.bringGoldMax" value="${command.result.bringGoldMax}">
            <label>* 即AI进入房间时身上的金币范围</label>
        </div>
    </div>

    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            进入时间间隔*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.intervalMinTime" value="${command.result.intervalMinTime}" >---<input type="text" name="result.intervalMaxTime" value="${command.result.intervalMaxTime}">
            <label>* 即AI进入房间时的频率</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            离开时间间隔*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.leaveMinTime" value="${command.result.leaveMinTime}" >---<input type="text" name="result.leaveMaxTime" value="${command.result.leaveMaxTime}">
            <label>* 即AI离开房间时的频率</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            游戏休息局数*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.restMinGames" value="${command.result.restMinGames}" >---<input type="text" name="result.restMaxGames" value="${command.result.restMaxGames}" >
            <label>* 即AI连续游戏的局数，0为不休息</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            投注筹码比率*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.chipRates"  value="${command.result.chipRates}">
            <label>*  投注筹码比率，用‘，’进行隔开</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            每场比赛平均押注次数*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.betCount" value="${command.result.betCount}">
            <label>*  每场比赛平均押注次数</label>
        </div>
    </div>
    <div class="form-group clearfix line-hi34 m-b-xxs">
        <label class="col-xs-3 al-right">
            人数上限*:
        </label>
        <div class="col-xs-8 p-x">
            <input type="text" name="result.roomMaxQty" value="${command.result.roomMaxQty}" >
            <label>* 房间玩家+AI数到达该值时，AI不再自动进入高于该值时，AI有序退出</label>
        </div>
    </div>

    <!--//region your codes 3-->
    <div class="modal-footer">
        <soul:button cssClass="btn btn-default" text="保存" opType="ajax" dataType="json" target="${root}/playerAiControl/saveAi.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <%--<soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/playerAiControl/delete.html?id=${result.id}" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />--%>
    </div>
</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="edit"/>
<!--//endregion your codes 4-->
</html>