<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="so.wwb.circle.model.master.player.vo.UserBankcardListVo"--%>
<%--@elvariable id="bankInfo" type="so.wwb.circle.model.company.vo.BankVo"--%>
<!--银行卡-->
<%--<div class="clearfix line-hi25">
    <c:if test="${fn:length(command.result)>0}">
        <soul:button target="${root}/player/view/bankEdit.html?search.userId=${command.search.userId}" userId="${command.search.userId}" text="${views.role['Player.detail.bank.editBankInfo']}" opType="dialog" cssClass="pull-right" callback="bankcard.queryBankCard"/>
    </c:if>
</div>--%>
<div id="editable_wrapper" class="dataTables_wrapper" role="grid">
    <div class="table-responsive" id="tab-1">
<table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
    <thead>
    <tr>
        <th>${views.column["UserBankcard.bankName"]}</th>
        <th>${views.column["UserBankcard.bankcardMasterName"]}</th>
        <th>${views.column["UserBankcard.bankcardNumber"]}</th>
        <th>${views.column["UserBankcard.createTime"]}</th>
        <th>${views.column["UserBankcard.useCount"]}</th>
        <th>${views.common['status']}</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${command.result}" var="l" varStatus="vs">
        <c:set value="${fn:toLowerCase(l.bankName)}" var="bankName"></c:set>
        <tr>
            <td><%--<img src="${resComRoot}<c:forEach var="item" items="${bankMap}">
                    <c:if test="${item.key eq bankName}">${item.value.bankIcon}</c:if>
                    </c:forEach>">--%>
                <c:choose>
                    <c:when test="${bankName eq 'wechat'}">
                        <span class="pay-third ${bankName}pay"></span>
                    </c:when>
                    <c:when test="${bankName eq 'alipay'}">
                        <span class="pay-third ${bankName}"></span>
                    </c:when>
                    <c:otherwise>
                        <span class="pay-bank ${bankName}"></span>
                    </c:otherwise>
                </c:choose>
                    ${dicts.common.bankname[bankName]}
            </td>
            <td>${l.bankcardMasterName}</td>
            <td>${soulFn:formatBankCard(l.bankcardNumber)}</td>
            <td>${soulFn:formatDateTz(l.createTime, DateFormat.DAY_SECOND,timeZone)}</td>
            <td class="co-red">${l.useCount}</td>
            <td>
                <c:if test="${l.isDefault}">
                    <a href="javascript:void(0)" class="btn btn-xs btn-danger">${views.common['currentUse']}</a>
                </c:if>
                <c:if test="${!l.isDefault}">
                    <span>${views.common['historyUse']}</span>
                </c:if>
            </td>
            <td>
                <c:if test="${l.isDefault}">
                    <soul:button target="${root}/player/view/bankEdit.html?search.userId=${command.search.userId}&search.bankType=${l.bankType}" userId="${command.search.userId}" text="${views.role['Player.detail.bank.editBankInfo']}" opType="dialog" callback="bankcard.queryBankCard"/>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
        </div></div>
<script type="text/javascript">
    curl(['site/player/view.include/Bankcard'], function(Bankcard) {
        page.bankcard = new Bankcard();
    });
</script>