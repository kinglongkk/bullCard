<%--@elvariable id="command" type="g.model.gameroom.vo.GameRoomVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->

<html lang="zh-CN">
<head>
	<title>详情</title>
	<%@ include file="/include/include.head.jsp" %>
	<!--//region your codes 2-->

	<!--//endregion your codes 2-->
</head>
<body>
	<!--//region your codes 3-->
	<div class="modal-body">

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">所属游戏</label>
			<div class="col-xs-6 p-x">
				<c:forEach items="${gameListVo.result}" var="game">
					<c:choose>
						<c:when test="${command.result.gameId eq game.id}">
							${game.name}
						</c:when>
					</c:choose>
				</c:forEach>
			</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">所属模式</label>
			<div class="col-xs-6 p-x">
				<c:forEach items="${gameModelListVo.result}" var="gameModel">
				<c:choose>
					<c:when test="${command.result.gameModelId eq gameModel.id}">
						${gameModel.name}
					</c:when>
				</c:choose>
				</c:forEach>
			</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">房间名称</label>
			<div class="col-xs-6 p-x">${command.result.name}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">${views.common['status']}</label>
			<div class="col-xs-6 p-x">
				<span class="label label-success">${command.result.status eq '10' ? views.common['enable'] : ''}</span>
				<span class="label label-danger" >${command.result.status eq '20' ? views.common['forbidden'] : ''}</span>
				<span class="label label-warning">${command.result.status eq '30' ? views.common['safeguard'] : ''}</span>
			</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">房间描述</label>
			<div class="col-xs-6 p-x">${command.result.description}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">上庄最低额度</label>
			<div class="col-xs-6 p-x">${command.result.minLimitDealerBlance}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">最大容纳玩家数</label>
			<div class="col-xs-6 p-x">${command.result.maxLimitPlayerNumber}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">最小玩家余额</label>
			<div class="col-xs-6 p-x">${command.result.minLimitPlayerBlance}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">最大投注数</label>
			<div class="col-xs-6 p-x">${command.result.betMax}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">最小投注数</label>
			<div class="col-xs-6 p-x">${command.result.betMin}</div>
		</div>

	</div>
	<!--//endregion your codes 3-->
</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="view"/>
<!--//endregion your codes 4-->
</html>