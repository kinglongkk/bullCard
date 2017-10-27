<%--@elvariable id="command" type="g.model.master.player.vo.UserAgentVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--首页-->
<form:form>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>${views.home['index.title.agent']}</span>
            <span>/</span>
            <span>${views.home['index.title.index']}</span>
        </div>

        <div class="clearfix">
            <div class="m-b col-lg-5">
                <div class="shadow clearfix">
                    <div class="manage_tit">
                        <b class="m-l-md">${views.home['index.accountAbstract']}</b>
                    </div>
                    <ul class="system-announcement-list">
                        <li class="clearfix">
                                ${views.home['index.preOrderMoney']}
                            <span class="pull-right">${preOrderMoney}</span>
                        </li>
                        <li class="clearfix">
                                ${views.home['index.todayAddPlayer']}
                            <span class="pull-right">${addPlayerCount}</span>
                        </li>
                        <li class="clearfix">
                                ${views.home['index.lastLoginDay']}
                            <span class="pull-right">${soulFn:formatDateTz(command.result.lastLoginTime, DateFormat.DAY, timeZone)}</span>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="m-b col-lg-5">
                <div class="shadow clearfix">
                    <div class="manage_tit">
                        <b style="margin-left: 14%">${views.home['index.accountStatus']}</b>
                        <b style="margin-left: 14%">${views.home['index.enable']}</b>
                        <b style="margin-left: 14%">${views.home['index.disable']}</b>
                        <b style="margin-left: 14%">${views.home['index.freeze']}</b>
                    </div>

                    <c:if test="${not empty playerCountBalance && playerCountBalance.size() == 3}">
                        <div class="manage_tit">
                            <b style="margin-left: 14%">会员</b>
                            <b style="margin-left: 18%">${playerCountBalance.get(0).get("number")}</b>
                            <b style="margin-left: 16%">${playerCountBalance.get(1).get("number")}</b>
                            <b style="margin-left: 16%">${playerCountBalance.get(2).get("number")}</b>
                        </div>

                        <div class="manage_tit">
                            <b style="margin-left: 14%">余额</b>
                            <b style="margin-left: 16%">${playerCountBalance.get(0).get("wallet_balance")}</b>
                            <b style="margin-left: 12%">${playerCountBalance.get(1).get("wallet_balance")}</b>
                            <b style="margin-left: 12%">${playerCountBalance.get(2).get("wallet_balance")}</b>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>


</form:form>
<soul:import res="site/home/index"/>