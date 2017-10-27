<%--@elvariable id="command" type="g.model.match.vo.VBetListListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/vBetList/lottery.html" method="post">
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.bet["moduleName"]}</span>
            <a href="/bet/list/lottery.html" style="display: none" name="refresh" nav-target="mainFrame"/>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="filter-wraper clearfix">
                    <div class="btn-group pull-left m-r-n-xs">
                        <div class="input-group">
                            <div class="input-group pull-left">
                                <span class="input-group-addon abroder-no"><b>下注时间：</b></span>
                                <gb:dateRange format="${DateFormat.DAY}" startName="search.betTimeFrom" endName="search.betTimeTo" useRange="true" useToday="true"></gb:dateRange>
                            </div>
                            <div class="input-group pull-left">
                                <span class="input-group-addon abroder-no"><b>结算状态：</b></span>
                                <gb:select name="search.settleStatus" cssClass="btn-group chosen-select-no-single" prompt="${views.common['all']}" value="${command.search.settleStatus}" list="${betSettleStatus}"/>
                            </div>
                        </div>
                    </div>
                    <div class="search-wrapper btn-group pull-right">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <div>
                                    <select tabindex="-1" class="chosen-select-no-single" id="searchlist">
                                        <c:forEach items="${command.searchList()}" var="item">
                                            <option value="${item.key}">${item.value}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <input type="text"  class="form-control" id="searchtext" name="${command.searchList().get(0).key}" placeholder="${command.searchList().get(0).value}">
                            <span class="input-group-btn">
                                <soul:button cssClass="btn btn-filter" precall="checksearch" tag="button" opType="function" text="${views.common['search']}" target="query">
                                    <i class="fa fa-search"></i><span class="hd">&nbsp;${views.common['search']}</span>
                                </soul:button>
                                <%--<input class="btn btn-filter" type="reset" value="${views.common['clearAll']}"/>--%>
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

<!--//region your codes 3-->
<soul:import res="site/bet/list/Index"/>
<!--//endregion your codes 3-->