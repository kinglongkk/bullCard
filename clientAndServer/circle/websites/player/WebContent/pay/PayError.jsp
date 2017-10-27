<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>充值接口-充值结果</title>
</head>

<body>
<form id="form1" method="get" name="form1">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td height="30" align="center">
				<h1>
					※ 在线支付失败 ※
				</h1>
			</td>
		</tr>
	</table>
	<table bordercolor="#cccccc" cellspacing="5" cellpadding="0" width="400" align="center"
		border="0">		
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				订单号：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
				${orderId}
			</td>
		</tr>
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				订单金额(元)：</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
				${orderMoney}
			</td>
		</tr>		
		<tr>
			<td class="text_12" bordercolor="#ffffff" align="right" width="150" height="20">
				错误代码：
			</td>
			<td class="text_12" bordercolor="#ffffff" align="left">
				${statusCode}-${responseText}
			</td>
		</tr>
	</table>
</form>
</body>
</html>
