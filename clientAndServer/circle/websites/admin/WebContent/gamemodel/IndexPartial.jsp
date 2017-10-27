<%--@elvariable id="command" type="g.model.gamemodel.vo.GameModelListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>所属游戏</th>
                <th>模式名称</th>
                <th>模式代码</th>
                <th>${views.common['status']}</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>
                        <c:forEach items="${gameListVo.result}" var="game">
                            <c:choose>
                                <c:when test="${p.gameId eq game.id}">
                                    ${game.name}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>${p.name}</td>
                    <td>${p.code}</td>
                    <td>
                        <span class="label label-success">${p.status eq '10' ? views.common['enable'] : ''}</span>
                        <span class="label label-danger">${p.status eq '20' ? views.common['forbidden'] : ''}</span>
                        <span class="label label-warning">${p.status eq '30' ? views.common['safeguard'] : ''}</span>
                    </td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/gameModel/view.html?id=${p.id}" text="查看" opType="dialog" />
                            <soul:button target="${root}/gameModel/edit.html?id=${p.id}" text="编辑" opType="dialog" callback="query" />
                            <soul:button target="${root}/gameModel/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+6}" class="no-content_wrap">
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
<!--//endregion your codes 1-->
