<%--@elvariable id="command" type="g.model.gameRoomroom.vo.gameRoomRoomListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<style>
    .modal-content{
        width: 900px;
    }
</style>
<form:form>
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>游戏</span><span>/</span><span>游戏房间</span>
            <a href="/gameRoom/list.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>
        </div>
      <div class="col-lg-12">
        <div class="wrapper white-bg shadow">
           <div class="clearfix filter-wraper border-b-1">
                    <div class="pull-left">
                        <soul:button target="${root}/gameRoom/create.html" text="" opType="dialog" cssClass="btn btn-outline btn-filter" callback="query">
                            <i class="fa fa-plus"></i><span class="hd">&nbsp;&nbsp;新增游戏房间</span>
                        </soul:button>
                        <a nav-target="mainFrame" href="/gameBull100Room/bull100Room.html" class="btn btn-outline btn-filter"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>
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
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.name">房间名称</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.description">房间描述</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.dealerBlance">上庄最低额度</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.maxLimitPlayerNumber">最大容纳玩家数</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.minLimitPlayerBlance">最小玩家余额</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.betMax">最大投注数</a></li>
                                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)" key="search.betMin">最小投注数</a></li>
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
                    <div class="col-md-1 pull-right">
                        <form:select class="btn-group chosen-select-no-single" path="search.status" callback="query">
                            <form:option value="">${views.common['all_status']}</form:option>
                            <form:option value="10">${views.common['enable']}</form:option>
                            <form:option value="20">${views.common['forbidden']}</form:option>
                            <form:option value="30">${views.common['safeguard']}</form:option>
                        </form:select>
                    </div>
                    <div class="col-md-1 pull-right">
                        <form:select class="btn-group chosen-select-no-single" path="search.gameModelId" callback="query">
                            <form:option value="">全部游戏玩法</form:option>
                                <c:forEach items="${gameModelListVo.result}" var="gameModel">
                                    <form:option value="${gameModel.id}">${gameModel.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="col-md-1 pull-right">
                        <form:select class="btn-group chosen-select-no-single" path="search.gameId" callback="query">
                            <form:option value="">全部游戏</form:option>
                                <c:forEach items="${gameListVo.result}" var="game">
                                            <form:option value="${game.id}">${game.name}</form:option>
                                </c:forEach>
                        </form:select>
                    </div>
              </div>
                 <div class="search-list-container">
                     <%@ include file="IndexPartial.jsp" %>
                 </div>
           </div>
        </div>
      </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->