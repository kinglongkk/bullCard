<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserVo"--%>
<form:form>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont"></i> </a></h2>
            <span>赢的金币倍数比</span><span>/</span><span>详情</span>
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
                    <div class="pull-right m-l" data_id="${commandv.result.id}" id="btns">
                        <a href="/player/playerView.html?search.id=${commandv.result.id}" name="returnMain" nav-second="mainFrame" style="display: none"/>
                        <a href="" id="tot" nav-second="mainFrame" style="display: none"></a>
                        <input type="hidden" name="userStatus" value="${commandv.result.status}"/>
                        <input type="hidden" name="accountStatus" value="${accountStatus}"/>
                        <a nav-target="mainFrame" style="display: none" name="editTmpl" href="/noticeTmpl/tmpIndex.html?lastPage=t"><span></span></a>
                        <a nav-target="mainFrame" style="display: none" id="reloadView" href="/player/playerDetail.html?search.id=${commandv.result.id}"><span></span></a>
                        <c:choose>
                            <c:when test="${commandv.result.accountfreeze}">
                                <soul:button target="${root}/share/account/toCancelAccountFreeze.html?search.id=${commandv.result.id}&sign=player"
                                             text="${views.column['role.player.cancelAccountFreeze']}"
                                             opType="dialog"
                                             cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                                             confirm="${views.column['role.player.cancelAccountFreezeOk']}">
                                    ${views.column['role.player.cancelAccountFreeze']}
                                </soul:button>
                            </c:when>
                            <c:otherwise>
                                <soul:button target="${root}/share/account/freezeAccount.html?result.id=${commandv.result.id}"
                                             text="${views.column['role.player.freezeAccount']}"
                                             opType="dialog"
                                             cssClass="btn btn-outline btn-filter btn-sm${option_btn_disabled ?' disabled':''}"
                                            >
                                    ${views.column['role.player.freezeAccount']}
                                </soul:button>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${commandv.result.status == '2'}">
                                <soul:button
                                        target="${root}/player/view/cancelDisabled.html?userId=${commandv.result.id}"
                                        cssClass="btn btn-outline btn-filter btn-sm disabled"
                                        text="${views.column['role.player.disabled']}"
                                        name="accountDisabled"
                                        opType="ajax"
                                        confirm="${views.column['player.confirm.cancelstop']}"
                                        >
                                    ${views.column['role.player.disabled']}
                                </soul:button>
                            </c:when>
                            <c:otherwise>
                                <soul:button
                                        target="${root}/share/account/disabledAccount.html?result.id=${commandv.result.id}"
                                        text="${messages['role.player.accountDisabled']}"
                                        opType="dialog"
                                        cssClass="btn btn-outline btn-filter btn-sm">
                                    ${views.column['role.player.accountDisabled']}
                                </soul:button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="">
                    <div class="panel blank-panel">
                        <div class="">
                            <div class="panel-options">
                                <ul class="nav nav-tabs p-l-sm p-r-sm" id="mytab">
                                    <li class="active">
                                        <a data-toggle="tab" href="#analinfo" aria-expanded="true">基础信息</a>
                                    </li>
                                    <li>
                                        <a data-toggle="tab" href="#playerMatch" aria-expanded="false"
                                           data-href="${root}/vWarningPlayerDetail/matchInfo.html?sysUserId=${playerId}&matchIds=${matchIds}">赛事详情</a>
                                    </li>
                                    <li>
                                        <a data-toggle="tab" href="#playerInfo" aria-expanded="false"
                                           data-href="${root}/vWarningPlayerDetail/betMatchDetailInfo.html?matchIds=${matchIds}">游戏记录详情</a>
                                    </li>
                                    <li>
                                        <a data-toggle="tab" href="#bankCard" aria-expanded="false"
                                           data-href="${root}/playerWarningMultiple/loginInfo.html?sysUserId=${playerId}&date=${date}">登录记录详情</a>
                                    </li>
                                    <li>
                                        <a data-toggle="tab" href="#remark" aria-expanded="false"
                                           data-href="${root}/vWarningTransaction/trascationInfo.html?sysUserId=${playerId}&date=${date}" >充值记录详情</a>
                                    </li>
                                    <%--<li>--%>
                                        <%--<a data-toggle="tab" href="#journal" aria-expanded="false"--%>
                                          <%-->提现记录详情</a>--%>
                                    <%--</li>--%>


                                </ul>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="tab-content">
                                <!--玩家信息-->
                                <div id="analinfo" class="tab-pane active">
                                    <%@include file="/warning/wincount/View.jsp" %>
                                </div>
                                <div id="playerMatch" class="tab-pane"></div>
                                <div id="playerInfo" class="tab-pane"></div>
                                <div id="bankCard" class="tab-pane"></div>
                                <div id="remark" class="tab-pane"></div>
                                <%--<div id="journal" class="tab-pane"></div>--%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
<soul:import res="site/warning/Index"/>
