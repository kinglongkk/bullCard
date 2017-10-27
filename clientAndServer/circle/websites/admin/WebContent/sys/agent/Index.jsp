<%@ page import="java.util.Date" %>
<%--@elvariable id="command" type="g.model.agent.vo.VAgentManageListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="row">
    <form:form action="${root}/vAgentManage/list.html" method="post">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['角色']}</span><span>/</span><span>${views.sysResource['代理管理']}</span>
            <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <a href="/vAgentManage/create.html" nav-target="mainFrame" class="btn btn-outline btn-filter">
                        <i class="fa fa-plus" ></i><span class="hd">&nbsp;&nbsp;新增代理</span>
                    </a>

                    <a nav-target="mainFrame" href="/vAgentManage/list.html" class="btn btn-outline btn-filter" ><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>

                    <div class="search-wrapper btn-group pull-right">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <select tabindex="-1" class="btn-group chosen-select-no-single" id="searchlist" data-placeholder="${views.common['pleaseSelect']}" style="display: none;">
                                    <option value="">${views.common['pleaseSelect']}</option>
                                    <c:forEach items="${command.searchList()}" var="item">
                                        <option value="${item.key}">${item.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <input type="text" class="form-control" id="searchtext">
                            <span class="input-group-btn">
                                <soul:button cssClass="btn btn-filter" precall="checksearch" tag="button" opType="function" text="${views.common['search']}" target="query">
                                    <i class="fa fa-search"></i><span class="hd">&nbsp;${views.common['search']}</span>
                                </soul:button>
                            </span>
                        </div>
                    </div>
                </div>
                <div id="editable_wrapper" class="dataTables_wrapper" role="grid">

                    <div class="search-list-container">
                        <%@ include file="IndexPartial.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </form:form>
</div>
<soul:import type="list"/>
