<%--@elvariable id="command" type="g.model.activityapply.vo.activityapplyListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/activityPlayerApply/groupByList.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>游戏活动</span><span>/</span><span>参与统计</span>
            <a href="/activityPlayerApply/groupByList.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/activityPlayerApply/groupByList.html" class="btn btn-outline btn-filter"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>
                    </div>

                    <div class="col-md-1 pull-right">
                        <form:select class="btn-group chosen-select-no-single" path="search.activityClassifyKey" callback="query">
                            <form:option value="">活动</form:option>
                            <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                                <form:option value="${activityTypeName['code']}">${activityTypeName['trans']}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="input-group content-width-limit-550 pull-right">
                        <span class="input-group-addon abroder-no"><b>时间：</b></span>
                        <gb:dateRange useRange="true" format="${DateFormat.DAY}" callback="query" startName="search.starttime" endName="search.endtime" opens="left" />
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
<soul:import type="list"/>
<!--//endregion your codes 3-->