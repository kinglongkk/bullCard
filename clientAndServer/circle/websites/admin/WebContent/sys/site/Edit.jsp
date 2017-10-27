<%--@elvariable id="command" type="type="g.model.sys.vo.syssitevo""--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<html lang="zh-CN">
<head>
    <title>编辑</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>

<form:form id="editForm" action="${root}/sys/site/edit.html" method="post">
    <form:hidden path="result.id" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>我的站点</span><span>/</span><span>站点信息</span>
            <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>

        <div class="col-lg-12">

            <div class="wrapper white-bg shadow">

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        站点名称
                    </label>
                    <div class="col-xs-8 p-x">
                        <form:input path="result.name" cssClass="form-control m-b" value="${command.result.name}" />
                        <form:input type="hidden" path="result.id" value="${command.result.id}"/>
                        <form:input type="hidden" path="result.siteClassifyKey" value="${command.result.siteClassifyKey}" />
                        <form:input type="hidden" path="result.logoPath" value="${command.result.logoPath}" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        站点状态
                    </label>
                    <div class="col-xs-8 p-x">
                        <c:choose>
                            <c:when test="${command.result.status eq '1'}">正常</c:when>
                            <c:when test="${command.result.status eq '2'}">停用</c:when>
                            <c:when test="${command.result.status eq '3'}">维护中</c:when>
                            <c:otherwise>未建库</c:otherwise>
                        </c:choose>
                        <input type="hidden" name="result.status" value="${command.result.status}" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        站点简称
                    </label>
                    <div class="col-xs-8 p-x">
                        <form:input path="result.shortName" cssClass="form-control m-b" value="${command.result.shortName}" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        站点网站
                    </label>
                    <div class="col-xs-8 p-x">
                        <form:input path="result.webSite" cssClass="form-control m-b" value="${command.result.webSite}" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        维护开始时间
                    </label>
                    <div class="col-xs-8 p-x">
                        <gb:dateRange value="${command.result.maintainStartTime}" format="${DateFormat.DAY_HOUR}" name="result.maintainStartTime" callback="clickShow" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        维护结束时间
                    </label>
                    <div class="col-xs-8 p-x">
                        <gb:dateRange value="${command.result.maintainEndTime}" format="${DateFormat.DAY_HOUR}" name="result.maintainEndTime" callback="clickShow" />
                    </div>
                </div>

                <div class="form-group clearfix line-hi34 m-b-xxs">
                    <label class="col-xs-3 al-right">
                        维护原因
                    </label>
                    <div class="col-xs-8 p-x">
                        <form:input id="maintainReason" path="result.maintainReason" cssClass="form-control m-b" value="${command.result.maintainReason}" oninput="window.top.page.clickShow(value)" />
                    </div>
                </div>

            </div>

        </div>

        <div class="modal-footer">
            <soul:button cssClass="btn btn-filter" text="${views.common['OK']}" opType="ajax" dataType="json" target="${root}/sys/site/persist.html" precall="validateForm" post="getCurrentFormData" callback="goToLastPage" refresh="true" />
            <soul:button target="goToLastPage"  text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter btn-lg" opType="function" refresh="true">${views.common['cancel']}</soul:button>
        </div>

    </div>

</form:form>

</body>
<!--//region your codes 4-->
<soul:import res="site/sys/site/Edit"/>
<!--//endregion your codes 4-->
</html>