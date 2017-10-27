<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->
<style>
    .searchInput{
        height: 34px;
        /*border-radius: 3px;*/
    }
    .searchButton{
        margin-left: 6px;
        margin-top: -3px;
        border-radius: 0px;
    }
</style>
<!--//endregion your codes 1-->
<form:form action="${root}/player/playerUserList.html" method="post">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>账号管理</span><span>/</span>
        <span>玩家查询</span>
        <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
    </div>
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="panel panel-default">
        <div class="panel-body">
            <!--//region your codes 2-->
            <div class="row">
                <div style="padding-top: 0px;margin-left: 15px;">
                    玩家用户名:<input type="text" name="search.username" class="searchInput">
                    玩家昵称:<input type="text" name="search.nickname" class="searchInput">
                    <soul:button target="query" opType="function" text="查询" cssClass="btn btn-filter searchButton" />
                </div>
            </div>

            <br/>
            <div class="search-list-container">
                <%@ include file="indexPlayerPartial.jsp" %>
            </div>
            <!--//endregion your codes 2-->
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->