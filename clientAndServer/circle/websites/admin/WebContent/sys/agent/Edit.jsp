<%--@elvariable id="command" type="g.model.agent.vo.VAgentManageVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="row" id="agent_main">
    <form:form>
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['角色']}</span>
        <span>/</span><span>${views.sysResource['代理管理']}</span>
        <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
            <em class="fa fa-caret-left"></em>${views.common['return']}
        </soul:button>
    </div>
    <div id="validateRule" style="display: none">${command.validateRule}</div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="present_wrap"><b>${empty command.sysUser.id ? views.role['agent.createTitle']:views.role['agent.editTitle']}</b></div>
                <%--代理基本信息 start--%>
                <div class="clearfix m-t-sm">
                    <div class="dataTables_wrapper" role="grid">
                        <form:hidden path="sysUser.id"></form:hidden>
                        <form:hidden path="result.id"></form:hidden>
                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="sysUser.username">
                                <span class="co-red3 m-r-xs">*</span>
                                    ${views.subAccount['edit.username']}
                            </label>

                            <c:if test="${not empty command.sysUser.id}">
                                <div class="col-xs-8 p-x">${command.sysUser.username}</div>
                                <input type="hidden" name="sysUser.username" value="${command.sysUser.username}">
                            </c:if>

                            <c:if test="${empty command.sysUser.id}">
                            <div class="col-xs-8 p-x">
                                <form:input path="sysUser.username" cssClass="form-control m-b"></form:input>
                            </div>
                            </c:if>
                        </div>

                        <c:if test="${not empty command.sysUser.id && not empty command.sysUser.registerSite}">
                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" >
                                推广地址:
                            </label>
                            <div class="col-xs-8 p-x">${sessionScope.S_SYS_SITE_PO.webSite}/passport/register.html?from=${command.sysUser.registerSite}</div>
                        </div>
                        </c:if>

                        <c:if test="${empty command.sysUser.id}">
                            <div class="form-group clearfix line-hi34 m-b-xxs">
                                <label class="col-xs-3 al-right" for="sysUser.password">
                                    <span class="co-red3 m-r-xs">*</span>
                                        ${views.subAccount['edit.loginPassword']}
                                </label>
                                <div class="col-xs-8 p-x">
                                    <form:password path="sysUser.password" cssClass="form-control m-b"></form:password>
                                </div>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs">
                                <label class="col-xs-3 al-right" for="confirmPassword">
                                    <span class="co-red3 m-r-xs">*</span>
                                        ${views.subAccount['edit.confirmPassword']}
                                </label>
                                <div class="col-xs-8 p-x">
                                    <form:password path="confirmPassword" cssClass="form-control m-b"></form:password>
                                </div>
                            </div>
                        </c:if>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="sysUser.realName">
                                <span class="co-red3 m-r-xs">*</span>
                                    ${views.column['VAgentManage.realName']}：
                            </label>
                            <div class="col-xs-8 p-x">
                                <form:input path="sysUser.realName" cssClass="form-control m-b"></form:input>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="sysUser.realName">
                                <span class="co-red3 m-r-xs">*</span>
                                    ${views.column['VAgentManage.nickname']}：
                            </label>
                            <div class="col-xs-8 p-x">
                                <form:input path="sysUser.nickname" cssClass="form-control m-b"></form:input>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="sysUser.status">
                                <span class="co-red3 m-r-xs">*</span>
                                状态：
                            </label>
                            <div class="col-xs-8 p-x">
                                <gb:select name="sysUser.status" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.status}" list="${command.status}"></gb:select>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34">
                            <label class="col-xs-3 al-right" for="sysUser.userType">
                                <span class="co-red3 m-r-xs">*</span>
                                用户类型：
                            </label>
                            <div class="col-xs-8 p-x">
                                <gb:select name="sysUser.userType" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.userType}" list="${command.userType}"></gb:select>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="mobilePhone.contactValue">
                                    ${views.subAccount['edit.phone']}
                            </label>
                            <div class="col-xs-8 p-x">
                                <input type="text" class="form-control m-b" name="mobilePhone.contactValue" id="mobilePhone.contactValue" value="${command.mobilePhone.contactValue}">
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="email.contactValue">
                                    ${views.subAccount['edit.email']}
                            </label>
                            <div class="col-xs-8 p-x">
                                <input type="text" class="form-control m-b" name="email.contactValue" id="email.contactValue" value="${command.email.contactValue}">
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right" for="skype.contactValue">
                                    ${views.subAccount['edit.skype']}
                            </label>
                            <div class="col-xs-8 p-x">
                                <input type="text" class="form-control m-b" name="skype.contactValue" id="skype.contactValue" value="${command.skype.contactValue}">
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34">
                            <label class="col-xs-3 al-right">国家：</label>
                            <div class="col-xs-8 p-x">
                                <gb:select name="sysUser.country" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.country}" list="${command.country}"></gb:select>
                            </div>
                        </div>

                        <%--<div class="form-group clearfix line-hi34">--%>
                            <%--<label class="col-xs-3 al-right">民族：</label>--%>
                            <%--<div class="col-xs-8 p-x">--%>
                                <%--<gb:select name="sysUser.nation" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.nation}" list="${command.nation}"></gb:select>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="form-group clearfix line-hi34">
                            <label class="col-xs-3 al-right">${views.subAccount['edit.sex']}</label>
                            <div class="col-xs-8 p-x">
                                <gb:select name="sysUser.sex" cssClass="btn-group chosen-select-no-single" prompt="${views.common['pleaseSelect']}" value="${command.sysUser.sex}" list="${command.sex}"></gb:select>
                            </div>
                        </div>

                    </div>
                </div>
                <%--代理基本信息 end--%>

                <%--按钮--%>
                <div class="operate-btn">
                    <c:if test="${empty command.sysUser.id}">
                        <soul:button target="${root}/vAgentManage/persist.html" text="${views.common['OK']}" cssClass="btn btn-filter btn-lg" precall="myValidateForm" opType="ajax" post="getCurrentFormData" callback="goToLastPage" refresh="true">${views.common['OK']}</soul:button>
                    </c:if>
                    <c:if test="${!(empty command.sysUser.id)}">
                        <soul:button target="${root}/vAgentManage/update.html" text="${views.common['OK']}" cssClass="btn btn-filter btn-lg" precall="myValidateForm" opType="ajax" post="getCurrentFormData" callback="goToLastPage" refresh="true">${views.common['OK']}</soul:button>
                    </c:if>
                    <soul:button target="goToLastPage"  text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter btn-lg" opType="function">${views.common['cancel']}</soul:button>
                </div>
            </div>
        </div>
    </form:form>
</div>

<soul:import res="site/agent/Edit"/>
