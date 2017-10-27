<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/playerDataStatistics/allGameList.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>报表</span>
            <span>/</span>
            <span>游戏报表</span>
            <a href="javascript:void(0)" class="pull-right siteMap">
                <i class="fa fa-sitemap"></i>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/playerDataStatistics/allGameList.html" class="btn btn-outline btn-filter">
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
                        <gb:dateRange useRange="true" format="${DateFormat.DAY}" startName="search.startTime" endName="search.endTime" style="width:250px;" required="required" title="此为必填项" />
                    </div>

                    <div class="pull-left" style="padding-left:12px;">
                        <select name="search.gameId" id="game" style="width: 100px; height: 34px;">
                            <option value="" selected="selected">请选择游戏</option>
                            <c:forEach items="${gameList.result}" var="game">
                                <option value="${game.id}">${game.name}</option>
                            </c:forEach>
                        </select>

                        <select name="search.gameModelId" id="gameModel" style="width: 180px; height: 34px;">

                        </select>
                    </div>

                    <div class="pull-left" style="padding-left: 12px;">
                        <span class="pull-left" style="padding: 7px 12px;">
                            <b>玩家：</b>
                        </span>
                        <input type="text" name="search.username" style="width: 200px;height: 34px; text-indent: 5px">
                    </div>
                    <span class="input-group-btn pull-left">
                        <soul:button cssClass="btn btn-filter" tag="button" opType="function" text="搜索" target="query">
                            <i class="fa fa-search"></i>
                            <span class="hd">&nbsp;搜索</span>
                        </soul:button>
                    </span>
                </div>

                <div class="search-list-container">
                    <%@ include file="GroupIndexPartial.jsp" %>
                </div>

            </div>
        </div>
    </div>
</form:form>
<!--//region your codes 3-->
<soul:import res="site/playerstatistics/groupByGame/allGame/List"/>
<!--//endregion your codes 3-->
