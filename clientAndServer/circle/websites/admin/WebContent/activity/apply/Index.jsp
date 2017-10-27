<%--@elvariable id="command" type="g.model.activityapply.vo.activityapplyListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/activityPlayerApply/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>游戏活动</span><span>/</span><span>参与统计</span>
            <a href="/activityPlayerApply/groupByList.html" class="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" name="refresh" nav-target="mainFrame"><em class="fa fa-caret-left"></em>${views.common['return']}</a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
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
