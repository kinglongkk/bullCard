<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<html lang="zh-CN">
<head>
    <title>编辑</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>

<form:form id="editForm" action="${root}/game/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="modal-body">

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                游戏名称
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.name" cssClass="form-control m-b" value="${command.result.name}" />

                <input type="hidden" name="result.createUser" value="${command.result.createUser}" />
                <input type="hidden" name="result.createTime" value="${command.result.createTime}" />
                <input type="hidden" name="result.isDeleted" value="${command.result.isDeleted}" />
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                游戏代码
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="result.code" cssClass="form-control m-b" value="${command.result.code}" />
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
                            <option value="10" ${command.result.status eq '10'?'selected="selected"':''}>${views.common['enable']}</option>
                            <option value="20" ${command.result.status eq '20'?'selected="selected"':''}>${views.common['forbidden']}</option>
                            <option value="30" ${command.result.status eq '30'?'selected="selected"':''}>${views.common['safeguard']}</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                一级类型
            </label>
            <div class="col-xs-8 p-x">
                <select name="result.firstType" id="firstType">
                    <c:forEach items="${firstName}" var="firstTypeName">
                            <option value="${firstTypeName.code}" ${command.result.firstType eq firstTypeName.code ?'selected="selected"':''}>${firstTypeName['trans']}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                二级类型
            </label>
            <div class="col-xs-8 p-x">
                <select name="result.type" id="typeOption">
                    <c:choose>
                    <c:when test="${command.result.type eq null or command.result.type eq ''}">
                        <option value="${sportType[0].code}">${sportType[0].trans}</option>
                    </c:when>
                    </c:choose>
                    <c:forEach items="${secondType}" var="secondTypeName">
                        <option value="${secondTypeName['code']}" ${command.result.type eq secondTypeName['code']?'selected="selected"':''}>${secondTypeName['trans']}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/game/persist.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <c:choose>
            <c:when test="${command.result.status eq null or command.result.status eq ''}">
                <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
            </c:when>
            <c:otherwise>
                <soul:button cssClass="btn btn-default" text="删除" opType="ajax" dataType="json" target="${root}/game/delete.html?id=${command.result.id}" callback="deleteCallbak" confirm="您确定要删除该条记录吗？" />
            </c:otherwise>
        </c:choose>
    </div>

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/game/Edit"/>
</html>