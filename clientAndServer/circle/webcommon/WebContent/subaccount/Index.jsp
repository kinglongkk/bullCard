<%--@elvariable id="command" type="g.model.agent.vo.VSubAccountListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/subAccount/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource[command.resourceKey]}</span><span>/</span><span>${views.sysResource['子账号']}</span>
            <a href="/subAccount/list.html" style="display: none" name="refresh" nav-target="mainFrame"/>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <soul:button permission="subaccount:add" target="${root}/subAccount/create.html" tag="button" cssClass="btn btn-outline btn-filter" text="${views.subAccount['list.addAcount']}" opType="dialog" callback="query">
                        <i class="fa fa-plus"></i><span class="hd">&nbsp;&nbsp;新增</span>
                    </soul:button>
                    <shiro:hasPermission name="subaccount:role">
                        <a nav-second="mainFrame" id="_roleSetting"  href="/subAccount/role.html" class="btn btn-outline btn-filter"><i class="fa fa-gear"></i><span class="hd">&nbsp;&nbsp;${views.subAccount['list.roleSetting']}</span></a>
                    </shiro:hasPermission>
                    <div class="function-menu-show hide">

                        <soul:button target="deleteSubAccount" tag="button" cssClass="btn btn-outline btn-filter" text="" opType="function" callback="query">
                            <i class="fa fa-trash-o"></i><span class="hd">&nbsp;&nbsp;${views.common['delete']}</span>
                        </soul:button>

                        <div class="btn-group" id="sub_account_roles">
                            <button data-toggle="dropdown" class="btn btn-filter btn-outline dropdown-toggle">
                                <i class="fa fa-list"></i>&nbsp;&nbsp;${views.subAccount['list.role']}&nbsp;
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <div class="label-menu-o" id="sys_user_role">

                                </div>
                                <li class="divider"></li>
                                <li class="m-b-sm bt m-t-xs">
                                    <soul:button target="${root}/subAccount/resetRole.html" post="getData" tag="button" text="" callback="query" opType="ajax" cssClass="btn btn-filter btn-sm m-r-sm">确认</soul:button>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <%--</div>--%>

                    <div class="search-wrapper btn-group pull-right m-r-n-xs">
                        <div class="input-group">
                            <span class="input-group-addon">
                                    ${views.subAccount['list.username']}
                            </span>
                            <input type="text" class="form-control" name="search.username" value="">
                            <span class="input-group-btn">
                                <soul:button target="query" tag="button" cssClass="btn btn-filter" text="" opType="function">
                                    <i class="fa fa-search"></i><span class="hd">&nbsp;${views.common['search']}</span>
                                </soul:button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="dataTables_wrapper search-list-container">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </div>
</form:form>
<soul:import res="subaccount/Index"/>