<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.PayAccountVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<body>
<form:form id="editForm" action="${root}/payAccount/edit.html" method="post">
    <form:hidden path="result.id"/>
    <div id="validateRule" style="display: none">${command.validateRule}</div>


    <form:hidden path="result.bankCode"/>
    <form:hidden path="result.fullName"/>
    <form:hidden path="result.accountType"/>
    <form:hidden path="result.account"/>
    <input name="token" hidden="hidden" value="${token}">
    <input type="hidden" id="depositDefaultCount" name="result.depositDefaultCount" value="<fmt:formatNumber value="${command.result.depositDefaultCount}" pattern="0"/>">
    <input type="hidden" id="depositDefaultTotal" name="result.depositDefaultTotal" value="<fmt:formatNumber value="${command.result.depositDefaultTotal}" pattern="0"/>">
    <input name="result.fullRank" value="${empty command.result.fullRank?false:command.result.fullRank}" type="hidden">
    <input id="code" name="result.payChannelCode" hidden="hidden">

    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a href="javascript:void(0)" class="navbar-minimalize"><i class="icon iconfont"></i> </a></h2>
            <span>线上支付账户</span>
            <span>/</span><span>编辑线上支付账户</span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow clearfix">
                <div class="present_wrap"><b>编辑线上支付账户</b></div>
                <form class="m-t" action="">
                    <div class="form-group line-hi34 clearfix">
                        <label class="ft-bold col-sm-3 al-right">代号：</label>

                        <div class="col-sm-5">${command.result.code}</div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label for="result.payName" class="ft-bold col-sm-3 al-right line-hi34">账户名称：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input disabled="${disabled}" id="payName" path="result.payName"
                                            cssClass="form-control"/>

                                <span class="input-group-addon bdn">&nbsp;&nbsp;<span class="co-red">*</span></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label class="ft-bold col-sm-3 al-right line-hi34">存款渠道：</label>

                        <div class="col-sm-5" id="thirdError">
                                ${dicts.common.bankname[command.result.bankCode]}
                        </div>
                    </div>
                    <c:forEach items="${channelJson}" var="json">
                        <div class="form-group clearfix line-hi34">
                            <label class="ft-bold col-sm-3 al-right line-hi34">${messages.content['pay_channel.'.concat(json.get("view"))]}：</label>
                            <div class="col-sm-5">
                                    ${json.get("value")}
                            </div>
                        </div>
                    </c:forEach>




                    <div class="form-group clearfix">
                        <label for="result.disableAmount" class="ft-bold col-sm-3 al-right line-hi34">停用金额CNY：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input id="disableAmount" path="result.disableAmount"
                                                          cssClass="form-control"/>
                                <span tabindex="0" class=" help-popover input-group-addon" role="button"
                                      data-container="body" data-toggle="popover" data-trigger="focus"
                                      data-placement="top" data-original-title="" title="" data-html="true"
                                      data-content="${views.content['payAccount.disableAmount']}"><i
                                        class="fa fa-question-circle"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group clearfix">
                        <label class="ft-bold col-sm-3 al-right line-hi34">单次存款CNY：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input placeholder="单次存款最小值" id="singleDepositMin" path="result.singleDepositMin"
                                            cssClass="form-control"/>
                                <span class="input-group-addon bg-gray">~</span>
                                <form:input placeholder="单次存款最大值" id="singleDepositMax" path="result.singleDepositMax"
                                            cssClass="form-control"/>
                            </div>
                        </div>
                    </div>



                    <div class="form-group clearfix">
                        <label class="ft-bold col-sm-3 al-right line-hi34">有效分钟数：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input placeholder="请输入" maxlength="10" id="effectiveMinutes" path="result.effectiveMinutes"
                                            cssClass="form-control"/>
                                <span class="input-group-addon bg-gray">分钟</span>
                                <span tabindex="0" class=" help-popover input-group-addon" role="button" data-container="body" data-toggle="popover" data-trigger="focus" data-placement="top" data-original-title="" title="" data-html="true"  data-content="${views.content['payAccount.prompt']}"><i class="fa fa-question-circle"></i></span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group clearfix line-hi34" style="display:${command.result.id>0?"":"none"}">
                        <label class="ft-bold col-sm-3 al-right">累计入款次数：</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <input id="depositCount" name="result.depositCount" class="form-control" value="<fmt:formatNumber value="${command.result.depositCount}" pattern="0"/>">
                                <span class="input-group-addon bdn"><a class="m-l-sm revert" href="javascript:void(0)" sourceId="depositDefaultCount" targetId="depositCount">${views.column['PayAccount.revert']}</a></span>
                            </div>
                        </div>
                    </div>


                    <div class="form-group clearfix line-hi34" style="display:${command.result.id>0?"":"none"}">
                        <label class="ft-bold col-sm-3 al-right">累计入款金额：</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <input id="depositTotal" name="result.depositTotal" class="form-control" value="<fmt:formatNumber value="${command.result.depositTotal}" pattern="0"/>">
                                <span class="input-group-addon bdn"><a class="m-l-sm revert" href="javascript:void(0)" sourceId="depositDefaultTotal" targetId="depositTotal">恢复</a></span>
                            </div>
                        </div>
                    </div>

                    <hr class="m-t-sm m-b">
                    <div class="operate-btn">
                        <soul:button cssClass="btn btn-outline btn-filter btn-lg m-r" text="${views.common['commit']}" opType="ajax"
                                     dataType="json"
                                     target="${root}/payAccount/updateOnLine.html" precall="updatePayAccount"
                                     post="getCurrentFormData"
                                     callback="saveCallbak"/>
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

<soul:import res="site/payaccount/onLine/Edit"/>
<!--//endregion your codes 4-->
</html>