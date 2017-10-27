<%--@elvariable id="command" type="g.model.admin.agent.quotamanager.vo.VAgentQuotaListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>账户</th>
                <th>姓名</th>
                <th>总额度</th>
                <th>消耗额度</th>
                <th>预警状态</th>
                <th>状态</th>
                <th>货币汇率</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>
                        <a href="/vAgentManage/detail/detail.html?search.id=${p.agentId}" nav-target="mainFrame" class="co-blue">${p.username}</a>
                    </td>
                    <td>${p.realName}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.quota)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.currentQuota)}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.warnType eq 'GREEN'}">无</c:when>
                            <c:when test="${p.warnType eq 'BLUE'}">蓝色</c:when>
                            <c:when test="${p.warnType eq 'YELLOW'}">黄色</c:when>
                            <c:when test="${p.warnType eq 'RED'}">红色</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${p.quotaStatus eq 1}">正常</c:when>
                            <c:when test="${p.quotaStatus eq 2}">欠费</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty p.exchange}">
                                ${p.name}(1:${p.exchange})
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/vAgentQuota/edit.html?id=${p.id}" text="充值" opType="dialog" callback="query" />
                            <c:choose>
                                <c:when test="${p.status eq 1}">
                                    <soul:button target="${root}/vAgentQuota/delete.html?id=${p.id}&search.agentId=${p.agentId}&search.status=2" text="禁用" opType="ajax" dataType="json" confirm="您确定要禁用该代理吗？" callback="query" />
                                </c:when>
                                <c:otherwise>
                                    <soul:button target="${root}/vAgentQuota/delete.html?id=${p.id}&search.agentId=${p.agentId}&search.status=1" text="启用" opType="ajax" dataType="json" confirm="您确定要启用该代理吗？" callback="query" />
                                </c:otherwise>
                            </c:choose>
                            <a href="/vAgentManage/detail/detail.html?search.id=${p.agentId}" nav-target="mainFrame" class="co-blue">详细</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+9}" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
