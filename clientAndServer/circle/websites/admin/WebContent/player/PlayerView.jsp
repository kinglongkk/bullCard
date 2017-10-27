<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserVo"--%>
<form:form>
<div class="row">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont"></i> </a></h2>
        <span>${views.sysResource['角色']}</span><span>/</span><span>${views.sysResource['玩家管理']}</span>
        <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
            <em class="fa fa-caret-left"></em>${views.common['return']}
        </soul:button>
    </div>
    <div class="col-lg-12">
        <div class="wrapper white-bg clearfix shadow">
            <div class="sys_tab_wrap clearfix line-hi34 p-xs m-b-sm">
                <div class="pull-left">
                    <h3 class="pull-left m-r-sm">${views.column['Player.detail.title']}</h3>
                </div>
                <div class="pull-right m-l" data_id="${command.result.id}" id="btns">
                    <a href="/player/playerView.html?search.id=${command.result.id}" name="returnMain" nav-second="mainFrame" style="display: none"/>
                    <a href="" id="tot" nav-second="mainFrame" style="display: none"></a>
                    <input type="hidden" name="userStatus" value="${command.result.status}"/>
                    <input type="hidden" name="accountStatus" value="${accountStatus}"/>
                    <a nav-target="mainFrame" style="display: none" name="editTmpl" href="/noticeTmpl/tmpIndex.html?lastPage=t"><span></span></a>
                    <a nav-target="mainFrame" style="display: none" id="reloadView" href="/player/playerDetail.html?search.id=${command.result.id}"><span></span></a>

                    <%--账户停用--%>
                   <%--<c:if test="${command.result.status eq '2'}">
                        <c:set value="true" var="option_btn_disabled"></c:set>
                    </c:if>--%>
                    <c:choose>
                        <c:when test="${command.result.accountfreeze}">
                            <soul:button target="${root}/share/account/toCancelAccountFreeze.html?search.id=${command.result.id}&sign=player"
                                         text="${views.column['role.player.cancelAccountFreeze']}"
                                         opType="dialog"
                                         cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                                         callback="reloadViewWithoutReturnValue" confirm="${views.column['role.player.cancelAccountFreezeOk']}">
                                ${views.column['role.player.cancelAccountFreeze']}
                            </soul:button>
                        </c:when>
                        <c:otherwise>
                            <soul:button target="${root}/share/account/freezeAccount.html?result.id=${command.result.id}"
                                        text="${views.column['role.player.freezeAccount']}"
                                        opType="dialog"
                                        cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                                        callback="reloadViewWithoutReturnValue">
                                ${views.column['role.player.freezeAccount']}
                            </soul:button>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${command.result.status == '2'}">
                            <soul:button
                                    target="${root}/player/view/cancelDisabled.html?userId=${command.result.id}"
                                    cssClass="btn btn-outline btn-filter btn-sm disabled"
                                    text="${views.column['role.player.disabled']}"
                                    name="accountDisabled"
                                    opType="ajax"
                                    confirm="${views.column['player.confirm.cancelstop']}"
                                    callback="reloadViewWithoutReturnValue">
                                ${views.column['role.player.disabled']}
                            </soul:button>
                        </c:when>
                        <c:otherwise>
                            <soul:button
                                    target="${root}/share/account/disabledAccount.html?result.id=${command.result.id}"
                                    text="${messages['role.player.accountDisabled']}"
                                    opType="dialog"
                                    cssClass="btn btn-outline btn-filter btn-sm">
                                ${views.column['role.player.accountDisabled']}
                            </soul:button>
                        </c:otherwise>
                    </c:choose>

                    <%--<soul:button title="${views.column['role.player.resetLoginPwd']}" target="${root}/player/resetPwd/index.html?resetType=loginPwd&userId=${command.result.id}" cssClass="${option_btn_disabled ?'disabled':''} btn btn-outline btn-filter btn-sm" text="" opType="dialog" tag="button">--%>
                        <%--${views.column['role.player.resetLoginPwd']}--%>
                    <%--</soul:button>--%>

                    <%--由于次功能为404，暂时不启用 By Kyle--%>
                    <%--<a href="/report/fundsLog/list.html?search.username=${command.result.username}" class="btn btn-outline btn-filter btn-sm areport" nav-target="mainFrame">${views.column['role.player.fundsLog']}</a>--%>
                </div>
            </div>
            <div class="">
                <div class="panel blank-panel">
                    <div class="">
                        <div class="panel-options">
                            <ul class="nav nav-tabs p-l-sm p-r-sm">
                                <li class="active">
                                    <a data-toggle="tab" href="#playerInfo${command.result.id}" aria-expanded="true" data-load="1">玩家信息</a>
                                </li>
                                <li>
                                    <a data-toggle="tab" href="#bankCard${command.result.id}" aria-expanded="false"
                                       data-href="${root}/player/view/bankCard.html?search.userId=${command.result.id}">取款帐号</a>
                                </li>
                                <li>
                                    <a data-toggle="tab" href="#remark${command.result.id}" aria-expanded="false"
                                       data-href="${root}/playerRemark/remark.html?search.entityUserId=${command.result.id}&search.operatorId=${command.result.id}"  id="remark">备注</a>
                                </li>
                                <li>
                                    <a data-toggle="tab" href="#journal${command.result.id}" aria-expanded="false"
                                       data-href="${root}/playerLog/view/journal.html?search.entityId=${command.result.id}">日志</a>
                                </li>


                            </ul>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <!--玩家信息-->
                            <div id="playerInfo${command.result.id}" class="tab-pane active">
                                <%@include file="/player/PlayerDetail.jsp" %>
                            </div>
                            <!--银行卡-->
                            <div id="bankCard${command.result.id}" class="tab-pane"></div>
                            <!--备注-->
                            <div id="remark${command.result.id}" class="tab-pane"></div>
                            <!--日志-->
                            <div id="journal${command.result.id}" class="tab-pane"></div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form:form>
<soul:import res="site/player/Index"/>
