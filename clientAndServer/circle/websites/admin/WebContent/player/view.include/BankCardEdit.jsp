<%--@elvariable id="command" type="so.wwb.circle.model.master.player.vo.UserBankcardVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<c:if test="${command.result==null}">
    <div class="no-content_wrap">
        <i class="fa fa-exclamation-circle"></i> ${views.role['Player.detail.bankcard.noresult']}
    </div>
</c:if>
<c:if test="${command.result!=null}">
    <form:form method="post">
        <div id="validateRule" style="display: none">${validate}</div>
        <form:input path="search.id" type="hidden" value="${command.result.id}"/>
        <div class="modal-body">
            <div class="form-group over clearfix">
                <label class="col-xs-2 al-right">${views.role['Player.detail.bank.realName']}</label>
                <div class="col-xs-9 p-x">${command.result.bankcardMasterName}</div>
            </div>
            <div class="form-group over clearfix">
                <label class="col-xs-2 al-right">${views.role['Player.detail.bank.bank']}</label>
                <div class="col-xs-9 p-x">
                    <%--<form:select class="btn-group chosen-select-no-single" path="result.bankName">
                        <c:forEach items="${bankListVo.result}" var="i">
                            <option value="${i.bankName}" ${command.result.bankName==i.bankName?'selected':''}>${dicts.common.bankname[i.bankName]}</option>
                        </c:forEach>
                    </form:select>--%>
                    <c:set var="bankcount" value="${fn:length(bankListVo.result)}"></c:set>
                        <c:choose>
                            <c:when test="${command.result.bankType eq 'wechat'}">
                                <span class="pay-third ${command.result.bankType}pay"></span>${dicts.common.bankname[command.result.bankType]}
                            </c:when>
                            <c:when test="${command.result.bankType eq 'alipay'}">
                                <span class="pay-third ${command.result.bankType}"></span>${dicts.common.bankname[command.result.bankType]}
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${bankListVo.result}" var="i" varStatus="status">
                                    <div style="float: left;padding: 5px;">
                                        <span>
                                            <input name="result.bankName" type="radio" value="${i.bankName}" ${command.result.bankName==i.bankName?'checked':''} class="ignore">
                                            <div class="pay-bank sm ${i.bankName}" bank="${i.bankName}" style="padding-left: 40px;width: 180px;cursor: pointer;">${i.bankShortName}</div>
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="col-xs-2 al-right" for="result.bankcardNumber">${views.role['Player.detail.bank.cardNumber']}</label>
                <div class="col-xs-9 p-x">
                    <form:input type="text" class="form-control" path="result.bankcardNumber" value="${command.result.bankcardNumber}"/>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="${views.common['OK']}" opType="ajax" dataType="json" target="${root}/player/view/bankCardSave.html" post="getCurrentFormData"/>
            <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
        </div>
    </form:form>
</c:if>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/player/agent/BankCardEdit"/>
</html>