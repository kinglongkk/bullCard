<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form id="viewLogsForm" action="${root}/agentQuotaTransaction/list.html?search.agentId=${agentId}" method="post">
    <div id="tab-10" class="tab-pane">

        <div class="scope_wrap clearfix">
            <div class="col-sm-6 form-inline al-left">
                <b>时间范围</b>
                <div class="input-group date">
                    <gb:dateRange useRange="true" format="${DateFormat.DAY}" callback="query" startName="search.startTime" endName="search.endTime" style="" />
                </div>
            </div>

            <div class="col-md-1 pull-right">
                <form:select class="btn-group chosen-select-no-single" path="search.type" callback="query">
                    <form:option value="">全部</form:option>
                    <form:option value="SETTLE">结算</form:option>
                    <form:option value="DEPOSIT">存款</form:option>
                </form:select>
            </div>
        </div>

        <div id="editable_wrapper" class="dataTables_wrapper search-list-container" role="grid">
            <%@include file="AgentQuotaTransactionPartial.jsp"%>
        </div>

    </div>
</form:form>
<soul:import type="list"/>

