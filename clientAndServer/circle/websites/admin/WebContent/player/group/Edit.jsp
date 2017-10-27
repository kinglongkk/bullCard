<%--@elvariable id="command" type="g.model.VAaa.vo.VAaaVo"--%>
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
<form:form id="editForm" action="" method="post">
    <form:input type="hidden" path="result.id" value="${command.result.id}"/>
    <form:input type="hidden" path="result.createUser" value="${command.result.createUser}"/>
    <div id="validateRule" style="display: none">${command.validateRule}</div>

    <!--//region your codes 3-->


    <div class="table-responsive">
        <div class=""></div>
        <table class="table table-condensed table-hover table-bordered">
            <tr>
                <td>${views.group['groupName']}：</td>
                <td><form:input class="form-control" path="result.groupName" value="${command.result.groupName}" maxlength="100"/></td>
            </tr>
        </table>
    </div>
    <div class="">
        <soul:button cssClass="btn btn-filter" text="${views.common['save']}" opType="ajax" dataType="json" target="${root}/vUserPlayerGroup/editUserGroup.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
    </div>
    <!--//endregion your codes 3  -->
</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import type="edit"/>
<!--//endregion your codes 4-->
</html>
