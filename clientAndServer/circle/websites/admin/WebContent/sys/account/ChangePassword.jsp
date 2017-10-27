<%--@elvariable id="command" type="org.soul.model.security.privilege.vo.SysUserVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
    <form>
        <div class="modal-body">

            <div id="validateRule" style="display: none">${command.validateRule}</div>
            <input value="${resetType}" type="hidden" name="resetType" />
            <input value="${command.result.id}" type="hidden" name="userId" />
            <input value="${command.result.username}" type="hidden" id="userName" />
            <input value="${isLogin}" type="hidden" id="isLogin" />
            <c:choose>
                <c:when test="${resetType eq 'payPwd'}">
                    <c:set var="passwordLable" value="${views.account['siteManage.siteMaintain.cancel.spwd']}"/>
                </c:when>
                <c:when test="${resetType eq 'loginPwd'}">
                    <c:set var="passwordLable" value="${views.account['MasterManage.edit.Edit.loginPwd']}"/>
                </c:when>
            </c:choose>
            <input type="hidden" id="resetTypeName" value="${passwordLable}">
            <div class="m-b-sm">${views.account['MasterManage.edit.ConfirmDisabled.masterUsername']}:<span class="co-blue">${command.result.username}</span></div>
            <div class="condition-options-wraper">
                <c:choose>
                    <c:when test="${resetType eq 'payPwd'}">
                        <div class="form-group">
                            <label>${views.resetPwd['newPwd']}</label>
                            <input type="password" name="permissionPwd" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>${views.resetPwd['confirmNewPwd']}</label>
                            <input type="password" name="confirmPermissionPwd" class="form-control">
                        </div>

                    </c:when>
                    <c:when test="${resetType eq 'loginPwd'}">
                        <div class="form-group">
                            <label>${views.resetPwd['newPwd']}</label>
                            <input type="password" name="password" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>${views.resetPwd['confirmNewPwd']}</label>
                            <input type="password" name="confirmLoginPwd" class="form-control">
                        </div>

                    </c:when>
                </c:choose>
            </div>
        </div>

        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" target="resetPwdByHandConfirm" tag="button" opType="function" text="${views.common['OK']}"></soul:button>
            <soul:button target="closePage" cssClass="btn btn-outline btn-filter" opType="function" text="${views.common['cancel']}"></soul:button>
        </div>
    </form>
</body>
<%@ include file="/include/include.js.jsp" %>
</html>
<soul:import res="site/account/ChangePassword"/>
