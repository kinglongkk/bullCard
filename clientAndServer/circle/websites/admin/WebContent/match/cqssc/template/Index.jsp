<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--运营管理-消息公告-->
<form:form action="${root}/matchCqsscTemplate/list.html" method="post">
    <div class="row">
        <input type="hidden" id="defaultLoacl" value="${locale}"/>

        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>控盘</span>
            <span>/</span>
            <span>重庆时时彩</span>
            <span>/</span>
            <span>彩期管理</span>
        </div>

        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="filter-wraper clearfix">
                    <div class="btn-group pull-left m-r-n-xs">
                        <div class="input-group">
                            <div class="input-group content-width-limit-250 pull-left">
                                <span class="input-group-addon abroder-no"><b>期号：</b></span>
                                <input type="text" class="form-control" name="search.no">
                                <span class="input-group-btn">
                                    <soul:button cssClass="btn btn-filter" tag="button" opType="function" text="搜索" target="query">
                                        <i class="fa fa-search"></i><span class="hd">&nbsp;搜索</span>
                                    </soul:button>
                                </span>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
                <!--表格内容-->
                <div class="search-list-container">
                    <%@include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </div>
</form:form>
<soul:import type="list"/>



