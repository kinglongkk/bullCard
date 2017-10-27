<%--@elvariable id="result" type="g.model.master.player.po.Remark"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>
    <form:form method="post">
        <div id="validateRule" style="display: none">${validateRule}</div>
        <input type="hidden" name="result.id" value="${sysParam.result.id}"/>
        <div class="modal-body">
            <div class="form-group over clearfix" style="display: none;">
                <label class="col-xs-3 al-right line-hi34">参数名称：</label>
                <div class="input-group m-b col-xs-9">
                    <input class="form-control" disabled="disabled" name="result.paramCode" value="${sysParam.result.paramCode}"/>
                </div>
            </div>
            <div class="form-group over clearfix" style="display: none;">
                <label class="col-xs-3 al-right line-hi34">默认值：</label>
                <div class="input-group m-b col-xs-9">
                    <input class="form-control" disabled="disabled" name="result.defaultValue" value="${sysParam.result.defaultValue}" maxlength="100"/>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="al-right pull-left line-hi34">
                    <c:choose>
                        <c:when test="${sysParam.result.paramCode eq 'sys_win_max'}">
                            单注最高可赢金额：
                        </c:when>
                        <c:when test="${sysParam.result.paramCode eq 'sys_bet_min'}">
                            单注最低限额：
                        </c:when>
                        <c:otherwise>当前值：</c:otherwise>
                    </c:choose>
                </label>
                <div class="input-group m-b pull-left">
                    <input class="form-control" name="result.paramValue" value="${sysParam.result.paramValue}" maxlength="100"/>
                </div>
            </div>
            <div class="form-group over clearfix" style="display: none;">
                <label class="col-xs-3 al-right line-hi34">描述：</label>
                <div class="input-group m-b col-xs-9">
                    <textarea class="form-control" name="result.remark" value="${sysParam.result.remark}" maxlength="200">${sysParam.result.remark}</textarea>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="${views.common['save']}" opType="ajax" dataType="json" target="${root}/parameterConfig/edit.html" post="getCurrentFormData"/>
            <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>