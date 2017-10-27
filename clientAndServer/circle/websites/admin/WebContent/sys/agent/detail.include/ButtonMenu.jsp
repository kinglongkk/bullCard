<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="sys_tab_wrap clearfix m-b-sm">
  <div class="m-sm">
    <b class="fs16">${views.role['Agent.detail.title']}</b>
    <div class="pull-right m-b-sm">
        <input type="hidden" name="id" value="${command.search.id}">
      <c:if test="${command.result.status eq '2'}">
        <c:set value="true" var="option_btn_disabled"></c:set>
      </c:if>
      <c:choose>
            <c:when test="${command.result.status eq '3'}">
                <soul:button permission="site:mastermanage_cancelfreeze" target="${root}/accountManage/cancelAccountFreeze.html?search.id=${command.search.id}"
                             text="${views.role['Agent.detail.cancelAccountFreeze']}"
                             opType="dialog"
                             cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                             callback="painView">
                    ${views.role['Agent.detail.cancelAccountFreeze']}
                </soul:button>
            </c:when>
            <c:otherwise>
                <soul:button permission="site:mastermanage_freezeaccount" target="${root}/accountManage/freezeAccount.html?search.id=${command.search.id}"
                             text="${views.role['Agent.detail.freezeAccount']}"
                             opType="dialog"
                             cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                             callback="painView">
                    ${views.role['Agent.detail.freezeAccount']}
                </soul:button>
            </c:otherwise>
      </c:choose>

      <c:choose>
            <c:when test="${command.result.status == '2'}">
                <soul:button
                        target=""
                        cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''} "
                        text="${views.role['Agent.detail.accountDisabled']}"
                        name="accountDisabled"
                        opType=""
                        confirm=""
                        callback="">
                    ${views.role['Agent.detail.accountDisabled']}
                </soul:button>
            </c:when>
            <c:otherwise>
                <soul:button permission="site:mastermanage_disabled"
                        target="${root}/accountManage/disabledAccount.html?search.id=${command.search.id}"
                        text="${messages['playerTag']['accountDisabled']}"
                        title="${views.site['MasterManage.detail.buttonMenu.accountDisable']}"
                        opType="dialog"
                        cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''} "
                        callback="painView">
                    ${views.role['Agent.detail.disabledAccount']}
                </soul:button>
            </c:otherwise>
      </c:choose>

      <soul:button permission="site:mastermanage_resetPwdByHand" title="${views.role['Agent.detail.resetLoginPwd']}" target="${root}/accountManage/resetPwdByHand.html?resetType=loginPwd&search.id=${command.search.id}"
                   cssClass="${option_btn_disabled ?'disabled':''} btn btn-outline btn-filter btn-sm"
                   text="" opType="dialog" tag="button">
          ${views.role['Agent.detail.resetLoginPwd']}
      </soul:button>

      <soul:button permission="site:mastermanage_resetPwdByHand" title="${views.role['Agent.detail.resetPermissionPwd']}" target="${root}/accountManage/resetPwdByHand.html?resetType=payPwd&search.id=${command.result.id}"
                   cssClass="${option_btn_disabled ?'disabled':''} btn btn-outline btn-filter btn-sm"
                   text="" opType="dialog" tag="button">
          ${views.role['Agent.detail.resetPermissionPwd']}
      </soul:button>

        <%-- 先隐藏有需要再添加 --%>
        <%--<a href="/report/fundsLog/list.html?search.agentname=${command.result.username}" class="btn btn-outline btn-filter btn-sm" nav-target="mainFrame">${views.role['Agent.detail.fundsLog']}</a>--%>
    </div></div>
</div>