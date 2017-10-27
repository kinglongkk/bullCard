<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/userAgent/agentMessage.html?search.id=${command.result.id}" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
        <div class="table-responsive" id="tab-1">
            <table class="table dataTable">
                <tbody>
                    <tr class="tab-title">
                        <th class="bg-tbcolor">商户号</th>
                        <td>${command.result.merchantNo}</td>
                        <th class="bg-tbcolor">商户密匙</th>
                        <td>
                            ${command.result.merchantKey}
                            <soul:button cssClass="btn btn-filter" text="重置" opType="ajax" dataType="json" target="${root}/userAgent/persist.html?result.merchantKey=reset" precall="validateForm" post="getCurrentFormData" callback="query" />
                        </td>
                    </tr>
                    <tr class="tab-title">
                        <th class="bg-tbcolor">商户logo</th>
                        <td>
                            <img src="${soulFn:getImagePathWithDefault(domain, command.result.merchantLogo,resRoot.concat('/images/myaccount.jpg'))}" class="logo-size-h100">
                            <soul:button target="${root}/userAgent/toUploadLogoPortrait.html?result.id=${command.result.id}" text="点击修改Logo" title="${views.setting['myAccount.editHeadPortrait']}" opType="dialog" callback="query"></soul:button>
                        </td>
                        <th class="bg-tbcolor">货币汇率</th>
                        <td>
                            <select name="result.exchangeRateId">
                                <c:forEach items="${exchange.result}" var="exchange">
                                    <option value="${exchange.id}" ${command.result.exchangeRateId eq exchange.id?'selected="selected"':''}>${exchange.name}(1:${exchange.exchange})</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="tab-title">
                        <th class="bg-tbcolor">商户主页链接</th>
                        <td><form:input path="result.merchantIndexLink" value="${command.result.merchantIndexLink}" /></td>
                        <th class="bg-tbcolor">商户充值链接</th>
                        <td><form:input path="result.merchantRechargeLink" value="${command.result.merchantRechargeLink}" /></td>
                    </tr>
                    <tr class="tab-title">
                        <th class="bg-tbcolor">额度</th>
                        <td>${soulFn:formatCurrency(command.result.quota)}</td>
                        <th class="bg-tbcolor">警告类型</th>
                        <td>
                            <c:choose>
                                <c:when test="${command.result.warnType eq 'GREEN'}">无</c:when>
                                <c:when test="${command.result.warnType eq 'BLUE'}">蓝色</c:when>
                                <c:when test="${command.result.warnType eq 'YELLOW'}">黄色</c:when>
                                <c:when test="${command.result.warnType eq 'RED'}">红色</c:when>
                            </c:choose>
                        </td>
                    </tr>
                    <tr class="tab-title">
                        <th class="bg-tbcolor">当前额度</th>
                        <td>${soulFn:formatCurrency(command.result.currentQuota)}</td>
                        <th class="bg-tbcolor">状态</th>
                        <td>
                            <c:choose>
                                <c:when test="${command.result.quotaStatus eq 1}">正常</c:when>
                                <c:when test="${command.result.quotaStatus eq 2}">欠费</c:when>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br>
            <div class="modal-footer">
                <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/userAgent/persist.html" precall="validateForm" post="getCurrentFormData" callback="query" />
            </div>
        </div>
    </div>
</form:form>
<soul:import res="site/agent/AgentInfo"/>

