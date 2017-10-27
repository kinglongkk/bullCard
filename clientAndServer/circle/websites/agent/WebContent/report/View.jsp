<%--@elvariable id="command" type="g.model.report.vo.ReportCqsscVo"--%>
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
	<div class="table-responsive">
		<div class=""></div>
		<table class="table table-condensed table-hover table-striped table-bordered">
			<tr>
				<td>期号</td>
				<td>${command.result.matchCode}</td>
			</tr>
			<tr>
				<td>开始时间</td>
				<td>${soulFn:formatDateTz(command.result.beginTime, DateFormat.DAY_SECOND,timeZone)}</td>
			</tr>
			<tr>
				<td>投注额</td>
				<td>${command.result.singleAmount}</td>
			</tr>
			<tr>
				<td>有效投注额</td>
				<td>${command.result.effectiveAmount}</td>
			</tr>
			<tr>
				<td>输赢</td>
				<td>${command.result.profitAmount}</td>
			</tr>
			<tr>
				<td>返点</td>
				<td>${command.result.rakebackAmount}</td>
			</tr>
		</table>
	</div>
	<!--//endregion your codes 3-->
</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="view"/>
<!--//endregion your codes 4-->
</html>