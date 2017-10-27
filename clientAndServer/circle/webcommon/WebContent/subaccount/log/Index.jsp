<%--@elvariable id="command" type="org.soul.model.sys.vo.SysAuditLogListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/subAccount/logList.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>

    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['子账号']}</span>
            <span>/</span><span>${views.sysResource['日志查询']}</span>
            <c:if test="${hasReturn}">
                <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                    <em class="fa fa-caret-left"></em>${views.common['return']}
                </soul:button>
            </c:if>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow clearfix">
                <div class="clearfix m-t-xs m-b-sm">
                    <div class="col-lg-10 clearfix">
                        <div class="form-group clearfix pull-left content-width-limit-500 m-t-sm m-b-none">
                            <div class="input-group">
                                <span class="input-group-addon abroder-no"><b>${views.playerAdmin['log.query.time']}</b></span>
                                <gb:dateRange style="width:360px;" useRange="true" useTime="true" opens="right" position="down" format="${DateFormat.DAY_SECOND}" startName="search.operatorBegin" maxDate="${now}" endName="search.operatorEnd" />
                            </div>
                        </div>
                        <div class="form-group clearfix pull-left content-width-limit-250 m-t-sm m-b-none">
                            <div class="input-group">
                                <span class="input-group-addon abroder-no"><b>${views.playerAdmin['log.query.type']}</b></span>
                                <gb:select name="search.moduleType" value="${command.search.moduleType}" cssClass="chosen-select-no-single" listKey="code"
                                               listValue="trans" list="${moduleTypes}" prompt="${views.common['pleaseSelect']}"/>
                            </div>
                        </div>
                        <div class="form-group clearfix pull-left content-width-limit-30 m-t-sm m-b-none">
                            <div class="input-group sel_parent">
                                <span class="input-group-addon abroder-no"><b>${views.playerAdmin['log.query.key']}</b></span>
                                <span class="input-group-btn">
                                    <gb:select name="keys" cssClass="chosen-select-no-single" callback="changeKey" list="${keys}" value="search.operator" listKey="key" listValue="value"/>
                                </span>
                                <input type="text" class="form-control sel_input" name="search.operator" value="${empty command.search.operator?command.search.operateIp:command.search.operator}" id="operator">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-2">
                        <div class="form-group clearfix m-t-sm m-b-none">
                            <soul:button target="query" opType="function" text="${views.common['query']}" cssClass="btn btn-filter pull-right" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-12 m-t">
            <div class="wrapper white-bg shadow">
                <div class="dataTables_wrapper search-list-container" role="grid">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import res="site/log/index" />
<!--//endregion your codes 3-->