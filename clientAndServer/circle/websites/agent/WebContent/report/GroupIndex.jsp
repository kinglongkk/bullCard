<%--@elvariable id="command" type="g.model.report.vo.ReportCqsscListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<div class="row">
    <form:form action="${root}/report/agentReportByGroup.html" method="post">
        <div id="validateRule" style="display: none">${command.validateRule}</div>
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>报表</span>
            <span>/</span>
            <span>我的报表</span>
            <a href="javascript:void(0)" class="pull-right siteMap">
                <i class="fa fa-sitemap"></i>
            </a>
        </div>
        <div class="panel panel-default">
            <div class="panel-body">
                <!--//region your codes 2-->
                <div class="input-group">
                    <div class="input-group pull-left">
                        <span class="input-group-addon abroder-no">
                            <b>开始时间：</b>
                        </span>
                        <gb:dateRange useRange="true" format="${DateFormat.DAY_SECOND}" callback="query" startName="search.startTime" endName="search.endTime" style="" />
                    </div>
                </div>

                <br/>
                <div class="search-list-container">
                    <%@ include file="GroupIndexPartial.jsp" %>
                </div>
                <!--//endregion your codes 2-->
            </div>
        </div>
    </form:form>
</div>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->