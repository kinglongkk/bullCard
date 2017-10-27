<%--@elvariable id="command" type="g.model.admin.agent.currency.vo.AgentCurrencyExchangeListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<form:form action="${root}/agentCurrencyExchange/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">

        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>系统</span><span>/</span><span>货币汇率</span>
        </div>

        <div class="col-lg-12">

            <div class="wrapper white-bg shadow">

                <div class="clearfix filter-wraper border-b-1">

                    <div class="pull-left">
                        <soul:button target="${root}/agentCurrencyExchange/create.html" text="" opType="dialog" cssClass="btn btn-outline btn-filter" callback="query">
                            <i class="fa fa-plus"></i><span class="hd">&nbsp;&nbsp;新增</span>
                        </soul:button>
                        <a nav-target="mainFrame" href="/agentCurrencyExchange/list.html" class="btn btn-outline btn-filter">
                            <i class="fa fa-refresh"></i>
                            <span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span>
                        </a>
                    </div>

                    <div class="search-wrapper btn-group pull-right">
                        <div class="input-group">

                            <div class="input-group-btn">
                                <div selectdiv="" value="" class="btn-group chosen-select-no-single" initprompt="请选择" callback="query">
                                    <input type="hidden" name="" value="" id="searchlist">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" style="overflow: hidden;padding-right: 10px;" aria-expanded="false">
                                        <span prompt="prompt" style="display:inline-block;min-width: 60px;">请选择</span>
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="">请选择</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.code">货币编码</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.name">货币名称</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.exchange">货币汇率</a></li>
                                    </ul>
                                </div>
                            </div>

                            <input type="text" class="form-control" id="searchtext">
                            <span class="input-group-btn">
                                <button title="搜索" class="btn btn-filter" type="button" data-rel="{&quot;precall&quot;:&quot;checksearch&quot;,&quot;callback&quot;:&quot;&quot;,post:&quot;&quot;,opType:&quot;function&quot;,dataType:&quot;&quot;,target:&quot;query&quot;,confirm:&quot;&quot;,text:&quot;搜索&quot;,size:&quot;&quot; }">
                                    <i class="fa fa-search"></i>
                                    <span class="hd">&nbsp;搜索</span>
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

<soul:import type="list"/>