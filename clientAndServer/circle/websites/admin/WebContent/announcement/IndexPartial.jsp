<%--@elvariable id="command" type="g.model.announcement.vo.GameAnnouncementListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <table class="table table-condensed table-hover table-striped table-bordered">
        <thead>
        <tr>
            <th width="30px">#</th>
            <th width="70px">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>
                    <div class="joy-list-row-operations">
                        <soul:button target="${root}/gameAnnouncement/view.html?id=${p.id}" text="查看" opType="dialog" />
                        <soul:button target="${root}/gameAnnouncement/edit.html?id=${p.id}" text="编辑" opType="dialog" />
                        <soul:button target="${root}/gameAnnouncement/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
