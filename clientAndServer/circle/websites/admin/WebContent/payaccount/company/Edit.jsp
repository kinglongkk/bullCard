<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.PayAccountVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<body>
<form:form id="editForm" action="${root}/payAccount/edit.html" method="post">
    <form:hidden path="result.id"/>
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <input name="token" hidden="hidden" value="${token}">
    <form:hidden path="result.accountType"/>
    <form:hidden path="result.code" />

    <input type="hidden" id="depositDefaultCount" name="result.depositDefaultCount" value="<fmt:formatNumber value="${command.result.depositDefaultCount}" pattern="0"/>">
    <input type="hidden" id="depositDefaultTotal" name="result.depositDefaultTotal" value="<fmt:formatNumber value="${command.result.depositDefaultTotal}" pattern="0"/>">

    <input name="result.fullRank" value="${empty command.result.fullRank?false:command.result.fullRank}" type="hidden">

    <input id="code" name="result.payChannelCode" hidden="hidden">

    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a href="javascript:void(0)" class="navbar-minimalize"><i class="icon iconfont"></i> </a></h2>
            <span>公司入款账户</span>
            <span>/</span><span>编辑公司入款</span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow clearfix">
                <div class="present_wrap"><b>编辑收款账户</b></div>
                <form class="m-t" action="">
                    <div class="form-group line-hi34 clearfix">
                        <label class="ft-bold col-sm-3 al-right">代号：</label>

                        <div class="col-sm-5">${command.result.code}</div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label for="result.payName" class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>账户名称：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input disabled="${disabled}" id="payName" path="result.payName"
                                            cssClass="form-control"/>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <!-- 二维码图片 -->
                    <c:if test="${command.result.accountType=='2'}">
                    <div class="form-group clearfix line-hi34 bank" >
                        <label for="result.payName" class="ft-bold col-sm-3 al-right line-hi34">二维码图片：</label>
                        <div class="col-sm-5" style="width: 41.2%">
                            <div>
                                <c:if test="${not empty command.result.qrCodeUrl}">
                                    <img id="picUrl" src="${soulFn:getThumbPath(domain, command.result.qrCodeUrl, 200, 200)}" class="logo-size-h100"/>
                                </c:if>
                            </div>
                            <input id="image_file_path" class="file" type="file" accept="image/jpg" name="image_file_path" target="result.qrCodeUrl">
                            <div style="line-height: 20px; color: #A7A6A6;">请上传1M以内，JPG格式的图片</div>
                            <input type="hidden" name="result.qrCodeUrl" id="path" value="${command.result.qrCodeUrl}">
                        </div>
                    </div>
                    </c:if>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label  class="ft-bold col-sm-3 al-right line-hi34">账户类型：</label>

                        <div class="col-sm-5">
                            ${dicts.content.pay_account_account_type[command.result.accountType]}
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label class="ft-bold col-sm-3 al-right line-hi34">账号：</label>
                        <div class="col-sm-5">
                           ${command.result.account}
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label class="ft-bold col-sm-3 al-right line-hi34">开户行：</label>
                        <div class="col-sm-5">
                              <%--<form:input disabled="${disabled}" id="openAcountName" path="result.openAcountName" cssClass="form-control"/>--%>
                            <input id="openAcountName" name="result.openAcountName" class="form-control" value="${command.result.openAcountName}">
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label class="ft-bold col-sm-3 al-right line-hi34">姓名：</label>

                        <div class="col-sm-5">${command.result.fullName}</div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <c:if test="${command.result.accountType=='1'}">
                            <label class="ft-bold col-sm-3 al-right">银行：</label>
                            <div class="col-sm-5">
                                    <span class="pay-bank ${command.result.bankCode}"></span>
                                    <%--<span id="showName"> <img  src="${root}/common/${command.bankList[0].bankIcon}">&nbsp;&nbsp;</span>--%>
                            </div>
                        </c:if>
                        <c:if test="${command.result.accountType=='2'}">
                            <label class="ft-bold col-sm-3 al-right line-hi34">存款渠道：</label>
                            <div class="col-sm-5" id="thirdError">
                                    ${dicts.common.bankname[command.result.bankCode]}
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group clearfix">
                        <label for="result.disableAmount" class="ft-bold col-sm-3 al-right line-hi34">停用金额CNY：</label>

                        <div class="col-sm-5">
                            <div class="input-group date"><form:input id="disableAmount" path="result.disableAmount"
                                                          cssClass="form-control"/>
                        <span tabindex="0" class=" help-popover input-group-addon" role="button"
                              data-container="body" data-toggle="popover" data-trigger="focus"
                              data-placement="top" data-original-title="" title="" data-html="true"
                              data-content="${views.content['payAccount.disableAmount']}"><i
                                class="fa fa-question-circle"></i></span></div></div>
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
                        <label class="ft-bold col-sm-3 al-right">累计入款金额CNY：</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <input id="depositTotal" name="result.depositTotal" class="form-control" value="<fmt:formatNumber value="${command.result.depositTotal}" pattern="0"/>">
                                <span class="input-group-addon bdn"><a class="m-l-sm revert" href="javascript:void(0)" sourceId="depositDefaultTotal" targetId="depositTotal">${views.column['PayAccount.revert']}</a></span>
                            </div>
                        </div>
                    </div>



                    <%--<div class="line-hi34 col-sm-12 bg-gray m-b">--%>
                        <%--<label class="ft-bold col-sm-3 al-right line-hi34"></label>--%>
                        <%--<span class="co-yellow m-r-sm m-l-sm"><i class="fa fa-exclamation-circle"></i></span>--%>
                            <%--${views.content['payAccount.add.monetaryTips']}--%>
                    <%--</div>--%>
                    <%--<div class="form-group clearfix line-hi34">--%>
                        <%--<label class="ft-bold col-sm-3 al-right">${views.content['payAccount.add.supportMoney']}：</label>--%>

                        <%--<div class="col-sm-5">--%>
                            <%--<div class="col-sm-5 currency">--%>
                                <%--<c:forEach items="${command.openAndSupportList}" var="p">--%>
                                    <%--<label class="m-r-sm"><input name="currency" type="checkbox" class="i-checks"--%>
                                                                 <%--value="${p.code}" <c:forEach items="${command.payAccountCurrencyList}" var="payAccount">${p.code==payAccount.currencyCode?"checked":""}</c:forEach>> ${messages.common[p.code]}</label>--%>
                                <%--</c:forEach>--%>
                                <%--<form:hidden path="currencyStr" id="currencyStr"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="form-group clearfix line-hi34">--%>
                        <%--<label class="ft-bold col-sm-3 al-right">${views.content['payAccount.add.useRank']}：</label>--%>

                        <%--<div class="col-sm-5 rank">--%>
                            <%--<div><label class="m-r-sm"><input type="checkbox" id="fullRank" ${command.result.fullRank?'checked':''}> 全部层级</label><span class="m-l co-grayc2">勾选后，后续新增的层级同样适用！</span></div>--%>
                            <%--<div class="input-group date allRank ${command.result.fullRank?'hide':''}">--%>
                            <%--<c:forEach items="${command.playerRankList}" var="p">--%>
                                <%--<label class="m-r-sm"><input name="rank" type="checkbox" class="i-checks"--%>
                                                             <%--value="${p.id}" <c:forEach items="${command.payRankList}" var="payRank">${p.id==payRank.playerRankId?"checked":""}</c:forEach>> ${p.rankName}</label>--%>
                            <%--</c:forEach>--%>
                                <%--<form:hidden path="rankStr" id="rankStr"/>--%>
                                <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <hr class="m-t-sm m-b">
                    <div class="operate-btn">
                        <soul:button cssClass="btn btn-filter btn-lg" text="${views.common['commit']}" opType="ajax"
                                     dataType="json"
                                     target="${root}/payAccount/updateCompany.html" precall="updatePayAccount"
                                     post="getCurrentFormData"
                                     callback="saveCallbak"/>



                        <soul:button target="goToLastPage" refresh="true" cssClass="btn btn-outline btn-filter btn-lg m-r" text="${views.common['cancel']}" opType="function">
                        </soul:button>







                    </div>
                </form>
            </div>
        </div>
    </div>
</form:form>
</body>
<!--//region your codes 4-->

<soul:import res="site/payaccount/company/Edit"/>
<!--//endregion your codes 4-->
</html>