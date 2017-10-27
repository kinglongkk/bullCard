<%--@elvariable id="command" type="g.model.gameroom.vo.GameRoomVo"--%>
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

<form:form id="editForm" action="${root}/gameRoom/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="modal-body">

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                所属游戏
            </label>
            <div class="col-xs-8 p-x">
                <form:select path="result.gameId">
                    <c:choose>
                        <c:when test="${command.result.gameId eq null or command.result.gameId eq ''}">
                            <form:option value="">${views.common['pleaseSelect']}</form:option>
                            <c:forEach items="${gameListVo.result}" var="game">
                                <form:option value="${game.id}">${game.name}</form:option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${gameListVo.result}" var="game">
                                <c:choose>
                                    <c:when test="${game.id eq command.result.gameId}">
                                        <form:option value="${game.id}" selected="selected">${game.name}</form:option>
                                    </c:when>
                                    <c:otherwise>
                                        <form:option value="${game.id}">${game.name}</form:option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                所属玩法
            </label>
            <div class="col-xs-8 p-x">
                <form:select path="result.gameModelId" id="gameModelId">
                    <c:choose>
                        <c:when test="${command.result.gameModelId eq null or command.result.gameModelId eq ''}">
                            <form:option value="">${views.common['pleaseSelect']}</form:option>
                            <c:forEach items="${gameModelListVo.result}" var="gameModel">
                                <form:option value="${gameModel.id}">${gameModel.name}</form:option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${gameModelListVo.result}" var="gameModel">
                                <c:choose>
                                    <c:when test="${gameModel.id eq command.result.gameModelId}">
                                        <form:option value="${gameModel.id}" selected="selected">${gameModel.name}</form:option>
                                    </c:when>
                                    <c:otherwise>
                                        <form:option value="${gameModel.id}">${gameModel.name}</form:option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                奖池实际金额
            </label>
            <div class="col-xs-8 p-x">
                <select name="result.jackpot" id="bullConf">

                </select>
            </div>
            <input type="hidden" name="result.jackpotSum" id="jackpotSum">
        </div>
        <!-- 开始==========================  -->
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                选择房间配置模板
            </label>
            <div class="col-xs-8 p-x">
                <select name="result.id" id="gameRoomConfModel">

                </select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                投注码
            </label>
            <div class="col-xs-8 p-x">
                <%--<form:input path="result.betChip" cssClass="form-control m-b" value="${command.result.betChip}" />--%>
                <input name="result.betChip" id="betChip" class="form-control m-b">
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                上庄金额
            </label>
            <div class="col-xs-8 p-x">
                <input name="confVo.dealerBlance" id="dealerBlance" class="form-control m-b">
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                庄家提醒金额
            </label>

            <div class="col-xs-8 p-x">
                <input name="result.dealerBlanceTip" id="dealerBlanceTip" class="form-control m-b">
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                庄家下庄金额
            </label>
            <div class="col-xs-8 p-x">
                <input name="result.dealerBlanceQuit" id="dealerBlanceQuit" class="form-control m-b">
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                最大投注次数
            </label>
            <div class="col-xs-8 p-x">
                <input name="result.betTimes" id="betTimes" class="form-control m-b">
            </div>
        </div>



        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                单据最高可输金额
            </label>
            <div class="col-xs-8 p-x">
                <input name="result.maxLimitGameLose" id="maxLimitGameLose" class="form-control m-b">
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                奖池最低下限金额
            </label>
            <div class="col-xs-8 p-x">
                <input name="result.minJackpotLimit" id="minJackpotLimit" class="form-control m-b">

            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                奖池最高积累金额
            </label>
            <div class="col-xs-8 p-x">
               <input name="result.maxJackpotLimit" id="maxJackpotLimit" class="form-control m-b">
            </div>
        </div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                单场比赛最高金额
            </label>
            <div class="col-xs-8 p-x">
               <input name="result.maxJackpotAmatch" id="maxJackpotMatch" class="form-control m-b">
            </div>
        </div>
        <!-- ==========================  -->

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                房间名称
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.name" cssClass="form-control m-b" value="${command.result.name}" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                    ${views.common['status']}
            </label>
            <div class="col-xs-8 p-x">
                <select name="result.status">
                    <c:choose>
                        <c:when test="${command.result.status eq null or command.result.status eq ''}">
                            <option value="">${views.common['pleaseSelect']}</option>
                            <option value="10">${views.common['enable']}</option>
                            <option value="20">${views.common['forbidden']}</option>
                            <option value="30">${views.common['safeguard']}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="10" ${command.result.status eq '10' ? 'selected="selected"' : ''}>${views.common['enable']}</option>
                            <option value="20" ${command.result.status eq '20' ? 'selected="selected"' : ''}>${views.common['forbidden']}</option>
                            <option value="30" ${command.result.status eq '30' ? 'selected="selected"' : ''}>${views.common['safeguard']}</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                房间描述
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.description" cssClass="form-control m-b" value="${command.result.description}" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                最大容纳玩家数
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.maxLimitPlayerNumber" cssClass="form-control m-b" value="${command.result.maxLimitPlayerNumber}" />
            </div>
        </div>

    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/gameRoom/addGameRoom.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <c:choose>
            <c:when test="${command.result.status eq null or command.result.status eq ''}">
                <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
            </c:when>
            <c:otherwise>
                <soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/gameRoom/delete.html?id=${command.result.id}" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />
            </c:otherwise>
        </c:choose>
    </div>
    <!--//endregion your codes 3-->

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import res="site/gameroom/Edit"/>
<!--//endregion your codes 4-->
</html>