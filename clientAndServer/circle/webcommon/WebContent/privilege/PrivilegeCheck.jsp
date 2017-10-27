<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>status密码</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form>
    <input id="7f1a3d0c-f15e-4e7a-8836-fc7182298af9" type="hidden" value="${leftTimes}"/>
    <div id="validateRule" style="display: none">${rule}</div>
    <div class="modal-body">
        <div class="form-group clearfix">
            ${views.common['privilege.tip']}
        </div>
        <div class="form-group over clearfix">
            <label class="control-label">${views.common['privilege.password']}：</label>

            <div class="col-sm-12 p-x">
                <input id="privilegeCode" class="form-control" name="code" type="password" placeholder="${views.common['privilege.password.please']}"/>
            </div>
            <%--<div><input type="checkbox" id="togglePassword"><label for="togglePassword">显示密码</label></div>--%>
        </div>
        <div id="privilegeTipDiv" class="form-group over clearfix hide">
            <input type="hidden" name="requiedValiCode" value="">
            <label class="control-label">${views.common['privilege.valicode']}：</label>
            <div class="">
                <%--<div class="col-sm-6 p-x" style="width: 390px; float: left;"><input class="form-control" type="text" name="valiCode" placeholder="${views.common['privilege.valicode.please']}"></div>
                <img class="captcha-code" src="${root}/captcha/privilege.html?t=${random}" reloadable>--%>
                <soul:button cssClass="co-gray6 m-l" target="refreshCode" opType="function" text="refresh"><i class="fa fa-refresh"></i></soul:button>
                <span class="help-block m-b-none"><i class="fa fa-times-circle co-red3"></i> ${views.common['privilege.password.prefix']}&nbsp;<span class="co-red3">2</span>&nbsp;${views.common['privilege.password.suffix']}</span>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" precall="validateForm" target="${root}/privilege/valiPrivilege.html" opType="ajax" post="getCurrentFormData" dataType="json" text="${views.common.OK}" callback="showTips"/>
        <soul:button cssClass="btn btn-filter" target="closePage" opType="function" text="${views.common.cancel}"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="gb/common/Privilege"/>
</html>
