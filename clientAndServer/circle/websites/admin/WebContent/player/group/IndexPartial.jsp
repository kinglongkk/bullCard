<%--@elvariable id="command" type="g.model.agent.vo.VSubAccountListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h">
    <table class="table table-striped table-hover dataTable" aria-describedby="editable_info">
        <thead>
        <tr role="row" class="bg-gray">
            <th>分组名称</th>
            <th>玩家数</th>
            <th>是否为默认分组</th>
            <th>${views.common['operate']}</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="s">
            <tr class="gradeA odd tab-detail">
                <td>${s.groupName}</td>
                <td>${s.playernum}</td>
                <td>${true eq s.isDefault ? '是' : '否'}</td>
                <td>
                    <soul:button target="${root}/vUserPlayerGroup/view.html?id=${s.id}" text="${views.common['detail']}" opType="dialog" />
                    <soul:button target="${root}/vUserPlayerGroup/edit.html?id=${s.id}" text="${views.common['edit']}"  opType="dialog" callback="query"/>
                    <c:if test="${s.playernum==0 and s.isDefault eq false}">
                        <soul:button target="${root}/vUserPlayerGroup/deletePlayerGroup.html?search.id=${s.id}" text="删除" opType="ajax" callback="query"/>
                    </c:if>
                    <c:if test="${s.isDefault eq false}">
                    <soul:button target="${root}/vUserPlayerGroup/setDefault.html?search.id=${s.id}" text="设置为默认分组"  opType="ajax" callback="query"/>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>
