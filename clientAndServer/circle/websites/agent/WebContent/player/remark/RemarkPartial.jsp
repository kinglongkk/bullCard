<%--@elvariable id="command" type="g.model.master.player.vo.RemarkListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--备注-->
<div class="dataTables_wrapper" role="grid">
    <div class="table-responsive table-min-h">
        <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
            <div class="table-responsive" id="tab-1">
                <table class="table table-striped table-bordered table-hover dataTable"
                       aria-describedby="editable_info">
                    <thead>
                    <tr>
                        <th>${views.playerAdmin["Remark.remarkType"]}</th>
                        <th>${views.playerAdmin["Remark.remarkTime"]}</th>
                        <th>${views.playerAdmin["Remark.remarkTitle"]}</th>
                        <th>${views.playerAdmin["Remark.username"]}</th>
                        <th>${views.playerAdmin["Remark.operate"]}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${command.result}" var="s" varStatus="vs">
                        <tr ${(vs.index % 2)==0 ? 'class="gradeA odd"':' class="gradeA even"'}>
                            <td>${dicts.common.remark_type[s.remarkType]}</td>
                            <td>${soulFn:formatDateTz(s.remarkTime, DateFormat.DAY_SECOND,timeZone)}</td>
                            <td title="${s.remarkTitle}">${fn:substring(s.remarkTitle, 0, 20)}<c:if
                                    test="${fn:length(s.remarkTitle)>20}">...</c:if></td>
                            <td>${s.operator}</td>
                            <td>
                                <soul:button target="${root}/playerRemark/edit.html?id=${s.id}"
                                             text="${views.common['edit']}"
                                             opType="dialog" callback="remark.queryRemark"/>
                                <soul:button target="${root}/playerRemark/view.html?id=${s.id}"
                                             text="${views.common['detail']}"
                                             opType="dialog"/>
                                <soul:button target="${root}/playerRemark/delete.html?id=${s.id}"
                                             text="${views.common['delete']}"
                                             callback="remark.queryRemark" opType="ajax" dataType="json"
                                             confirm="${views.playerAdmin['player.view.remark.sureToDelete']}？"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="clearfix">
    <soul:button
            target="${root}/playerRemark/create.html?result.entityUserId=${command.search.entityUserId}&result.remarkType=remark&result.model=player"
            opType="dialog" callback="remark.queryRemark" cssClass="btn btn-filter pull-left"
            text="${views.playerAdmin['player.view.remark.addRemark']}">
        <i class="fa fa-plus"></i>&nbsp;&nbsp;${views.playerAdmin['player.view.remark.addRemark']}
    </soul:button>
</div>

<soul:pagination/>

