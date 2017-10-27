<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--运营管理-消息公告-->
<form:form action="${root}/match/cqssc/list.html" method="post">
    <div class="row">
        <input type="hidden" id="defaultLoacl" value="${locale}"/>
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['控盘']}</span><span>/</span><span>${views.sysResource['重庆时时彩']}</span>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="filter-wraper clearfix">
                    <div class="btn-group pull-left m-r-n-xs">
                        <div class="input-group">
                            <div class="input-group content-width-limit-250 pull-left">
                                <span class="input-group-addon abroder-no"><b>开始时间：</b></span>
                                <gb:dateRange useRange="true" format="${DateFormat.DAY_SECOND}" callback="query" startName="search.startTime"
                                              endName="search.endTime" style="" />
                            </div>
                            <div class="input-group content-width-limit-250 pull-left">
                                <span class="input-group-addon abroder-no"><b>期号：</b></span>
                                <input type="text" class="form-control" name="search.code">
                            </div>

                            <div class="input-group content-width-limit-250 pull-left">
                                <span class="input-group-addon abroder-no"><b>结算状态：</b></span>
                                <gb:select name="search.settleStatus" cssClass="btn-group chosen-select-no-single" prompt="${views.common['all']}" value="${command.search.settleStatus}" list="${betSettleStatus}"/>
                            </div>

                            <span class="input-group-btn content-width-limit-250 pull-left">
                                <soul:button cssClass="btn btn-filter" tag="button" opType="function" text="搜索" target="query">
                                    <i class="fa fa-search"></i><span class="hd">&nbsp;搜索</span>
                                </soul:button>
                            </span>
                        </div>
                    </div>
                </div>
                <!--表格内容-->
                <div class="dataTables_wrapper search-list-container">
                    <%@include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </div>
</form:form>
<soul:import res="site/match/cqssc/Index"/>


