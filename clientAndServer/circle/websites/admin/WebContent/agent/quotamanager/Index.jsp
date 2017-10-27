<%--@elvariable id="command" type="g.model.admin.agent.quotamanager.vo.VAgentQuotaListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/vAgentQuota/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>代理管理</span><span>/</span><span>额度管理</span>
            <a href="/vAgentQuota/list.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/vAgentQuota/list.html" class="btn btn-outline btn-filter">
                            <i class="fa fa-refresh"></i>
                            <span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span>
                        </a>
                    </div>
                    <div class="search-wrapper btn-group pull-right">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <div selectdiv="" value="" class="btn-group chosen-select-no-single" initprompt="请选择" callback="selectListChange">
                                    <input type="hidden" name="" value="" id="searchlist">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" style="overflow: hidden;padding-right: 10px;" aria-expanded="false">
                                        <span prompt="prompt" style="display:inline-block;min-width: 60px;">请选择</span>
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="">请选择</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.username">账号</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.realName">姓名</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.merchantNo">商务号</a></li>
                                    </ul>
                                </div>
                            </div>
                            <input type="text" class="form-control" id="searchtext">
                            <span class="input-group-btn">
                             <button title="搜索" class="btn btn-filter" type="button" data-rel="{&quot;precall&quot;:&quot;checksearch&quot;,&quot;callback&quot;:&quot;&quot;,post:&quot;&quot;,opType:&quot;function&quot;,dataType:&quot;&quot;,target:&quot;query&quot;,confirm:&quot;&quot;,text:&quot;搜索&quot;,size:&quot;&quot; }">
                                <i class="fa fa-search"></i><span class="hd">&nbsp;搜索</span>
                             </button>
                             </span>
                        </div>
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