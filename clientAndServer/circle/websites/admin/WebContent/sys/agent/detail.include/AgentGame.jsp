<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/userAgentGame/list.html?search.agentId=${agentId}" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>

    <div class="scope_wrap clearfix">
        <div class="col-sm-6 form-inline al-left">
            <soul:button target="${root}/userAgentGame/create.html?search.agentId=${agentId}" text="" opType="dialog" cssClass="btn btn-outline btn-filter" callback="query">
                <i class="fa fa-plus"></i>
                <span class="hd">&nbsp;&nbsp;新增代理游戏</span>
            </soul:button>
        </div>
    </div>

    <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
        <%@include file="AgentGamePartial.jsp"%>
    </div>
</form:form>
<soul:import res="site/agent/AgentGame"/>

