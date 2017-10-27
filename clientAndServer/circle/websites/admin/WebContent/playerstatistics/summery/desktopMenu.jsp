<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<style>
    .dxlabel{
        color: #235ec0;
        font-size: 18px;
        font-weight: bold;
        fill: #235ec0;
    }
    .titledx{
        float: left;margin-left: 47px;
    }
    .seach-but{
        margin-top: -4px;
    }
</style>
<form:form action="${root}/playerSummery/desktopMenu.html" method="post">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>首页</span><span>/</span>
        <span>桌面统计</span>
        <%--<a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>--%>
    </div>
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="panel panel-default">
        <div class="panel-body">
            <!--//region your codes 2-->
            <div class="row border-b-1">
                <div style="padding:0 0 20px 20px;float: left">
                    年:
                    <select name="year" style="height: 34px;border-radius: 3px;">
                        <c:forEach begin="2016" end="2100" var="i">
                            <option value="${i}" <c:if test="${i == year}">selected</c:if> >${i}</option>
                        </c:forEach>
                    </select>
                    月:
                    <select name="month" style="height: 34px; border-radius: 3px;">
                        <c:forEach begin="1" end="12" var="i">
                            <option value="${i}" <c:if test="${i == month}">selected</c:if>  >${i}</option>
                        </c:forEach>
                    </select>
                    代理商:
                    <select name="agent" style="height: 34px; border-radius: 3px;">
                        <option value="all">全部</option>
                        <c:forEach items="${listAgent}" var="i">
                            <option value="${i.id}@@@${i.username}">${i.username}</option>
                            <%--<input type="checkbox" name="agentId" value="${i.id}" checked="checked" data="${i.realName}">${i.realName}--%>
                        </c:forEach>
                    </select>
                    <soul:button target="query" opType="function" text="查询" cssClass="btn btn-filter seach-but" >
                        <i class=" fa fa-search"> </i>
                        <span>查询</span>
                    </soul:button>
                </div>
                <div style="float: left;">
                    <div class="titledx">
                        <label class="today_title">今日详情</label>
                    </div>
                    <div class="titledx">
                      <label class="today_title">盈亏:</label>
                      <span style="color: red;" id="profitAmount">加载中...</span>
                        <br>
                        <label style="color: #2095f2">提示:实时盈亏延迟2分钟</label>
                    </div>
                    <div class="titledx">
                        <label class="today_title">活跃玩家:</label>
                        <span style="color: red;" id="dxactive">加载中...</span>
                    </div>
                    <div class="titledx">
                        <label class="today_title">新增玩家:</label>
                        <span style="color: red;" id="dxadd">加载中...</span>
                    </div>
                </div>
            </div>

            <br/>
            <div class="search-list-container">
                <%@ include file="desktopMenuPartial.jsp" %>
            </div>
            <!--//endregion your codes 2-->
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
