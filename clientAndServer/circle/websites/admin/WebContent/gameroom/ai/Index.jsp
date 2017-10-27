<%--@elvariable id="command" type="g.model.gameroom.vo.PlayerAiControlListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->
<style>
    .modal-content{
        width: 900px;
    }
</style>
<!--//endregion your codes 1-->
<form:form action="${root}/playerAiControl/ailist.html?roomId=${roomId}" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>监控</span><span>/</span>调控<span></span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
                <%--<a href="/game/list.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>--%>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <soul:button target="${root}/playerAiControl/create.html?roomId=${roomId}" text="" title="AI调控" size="size-wide" opType="dialog" cssClass="btn btn-outline btn-filter" callback="query">
                            <i class="fa fa-plus"></i><span class="hd">&nbsp;&nbsp;新增</span>
                        </soul:button>
                        <a nav-target="mainFrame" href="/playerAiControl/ailist.html?roomId=${roomId}" class="btn btn-outline btn-filter"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>
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