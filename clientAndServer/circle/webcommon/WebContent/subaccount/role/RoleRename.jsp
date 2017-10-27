<%--
  Created by IntelliJ IDEA.
  User: jeff
  Date: 15-10-29
  Time: 上午10:22
--%>

<%--@elvariable id="sysRoleVo" type="org.soul.model.security.privilege.vo.SysRoleVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>

    <form>
        <input type="hidden" value="rename" name="type">
        <div class="modal-body">
            <div id="validateRule" style="display: none">${sysRoleVo.validateRule}</div>
            <div class="form-group clearfix line-hi34">
                <label class="col-xs-3 al-right" for="result.name">${views.subAccount['role.roleName']}</label>
                <div class="col-xs-8 p-x">
                    <input type="text" class="form-control" name="result.name" id="result.name" value="${sysRoleVo.result.name}">
                    <input type="hidden" name="result.id" value="${sysRoleVo.result.id}">
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button target="${root}/subAccount/changeRoleName.html" post="getCurrentFormData" precall="validateForm" callback="closePage" cssClass="btn btn-filter" text="" opType="ajax">
                ${views.common['OK']}
            </soul:button>
        </div>
    </form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>
