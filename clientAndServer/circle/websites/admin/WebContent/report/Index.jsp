<%--@elvariable id="command" type="g.model.report.vo.ReportCqsscListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<div class="row">
    <form:form action="${root}/reportCqssc/masterReport.html" method="post">
        <div id="validateRule" style="display: none">${command.validateRule}</div>
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont"></i>
                </a>
            </h2>
            <span>${views.sysResource['报表']}</span>
            <span>/</span>
            <span>站长报表</span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="panel panel-default">
            <div class="panel-body">
                <!--//region your codes 2-->
                <div class="input-group">
                    <div class="input-group pull-left">
                        <span class="input-group-addon abroder-no">
                            <b>开始时间：</b>
                        </span>
                        <gb:dateRange value="${search.startTime}" useRange="true" format="${DateFormat.DAY}" callback="query" startName="search.startTime" endName="search.endTime" style="" />
                    </div>
                    <div class="input-group content-width-limit-250 pull-left">
                        <span class="input-group-addon abroder-no">
                            <b>期号：</b>
                        </span>
                        <input type="text" class="form-control" name="search.matchCode">
                    </div>

                    <div class="input-group pull-left">
                        <span class="input-group-addon abroder-no">
                            <b>代理：</b>
                        </span>
                        <input type="text" value="${search.username}" class="form-control" name="search.username">
                    </div>
                        <span class="input-group-btn pull-left">
                            <soul:button cssClass="btn btn-filter" tag="button" opType="function" text="搜索" target="query">
                                <i class="fa fa-search"></i>
                                <span class="hd">&nbsp;搜索</span>
                            </soul:button>
                        </span>
                    </div>
                <br/>
                <div class="search-list-container">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
                <!--//endregion your codes 2-->
            </div>
        </div>
    </form:form>
</div>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->