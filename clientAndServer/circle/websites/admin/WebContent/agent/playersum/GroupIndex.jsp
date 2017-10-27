<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/playerSummery/allAgentList.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>角色</span>
            <span>/</span>
            <span>玩家统计</span>
            <a href="javascript:void(0)" class="pull-right siteMap">
                <i class="fa fa-sitemap"></i>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/playerSummery/allAgentList.html" class="btn btn-outline btn-filter">
                            <i class="fa fa-refresh"></i>
                            <span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span>
                        </a>
                    </div>

                    <div class="pull-right">
                        <a id="export" class="btn btn-outline btn-filter">
                            <span class="hd">导出</span>
                        </a>
                    </div>
                </div>

                <div class="input-group filter-wraper">
                    <div class="pull-left" id="searchDiv" >
                        <span class="pull-left" style="padding: 7px 12px;">
                            <b>日期：</b>
                        </span>
                        <gb:dateRange useRange="true" format="${DateFormat.DAY}" callback="query" startName="search.startTime" endName="search.endTime" style="width:250px;" />
                    </div>
                </div>

                <div class="search-list-container">
                    <%@ include file="GroupIndexPartial.jsp" %>
                </div>

            </div>
        </div>
    </div>
</form:form>
<!--//region your codes 3-->
<soul:import res="site/agent/playersum/GroupIndex"/>
<!--//endregion your codes 3-->
