<%--@elvariable id="command" type="g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/gameRoomJackpot/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>游戏</span>
            <span>/</span>
            <span>奖池设置</span>
            <a href="javascript:void(0)" class="pull-right siteMap">
                <i class="fa fa-sitemap"></i>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/gameRoomJackpot/list.html" class="btn btn-outline btn-filter">
                            <i class="fa fa-refresh"></i>
                            <span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span>
                        </a>
                    </div>
                </div>

                <div class="search-list-container">
                    <%@ include file="IndexPartial.jsp" %>
                </div>

            </div>
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->