<%--@elvariable id="command" type="g.model.agent.vo.VSubAccountListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form method="post" action="${root}/vUserPlayerGroup/Index.html">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['会员']}</span><span>/</span><span>管理分组</span>
            <a style="display: none" name="refresh" href="/vUserPlayerGroup/Index.html" nav-second="mainFrame"/>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <soul:button target="${root}/vUserPlayerGroup/create.html" text="新增"  opType="dialog" cssClass="btn btn-outline btn-filter" callback="query"/>
                </div>
                <div class="dataTables_wrapper search-list-container">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </div>
</form:form>
<soul:import res="site/player/Index"/>