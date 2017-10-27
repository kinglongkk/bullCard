<%--@elvariable id="command" type="g.model.gameroom.vo.GameRoomVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <title>配置编辑</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form id="editForm" action="${root}/gameRoom/edit.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <input type="hidden" name="result.id" value="${config.id}">
    <input type="hidden" name="result.roomId" value="${config.roomId}">
    <div class="modal-body">
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">上庄金额：</label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlance" value="${config.dealerBlance}" onkeyup="this.value=this.value.replace(/\D/g, '')" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                提醒金额(%)：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlanceTip" value="${config.dealerBlanceTip}" onkeyup="this.value=this.value.replace(/\D/g, '')" />%
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                下庄金额(%)：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlanceQuit"  value="${config.dealerBlanceQuit}" onkeyup="this.value=this.value.replace(/\D/g, '')" />%
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                最大投注次数：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.betTimes"  value="${config.betTimes}" onkeyup="this.value=this.value.replace(/\D/g, '')" />
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                投注筹码：<br/>(请选择5个选项)
            </label>
            <div class="col-xs-8 p-x">
                <c:set value="${fn:split(config.betChip, ',')}" var="bet"></c:set>
                <input type="checkbox" name="result.betChip" value="100" <c:forEach var="token" items="${bet}"><c:if test="${token=='100'}">checked</c:if></c:forEach> />100&nbsp;&nbsp;&nbsp;

                <input type="checkbox" name="result.betChip" value="200" <c:forEach var="token" items="${bet}"><c:if test="${token=='200'}">checked</c:if></c:forEach>/>200&nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="500" <c:forEach var="token" items="${bet}"><c:if test="${token=='500'}">checked</c:if></c:forEach>/>500&nbsp;&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="1000" <c:forEach var="token" items="${bet}"><c:if test="${token=='1000'}">checked</c:if></c:forEach>/>1000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="2000" <c:forEach var="token" items="${bet}"><c:if test="${token=='2000'}">checked</c:if></c:forEach>/>2000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="5000"  <c:forEach var="token" items="${bet}"><c:if test="${token=='5000'}">checked</c:if></c:forEach>/>5000&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="10000" <c:forEach var="token" items="${bet}"><c:if test="${token=='10000'}">checked</c:if></c:forEach>/>10000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="20000"  <c:forEach var="token" items="${bet}"><c:if test="${token=='20000'}">checked</c:if></c:forEach>/>20000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="50000"  <c:forEach var="token" items="${bet}"><c:if test="${token=='50000'}">checked</c:if></c:forEach>/>50000&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="100000" <c:forEach var="token" items="${bet}"><c:if test="${token=='100000'}">checked</c:if></c:forEach>/>100000&nbsp;
                <input type="checkbox" name="result.betChip" value="200000" <c:forEach var="token" items="${bet}"><c:if test="${token=='200000'}">checked</c:if></c:forEach>/>200000&nbsp;
                <input type="checkbox" name="result.betChip" value="500000"  <c:forEach var="token" items="${bet}"><c:if test="${token=='500000'}">checked</c:if></c:forEach> />500000&nbsp;<br/>
            </div>
        </div>
    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/gameRoomConfigBull100/persist.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
        <soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/gameRoomConfigBull100/delete.html?id=" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />
    </div>
</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/gameroom/ConfigEdit"/>
</html>