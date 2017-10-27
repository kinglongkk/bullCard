<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.PayAccountVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<body>
<form:form id="editForm" action="${root}/payAccount/edit.html" method="post">
<form:hidden path="result.id"/>
<div id="validateRule" style="display: none">${command.validateRule}</div>
<form:hidden path="result.bankCode" id="bankCode"/>
<form:hidden path="result.account" id="account"/>
    <input type="hidden" value="1" name="result.type"/>
<input name="channelJson" hidden="hidden" value="1">
    <input name="token" hidden="hidden" value="${token}">
<input id="code" name="result.payChannelCode" hidden="hidden">
<input name="result.fullRank" value="${empty command.result.fullRank?false:command.result.fullRank}" hidden="hidden">

    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a href="javascript:void(0)" class="navbar-minimalize"><i class="icon iconfont"></i> </a></h2>
            <span>公司入款账户</span>
            <span>/</span><span>新增公司入款</span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow clearfix">
                <div class="present_wrap"><b>新增收款账户</b></div>
                <form class="m-t" action="">
                    <div class="form-group line-hi34 clearfix">
                        <label class="ft-bold col-sm-3 al-right">代号：</label>

                        <div class="col-sm-5 payCode" data-value="${command.result.code}">${command.result.code}</div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label for="result.payName" class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>账户名称：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input disabled="${disabled}" id="payName" path="result.payName"
                                            cssClass="form-control"/>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label for="result.accountType" class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>账户类型：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:select path="result.accountType" callback="accountTypeChange" id="accountType" cssClass="btn-group chosen-select-no-single">
                                        <form:option value="1">银行账户</form:option>
                                        <form:option value="2">第三方账户</form:option>
                                </form:select>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>账号：</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <input class="form-control account" name="account1"  />
                                <input class="form-control account" name="account2"  />
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span>
                            </div>
                        </div>
                    </div>
                    <!-- 二维码图片 -->
                    <c:set var="showPic" value="${command.result.id == null || (command.result.type!='1' && command.result.accountType!='2')}" />
                    <div class="form-group clearfix line-hi34 qr-code" style="<c:if test="${showPic}">display: none"</c:if>>
                        <label class="ft-bold col-sm-3 al-right line-hi34">二维码图片：</label>
                        <div class="col-sm-5" style="width: 41.2%">
                            <input id="image_file_path" class="file" type="file" accept="image/jpg" name="image_file_path" target="result.qrCodeUrl">
                            <div style="line-height: 20px; color: #A7A6A6;">请上传1M以内，JPG格式的图片</div>
                            <input type="hidden" name="result.qrCodeUrl" id="path" value="${command.result.qrCodeUrl}">
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>姓名：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input disabled="${disabled}" id="fullName" path="result.fullName"
                                            cssClass="form-control"/>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span></div>
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34 bank" >
                        <label class="ft-bold col-sm-3 al-right"><span class="co-red m-r-sm">*</span>银行：</label>
                        <div class="col-sm-5" style="width: 41.2%">
                            <div selectdiv="paging.pageSize"   class="btn-group" id="bankError" initprompt="10条" callback="thirdChange">
                                <input type="hidden" id="selectdivBank" name="paging.pageSize" value="">
                                <button type="button" class="btn btn btn-default" data-toggle="dropdown" aria-expanded="false">
                                    <span prompt="prompt">请选择银行</span>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" style="max-height: 300px" role="menu" aria-labelledby="dropdownMenu1" style="max-height: 1298px; min-width: 55px; overflow-y: scroll; overflow-x: visible;">
                                    <li role="presentation">
                                        <a role="menuitem" tabindex="-1" href="javascript:void(0)" key="" ><span class="pay-bank"></span>请选择</a>
                                    </li>
                                    <c:forEach items="${command.bankList}" var="p" varStatus="status">
                                    <c:if test="${p.type=='1'}">
                                        <li role="presentation">
                                            <a role="menuitem" tabindex="-1" href="javascript:void(0)" key="${p.bankName}" ><span class="pay-bank ${p.bankName} " showName="${dicts.common.bankname[p.bankName]}"></span>${dicts.common.bankname[p.bankName]}</a>
                                        </li>
                                    </c:if>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="form-group clearfix line-hi34">
                        <label class="ft-bold col-sm-3 al-right line-hi34">开户行：</label>

                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input id="openAcountName" path="result.openAcountName" cssClass="form-control"/>
                                <span class="input-group-addon bdn">&nbsp;&nbsp;</span></div>
                        </div>
                    </div>
                    <div class="form-group clearfix third" style="display: none">
                        <label class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>存款渠道：</label>
                        <div class="input-group col-sm-5" id="thirdError" style="width: 41.2%">
                            <div class="input-group-btn">
                                <div selectdiv="paging.pageSize" class="btn-group" initprompt="10条" id="third" callback="thirdChange">                    <input type="hidden" name="paging.pageSize" value="">
                                    <button type="button" class="btn btn btn-default" data-toggle="dropdown" aria-expanded="false">
                                        <span prompt="prompt">请选择</span>
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="max-height: 1298px; min-width: 55px; overflow-y: scroll; overflow-x: visible;">
                                        <c:forEach items="${command.bankList}" var="p" varStatus="status">
                                            <c:if test="${p.type=='2'}">
                                                <li role="presentation">
                                                    <a role="menuitem" tabindex="-1" href="javascript:void(0)" key="${p.bankName}" ><span class="pay-third ${p.bankName} " showName="${dicts.common.bankname[p.bankName]}"></span>${dicts.common.bankname[p.bankName]}</a>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <input name="result.customBankName" class="form-control">
                        </div>
                    </div>
                    <div class="form-group clearfix">
                        <label for="result.disableAmount" class="ft-bold col-sm-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>停用金额CNY：</label>

                        <div class="col-sm-5" style="width: 41.2%">
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
                        <label class="ft-bold col-sm-3 al-right">累计入款次数</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <form:input path="result.depositCount"
                                            cssClass="form-control"/><span class="input-group-addon bdn">&nbsp;&nbsp;<span class="co-red">*</span></span>
                                <span class="input-group-addon bdn"><a class="m-l-sm" href="javascript:void(0)">恢复</a></span>
                            </div>
                        </div>
                    </div>


                    <div class="form-group clearfix line-hi34" style="display:${command.result.id>0?"":"none"}">
                        <label class="ft-bold col-sm-3 al-right">累计入款金额</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <form:input path="result.depositTotal"
                                            cssClass="form-control"/><span class="input-group-addon bdn">&nbsp;&nbsp;<span class="co-red">*</span></span>
                                <span class="input-group-addon bdn"><a class="m-l-sm" href="javascript:void(0)">恢复</a></span>
                            </div>
                        </div>
                    </div>
                    <hr class="m-t-sm m-b">
                    <div class="operate-btn">
                        <soul:button cssClass="btn btn-filter btn-lg disabled _search " text="${views.common['commit']}" opType="ajax"
                                    dataType="json"
                                    target="${root}/payAccount/saveCompany.html" precall="savePlayer"
                                    post="getCurrentFormData"
                                    callback="saveCallbak" />
                        <soul:button target="goToLastPage" refresh="true" cssClass="btn btn-outline btn-filter btn-lg m-r" text="${views.common['cancel']}" opType="function" />

                    </div>
                </form>
            </div>
        </div>
    </div>
    </form:form>
</body>
<!--//region your codes 4-->

<soul:import res="site/payaccount/company/Add"/>
<!--//endregion your codes 4-->
</html>