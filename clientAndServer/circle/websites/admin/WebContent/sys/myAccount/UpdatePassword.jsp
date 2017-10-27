<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
    <form:form>
    <div id="validateRule" style="display: none">${rule}</div>
    <!--修改密码-->
    <div class="modal-body">
        <div class="form-group clearfix line-hi34 ${first ? 'hide' : ''}">
            <label class="col-xs-3 al-right">${views.column['myAccount.oldPassword']}：</label>
            <div class="col-xs-8 p-x"><input type="password" class="form-control" name="password"></div>
        </div>
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">${views.column['myAccount.newPassword']}：</label>
            <div class="col-xs-8 p-x"><input type="password" class="form-control" name="newPassword"></div>
        </div>
        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">${views.column['myAccount.newRePassword']}：</label>
            <div class="col-xs-8 p-x"><input type="password" class="form-control" name="newRePassword"></div>
        </div>
    </div>
    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="${views.common.OK}" opType="ajax" precall="validateForm" target="${root}/sys/account/${type == 1 ? 'updatePassword' : 'updatePrivilegePassword'}.html" dataType="json" post="getCurrentFormData" callback="closePage"/>
        <soul:button cssClass="btn btn-outline btn-filter" target="closePage" opType="function" text="${views.common.cancel}"/>
    </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/sys/account/Index"/>
</html>