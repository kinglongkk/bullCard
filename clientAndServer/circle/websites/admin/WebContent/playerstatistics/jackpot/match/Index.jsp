<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningWinCountListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/jackpotReport/jackpot.html?sysuserid=${sysuserid}&gameid=${gameid}&gamemodelid=${gamemodelid}&startTimeStr=${startTimeStr}&endTimeStr=${endTimeStr}" method="post">
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>奖池</span><span>/</span><span>赛事详情</span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <a nav-target="mainFrame" href="/jackpotReport/jackpot.html?sysuserid=${sysuserid}&gameid=${gameid}&gamemodelid=${gamemodelid}&startTimeStr=${startTimeStr}&endTimeStr=${endTimeStr}" class="btn btn-outline btn-filter"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>
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
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.gameroomname">房间名称</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.id">局号</a></li>
                                    </ul>
                                </div>
                            </div>
                            <input type="text" class="form-control" id="searchtext">
                            <span class="input-group-btn">
                                  <soul:button target="query" opType="function" text="查询" cssClass="btn btn-filter" >
                                      <i class=" fa fa-search"> </i>
                                      <span>查询</span>
                                  </soul:button>
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
