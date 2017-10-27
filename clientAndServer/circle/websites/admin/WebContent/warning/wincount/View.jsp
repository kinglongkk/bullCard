<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/userAgent/agent/detail.html?search.id=${command.result.id}&ajax=true" method="post">
<div id="editable_wrapper" class="dataTables_wrapper" role="grid">
	<div class="table-responsive" id="tab-1">
		<table class="table dataTable">
			<tbody>

			<tr class="tab-title">
				<th class="bg-tbcolor">用户:</th>
				<td>${playerWarningWinCount.username}</td>
				<th class="bg-tbcolor">昵称:</th>
				<td>${playerWarningWinCount.nickname}</td>
				<th class="bg-tbcolor">注册日期：</th>
				<td>${soulFn:formatDateTz(playerWarningWinCount.registerDate, DateFormat.DAY_SECOND, timeZone)}</td>
				<th class="bg-tbcolor">赢得金币：</th>
				<td>
						${soulFn:formatCurrency(playerWarningWinCount.winAmount)}
				</td>
			</tr>
			<tr class="tab-title">
				<th class="bg-tbcolor">投注金额:：</th>
				<td>${soulFn:formatCurrency(playerWarningWinCount.betAmount)}</td>
				<th class="bg-tbcolor">开始时间:</th>
				<td>${soulFn:formatDateTz(playerWarningWinCount.startTime, DateFormat.DAY_SECOND, timeZone)}</td>
				<th class="bg-tbcolor">结束时间:</th>
				<td>${soulFn:formatDateTz(playerWarningWinCount.endTime, DateFormat.DAY_SECOND, timeZone)}</td>
				<th class="bg-tbcolor">连赢次数:</th>
				<td>${playerWarningWinCount.num}</td>
			</tr>
			<tr class="tab-title">
				<th class="bg-tbcolor">状态：</th>
				<td>
					<c:if test="${playerWarningWinCount.status == '1'}"><div style="float: left;border: solid 0px black;background-color: yellow;height: 20px;width: 100px"></div></c:if>
					<c:if test="${playerWarningWinCount.status == '2'}"><div style="float:left;border: solid 0px black;background-color: red;height: 20px;width: 100px"></div></c:if>
				</td>
				<th class="bg-tbcolor">所属代理：</th>
				<td>
				${playerWarningWinCount.agentUsername}
				</td>
			</tr>
			<tr class="tab-title">
				<th class="bg-tbcolor">累计充值：</th>
				<td>${soulFn:formatCurrency(mapUserPlayer.recharge_total)}</td>
				<th class="bg-tbcolor">累计提现：</th>
				<td>${soulFn:formatCurrency(mapUserPlayer.withdraw_total)}</td>
				<th class="bg-tbcolor">上次登录时间:</th>
				<td>${soulFn:formatDateTz(mapUserPlayer.last_login_time, DateFormat.DAY_SECOND, timeZone)}</td>
				<th class="bg-tbcolor">上次登录IP:</th>
				<td>${soulFn:formatIp(mapUserPlayer.last_login_ip)}</td>
			</tr>
			<tr class="tab-title">
				<th class="bg-tbcolor">冻结金币：</th>
				<td>${soulFn:formatCurrency(mapUserPlayer.freezing_funds_balance)}</td>
				<th class="bg-tbcolor">余额：</th>
				<td>${soulFn:formatCurrency(mapUserPlayer.wallet_balance)}</td>
				<th class="bg-tbcolor">累计提现次数:</th>
				<td>${mapUserPlayer.withdraw_count}</td>

			</tr>

			</tbody>
		</table>
		<br>
	</div>
</div>
</form:form>
