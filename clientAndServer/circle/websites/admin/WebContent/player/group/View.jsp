
<%--@elvariable id="command" type="g.model.VAaa.vo.VAaaVo"--%>
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
    <table class="table table-condensed table-hover table-bordered">
        <tr>
            <td> ${views.group['groupName']}：</td>
            <td>${command.result.groupName}</td>
            <td>是否为默认分组</td>
        </tr>
        <tr>
            <td> ${views.group['playernum']}</td>
            <td>${command.result.playernum}</td>
            <td>${true eq command.result.isDefault ? '是' : '否'}</td>

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

