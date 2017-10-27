<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.PayAccountVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<body>
    <form:form id="editForm" action="${root}/payAccount/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <form:hidden path="result.channelJson" id="channelJson" />
    <form:hidden path="result.accountType" id="accountType" />
        <input name="token" hidden="hidden" value="${token}">
    <input type="hidden" value="2" name="result.type" />
    <input name="result.fullRank" value="${empty command.result.fullRank?false:command.result.fullRank}" hidden="hidden" />
    <input id="code" name="result.payChannelCode" hidden="hidden" />

    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a href="javascript:void(0)" class="navbar-minimalize"><i class="icon iconfont"></i></a></h2>
            <span>线上支付账户</span>
            <span>/</span>
            <span>新增线上支付</span>
            <soul:button target="goToLastPage" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>

        <div class="col-lg-12">
            <div class="wrapper white-bg shadow clearfix">
                <div class="present_wrap"><b>新增线上支付</b></div>
                <form class="m-t" action="">
                    <%-- 代号 --%>
                    <div class="form-group line-hi34 clearfix">
                        <label class="ft-bold col-sm-3 al-right">代号：</label>
                        <div class="col-sm-5">${command.result.code}</div>
                    </div>
                    <%-- 账户名称 --%>
                    <div class="form-group clearfix line-hi34">
                        <label for="result.payName" class="ft-bold col-sm-3 al-right line-hi34">
                            <span class="co-red m-r-sm">*</span>账户名称：
                        </label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input disabled="${disabled}" id="payName" path="result.payName" cssClass="form-control"/>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <%-- 存款渠道 --%>
                    <div class="form-group clearfix third">
                        <label class="ft-bold col-sm-3 al-right line-hi34">
                            <span class="co-red m-r-sm">*</span>存款渠道：
                        </label>
                        <div class="col-sm-5" id="thirdError">
                            <div class="input-group date">
                                <form:select path="result.bankCode" callback="bankChannel" cssClass="btn-group chosen-select-no-single">
                                    <option value="">请选择</option>
                                    <c:forEach items="${command.bankList}" var="p">
                                        <option value="${p.bankName}">${dicts.common.bankname[p.bankName]}</option>
                                    </c:forEach>
                                </form:select>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <%--&lt;%&ndash; 账户名称 &ndash;%&gt;--%>
                    <%--<div class="form-group clearfix hide payDomain">--%>
                        <%--<label class="ft-bold col-sm-3 al-right line-hi34">--%>
                            <%--<span class="co-red m-r-sm">*</span>${messages.content['pay_channel.payDomain']}：--%>
                        <%--</label>--%>
                        <%--<div class="col-sm-5" id="onLinePay">--%>
                            <%--<div class="input-group date">--%>
                                <%--<form:select path="onLinePay" callback="onLinePay" cssClass="btn-group chosen-select-no-single">--%>
                                    <%--<option value="">${views.common['pleaseSelect']}</option>--%>
                                    <%--<c:forEach items="${command.sysDomains}" var="p">--%>
                                        <%--<option value="${p.domain}">${p.domain}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</form:select>--%>
                                <%--<span class="input-group-addon bdn">&nbsp;&nbsp;</span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%-- 提示 --%>
                    <%--<div class="line-hi34 col-sm-12 bg-gray m-b">--%>
                        <%--<label class="ft-bold col-sm-3 al-right line-hi34"></label>--%>
                        <%--<span class="co-yellow m-r-sm m-l-sm"><i class="fa fa-exclamation-circle"></i></span>--%>
                        <%--${views.content['payAccount.add.monetaryTips']}--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash; 支持货币 &ndash;%&gt;--%>
                    <%--<div class="form-group clearfix line-hi34">--%>
                        <%--<label class="ft-bold col-sm-3 al-right">--%>
                            <%--<span class="co-red m-r-sm">*</span>${views.content['payAccount.add.supportMoney']}：--%>
                        <%--</label>--%>
                        <%--<div class="col-sm-5">--%>
                            <%--<div class="input-group date">--%>
                                <%--<div class="col-sm-5 currency" id="currenct"></div>--%>
                                <%--<span class="input-group-addon bdn">&nbsp;&nbsp;</span>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%-- 停用金额 --%>
                    <div class="form-group clearfix">
                        <label for="result.disableAmount" class="ft-bold col-sm-3 al-right line-hi34"><span
                                class="co-red m-r-sm">*</span>停用金额CNY：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input id="disableAmount" path="result.disableAmount" cssClass="form-control"/>
                                    <span tabindex="0" class=" help-popover input-group-addon" role="button" data-container="body" data-toggle="popover"
                                          data-trigger="focus" data-placement="top" data-original-title="" title="" data-html="true"
                                          data-content="${views.content['payAccount.disableAmount']}">
                                        <i class="fa fa-question-circle"></i>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <%-- 单笔存款 --%>
                    <div class="form-group clearfix">
                        <label class="ft-bold col-sm-3 al-right line-hi34">单笔存款：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input placeholder="单笔存款最小值" id="singleDepositMin" path="result.singleDepositMin" cssClass="form-control"/>
                                <span class="input-group-addon bg-gray">~</span>
                                <form:input placeholder="单笔存款最大值" id="singleDepositMax" path="result.singleDepositMax" cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                    <%-- 有效分钟数--%>
                    <div class="form-group clearfix">
                        <label class="ft-bold col-sm-3 al-right line-hi34">有效分钟数：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input placeholder="请输入" id="effectiveMinutes" path="result.effectiveMinutes" cssClass="form-control"/>
                                <span class="input-group-addon bg-gray">分钟</span>
                                    <span tabindex="0" class=" help-popover input-group-addon" role="button" data-container="body" data-toggle="popover"
                                          data-trigger="focus" data-placement="top" data-original-title="" title="" data-html="true"
                                          data-content="线上支付订单的有效时间。<br>超过有效时间仍未付款，系统会自动将【待支付】订单改为【超时】。">
                                        <i class="fa fa-question-circle"></i>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <hr class="m-t-sm m-b">
                    <%-- 提交 --%>
                    <div class="operate-btn">
                        <soul:button cssClass="btn btn-outline btn-filter btn-lg m-r" text="${views.common['commit']}" opType="ajax" dataType="json"
                                     target="${root}/payAccount/saveOnLine.html" precall="savePlayer" post="getCurrentFormData" callback="goToLastPage" refresh="true"/>
                        <soul:button target="goToLastPage" refresh="false" cssClass="btn btn-outline btn-filter btn-lg m-r" text="${views.common['cancel']}" opType="function">
                        </soul:button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</form:form>
</body>
<!--//region your codes 4-->

<soul:import res="site/payaccount/onLine/Add"/>
<!--//endregion your codes 4-->
</html>