<%--@elvariable id="command" type="g.model.game.vo.GameVo"--%>
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
			<label class="col-xs-6 al-right">游戏名称</label>
			<div class="col-xs-6 p-x">${command.result.name}</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">游戏代码</label>
			<div class="col-xs-6 p-x">${command.result.code}</div>
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
			<label class="col-xs-6 al-right">一级类型</label>
			<div class="col-xs-6 p-x">
					<c:forEach items="${firstName}" var="firstTypeName">
						<c:choose>
							<c:when test="${command.result.firstType eq firstTypeName['code']}">
								${firstTypeName['trans']}
							</c:when>
						</c:choose>
					</c:forEach>
			</div>
		</div>

		<div class="form-group clearfix line-hi34">
			<label class="col-xs-6 al-right">二级类型</label>
			<div class="col-xs-6 p-x">
				<c:forEach items="${secondType}" var="secondTypeName">
					<c:choose>
						<c:when test="${command.result.type eq secondTypeName['code']}">
							${secondTypeName['trans']}
						</c:when>
					</c:choose>
				</c:forEach>
			</div>
		</div>

	</div>
	<!--//endregion your codes 3-->
</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="view"/>
<!--//endregion your codes 4-->
</html>