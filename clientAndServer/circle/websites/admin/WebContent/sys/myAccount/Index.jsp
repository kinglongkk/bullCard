<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="g.model.master.player.vo.MyAccountVo"--%>
<c:set var="_emptyPlace" value="--"></c:set>
<div class="row">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['系统设置']}</span>
        <span>/</span><span>${views.column['myAccount.myAccount']}</span>
        <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
    </div>
    <form:form action="${root}/sys/account/myAccount.html" method="post">
    <div class="col-lg-12">
        <div class="wrapper white-bg shadow">
            <div class="present_wrap"><b>${views.column['myAccount.myAccount']}</b></div>
            <div class="clearfix personalInfo_wrap">
                <div class="img pull-left">
                    <soul:button target="${root}/sys/account/toUploadHeadPortrait.html" text="${views.column['myAccount.uploadHeadPortrait']}" title="${views.setting['myAccount.editHeadPortrait']}" opType="dialog" callback="callBackQuery"></soul:button>
                    <!-- TODO cj set default img -->
                    <img src="${soulFn:getImagePathWithDefault(domain, command.result.avatarUrl,resRoot.concat('/images/myaccount.jpg'))}" class="logo-size-h100">
                </div>
                <div class="con clearfix">
                    <div class="col-sm-4">
                        <b class="title">${command.result.username}</b>
                        <div>
                            <span>${command.result.realName}</span>
                            <c:choose>
                                <c:when test="${command.result.sex eq 'male'}">
                                    <img src="${resRoot}/images/sex-man.png" class="m-l-xs m-r-sm">
                                </c:when>
                                <c:when test="${command.result.sex eq 'female'}">
                                    <img src="${resRoot}/images/sex-woman.png" class="m-l-xs m-r-sm">
                                </c:when>
                                <c:otherwise>
                                    <img src="${resRoot}/images/sex-intersex.png" class="m-l-xs m-r-sm">
                                </c:otherwise>
                            </c:choose>
                            <span class="m-l-sm">${views.column['myAccount.type.'.concat(type)]}</span>
                            <c:if test="${type eq '2'}">
                                <shiro:hasPermission name="system:subaccount_role">
                                    <a href="/subAccount/role.html" nav-target="mainFrame">${views.column['myAccount.viewMyPrivilege']}</a>
                                </shiro:hasPermission>
                            </c:if>
                            <c:if test="${type eq '21'}">
                                <shiro:hasPermission name="system:subaccount_role">
                                <a href="/subAccount/role.html?readOnly=true" nav-target="mainFrame">${views.column['myAccount.viewMyPrivilege']}</a>
                                </shiro:hasPermission>
                            </c:if>
                        </div>
                        <div>
                            <soul:button target="${root}/sys/account/toUpdatePassword.html" text="${views.column['myAccount.updateAccountPassword']}" opType="dialog" cssClass="upd"></soul:button>
                        </div>
                        <div>
                            <soul:button target="${root}/sys/account/toUpdatePrivilegePassword.html" text="${views.column['myAccount.updatePrivilegePassword']}" opType="dialog" cssClass="upd"></soul:button>
                        </div>
                        <div>
                            <soul:button target="${root}/sys/account/toUpdatePersonInfo.html" text="${views.column['myAccount.updatePersonInfo']}" opType="dialog" cssClass="upd" callback="callBackQuery"></soul:button>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div>${views.column['subAccount.list.nickname']}：${empty command.result.nickname ?_emptyPlace :command.result.nickname}</div>
                        <div>${views.column['myAccount.mobile']}：${empty command.mobilePhone.contactValue ? _emptyPlace:soulFn:overlayTel(command.mobilePhone.contactValue)}</div>
                        <div>${views.column['myAccount.email']}：${empty command.email.contactValue ?_emptyPlace :soulFn:overlayEmaill(command.email.contactValue)}</div>
                    </div>
                </div>
            </div>
            <b class="m-l">${views.column['myAccount.recentLoginLog']}</b>
            <div class="table-responsive table-min-h table_border">
                <table class="table table-striped table-hover dataTable m-b-none">
                    <thead>
                    <tr class="bg-gray">
                        <th>${views.column['myAccount.log.loginTime']}</th>
                        <th>${views.column['myAccount.log.ip']}</th>
                        <th>${views.column['myAccount.log.device']}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${command.sysAuditLogs}" var="log">
                            <tr>
                                <td class="text_c">${soulFn:formatDateTz(log.operateTime, DateFormat.DAY_SECOND, timeZone)}</td>
                                <td>${soulFn:formatIp(log.operateIp)}</td>
                                <td>${log.clientOs}&nbsp;${log.clientBrowser}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <c:if test="${not empty command.sysAuditLogs && command.countLogNumber gt 10}">
                <div class="clearfix m-r"><a class="pull-right" href="/report/log/logList.html?search.moduleType=1&search.operator=${command.result.username}" nav-target="mainFrame">${views.setting['common.more']}</a></div>
            </c:if>
        </div>
    </div>
    </form:form>
</div>
<soul:import res="site/sys/account/Index"/>