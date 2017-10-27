<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <title>编辑</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>
<form:form id="editForm" action="${root}/userAgentGame/edit.html" method="post">
    <form:hidden path="result.id" />
    <form:hidden path="result.agentId" value="${agentId}" id="agentId" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="modal-body">

        <c:choose>
            <c:when test="${not empty command.result.id}" >
                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        游戏返回链接
                    </label>
                    <div class="col-xs-8 p-x">
                        <input type="text" name="result.gameLink" value="${command.result.gameLink}" />
                    </div>
                </div>
                <form:hidden path="result.gameId" value="${command.result.gameId}" />
            </c:when>
            <c:otherwise>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        游戏类型
                    </label>
                    <div class="col-xs-8 p-x">
                        <select name="result.firstType" id="firstType">
                            <option value="">${views.common['pleaseSelect']}</option>
                            <c:forEach items="${firstTypeList.result}" var="firstTypeName">
                                <option value="${firstTypeName.type}">${firstTypeName.firstType}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        游戏名称
                    </label>
                    <div class="col-xs-8 p-x">
                        <select name="result.gameId" id="typeOption">
                        </select>
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        游戏返回链接
                    </label>
                    <div class="col-xs-8 p-x">
                        <input type="text" name="result.gameLink" value="${command.result.gameLink}" />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/userAgentGame/persist.html?persistKey=gameLink" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
        <soul:button cssClass="btn btn-default" text="取消" opType="function" target="closePage" />
    </div>

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/agent/AgentGameEdit"/>
</html>
