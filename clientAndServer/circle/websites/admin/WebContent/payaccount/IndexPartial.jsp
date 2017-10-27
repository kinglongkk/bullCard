<%@ page import="g.model.payaccount.po.VPayAccount" %>
<%@ page import="g.model.payaccount.po.VPayAccount" %>
<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.VPayAccountListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<c:set var="poType" value="<%= VPayAccount.class %>"></c:set>

<!--//region your codes 1-->
<div id="editable_wrapper" class="dataTables_wrapper" role="grid">
<div class="table-responsive table-min-h">
    <table class="table table-striped table-hover dataTable m-b-sm" aria-describedby="editable_info">
        <thead>
        <tr role="row" class="bg-gray">
            <th><input type="checkbox" class="i-checks"></th>
            <th>${views.common['number']}</th>
            <th>
                代号
            </th>
            <th>${views.column['VPayAccount.accountName']}</th>
            <th>${views.column['VPayAccount.account']}</th>
            <th class="inline">
                    <select name="search.bankCode" data-placeholder="存款渠道" value="${command.search.bankCode}"  class="btn-group chosen-select-no-single" tabindex="-1" callback="query">
                        <option value="" selected>存款渠道</option>
                        <c:forEach  items="${filterDeposit}" var="item" varStatus="status">
                            <c:choose>
                                <c:when test="${command.search.type=='1'}">
                                    <c:if test="${item.type!='3'}">
                                        <option value="${item.bankName}" >${dicts.common.bankname[item.bankName]}</option>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${item.type=='3'}">
                                        <option value="${item.bankName}" >${dicts.common.bankname[item.bankName]}</option>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select>
            </th>
            <th>姓名</th>
            <soul:orderColumn poType="${poType}" property="disableAmount" column="停用金额CNY"/>
            <soul:orderColumn poType="${poType}" property="depositCount" column="存款次数"/>
            <soul:orderColumn poType="${poType}" property="depositTotal" column="存款金额CNY"/>
            <th class="inline">
                <gb:select name="search.status" value="${command.search.status}" cssClass="btn-group chosen-select-no-single" prompt="${views.common['all']}" list="${statusMap}" callback="query"/>
            </th>
            <th>是否启用</th>
            <th>操作</th>
        </tr>
        <tr class="bd-none hide">
            <th colspan="13">
                <div class="select-records"><i class="fa fa-exclamation-circle"></i>${views.role['player.cancelSelectAll.prefix']}&nbsp;<span id="page_selected_total_record"></span>${views.role['player.cancelSelectAll.middlefix']}
                    <soul:button target="cancelSelectAll" opType="function" text="${views.role['player.cancelSelectAll']}"/>${views.role['player.cancelSelectAll.suffix']}
                </div>
            </th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="stat">
                <tr class="tab-detail">
                    <c:choose>
                        <c:when test="${p.status eq '1'}">
                            <c:set var="color" value="label-success"></c:set>
                        </c:when>
                        <c:when test="${p.status eq '2'}">
                            <c:set var="color" value="label-danger"></c:set>
                        </c:when>
                        <c:when test="${p.status eq '3'}">
                            <c:set var="color" value="label-info"></c:set>
                        </c:when>
                    </c:choose>
                    <td><label><input type="checkbox" class="i-checks" value="${p.id}"></label></td>
                    <td>${(command.paging.pageNumber-1)*command.paging.pageSize+(stat.index+1)}</td>
                    <td>${p.code}</td>

                    <td>${p.payName}</td>
                    <td>${p.account}</td>
                    <td>${dicts.common.bankname[p.bankCode]}</td>
                    <td>${p.fullName}</td>
                    <%--<td>--%>
                        <%--<div class="btn-group" ajaxId="${p.id}">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${p.fullRank}">--%>
                                    <%--<span class="btn btn-warning dropdown-toggle btn-xs">全部层级</span>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<button data-toggle="dropdown" class="btn btn-warning dropdown-toggle btn-xs">${empty p.payRankNum?0:p.payRankNum}个<i class="fa fa-angle-down"></i></button>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--<ul class="dropdown-menu lang">--%>
                                <%--&lt;%&ndash;<c:forEach items="${p.payRankList}" var="payRank">--%>
                                    <%--<li>--%>
                                        <%--<a href="javascript:void(0)">${payRank.rankName}</a>--%>
                                        <%--<soul:button tag="a" target="${root}/vPayAccount/delpayrank.html?search.id=${payRank.payRankId}" text="" opType="ajax" cssClass="clo" confirm="${views.role['playerrank.delete']}" callback="query"><img src="${resComRoot}/images/cle.png"></soul:button>--%>
                                        <%--&lt;%&ndash;<a href="/playerRank/rank_delete.html?id=${payRank.payRankId}" nav-second='mainFrame' class="clo"><img src="${resComRoot}/images/cle.png"></a>&ndash;%&gt;--%>
                                    <%--</li>--%>
                                <%--</c:forEach>&ndash;%&gt;--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</td>--%>
                    <td>${empty p.disableAmount?0:soulFn:formatCurrency(p.disableAmount)}${views.common['yuan']}</td>
                    <td>${empty p.depositCount?0:p.depositCount}${views.common['ci']}</td>
                    <td>${empty p.depositTotal?0:soulFn:formatCurrency(p.depositTotal)}${views.common['yuan']}</td>
                    <td><span class="label ${color}" id="status${stat.index}">${dicts.player.pay_account_status[p.statusLabel]}</span></td>
                    <td>
                        <c:choose>
                            <c:when test="${p.status eq '1'}">
                                <input type="checkbox" name="my-checkbox" tt="${stat.index}" payRankId="${p.id}" data-size="mini" checked>
                            </c:when>
                            <c:when test="${p.status eq '2'}">
                                <input type="checkbox" name="my-checkbox" tt="${stat.index}" payRankId="${p.id}" data-size="mini">
                            </c:when>
                            <c:when test="${p.status eq '3'}">
                                <soul:button target="${root}/payAccount/thawInfo.html?search.id=${p.id}" text="${views.content['payAccount.unfreeze']}" opType="dialog" callback="callBackQuery" />
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${command.search.type=='1'}">
                                <%--<shiro:hasPermission name="content:payaccount_edit">--%>
                                <a href="/payAccount/companyEdit.html?id=${p.id}" nav-second="mainFrame">编辑</a>
                                <%--</shiro:hasPermission>--%>
                            </c:when>
                            <c:otherwise>
                                <%--<shiro:hasPermission name="content:onlineaccount_edit">--%>
                                <a href="/payAccount/onLineEdit.html?id=${p.id}" nav-second="mainFrame">编辑</a>
                                <%--</shiro:hasPermission>--%>
                            </c:otherwise>

                        </c:choose>
                        <span class="dividing-line m-r-xs m-l-xs">|</span>
                        <a href="/vPayAccount/detail.html?result.id=${p.id}&search.type=${command.search.type}" nav-target="mainFrame">${views.common['detail']}</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="13" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i>
                            <c:if test="${command.search.type eq '1'}">
                                ${views.content['payAccount.notInMoneyMessage']}
                            </c:if>
                            <c:if test="${command.search.type eq '2'}">
                                ${views.content['payAccount.notPayMessage']}
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <li style="display: none" id="rankTmpl">
        <a href="javascript:void(0)">{rankName}</a>
        <soul:button permission="content:payaccount_delete" tag="a" target="${root}/vPayAccount/delpayrank.html?search.id={payRankId}" text="" opType="ajax" cssClass="clo" confirm="${views.role['playerrank.delete']}" callback="query"><img src="${resRoot}/images/cle.png"></soul:button>
    </li>
</div>
<soul:pagination/>
</div>

