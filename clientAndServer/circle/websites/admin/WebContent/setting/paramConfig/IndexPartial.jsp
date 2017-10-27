<%--@elvariable id="command" type="g.model.master.player.vo.VUserPlayerListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
        <tr role="row" class="bg-gray">
            <th>参数名称</th>
            <th>默认值</th>
            <th>当前值</th>
            <th>描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="p" varStatus="status">
            <tr class="tab-detail" data-id="${p.id}">
                <td>${p.paramCode}</td>
                <td class="text_r">${p.defaultValue}</td>
                <td class="text_r">${p.paramValue}</td>
                <td>${p.remark}</td>
                <td class="text_c"><soul:button target="${root}/parameterConfig/toConfigEdit.html?search.id=${p.id}" text="${views.common['edit']}" opType="dialog" callback="reload"/></td>
        </c:forEach>
        <c:if test="${fn:length(command.result)<1}">
        <tr>
            <td colspan="${fn:length(command.fields)+5}" class="no-content_wrap">
                <div>
                    <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                </div>
            </td>
        </tr>
        </c:if>
        </tbody>
    </table>
</div>
<soul:pagination/>