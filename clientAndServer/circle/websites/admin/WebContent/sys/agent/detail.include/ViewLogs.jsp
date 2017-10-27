<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form id="viewLogsForm" action="${root}/vAgentManage/detail/log.html?search.operatorId=${command.search.operatorId}" method="post">
<div id="tab-10" class="tab-pane">
    <div class="scope_wrap clearfix">
        <div class="col-sm-6 form-inline al-left">
            <b>${views.common['timeRange']}：</b>
            <div class="input-group date">
                <gb:dateRange useRange="true" format="${DateFormat.DAY}" startDate="${command.search.operatorBegin}" endDate="${command.search.operatorEnd}" maxDate="${nowTime}" callback="searchQuery" startName="search.operatorBegin" endName="search.operatorEnd"/>
            </div>
        </div>
    </div>
    <div id="editable_wrapper" class="dataTables_wrapper search-list-container" role="grid">
        <%@include file="PartialLogs.jsp"%>
    </div>
</div>
</form:form>
<script type="text/javascript">
    curl(['site/agent/ViewLogs'], function(Page) {
        page.index = new Page();
    });
</script>