<%--@elvariable id="command" type="so.wwb.circle.model.master.content.vo.VPayAccountListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/vPayAccount/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
        <form:hidden path="search.type"/>
            <!--//region your codes 2-->
            <div class="row">
                <div class="position-wrap clearfix">
                    <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
                    <span>收款账户</span><span>/</span>
                    <span>
                        <c:if test="${command.search.type eq '1'}">
                            公司入款账户
                        </c:if>
                        <c:if test="${command.search.type eq '2'}">
                            线上支付账户
                        </c:if>
                    </span>
                    <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
                </div>
                <div class="col-lg-12">
                    <div class="wrapper white-bg shadow">
                        <a href="/vPlayerRankStatistics/list.html" id="rankList" nav-target="mainFrame" class="fil" hidden/>
                        <!--筛选条件-->
                        <div class="clearfix filter-wraper border-b-1">
                            <a href="${command.search.type=="1"?"/payAccount/companyCreate.html":"/payAccount/onLineCreate.html"}" class="btn btn-outline btn-filter pull-left m-r-sm" nav-second="mainFrame"><i class="fa fa-plus"></i><span class="hd">&nbsp;&nbsp;新增</span></a>
                            <soul:button tag="button" target="query" opType="function" cssClass="btn btn-outline btn-filter" text="${views.common['refresh']}"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;刷新</span></soul:button>
                            <div class="search-wrapper btn-group pull-right m-r-n-xs">
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <select id="searchlist" data-placeholder="${views.column['VPayAccount.accountName']}" class="btn-group chosen-select-no-single" tabindex="-1">
                                            <c:forEach items="${command.searchList()}" var="item" varStatus="status">
                                                <option value="${item.key}" ${status.index==0?'selected':''}>${item.value}</option>
                                                <c:if test="${status.index==0}">
                                                    <c:set value="${item.key}" var="firstSelectKey"/>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <input type="text" class="form-control" id="searchtext" name="${firstSelectKey}">
                                    <span class="input-group-btn">
                                        <soul:button cssClass="btn btn-filter" precall="checksearch" tag="button" opType="function" text="${views.common['search']}" target="query">
                                            <i class="fa fa-search"></i><span class="hd">&nbsp;${views.common['search']}</span>
                                        </soul:button>
                                        <%--<button type="button" class="btn btn-filter"><i class="fa fa-search"></i><span class="hd">&nbsp;${views.column['search']}</span></button>--%>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <!--表格内容-->
                        <dl class="clearfix filter-conditions p-xxs p-b-xs m-b-none border-b-1 hide">
                            <dt>${views.common['filterCondition']}（<a href="javascript:void(0)">${views.common['clear']}</a>）</dt>
                        </dl>
                        <div id="editable_wrapper" class="dataTables_wrapper search-list-container" role="grid">
                            <%@ include file="IndexPartial.jsp" %>
                        </div>
                    </div>
                </div>
            <!--//endregion your codes 2-->
        </div>
</form:form>
<soul:import type="edit"/>
<!--//region your codes 3-->

<!--//endregion your codes 3-->