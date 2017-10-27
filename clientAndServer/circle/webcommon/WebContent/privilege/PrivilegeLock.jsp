<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form>
    <div class="modal-body">
        <div class="clearfix sx-wrap scing">
            <span class="sx"><i class="fa fa-times-circle co-red3"></i></span>
            <div class="sx-data">
                <h1>${views.common['privilege.lock.error.time.limited']}</h1>
                <span>${views.common['privilege.lock.freeze.prefix']}<span class="co-red">${hour }${views.common.hour}${min }${views.common.minute}${sec }${views.common.second}</span>，${views.common['privilege.lock.freeze.suffix']}</span><br>
                ${customer}
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <%--<soul:button cssClass="btn btn-filter" target="${root}/share/reset.html" opType="dialog" post="getCurrentFormData" dataType="json" text="${views.common.resetPassword}"/>--%>
        <soul:button target="${root}/myAccount/toUpdatePrivilegePassword.html" text="${views.common.resetPassword}" opType="dialog" cssClass="btn btn-filter" callback="closePrivilege"></soul:button>
        <soul:button cssClass="btn btn-outline btn-filter" target="closePage" opType="function" text="${views.common.cancel}"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="gb/common/Privilege"/>
</html>
