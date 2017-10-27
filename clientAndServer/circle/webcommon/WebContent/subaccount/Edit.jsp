<%--
  Created by IntelliJ IDEA.
  User: jeff
  Date: 15-10-19
  Time: 下午3:47
--%>
<%--@elvariable id="command" type="g.model.agent.vo.VSubAccountVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
    <form:form commandName="command">

    <div class="modal-body">
        <c:if test="${not empty command.sysUser.id}">
            <div class="form-group clearfix line-hi34 m-b-xxs">
                <label class="col-xs-3 al-right">ID：</label>
                <div class="col-xs-8 p-x ">
                    ${command.sysUser.id}
                </div>
            </div>
        </c:if>

        <c:set var="_isCreate" value="${empty command.sysUser.id}"></c:set>
        <c:set var="_isEdit" value="${not empty command.sysUser.id}"></c:set>
        <form:hidden path="search.id"></form:hidden>
        <form:hidden path="sysUser.id"></form:hidden>
        <form:hidden path="result.id"></form:hidden>

        <input type="hidden" value="${command.subSysCode}" name="subSysCode">
        <div id="validateRule" style="display: none">${command.validateRule}</div>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="sysUser.username">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.username']}
            </label>
            <div class="col-xs-8 p-x">
                <c:choose>
                    <c:when test="${_isCreate}">
                        <form:input path="sysUser.username" cssClass="form-control m-b"></form:input>
                    </c:when>
                    <c:otherwise>
                        <form:hidden path="sysUser.username"></form:hidden>
                        <div class="col-xs-8 p-x">${command.sysUser.username}</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:if test="${_isEdit}">
            <div class="form-group clearfix line-hi34 m-b-xxs">
                <label class="col-xs-3 al-right"></label>
                <div class="col-xs-8 p-x">
                    <label class="m-r-sm"><input type="radio" value="_permissionPwd" data-show="._permissionPwd" data-hide="._password" name="_changePasswordType" class="i-checks _changePasswordType">${views.subAccount['edit.changePassword']}</label>
                    <label><input type="radio" value="_password" data-show="._password" data-hide="._permissionPwd" name="_changePasswordType" class="i-checks _changePasswordType">${views.subAccount['edit.changePermissionPwd']}</label>
                    <input type="hidden" value="none" name="changePassword">
                </div>
            </div>
        </c:if>

        <div class="form-group clearfix line-hi34 m-b-xxs ${_isCreate ?'':'hide'} _password">
            <label class="col-xs-3 al-right" for="sysUser.password">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.loginPassword']}
            </label>
            <div class="col-xs-8 p-x">
                <form:password path="sysUser.password" cssClass="form-control m-b"></form:password>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs ${_isCreate ?'':'hide'} _password">
            <label class="col-xs-3 al-right" for="confirmPassword">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.confirmPassword']}
            </label>
            <div class="col-xs-8 p-x">
                <form:password path="confirmPassword" cssClass="form-control m-b"></form:password>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs ${_isCreate ?'':'hide'} _permissionPwd">
            <label class="col-xs-3 al-right" for="sysUser.permissionPwd">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.permissionPwd']}
            </label>
            <div class="col-xs-8 p-x">
                <form:password path="sysUser.permissionPwd" cssClass="form-control m-b"></form:password>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs ${_isCreate ?'':'hide'} _permissionPwd">
            <label class="col-xs-3 al-right" for="confirmPermissionPwd">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.confirmPermissionPwd']}
            </label>
            <div class="col-xs-8 p-x">
                <form:password path="confirmPermissionPwd" cssClass="form-control m-b"></form:password>
            </div>
        </div>
        <c:if test="${_isEdit}">
            <div class="form-group clearfix line-hi34 m-b-xxs">
                <label class="col-xs-3 al-right"><span class="co-red3 m-r-xs">*</span>${views.subAccount['edit.status']}：</label>
                <div class="col-xs-8 p-x">
                    <c:forEach items="${command.userStatus}" var="status" varStatus="state">
                        <label class="${state.first ? 'm-r-sm':''}"><input type="radio" name="sysUserStatus" value="${status}" class="i-checks" ${command.sysUser.status eq status ? "checked":""}>${dicts.player.user_status[status]}</label>
                    </c:forEach>
                    <input type="hidden" value="${command.sysUser.status}" name="sysUser.status">
                </div>
            </div>
        </c:if>
        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="sysUser.nickname">
                <span class="co-red3 m-r-xs">*</span>
                ${views.subAccount['edit.nickname']}
            </label>
            <div class="col-xs-8 p-x">
                <form:input path="sysUser.nickname" cssClass="form-control m-b"></form:input>
            </div>
        </div>

        <div class="form-group clearfix line-hi34">
            <label class="col-xs-3 al-right">${views.subAccount['edit.sex']}</label>
            <div class="col-xs-8 p-x">
                <gb:select name="sysUser.sex" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.sex}" list="${command.sex}"></gb:select>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="email.contactValue">
                ${views.subAccount['edit.email']}
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" class="form-control m-b" name="email.contactValue" id="email.contactValue" value="${command.email.contactValue}">
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="mobilePhone.contactValue">
                ${views.subAccount['edit.phone']}
            </label>
            <div class="col-xs-8 p-x">
                <input type="text" class="form-control m-b" name="mobilePhone.contactValue" id="mobilePhone.contactValue" value="${command.mobilePhone.contactValue}">
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="sysUser.memo">
                ${views.subAccount['edit.memo']}
            </label>
            <div class="col-xs-8 p-x">
                <textarea class="form-control" name="sysUser.memo" id="sysUser.memo">${command.sysUser.memo}</textarea>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right" for="roleIds"><span class="co-red3 m-r-xs">*</span>${views.subAccount['edit.role']}</label>
            <div class="col-xs-8 p-x">
                <c:forEach items="${command.sysRoles}" var="sysRole">
                    <c:set var="role_checked" value="0"></c:set>
                    <c:forEach items="${command.result.roleIdCollection}" var="role_id">
                        <c:if test="${role_id eq sysRole.id}">
                            <c:set var="role_checked" value="1"></c:set>
                            <span class="m-r-sm"><input type="checkbox" class="i-checks" checked value="${sysRole.id}" name="roleIds">${empty views.subAccount["role.".concat(sysRole.name)] ? sysRole.name:views.subAccount["role.".concat(sysRole.name)]}</span>
                        </c:if>
                    </c:forEach>
                    <c:if test="${role_checked eq 0}">
                        <span class="m-r-sm"><input type="checkbox" class="i-checks" value="${sysRole.id}" name="roleIds">
                            ${empty views.subAccount["role.".concat(sysRole.name)] ? sysRole.name:views.subAccount["role.".concat(sysRole.name)]}
                        </span>
                    </c:if>
                </c:forEach>
                <div class="m-t-n"><soul:button target="previewRole" tag="a" text="" opType="function"><i class="fa fa-search"></i> ${views.subAccount['edit.preview']}</soul:button></div>
            </div>
        </div>
    </div>

        <div class="modal-footer">
            <soul:button target="${root}/subAccount/persist.html" text="" cssClass="btn btn-filter" precall="validateForm" opType="ajax" post="getCurrentFormData" callback="closePage">${views.common['OK']}</soul:button>
            <soul:button target="closePage" text="${views.common['cancel']}" opType="function" cssClass="btn btn-outline btn-filter"></soul:button>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="subaccount/Edit"/>
</html>
