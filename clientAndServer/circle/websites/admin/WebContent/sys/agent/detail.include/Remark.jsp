<%--@elvariable id="command" type="g.model.master.player.vo.RemarkListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--备注-->
<form:form id="agentRemarkForm" action="${root}/playerRemark/agentRemark.html?search.entityUserId=${command.search.entityUserId}&search.operatorId=${command.search.operatorId}" method="post">
    <div class="clearfix remark-wrap">
        <soul:button target="${root}/playerRemark/create.html?result.entityUserId=${command.search.entityUserId}&result.remarkType=remark&result.model=agent&result.entityId=${command.search.entityId}" opType="dialog" callback="remark.callBack" cssClass="btn btn-filter m-l-xs pull-left" text="${views.column['player.view.remark.addRemark']}">
            <i class="fa fa-plus"></i>&nbsp;&nbsp;${views.column['player.view.remark.addRemark']}
        </soul:button>
    </div>
    <br>
    <div class="tab-left-b">
    <div role="grid" class="dataTables_wrapper" id="editable_wrapper">
        <div class="table-responsive">
        <table class="table table-striped table-bordered table-hover dataTable" aria-describedby="editable_info">
            <thead>
            <tr>
                <th>${views.column["Remark.remarkTime"]}</th>
                <th>${views.column["Remark.remarkType"]}</th>
                <th>${views.column["Remark.remarkTitle"]}</th>
                <th>${views.column["Remark.username"]}</th>
                <th>${views.column["Remark.operate"]}</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${command.result}" var="s" varStatus="vs">
                <tr ${(vs.index % 2)==0 ? 'class="gradeA odd"':' class="gradeA even"'}>
                    <td>${soulFn:formatDateTz(s.remarkTime, DateFormat.DAY_SECOND,timeZone)}</td>
                    <td>${dicts.common.remark_type[s.remarkType]}</td>
                    <td title="${s.remarkTitle}">${fn:substring(s.remarkTitle, 0, 20)}<c:if test="${fn:length(s.remarkTitle)>20}">...</c:if></td>
                    <td>${s.operator}</td>
                    <td>
                        <soul:button target="${root}/playerRemark/edit.html?id=${s.id}" text="${views.common['edit']}" opType="dialog" callback="remark.callBack"/>
                        <soul:button target="${root}/playerRemark/view.html?id=${s.id}" text="${views.common['detail']}" opType="dialog"/>
                        <soul:button target="${root}/playerRemark/delete.html?id=${s.id}" text="${views.common['delete']}" callback="remark.query" opType="ajax" dataType="json" confirm="${views.role['player.view.remark.sureToDelete']}？"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
        <soul:pagination/>
    </div>
    </div>
</form:form>
<script>
    curl(["site/player/agent/Remark"], function (Remark) {
        page.remark = new Remark();
    });
</script>

