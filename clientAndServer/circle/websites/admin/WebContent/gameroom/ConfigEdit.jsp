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
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="modal-body">
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <input type="hidden" name="result.id" value="" />
            <label class="col-xs-3 al-right">
                当前房间：
            </label>
            <div class="col-xs-8 p-x">
                <%--command是房间信息，config是配置信息--%>
                <input type="hidden" name="search.id" value="${config.result.id}">
                <input type="hidden" name="result.roomId" value="${command.result.id}" />
                ${command.result.name}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                上庄金额：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlance" value="${config.result.dealerBlance}" onkeyup="this.value=this.value.replace(/\D/g, '')" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                提醒金额：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlanceTip" value="${config.result.dealerBlanceTip}" onkeyup="this.value=this.value.replace(/\D/g, '')" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                下庄金额(%)：
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" name="result.dealerBlanceQuit"  value="${config.result.dealerBlanceQuit}" onkeyup="this.value=this.value.replace(/\D/g, '')" />%
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                投注筹码：<br/>(请选择5个选项)
            </label>
            <div class="col-xs-8 p-x">
                <input type="checkbox" name="result.betChip" value="100" ${fn:contains(config.result.betChip,'100,')?'checked':''} />100&nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="200" ${fn:contains(config.result.betChip,'200,')?'checked':''} />200&nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="500" ${fn:contains(config.result.betChip,'500,')?'checked':''} />500&nbsp;&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="1000" ${fn:contains(config.result.betChip,'1000,')?'checked':''} />1000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="2000" ${fn:contains(config.result.betChip,'2000,')?'checked':''} />2000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="5000" ${fn:contains(config.result.betChip,'5000,')?'checked':''} />5000&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="10000" ${fn:contains(config.result.betChip,'10000,')?'checked':''} />10000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="20000" ${fn:contains(config.result.betChip,'20000,')?'checked':''} />20000&nbsp;&nbsp;
                <input type="checkbox" name="result.betChip" value="50000" ${fn:contains(config.result.betChip,'50000,')?'checked':''} />50000&nbsp;&nbsp;<br/>

                <input type="checkbox" name="result.betChip" value="100000" ${fn:contains(config.result.betChip,'100000')?'checked':''} />100000&nbsp;
                <input type="checkbox" name="result.betChip" value="200000" ${fn:contains(config.result.betChip,'200000')?'checked':''} />200000&nbsp;
                <input type="checkbox" name="result.betChip" value="500000" ${fn:contains(config.result.betChip,'500000')?'checked':''} />500000&nbsp;<br/>
            </div>
        </div>

    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/gameRoomConfigBull100/persist.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
        <soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/gameRoomConfigBull100/delete.html?id=${command.result.id}" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />
    </div>
    <!--//endregion your codes 3-->

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<%--<soul:import type="edit"/>--%>
<soul:import res="site/gameroom/ConfigEdit"/>
<!--//endregion your codes 4-->
</html>