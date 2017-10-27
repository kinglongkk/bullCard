<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="org.soul.model.security.privilege.vo.SysUserVo"--%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
    <form:form>
        <div id="validateRule" style="display: none">${rule}</div>
        <div class="modal-body">
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right">账号：</label>
                <div class="col-xs-8 p-x">${command.result.username}</div>
            </div>
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right">昵称：</label>
                <div class="col-xs-8 p-x">
                    <input class="form-control" type="text" name="nickname" value="${command.result.nickname}" maxlength="30"/>
                </div>
            </div>
            <div class="form-group clearfix line-hi34">
                <label for="sex" class="col-xs-3 al-right"><span class="co-red3 m-r-xs">*</span>性别：</label>
                <div class="col-xs-8 p-x">
                    <gb:select name="sex" value="${command.result.sex}" prompt="${views.common['pleaseSelect']}" cssClass="btn-group chosen-select-no-single" list="${sexes}"/>
                </div>
            </div>
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right"><span class="co-red3 m-r-xs">*</span>手机：</label>
                <div class="col-xs-8 p-x">
                    <input class="form-control" name="phone" type="text" value="${phone}">
                    <input class="form-control" name="phoneId" type="hidden" value="${phoneId}">
                </div>
            </div>
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right"><span class="co-red3 m-r-xs">*</span>邮箱：</label>
                <div class="col-xs-8 p-x">
                    <input class="form-control" name="email" type="text" value="${email}">
                    <input class="form-control" name="emailId" type="hidden" value="${emailId}">
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button cssClass="btn btn-filter" text="${views.common.OK}" opType="ajax" precall="validateForm" target="${root}/agentAccount/updatePersonInfo.html" dataType="json" post="getCurrentFormData" callback="saveCallbak"/>
            <soul:button cssClass="btn btn-outline btn-filter" target="closePage" opType="function" text="${views.common.cancel}"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/sys/Index"/>
</html>